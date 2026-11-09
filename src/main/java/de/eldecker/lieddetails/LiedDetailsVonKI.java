package de.eldecker.lieddetails;

import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatMessage.UserMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

public class LiedDetailsVonKI {
	
	private static String NAME_UMGEBUNGSVARIABLE = "OPENROUTER_API_KEY";
	
	/** 
	 * KI-Modell, an das OpenRouter weiterleiten soll.
	 * <br>
	 * 
	 * Derzeit billigstes non-free Modell von Openrouter:
	 * <tt>liquid/lfm-3-1.7b</tt>, siehe auch: https://openrouter.ai/liquid/lfm-3-1.7b
	 */
	private static String MODEL_NAME = "liquid/lfm-3-1.7b"; // billigestes?  

	/** API-Key für Zugriff auf openrouter.ai */
	private static String API_KEY = System.getenv( NAME_UMGEBUNGSVARIABLE );
	
	private static SimpleOpenAI sSIMPLE_OPENAI_CLIENT = null;
	
	
	/**
	 * 
	 * 
	 * @param interpret
	 * 
	 * @param liedTitel
	 * 
	 * @return
	 * 
	 * @throws LiedDetailsException API-Key nicht gefunden oder Fehler bei Kommunikation mit OpenRouter
	 */
	public static String holeLiedDetails( String interpret, String liedTitel ) throws LiedDetailsException {
		
		if ( API_KEY == null  || API_KEY.isBlank() ) {
			
			throw new LiedDetailsException( 
					"Umgebungsvariable " + NAME_UMGEBUNGSVARIABLE + " nicht gesetzt." );
		}
		
		if ( sSIMPLE_OPENAI_CLIENT == null ) {

			sSIMPLE_OPENAI_CLIENT = new SimpleOpenAI( API_KEY, 
								                      null, null, null, null, 
										              null, null, null, null );
		}
		
		final UserMessage userMessage = 
				UserMessage.of( "Welche Informationen gibt es zum Lied 'Africa' von Toto?" ); 
		
        final ChatRequest chatRequest = ChatRequest.builder()
                .model( MODEL_NAME )
                .message( userMessage )
                .build();
		
        String antwort = sSIMPLE_OPENAI_CLIENT.chatCompletions()
                .create( chatRequest )
                .join()
                .firstContent();        
		
		return antwort;
	}
}
