<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#assign pageTitle><@spring.message "employee.edit" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "employee.edit" /></h1>
	
	
	<form method="POST">

		<@spring.message "employee.firstName" />
		<@spring.formInput "employee.firstName"/>
		<@spring.showErrors "<br>"/> 
		<br />
		
		<@spring.message "employee.lastName" />
		<@spring.formInput "employee.lastName"/>
		<@spring.showErrors "<br>"/> 
		<br />
		

		<#-- Working checkbox binding taken from http://justsomejavaguy.blogspot.com/2009/08/single-form-checkbox-macro-for.html -->
		<@spring.bind "employee.active" />
    		<@spring.message "employee.active" /> <input type="hidden" name="_${spring.status.expression}" value="false"/>
    		<input type="checkbox" id="${spring.status.expression}" name="${spring.status.expression}"
           		<#if spring.status.value?? && spring.status.value?string=="true">checked="true"</#if>
    			<@spring.closeTag/> 
		<br />
		
		
		<@spring.message "employee.salary" />
		<@spring.formInput "employee.salary"/>
		<@spring.showErrors "<br>"/> 
		<br />
		
		<@spring.message "employee.birthdate" />
		<@spring.formInput "employee.birthdate"/>
		<@spring.showErrors "<br>"/> 
		<br />

		
		<@spring.message "employee.department" /><@spring.formSingleSelect path="employee.department" options=departments/>
		<br />

		<input type="submit">
	</form>
	
</@layout.layout>