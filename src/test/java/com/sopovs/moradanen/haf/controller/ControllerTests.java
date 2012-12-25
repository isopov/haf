package com.sopovs.moradanen.haf.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext.xml",
		"file:src/main/webapp/WEB-INF/spring-servlet.xml" })
@ActiveProfiles("default")
public class ControllerTests {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Test
	public void testNotFound() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		this.mockMvc.perform(get("/foo")).andExpect(status().isNotFound());
	}

	@Test
	public void testList() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		this.mockMvc.perform(get("/employee/list"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("First Name")));
	}

}