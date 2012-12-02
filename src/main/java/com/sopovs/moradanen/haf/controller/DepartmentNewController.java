package com.sopovs.moradanen.haf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.service.IDaoService;

@Controller
@RequestMapping("department/")
public class DepartmentNewController {

	@Autowired
	private IDaoService dao;

	@ModelAttribute("department")
	public Department getDepartmentObject() {
		return new Department(null, "New Department");
	}

	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String newDepartment() {
		return "departmentForm";
	}

	@RequestMapping(value = "new", method = RequestMethod.POST)
	public String newDepartmentForm(
			@ModelAttribute("department") @Valid Department department) {
		dao.saveOrUpdateDepartment(department);
		return "afterEdit";
	}
}
