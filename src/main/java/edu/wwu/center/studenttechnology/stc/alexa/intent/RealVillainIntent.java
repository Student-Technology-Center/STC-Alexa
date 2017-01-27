package edu.wwu.center.studenttechnology.stc.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.ui.SsmlOutputSpeech;

import edu.wwu.center.studenttechnology.stc.alexa.framework.intent.IntentBase;
import edu.wwu.center.studenttechnology.stc.alexa.framework.speechlet.SpeechletResponse;
import edu.wwu.center.studenttechnology.stc.alexa.framework.util.SessionUtil;

public class RealVillainIntent extends IntentBase {
    private final String NUMBER_OF_NO_ATTRIBUTE = "NUMBER_OF_NO_ATTRIBUTE";

    public RealVillainIntent(String name) {
        super(name);
    }

    @Override
    public SpeechletResponse execute(Intent intent, Session session) {
        String response = "Are you a real villain?";

        SessionUtil.setIntentToHandleNextYesNo(session, getName());

        return SpeechletResponse.newAskResponse(response, response);
    }

    @Override
    public SpeechletResponse handleYesResponse(Intent intent, Session session) {
        String response = "Great! You are a true villain!";

        return SpeechletResponse.newTellResponse(response);
    }

    @Override
    public SpeechletResponse handleNoResponse(Intent intent, Session session) {

        Object obj = session.getAttribute(NUMBER_OF_NO_ATTRIBUTE);

        int numberOfNo = obj == null ? 1 : ((int) obj) + 1;
        session.setAttribute(NUMBER_OF_NO_ATTRIBUTE, numberOfNo);

        switch (numberOfNo) {
            case 1:
                String firstNo = "Have you ever caught a good guy, Like a real super hero?";
                SessionUtil.setIntentToHandleNextYesNo(session, getName());
                return SpeechletResponse.newAskResponse(firstNo, firstNo);
            case 2:
                String secondNo = "Have you ever tried a disguise?";
                SessionUtil.setIntentToHandleNextYesNo(session, getName());
                return SpeechletResponse.newAskResponse(secondNo, secondNo);
        }

        String smmlResponse = "<speak>"
                + "I can see that I will have to teach you to be a villain! "
                + "<audio src='https://s3.amazonaws.com/number.one.bucket/numberOneAlexa.mp3'/>"
                + "</speak>";

        SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
        outputSpeech.setSsml(smmlResponse);

        return SpeechletResponse.newTellResponse(outputSpeech);
    }

    @Override
    public SpeechletResponse handleNextIntent(Intent intent, Session session) {
        return null;
    }

}
