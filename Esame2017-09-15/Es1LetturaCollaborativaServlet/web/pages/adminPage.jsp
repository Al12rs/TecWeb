<%@ page session="true"%>
<%@ page import="beans.MessageList"%>
<%@ page import="beans.Message"%>
<%@ page import="beans.LoginInfo"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>Start</title>
<link type="text/css" href="styles/default.css" rel="stylesheet"></link>
</head>
<body>
	<%
		Set<HttpSession> sessionsSet = (Set<HttpSession>) application.getAttribute("sessionsSet");
		
		
	%>
	<fieldset>
		<legend>Sessioni utenti</legend>
		<form method="post" action="../adminservlet">
			<label for="userSession">Utente</label>
			<select name="userSession">
				<%
					for(HttpSession userSession : sessionsSet){
						LoginInfo user = (LoginInfo)userSession.getAttribute("loginInfo");
						%>
						<option value="<%= user.getUsername() %>" selected="selected"><%= user.getUsername() %></option>
						<%
					}
				%>
				<input type="submit" value="Azzera sessione" />
			</select>
		</form>
	</fieldset>

</body>
</html>