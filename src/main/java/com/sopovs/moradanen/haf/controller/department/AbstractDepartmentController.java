package com.sopovs.moradanen.haf.controller.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.service.IDaoService;

public abstract class AbstractDepartmentController {

	@Autowired
	private IDaoService dao;

	protected IDaoService getDao() {
		return dao;
	}

	protected ModelAndView saveOrUpdateDepartment(Department department,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("department/departmentForm", "department", department);
		}
		dao.saveOrUpdateDepartment(department);
		return new ModelAndView("redirect:/department/list");
	}

	@ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
	public String duplicateDepartment() {
		return "department/dupDepartment";
	}

}
