package edu.wwu.center.studenttechnology.util.speech.assets;

import java.util.ArrayList;
import java.util.List;

public class SampleUtterance {
    private final String parentString;
    private List<String> subStrings;

    // We start off a new SampleUtterance object by storing what the parent name
    // is.
    public SampleUtterance(String parentString) {
        this.parentString = parentString;
        subStrings = new ArrayList<String>();
    }

    // Add any subStrings here that we want to mean our parentString
    // AKA, if "Introduction to 3d printing & making" was our parentString, we
    // can say that we want "3d printing", "3d printing and making",
    // "introduction to three d printing" to all mean the parent string.
    public void AddSubString(String subString) {
        subStrings.add(subString);
    }

    // If the string given to us matches our parent string or any of our
    // subStrings, we return the parent string. Otherwise we return null.
    public String CheckString(String testString) {
        return (subStrings.contains(testString) || testString == parentString)
                ? parentString : null;
    }
}
