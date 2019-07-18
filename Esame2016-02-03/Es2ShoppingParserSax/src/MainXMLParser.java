
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import org.w3c.dom.Document;

public class MainXMLParser {

	private Document domDocument = null;
	private SAXContentHandler handler = null;

	public static void main(String[] args) throws Exception {

		String xmlFilename;

		/*
		 * //if autput is to go on a file String outFile =""; PrintWriter outWriter =
		 * new PrintWriter(outFile); outWriter.println("result:"); outWriter.close();
		 */

		/*
		 * if (args.length != 0) { System.out.println("usage: " +
		 * MainXMLParser.class.getSimpleName() + " xmlFilename"); exit(1); }
		 */

		xmlFilename = "resources/shopping1.xml";
		XmlApplication app = new XmlApplication();

		// SAX
		System.out.println("SAX Parsing Results");
		app.parseSAX(xmlFilename);

		Hashtable<String, ArrayList<CapoAbbigliamento>> scelte = app.getScelte();
		StringBuilder result = new StringBuilder();
		if (scelte == null) {
			System.out.println("How?");
		} else {
			for (String cat : scelte.keySet()) {
				result.append(cat + "\n");
				for (CapoAbbigliamento ca : scelte.get(cat)) {
					result.append(ca.toString() + "\n");
				}
			}
		}

		// stampa
		PrintWriter outWriter = new PrintWriter("Shopping.txt");
		outWriter.println(result.toString());
		outWriter.close();
		System.out.println("fine");

	}

}
