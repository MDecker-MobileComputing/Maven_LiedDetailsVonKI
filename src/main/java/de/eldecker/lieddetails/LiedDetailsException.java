package de.eldecker.lieddetails;


/**
 * Eigene Exception-Klasse für diese Anwendung.
 */
@SuppressWarnings("serial")
public class LiedDetailsException extends Exception {

	/**
	 * Konstruktor.
	 * 
	 * @param fehlertext Kurze Beschreibung des Fehlers, der die
	 *                   Exception ausgelöst hat
	 */
	public LiedDetailsException( String fehlertext ) {
		
		super( fehlertext );
	}	
}
