package com.sopovs.moradanen.haf.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.domain.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("default")
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DaoServiceTest {

	@Autowired
	private IDaoService dao;

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Test
	public void testListEmployees() {
		Assert.assertTrue(dao.listEmployees().size() > 500);
	}

	@Test
	public void testListDepartments() {
		Assert.assertTrue(dao.listDepartments().size() > 5);
	}

	@Test
	public void testNewDepartment() {
		String testDepartmentname = "TestDepartment";
		dao.saveOrUpdateDepartment(new Department(null, testDepartmentname));

		Department depFromDb = template.queryForObject(
				"select * from department where name=:name",
				new MapSqlParameterSource("name", testDepartmentname),
				new HsqlDaoService.DepartmentRowMapper());

		Assert.assertEquals(testDepartmentname, depFromDb.getName());
	}

	@Test
	public void testGetDepartment() {
		String testDepartmentname = "Security";
		Long id = template.queryForObject(
				"select * from department where name=:name",
				new MapSqlParameterSource("name", testDepartmentname),
				new HsqlDaoService.DepartmentRowMapper()).getId();
		Department depFromDao = dao.getDepartment(id);

		Assert.assertEquals(testDepartmentname, depFromDao.getName());
		Assert.assertEquals(id, depFromDao.getId());
		// In theory this particular assert can fail, but if we will run this
		// test non-stop the amount of time required to this is comparable with
		// lifetime of the Universe (and due to pseudo-randomness instead of
		// real randomness this may either happen earlier, or not happen at all)
		Assert.assertFalse(depFromDao.getEmployees().isEmpty());
	}

	@Test
	public void testSaveDepartment() {
		Department marketingDepartment = template.queryForObject(
				"select * from department where name=:name",
				new MapSqlParameterSource("name", "Marketing"),
				new HsqlDaoService.DepartmentRowMapper());
		marketingDepartment.setName("Marketing Testing Update");
		dao.saveOrUpdateDepartment(marketingDepartment);

		Department marketingDepartmentUpdated = template.queryForObject(
				"select * from department where id=:id",
				new MapSqlParameterSource("id", marketingDepartment.getId()),
				new HsqlDaoService.DepartmentRowMapper());

		Assert.assertEquals("Marketing Testing Update",
				marketingDepartmentUpdated.getName());
	}

	@Test
	public void testNewEmployee() {
		Employee testEmployee = new Employee(null, "TestFirstName",
				"TestLastName", 42.0, new LocalDate(1990, 5, 5), true,
				new Department(0L, null));
		dao.saveOrUpdateEmployee(testEmployee);

		Employee depFromDb = template.queryForObject(
				"select * from employee where first_name=:firstName",
				new MapSqlParameterSource("firstName", "TestFirstName"),
				new HsqlDaoService.EmployeeRowMapper());

		Assert.assertNotNull(depFromDb.getId());
		assertEquals(testEmployee.getFirstName(), depFromDb.getFirstName());
		assertEquals(testEmployee.getLastName(), depFromDb.getLastName());
		assertEquals(testEmployee.isActive(), depFromDb.isActive());
		assertEquals(testEmployee.getBirthdate(), depFromDb.getBirthdate());

	}

	@Test
	public void testSaveEmployee() {
		Employee employee = template.queryForObject(
				"select * from employee where id=:id",
				new MapSqlParameterSource("id", 10L),
				new HsqlDaoService.EmployeeRowMapper());

		employee.setActive(!employee.isActive());
		employee.setFirstName(employee.getFirstName() + " TestUpdated");
		employee.setLastName(employee.getLastName() + " TestUpdated");
		employee.setBirthdate(employee.getBirthdate().plusDays(100));
		employee.setSalary(employee.getSalary() + 42.0);
		employee.setDepartment(new Department(0L, null));
		dao.saveOrUpdateEmployee(employee);

		Employee employeeFromDb = template.queryForObject(
				"select * from employee where id=:id",
				new MapSqlParameterSource("id", 10L),
				new HsqlDaoService.EmployeeRowMapper());

		assertEquals(employee.getFirstName(), employeeFromDb.getFirstName());
		assertEquals(employee.getLastName(), employeeFromDb.getLastName());
		assertEquals(employee.isActive(), employeeFromDb.isActive());
		assertEquals(employee.getBirthdate(), employeeFromDb.getBirthdate());
	}

	@Test
	public void testGetEmployee() {
		createTestEmployee();

		Employee employee = dao.getEmployee(-1L);
		assertEquals(-1L, (long) employee.getId());
		assertEquals("TestFirstName", employee.getFirstName());
		assertEquals("TestLastName", employee.getLastName());
		assertEquals(new LocalDate(1990, 10, 10), employee.getBirthdate());
		assertEquals(42.0, employee.getSalary(), 0.000001);
		assertEquals(0L, (long) employee.getDepartment().getId());
	}

	@Test
	public void testSearchEmployee() {
		createTestEmployee();

		assertTestEmployeeInList(dao.searchEmployees("T*"));
		assertTestEmployeeInList(dao.searchEmployees("????FirstName*"));
		assertTestEmployeeInList(dao.searchEmployees("*Name"));
		assertTestEmployeeInList(dao
				.searchEmployees("TestFirstNameTestLastName"));
		assertTestEmployeeInList(dao
				.searchEmployees("Test?irstN?meTestL?st?ame"));
	}

	private void assertTestEmployeeInList(List<Employee> employees) {
		// Another try to use hamcrest instead of plain old JUnit - have not
		// noticed huge readability increase
		assertThat(
				employees,
				contains(both(hasProperty("firstName", is("TestFirstName")))
						.and(hasProperty("lastName", is("TestLastName")))
						.and(hasProperty("birthdate", is(new LocalDate(1990,
								10, 10)))).and(hasProperty("salary", is(42.0)))
						.and(hasProperty("active", is(true)))
						.and(hasProperty("id", is(-1L)))));

	}

	private void createTestEmployee() {
		template.update(
				"insert into employee(id, first_name, last_name, birthdate, salary, active, department_id)"
						+ " values(:id,:firstName,:lastName,:birthdate,:salary,:active,:departmentId)",
				new MapSqlParameterSource()
						.addValue("id", -1L)
						.addValue("firstName", "TestFirstName")
						.addValue("lastName", "TestLastName")
						.addValue("birthdate",
								new LocalDate(1990, 10, 10).toDate())
						.addValue("salary", 42.0).addValue("active", "Y")
						.addValue("departmentId", 0L));

	}

}
