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
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

public class STCAlexaSpeechlet implements Speechlet {
    private final Logger log;
    private final Map<String, ISpeechCommand> commandDict;

    public STCAlexaSpeechlet(Logger log) {
        this.log = log;
        this.commandDict = new HashMap<String, ISpeechCommand>();
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
        return handleHelpIntent();
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();

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

    private SpeechletResponse handleStopIntent() {
        String goodbye = "Goodbye, have a nice day!";

        return SpeechletResponse
                .newTellResponse(constructOutputSpeech(goodbye));
    }

    private SpeechletResponse handleWorkshopIntent() {
        // TODO: Obviously this isn't the best nor most accurate response. Also,
        // asking about more information about a workshop isn't functional atm.
        // We need to scrap the data in the form of a JSON, parse it, and return
        // that message to the user. Furthermore, we need to handle the request
        // of information about a specific workshop.
        String workshops = "Currently we offer workshops on the Adobe Suite"
                + ", Microsoft Office, Google Applications, and various "
                + "other programs.";

        workshops += " For more information about a specific workshop, "
                + "please ask me to tell you more";
        return SpeechletResponse
                .newTellResponse(constructOutputSpeech(workshops));
    }

    public class Test implements ISpeechCommand {
        public SpeechletResponse execute(Object... data) {
            return null;
        }
    }
}
