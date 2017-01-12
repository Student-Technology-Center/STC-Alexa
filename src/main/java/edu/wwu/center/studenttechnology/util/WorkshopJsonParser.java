package edu.wwu.center.studenttechnology.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class WorkshopJsonParser {
    private final String JSON_URL = "http://west.wwu.edu/stcworkshops/workshop_jason.asp";
    private JSONObject jsonObject;
    private HashMap<String, Workshop> workshopMap;

    public WorkshopJsonParser() {
        workshopMap = new HashMap<String, Workshop>();

        checkForUpdate();
    }

    public void checkForUpdate() {
        // TODO: Clean this up lol
        try {
            setJsonObject();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        createWorkshopMap();
    }

    private void setJsonObject() throws MalformedURLException, IOException {
        InputStream inputStream = new URL(JSON_URL).openStream();

        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream,
                            Charset.forName("UTF-8")));

            String newLine;
            String jsonText = "";

            while ((newLine = bufferedReader.readLine()) != null) {
                jsonText += newLine;
            }

            jsonObject = new JSONObject(jsonText);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
    }

    private void createWorkshopMap() {
        JSONArray workshopArray = jsonObject.getJSONArray("workshops");
        workshopMap = new HashMap<String, Workshop>();

        int i = 0;

        while (workshopArray.getJSONObject(i) != null) {
            JSONObject workshopJsonObject = workshopArray.getJSONObject(i);
            String name = workshopJsonObject.getString("name");
            String date = workshopJsonObject.getString("date");
            String startTime = workshopJsonObject.getString("start");
            String seats = workshopJsonObject.getString("seats");

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
}
