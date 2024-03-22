#include <iostream>
#include <fstream>
#include <sstream>
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
    std::string inputWord;
    std::cout << "Enter a word: ";
    std::cin >> inputWord;

    // Sort and convert the input word to lowercase
    std::string sortedWord = sortString(inputWord);

    // Open the CSV file
    std::ifstream inputFile("output.csv");
    if (!inputFile) {
        std::cerr << "Failed to open the file.\n";
        return 1;
    }

    // Dictionary to store words with sorted character frequencies
    std::unordered_map<std::string, std::vector<std::string>> frequencyDict;

    std::string line;
    // Skip the header line
    std::getline(inputFile, line);

    // Read each line of the file
    while (std::getline(inputFile, line)) {
        std::istringstream iss(line);
        std::string sortedFreq, word;
        // Extract sorted frequency and words
        if (std::getline(iss, sortedFreq, ',')) {
            while (std::getline(iss, word, ';')) {
                // Add word to the dictionary
                frequencyDict[sortedFreq].push_back(word);
            }
        }
    }

    inputFile.close();

    // Print anagrams if found
    if (frequencyDict.find(sortedWord) != frequencyDict.end()) {
        std::cout << "Anagrams of \"" << inputWord << "\": ";
        for (const auto& word : frequencyDict[sortedWord]) {
            std::cout << word << " ";
        }
        std::cout << std::endl;
    } else {
        std::cout << "No anagrams found for \"" << inputWord << "\".\n";
    }

    return 0;
}
