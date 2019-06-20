
var URLs = new Array();
var resultIndex = 0;
var x = undefined;
var remaining = undefined;

function checkX(x) {
    if (isNaN(x.value) || parseInt(x.value) == 0) {
        x.value = "";
        alert("please insert valid fields");
        return;
    }
}

function checkURLParameters() {

    if (x == undefined) {
        var url = new URL(this.document.URL);
        x = url.searchParams.get("x");
        if (isNaN(x)) {
            window.location.assign("home.html");
        }
    }
    remaining = x;
}

function saveURL(documentURL) {
    URLs.push(documentURL);
    remaining -= 1;
    myGetElementById("remaining").innerHTML = "Rimasti: " + remaining;
    myGetElementById("documentURL").value = "";
    if (remaining <= 0) {
        calcolaOccorrenzeTotali();
    }

}

var responseHandler = function (responseText, targetElement) {
    var result = JSON.parse(responseText);
    if (result == null) {
        return;
    } else {
        targetElement.innerHTML = "Occorrenze trovate: " + result;
    }
}

function calcolaOccorrenzeTotali() {

    var uri = "../contaoccorrenze";
    var targetElement = myGetElementById("risultato");

    for (var i = 0; i < x; i++) {
        ajaxSendPOSTRequest(uri, "x=" + x + "&fileLocation=" + URLs[i], targetElement, responseHandler);
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

        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

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