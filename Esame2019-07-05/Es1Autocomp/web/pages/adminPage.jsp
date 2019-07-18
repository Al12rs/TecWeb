<%@ page session="true"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>AdminPage</title>
<link type="text/css" href="styles/default.css" rel="stylesheet"></link>
<script>
	
</script>
</head>
<body>
	<% 
    	// se fossero stati più gruppi, avrei potuto usare application.getAttributeNames() per ciclare su tutte le tabelle
    	// e generare dinamicamente la tabella usando un ciclo for e i nomi ottenuti da application
    %>


	<h2>Sezioni</h2>
	<table id="userTable">
		<th>Statitisca</th>
		<th></th>
		<tr>
			<td>globalPopularity</td>
			<td>
				<form method="get"
					action="../completaparola?remove=globalPopularity">
					<input type="submit" value="Rimuovi" />
				</form>
			</td>
		</tr>
		<tr>
			<td>group1Popularity</td>
			<td>
				<form method="get"
					action="../completaparola?remove=group1Popularity">
					<input type="submit" value="Rimuovi" />
				</form>
			</td>
		</tr>
		<tr>
			<td>group2Popularity</td>
			<td>
				<form method="get"
					action="../completaparola?remove=group2Popularity">
					<input type="submit" value="Rimuovi" />
				</form>
			</td>
		</tr>
		
	</table>
	<!--End userTable-->



</body>
</html>