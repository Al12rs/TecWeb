
//var partialResults = new Array();
var resultIndex = 0;

/*function showMatrix(result) {
    var targetElement = myGetElementById("risultato");
    var table = document.createElement("TABLE");
    table.createCaption().innerHTML = "Result of operation A" + result.operation + "B, index: " + result.id;
    table.insertRow

}*/

var responseHandler = function (responseText, targetElement) {
    if (responseText == null) {
        targetElement.innerHTML = targetElement.innerHTM + "failed copy";
        return;
    }
    result = JSON.parse(responseText);
    targetElement.innerHTML="Copied attributes: <br/>";
    for(var i = 0; i < result.length; i++){
        targetElement.innerHTML = targetElement.innerHTM + result[i] +"; ";
    }
}



function copySession(form) {

    var uri = "../copysession";
    var targetElement = myGetElementById("risultato");
    var reqBody = new Object();

    var table = document.createElement("TABLE");
    table.createCaption().innerHTML = "Result of operation A" + result.operation + "B, index: " + resultIndex;
    targetElement.appendChild(table);

    reqBody.source = form.sessione_sorgente.value;
    reqBody.destination = form.sessione_destinazione.value;
    var postString = JSON.stringify(reqBody);
    ajaxSendPOSTRequest(uri, postString, targetElement, responseHandler);

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