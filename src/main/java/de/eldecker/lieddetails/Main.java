package de.eldecker.lieddetails;

public class Main {

	public static void main( String[] args ) throws Exception {
		
		System.out.println();
		
		final LiedDetailsVonKI liedDetailsVonKI = new LiedDetailsVonKI();
		final String ergebnis = liedDetailsVonKI.holeLiedDetails( "Iron Maiden", "The Number of The Beast" );
		
		System.out.println( ergebnis + "\n" );
	}
	
}
