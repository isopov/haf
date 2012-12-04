package com.sopovs.moradanen.haf.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" })
public class DaoServiceTransactionalTest {

	@Autowired
	private IDaoService dao;

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * This test is in the separate file to test transaction of the DaoService,
	 * and thus this transaction should not be placed inside other transaction
	 * (transaction for test)
	 */
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
				new HsqlDaoService.DepartmentRowMapper()).size()));

	}

}
