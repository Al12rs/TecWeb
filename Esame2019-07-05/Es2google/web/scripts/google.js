function checkString(str){
    var arr = str.split(" ");
    if(arr.length != 4){
        alert("Si accettano solo quattro parole");
        str="";
    }
    for(var i=0; i < str.length; i++ ){
        if((str.charAt(i)>='0' && str.charAt(i)<='9')||(str.charAt(i)>='A' && str.charAt(i)<='Z')){
            alert("Si accettano solo caratteri alfabetici minuscoli");
            str="";
            return;
        }
    }
}