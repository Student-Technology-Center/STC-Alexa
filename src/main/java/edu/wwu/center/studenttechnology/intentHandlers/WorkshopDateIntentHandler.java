package edu.wwu.center.studenttechnology.intentHandlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;

import edu.wwu.center.studenttechnology.util.SpeechletResponse;
import edu.wwu.center.studenttechnology.util.Workshop;
import edu.wwu.center.studenttechnology.util.WorkshopJsonParser;
import edu.wwu.center.studenttechnology.util.speech.assets.SampleUtteranceHandler;

public class WorkshopDateIntentHandler extends IntentHandlerBase {
    private final WorkshopJsonParser workshopJsonParser;
    private final SampleUtteranceHandler sampleUtteranceHandler;

    public WorkshopDateIntentHandler(String name,
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
            response = "Sorry, I don't understand what that workshop is, please let a STC member know if this is a mistake";
            System.out.println("Unknown Workshop: " + workshop);
        } else {
            String date = workshop.GetDates().get(0);
            response = "You asked about " + workshop.getReadableName()
                    + " which is available on " + date + " at "
                    + workshop.getStartTime(date) + " and has "
                    + workshop.getSeatsRemaining(date) + " seats remaining";
        }

        return SpeechletResponse.newTellResponse(response);
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
