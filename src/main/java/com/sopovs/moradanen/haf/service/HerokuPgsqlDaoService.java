package com.sopovs.moradanen.haf.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HerokuPgsqlDaoService extends PgsqlDaoService {

	@Override
	public void afterPropertiesSet() throws Exception {
		// no code
	}

}
