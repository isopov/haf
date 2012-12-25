package com.sopovs.moradanen.haf.controller.employee;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.haf.domain.Employee;

@Controller
@RequestMapping("employee/")
public class EmployeeEditController extends AbstractEmployeeController {

	@ModelAttribute("employee")
	public Employee getEmployeeObject(@PathVariable Long id) {
		return getDao().getEmployee(id);
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String getEditForm(@PathVariable long id) {
		return "employee/employeeForm";
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
	public ModelAndView postEditForm(@PathVariable long id, @Valid @ModelAttribute("employee") Employee employee,
			BindingResult bindingResult) {
		return saveOrUpdateEmployee(employee, bindingResult);

	}

}
