<%@ page session="true"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<html>
<head>
<title>Home</title>
<link type="text/css" href="styles/default.css" rel="stylesheet"></link>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script>
	
</script>
</head>
<body>
	<%
		ArrayList<File> files = (ArrayList<File>) application.getAttribute("files");
		if (files == null) {
			files = new ArrayList<File>();
			String dirPath = System.getProperty("catalina.base")
					+ "/webapps/Es1SplitCounting.jsp/resources/Split_Counting/";
			File directory = new File(dirPath);
			for (File f : directory.listFiles()) {
				files.add(f);
			}
			application.setAttribute("files", files);
		}
	%>
	<fieldset>
		<legend>Split_Counting</legend>
		<form>
			<label for="fileName">Nome File</label><br /> <select name="fileName"
				onchange="checkAndSendRequest(this.form)">
				<%
					for (File f : files) {
				%>
				<option value="<%=f.getName()%>" selected="selected"><%=f.getName()%></option>
				<%
					}
				%>
			</select><br /> <label for="option">Opzione</label><br /> <select
				name="option" onchange="checkAndSendRequest(this.form)">
				<option value="option1" selected="selected">Caratteri
					Maiuscoli</option>
				<option value="option2" selected="selected">Caratteri Non
					Alfabetici</option>
			</select><br />
		</form>
	</fieldset>

	<div id="risultato"></div>

</body>
</html>