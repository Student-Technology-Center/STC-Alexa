package edu.wwu.center.studenttechnology.intentHandlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.ui.SsmlOutputSpeech;

import edu.wwu.center.studenttechnology.util.SpeechletResponse;

public class YeahBoiIntent extends IntentHandlerBase {

    public YeahBoiIntent(String name) {
        super(name);
    }

    @Override
    public SpeechletResponse execute(Intent intent, Session session) {
        String response = "<speak>" + "Okay! "
                + "<audio src='https://s3.amazonaws.com/number.one.bucket/longestYeahBoi.mp3'/>"
                + "</speak>";

        SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
        outputSpeech.setSsml(response);

        return SpeechletResponse.newTellResponse(outputSpeech);
    }

    @Override
    public SpeechletResponse handleYesResponse(Intent intent, Session session) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SpeechletResponse handleNoResponse(Intent intent, Session session) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SpeechletResponse handleNextIntent(Intent intent, Session session) {
        // TODO Auto-generated method stub
        return null;
    }

}
