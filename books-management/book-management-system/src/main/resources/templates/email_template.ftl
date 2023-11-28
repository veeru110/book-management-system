<!DOCTYPE html>
<html>
<head>
<title>${greeting}</title>
</head>
<body>
	<h3>Dear ${name},</h3>
	<p>${body}</p>
	<#if tableExists?? && tableExists == true>
	<table>
	    <thead>
	        <th>${column1}</th>
	        <th>${column2}</th>
	    </thead>
	    <tbody>
	        <#list dataItr as data>
	            <tr>
	                <td>${data.column1}</td>
	                <td>${data.column2}</td>
	            </tr>
	        </#list>
	    </tbody>
	</table>
	</#if>
<p>Thanks & Regards</p>
<p>BookStore Team</p>
</body>
</html>