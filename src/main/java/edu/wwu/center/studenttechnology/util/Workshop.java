package edu.wwu.center.studenttechnology.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Workshop {
    private final String name;
    private final List<String> dates;

    private final Map<String, String> dateMapStartTime;
    private final Map<String, String> dateMapSeatsRemaining;

    /*
     * This class is used to package the data we get from WorkshopJson Parser.
     * This class is simply a container for all the data about a particular
     * workshop.
     */

    public Workshop(String name, String date, String startTime,
            String seatsRemaining) {
        this.name = name;

        this.dates = new ArrayList<String>();
        dates.add(date);

        dateMapStartTime = new HashMap<>();
        dateMapSeatsRemaining = new HashMap<>();

        dateMapStartTime.put(date, startTime);
        dateMapSeatsRemaining.put(date, seatsRemaining);
    }

    public String getName() {
        return name;
    }

    // Some workshop names have roman numerals, we want to remove those (and
    // other future weirdness) here so that Alexa can pronounce things okay
    public String getReadableName() {
        String readableString = name;

        String lastTwoCharacterString = readableString
                .substring(readableString.length() - 2);

        if (lastTwoCharacterString.equals(" i")
                || lastTwoCharacterString.equals(" x")) {
            readableString = readableString.substring(0,
                    readableString.length() - 2);
        }

        return readableString;
    }

    public List<String> GetDates() {
        return dates;
    }

    public void addDate(String date) {
        dates.add(date);
    }

    public String getStartTime(String date) {
        return dateMapStartTime.get(date);
    }

    public void addStartTime(String date, String startTime) {
        dateMapStartTime.put(date, startTime);
    }

    public String getSeatsRemaining(String date) {
        return dateMapSeatsRemaining.get(date);
    }

    public void addSeatsRemaining(String date, String seatsRemaining) {
        dateMapSeatsRemaining.put(date, seatsRemaining);
    }
}
