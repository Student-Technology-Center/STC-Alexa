package edu.wwu.center.studenttechnology.intentHandlers;

import java.util.Collection;

import com.amazon.speech.slu.Intent;

import edu.wwu.center.studenttechnology.util.SpeechletResponse;
import edu.wwu.center.studenttechnology.util.Workshop;
import edu.wwu.center.studenttechnology.util.WorkshopJsonParser;

public class WorkshopInformationIntentHandler extends IntentHandlerBase {
    private final WorkshopJsonParser workshopJsonParser;

    public WorkshopInformationIntentHandler(
            WorkshopJsonParser workshopJsonParser) {
        this.workshopJsonParser = workshopJsonParser;
    }

    @Override
    public SpeechletResponse execute(Intent intent) {
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

        response += ".";

        String repromptText = "Is there a workshop you wish to learn more about?";

        response += " " + repromptText;

        return SpeechletResponse.newAskResponse(response, repromptText);
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
