<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#assign pageTitle><@spring.message "department.list" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "department.list" /></h1>
	<table border="1">
		<tr>
			<th>ID</th>
			<th><@spring.message "department.name" /></th>
			<th><@spring.message "simple.view" /></th>
			<th><@spring.message "simple.edit" /></th>
		</tr>
		<#list departments as department>
			<tr>
				<td>${department.id}</td>
				<td>${department.name}</td>
				<td><a href="view/${department.id}"><@spring.message "simple.view" /></a></td>
				<#-- TODO only for secured-->
				<td><a href="edit/${department.id}"><@spring.message "simple.edit" /></a></td>
			</tr>
		</#list>
	</table>
</@layout.layout>