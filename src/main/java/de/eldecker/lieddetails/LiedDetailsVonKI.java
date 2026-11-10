package de.eldecker.lieddetails;

import java.util.HashMap;

import io.github.sashirestela.cleverclient.client.JavaHttpClientAdapter;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatMessage.UserMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;


/**
 * Klasse, die die Kommunikation mit der KI/LLM über OpenRouter kapselt.
 */
public class LiedDetailsVonKI {

	/**
	 * Name der Umgebungsvariable mit dem API-Key für OpenRouter.
	 */
	private static final String NAME_UMGEBUNGSVARIABLE = "OPENROUTER_API_KEY";

	/**
	 * Template mit Prompt, der Platzhalter für Titel und Interpret enthält.
	 */
	private static final String PROMPT_TEMPLATE =
			"""
			In welchem Jahr und auf welchem Album wurde das Lied "{{LIED_TITEL}}" von "{{INTERPRET}}" veröffentlicht?
			Aus welchem Land ist der Interpret?
			Wer hat das Lied geschrieben?
			Welcher Stilrichtung ist es zuzuordnen?
			Was bedeutet der Text?
			Gibt es Wissenswertes zu diesem Lied, z.B. bekannte Cover-Versionen oder ist das Lied selbst eine Cover-Version?
			Beantworte diese Fragen, ohne Formatierungen wie Markdown zu verwenden.
			""";

	/**
	 * KI-Modell, an das OpenRouter weiterleiten soll, in Form
	 * <pre>{provider}/{model}</pre>, z.B. <pre>deepseek/deepseek-v4.1-flash-1017</pre>.
	 * <br>
	 *
	 * Siehe die Liste aller verfügbaren Modelle für Text-Input/Output unter:
	 * https://openrouter.ai/models?output_modalities=text&amp;order=pricing-low-to-high
	 * <br>
	 *
	 * Achtung: Die Output-Token sind meistens deutlich teurer als die Input-Token.
	 */
	private static final String MODEL_NAME = "mistralai/mistral-small-24b-instruct-2501";
	//private static final String MODEL_NAME = "meta-llama/llama-3.1-8b-instruct";
	//private static final String MODEL_NAME = "openrouter/free"; // kostenloses Modell verwenden (OpenRouter.ai wählt aus)


	/**
	 * Client-Objekt, das u.a. den API-Key kapselt und für das Absenden der
	 * einzelnen Anfragen an die KI benötigt wird.
	 */
	private final SimpleOpenAI _simpleOpenAIClient;


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

		final JavaHttpClientAdapter httpAdapter = new JavaHttpClientAdapter();

		_simpleOpenAIClient = SimpleOpenAI.builder()
				                          .apiKey( apiKey )
				                          .baseUrl( "https://openrouter.ai/api" )
				                          .clientAdapter( httpAdapter )
				                          .build();

		// HTTP-Header für Zuordnung der Requests in Auswertungen von OpenRouter setzen
		// (Spalte "App" unter "Logs")
		httpAdapter.setRequestInterceptor( request -> {

		    final HashMap<String, String> headers = new HashMap<>( request.getHeaders() );		    
		    headers.put( "X-OpenRouter-Title", "Lied-Details (Maven)"         );
		    headers.put( "HTTP-Referer"      , "https://tinyurl.com/4vt85vn6" ); // Kurz-URL für GitHub-Repo der App
		    request.setHeaders( headers );
		    return request;
		});
	}


	/**
	 * Anfrage an KI/LLM via OpenRouter schicken, um Infos zu einem Lied zu bekommen.
	 *
	 * @param interpret Band oder Einzelkünstler, z.B. "Michael Jackson" oder "The Beatles"
	 *
	 * @param titelLied Titel des Liedes, z.B. "Yellow Submarine"
	 *
	 * @return String mit Antwort von KI
	 *
	 * @throws java.util.concurrent.CompletionException Fehler bei Kommunikation mit OpenRouter
	 */
	public String holeLiedDetails( String interpret, String titelLied ) {

		final String prompt = PROMPT_TEMPLATE.replace( "{{INTERPRET}}" , interpret )
		                                     .replace( "{{LIED_TITEL}}", titelLied );

		final UserMessage userMessage = UserMessage.of( prompt );

        final ChatRequest chatRequest = ChatRequest.builder()
        		                                   .model( MODEL_NAME )
                                                   .message( userMessage )
                                                   .build();

        System.out.println( "\nSende Anfrage an KI ...\n" );
        
        final long zeitStart = System.nanoTime();

        final String antwort = _simpleOpenAIClient.chatCompletions()
                                                  .create( chatRequest )
                                                  .join()
                                                  .firstContent();
        
        final long zeitStopp = System.nanoTime();
        
        final long laufzeit = ( zeitStopp - zeitStart ) / 1_000_000;
        
        System.out.println( "Dauer der Anfrage: " + laufzeit + " ms\n" );
        
		return antwort;
	}
}
