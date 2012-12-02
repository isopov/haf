package com.sopovs.moradanen.haf.service;

import java.util.List;

import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.domain.Employee;

public interface IDaoService {

	/**
	 * Since all the methods are really simple and use only one SQL query each,
	 * there is no possibility to roll-back anything on any exception. This
	 * method serves the only purpose of trying to insert 'TestForTransactional'
	 * department two times in one transaction. <br>
	 * <br>
	 * Expected:
	 * <ul>
	 * <li>'TestForTransactional' department is successfully created;</li>
	 * <li>second instance of 'TestForTransactional' department failed to be
	 * created due to duplicate name;</li>
	 * <li>rolling back the transaction insertion of the first instance of
	 * 'TestForTransactional' department is rolled back.</li>
	 * </ul>
	 */
	void testTransactional();

	Employee getEmployee(Long id);

	List<Employee> listEmployees();

	Department getDepartment(Long id);

	void saveOrUpdateDepartment(Department department);

	void saveOrUpdateEmployee(Employee employee);

	List<Department> listDepartments();

	List<Employee> searchEmployees(String criteria);

}
