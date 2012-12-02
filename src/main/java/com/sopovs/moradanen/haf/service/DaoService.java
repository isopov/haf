package com.sopovs.moradanen.haf.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.domain.Employee;

@Service
@Transactional
public class DaoService implements InitializingBean, IDaoService {
	private final Logger logger = LoggerFactory.getLogger(DaoService.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	protected void dropIfExistsAndCreateSchema() {
		template.update("drop table employee if exists",
				Collections.<String, String> emptyMap());
		template.update("drop table dpublicepartment if exists",
				Collections.<String, String> emptyMap());

		template.update(
				"create table department(id int identity primary key, name varchar(30))",
				Collections.<String, String> emptyMap());
		template.update("alter table department add unique (name)",
				Collections.<String, String> emptyMap());

		template.update(
				"create table employee(id int identity primary key,"
						+ " first_name varchar(30), last_name varchar(30),"
						+ " salary double, birthdate date, active char(1), department_id int)",
				Collections.<String, String> emptyMap());

		// TODO There were some problems creating named foreign key constraint
		template.update("alter table employee add foreign key (department_id)"
				+ " references department(id)",
				Collections.<String, String> emptyMap());

		logger.info("schema created");

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		dropIfExistsAndCreateSchema();

		List<String> departmentNames = Arrays.asList("Marketing", "Stocking",
				"Accounting", "Logistics", "Legal", "Planning", "Security",
				"Human resources");

		List<String> firstNames = Arrays.asList("John", "Baltazar", "Barnabas",
				"Bartlomiej", "Bazyli", "Bede", "Benedykt", "Bernard",
				"Bernardyn", "Blazej", "Bogumil", "Boleslaw", "Bonifacy",
				"Bronislaw", "Brunon", "Fabian", "Feliks", "Filip", "Florjan",
				"Franciszek", "Fryderyk", "Jacek", "Jakub", "Jan",
				"Jan Baptysta", "Jan Chrzciciel", "Jan Jozef", "Jan Kanty",
				"Jan Nepomucen", "Januariusz", "Jerzy", "Joachim", "Jozef",
				"Jozefat", "Julian", "Justin");

		List<String> lastNames = Arrays.asList("Smih", "Jones", "Williams",
				"Brown", "Taylor", "Davies", "Wilson", "Evans", "Thomas",
				"Johnson", "Roberts", "Walker", "Wright", "Robinson",
				"Thompson", "White", "Hughes", "Edwards", "Green", "Hall",
				"Wood", "Harris", "Lewis", "Martin", "Jackson", "Clarke",
				"Clark", "Turner", "Hill", "Scott", "Cooper", "Morris", "Ward",
				"Moore", "King", "Watson", "Baker", "Harrison", "Morgan",
				"Patel", "Young", "Allen", "Mitchell", "James", "Anderson",
				"Phillips", "Lee", "Bell", "Parker", "Davis	");

		template.batchUpdate(
				"insert into department(name) values(:name)",
				Lists.transform(departmentNames,
						new Function<String, SqlParameterSource>() {
							@Override
							public SqlParameterSource apply(String name) {
								return new MapSqlParameterSource("name", name);
							}
						}).toArray(
						new MapSqlParameterSource[departmentNames.size()]));

		MapSqlParameterSource[] empoyees = new MapSqlParameterSource[1000];
		Random r = new Random();
		for (int i = 0; i < empoyees.length; i++) {
			MapSqlParameterSource employeeParameters = new MapSqlParameterSource();

			employeeParameters.addValue("departmentName",
					departmentNames.get(r.nextInt(departmentNames.size())));
			employeeParameters.addValue("firstName",
					firstNames.get(r.nextInt(firstNames.size())));
			employeeParameters.addValue("lastName",
					lastNames.get(r.nextInt(lastNames.size())));
			employeeParameters
					.addValue("salary", r.nextDouble() * 10000 + 1000);
			// Random birthdate in range 1950-1990
			employeeParameters.addValue("birthDate", new java.sql.Date(
					new LocalDate(1950, 1, 1).plusDays(r.nextInt(40 * 365))
							.toDate().getTime()));
			// Approximately 20% of employee will be not active
			employeeParameters.addValue("active", (r.nextDouble() > 0.8) ? "N"
					: "Y");
			empoyees[i] = employeeParameters;
		}

		template.batchUpdate(
				"insert into employee(first_name, last_name, salary, birthdate, active, department_id)"
						+ " values(:firstName, :lastName, :salary, :birthDate, :active,"
						+ " (select id from department where name=:departmentName))",
				empoyees);

	}

	@Override
	public void testTransactional() {
		template.update("insert into department(name) values(:name)",
				new MapSqlParameterSource("name", "TestForTransactional"));
		// Second insert should fail and rollback transaction
		template.update("insert into department(name) values(:name)",
				new MapSqlParameterSource("name", "TestForTransactional"));
	}

	@Override
	public Employee getEmployee(Long id) {
		return template.queryForObject(
				"select e.*, d.id as dep_id, d.name as dep_name"
						+ " from employee e join department d"
						+ " on e.department_id=d.id where e.id=:id",
				new MapSqlParameterSource("id", id),
				new EmployeeWithDepartmentRowMapper());
	}

	@Override
	public List<Employee> listEmployees() {
		return template.query("select * from employee order by id",
				Collections.<String, String> emptyMap(),
				new EmployeeRowMapper());
	}

	@Override
	public List<Employee> searchEmployees(String criteria) {
		return template.query("select * from employee"
				+ " where (first_name + last_name) like :criteria",
				new MapSqlParameterSource("criteria", criteria
						.replace("?", "_").replace("*", "%")),
				new EmployeeRowMapper());
	}

	@Override
	public Department getDepartment(Long id) {
		Department result = template.queryForObject(
				"select * from department where id=:id",
				new MapSqlParameterSource("id", id), new DepartmentRowMapper());
		result.setEmployees(template.query(
				"select * from employee where department_id=:id order by id",
				new MapSqlParameterSource("id", id), new EmployeeRowMapper()));
		return result;
	}

	@Override
	public void saveOrUpdateDepartment(Department department) {
		if (department.getId() == null) {
			template.update("insert into department(name) values(:name)",
					new MapSqlParameterSource("name", department.getName()));
		} else {
			template.update(
					"update department set name=:name where id=:id",
					new MapSqlParameterSource().addValue("id",
							department.getId()).addValue("name",
							department.getName()));
		}
	}

	@Override
	public void saveOrUpdateEmployee(Employee employee) {
		MapSqlParameterSource pars = new MapSqlParameterSource()
				.addValue("id", employee.getId())
				.addValue("firstName", employee.getFirstName())
				.addValue("lastName", employee.getLastName())
				.addValue("active", employee.isActive() ? "Y" : "N")
				.addValue("salary", employee.getSalary())
				.addValue("departmentId", employee.getDepartment().getId())
				.addValue(
						"birthdate",
						new java.sql.Date(employee.getBirthdate().toDate()
								.getTime()));

		if (employee.getId() == null) {
			template.update(
					"insert into employee(first_name, last_name,active,birthdate,salary,department_id)"
							+ " values(:firstName,:lastName,:active,:birthdate,:salary,:departmentId)",
					pars);
		} else {
			template.update(
					"update employee set first_name=:firstName, last_name=:lastName, active=:active,"
							+ " birthdate=:birthdate,salary=:salary,department_id=:departmentId"
							+ " where id=:id", pars);
		}

	}

	@Override
	public List<Department> listDepartments() {
		return template.query("select * from department",
				Collections.<String, String> emptyMap(),
				new DepartmentRowMapper());
	}

	@VisibleForTesting
	static class DepartmentRowMapper implements RowMapper<Department> {
		@Override
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Department(rs.getLong("id"), rs.getString("name"));
		}
	}

	@VisibleForTesting
	static class EmployeeRowMapper implements RowMapper<Employee> {
		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee result = new Employee();
			result.setId(rs.getLong("id"));
			result.setFirstName(rs.getString("first_name"));
			result.setLastName(rs.getString("last_name"));
			result.setActive("Y".equals(rs.getString("active")));
			result.setSalary(rs.getDouble("salary"));
			result.setBirthdate(LocalDate.fromDateFields(rs
					.getDate("birthdate")));
			return result;
		}
	}

	@VisibleForTesting
	static class EmployeeWithDepartmentRowMapper extends EmployeeRowMapper {
		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee result = super.mapRow(rs, rowNum);
			result.setDepartment(new Department(rs.getLong("dep_id"), rs
					.getString("dep_name")));
			return result;
		}
	}
}
