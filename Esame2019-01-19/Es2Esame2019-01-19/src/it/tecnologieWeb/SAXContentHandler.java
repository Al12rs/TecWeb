package it.tecnologieWeb;



import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler; 

public class SAXContentHandler extends DefaultHandler {
	
	private int counter = 0;
	
	public void startElement (String namespaceURI, String localName, String rawName, Attributes atts) { 
		//System.out.println("AddressListContentHandler.startElement   namespaceURI=" + namespaceURI + " localName=" + localName + " rawName=" +rawName +" atts="+atts);
		
		if(localName.equals("piatto")) {
			counter++;
		}
	} 

	public void characters (char ch[], int start, int length) {
		
	} 

	public void endElement(String namespaceURI, String localName, String qName) {
		
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
	public int getNumeroPortateOfferte() {
		return this.counter;
	}
	
}	
	
	
	
	
	
	
	
	
	
	


