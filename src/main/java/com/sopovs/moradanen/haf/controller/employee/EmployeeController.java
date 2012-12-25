package com.sopovs.moradanen.haf.controller.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.sopovs.moradanen.haf.domain.Employee;
import com.sopovs.moradanen.haf.service.IDaoService;

@Controller
@RequestMapping("employee/")
public class EmployeeController {

	@Autowired
	private IDaoService dao;

	@RequestMapping("list")
	public ModelAndView list() {
		return new ModelAndView("employee/employeeList", "employeeList", dao.listEmployees());
	}

	@RequestMapping("list/excel")
	public View listExcell() {
		return new AbstractExcelView() {
			@Override
			protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
					HttpServletRequest request, HttpServletResponse response) throws Exception {
				response.addHeader("Content-Disposition", "attachment; filename=\"employee-list.xls\"");

				HSSFSheet sheet = workbook.createSheet("List of employees");

				setText(getCell(sheet, 0, 0), "Id");
				setText(getCell(sheet, 0, 1), "First name");
				setText(getCell(sheet, 0, 2), "Last name");
				setText(getCell(sheet, 0, 3), "Active");
				setText(getCell(sheet, 0, 4), "Salary");

				List<Employee> employees = dao.listEmployees();
				for (int i = 0; i < employees.size(); i++) {
					Employee employee = employees.get(i);
					setText(getCell(sheet, i + 1, 0), String.valueOf(employee.getId()));
					setText(getCell(sheet, i + 1, 1), employee.getFirstName());
					setText(getCell(sheet, i + 1, 2), employee.getLastName());
					setText(getCell(sheet, i + 1, 3), String.valueOf(employee.isActive()));
					setText(getCell(sheet, i + 1, 4), String.valueOf(employee.getSalary()));
				}
			}
		};
	}

	@RequestMapping("view/{id}")
	public ModelAndView view(@PathVariable long id) {
		return new ModelAndView("employee/employeeView", "employee", dao.getEmployee(id));
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String searchForm() {
		return "employee/employeeSearch";
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView performSearch(@RequestParam String searchString) {
		// TODO accept only alphabetic characters plus '?' and '*'
		return new ModelAndView("employee/employeeSearch", "employeeList", dao.searchEmployees(searchString));
	}

}
