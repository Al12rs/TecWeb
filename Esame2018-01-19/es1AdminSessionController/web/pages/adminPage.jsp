<%@ page session="true"%>
<%@ page import="beans.SessionInfo"%>
<%@ page import="java.util.*"%>
<html>
    <head>
        <meta http-equiv="Pragma" content="no-cache" />
        <meta http-equiv="Expires" content="-1" />
        <title>Home</title>
        <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
        <script type="text/javascripts" src="../scripts/ajax.js"></script>
    </head>
    <body>

    <div id="stats">
        <% 
            HashTable<String, SessionInfo> sessionTracker = (HashTable<String, SessionInfo>) application.getAttribute("sessionTracker");
            if(sessionTracker != null){
                //HTML table heading
                %>
                <h2>Session statistics</h2>
                <table id="sessionTable">
                    <th>ID</th>
                    <th>Creation Time</th>
                    <th>End Time</th>
                    <th>UserName</th>
                <%
                HashTable<String, Integer> userSessionCounter = new HashTable<String, Integer>();
                Integer anonSessionCounter = 0;
                for (String sessionId : sessionTracker.keySet() ){

                    //html code:
                    %>
                    <tr>
                        <td><%= sessionTracker.get(sessionId).getSessionId %></td>
                        <td><%= sessionTracker.get(sessionId).getCreationTime().toString() %></td>
                        <td><%= sessionTracker.get(sessionId).getEndTime().toString() %></td>
                        <%
                        LoginInfo loginInfo = sessionTracker.get(sessionId).getLoginInfo();
                        if(loginInfo == null){
                            anonSessionCounter++;
                        } else {
                            Integer userCount = userSessionCounter.get(loginInfo.getUsername());
                            if(userCount != null){
                                userSessionCounter.put(loginInfo.getUsername(), ++userCount);
                            } else {
                                userSessionCounter.put(loginInfo.getUsername(), 1);
                            }
                        }
                        %>
                        <td><%= (loginInfo != null ) ? loginInfo.getUsername() : "anon" %></td>
                    </tr>
                <%
                }
                %>
                </table><!--End sessionTable-->
                <h2>User statistics</h2>
                <table id="userTable">
                    <th>Username</th>
                    <th>Session Count:</th>
                <% for (String username : userSessionCounter.keySet() ){

                    //html code:
                    %>
                    <tr>
                        <td><%= username %></td>
                        <td><%= userSessionCounter.get(username).toString() %></td>
                    </tr>
                <%
                }
                %>
                <tr>
                    <td>anon</td>
                    <td><%= anonSessionCounter %></td>
                </table><!--End userTable-->
            }
            
        %>

    </div>

    <form>
        <label>SessionId sorgente: </label><br/>
        <select name="sessione_sorgente" required>
        <% 
        for (String sessionId : sessionTracker.keySet() ){

            //html code:
            %>
            <option value='<%=sessionId %>' >
                <%=sessionId %>
            </option>
        <%  
        }
        %>
        </select><br/>
        <label>SessionId destinazione: </label><br/>
        <select name="sessione_destinazione" required>
        <% 
        for (String sessionId : sessionTracker.keySet() ){

            //html code:
            %>
            <option value='<%=sessionId %>' >
                <%=sessionId %>
            </option>
        <%  
        }
        %>
        </select><br/>
        
        <input type="button" value="Esegui copia" onclick="copySession(this.form)" />
    </form>
    <br/>
    <div id="risultato">

    </div>

    </body>
    <%
    /*
        Domanda 1, utente e sessioni concorrenti:
        In realtà in questa implementazione priva 
        di business logic non ci sono particolari problemi in
        questo caso.

        Domanda due, più amministratori con copie concorrenti:
        In questo caso invece ci possono essere problemi in quanto l'operazione
        di copia non è atomica, quindi si possono ottenere degli stati inconsistenti
        delle sessioni. Una possibile soluzione è quella di incapsulare
        tutte le operazioni sulla lista di sessioni in blocchi synchronized su 
        application context.
    */
    %>
</html>