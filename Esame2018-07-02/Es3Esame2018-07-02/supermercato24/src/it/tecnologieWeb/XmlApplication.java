import java.io.File;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.XMLReader;

public class XmlApplication {

    private XMLReader xmlReader;
    private SAXContentHandler handler;
    private Document domDocument;
    private DocumentBuilder db;

    public XmlApplication() {
        try {
            String schemaFeature = "http://apache.org/xml/features/validation/schema";
            String ignorableWhitespace = "http://apache.org/xml/features/dom/include-ignorable-whitespace";

            // SAX
            // 1) Costruire un parser SAX che validi il documento XML
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(true);
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();

            // 2) agganciare opportuni listener al lettore XML
            xmlReader = saxParser.getXMLReader();
            ErrorHandler errorHandler = new ErrorHandler();
            xmlReader.setErrorHandler(errorHandler);
            // (unico content handler per tutti i DTD/XSD;
            // sarebbe pi� corretto content handler diversi per DTD/XSD diversi)
            handler = new SAXContentHandler();
            xmlReader.setContentHandler(handler);
            //
            // // seguente istruzione per specificare che stiamo validando tramite XML
            // Schema
            xmlReader.setFeature(schemaFeature, true);

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

    public void parseSAX(String filename) {
        try {
            xmlReader.parse(filename);
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
    public Hashtable<String, Integer> getCountCategoria() {
        if (this.domDocument == null)
            return null;
        Hashtable<String, Integer> result = new Hashtable<String, Integer>();
        for (int i = 0; i < this.domDocument.getElementsByTagName("ordine").item(0).getChildNodes().getLength(); i++) {
            result.put(this.domDocument.getElementsByTagName("ordine").item(0).getChildNodes().item(i).getNodeName(),
                    this.domDocument.getElementsByTagName("ordine").item(0).getChildNodes().item(i).getChildNodes().getLength());
        }
        return result;
    }

    public Float getCostoTotaleSpesa() {
        //Dom check
        if (this.domDocument == null)
            return null;
        
        NodeList pricesList = domDocument.getElementsByTagName("prezzo");
        int xLen = pricesList.getLength();

        String value;
        float result = 0;
        for (int i = 0; i < xLen; i++) {
            value = pricesList.item(i).getFirstChild().getNodeValue();

            try {
                result += Float.parseFloat(value.substring(1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }

}