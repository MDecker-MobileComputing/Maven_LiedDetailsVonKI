package de.eldecker.lieddetails;

public class Main {

	public static void main( String[] args ) throws Exception {
		
		System.out.println();
		
		final LiedDetailsVonKI liedDetailsVonKI = new LiedDetailsVonKI();
		final String ergebnis = liedDetailsVonKI.holeLiedDetails( "Jimi Hendrix", "All Along The Watchtower" );
		
		System.out.println( ergebnis + "\n" );
	}
	
}
