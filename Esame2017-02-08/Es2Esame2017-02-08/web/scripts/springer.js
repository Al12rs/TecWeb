function show(f){
    f.setAttribute("style", "visibility:visible;");
}

function checkString(str){
    if(str.split(" ").length > 2){
        alert("massimo due parole");
        return;
    }
    for(var i=0; i<str.length; i++){
        if(!((str.charAt(i) >= 'a' && (str.charAt(i) <= 'z') || (str.charAt(i) >= 'A' && (str.charAt(i) <= 'Z'))))){
            alert("sono ammessi solo caratteri alfabetici");
            return;
        }
    }
}