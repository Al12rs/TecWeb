/**
 * 
 */



function checkString(event, stringa){
	if(event.keyCode !== 13)
		{
			return;
		}
	var words = stringa.split(" ");
	if(words.length >1 )
		{
			alert("E' consentita solo una parola con caratteri alfabetici");
			return;
		}
	
	if(stringa != stringa.toUpperCase() && stringa != stringa.toLowerCase())
		{
			alert("la parola dev'essere o tutta maiuscola o tutta minuscola");
			return;
		}
	stringa = stringa.toUpperCase()
	var alphaTest = /^[A-Z]*$/;
	
			if(!alphaTest.test(stringa))
			{
				alert("Si possono inserire solo caratteri alfabetici per la ricerca");
				return;
			}
		
	
	
	alert("Ricerca Effettuata Correttamente, the flickr is proud of you!!");
}

function underlineOnOff(lista, element)
{
	var menuItems = myGetElementById(lista).childNodes;
	
	for(var i=0; i< menuItems.length; i++ )
		{
			if(menuItems[i].firstChild != null)
				{
					var a = menuItems[i].childNodes[0];
					if(a.innerText == element)
						{
							var ele = myGetElementById(element);
							ele.style.borderBottom = "2px solid #556675";
						}
					else
						{
							var ele = myGetElementById(a.innerText);
							ele.style.borderBottom = "none";
						}
				
				}
		}
	
	

}

function underlineOff(element)
{
	var ele = myGetElementById(element);
	ele.style.borderBottom = "none";
}

function displayOn(element)
{
	var ele = myGetElementById(element);
	ele.style.display = "block";
	

}

function displayoff(element)
{
	var ele = myGetElementById(element);
	ele.style.display = "none";
	

}

