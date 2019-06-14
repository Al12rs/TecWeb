import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

public class Ludoteca {

    private XMLReader xmlReader;
    private SAXContentHandler handler;

    public Ludoteca() {
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

    public void parse(String filename) {
        try {
            xmlReader.parse(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Cdmusicale> getCDMusicali_Fascia03() {
        return handler.getCDMusicali_Fascia03();
    }

    public ArrayList<Audiovisivo> getAudiovisivi_Italiano() {
        return handler.getAudiovisivi_Italiano();
    }

    public ArrayList<GiocattoloTradizionale> getGiocoTradizionale_Fascia68_GI() {
        return handler.getGiocoTradizionale_Fascia68_GI();
    }
}