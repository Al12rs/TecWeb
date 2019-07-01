<%@ page session="true"%>
<%@ page errorPage="errors/failure.jsp"%>
<%@ page import="esame.servlets.LoginServlet"%>
<%@ page import="esame.beans.LoginInfo"%>

<%
	// NB: non uso jsp:useBean perch� mi creerebbe l'oggetto "loginInfo"
	if (session.getAttribute(LoginInfo.LOGIN_INFO) != null) {
%>
<jsp:forward page="loginservlet" />
<%
	} else {
%>

<html>
<head>
<title>Login jsp</title>
<link rel="stylesheet" href="styles/mystyle.css" type="text/css"></link>
</head>
<body>
	<div id="container">
		<%@ include file="fragments/header.html"%>
		<div id="body">
			<%
				String message = null;
					if ((message = (String) request.getAttribute(LoginServlet.LOGIN_ERROR_MESSAGE)) != null)
						message = "Login error: " + message;
					else
						message = "Insert your credentialis:";
			%>
			<p><%=message%></p>
			<script src="scripts/forms.js" type="text/javascript"></script>
			<fieldset><legend>form to compile</legend>
			<form name="login" action="loginservlet"
				onSubmit="return validateLoginForm(this)" method="post">
				<label for="usermail">Email</label> <input type="email"
					name="usermail" placeholder="yourname@email.com" required>
				<br /> <label for="username">Username</label> <input type="text"
					name="username" placeholder="username" required> <br /> <label
					for="password">Password</label> <input type="password"
					name="password" placeholder="password" required> <br /> <input
					type="submit" value="Login">
			</form>
			</fieldset>
		</div>
		<%@ include file="fragments/footer.html"%>
	</div>
</body>
</html>

<%
	}
%>
