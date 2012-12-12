package com.sopovs.moradanen.haf.service;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HerokuPgsqlDaoService extends PgsqlDaoService {
	private final Logger logger = LoggerFactory
			.getLogger(HerokuPgsqlDaoService.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		int tablesCreated = getTemplate()
				.update("create table IF NOT EXIST department(id serial primary key, name varchar(30))",
						Collections.<String, String> emptyMap());
		
		if(tablesCreated != 0){
			super.afterPropertiesSet();
		}else{
			logger.info("Schema already exists");
		}
	}
	
}
