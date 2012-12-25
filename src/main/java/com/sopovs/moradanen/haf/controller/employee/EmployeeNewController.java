package com.sopovs.moradanen.haf.controller.employee;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.haf.domain.Department;
import com.sopovs.moradanen.haf.domain.Employee;

@Controller
@RequestMapping("employee/")
public class EmployeeNewController extends AbstractEmployeeController {

	@ModelAttribute("employee")
	public Employee getDepartmentObject() {
		Employee newEmployee = new Employee();
		// default department
		newEmployee.setDepartment(new Department(0L, null));
		return newEmployee;
	}

	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String getEditForm() {
		return "employee/employeeForm";
	}

	@RequestMapping(value = "new", method = RequestMethod.POST)
	public ModelAndView postEditForm(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
		return saveOrUpdateEmployee(employee, bindingResult);

	}

}
