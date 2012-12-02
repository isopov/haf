package com.sopovs.moradanen.haf.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.service.IDaoService;

@Controller
@RequestMapping("department/")
public class DepartmentEditController {

	@Autowired
	private IDaoService dao;

	@ModelAttribute("department")
	public Department getDepartmentObject(@PathVariable Long id) {
		return dao.getDepartment(id);
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView getEditForm(@PathVariable Long id) {
		return new ModelAndView("departmentForm", new HashMap<String, String>());
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
	public String postEditForm(@PathVariable Long id,
			@Valid @ModelAttribute("department") Department department,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// TODO Error messages are not shown
			// bindingResult.reject(bindingResult.getFieldError().getCode(),
			// bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/department/edit/{id}";
		}
		department.setId(id);
		dao.saveOrUpdateDepartment(department);
		return "afterEdit";
	}

}
