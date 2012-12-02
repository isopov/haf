package com.sopovs.moradanen.haf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.haf.service.IDaoService;

@Controller
@RequestMapping("employee/")
public class EmployeeController {

	@Autowired
	private IDaoService dao;

	@RequestMapping("list")
	public ModelAndView list() {
		return new ModelAndView("employeeList", "employeeList",
				dao.listEmployees());
	}

	@RequestMapping("view/{id}")
	public ModelAndView view(@PathVariable long id) {
		return new ModelAndView("employeeView", "employee", dao.getEmployee(id));
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String searchForm() {
		return "employeeSearch";
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView performSearch(@RequestParam String searchString) {
		// TODO accept only alphabetic characters plus '?' and '*'
		return new ModelAndView("employeeSearch", "employeeList",
				dao.searchEmployees(searchString));
	}

}
