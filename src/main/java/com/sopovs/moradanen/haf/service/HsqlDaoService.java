package com.sopovs.moradanen.haf.service;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HsqlDaoService extends AbstractDaoService {

	private final Logger logger = LoggerFactory			.getLogger(AbstractDaoService.class);

	@Override
	protected void dropIfExistsAndCreateSchema() {
		getTemplate().update("drop table employee if exists",
				Collections.<String, String> emptyMap());
		getTemplate().update("drop table department if exists",
				Collections.<String, String> emptyMap());

		getTemplate()
				.update("create table department(id int identity primary key, name varchar(30))",
						Collections.<String, String> emptyMap());
		getTemplate().update("alter table department add unique (name)",
				Collections.<String, String> emptyMap());

		getTemplate()
				.update("create table employee(id int identity primary key,"
						+ " first_name varchar(30), last_name varchar(30),"
						+ " salary double, birthdate date, active char(1), department_id int)",
						Collections.<String, String> emptyMap());

		// TODO There were some problems creating named foreign key constraint
		getTemplate().update(
				"alter table employee add foreign key (department_id)"
						+ " references department(id)",
				Collections.<String, String> emptyMap());

		logger.info("schema created");

	}

}
