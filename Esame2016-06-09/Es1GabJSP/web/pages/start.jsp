<%@ page session="true"%>
<%@ page import="beans.MsgList"%>
<%@ page import="java.util.*"%>
<html>
    <head>
		<title>Start</title>
        <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
        <script>
            function validate(value){
                if(value.length != 1 && !( (value > '0' && value < '9') || (value > 'a' && value < 'z')) ){
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>

    <legend>File filter:</legend>
	<form name="filter" action="/dispatcher" method="post" onsubmit="return validate(this.character.value)">
	    <label for="file">file name</label>
		<input type="text" name="file" placeholder="filename" required>
		<br />
		<label for="character">character</label>
		<input type="text" name="character" placeholder="c" required>
		<br />
		<input type="submit" value="filter">
	</form>

    <div id="result">
        <%  
            HashMap<String, ResultInfo> resultMap = (HashMap<String, ResultInfo>)application.getAttribute("resultMap");
            ResultInfo currentUserResults = resultMap.get(session.getId());
            ArrayList<String> messages = msgToReturn.getMsgList();
            if (currentUserResults != null) {
                ResultInfo[] results = currentUserResults.getResults();
                for (int i =0; i<3; i++){
                    if(results[i] > 0){
                    %>
                    <p> Result: <%= results[i]%></p>
                    <%
                    }
                }
            }
        %>

    </div>

    </body>
</html>