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

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.domain.Employee;

@Service
public class DaoService implements InitializingBean {
	private final Logger logger = LoggerFactory.getLogger(DaoService.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Override
	public void afterPropertiesSet() throws Exception {
		template.update("drop table employee if exists",
				Collections.<String, String> emptyMap());
		template.update("drop table department if exists",
				Collections.<String, String> emptyMap());

		template.update(
				"create table department(id int identity primary key, name varchar(30))",
				Collections.<String, String> emptyMap());

		template.update(
				"create table employee(id int identity primary key,"
						+ " first_name varchar(30), last_name varchar(30),"
						+ " salary double, bithdate date, active char(1), department_id int)",
				Collections.<String, String> emptyMap());

		// TODO There were some problems creating named foreign key constraint
		template.update("alter table employee add foreign key (department_id)"
				+ " references department(id)",
				Collections.<String, String> emptyMap());

		logger.info("schema created");

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
			// Random bithdate in range 1950-1990
			employeeParameters.addValue("bithDate", new java.sql.Date(
					new LocalDate(1950, 1, 1).plusDays(r.nextInt(40 * 365))
							.toDate().getTime()));
			// Approximately 20% of employee will be not active
			employeeParameters.addValue("active", (r.nextDouble() > 0.8) ? "N"
					: "Y");
			empoyees[i] = employeeParameters;
		}

		template.batchUpdate(
				"insert into employee(first_name, last_name, salary, bithdate, active, department_id)"
						+ " values(:firstName, :lastName, :salary, :bithDate, :active,"
						+ " (select id from department where name=:departmentName))",
				empoyees);

	}

	public Employee getEmployee(Long id) {
		return template.queryForObject(
				"select e.*, d.id as dep_id, d.name as dep_name"
						+ " from employee e join department d"
						+ " on e.department_id=d.id where e.id=:id",
				new MapSqlParameterSource("id", id),
				new EmployeeWithDepartmentRowMapper());
	}

	public List<Employee> listEmployees() {
		return template.query("select * from employee",
				Collections.<String, String> emptyMap(),
				new EmployeeRowMapper());
	}

	public Department getDepartment(Long id) {
		Department result = template.queryForObject(
				"select * from department where id=:id",
				new MapSqlParameterSource("id", id), new DepartmentRowMapper());
		result.setEmployees(template.query(
				"select * from employee where department_id=:id",
				new MapSqlParameterSource("id", id), new EmployeeRowMapper()));
		return result;
	}

	public List<Department> listDepartments() {
		return template.query("select * from department",
				Collections.<String, String> emptyMap(),
				new DepartmentRowMapper());
	}

	private static class DepartmentRowMapper implements RowMapper<Department> {
		@Override
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Department(rs.getLong("id"), rs.getString("name"));
		}
	}

	private static class EmployeeRowMapper implements RowMapper<Employee> {
		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee result = new Employee();
			result.setId(rs.getLong("id"));
			result.setFirstName(rs.getString("first_name"));
			result.setLastName(rs.getString("last_name"));
			result.setActive("Y".equals(rs.getString("active")));
			result.setSalary(rs.getDouble("salary"));
			result.setBirthdate(LocalDate.fromDateFields(rs.getDate("bithdate")));
			return result;
		}
	}

	private static class EmployeeWithDepartmentRowMapper extends
			EmployeeRowMapper {
		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee result = super.mapRow(rs, rowNum);
			result.setDepartment(new Department(rs.getLong("dep_id"), rs
					.getString("dep_name")));
			return result;
		}
	}

}
