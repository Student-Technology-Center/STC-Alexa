package edu.wwu.center.studenttechnology.stc.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;

import edu.wwu.center.studenttechnology.stc.alexa.framework.intent.IntentBase;
import edu.wwu.center.studenttechnology.stc.alexa.framework.speechlet.SpeechletResponse;
import edu.wwu.center.studenttechnology.stc.alexa.util.speech.SampleUtteranceHandler;
import edu.wwu.center.studenttechnology.stc.alexa.util.workshop.Workshop;
import edu.wwu.center.studenttechnology.stc.alexa.util.workshop.WorkshopJsonParser;

public class WorkshopDateIntentHandler extends IntentBase {
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
            System.out.println("Unknown Workshop: " + workshopString);
        } else if (workshop == null) {
            response = "Sorry, that workshop isn't available right now.";
        } else {
            String date = workshop.GetDates().get(0);
            response = "You asked about " + workshop.getReadableName()
                    + " which is available on " + date + " at "
                    + workshop.getStartTime(date) + ", has "
                    + workshop.getSeatsRemaining(date)
                    + " seats remaining, and is taught by "
                    + workshop.getInstructor(date);
        }

        return SpeechletResponse.newTellResponse(response);
    }
}
