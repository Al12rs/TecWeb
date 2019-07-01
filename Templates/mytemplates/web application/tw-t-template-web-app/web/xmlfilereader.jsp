<?xml version="1.0" encoding="UTF-8"?>
<%-- ?xml... must be the first line before anything else
     and must NOT be in the xml file --%>
<%@page import="java.net.URL"%>
<%@page errorPage="errors/failure.jsp"%>

<%
	response.setHeader("Content-Type", "application/xml");
	String filename = request.getParameter("filename");
	if (filename != null) {
		filename = "/" + filename + ".xml";
		URL url = application.getResource(filename);
		if (url != null) {
%>
<jsp:include page="<%=filename%>"></jsp:include>
<%
		} else {
			String empty = "empty.xml";
%>
<jsp:include page="<%=empty%>"></jsp:include>
<%
		}
	}
%>