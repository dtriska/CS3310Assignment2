// Derek Triska

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Anagram {
    // Function to sort a string alphabetically after converting to lowercase
    private static String sortString(String str) {
        char[] chars = str.toLowerCase().toCharArray();
        java.util.Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java AnagramFinder <input_file>");
            return;
        }

        String inputFileName = args[0];
        String outputFileName = "output.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {

            // Dictionary to store words with sorted character frequencies
            Map<String, List<String>> frequencyDict = new HashMap<>();

            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim();

                // Sort and convert the input word to lowercase
                String sortedWord = sortString(word);

                // Open the CSV file
                try (BufferedReader csvReader = new BufferedReader(new FileReader("output.csv"))) {
                    // Skip the header line
                    csvReader.readLine();

                    // Read each line of the CSV file
                    String line;
                    while ((line = csvReader.readLine()) != null) {
                        String[] parts = line.split(",");
                        String sortedFreq = parts[0];
                        String[] words = parts[1].split(";");
                        if (sortedWord.equals(sortedFreq)) {
                            for (String w : words) {
                                frequencyDict.computeIfAbsent(sortedWord, k -> new ArrayList<>()).add(w);
                            }
                            break; // No need to continue if we found matching sorted word
                        }
                    }
                }

                // Writing results to the output file
                if (frequencyDict.containsKey(sortedWord)) {
                    writer.write("Anagrams of \"" + word + "\": ");
                    for (String anagram : frequencyDict.get(sortedWord)) {
                        writer.write(anagram + " ");
                    }
                    writer.newLine();
                } else {
                    writer.write("No anagrams found for \"" + word + "\".");
                    writer.newLine();
                }

                frequencyDict.clear(); // Clear the dictionary for the next word
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
