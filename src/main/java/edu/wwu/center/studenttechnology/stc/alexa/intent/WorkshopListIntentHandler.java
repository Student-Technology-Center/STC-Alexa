package edu.wwu.center.studenttechnology.stc.alexa.intent;

import java.util.Collection;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;

import edu.wwu.center.studenttechnology.stc.alexa.framework.intent.IntentBase;
import edu.wwu.center.studenttechnology.stc.alexa.framework.speechlet.SpeechletResponse;
import edu.wwu.center.studenttechnology.stc.alexa.util.workshop.Workshop;
import edu.wwu.center.studenttechnology.stc.alexa.util.workshop.WorkshopJsonParser;

public class WorkshopListIntentHandler extends IntentBase {
    private final WorkshopJsonParser workshopJsonParser;

    public WorkshopListIntentHandler(String name,
            WorkshopJsonParser workshopJsonParser) {
        super(name);
        this.workshopJsonParser = workshopJsonParser;
    }

    @Override
    public SpeechletResponse execute(Intent intent, Session session) {
        workshopJsonParser.checkForUpdate();
        
        Collection<Workshop> workshopCollection = workshopJsonParser
                .getWorkshopCollection();

        String response = "Currently we have workshops on ";

        boolean firstIteration = true;

        for (Workshop workshop : workshopCollection) {
            if (!firstIteration) {
                response += ", ";
            }

            response += workshop.getReadableName();

            firstIteration = false;
        }

        return SpeechletResponse.newTellResponse(response);
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
        return null;
    }
}
