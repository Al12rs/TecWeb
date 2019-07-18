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
		 * if (args.length != 0) { System.out.println("usage: " +
		 * MainXMLParser.class.getSimpleName() + " xmlFilename"); exit(1); }
		 */

		String outFile = "takeaway.txt";
		PrintWriter outWriter = new PrintWriter(outFile);
		

		//ChangeMe
        xmlFilename = "resources/takeaway1.xml";
        XmlApplication app = new XmlApplication();
        
        //DOM
		
		//SAX
		System.out.println("SAX Parsing Results");
		app.parseSAX(xmlFilename);

		Integer numPortate = app.getNumeroPortateOfferte();
		if (numPortate == null) {
			System.out.println("Failure for some reason");
		} else {
			outWriter.println("Numero portate offerte: "+ numPortate);
		}
		
		outWriter.close();
		System.out.println("fine");

	}

}
