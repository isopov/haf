<#import "spring.ftl" as spring />

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#macro layout pageTitle>
<!DOCTYPE html>
<html>
    <head>
        <title>${pageTitle}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://ajax.aspnetcdn.com/ajax/jquery/jquery-1.8.3.js" type="text/javascript"></script>
    </head>
    <body>
    	<#--Maybe it should be done ith divs, but I'm not so good at making-up html-->
    	<table>
    		<tr>
    			<td><a href="<@spring.url "/employee/search"/>"><@spring.message "employee.search" /></a></td>
    			<td><a href="<@spring.url "/employee/list"/>"><@spring.message "employee.list" /></a></td>
    			<td>
    				<@security.authorize ifAnyGranted="EDITOR">
        				<a href="<@spring.url "/employee/new"/>"><@spring.message "employee.new" /></a>
        			</@security.authorize>
    			</td>
    			<td><a href="<@spring.url "/department/list"/>"><@spring.message "department.list" /></a></td>
    			<td>
    				<@security.authorize ifAnyGranted="EDITOR">
        				<a href="<@spring.url "/department/new"/>"><@spring.message "department.new" /></a>
        			</@security.authorize>
    			</td>
    			<td>
        			<a href="<@spring.url "/?lang=en"/>">English version</a>
    			</td>
    			<td>
        			<a href="<@spring.url "/?lang=ru"/>">Русская версия</a>
    			</td>
    			<td>
    				<@security.authorize ifAnyGranted="EDITOR">
        				<a href="<@spring.url "/department/new"/>"><@spring.message "department.new" /></a>
        			</@security.authorize>
    			</td>
    			<td>
        			<a href="<@spring.url "/j_spring_security_logout"/>"><@spring.message "simple.logout" /></a>
    			</td>
    		</tr>
    	</table>
    	
   		<#nested/>
    </body>
</html>
</#macro>