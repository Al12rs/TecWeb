<%@ page session="true"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>Start</title>
<link type="text/css" href="styles/default.css" rel="stylesheet"></link>

</head>
<body>
	<fieldset>
		<legend>Acquista biglietti:</legend>
		<form name="filter" action="../salesagent" method="post">
			<label for="file">Quantità</label><br/>
			<input type="text" name="numBiglietti" required><br /> 
			 <input type="submit" value="Acquista">
		</form>
	</fieldset>

	<h3>Stato acquisto</h3>
	<div id="result">
		<%  
			String statoAcquisto = (String)session.getAttribute("statoAcquisto");
			if(statoAcquisto != null){
		%>
				<p>Stato: <%= statoAcquisto %></p>
		<%
			}
		%>
	</div>

</body>
</html>