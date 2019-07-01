<%@page import="org.jabsorb.JSONSerializer"%>

<%		
	JSONSerializer serializer = new JSONSerializer();
	try {
		// inizializza i tipi serializzatori forniti di default 
		serializer.registerDefaultSerializers(); 
	} 
	catch (Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}
	
	// ricevere un oggetto serializzato come parametro della richiesta
	Object toReceive = serializer.fromJSON(request.getParameter("jsonObject"));
	
	// inviare un oggetto come corpo della risposta
	Object toSend = new Object();
%>
<%= serializer.toJSON(toSend) %>
