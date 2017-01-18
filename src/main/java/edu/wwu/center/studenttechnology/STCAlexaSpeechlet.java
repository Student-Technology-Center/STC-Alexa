package edu.wwu.center.studenttechnology;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;

import edu.wwu.center.studenttechnology.intentHandlers.ExampleYesNoIntent;
import edu.wwu.center.studenttechnology.intentHandlers.IntentHandler;
import edu.wwu.center.studenttechnology.intentHandlers.STCInformationIntentHandler;
import edu.wwu.center.studenttechnology.intentHandlers.WorkshopDateIntentHandler;
import edu.wwu.center.studenttechnology.intentHandlers.WorkshopInformationIntentHandler;
import edu.wwu.center.studenttechnology.util.SpeechletResponse;
import edu.wwu.center.studenttechnology.util.WorkshopJsonParser;
import edu.wwu.center.studenttechnology.util.speech.assets.SampleUtteranceHandler;

import org.slf4j.Logger;

public class STCAlexaSpeechlet implements Speechlet {
    private final Logger log;
    private final SampleUtteranceHandler sampleUtteranceHandler;
    private final IntentHandler intentHandler;
    private final WorkshopJsonParser workshopJsonParser;

    public STCAlexaSpeechlet(Logger log) {
        this.log = log;

        // Workshop JSON Parser is how some intents transcribe a JSON full with
        // information about workshops. More detailed in it's class
        workshopJsonParser = new WorkshopJsonParser();

        // SampleUtteranceHandler is used to make sure we're understanding the
        // user when they're trying to say a workshop, due to the various names
        // and abbreviations workshops may have. See class for more details
        sampleUtteranceHandler = new SampleUtteranceHandler();

        // IntentHandler is used to easily point the code to the correct handler
        // when Alexa is called. See class for more details
        intentHandler = new IntentHandler();

        // Construction of each intenthandler is done below, arguments for each
        // constructor is up to the individual class, but each at least needs a
        // name to keep things consistent
        WorkshopInformationIntentHandler workshopInformationHandler = new WorkshopInformationIntentHandler(
                "WorkshopInformationIntent", workshopJsonParser);
        STCInformationIntentHandler stcInformationHandler = new STCInformationIntentHandler(
                "STCInformationIntent");
        WorkshopDateIntentHandler workshopDateHandler = new WorkshopDateIntentHandler(
                "WorkshopDateIntent", workshopJsonParser,
                sampleUtteranceHandler);
        ExampleYesNoIntent testIntent = new ExampleYesNoIntent("TestIntent");

        // Register the intent handlers here
        intentHandler.addIntentHandler(workshopInformationHandler.getName(),
                workshopInformationHandler);
        intentHandler.addIntentHandler(stcInformationHandler.getName(),
                stcInformationHandler);
        intentHandler.addIntentHandler(workshopDateHandler.getName(),
                workshopDateHandler);
        intentHandler.addIntentHandler(testIntent.getName(), testIntent);
    }

    // Fired when a user asks Alexa / STC something
    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session)
            throws SpeechletException {
        // We log the request ID and session ID for log purposes
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        // Our workshop json parser will check it's json page for any changes
        // and will update it's own information if needed.
        workshopJsonParser.checkForUpdate();

        // Gets the intent from the request
        Intent intent = request.getIntent();

        // If our intent is somehow null, we throw an error
        if (intent == null) {
            throw new SpeechletException("Invalid Intent");
        }

        // Pass our data to our handler to handle
        return intentHandler.update(intent, session);
    }

    // Fires when a session is first initialize
    @Override
    public void onSessionStarted(SessionStartedRequest request, Session session)
            throws SpeechletException {
        // We only log here
        log.info("onSessionStarted requestID={}, sessionId={}",
                request.getRequestId(), session.getSessionId());
    }

    // Fires when a session is over
    @Override
    public void onSessionEnded(SessionEndedRequest request, Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestID={}, sessionID={}",
                request.getRequestId(), session.getSessionId());
    }

    // Fires when a user says 'Ask STC' without any other arguments
    @Override
    public SpeechletResponse onLaunch(LaunchRequest request, Session session)
            throws SpeechletException {
        log.info("onlaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        // TODO: Give example commands or options?
        return SpeechletResponse.newTellResponse("Okay, launching STC.");
    }
}
