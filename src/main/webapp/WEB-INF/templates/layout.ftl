<#import "spring.ftl" as spring />

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#macro head pageTitle>
	<head>
    	<meta charset="utf-8">
        <title>${pageTitle}</title>
        
        <link href="<@spring.url "/css/bootstrap.css"/>" rel="stylesheet">
    	<style>
      		body {
        		padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      		}
    	</style>
    	
    	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    	<!--[if lt IE 9]>
      		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    	<![endif]-->

        <script src="<@spring.url "/js/jquery.js"/>" type="text/javascript"></script>
		<script src="<@spring.url "/js/bootstrap.js"/>" type="text/javascript"></script>
    </head>
</#macro>

<#macro layout pageTitle>
<!DOCTYPE html>
<html>
    <@head pageTitle/>
    <body>
    
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <#-- TODO what is this btn-navbar for? -->
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="<@spring.url "/"/>"><@spring.message "index.title" /></a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@spring.message "simple.employees" /> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                	<li class="active"><a href="<@spring.url "/employee/search"/>"><@spring.message "employee.search" /></a></li>
              		<li><a href="<@spring.url "/employee/list"/>"><@spring.message "employee.list" /></a></li>
              		<@security.authorize ifAnyGranted="EDITOR">
              			<li><a href="<@spring.url "/employee/new"/>"><@spring.message "employee.new" /></a></li>
             	 	</@security.authorize>
                </ul>
              </li>
              
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@spring.message "simple.departments" /> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                	<li><a href="<@spring.url "/department/list"/>"><@spring.message "department.list" /></a></li>
					<@security.authorize ifAnyGranted="EDITOR">
              			<li><a href="<@spring.url "/department/new"/>"><@spring.message "department.new" /></a></li>
              		</@security.authorize>
                </ul>
              </li>
              
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@spring.message "simple.language" /> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                	<li><a href="<@spring.url "/?lang=en"/>">English version</a></li>
              		<li><a href="<@spring.url "/?lang=ru"/>">Русская версия</a></li>
                </ul>
              </li>

              <li><a href="<@spring.url "/j_spring_security_logout"/>"><@spring.message "simple.logout" /></a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
    
    <div class="container">
   		<#nested/>
   	</div>
    </body>
</html>
</#macro>