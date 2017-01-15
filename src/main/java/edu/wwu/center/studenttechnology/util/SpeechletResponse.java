package edu.wwu.center.studenttechnology.util;

import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;

public class SpeechletResponse
        extends com.amazon.speech.speechlet.SpeechletResponse {
    
    private boolean handleNextYesNoEvent;
    private boolean handleNextEvent;
    
    public SpeechletResponse() {
        handleNextYesNoEvent = false;
        handleNextEvent = false;
    }
    
    /*
     * A lot of this code will be direct copy of Amazon's original
     * SpeechletResponse. This is so that we can repackage what's here and use
     * it for other means. It also allows us to do easier versions of amazon's
     * methods.
     */

    // This constructs a tell response with only 2 strings
    public static SpeechletResponse newTellResponse(String outputSpeech) {
        PlainTextOutputSpeech output = new PlainTextOutputSpeech();
        output.setText(outputSpeech);

        return newTellResponse(output);
    }

    // This constructs an ask response with only 2 strings
    public static SpeechletResponse newAskResponse(String outputSpeech,
            String repromptSpeech) {
        PlainTextOutputSpeech output = new PlainTextOutputSpeech();
        output.setText(outputSpeech);

        PlainTextOutputSpeech repromptOutput = new PlainTextOutputSpeech();
        output.setText(repromptSpeech);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptOutput);

        return newAskResponse(output, reprompt);
    }
    
    public void handleNextYesNoIntent(boolean value) {
        handleNextYesNoEvent = value;
    }
    
    public void handleNextEvent(boolean value) {
        handleNextEvent = value;
    }
    
    public boolean getHandleNextYesNoIntent() {
        return handleNextYesNoEvent;
    }
    
    public boolean getHandleNextEvent() {
        return handleNextEvent;
    }

    /*
     * Below is all copied Amazon code.
     */

    public static SpeechletResponse newTellResponse(
            final OutputSpeech outputSpeech) {
        if (outputSpeech == null) {
            throw new IllegalArgumentException("OutputSpeech cannot be null");
        }

        SpeechletResponse response = new SpeechletResponse();
        response.setShouldEndSession(true);
        response.setOutputSpeech(outputSpeech);
        return response;
    }

    public static SpeechletResponse newTellResponse(
            final OutputSpeech outputSpeech, final Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }

        SpeechletResponse response = newTellResponse(outputSpeech);
        response.setCard(card);
        return response;
    }

    public static SpeechletResponse newAskResponse(
            final OutputSpeech outputSpeech, final Reprompt reprompt) {
        if (outputSpeech == null) {
            throw new IllegalArgumentException("OutputSpeech cannot be null");
        }

        if (reprompt == null) {
            throw new IllegalArgumentException("Reprompt cannot be null");
        }

        SpeechletResponse response = new SpeechletResponse();
        response.setShouldEndSession(false);
        response.setOutputSpeech(outputSpeech);
        response.setReprompt(reprompt);
        return response;
    }

    public static SpeechletResponse newAskResponse(
            final OutputSpeech outputSpeech, final Reprompt reprompt,
            final Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }

        SpeechletResponse response = newAskResponse(outputSpeech, reprompt);
        response.setCard(card);
        return response;
    }
}
