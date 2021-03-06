package com.sopovs.moradanen.haf.controller;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import com.sopovs.moradanen.haf.domain.Employee;

public class EmployeeControllerTest extends AbstractControllerTest {

	@Test
	public void testList() throws Exception {
		this.mockMvc.perform(get("/employee/list"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("First Name")));
	}

	@Test
	public void testListExcel() throws Exception {
		this.mockMvc.perform(get("/employee/list/excel"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/vnd.ms-excel"))
				.andExpect(header().string("Content-Disposition", "attachment; filename=\"employee-list.xls\""));
	}

	@Test
	public void testView() throws Exception {
		this.mockMvc.perform(get("/employee/view/0"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("First Name")));
	}

	@Test
	public void testSearchForm() throws Exception {
		this.mockMvc
				.perform(get("/employee/search"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(
						content().string(
								stringContainsInOrder(Arrays.asList("<h1>Search for employees</h1>",
										"<form method=\"POST\">",
										"<input type=\"text\" name=\"searchString\" />",
										"<input type=\"submit\" />",
										"</form>"))));
	}

	@Test
	public void testSearchNothing() throws Exception {
		this.mockMvc
				.perform(post("/employee/search").param("searchString", "Should not find anyone"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(
						content().string(
								stringContainsInOrder(Arrays.asList("<h1>Search for employees</h1>",
										"<form method=\"POST\">",
										"<input type=\"text\" name=\"searchString\" />",
										"<input type=\"submit\" />",
										"</form>"))))
				.andExpect(content().string(not(containsString("<a href=\"/employee/view/"))));
	}

	@Test
	public void testSearchEverybody() throws Exception {
		this.mockMvc
				.perform(post("/employee/search").param("searchString", "*"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(
						content().string(
								stringContainsInOrder(Arrays.asList("<h1>Search for employees</h1>",
										"<form method=\"POST\">",
										"<input type=\"text\" name=\"searchString\" />",
										"<input type=\"submit\" />",
										"</form>"))))
				.andExpect(content().string(containsString("<a href=\"/employee/view/")));
	}

	@Test
	public void testCreateEmployee() throws Exception {
		List<Employee> created = dao.searchEmployees("TestFirstName*");
		Assert.assertEquals(0, created.size());
		this.mockMvc
				.perform(post("/employee/new")
						.param("firstName", "TestFirstName")
						.param("lastName", "TestLastName")
						.param("salary", "4000")
						.param("active", "true")
						.param("birthdate", "20/10/1987")
						.param("department", "1"))
				.andExpect(status().isMovedTemporarily())
				.andExpect(header().string("Location", "/employee/list"));

		created = dao.searchEmployees("TestFirstName*");
		assertEquals(1, created.size());
		Employee employee = created.get(0);
		assertEquals(new LocalDate(1987, 10, 20), employee.getBirthdate());
		assertEquals("TestFirstName", employee.getFirstName());
		assertEquals("TestLastName", employee.getLastName());
		assertEquals(4000d, employee.getSalary(), 0.0001d);
	}

}