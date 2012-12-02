package com.sopovs.moradanen.haf.domain;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

//Specification does not contain any details about departments, so it is as simple as possible without any hierarchy or details
public class Department {
	private Long id;

	@NotEmpty
	@Size(max = 30)
	private String name;
	private List<Employee> employees;

	public Department() {

	}

	public Department(Long id, String name) {

		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		// Note do not add employees here to avoid cycle
		return "Department [id=" + id + ", name=" + name + "]";
	}

}
