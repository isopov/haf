<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#assign pageTitle><@spring.message "redirect.title" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<script lang="javascript">
		history.go(-2);
	</script>
	<h1><@spring.message "redirect.message" /></h1>
	
	
	
</@layout.layout>