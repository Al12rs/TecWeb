

import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler; 

public class SAXContentHandler extends DefaultHandler {

	private ArrayList<GiocattoloTradizionale> GT68 = new ArrayList<GiocattoloTradizionale>();
	private ArrayList<Audiovisivo> AVIta = new ArrayList<Audiovisivo>();
	private ArrayList<Cdmusicale> CDM03 = new ArrayList<Cdmusicale>();

	
	public void startElement (String namespaceURI, String localName, String rawName, Attributes atts) { 
		//System.out.println("AddressListContentHandler.startElement   namespaceURI=" + namespaceURI + " localName=" + localName + " rawName=" +rawName +" atts="+atts);
		if (localName.equals("AV") && atts.getValue("lingua").equals("italiano")) {
			AVIta.add(new Audiovisivo(atts.getValue("id"), atts.getValue("titolo"), atts.getValue("autore"),
					atts.getValue("casaProduttrice"), atts.getValue("annoProduzione"), atts.getValue("fasciaEta"),
					atts.getValue("lingua"), atts.getValue("durata")));
		}
		
		if (localName.equals("CDM") && atts.getValue("fasciaEta").equals("0-3")) {
			CDM03.add(new Cdmusicale(atts.getValue("id"), atts.getValue("titolo"), atts.getValue("autore"), 
					atts.getValue("casaProduttrice"), atts.getValue("annoProduzione"), atts.getValue("fasciaEta"), 
					atts.getValue("lingua"), atts.getValue("durata")));
		}

		if (localName.equals("GT") && atts.getValue("fasciaEta").equals("6-8")) {
			GT68.add(new GiocattoloTradizionale(atts.getValue("id"), atts.getValue("nome"), atts.getValue("autore"), atts.getValue("marca"), atts.getValue("fasciaEta"), atts.getValue("modalitaUso")));
		}
		
	} 

	public void characters (char ch[], int start, int length) {
		
	} 

	public void endElement(String namespaceURI, String localName, String qName) {
		//System.out.println("AddressListContentHandler.endElement   namespaceURI=" + namespaceURI + " localName=" + localName + " qName=" +qName);
		if (localName.equals("First_name")) {
		}
	}

	

	public ArrayList<Cdmusicale> getCDMusicali_Fascia03() {
        return this.CDM03;
	}

	public ArrayList<Audiovisivo> getAudiovisivi_Italiano() {
		return this.AVIta;
	}

	public ArrayList<GiocattoloTradizionale> getGiocoTradizionale_Fascia68_GI() {
        return this.GT68;
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
	
	private int peopleAmount = 0;
	public int getPeopleAmount(){
		return peopleAmount;
	}
	
	private int peoplePreMM = 0;
	public int getPeoplePreMM(){
		return peoplePreMM;
	}

	private Vector<String> donTel = new Vector<String>();
	public Vector<String> getDonTel(){
		return donTel;
	}
	
}	
	
	
	
	
	
	
	
	
	
	


