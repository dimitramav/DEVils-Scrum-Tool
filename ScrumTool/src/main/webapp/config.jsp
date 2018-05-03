<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="ys09.conf.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>JSP</title>
    </head>

    <body>

        <h2>
        	Configuration
    	</h2>
    	<div>
    	    <table border="1">
    		<%
    		Configuration conf = Configuration.getInstance();
    		for(String key : Configuration.CONFIG_KEYS) {
    		%>
    			<tr>
    				<td><%= key %></td>
    				<td><%= conf.getProperty(key) %></td>
				</tr>
    		<% } %>
    		</table>
    	</div>

    </body>
</html>
