package com.sopovs.moradanen.haf.controller.department;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.haf.domain.Department;

@Controller
@RequestMapping("department/")
public class DepartmentNewController extends AbstractDepartmentController {

	@ModelAttribute("department")
	public Department getDepartmentObject() {
		return new Department(null, "New Department");
	}

	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String newDepartment() {
		return "department/departmentForm";
	}

	@RequestMapping(value = "new", method = RequestMethod.POST)
	public ModelAndView newDepartmentForm(@ModelAttribute("department") @Valid Department department,
			BindingResult bindingResult) {
		return saveOrUpdateDepartment(department, bindingResult);
	}
}
