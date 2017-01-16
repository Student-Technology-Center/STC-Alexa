package edu.wwu.center.studenttechnology;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.ui.PlainTextOutputSpeech;

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

    private String intentToHandleNextYesNoIntent;
    private String intentToHandleNextIntent;

    public STCAlexaSpeechlet(Logger log) {
        this.log = log;
        workshopJsonParser = new WorkshopJsonParser();
        sampleUtteranceHandler = new SampleUtteranceHandler();
        intentHandler = new IntentHandler();

        intentToHandleNextYesNoIntent = null;
        intentToHandleNextIntent = null;

        WorkshopInformationIntentHandler workshopInformationHandler = new WorkshopInformationIntentHandler(
                workshopJsonParser);
        WorkshopDateIntentHandler workshopDateHandler = new WorkshopDateIntentHandler(
                workshopJsonParser, sampleUtteranceHandler);
        STCInformationIntentHandler stcInformationHandler = new STCInformationIntentHandler();
        ExampleYesNoIntent testIntent = new ExampleYesNoIntent();

        intentHandler.addIntentHandler("WorkshopInformationIntent",
                workshopInformationHandler);
        intentHandler.addIntentHandler("WorkshopDateIntent",
                workshopDateHandler);
        intentHandler.addIntentHandler("STCInformationIntent",
                stcInformationHandler);
        intentHandler.addIntentHandler("TestIntent", testIntent);
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        workshopJsonParser.checkForUpdate();

        Intent intent = request.getIntent();

        if (intent == null) {
            throw new SpeechletException("Invalid Intent");
        }

        String intentString = (intentToHandleNextYesNoIntent == null)
                ? intent.getName() : intentToHandleNextYesNoIntent;
        intentString = (intentToHandleNextIntent == null) ? intentString
                : intentToHandleNextIntent;

        if ((intent.getName().equals("ConfirmationIntent")
                || intent.getName().equals("NoIntent"))
                && intentToHandleNextYesNoIntent == null) {
            return SpeechletResponse
                    .newTellResponse("Sorry, I don't understand.");
        }

        intentToHandleNextYesNoIntent = null;
        intentToHandleNextIntent = null;

        SpeechletResponse response = intentHandler.update(intentString, intent);

        if (response.getHandleNextYesNoIntent()) {
            intentToHandleNextYesNoIntent = intent.getName();
        }

        if (response.getHandleNextEvent()) {
            intentToHandleNextIntent = intent.getName();
        }

        return response;
    }

    @Override
    public void onSessionStarted(SessionStartedRequest request, Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestID={}, sessionId={}",
                request.getRequestId(), session.getSessionId());
    }

    @Override
    public void onSessionEnded(SessionEndedRequest request, Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestID={}, sessionID={}",
                request.getRequestId(), session.getSessionId());
    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest request, Session session)
            throws SpeechletException {
        log.info("onlaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        PlainTextOutputSpeech output = new PlainTextOutputSpeech();
        output.setText("Okay, launching STC.");

        return SpeechletResponse.newTellResponse(output);
    }
}
