package com.sopovs.moradanen.haf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.haf.domain.Employee;
import com.sopovs.moradanen.haf.service.DaoService;

@Controller
@RequestMapping("employee/")
public class EmployeeController {

	@Autowired
	private DaoService dao;

	@RequestMapping("list")
	public ModelAndView list() {
		return new ModelAndView("employeeList", "employeeList",
				dao.listEmployees());
	}

	@RequestMapping("view/{id}")
	public ModelAndView view(@PathVariable long id) {
		return new ModelAndView("employeeView", "employee", dao.getEmployee(id));
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView getEditForm(@PathVariable long id) {
		return new ModelAndView();
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public ModelAndView postEditForm(@Valid Employee employee) {
		return new ModelAndView();
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String searchForm() {
		return "afterEdit";
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String performSearch(@RequestParam String searchString) {
		return "afterEdit";
	}

}
