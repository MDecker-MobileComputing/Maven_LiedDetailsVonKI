package de.eldecker.lieddetails;

import static de.eldecker.lieddetails.LiedDetailsVonKI.holeLiedDetails;

public class Main {

	public static void main( String[] args ) throws Exception {
		
		System.out.println();
		
		final String ergebnis = holeLiedDetails( "Iron Maiden", "The Number of The Beast" );
		
		System.out.println( ergebnis + "\n" );
	}
	
}
