<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />
<#assign pageTitle><@spring.message "employee.edit" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.10.0/jquery.validate.js" type="text/javascript"></script>
	<script type="text/javascript">
	
  		$(document).ready(function(){
  			jQuery.validator.addMethod("ruDate", function(ruDate, element) {
    			ruDate = ruDate.replace(/\s+/g, ""); 
				return this.optional(element) || ruDate.length > 7 &&
					ruDate.match(/^[0-3][0-9]\/[0-1][0-9]\/[4-9][0-9]$/);
			}, "Please specify a valid date in the format DD/MM/YY in the past and after 1940");
  		
    		$("#employeeForm").validate();
  		});
  	</script>
	<h1><@spring.message "employee.edit" /></h1>
	
	
	<form method="POST" id="employeeForm">

		<@spring.message "employee.firstName" />
		<@spring.formInput "employee.firstName" "class='required' minlength='2' maxlength='30'" />
		<@spring.showErrors "<br>"/> 
		<br />
		
		<@spring.message "employee.lastName" />
		<@spring.formInput "employee.lastName" "class='required' minlength='2' maxlength='30'" />
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
		<@spring.formInput "employee.salary" "class='required number' min='100' max='100000'" />
		<@spring.showErrors "<br>"/> 
		<br />
		
		<@spring.message "employee.birthdate" />
		<@spring.formInput "employee.birthdate" "class='required ruDate'" />
		<@spring.showErrors "<br>"/> 
		<br />

		
		<@spring.message "employee.department" /><@spring.formSingleSelect path="employee.department" options=departments/>
		<br />

		<input type="submit">
	</form>
	
</@layout.layout>