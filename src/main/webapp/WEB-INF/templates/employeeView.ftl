<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#assign pageTitle><@spring.message "employee.view" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "employee.view" /></h1>
	<table>
		<tr>
			<th><@spring.message "employee.id" /></th>
			<td>${employee.id?c}</td>
		</tr>
		<tr>
			<th><@spring.message "employee.firstName" /></th>
			<td>${employee.firstName}</td>
		</tr>
		<tr>
			<th><@spring.message "employee.lastName" /></th>
			<td>${employee.lastName}</td>
		</tr>
		<tr>
			<th><@spring.message "employee.salary" /></th>
			<td>${employee.salary}</td>
		</tr>
		<tr>
			<th><@spring.message "employee.birthdate" /></th>
			<td>${employee.birthdate}</td>
		</tr>
		<tr>
			<th><@spring.message "employee.active" /></th>
			<td>${employee.active?string}</td>
		</tr>
		<tr>
			<th><@spring.message "department.id" /></th>
			<td>${employee.department.id}</td>
		</tr>
		<tr>
			<th><@spring.message "department.name" /></th>
			<td>${employee.department.name}</td>
		</tr>
	</table>
</@layout.layout>