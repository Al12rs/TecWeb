<%@ page session="true"%>
<%@ page import="beans.ResultsList"%>
<%@ page import="beans.Result"%>
<%@ page import="java.util.*"%>
<html>
    <head>
		<title>Start</title>
        <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
        <!--<jsp:useBean id="resultss" class="beans.ResultsList" scope="session"/>-->
    </head>
    <body>

    <div id="results">
        <% 
        	ResultsList results = (ResultsList) session.getAttribute("results");
            for (Result res : results.getResults() ){
            	if(res != null){
                //html code:
                %><p>Nome file: <%= res.getFile()%>; Carattere: <%= res.getCar()%>; Occorrenze: <%= res.getOccurrences()%>.</p><%
            	}
            }
        %>

    </div>

    <form method="post" action="../dispatcher">
        <label>Nome file:</label><br/>
        <input type="text" name="file" value="" /><br/>
        <label>Carattere alfanumerico:</label><br/>
        <input type="text" name="car" value=""/><br/>
        <input type="submit" name="submit" value="Richiedi conteggio">
    </form>
    <br/>
    <br/>
    <br/>
    <h3>Sei l'amministratore?</h3>
	<form method="post" action="../pages/admin.jsp">
        <label>Nome Utente:</label><br/>
        <input type="text" name="user" value="" /><br/>
        <label>Password:</label><br/>
        <input type="text" name="password" value=""/><br/>
        <input type="submit" name="submit" value="Login">
    </form>
    </body>
</html>

