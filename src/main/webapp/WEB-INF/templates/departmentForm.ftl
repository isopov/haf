<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#assign pageTitle><@spring.message "department.edit" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "department.edit" /></h1>

	<form method="POST">
		<@spring.formInput "department.name"/>
		<@spring.showErrors "<br>"/> 
	<input type="submit">
	</form>
	
	
	
</@layout.layout>