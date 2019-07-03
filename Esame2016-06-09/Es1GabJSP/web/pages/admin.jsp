<%@ page session="true"%>
<%@ page import="beans.MsgList"%>
<%@ page import="java.util.*"%>
<html>
    <head>
		<title>admin</title>
        <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
        <script>
           
        </script>
    </head>
    <body>



    <div id="result">
        <%  
            HashMap<String, ResultInfo> resultMap = (HashMap<String, ResultInfo>)application.getAttribute("resultMap");
            int numOfResult = 0;
            for(ResultInfo currentResult : resultMap.valueSet()){
                ArrayList<String> messages = msgToReturn.getMsgList();
                if (currentUserResults != null) {
                    ResultInfo[] results = currentUserResults.getResults();
                    long[] timestamps = currentUserResults.getTimeStamps();
                    for (int i =0; i<3; i++){
                        if(results[i] > 0 && ((new Date()).getTime() - timestaps[i]) < 30*60*1000 ){
                            numOfResult++;
                            %>
                            <p> Result: <%= results[i]%></p>
                            <%
                        }
                    }
                }
            }
        %>
        <p>Num filter operations in last 30 mins: <%=numOfResult %></p>

    </div>

    </body>
</html>