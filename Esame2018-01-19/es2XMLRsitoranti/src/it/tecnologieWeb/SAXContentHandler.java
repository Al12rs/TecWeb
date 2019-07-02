package it.tecnologieWeb;



import java.util.Hashtable;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler; 

public class SAXContentHandler extends DefaultHandler {
	
	Integer numPortateOfferte = 0;

	float costoTotale = 0;
	Hashtable<String, Integer> CountCategoria = new Hashtable<String, Integer>();
	private int midCounter = 0;
	private boolean inFV = false;
	private boolean inPP = false;
	private boolean inL = false;
	private boolean inCP = false;
	private boolean inPrezzo = false;
	
	public void startElement (String namespaceURI, String localName, String rawName, Attributes atts) { 
		//System.out.println("AddressListContentHandler.startElement   namespaceURI=" + namespaceURI + " localName=" + localName + " rawName=" +rawName +" atts="+atts);
		
		if(localName.equals("piatto")) {
			numPortateOfferte++;
		}
	}  

	
	private int ignorableWhitespace = 0;
	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		ignorableWhitespace += length;
	}
	public int getIgnorableWhitespace(){
		return ignorableWhitespace;
	}

	
	//metodi di restituzione risultati
	public Integer getNumeroPortateOfferte() {
		return numPortateOfferte;
	}


	
}	
	
	
	
	
	
	
	
	
	
	


