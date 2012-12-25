package com.sopovs.moradanen.haf.controller.department;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
		return "department/departmentForm";
	}

	@RequestMapping(value = "new", method = RequestMethod.POST)
	public String newDepartmentForm(@ModelAttribute("department") @Valid Department department,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println("foo");
		}
		dao.saveOrUpdateDepartment(department);
		return "redirect:/department/list";
	}

	@ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
	public String duplicateDepartment() {
		return "department/dupDepartment";
	}
}
