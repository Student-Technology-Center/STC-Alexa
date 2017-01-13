package edu.wwu.center.studenttechnology.intentHandlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;

public abstract class IntentHandlerBase {
    public IntentHandlerBase() {

    }

    public abstract SpeechletResponse execute(Intent intent);

    public abstract SpeechletResponse handleYesResponse(Intent intent);

    public abstract SpeechletResponse handleNoResponse(Intent intent);

    protected PlainTextOutputSpeech constructOutputSpeech(String msg) {
        PlainTextOutputSpeech output = new PlainTextOutputSpeech();
        output.setText(msg);

        return output;
    }

    protected Reprompt constructReprompt(String msg) {
        PlainTextOutputSpeech plainTextOutputSpeech = constructOutputSpeech(
                msg);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(plainTextOutputSpeech);

        return reprompt;
    }
}
