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
	 * Methode, um Interpret oder Titel des Lieds von Tastatur einzulesen.
	 * 
	 * @param nameAttribut "Interpret" oder "Titel"
	 * 
	 * @return Optional enthält eingegebenen String, wenn Nutzer etwas eingegeben hat;
	 *         wenn das Optional leer ist, dann soll das Programm beendet werden.
	 */
	public static Optional<String> einlesen( String nameAttribut ) {

		System.out.print( "\nBitte " + nameAttribut + " eingeben: " );
		
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
