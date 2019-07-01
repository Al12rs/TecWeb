package it.tecnologieWeb;



import java.util.Hashtable;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler; 

public class SAXContentHandler extends DefaultHandler {
	
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
		
		if(localName.equals("FV")) {
			inFV=true;
		}
		if(localName.equals("PP")) {
			inPP=true;
		}
		if(localName.equals("L")) {
			inL =true;
		}
		if(localName.equals("CP")) {
			inCP =true;
		}
		if(localName.equals("prodotto") && (inFV || inPP || inL || inCP)) {
			midCounter++;
		}
		if(localName.equals("prezzo")) {
			inPrezzo =true;
		}
	} 

	public void characters (char ch[], int start, int length) {
		//System.out.println("AddressListContentHandler.characters   start=" + start + " length=" + length + " ch=" +new String(ch,start,length));
		if(inPrezzo) {
			float prezzoProdotto = Float.parseFloat((new String(ch, start, length)).substring(1));
			this.costoTotale += prezzoProdotto;
		}
	} 

	public void endElement(String namespaceURI, String localName, String qName) {
		//System.out.println("AddressListContentHandler.endElement   namespaceURI=" + namespaceURI + " localName=" + localName + " qName=" +qName);
		if(localName.equals("FV")) {
			inFV=false;
			this.CountCategoria.put("FV", midCounter);
			midCounter=0;
		}
		if(localName.equals("PP")) {
			inPP=false;
			this.CountCategoria.put("PP", midCounter);
			midCounter=0;
		}
		if(localName.equals("L")) {
			inL=false;
			this.CountCategoria.put("L", midCounter);
			midCounter=0;
		}
		if(localName.equals("CP")) {
			inCP=false;
			this.CountCategoria.put("CP", midCounter);
			midCounter=0;
		}
		if(localName.equals("prezzo")) {
			inPrezzo = false;
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

	private Vector<String> donTel = new Vector<String>();
	public Vector<String> getDonTel(){
		return donTel;
	}
	
	//metodi di restituzione risultati
	public Float getCostoTotale() {
		return costoTotale;
	}

	public Hashtable<String, Integer> getCountCategoria() {
		return CountCategoria;
	}
	
}	
	
	
	
	
	
	
	
	
	
	


