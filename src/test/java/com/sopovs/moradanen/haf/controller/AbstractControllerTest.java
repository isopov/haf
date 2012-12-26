package com.sopovs.moradanen.haf.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.sopovs.moradanen.haf.service.IDaoService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext.xml",
		"file:src/main/webapp/WEB-INF/spring-servlet.xml" })
@ActiveProfiles("default")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public abstract class AbstractControllerTest {

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected IDaoService dao;

	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

}