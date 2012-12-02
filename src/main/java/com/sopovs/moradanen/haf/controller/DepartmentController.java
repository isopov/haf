package com.sopovs.moradanen.haf.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.service.DaoService;

@Controller
@RequestMapping("department/")
public class DepartmentController {

	@Autowired
	private DaoService dao;

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

	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView getEditForm(@PathVariable Long id) {
		return new ModelAndView("departmentView", new HashMap<String, String>());
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String postEditForm(@Valid Department department) {
		return "afterEdit";
	}

}
