package edu.wwu.center.studenttechnology.intentHandlers;

import com.amazon.speech.slu.Intent;

import edu.wwu.center.studenttechnology.util.SpeechletResponse;

public abstract class IntentHandlerBase {
    public IntentHandlerBase() {

    }

    public abstract SpeechletResponse execute(Intent intent);

    public abstract SpeechletResponse handleYesResponse(Intent intent);

    public abstract SpeechletResponse handleNoResponse(Intent intent);
    
    public abstract SpeechletResponse handleNextIntent(Intent intent);
}
