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
        // Gets our list of workshops text file from our resource folder
        File file = new File(getClass().getClassLoader()
                .getResource(SAMPLE_UTTERANCE_TEXT_PATH).getFile());

        // Try to open the file with scanner
        try (Scanner scanner = new Scanner(file)) {
            SampleUtterance sampleUtterance = null;

            // While there is still more to read...
            while (scanner.hasNextLine()) {
                // Get the new line
                String line = scanner.nextLine();

                // If there isn't a sample utterance we are adding on to, we
                // create a new one with the next line (our parent line)
                if (sampleUtterance == null) {
                    sampleUtterance = new SampleUtterance(line);
                    continue;
                }

                // If there is a blank line, we complete the construction of one
                // SampleUtterance and add it to our list
                if (line.equals("")) {
                    utteranceList.add(sampleUtterance);
                    sampleUtterance = null;
                    continue;
                }

                // Otherwise, if there is a new line to be read and it's not our
                // parent string, we add it to the sample utterance we're
                // constructing.
                sampleUtterance.AddSubString(line);
            }

            // We finish off by adding our last SampleUtterance to the list
            utteranceList.add(sampleUtterance);
        } catch (IOException ex) {
            // Error trace in case file can't be read
            ex.printStackTrace();
        }
    }

    public String GetString(String utteranceString) {
        // We iterate through every sample utterance we have saved
        for (SampleUtterance sampleUtterance : utteranceList) {
            // Gets the result of checking our given string against what the
            // SampleUtterance has
            String result = sampleUtterance
                    .CheckString(utteranceString.toLowerCase());

            // If our result is null, it means that the SampleUtterance doesn't
            // have anything close to what was given to it, so we continue to
            // check the next SampleUtterance
            if (result == null) {
                continue;
            }

            // If the result isn't null, a SampleUtterance returned something
            // successfully.
            return result;
        }

        // Null if no SampleUtterance accomplished
        return null;
    }
}
