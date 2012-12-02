<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />

<#assign pageTitle><@spring.message "index.title" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
<h1><@spring.message "welcome.message" /></h1>
        	<ul>
        		<li><a href="employee/list"><@spring.message "employee.list" /></a></li>
        		<li><a href="department/list"><@spring.message "department.list" /></a></li>
        		
        		<!-- TODO The following links should be accessible only to editors-->
        		<li><a href="employee/create"><@spring.message "employee.create" /></a></li>
        		<li><a href="department/create"><@spring.message "department.create" /></a></li>
        	</ul>
</@layout.layout>
