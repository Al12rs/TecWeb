<jsp:include page="loginservlet" />

<%@ page session="true"%>
<%@ page info="Pagina jsp"%>
<%@ page language="java" import="java.net.*"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<%@ page import="esame.beans.LoginInfo"%>
<%@ page errorPage="../errors/failure.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Wait for message jsp</title>
<link rel="stylesheet" href="styles/mystyle.css" type="text/css"></link>
<script src="scripts/utils.js" type="text/javascript"></script>
<script src="scripts/json.js" type="text/javascript"></script>
<script src="scripts/ajax.js" type="text/javascript"></script>
<script src="scripts/waitforamessage.js" type="text/javascript"></script>
</head>
<body onload="receiveMessage('waitingservlet')">
	<div id="container">
		<%@ include file="fragments/header.html"%>
		<div id="body">
			<%
				// GESTIONE RICHIESTA
			%>

			<div id="message">Aspetta un messaggio...</div>
		</div>
		<%@ include file="fragments/footer.html"%>
	</div>
</body>
</html>