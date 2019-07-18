package it.tecnologieWeb;

import java.util.Set;
import org.w3c.dom.Document;

public class MainXMLParser {

	//private Document domDocument = null;

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

        xmlFilename = "resources/bibliotecaMusicale1.xml";
        XmlApplication app = new XmlApplication();
        
        //DOM
        System.out.println("DOM Parsing Results");
		app.parseDOM(xmlFilename);
		System.out.println("N elementi: " +app.getLength());
		Set<String> cognomi = app.getCognomi(app.getDomDocument(), "Rap", 3);
		if (cognomi == null) {
			System.out.println("How?");
		} else {
			for(String s : cognomi)
				System.out.println(s);
		}

		boolean result = app.addAlbum(app.getDomDocument(), "Pinco", "Pallino", "Rap", "RapDeity", 2017);
		
		if (!result) {
			System.out.println("How?");
		} else {
			System.out.println("OOOOOOOP!");
		}
		
		
		System.out.println("fine");

	}

}
