package de.eldecker.lieddetails;

import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatMessage.UserMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

public class LiedDetailsVonKI {

	private static final String NAME_UMGEBUNGSVARIABLE = "OPENROUTER_API_KEY";

	/**
	 * KI-Modell, an das OpenRouter weiterleiten soll.
	 * <br>
	 * 
	 * siehe Liste aller Modelle für Text-Input/Output:
	 * https://openrouter.ai/models?output_modalities=text
	 */
	private static final String MODEL_NAME = "meta-llama/llama-3.1-8b-instruct";

	private final SimpleOpenAI simpleOpenAIClient;

	
	/**
	 * Erstellt einen Client, der für mehrere Anfragen verwendet werden kann.
	 *
	 * @throws LiedDetailsException API-Key nicht gefunden
	 */
	public LiedDetailsVonKI() throws LiedDetailsException {

		final String apiKey = System.getenv( NAME_UMGEBUNGSVARIABLE );
		if ( apiKey == null || apiKey.isBlank() ) {

			throw new LiedDetailsException(
					"Umgebungsvariable " + NAME_UMGEBUNGSVARIABLE + " nicht gesetzt." );
		}

		simpleOpenAIClient = SimpleOpenAI.builder()
				.apiKey( apiKey )
				.baseUrl( "https://openrouter.ai/api" )
				.build();
	}


	/**
	 * Anfrage an KI/LLM via OpenRouter schicken, um Infos zu einem Lied zu bekommen.
	 *
	 * @param interpret Band oder Einzelkünstler, z.B. "Michael Jackson" oder "The Beatles"
	 *
	 * @param liedTitel Titel des Liedes, z.B. "Yellow Submarine"
	 *
	 * @return String mit Antwort von KI
	 *
	 * @throws java.util.concurrent.CompletionException Fehler bei Kommunikation mit OpenRouter
	 */
	public String holeLiedDetails( String interpret, String liedTitel ) {

		final UserMessage userMessage =
				UserMessage.of( "Welche Informationen gibt es zum Lied '" + liedTitel
						+ "' von " + interpret + "?" );

        final ChatRequest chatRequest = ChatRequest.builder()
                .model( MODEL_NAME )
                .message( userMessage )
                .build();

        System.out.println( "Sende Anfrage an KI ..." );
        
        String antwort = simpleOpenAIClient.chatCompletions()
                .create( chatRequest )
                .join()
                .firstContent();

		return antwort;
	}
}
