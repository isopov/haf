package com.sopovs.moradanen.haf.service;

import static junit.framework.Assert.assertEquals;
import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.domain.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" })
public class DaoServiceTest {

	@Autowired
	private IDaoService dao;

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Test
	public void testTransactional() {

		try {
			dao.testTransactional();
			Assert.fail();
		} catch (DuplicateKeyException de) {
			// expected
		}

		Assert.assertEquals(0, (template.query(
				"select * from department where name=:name",
				new MapSqlParameterSource("name", "TestForTransactional"),
				new DaoService.DepartmentRowMapper()).size()));

	}

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
				new DaoService.DepartmentRowMapper());

		Assert.assertEquals(testDepartmentname, depFromDb.getName());
	}

	@Test
	public void testGetDepartment() {
		String testDepartmentname = "Security";
		Long id = template.queryForObject(
				"select * from department where name=:name",
				new MapSqlParameterSource("name", testDepartmentname),
				new DaoService.DepartmentRowMapper()).getId();
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
				new DaoService.DepartmentRowMapper());
		marketingDepartment.setName("Marketing Testing Update");
		dao.saveOrUpdateDepartment(marketingDepartment);

		Department marketingDepartmentUpdated = template.queryForObject(
				"select * from department where id=:id",
				new MapSqlParameterSource("id", marketingDepartment.getId()),
				new DaoService.DepartmentRowMapper());

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
				new DaoService.EmployeeRowMapper());

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
				new DaoService.EmployeeRowMapper());

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
				new DaoService.EmployeeRowMapper());

		assertEquals(employee.getFirstName(), employeeFromDb.getFirstName());
		assertEquals(employee.getLastName(), employeeFromDb.getLastName());
		assertEquals(employee.isActive(), employeeFromDb.isActive());
		assertEquals(employee.getBirthdate(), employeeFromDb.getBirthdate());
	}

}
