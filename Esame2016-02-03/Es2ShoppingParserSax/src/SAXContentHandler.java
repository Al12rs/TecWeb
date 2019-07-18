
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXContentHandler extends DefaultHandler {

	private Hashtable<String, ArrayList<CapoAbbigliamento>> scelte = new Hashtable<String, ArrayList<CapoAbbigliamento>>();
	private boolean inScelta = false;
	private String currentScelta = null;

	public void startElement(String namespaceURI, String localName, String rawName, Attributes atts) {
		// System.out.println("AddressListContentHandler.startElement namespaceURI=" +
		// namespaceURI + " localName=" + localName + " rawName=" +rawName +"
		// atts="+atts);

		if (localName.equals("abiti")) {
			inScelta = true;
			currentScelta = localName;
		}
		if (localName.equals("camice")) {
			inScelta = true;
			currentScelta = localName;
		}
		if (localName.equals("giacche")) {
			inScelta = true;
			currentScelta = localName;
		}
		if (localName.equals("gonne")) {
			inScelta = true;
			currentScelta = localName;
		}
		if (localName.equals("pantaloni")) {
			inScelta = true;
			currentScelta = localName;
		}
		if (localName.equals("ultimiarrivi")) {
			inScelta = true;
			currentScelta = localName;
		}
		if (localName.equals("capo") && (inScelta)) {
			if (scelte.containsKey(currentScelta)) {
				List<CapoAbbigliamento> capi = scelte.get(currentScelta);
				capi.add(new CapoAbbigliamento(atts.getValue("fotografia"), atts.getValue("descrizione"),
						atts.getValue("prezzo")));
			} else {
				scelte.put(currentScelta, (new ArrayList<CapoAbbigliamento>()));
				List<CapoAbbigliamento> capi = scelte.get(currentScelta);
				capi.add(new CapoAbbigliamento(atts.getValue("fotografia"), atts.getValue("descrizione"),
						atts.getValue("prezzo")));
			}
		}
	}


	public void endElement(String namespaceURI, String localName, String qName) {
		// System.out.println("AddressListContentHandler.endElement namespaceURI=" +
		// namespaceURI + " localName=" + localName + " qName=" +qName);
		if (localName.equals("abiti")) {
			inScelta = false;
			currentScelta = null;
		}
		if (localName.equals("camice")) {
			inScelta = false;
			currentScelta = null;
		}
		if (localName.equals("giacche")) {
			inScelta = false;
			currentScelta = null;
		}
		if (localName.equals("gonne")) {
			inScelta = false;
			currentScelta = null;
		}
		if (localName.equals("pantaloni")) {
			inScelta = false;
			currentScelta = null;
		}
		if (localName.equals("ultimiarrivi")) {
			inScelta = false;
			currentScelta = null;
		}
	}

	private int ignorableWhitespace = 0;

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		ignorableWhitespace += length;
	}

	public int getIgnorableWhitespace() {
		return ignorableWhitespace;
	}


	// metodi di restituzione risultati
	public Hashtable<String, ArrayList<CapoAbbigliamento>> getScelte() {
		return scelte;
	}

}
