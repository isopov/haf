<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#assign pageTitle><@spring.message "department.edit" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "department.edit" /></h1>

	<@spring.bind "department" />
	<@spring.showErrors "<br>"/> 
	<form method="POST">
	<@spring.bind "department.name" />
	<@spring.message "department.name" /> <input type="text" name="${spring.status.expression}" value="${spring.status.value?default("")}"> <@spring.showErrors "<br>" "department.name"/>
	<input type="submit">
	</form>
	
	
	
</@layout.layout>