<#import "spring.ftl" as spring />

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#macro employeeTable employees>
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
		<#list employees as employee>
			<tr>
				<td>${employee.id?c}</td>
				<td>${employee.firstName}</td>
				<td>${employee.lastName}</td>
				<td>${employee.salary}</td>
				<td>${employee.birthdate}</td>
				<td>${employee.active?string}</td>
				
				<td><a href="view/${employee.id}"><@spring.message "simple.view" /></a></td>
				<@security.authorize ifAnyGranted="EDITOR">
					<td><a href="edit/${employee.id}"><@spring.message "simple.edit" /></a></td>
				</@security.authorize>
			</tr>
		</#list>
	</table>
</#macro>
