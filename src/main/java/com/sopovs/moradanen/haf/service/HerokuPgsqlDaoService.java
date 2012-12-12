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
		//no code
	}
	
}
