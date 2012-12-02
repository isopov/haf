<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#import "employeeCommon.ftl" as epmloyee />

<#assign pageTitle><@spring.message "employee.search" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "employee.search" /></h1>
	<form method="POST">
		<input type="text" name="searchString" />
		<input type="submit" />
	</form>
	<#if employeeList??>
		<@epmloyee.employeeTable employeeList/>
	</#if>
</@layout.layout>