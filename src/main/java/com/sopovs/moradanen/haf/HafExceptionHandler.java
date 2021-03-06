package com.sopovs.moradanen.haf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class HafExceptionHandler implements HandlerExceptionResolver {

	private final Logger logger = LoggerFactory.getLogger(HafExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ModelAndView("errors/500");
	}

}
