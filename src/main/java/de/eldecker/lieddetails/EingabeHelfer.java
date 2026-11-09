package de.eldecker.lieddetails;

import java.util.Optional;
import java.util.Scanner;


/**
 * Diese Klasse kapselt den Code für das Einlesen von Interpret
 * und Lied-Titel von der Tastatur.
 */
public class EingabeHelfer {

	/**
	 * Scanner ist eine Java-Klasse, die Text aus einer Eingabequelle liest und
	 * in einzelne Werte zerlegen kann. Dieses Scanner-Objekt liest Textzeilen
	 * aus dem Standardeingabestrom {@link System#in}, also üblicherweise von
	 * der Tastatur.
	 */
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
     * Scanner für Einlesen von Tastatur schließen. Sollte
     * unmittelbar vor Ende des Programms aufgerufen werden.
     */
    public static void eingabeSchliessen() {

    		sScanner.close();
    }
}
