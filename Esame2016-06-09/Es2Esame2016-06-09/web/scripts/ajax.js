
function checkString(str){
	if(str.length < 3 || str.length > 20){
		return false;
	}
	for(var i=0; i < str.length; i++){
		if(!((str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') || (str.charAt(i) >= 'a' && str.charAt(i) <= 'z'))){
			return false;
		}
	}
	return true;
}

var responseHandler = function (responseText, targetElement) {ù
	if(responseText != null){
		result = JSON.parse(responseText);
		var container = document.createElement("DIV");
		for(var i=0; i < result.length; i++){
			var row = document.createElement("P");
			row.innerHTML = result[i];
			container.appendChild(row);
		}
		var numRighe = document.createElement("P");
		numRighe.innerHTML = "Numero righe: "+result.length;
		container.appendChild(numRighe);
		targetElement.appendChild(container);
	}
    
}

function checkAndSend(f){
	if(f.str1.value == null && f.str2.value == null && f.str3.value == null){
		alert("Stringhe vuote");
	}else{
		var uri = "../filter";
		var targetElement = myGetElementById("risultato");
		var postString = undefined;
		var sent = undefined;
		if(f.str1.value != null && checkString(f.str1.value){
			// sendajax
			postString="str="+f.str1.value;
			ajaxSendPOSTRequest(uri, postString, table, responseHandler);
			sent = true;
		}
		if(f.str2.value != null && checkString(f.str2.value){
			// sendajax
			postString="str="+f.str1.value;
			ajaxSendPOSTRequest(uri, postString, table, responseHandler);
			sent = true;
		}
		if(f.str3.value != null && checkString(f.str3.value){
			// sendajax
			postString="str="+f.str1.value;
			ajaxSendPOSTRequest(uri, postString, table, responseHandler);
			sent = true;
		}
		if(sent){
			targetElement.innerHTML="";
		} else {
			alert("Stringhe inserite non valide");
		}
	}
}



// template
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