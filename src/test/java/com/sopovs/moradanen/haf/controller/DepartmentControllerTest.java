package com.sopovs.moradanen.haf.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sopovs.moradanen.haf.domain.Department;

public class DepartmentControllerTest extends AbstractControllerTest {

	@Test
	public void testList() throws Exception {
		this.mockMvc.perform(get("/department/list"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("List of departments")));
	}

	@Test
	public void testView() throws Exception {
		this.mockMvc.perform(get("/department/view/0"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(stringContainsInOrder(Arrays.asList("Department ID",
						"Department name",
						"List of employees"))));
	}

	@Test
	public void testNewDepartment() throws Exception {
		List<Department> deps = dao.listDepartments();
		Assert.assertEquals(8, deps.size());
		this.mockMvc
				.perform(post("/department/new").param("name", "TestDepartmentName"))
				.andExpect(status().isMovedTemporarily())
				.andExpect(header().string("Location", "/department/list"));

		deps = dao.listDepartments();
		Assert.assertEquals(9, deps.size());
	}

	@Test
	public void testNewDepartmentDuplicate() throws Exception {
		testNewDepartment();
		this.mockMvc
				.perform(post("/department/new").param("name", "TestDepartmentName"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("Department with such name already exists")));
	}

	@Test
	public void testNewDepartmentWithTooBigName() throws Exception {
		this.mockMvc.perform(post("/department/new").param("name",
				"TestDepartmentNameThatIsTooLongTestDepartmentNameThatIsTooLong"
						+ "TestDepartmentNameThatIsTooLongTestDepartmentNameThatIsTooLong"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("size must be between 0 and 30")));
	}

	@Test
	public void testEditDepartment() throws Exception {
		Department dep = dao.getDepartment(1L);
		Assert.assertNotEquals("NewTestDepartmentName", dep.getName());
		this.mockMvc.perform(post("/department/edit/" + dep.getId()).param("name",
				"NewTestDepartmentName"))
				.andExpect(status().isMovedTemporarily())
				.andExpect(header().string("Location", "/department/list"));
		dep = dao.getDepartment(1L);
		Assert.assertEquals("NewTestDepartmentName", dep.getName());
	}

}