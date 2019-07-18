package it.tecnologieWeb;

import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.XMLReader;

public class XmlApplication {

	private Document domDocument;
	private DocumentBuilder db;

	public XmlApplication() {
		try {
			String schemaFeature = "http://apache.org/xml/features/validation/schema";
			String ignorableWhitespace = "http://apache.org/xml/features/dom/include-ignorable-whitespace";

			// DOM
			// 1) Costruire un DocumentBuilder che validi il documento XML
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			dbf.setNamespaceAware(true);
			// seguente istruzione per specificare che stiamo validando tramite XML Schema
			dbf.setFeature(schemaFeature, true);
			// seguente istruzione per specificare che gli "ignorable whitespace" (tab, new
			// line...)
			// tra un tag ed un altro devono essere scartati e non considerati come textnode
			dbf.setFeature(ignorableWhitespace, false);
			// 2) Attivare un gestore di non-conformita'
			db = dbf.newDocumentBuilder();
			db.setErrorHandler(new ErrorHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseDOM(String filename) {
		try {
			domDocument = db.parse(new File(filename));
			domDocument.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// custom methods:
	// DOM
	public Set<String> getCognomi(Document doc, String genere, int totAlbum) {
		Set<String> result = new HashSet<String>();
		NodeList musicisti = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < musicisti.getLength(); i++) {
			NodeList musicistaInfo = musicisti.item(i).getChildNodes();
			String cognome = null;
			boolean found = false;
			int albumCounter = 0;
			for (int j = 0; j < musicistaInfo.getLength(); j++) {
				String nodeName = musicistaInfo.item(j).getNodeName();
				if (nodeName.equals("cognome")) {
					cognome = musicistaInfo.item(j).getFirstChild().getNodeValue();
				}
				if (nodeName.equals("genereMusicale")
						&& (musicistaInfo.item(j).getFirstChild().getNodeValue().equals(genere))) {
					found = true;
				}				
				if (nodeName.equals("album")) {
					albumCounter++;
				}
			}
			if ((albumCounter < totAlbum) && found) {
				result.add(cognome);
			}
		}
		return result;
	}

	boolean addAlbum(Document doc, String nome, String cognome, String genere, String titoloAlbum,
			int annoPubblicazione) {
		boolean result = false;
		NodeList musicisti = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < musicisti.getLength(); i++) {
			NodeList musicistaInfo = musicisti.item(i).getChildNodes();
			Node info = musicistaInfo.item(0);
			// check nome
			if (!(info.getFirstChild().getNodeValue().equals(nome)))
				continue;
			info = info.getNextSibling();
			// check cognome
			if (!(info.getFirstChild().getNodeValue().equals(cognome)))
				continue;
			info = info.getNextSibling();
			// check genere
			if (info.getNodeName().equals("nomeArte")) {
				info = info.getNextSibling();
				if (!(info.getFirstChild().getNodeValue().equals(genere)))
					continue;
			} else {
				if (!(info.getFirstChild().getNodeValue().equals(genere)))
					continue;
			}
			// se sono arrivato qui, vuol dire che l'artista combacia con quello cercato
			info = info.getNextSibling();
			boolean found = false;
			while (info != null && !found) {
				// check titoloAlbum
				if (info.getFirstChild().getFirstChild().getNodeValue().equals(titoloAlbum))
					found = true;
				info = info.getNextSibling();
			}
			if (found) {
				// l'artista ha già un album con quel titolo
				break;
			} else {
				// aggiungo l'album
				Element newAlbum = doc.createElement("album");
				Element title = doc.createElement("titoloAlbum");
				title.appendChild(doc.createTextNode(titoloAlbum));
				Element year = doc.createElement("annoPubblicazione");
				year.appendChild(doc.createTextNode(Integer.toString(annoPubblicazione)));
				newAlbum.appendChild(title);
				newAlbum.appendChild(year);
				musicisti.item(i).appendChild(newAlbum);
				result = true;
				break;
			}
		}
		return result;
	}

	public int getLength() {
		return domDocument.getDocumentElement().getChildNodes().getLength();
	}

	public Document getDomDocument() {
		return domDocument;
	}

	public void setDomDocument(Document domDocument) {
		this.domDocument = domDocument;
	}

}