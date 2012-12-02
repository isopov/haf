<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#assign pageTitle><@spring.message "employee.edit" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "employee.edit" /></h1>
	
	
	<@spring.bind "employee" />
	<@spring.showErrors "<br>"/> 
	<form method="POST">
		<@spring.bind "employee.firstName" />
			<@spring.message "employee.firstName" /> <input type="text" 
			name="${spring.status.expression}" value="${spring.status.value?default("")}">
			<@spring.showErrors "<br>" "employee.firstName"/>
		<br />
		
		<@spring.bind "employee.lastName" />
			<@spring.message "employee.lastName" /> <input type="text" 
			name="${spring.status.expression}" value="${spring.status.value?default("")}">
			<@spring.showErrors "<br>" "employee.lastName"/>
		<br />
		
		<#-- Working checkbox binding taken from http://justsomejavaguy.blogspot.com/2009/08/single-form-checkbox-macro-for.html -->
		<@spring.bind "employee.active" />
    		<@spring.message "employee.active" /> <input type="hidden" name="_${spring.status.expression}" value="false"/>
    		<input type="checkbox" id="${spring.status.expression}" name="${spring.status.expression}"
           		<#if spring.status.value?? && spring.status.value?string=="true">checked="true"</#if>
    			<@spring.closeTag/> 
		<br />
		
		<@spring.bind "employee.salary" />
			<@spring.message "employee.salary" /> <input type="text" 
			name="${spring.status.expression}" value="${spring.status.value?default("")}">
			<@spring.showErrors "<br>" "employee.salary"/>
		<br />

		<@spring.bind "employee.birthdate" />
			<@spring.message "employee.birthdate" /> <input type="text" 
			name="${spring.status.expression}" value="${spring.status.value?default("")}">
			<@spring.showErrors "<br>" "employee.birthdate"/>
		<br />
		
		<@spring.message "employee.department" /><@spring.formSingleSelect path="employee.department" options=departments/>
		<br />

		<input type="submit">
	</form>
	
</@layout.layout>