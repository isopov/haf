package com.sopovs.moradanen.haf.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;

public class Employee {

	private Long id;
	
	@NotEmpty
	@Length(max=30)
	private String firstName;
	@NotEmpty
	@Length(max=30)
	private String lastName;
	
	private Double salary;
	private LocalDate birthdate;
	private boolean active;

	private Department department;

	public Employee() {
	}

	public Employee(Long id, String firstName, String lastName, Double salary,
			LocalDate birthdate, boolean active, Department department) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.birthdate = birthdate;
		this.active = active;
		this.department = department;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", salary=" + salary
				+ ", birthdate=" + birthdate + ", active=" + active
				+ ", department=" + department + "]";
	}

}
