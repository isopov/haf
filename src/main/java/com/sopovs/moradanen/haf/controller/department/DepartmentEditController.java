package com.sopovs.moradanen.haf.controller.department;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sopovs.moradanen.haf.domain.Department;

@Controller
@RequestMapping("department/")
public class DepartmentEditController extends AbstractDepartmentController {

	@ModelAttribute("department")
	public Department getDepartmentObject(@PathVariable Long id) {
		return getDao().getDepartment(id);
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView getEditForm(@PathVariable Long id) {
		return new ModelAndView("department/departmentForm", new HashMap<String, String>());
	}

	@RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
	public ModelAndView postEditForm(@PathVariable Long id, @Valid @ModelAttribute("department") Department department,
			BindingResult bindingResult) {
		return saveOrUpdateDepartment(department, bindingResult);
	}

}
