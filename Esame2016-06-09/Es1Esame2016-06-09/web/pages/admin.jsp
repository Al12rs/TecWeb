<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@ page import="beans.ResultTime"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin page</title>
</head>
<body>
	<%
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		if (user.equals("admin") && password.equals("admin")) {
			ArrayList<ResultTime> results = (ArrayList<ResultTime>) application.getAttribute("lastResults");
			//removing old results (older than 30 minutes)
			for (ResultTime rt : results) {
				if (((new Date()).getTime() - rt.getCreationTime()) >= 30 * 60 * 1000) {
					results.remove(rt);
				}
			}
			//show results
	%>
	<h2>
		Conteggi effettuati:
		<%=results.size()%></h2>
	<br />
	<table id="resultsTable">
		<th>File
		<th>
		<th>Car</th>
		<th>Occorrenze</th>
		<%
			for (ResultTime rt : results) {

					//html code:
		%>
		<tr>
			<td><%=rt.getFile()%></td>
			<td><%=rt.getCar()%></td>
			<td><%=rt.getOccurrences()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<!--End Table-->
	<%
		} else {
	%>
	<h2>Forbidden: credenziali errate</h2>
	<%
		}
	%>

</body>
</html>