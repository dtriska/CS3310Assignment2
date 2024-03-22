import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;

public class Anagram {
    // Function to sort a string alphabetically after converting to lowercase
    private static String sortString(String str) {
        char[] chars = str.toLowerCase().toCharArray();
        java.util.Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("output.csv")))) {
            // Dictionary to store words with sorted character frequencies
            Map<String, ArrayList<String>> frequencyDict = new HashMap<>();

            // Skip the header line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Read each line of the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String sortedFreq = parts[0];
                String[] words = parts[1].split(";");
                for (String word : words) {
                    // Add word to the dictionary
                    frequencyDict.computeIfAbsent(sortedFreq, k -> new ArrayList<>()).add(word);
                }
            }

            // Prompt user for input word
            System.out.print("Enter a word: ");
            try (Scanner inputScanner = new Scanner(System.in)) {
                String inputWord = inputScanner.nextLine().trim();

                // Sort and convert the input word to lowercase
                String sortedWord = sortString(inputWord);

                // Print anagrams if found
                if (frequencyDict.containsKey(sortedWord)) {
                    System.out.print("Anagrams of \"" + inputWord + "\": ");
                    for (String word : frequencyDict.get(sortedWord)) {
                        System.out.print(word + " ");
                    }
                    System.out.println();
                } else {
                    System.out.println("No anagrams found for \"" + inputWord + "\".");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }
}
