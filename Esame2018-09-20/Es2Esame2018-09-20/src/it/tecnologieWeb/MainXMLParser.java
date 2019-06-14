

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class MainXMLParser {

	public static void main(String[] args) throws Exception {
		System.out.println("helloword");
		Ludoteca ludoteca = new Ludoteca();
		String filename1 = "C:/Repos/TecWeb/Esame2018-09-20/Es2Esame2018-09-20/ludoteca1.xml";
		String filename2 = "C:/Repos/TecWeb/Esame2018-09-20/Es2Esame2018-09-20/ludoteca2.xml";
		String outFile = "Ludoteca.txt";
		PrintWriter outWriter = new PrintWriter(outFile);
		ludoteca.parse(filename1);
		ArrayList<Cdmusicale> cds = ludoteca.getCDMusicali_Fascia03();
		
		outWriter.println("CD Musicali, fascia 0-3, Trovati: "+ cds.size());
		for (Cdmusicale item : cds) {
			System.out.println(item.toString());
			outWriter.println(item.toString());
		}
		
		ArrayList<Audiovisivo> avs = ludoteca.getAudiovisivi_Italiano();

		System.out.println("Audiovisivi Italiani, Trovati: " + avs.size());
		outWriter.println("Audiovisivi Italiani, Trovati: " + avs.size());
		for (Audiovisivo item : avs) {
			System.out.println(item.toString());
			outWriter.println(item.toString());
		}

		ArrayList<GiocattoloTradizionale> gts = ludoteca.getGiocoTradizionale_Fascia68_GI();

		outWriter.println("Giocattoli tradizionali, Fascia 6-8, Trovati: " + gts.size());
		for (GiocattoloTradizionale item : gts) {
			System.out.println(item.toString());
			outWriter.println(item.toString());
		}
        
        outWriter.close();
	}
}