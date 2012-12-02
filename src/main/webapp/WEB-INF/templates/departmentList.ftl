<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#assign pageTitle><@spring.message "department.list" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "department.list" /></h1>
	<table border="1">
		<tr>
			<th>ID</th>
			<th><@spring.message "department.name" /></th>
			<th><@spring.message "simple.view" /></th>
			
			<@security.authorize ifAnyGranted="EDITOR">
				<th><@spring.message "simple.edit" /></th>
			</@security.authorize>
		</tr>
		<#list departments as department>
			<tr>
				<td>${department.id?c}</td>
				<td>${department.name}</td>
				<td><a href="view/${department.id}"><@spring.message "simple.view" /></a></td>
				
				<@security.authorize ifAnyGranted="EDITOR">
					<td><a href="edit/${department.id}"><@spring.message "simple.edit" /></a></td>
				</@security.authorize>
			</tr>
		</#list>
	</table>
</@layout.layout>