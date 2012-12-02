package com.sopovs.moradanen.haf.controller;

import java.beans.PropertyEditorSupport;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.domain.Employee;
import com.sopovs.moradanen.haf.service.IDaoService;

@Controller
@RequestMapping("employee/")
public class EmployeeEditController {

	@Autowired
	private IDaoService dao;

	@ModelAttribute("employee")
	public Employee getDepartmentObject(@PathVariable Long id) {
		return dao.getEmployee(id);
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView getEditForm(@PathVariable long id) {

		Map<String, String> departments = Maps.newHashMap();
		for (Department department : dao.listDepartments()) {
			departments.put(String.valueOf(department.getId()),
					department.getName());
		}

		return new ModelAndView("employeeForm", "departments", departments);
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
	public String postEditForm(@PathVariable long id,
			@Valid @ModelAttribute("employee") Employee employee,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// TODO Error messages are not shown
			return "redirect:/employee/edit/{id}";
		}
		dao.saveOrUpdateEmployee(employee);
		return "afterEdit";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Department.class, "department",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						setValue(new Department(Long.valueOf(text), null));
					}

					@Override
					public String getAsText() {
						Department dep = (Department) getValue();
						return String.valueOf(dep.getId());
					}

				});
	}

}