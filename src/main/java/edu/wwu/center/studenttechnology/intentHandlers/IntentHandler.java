package edu.wwu.center.studenttechnology.intentHandlers;

import java.util.HashMap;
import java.util.Map;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;

import edu.wwu.center.studenttechnology.util.SessionUtil;
import edu.wwu.center.studenttechnology.util.SpeechletResponse;

public class IntentHandler {
    private final Map<String, IntentHandlerBase> intentMap;

    public IntentHandler() {
        intentMap = new HashMap<String, IntentHandlerBase>();
    }

    public SpeechletResponse update(Intent intent, Session session) {
        // Get the intent handler name from our intent
        String intentHandlerName = intent.getName();

        // Gets the intent to handle the next yes no situation (if applicable)
        String intentToHandleNextYesNo = SessionUtil
                .returnIntentToHandleNextYesNo(session);
        // Gets the intent to handle the next intent (if applicable)
        String intentToHandleNextEvent = SessionUtil
                .returnIntentToHandleNextEvent(session);

        // If there is an intent registered to handle the next yes no event, we
        // set our intentHandlerName to it.
        intentHandlerName = (intentToHandleNextYesNo == null)
                ? intentHandlerName : intentToHandleNextYesNo;
        // If there is an intent registered to handle the next event no matter
        // what, we set our intentHandlerName to it.
        intentHandlerName = (intentToHandleNextEvent == null)
                ? intentHandlerName : intentToHandleNextEvent;

        // We set our attributes to null after doing our checks
        SessionUtil.setAttributesToNull(session);

        // We get the corresponding intentHandler based on our previous checks
        IntentHandlerBase intentHandler = intentMap.get(intentHandlerName);

        // If there was a intent registered to handle the next event, we run
        // that method
        if (intentToHandleNextEvent != null) {
            return intentHandler.handleNextIntent(intent, session);
        }

        // Otherwise, if intent given to us is a yes or no, we run the
        // corresponding yes or no methods
        if (intent.getName().equals("ConfirmationIntent")) {
            return intentHandler.handleYesResponse(intent, session);
        }

        if (intent.getName().equals("NoIntent")) {
            return intentHandler.handleNoResponse(intent, session);
        }

        // If a next intent wasn't lined up and the response given to us by the
        // user isn't a yes or no, we run the base method of our intentHandlers.
        return intentHandler.execute(intent, session);
    }

    // Adds a intentHandler to our map.
    public void addIntentHandler(String intentName,
            IntentHandlerBase intentHandler) {
        intentMap.put(intentName, intentHandler);
    }
}
