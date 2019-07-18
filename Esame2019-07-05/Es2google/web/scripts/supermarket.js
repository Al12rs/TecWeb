//valore della media passato dal server in qualche modo non rilevante.
var media = 4;
//Altro valore arbitrario in teoria passato dal server.
var numUtenti = 1200;

var currentSliderView = 0;

var logos = ["<span><img src='images/flickr_logo.png'></span>",
                 "<span class='logo'><img src='images/flickr_logo.png'></span>",
                 "<span class='logo'><img src='images/flickr_logo.png'></span>",
                 "<span class='logo'><img src='images/flickr_logo.png'></span>",
                 "<span class='logo'><img src='images/flickr_logo.png'></span>",
                 "<span class='logo'><img src='images/flickr_logo.png'></span>",
                 "<span class='logo'><img src='images/flickr_logo.png'></span>",
                 "<span class='logo'><img src='images/flickr_logo.png'></span>",
                 "<span class='logo'><img src='images/flickr_logo.png'></span>",
                 "<span class='logo'><img src='images/flickr_logo.png'></span>",
                 "<span class='logo'><img src='logo10.png'></span>",
                 "<span class='logo'><img src='logo11.png'></span>",
                 "<span class='logo'><img src='logo12.png'></span>",
                 "<span class='logo'><img src='logo13.png'></span>",
                 "<span class='logo'><img src='logo14.png'></span>",
                 "<span class='logo'><img src='logo15.png'></span>",
                 "<span class='logo'><img src='logo16.png'></span>",
                 "<span class='logo'><img src='logo17.png'></span>"];
var caps =[];
caps["40134"]="Bologna, Quartiere Saragozza";
caps["48012"]="Bagnacavallo Centro del mondo";
caps["48024"]="Fruges Culo del mondo";

function populatePage(){

    //populate rating
    var ratingDiv = myGetElementById("ratingDiv");
    var stellinaPiena= "<span><img src='fullStart.png'></span>";
    var stellinaVuota= "<span><img src='emptyStart.png'></span>";
    var stelline = "";
    for (var i=1; i <= 6; i++){
        if(i<media){
            stelline = stelline + stellinaPiena;
        } else{
            stelline = stelline + stellinaVuota;
        }
    }
    ratingDiv.innerHTML = stelline + numUtenti + ratingDiv.innerHTML;

    //populate slider
    
    var slider = myGetElementById("slider");
    for(var i=0; i<9; i++){
        slider.innerHTML = logos[i] + slider.innerHTML;
    }
    currentSliderView =0;


}

function sliderBtnClicked(){
    var sliderHtml = ' <button id="sliderBtn" style="position: absolute; right: 0px" type="button" onclick="sliderBtnClicked()">></button>';
    var slider = myGetElementById("slider");
    slider.innerHTML = sliderHtml;
    if (currentSliderView === 0){
        for (var i=9; i<18;i++){
            slider.innerHTML = logos[i] + slider.innerHTML;
        }
        currentSliderView=1;
    } else {
        for(var i=0; i<9;i++){
            slider.innerHTML = logos[i] + slider.innerHTML;
        }
        currentSliderView =0;
    }
}

function trovaClicked(){
    var input = myGetElementById("cap");
    if(input.value.length == 5 && input.value.charAt(0) !='0' ){
        var ok = true;
        for(var i=0; i<5;i++){
            if(input.value.charAt(i) < '0' || input.value.charAt(i) > '9'){
                ok=false;
                break;
            }
        }
        if (!ok){
            alert ("CAP non valido, inserisci 5 cifre.");
            return;
        }
    } else {
        alert ("CAP non valido, inserisci 5 cifre.");
        return;
    }

    var result = myGetElementById("quartiere");
    result.style.display = "block";
    result.value= caps[input.value];

}