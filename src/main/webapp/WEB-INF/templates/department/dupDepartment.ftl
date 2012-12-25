<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />


<#assign pageTitle><@spring.message "department.duplicate" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "department.duplicate" /></h1>
</@layout.layout>
