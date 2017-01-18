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
        String intentHandlerName = intent.getName();
        String intentToHandleNextYesNo = SessionUtil.returnIntentToHandleNextYesNo(session);
        String intentToHandleNextEvent = SessionUtil.returnIntentToHandleNextEvent(session);
        
        intentHandlerName = (intentToHandleNextYesNo == null) ? intentHandlerName : intentToHandleNextYesNo;
        intentHandlerName = (intentToHandleNextEvent == null) ? intentHandlerName : intentToHandleNextEvent;
        
        SessionUtil.setAttributesToNull(session);
        
        IntentHandlerBase intentHandler = intentMap.get(intentHandlerName);
        
        if (intentToHandleNextEvent != null) {
            return intentHandler.handleNextIntent(intent, session);
        }
        
        if (intent.getName().equals("ConfirmationIntent")) {
            return intentHandler.handleYesResponse(intent, session);
        }
        
        if (intent.getName().equals("NoIntent")) {
            return intentHandler.handleNoResponse(intent, session);
        }
        
        return intentHandler.execute(intent, session);
    }

    public void addIntentHandler(String intentName,
            IntentHandlerBase intentHandler) {
        intentMap.put(intentName, intentHandler);
    }
}
