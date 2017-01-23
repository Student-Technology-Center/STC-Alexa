package edu.wwu.center.studenttechnology.util;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WorkshopJsonParser {
    private final String JSON_URL = "http://west.wwu.edu/stcworkshops/workshop_jason.asp";
    private JSONObject jsonObject;
    private HashMap<String, Workshop> workshopMap;

    private String lastJsonBodytext;

    public WorkshopJsonParser() {
        workshopMap = new HashMap<String, Workshop>();
    }

    public void checkForUpdate() {
        boolean isJsonNew = setJsonObject();

        // We only create a new map if our JSON is new
        if (isJsonNew) {
            createWorkshopMap();
        }
    }

    private boolean setJsonObject() {
        Document doc;

        // We try to connect to the JSON URL and get the document
        try {
            doc = Jsoup.connect(JSON_URL).get();
        } catch (IOException e) {
            // Throw errors if that fails
            e.printStackTrace();
            return false;
        }

        // We get the text of the document
        String jsonText = doc.body().text();

        // If this is the same text as our last successful map creation, we
        // return false to signify not to create a new map
        if (jsonText.equals(lastJsonBodytext)) {
            return false;
        }

        // Sets our lastBodyText for future checks
        lastJsonBodytext = jsonText;

        // Sets our jsonObject for map creation purpose
        jsonObject = new JSONObject(jsonText);

        return true;
    }

    private void createWorkshopMap() {
        // Gets an array of the workshop json sections
        JSONArray workshopArray = jsonObject.getJSONArray("workshops");
        workshopMap = new HashMap<String, Workshop>();

        workshopArray.length();

        // Iterates through every jsonObject of the workshops
        for (int i = 0; i < workshopArray.length(); i++) {
            JSONObject workshopJsonObject = workshopArray.getJSONObject(i);

            // Gets the workshop name in lowercase
            String name = workshopJsonObject.getString("name").toLowerCase();
            // Deletes awkward space at the end of the name
            name = name.substring(0, name.length() - 1);

            // Gets the date of the workshop, without spaces
            String date = workshopJsonObject.getString("date")
                    .replaceAll("\\s+", "");

            // Gets the start time of the workshop, without spaces
            String startTime = workshopJsonObject.getString("start")
                    .replaceAll("\\s+", "");

            // Gets the number of seats available, without spaces
            String seats = workshopJsonObject.getString("seats")
                    .replaceAll("\\s+", "");

            // If there is already a saved workshop with the same name, we add
            // on to it.
            if (workshopMap.containsKey(name)) {
                Workshop workshop = workshopMap.get(name);
                workshop.addDate(date);
                workshop.addStartTime(startTime);
                workshop.addSeatsRemaining(seats);
                continue;
            }

            // If this workshop doesn't exist in our map yet, we create a new
            // workshop object and add it
            Workshop workshop = new Workshop(name, date, startTime, seats);
            workshopMap.put(name, workshop);
        }
    }

    public Workshop getWorkshop(String name) {
        return workshopMap.get(name);
    }

    public Collection<Workshop> getWorkshopCollection() {
        return workshopMap.values();
    }
}
