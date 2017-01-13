package edu.wwu.center.studenttechnology;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;

import edu.wwu.center.studenttechnology.intentHandlers.IntentHandler;
import edu.wwu.center.studenttechnology.intentHandlers.STCInformationIntentHandler;
import edu.wwu.center.studenttechnology.intentHandlers.WorkshopDateIntentHandler;
import edu.wwu.center.studenttechnology.intentHandlers.WorkshopInformationIntentHandler;
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
		workshopJsonParser = new WorkshopJsonParser();
		sampleUtteranceHandler = new SampleUtteranceHandler();
		intentHandler = new IntentHandler();

		WorkshopInformationIntentHandler workshopInformationHandler = new WorkshopInformationIntentHandler(
				workshopJsonParser);
		WorkshopDateIntentHandler workshopDateHandler = new WorkshopDateIntentHandler(workshopJsonParser,
				sampleUtteranceHandler);
		STCInformationIntentHandler stcInformationHandler = new STCInformationIntentHandler();

		intentHandler.addIntentHandler("WorkshopInformationIntent", workshopInformationHandler);
		intentHandler.addIntentHandler("WorkshopDateIntent", workshopDateHandler);
		intentHandler.addIntentHandler("STCInformationIntent", stcInformationHandler);
	}

	@Override
	public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

		Intent intent = request.getIntent();
		workshopJsonParser.checkForUpdate();

		if (intent == null) {
			throw new SpeechletException("Invalid Intent");
		}

		return intentHandler.update(intent);
	}

	@Override
	public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
		log.info("onSessionStarted requestID={}, sessionId={}", request.getRequestId(), session.getSessionId());
	}

	@Override
	public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
		log.info("onSessionEnded requestID={}, sessionID={}", request.getRequestId(), session.getSessionId());
	}

	@Override
	public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
		log.info("onlaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

		PlainTextOutputSpeech output = new PlainTextOutputSpeech();
		output.setText("Okay, launching STC.");

		return SpeechletResponse.newTellResponse(output);
	}
}
