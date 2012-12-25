<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />
<#assign pageTitle><@spring.message "department.edit" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.10.0/jquery.validate.js" type="text/javascript"></script>
	<script type="text/javascript">
  		$(document).ready(function(){
  			jQuery.extend(jQuery.validator.messages, {
    			required: "<@spring.message "validation.required" />",
    			maxlength: jQuery.validator.format("<@spring.message "validation.maxlength" />"),
    			minlength: jQuery.validator.format("<@spring.message "validation.minlength" />"),
			});
  		
    		$("#departmentForm").validate();
  		});
  	</script>
	<h1><@spring.message "department.edit" /></h1>

	<form method="POST" id="departmentForm">
		<@spring.formInput "department.name" "class='required' minlength='2' maxlength='30'" />
		<@spring.showErrors "<br>"/> 
		<input type="submit">
	</form>
	
	
	
</@layout.layout>