<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />

<#import "../employee/employeeCommon.ftl" as epmloyee />

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#assign pageTitle><@spring.message "department.view" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "department.view" /></h1>
	<table>
		<tr>
			<th><@spring.message "department.id" /></th>
			<td>${department.id?c}</td>
		</tr>
		<tr>
			<th><@spring.message "department.name" /></th>
			<td>${department.name}</td>
		</tr>
	</table>
	<h2><@spring.message "employee.list" /></h2>
	<@epmloyee.employeeTable department.employees/>
</@layout.layout>