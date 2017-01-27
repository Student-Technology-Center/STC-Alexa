package edu.wwu.center.studenttechnology.stc.alexa.util.workshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Workshop {
    private final String name;
    private final String description;
    private final String prerequisites;
    private final List<String> dates;

    // Make this an object?
    private final Map<String, String> dateMapStartTime;
    private final Map<String, String> dateMapSeatsRemaining;
    private final Map<String, String> dateMapInstructor;

    /*
     * This class is used to package the data we get from WorkshopJson Parser.
     * This class is simply a container for all the data about a particular
     * workshop.
     */

    public Workshop(String name, String description, String prerequisites,
            String instructor, String date, String startTime,
            String seatsRemaining) {
        this.name = name;
        this.description = description;
        this.prerequisites = prerequisites;

        this.dates = new ArrayList<String>();
        dates.add(date);

        dateMapStartTime = new HashMap<String, String>();
        dateMapSeatsRemaining = new HashMap<String, String>();
        dateMapInstructor = new HashMap<String, String>();

        dateMapStartTime.put(date, startTime);
        dateMapSeatsRemaining.put(date, seatsRemaining);
        dateMapInstructor.put(date, instructor);
    }

    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    // List later?
    public String getPrerequisites() {
        return prerequisites;
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

    public String getInstructor(String date) {
        System.out.println("Date: " + date);
        System.out.println(dateMapInstructor);
        return dateMapInstructor.get(date);
    }

    public void addInstructor(String date, String seatsRemaining) {
        dateMapInstructor.put(date, seatsRemaining);
    }
}
