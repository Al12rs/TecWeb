var futureResultList = [];
var futurePosition = new Object();
futurePosition.x = undefined;
futurePosition.y = undefined;

var responseHandlerCurrentPosition = function (responseText, targetElement) {
	if(responseText != null){
		//array di personaggi
		result = JSON.parse(responseText);
		targetElement.innerHTML = "";
    	for(var i=0; i< result.length; i++){
    		targetElement.innerHTML = targetElement.innerHTML + result[i].nome +"<br/>";
    	}
	}
}

var responseHandlerFuturePosition = function (responseText, targetElement) {
	if(responseText != null){
		//array di personaggi
		result = JSON.parse(responseText);
		futureResultList = [];
    	for(var i=0; i< result.length; i++){
    		futureResultList[i] = result[i];
    	}
	}
}

function calcolaPersonaggi(x, y) {

    var uri = "../calcolapersonaggi";
    var targetElement = myGetElementById("risultato");
    var reqBodyCurrentPosition = new Object();
    var reqBodyFuturePosition = new Object();
    
    if(x == futurePosition.x && y == futurePosition.y){
    	targetElement.innerHTML = "";
    	for(var i=0; i< futureResultList.length; i++){
    		targetElement.innerHTML = targetElement.innerHTML + futureResultList[i].nome +"<br/>";
    	}
    	reqBodyFuturePosition.x = x+50;
		reqBodyFuturePosition.y = y;
		futurePosition.x = x+50;
		futurePosition.y = y;
		var postString = JSON.stringify(reqBodyFuturePosition);
		ajaxSendPOSTRequest(uri, postString, targetElement, responseHandlerFuturePosition);
    } else {
    	reqBodyCurrentPosition.x = x;
    	reqBodyCurrentPosition.y = y;
    	reqBodyFuturePosition.x = x+50;
		reqBodyFuturePosition.y = y;
		futurePosition.x = x+50;
		futurePosition.y = y;
		var postStringCurrentPosition = JSON.stringify(currentPosition);
		var postStringFuturePosition = JSON.stringify(reqBodyFuturePosition);
		ajaxSendPOSTRequest(uri, postStringCurrentPosition, targetElement, responseHandlerCurrentPosition);
		ajaxSendPOSTRequest(uri, postStringFuturePosition, targetElement, responseHandlerFuturePosition);
    }

}


//template
function ajaxSendPOSTRequest(uri, postString, targetElement, responseHandler) {
    var xhr = myGetXmlHttpRequest();

    if (xhr) {
        // impostazione controllo e stato della richiesta
        xhr.onreadystatechange = function () { ajaxCallback(xhr, targetElement, responseHandler); };

        // impostazione richiesta asincrona in GET
        // del file specificato
        try {
            xhr.open("post", uri, true);
        }
        catch (e) {
            // Exceptions are raised when trying to access cross-domain URIs 
            alert(e);
        }

        // rimozione dell'header "connection" come "keep alive"
        xhr.setRequestHeader("connection", "close");

        // invio richiesta
        xhr.send(postString);
    }
    else
        targetElement.innerHTML = "Errore durante creazione xmlhttpRequest.";
}

//template
function ajaxCallback(xhr, targetElement, responseHandler) {
    // verifica dello stato
    if (xhr.readyState === 2) {
        //targetElement.innerHTML = "Richiesta inviata...";
    }// if 2
    else if (xhr.readyState === 3) {
        //targetElement.innerHTML = "Ricezione della risposta...";
    }// if 3
    else if (xhr.readyState === 4) {

        // verifica della risposta da parte del server
        if (xhr.status === 200) {

            // operazione avvenuta con successo
            responseHandler(xhr.responseText, targetElement);

        }// if 200

        else {
            // errore di caricamento
            targetElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
            targetElement.innerHTML += "Errore riscontrato: " + xhr.statusText;
        }// else (if ! 200)

    }// if 4
}