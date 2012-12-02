package com.sopovs.moradanen.haf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.haf.service.IDaoService;

@Controller
@RequestMapping("department/")
public class DepartmentController {

	@Autowired
	private IDaoService dao;

	@RequestMapping("list")
	public ModelAndView list() {
		return new ModelAndView("departmentList", "departments",
				dao.listDepartments());
	}

	@RequestMapping("view/{id}")
	public ModelAndView view(@PathVariable Long id) {
		return new ModelAndView("departmentView", "department",
				dao.getDepartment(id));
	}
}
