package de.eldecker.lieddetails;

import static de.eldecker.lieddetails.konsole.EingabeHelfer.eingabeSchliessen;
import static de.eldecker.lieddetails.konsole.EingabeHelfer.einlesen;

import java.util.Optional;

import de.eldecker.lieddetails.ki.LiedDetailsException;
import de.eldecker.lieddetails.ki.LiedDetailsVonKI;


/**
 * Klasse mit Einstiegsmethode des Programms.
 */
public class Main {
	

	/**
	 * Einstiegsmethode für Programmausführung.
	 * 
	 * @param args Wird nicht ausgewertet
	 * 
	 * @throws LiedDetailsException Fehler bei Ausführung, z.B. API-Key nicht gesetzt
	 *                              oder Fehler bei Kommunikation mit OpenRouter.  
	 */
	public static void main( String[] args ) throws LiedDetailsException {
		
		System.out.println();
		
		final LiedDetailsVonKI liedDetailsVonKI = new LiedDetailsVonKI(); // throws LiedDetailsException
		
		System.out.println( "Programmende: Leere Eingabe für Interpret oder Titel." );
		
		while ( true ) { // Endlos-Schleife
			
			final Optional<String> interpretOptional = einlesen( "Interpret" );
			if ( interpretOptional.isEmpty() ) { break; }			
					
			final Optional<String> titelOptional = einlesen( "Titel" );
			if ( titelOptional.isEmpty() ) { break; }			
			
			final String interpret = interpretOptional.get();
			final String titel     = titelOptional.get();
			
			final String ergebnis = liedDetailsVonKI.holeLiedDetails( interpret, titel ); // Aufruf KI
			
			System.out.println( ergebnis + "\n" );		
		}
		
		eingabeSchliessen();
		
		System.out.println( "\nProgramm beendet" );
	}	
}
