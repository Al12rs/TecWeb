package it.tecnologieWeb;



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
		//if autput is to go on a file
		String outFile ="";
		PrintWriter outWriter = new PrintWriter(outFile);
		outWriter.println("result:");
		outWriter.close();
		*/
		
		/*
		 * if (args.length != 0) { System.out.println("usage: " +
		 * MainXMLParser.class.getSimpleName() + " xmlFilename"); exit(1); }
		 */

        xmlFilename = "resources/ordine1.xml";
        XmlApplication app = new XmlApplication();
        
        //DOM
        System.out.println("DOM Parsing Results");
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
		
		//SAX
		System.out.println("SAX Parsing Results");
		app.parseSAX(xmlFilename);
		totalCost = app.getCostoTotaleSpesaSax();
		if (totalCost == null) {
			System.out.println("How?");
		} else {
			System.out.println(totalCost);
		}
		
		Hashtable<String, Integer> categoryCount = app.getCountCategoriaSax();
		if (categoryCount == null) {
			System.out.println("How?");
		} else {
			for (String cat : categoryCount.keySet()) {
				System.out.println(cat);
				System.out.println(categoryCount.get(cat));
			}
		}
		
		
		System.out.println("fine");

	}

}
