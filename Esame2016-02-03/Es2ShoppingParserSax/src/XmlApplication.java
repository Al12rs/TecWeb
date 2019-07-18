
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
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

    

    // custom methods:
    
    
    //SAX
    public Hashtable<String, ArrayList<CapoAbbigliamento>> getScelte() {
        return handler.getScelte();
    }
    

}