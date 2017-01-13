package edu.wwu.center.studenttechnology.intentHandlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;

public class STCInformationIntentHandler extends IntentHandlerBase {

    @Override
    public SpeechletResponse execute(Intent intent) {
        String helpMessage = "The Student Technology Center supports the "
                + "advancement of student knowledge of technology from fundamental "
                + "skills to advanced applications. The STC is a place where "
                + "students attend workshops, schedule peer tutoring, and make use "
                + "of manuals, tutorials and other advanced equipment and software "
                + "to promote their learning.";

        return SpeechletResponse
                .newTellResponse(constructOutputSpeech(helpMessage));
    }

    @Override
    public SpeechletResponse handleYesResponse(Intent intent) {
        return null;
    }

    @Override
    public SpeechletResponse handleNoResponse(Intent intent) {
        return null;
    }
}
