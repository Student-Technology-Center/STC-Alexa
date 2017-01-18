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

        checkForUpdate();
    }

    public void checkForUpdate() {
        boolean isJsonNew = setJsonObject();

        if (isJsonNew) {
            createWorkshopMap();
        }
    }

    private boolean setJsonObject() {
        Document doc;
        try {
            doc = Jsoup.connect(JSON_URL).get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        String jsonText = doc.body().text();

        if (jsonText.equals(lastJsonBodytext)) {
            return false;
        }

        lastJsonBodytext = jsonText;

        jsonObject = new JSONObject(jsonText);

        return true;
    }

    private void createWorkshopMap() {
        JSONArray workshopArray = jsonObject.getJSONArray("workshops");
        workshopMap = new HashMap<String, Workshop>();

        workshopArray.length();

        for (int i = 0; i < workshopArray.length(); i++) {
            JSONObject workshopJsonObject = workshopArray.getJSONObject(i);
            String name = workshopJsonObject.getString("name").toLowerCase();
            name = name.substring(0, name.length() - 1);
            String date = workshopJsonObject.getString("date")
                    .replaceAll("\\s+", "");
            String startTime = workshopJsonObject.getString("start")
                    .replaceAll("\\s+", "");
            String seats = workshopJsonObject.getString("seats")
                    .replaceAll("\\s+", "");

            if (workshopMap.containsKey(name)) {
                Workshop workshop = workshopMap.get(name);
                workshop.addDate(date);
                workshop.addStartTime(startTime);
                workshop.addSeatsRemaining(seats);
                continue;
            }

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
