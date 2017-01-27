package edu.wwu.center.studenttechnology.stc.alexa;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class STCAlexaSpeechletRequestStreamHandler
        extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();

    static {
        supportedApplicationIds
                .add("amzn1.ask.skill.280380bd-d8d3-4b06-b1c7-61436d7eed64");
    }

    public STCAlexaSpeechletRequestStreamHandler() {
        super(new STCAlexaSpeechlet(
                LoggerFactory.getLogger(STCAlexaSpeechlet.class)),
                supportedApplicationIds);
    }

}
