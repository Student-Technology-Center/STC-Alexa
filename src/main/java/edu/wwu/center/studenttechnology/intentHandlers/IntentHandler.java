package edu.wwu.center.studenttechnology.intentHandlers;

import java.util.HashMap;
import java.util.Map;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;

public class IntentHandler {
	private final Map<String, IntentHandlerBase> intentMap;
	
	public IntentHandler() {
		intentMap = new HashMap<String, IntentHandlerBase>();
	}
	
	public SpeechletResponse update(Intent intent) {
		return executeHandler(intent.getName(), intent);
	}
	
	public void addIntentHandler(String intentName, IntentHandlerBase intentHandler) {
		intentMap.put(intentName, intentHandler);
	}
	
	public SpeechletResponse executeHandler(String intentName, Intent intent) {
		return intentMap.get(intentName).execute(intent);
	}
	
	public SpeechletResponse executeYesHandler(String intentName, Intent intent) {
		return intentMap.get(intentName).handleYesResponse(intent);
	}
	
	public SpeechletResponse executeNoHandler(String intentName, Intent intent) {
		return intentMap.get(intentName).handleNoResponse(intent);
	}
}
