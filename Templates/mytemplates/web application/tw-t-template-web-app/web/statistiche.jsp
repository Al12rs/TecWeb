<jsp:include page="loginservlet" />

<%@ page session="true"%>
<%@ page info="Statistiche jsp"%>
<%@ page language="java" import="java.net.*"%>
<%@ page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page import="esame.beans.SavedFile, esame.beans.FullHistory"%>
<%@ page import="esame.beans.LoginInfo"%>
<%@ page errorPage="errors/failure.jsp"%>

<jsp:useBean id="fullHistory" class="esame.beans.FullHistory"
	scope="application" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stats jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/mystyle.css" type="text/css" />
</head>
<body>
	<div id="container">
		<%@ include file="fragments/header.html"%>

		<%
			// Computations!
		%>

		<div id="body">

			<%
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(LoginInfo.LOGIN_INFO);
				if (loginInfo != null && loginInfo.getName().equals("admin")) {
			%>
			<ol id="pastHistory">
				<%
					DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
						for (SavedFile s : fullHistory.getRecentPrints()) {
				%>
				<li><%=s.getNomeFile() + " " + df.format(s.getTimestamp()) + (s.isSaved() ? " OK" : " ERROR")%></li>
				<%
					}
				%>
			</ol>
			<%
				} else {
			%>
			<p>UNAUTHORIZED</p>
			<%
				}
			%>
		</div>
		<%@ include file="fragments/footer.html"%>
	</div>
</body>
</html>
