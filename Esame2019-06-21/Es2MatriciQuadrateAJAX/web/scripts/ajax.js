var matrixA = [];
var matrixB = [];
var inserimenti = 0;

/*function showMatrix(result) {
    var targetElement = myGetElementById("risultato");
    var table = document.createElement("TABLE");
    table.createCaption().innerHTML = "Result of operation A" + result.operation + "B, index: " + result.id;
    table.insertRow

}*/

var responseHandlerLeft = function (responseText, table) {
    result = JSON.parse(responseText);
    //partialResults[result.id].bottomMatrix = result.matrixResult;
    for (var rowIndex = 0; rowIndex < 4; rowIndex++) {
        var row = table.insertRow(rowIndex);
        for (var cellIndex = 0; cellIndex < 2; cellIndex++) {
            row.insertCell(cellIndex).innerHTML = result.matrixResult[rowIndex][cellIndex];
        }
    }
}

var responseHandlerRight = function (responseText, table) {
    result = JSON.parse(responseText);
    //partialResults[result.id].bottomMatrix = result.matrixResult;
    for (var rowIndex = 0; rowIndex < 4; rowIndex++){
        var row = table.insertRow(rowIndex);
        for (var cellIndex = 2; cellIndex < 4; cellIndex++){
            row.insertCell(cellIndex).innerHTML = result.matrixResult[rowIndex][cellIndex - 2];
        }
    }
}

function calcolaMatrice(rigaA, rigaB) {
    matrixA[inserimenti] = rigaA.split(' ').map(function(v){
    	return parseInt(v, 10);
    });
    matrixB[inserimenti] = rigaB.split(' ').map(function(v){
    	return parseInt(v, 10);
    });
    inserimenti++;
    if(inserimenti == 4){
    	inserimenti = 0;
    	
    	var uri = "../matrixop";
        var targetElement = myGetElementById("risultato");
        var reqBody = new Object();

        var table = document.createElement("TABLE");
        table.createCaption().innerHTML = "Result of operation A" + result.operation + "B, index: " + resultIndex;
        targetElement.appendChild(table);

        resultIndex++;
        reqBody.A = matrixA;
        reqBody.B = matrixB;
        var postString = JSON.stringify(reqBody);
        ajaxSendPOSTRequest(uri + "left", postString, table, responseHandlerLeft);
        ajaxSendPOSTRequest(uri + "right", postString, table, responseHandlerRight);
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