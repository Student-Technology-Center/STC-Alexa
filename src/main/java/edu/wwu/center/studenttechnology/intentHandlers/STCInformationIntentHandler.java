package edu.wwu.center.studenttechnology.intentHandlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;

import edu.wwu.center.studenttechnology.util.SpeechletResponse;

public class STCInformationIntentHandler extends IntentHandlerBase {
    
    public STCInformationIntentHandler(String name) {
        super(name);
    }

    @Override
    public SpeechletResponse execute(Intent intent, Session session) {
        String helpMessage = "The Student Technology Center supports the "
                + "advancement of student knowledge of technology from fundamental "
                + "skills to advanced applications. The STC is a place where "
                + "students attend workshops, schedule peer tutoring, and make use "
                + "of manuals, tutorials and other advanced equipment and software "
                + "to promote their learning.";

        return SpeechletResponse.newTellResponse(helpMessage);
    }

    @Override
    public SpeechletResponse handleYesResponse(Intent intent, Session session) {
        return null;
    }

    @Override
    public SpeechletResponse handleNoResponse(Intent intent, Session session) {
        return null;
    }

    @Override
    public SpeechletResponse handleNextIntent(Intent intent, Session session) {
        // TODO Auto-generated method stub
        return null;
    }
}
