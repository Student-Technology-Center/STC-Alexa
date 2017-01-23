package edu.wwu.center.studenttechnology.intentHandlers;

import java.util.Collection;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;

import edu.wwu.center.studenttechnology.util.SpeechletResponse;
import edu.wwu.center.studenttechnology.util.Workshop;
import edu.wwu.center.studenttechnology.util.WorkshopJsonParser;

public class WorkshopInformationIntentHandler extends IntentHandlerBase {
    private final WorkshopJsonParser workshopJsonParser;

    public WorkshopInformationIntentHandler(String name,
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

        response += ". Please say 'Ask STC tell me more about workshop' if you are curious about a specific workshop.";

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
