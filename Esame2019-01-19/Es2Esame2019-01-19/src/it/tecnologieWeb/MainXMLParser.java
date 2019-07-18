package it.tecnologieWeb;



import java.io.*;


public class MainXMLParser {

	public static void main(String[] args) throws Exception {

		String xmlFilename;
		String outFile ="resources/Takeaway.txt";
		/*
		//if output is to go on a file
		String outFile ="";
		PrintWriter outWriter = new PrintWriter(outFile);
		outWriter.println("result:");
		outWriter.close();
		*/

        xmlFilename = "resources/ristoranti1.xml";
        XmlApplication app = new XmlApplication();
        
		
		//SAX
		System.out.println("SAX Parsing Results");
		app.parseSAX(xmlFilename);
		int numeroPortate = app.getNumeroPortateOfferte();
		System.out.println("Numero portate offerte: "+numeroPortate+".\n");
		System.out.println("Saving results to file...");
		
		PrintWriter outWriter = new PrintWriter(outFile);
		outWriter.println("Numero portate offerte: "+numeroPortate+".\n");
		outWriter.close();
		
		System.out.println("fine");

	}

}
