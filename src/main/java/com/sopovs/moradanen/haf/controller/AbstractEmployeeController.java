package com.sopovs.moradanen.haf.controller;

import java.beans.PropertyEditorSupport;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.domain.Employee;
import com.sopovs.moradanen.haf.service.IDaoService;

public abstract class AbstractEmployeeController {

	@Autowired
	private IDaoService dao;

	protected IDaoService getDao() {
		return dao;
	}

	@ModelAttribute("departments")
	public Map<String, String> getDepartments() {
		Map<String, String> departments = Maps.newHashMap();
		for (Department department : getDao().listDepartments()) {
			departments.put(String.valueOf(department.getId()),
					department.getName());
		}
		return departments;
	}

	protected ModelAndView saveOrUpdateEmployee(Employee employee,
			BindingResult bindingResult) {
		if (employee.getBirthdate() != null
				&& employee.getBirthdate().isBefore(new LocalDate(1900, 1, 1))) {
			bindingResult.addError(new FieldError("employee", "birthdate",
					employee.getBirthdate(), false, null, null,
					"must be more than in 1900"));
		}

		if (bindingResult.hasErrors()) {
			return new ModelAndView("employeeForm", "employee", employee);
		}
		dao.saveOrUpdateEmployee(employee);
		return new ModelAndView("redirect:/employee/list");
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class,
				new PropertyEditorSupport() {
					final DateTimeFormatter formatter = DateTimeFormat
							.forPattern("dd/MM/YY");

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						setValue(formatter.parseLocalDate(text));
					}

					@Override
					public String getAsText() {
						if (getValue() == null) {
							return null;
						}
						return formatter.print((LocalDate) getValue());

					}
				});
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
