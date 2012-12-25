<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />
<#import "employeeCommon.ftl" as epmloyee />

<#assign pageTitle><@spring.message "employee.list" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "employee.list" /></h1><a href="<@spring.url "/employee/list/excel"/>">Download in Excel</a>
	<@epmloyee.employeeTable employeeList/>
</@layout.layout>