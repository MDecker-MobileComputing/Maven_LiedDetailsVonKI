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
	 * siehe Liste aller Modelle für Text-Input/Output:
	 * https://openrouter.ai/models?output_modalities=text
	 */
	private static String MODEL_NAME = "meta-llama/llama-3.1-8b-instruct"; 

	/** API-Key für Zugriff auf openrouter.ai */
	private static String API_KEY = System.getenv( NAME_UMGEBUNGSVARIABLE );

	private static SimpleOpenAI sSIMPLE_OPENAI_CLIENT = null;


	/**
	 * Anfrage an KI/LLM via OpenRouter schicken, um Infos zu einem Lied zu bekommen.
	 *
	 * @param interpret Band oder Einzelkünstler, z.B. "Michael Jackson" oder "The Beatles"
	 *
	 * @param liedTitel Titel des Liedes, z.B. "Yellow Submarine"
	 *
	 * @return String mit Antwort von KI
	 *
	 * @throws LiedDetailsException API-Key nicht gefunden oder Fehler bei Kommunikation mit OpenRouter
	 */
	public static String holeLiedDetails( String interpret, String liedTitel ) throws LiedDetailsException {

		if ( API_KEY == null  || API_KEY.isBlank() ) {

			throw new LiedDetailsException(
					"Umgebungsvariable " + NAME_UMGEBUNGSVARIABLE + " nicht gesetzt." );
		}

		if ( sSIMPLE_OPENAI_CLIENT == null ) {

			sSIMPLE_OPENAI_CLIENT =
				SimpleOpenAI.builder()
					.apiKey( API_KEY )
					.baseUrl( "https://openrouter.ai/api" )
					.build();
		}

		final UserMessage userMessage =
				UserMessage.of( "Welche Informationen gibt es zum Lied 'Africa' von Toto?" );

        final ChatRequest chatRequest = ChatRequest.builder()
                .model( MODEL_NAME )
                .message( userMessage )
                .build();

        System.out.println( "Sende Anfrage an KI ..." );
        
        String antwort = sSIMPLE_OPENAI_CLIENT.chatCompletions()
                .create( chatRequest )
                .join()
                .firstContent();

		return antwort;
	}
}
