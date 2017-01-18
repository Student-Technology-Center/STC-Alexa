package edu.wwu.center.studenttechnology.intentHandlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;

import edu.wwu.center.studenttechnology.util.SpeechletResponse;

public abstract class IntentHandlerBase {
    /*
     * Base class for all new intent handler classes to use
     */

    // Intents need a name
    private String name;

    public IntentHandlerBase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Intents need the following methods

    public abstract SpeechletResponse execute(Intent intent, Session session);

    public abstract SpeechletResponse handleYesResponse(Intent intent,
            Session session);

    public abstract SpeechletResponse handleNoResponse(Intent intent,
            Session session);

    public abstract SpeechletResponse handleNextIntent(Intent intent,
            Session session);
}
