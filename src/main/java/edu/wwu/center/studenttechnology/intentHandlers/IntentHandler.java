package edu.wwu.center.studenttechnology.intentHandlers;

import java.util.HashMap;
import java.util.Map;

import com.amazon.speech.slu.Intent;

import edu.wwu.center.studenttechnology.util.SpeechletResponse;

public class IntentHandler {
    private final Map<String, IntentHandlerBase> intentMap;

    public IntentHandler() {
        intentMap = new HashMap<String, IntentHandlerBase>();
    }

    public SpeechletResponse update(String intentHandlerName, Intent intent) {
        return executeHandler(intentHandlerName, intent);
    }

    public void addIntentHandler(String intentName,
            IntentHandlerBase intentHandler) {
        intentMap.put(intentName, intentHandler);
    }

    // I dont know how I feel about this code, it feels sloppy
    public SpeechletResponse executeHandler(String intentHandlerName,
            Intent intent) {
        if (intent.getName().equals("ConfirmationIntent")) {
            return executeYesHandler(intentHandlerName, intent);
        } else if (intent.getName().equals("NoIntent")) {
            return executeNoHandler(intentHandlerName, intent);
        }

        return intentMap.get(intentHandlerName).execute(intent);
    }

    public SpeechletResponse executeYesHandler(String intentHandlerName,
            Intent intent) {
        return intentMap.get(intentHandlerName).handleYesResponse(intent);
    }

    public SpeechletResponse executeNoHandler(String intentHandlerName,
            Intent intent) {
        return intentMap.get(intentHandlerName).handleNoResponse(intent);
    }
}
