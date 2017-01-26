package edu.wwu.center.studenttechnology.intentHandlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;

import edu.wwu.center.studenttechnology.util.SpeechletResponse;
import edu.wwu.center.studenttechnology.util.Workshop;
import edu.wwu.center.studenttechnology.util.WorkshopJsonParser;
import edu.wwu.center.studenttechnology.util.speech.assets.SampleUtteranceHandler;

public class WorkshopInformationIntent extends IntentHandlerBase {
    private final WorkshopJsonParser workshopJsonParser;
    private final SampleUtteranceHandler sampleUtteranceHandler;

    public WorkshopInformationIntent(String name,
            WorkshopJsonParser workshopJsonParser,
            SampleUtteranceHandler sampleUtteranceHandler) {
        super(name);
        this.workshopJsonParser = workshopJsonParser;
        this.sampleUtteranceHandler = sampleUtteranceHandler;
    }

    @Override
    public SpeechletResponse execute(Intent intent, Session session) {
        Slot workshopShopSlot = intent.getSlot("workshop");
        String workshopString = workshopShopSlot.getValue();

        workshopJsonParser.checkForUpdate();

        workshopString = sampleUtteranceHandler.GetString(workshopString);

        Workshop workshop = workshopJsonParser.getWorkshop(workshopString);

        String response;

        if (workshopString == null) {

            System.out.println("Unknown Workshop: " + workshopString);
            response = "I'm sorry, I don't know that workshop.";
        } else if (workshop == null) {
            response = "Sorry, that workshop isn't available right now.";
        } else {
            response = workshop.getDescription();

            if (response.equals("") || response.equals(" ")) {
                response = "Sorry, there's not a description for "
                        + workshopString + " yet.";
            }

            System.out.println("|" + workshop.getPrerequisites() + "|");

            if (workshop.getPrerequisites().equals("")
                    || workshop.getPrerequisites().equals(" ")) {
                return SpeechletResponse.newTellResponse(response);
            }
            response += " Prerequisites for this workshop are "
                    + workshop.getPrerequisites();
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
