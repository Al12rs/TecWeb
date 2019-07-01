<jsp:include page="loginservlet" />

<%-- uso della sessione --%>
<%@ page session="true"%>

<%-- info pagina --%>
<%@ page info="Pagina jsp"%>
<%@ page language="java" import="java.net.*"%>

<%-- import java --%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat, java.text.DateFormat"%>
<%@ page import="esame.beans.SavedFile"%>
<%@ page import="esame.beans.Bean1, esame.beans.Bean2"%>
<%@ page import="esame.beans.LoginInfo"%>

<%-- pagina per la gestione di errori --%>
<%@ page errorPage="errors/failure.jsp"%>

<%-- 
	/*oggetti built in*/
	//page --> info di una servlet
	//config --> getInitParamNames
	//request
	//response
	//out
	//SESSION --> getAttribute setAttribute
	//application (servletContext) --> var globali di application

	<jsp:forward page="url-dest">
	<jsp:param name="par1" value="val1"/>
	<jsp:param name="par2" value="val2"/>
	</jsp:forward>
	
	<jsp:include page="url-dest" flush="true">
	<jsp:param name="par1" value="val1"/>
	<jsp:param name="par2" value="val2"/>
	</jsp:include>
	
	<jsp:useBean id="nomeLogico" class="JavaBeans.aBean" scope="page|request|session|application"/>
	<jsp:getProperty property="nomeProp" name="nomeLogico"/>
	<jsp:setProperty property="nomeProp" name="nomeLogico" value="val"/>
	
 --%>

<jsp:useBean id="sessionBean" class="esame.beans.Bean1" scope="session" />
<jsp:useBean id="applicationBean" class="esame.beans.Bean2"
	scope="application" />


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Example jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/mystyle.css"
	type="text/css" />
</head>
<body>
	<div id="container">
		<%@ include file="fragments/header.html"%>

		<%
			// Computations!
		%>

		<div id="body">
		<p>Su questa pagina puoi fare tante cose..</p>
		<hr />
		<script src="scripts/forms.js" type="text/javascript"></script>
		<fieldset><legend>form to compile</legend>
		<form name="login" action="loginservlet"
			onSubmit="return validateLoginForm(this)" method="post">
			<label for="usermail">Email</label> <input type="email"
				name="usermail" placeholder="yourname@email.com" required> <br />
			<label for="username">Username</label> <input type="text"
				name="username" placeholder="username" required> <br /> <label
				for="password">Password</label> <input type="password"
				name="password" placeholder="password" required> <br /> <input
				type="submit" value="Login">
		</form>
		</fieldset>
		<hr />
		<a class="button" href="#">REFRESH</a>
		<hr />
		<ol id="pastHistory">
		<%
		DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
		List<SavedFile> list = fullHistory.getPrintsOf(((LoginInfo)session.getAttribute(LoginInfo.LOGIN_INFO)).getName());
			for (SavedFile s : list) {
		%>
		<li><%=s.getNomeFile() + " " + df.format(s.getTimestamp()) %></li>
		<%
			}
		%>
		</ol>
		</div>
		<%@ include file="fragments/footer.html"%>
	</div>
</body>
</html>
