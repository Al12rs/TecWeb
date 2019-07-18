
var responseHandler = function(responseText, targetElement) {
	if (responseText != null) {
		result = JSON.parse(responseText);
		targetElement.innerHTML = "File salvato lato servitore. Caratteri trasformati: "
				+ result;
	} else {
		targetElement.innerHTML = "Errore lato server";
	}

}

function saveToFile(textInput, fileName) {
	if (textInput.length >= 1000 && textInput.length <= 2000) {
		var uri = "../savetofile";
		var targetElement = myGetElementById("risultato");
		var reqBody = new Object();
		reqBody.textInput = textInput;
		reqBody.fileName = fileName;
		var postString = JSON.stringify(reqBody);
		ajaxSendPOSTRequest(uri, postString, targetElement, response);
	} else {
		alert("Inserire tra i 1000 e 2000 caratteri")
	}

}

// template
function ajaxSendPOSTRequest(uri, postString, targetElement, responseHandler) {
	var xhr = myGetXmlHttpRequest();

	if (xhr) {
		// impostazione controllo e stato della richiesta
		xhr.onreadystatechange = function() {
			ajaxCallback(xhr, targetElement, responseHandler);
		};

		// impostazione richiesta asincrona in GET
		// del file specificato
		try {
			xhr.open("post", uri, true);
		} catch (e) {
			// Exceptions are raised when trying to access cross-domain URIs
			alert(e);
		}

		// rimozione dell'header "connection" come "keep alive"
		xhr.setRequestHeader("connection", "close");

		// invio richiesta
		xhr.send(postString);
	} else
		targetElement.innerHTML = "Errore durante creazione xmlhttpRequest.";
}

// template
function ajaxCallback(xhr, targetElement, responseHandler) {
	// verifica dello stato
	if (xhr.readyState === 2) {
		// targetElement.innerHTML = "Richiesta inviata...";
	}// if 2
	else if (xhr.readyState === 3) {
		// targetElement.innerHTML = "Ricezione della risposta...";
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