<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />

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
	<table border="1">
		<tr>
			<th>ID</th>
			<th><@spring.message "employee.firstName" /></th>
			<th><@spring.message "employee.lastName" /></th>
			<th><@spring.message "employee.salary" /></th>
			<th><@spring.message "employee.birthdate" /></th>
			<th><@spring.message "employee.active" /></th>
			<th><@spring.message "simple.view" /></th>
			<@security.authorize ifAnyGranted="EDITOR">
				<th><@spring.message "simple.edit" /></th>
			</@security.authorize>
		</tr>
		<#list department.employees as employee>
			<tr>
				<td>${employee.id?c}</td>
				<td>${employee.firstName}</td>
				<td>${employee.lastName}</td>
				<td>${employee.salary}</td>
				<td>${employee.birthdate}</td>
				<td>${employee.active?string}</td>
				
				<td><a href="<@spring.url "/employee/view/${employee.id?c}"/>"><@spring.message "simple.view" /></a></td>
				<@security.authorize ifAnyGranted="EDITOR">
					<td><a href="<@spring.url "/employee/edit/${employee.id?c}"/>"><@spring.message "simple.edit" /></a></td>
				</@security.authorize>
			</tr>
		</#list>
	</table>
</@layout.layout>