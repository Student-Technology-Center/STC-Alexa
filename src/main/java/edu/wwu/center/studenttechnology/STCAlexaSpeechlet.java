package edu.wwu.center.studenttechnology;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;

import edu.wwu.center.studenttechnology.util.Workshop;
import edu.wwu.center.studenttechnology.util.WorkshopJsonParser;
import edu.wwu.center.studenttechnology.util.speech.assets.SampleUtteranceHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.RepositoryIdHelper;
import org.slf4j.Logger;

public class STCAlexaSpeechlet implements Speechlet {
    private final Logger log;
    private final Map<String, ISpeechCommand> commandDict;
    private final SampleUtteranceHandler utteranceHandler;

    private WorkshopJsonParser workshopJsonParser;

    public STCAlexaSpeechlet(Logger log) {
        this.log = log;
        this.commandDict = new HashMap<String, ISpeechCommand>();
        workshopJsonParser = new WorkshopJsonParser();
        utteranceHandler = new SampleUtteranceHandler();

        commandDict.put("ConfirmationIntent", new defaultYesResponse());
    }

    // Called when a new session with our skill is started
    @Override
    public void onSessionStarted(SessionStartedRequest request, Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestID={}, sessionId={}",
                request.getRequestId(), session.getSessionId());
        // Any initialization logic here
    }

    // Called when the skill (our application) is ended
    @Override
    public void onSessionEnded(SessionEndedRequest request, Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestID={}, sessionID={}",
                request.getRequestId(), session.getSessionId());
        // Any cleanup logic here
    }

    // Called when the skill (our application) is prompted, but not specifically
    // given an intent.
    //
    // For example 'Alexa STC' rather than 'Alexa, what are the STC workshops'
    @Override
    public SpeechletResponse onLaunch(LaunchRequest request, Session session)
            throws SpeechletException {
        log.info("onlaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        // For now, we just give them info on the STC
        return SpeechletResponse.newTellResponse(
                constructOutputSpeech("Succesfully launched the STC"));
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        workshopJsonParser.checkForUpdate();

        // Not sure when its possible for an intent to be null, but better safe
        // than sorry
        if (intent == null) {
            throw new SpeechletException("Invalid Intent");
        }

        // Depending on what our 'intent' is, we call different methods
        switch (intent.getName()) {
            case "AMAZON.StopIntent":
                return handleStopIntent();
            case "AMAZON.CancelIntent":
                return handleStopIntent();
            case "WorkshopInformationIntent":
                return handleWorkshopIntent();
            case "WorkshopDateIntent":
                return handleWorkshopDateIntent(intent);
            case "NoIntent":
                return handleNoIntent();
            case "STCInformationIntent":
                return handleHelpIntent();
            case "ConfirmationIntent":
                return commandDict.get(intent.getName()).execute(intent);
            default:
                throw new SpeechletException("Invalid Intent");
        }
    }

    // Handle Help Intent is called when someone asks for help or information
    // about the STC. Below is how Alexa will handle that
    // TODO: Give example questions the user can ask?
    private SpeechletResponse handleHelpIntent() {
        String helpMessage = "The Student Technology Center supports the "
                + "advancement of student knowledge of technology from fundamental "
                + "skills to advanced applications. The STC is a place where "
                + "students attend workshops, schedule peer tutoring, and make use "
                + "of manuals, tutorials and other advanced equipment and software "
                + "to promote their learning.";

        return SpeechletResponse
                .newTellResponse(constructOutputSpeech(helpMessage));
    }

    // For whatever reason, the object PlainTextOutputSpeech is constructed and
    // THEN given a message. Seeing as this object serves no other purpose, this
    // method is a one-liner to achieve the same effect.
    private PlainTextOutputSpeech constructOutputSpeech(String msg) {
        PlainTextOutputSpeech output = new PlainTextOutputSpeech();
        output.setText(msg);

        return output;
    }

    private Reprompt constructReprompt(String msg) {
        PlainTextOutputSpeech plainTextOutputSpeech = constructOutputSpeech(
                msg);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(plainTextOutputSpeech);

        return reprompt;
    }

    private SpeechletResponse handleStopIntent() {
        String goodbye = "Goodbye, have a nice day!";

        return SpeechletResponse
                .newTellResponse(constructOutputSpeech(goodbye));
    }

    private SpeechletResponse handleWorkshopIntent() {
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

        return SpeechletResponse.newAskResponse(constructOutputSpeech(response),
                constructReprompt(repromptText));
    }

    public class yesToWorkshopIntent implements ISpeechCommand {
        public SpeechletResponse execute(Object... data) {
            commandDict.put("ConfirmationIntent", new defaultYesResponse());

            String response = "Okay. Which one?";
            return SpeechletResponse.newAskResponse(
                    constructOutputSpeech(response),
                    constructReprompt(response));
        }
    }

    private SpeechletResponse handleWorkshopDateIntent(Intent intent) {
        Slot workshopShopSlot = intent.getSlot("workshop");
        String workshopString = workshopShopSlot.getValue();

        workshopString = utteranceHandler.GetString(workshopString);
        Workshop workshop = workshopJsonParser.getWorkshop(workshopString);

        String response = "You asked about " + workshop.getReadableName()
                + " which is available on " + workshop.GetDates().get(0)
                + " at " + workshop.getStartTime().get(0) + " and has "
                + workshop.getSeatsRemaining().get(0) + " seats remaining";

        return SpeechletResponse
                .newTellResponse(constructOutputSpeech(response));
    }

    private SpeechletResponse handleNoIntent() {
        commandDict.put("ConfirmationIntent", new defaultYesResponse());

        return SpeechletResponse
                .newTellResponse(constructOutputSpeech("Okay."));
    }

    public class defaultYesResponse implements ISpeechCommand {
        public SpeechletResponse execute(Object... data) {
            return SpeechletResponse.newTellResponse(
                    constructOutputSpeech("I'm sorry, I don't understand"));
        }
    }
}
