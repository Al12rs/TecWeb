

var responseHandler = function (responseText, targetElement) {
    result = JSON.parse(responseText);
    targetElement.innerHTML = result.content;
    var matches = result.content.match(/esame/g);
    var count = matches.length;
    var form = myGetElementById("esito");
    form.count.value = count;
    form.submit();
}

function requestPortion() {

    var uri = "../contentgiver";
    var targetElement = myGetElementById("richiesta");
    var reqBody = new Object();
    
    ajaxSendGETRequest(uri,  targetElement, responseHandler);

}


//template
function ajaxSendGETRequest(uri, targetElement, responseHandler) {
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
        xhr.send();
    }
    else
        targetElement.innerHTML = "Errore durante creazione xmlhttpRequest.";
}

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
        
        //set content type
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
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