package edu.wwu.center.studenttechnology.util.speech.assets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SampleUtteranceHandler {
    private List<SampleUtterance> utteranceList;

    private final String SAMPLE_UTTERANCE_TEXT_PATH = "speechAssets/LIST_OF_WORKSHOPS.txt";

    public SampleUtteranceHandler() {
        utteranceList = new ArrayList<SampleUtterance>();
        createUtteranceList();
    }

    private void createUtteranceList() {
        File file = new File(getClass().getClassLoader()
                .getResource(SAMPLE_UTTERANCE_TEXT_PATH).getFile());

        try (Scanner scanner = new Scanner(file)) {
            SampleUtterance sampleUtterance = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (sampleUtterance == null) {
                    sampleUtterance = new SampleUtterance(line);
                    continue;
                }

                if (line.equals("")) {
                    utteranceList.add(sampleUtterance);
                    sampleUtterance = null;
                    continue;
                }

                sampleUtterance.AddSubString(line);
            }

            utteranceList.add(sampleUtterance);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String GetString(String utteranceString) {
        for (SampleUtterance sampleUtterance : utteranceList) {
            String result = sampleUtterance.CheckString(utteranceString);

            if (result == null) {
                continue;
            }

            return result;
        }

        return null;
    }
}
