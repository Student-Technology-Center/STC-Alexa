package edu.wwu.center.studenttechnology;

import com.amazon.speech.speechlet.SpeechletResponse;

public interface ISpeechCommand {
    public SpeechletResponse execute(Object... data);
}
