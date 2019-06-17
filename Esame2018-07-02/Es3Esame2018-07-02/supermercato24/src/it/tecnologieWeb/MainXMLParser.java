

import java.io.*;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class MainXMLParser {

	private Document domDocument = null;
	private SAXContentHandler handler = null;

	public static void main(String[] args) throws Exception {

		String xmlFilename;

		/*
		 * if (args.length != 0) { System.out.println("usage: " +
		 * MainXMLParser.class.getSimpleName() + " xmlFilename"); exit(1); }
		 */

        xmlFilename = "C:\\Repos\\TecWeb\\Esame2018-09-20\\Es3Esame2018-07-02\\supermercato24\\resources\\ordine1.xml";
        XmlApplication app = new XmlApplication();
		app.parseDOM(xmlFilename);
		Float totalCost = app.getCostoTotaleSpesa();
		if (totalCost == null) {
			System.out.println("How?");
		} else {
			System.out.println(totalCost);
		}

		Hashtable<String, Integer> catCount = app.getCountCategoria();
		
		if (catCount == null) {
			System.out.println("How?");
		} else {
			for (String cat : catCount.keySet()) {
				System.out.println(cat);
				System.out.println(catCount.get(cat));
			}
		}

		System.out.println("fine");

	}

}
