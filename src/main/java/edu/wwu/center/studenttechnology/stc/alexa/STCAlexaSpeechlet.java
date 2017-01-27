package edu.wwu.center.studenttechnology.stc.alexa;

import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletException;

import edu.wwu.center.studenttechnology.stc.alexa.framework.speechlet.Speechlet;
import edu.wwu.center.studenttechnology.stc.alexa.framework.speechlet.SpeechletResponse;
import edu.wwu.center.studenttechnology.stc.alexa.intent.STCInformationIntentHandler;
import edu.wwu.center.studenttechnology.stc.alexa.intent.WorkshopDateIntentHandler;
import edu.wwu.center.studenttechnology.stc.alexa.intent.WorkshopInformationIntent;
import edu.wwu.center.studenttechnology.stc.alexa.intent.WorkshopListIntentHandler;
import edu.wwu.center.studenttechnology.stc.alexa.util.speech.SampleUtteranceHandler;
import edu.wwu.center.studenttechnology.stc.alexa.util.workshop.WorkshopJsonParser;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;

public class STCAlexaSpeechlet extends Speechlet {
    private final Logger log;
    private final SampleUtteranceHandler sampleUtteranceHandler;
    private final WorkshopJsonParser workshopJsonParser;

    public STCAlexaSpeechlet(Logger log) {
        this.log = log;
        BasicConfigurator.configure();

        // Workshop JSON Parser is how some intents transcribe a JSON full with
        // information about workshops. More detailed in it's class
        workshopJsonParser = new WorkshopJsonParser();

        // SampleUtteranceHandler is used to make sure we're understanding the
        // user when they're trying to say a workshop, due to the various names
        // and abbreviations workshops may have. See class for more details
        sampleUtteranceHandler = new SampleUtteranceHandler();

        registerIntent(new WorkshopListIntentHandler("WorkshopListIntent",
                workshopJsonParser));
        registerIntent(new STCInformationIntentHandler("STCInformationIntent"));
        registerIntent(new WorkshopDateIntentHandler("WorkshopDateIntent",
                workshopJsonParser, sampleUtteranceHandler));
        registerIntent(
                new WorkshopInformationIntent("WorkshopInformationIntent",
                        workshopJsonParser, sampleUtteranceHandler));
    }

    // Fires when a user says 'Ask STC' without any other arguments
    @Override
    public SpeechletResponse onLaunch(LaunchRequest request, Session session)
            throws SpeechletException {
        log.info("onSessionLaunch requestID={}, sessionId={}",
                request.getRequestId(), session.getSessionId());
        return SpeechletResponse.newTellResponse("Okay, launching STC.");
    }
}
