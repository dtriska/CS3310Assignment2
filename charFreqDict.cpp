#include <iostream>
#include <fstream>
#include <string>
#include <unordered_map>
#include <algorithm>
#include <vector>

// Function to sort a string alphabetically after converting to lowercase
std::string sortString(const std::string& str) {
    std::string sorted = str;
    // Convert string to lowercase
    std::transform(sorted.begin(), sorted.end(), sorted.begin(), ::tolower);
    // Sort the string
    std::sort(sorted.begin(), sorted.end());
    return sorted;
}

int main() {
    std::ifstream inputFile("words.txt");
    if (!inputFile) {
        std::cerr << "Failed to open the file.\n";
        return 1;
    }

    // Dictionary to store words with sorted character frequencies
    std::unordered_map<std::string, std::vector<std::string>> frequencyDict;

    std::string word;
    while (inputFile >> word) {
        // Count character frequencies and sort them
        std::unordered_map<char, int> charFreq;
        for (char c : word) {
            charFreq[c]++;
        }

        std::string sortedFreq = sortString(word);

        // Add word to the dictionary
        frequencyDict[sortedFreq].push_back(word);
    }

    inputFile.close();

    // Writing data to a CSV file
    std::ofstream outputFile("output.csv");
    if (!outputFile) {
        std::cerr << "Failed to create the output file.\n";
        return 1;
    }

    // Write header
    outputFile << "Sorted Frequency,Words\n";

    // Write data
    for (const auto& pair : frequencyDict) {
        outputFile << pair.first << ",";
        for (size_t i = 0; i < pair.second.size(); ++i) {
            outputFile << pair.second[i];
            if (i != pair.second.size() - 1) {
                outputFile << ";"; // Use a delimiter such as ";" to separate words
            }
        }
        outputFile << "\n";
    }

    std::cout << "Data has been written to output.csv successfully.\n";

    outputFile.close();
    return 0;
}
