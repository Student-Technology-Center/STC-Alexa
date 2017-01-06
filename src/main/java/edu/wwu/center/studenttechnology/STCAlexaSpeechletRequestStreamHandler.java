package edu.wwu.center.studenttechnology;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class STCAlexaSpeechletRequestStreamHandler
        extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();

    static {
        // TODO: Add unique value here
        supportedApplicationIds
                .add("amzn1.echo-sdk-ams.app.[unique-value-here]");
    }

    public STCAlexaSpeechletRequestStreamHandler() {
        super(new STCAlexaSpeechlet(
                LoggerFactory.getLogger(STCAlexaSpeechlet.class)),
                supportedApplicationIds);
    }

}
