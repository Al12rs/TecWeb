<%@ page session="true"%>
<%@ page import="beans.LoginInfo"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>Start</title>
<link type="text/css" href="styles/default.css" rel="stylesheet"></link>
</head>
<body>
	<%
		Hashtable<String, Set<HttpSession>> groupSessionMap = (Hashtable<String, Set<HttpSession>>) this.getServletContext()
		.getAttribute("groupSessionMap");		
	%>
	
	<fieldset>
		<legend>Acquisti comitive</legend>
		<form method="post" action="../adminservlet">
			<label for="comitiva">Gruppo</label>
			<select name="comitiva">
				<%
					for(String group : groupSessionMap.keySet()){
						%>
						<option value="<%= group %>"><%= group %></option>
						<%
					}
				%>
			</select>
			<label for="azione">Azione</label>
			<select name="azione">
			<option value="completa">Completa</option>
			<option value="annulla">Annulla</option>
			</select>
			<input type="submit" value="Esegui" />
		</form>
	</fieldset>

</body>
</html>