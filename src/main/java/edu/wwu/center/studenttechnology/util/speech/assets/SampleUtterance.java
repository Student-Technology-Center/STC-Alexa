package edu.wwu.center.studenttechnology.util.speech.assets;

import java.util.ArrayList;
import java.util.List;

public class SampleUtterance {
    private final String parentString;
    private List<String> subStrings;

    public SampleUtterance(String parentString) {
        this.parentString = parentString;
        subStrings = new ArrayList<String>();
    }

    public void AddSubString(String subString) {
        subStrings.add(subString);
    }

    public String CheckString(String testString) {
        return (subStrings.contains(testString) || testString == parentString)
                ? parentString : null;
    }
}
