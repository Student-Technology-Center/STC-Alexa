package edu.wwu.center.studenttechnology.util;

import java.util.ArrayList;
import java.util.List;

public class Workshop {
    private final String name;
    private final List<String> dates;
    private final List<String> startTimes;
    private final List<String> seatsRemainingList;

    public Workshop(String name, String date, String startTime,
            String seatsRemaining) {
        this.name = name;

        // TODO: Tie the following three strings together somehow
        
        this.dates = new ArrayList<String>();
        dates.add(date);

        this.startTimes = new ArrayList<String>();
        startTimes.add(startTime);

        this.seatsRemainingList = new ArrayList<>();
        seatsRemainingList.add(seatsRemaining);
    }

    public String getName() {
        return name;
    }

    public List<String> GetDates() {
        return dates;
    }

    public void addDate(String date) {
        dates.add(date);
    }

    public List<String> getStartTime() {
        return startTimes;
    }

    public void addStartTime(String startTime) {
        startTimes.add(startTime);
    }

    public List<String> getSeatsRemaining() {
        return seatsRemainingList;
    }
    
    public void addSeatsRemaining(String seatsRemaining) {
        seatsRemainingList.add(seatsRemaining);
    }
}
