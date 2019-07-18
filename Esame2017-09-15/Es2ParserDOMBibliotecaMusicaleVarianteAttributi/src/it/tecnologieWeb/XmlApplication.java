package it.tecnologieWeb;

import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
			Element musicista = (Element) musicisti.item(i);
			if (musicista.getAttribute("genereMusicale").equals(genere)) {
				int numeroAlbum = musicista.getChildNodes().getLength();
				if (numeroAlbum < totAlbum) {
					result.add(musicista.getAttribute("cognome"));
				}
			}
		}
		return result;
	}

	public boolean addAlbum(Document doc, String nome, String cognome, String genere, String titoloAlbum,
			int annoPubblicazione) {
		boolean result = false;
		NodeList musicisti = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < musicisti.getLength(); i++) {
			Element musicista = (Element) musicisti.item(i);
			if (musicista.getAttribute("genereMusicale").equals(genere) && musicista.getAttribute("nome").equals(nome)
					&& musicista.getAttribute("cognome").equals(cognome)) {
				Node album = musicista.getChildNodes().item(0);
				boolean found = false;
				while (album != null && !found) {
					// check titoloAlbum
					if (album.getFirstChild().getFirstChild().getNodeValue().equals(titoloAlbum))
						found = true;
					album = album.getNextSibling();
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
					result = saveToXML(this.domDocument.getDocumentURI()
							.substring(this.domDocument.getDocumentURI().indexOf("file:\\") + 6));
					break;
				}
			}
		}
		return result;
	}

	private boolean saveToXML(String path) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(path));
			Source input = new DOMSource(this.domDocument);
			transformer.transform(input, output);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
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