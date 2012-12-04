package com.sopovs.moradanen.haf.domain;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.joda.time.LocalDate;

public class Employee {

	private Long id;

	@Size(max = 30, min = 2)
	@Pattern(regexp = "^[A-Z0-9-]+$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "can only contain alphanumeric characters")
	private String firstName;
	@Size(max = 30, min = 2)
	@Pattern(regexp = "^[A-Z0-9-]+$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "can only contain alphanumeric characters")
	private String lastName;

	// JSR specification states that these annotations might not work on double,
	// and it depends on implementation
	@DecimalMin("100.0")
	@DecimalMax("100000.0")
	@NotNull
	private Double salary;

	@NotNull
	// joda-time is supported in this implementation of bean validator, while
	// specification promise support only for java.util.* classes
	@Past
	private LocalDate birthdate;
	private boolean active;

	@NotNull
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
