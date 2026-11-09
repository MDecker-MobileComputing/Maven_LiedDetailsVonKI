package de.eldecker.lieddetails;

import java.util.Optional;
import java.util.Scanner;


/**
 * Diese Klasse kapselt den Code für das Einlesen von Interpret
 * und Lied-Titel von der Tastatur.
 */
public class EingabeHelfer {

	/** Objekt zum Einlesen der Tastatureingabe. */
	private static final Scanner sScanner = new Scanner( System.in );
	
	
	/**
	 * Methode, um Interpret oder Titel des Lieds einzulesen
	 * 
	 * @param feld "Interpret" oder "Titel"
	 * 
	 * @return Optional enthält eingegebenen String, wenn Nutzer etwas eingegeben hat.
	 */
	public static Optional<String> einlesen( String feld ) {

		System.out.print( "\nBitte " + feld + " eingeben: " );
		
        String eingabeZeile = sScanner.nextLine();

        if (eingabeZeile == null) {

        	return Optional.empty();
        }

        eingabeZeile = eingabeZeile.trim();
        if ( eingabeZeile.isEmpty() ) {

        	return Optional.empty();
        }

        return Optional.of( eingabeZeile );
	}
	
	
    /**
     * Scanner für Einlesen von Tastatur schließen. Sollte am besten
     * unmittelbar vor Ende des Programms aufgerufen werden.
     */
    public static void eingabeSchliessen() {
    	
    		sScanner.close();
    }	
}
