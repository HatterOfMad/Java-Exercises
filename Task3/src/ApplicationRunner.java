
import java.io.*;
import java.util.*;

public class ApplicationRunner {
// Converts string to a char[], sorts it, and converts it back to a string
// Since a string is essentially already a char[] it should be quite fast

    public static String sortString(String inputString) {
        char[] stringCharArray = inputString.toCharArray();
        Arrays.sort(stringCharArray);
        return (new String(stringCharArray));
    }

// Strings with the same characters are grouped in a hashmap for very fast access when you want to search for an anagram (~1 millisecond)
// The key of the hashmap is a sorted version of the strings which will always be the same no matter the order of the characters
// The  input file is expecting only valid letters and no uppercase characters since sanitising the input would decrease the speed by ~20%
    public static HashMap<String, List<String>> indexedWordArray() {
        HashMap<String, List<String>> lexMap = new HashMap<>();
        try (BufferedReader dataBuffer = new BufferedReader(new FileReader("lexicon.txt"))) {
            String currLine;
            while ((currLine = dataBuffer.readLine()) != null) {
                String strKey = sortString(currLine);
                if (!lexMap.containsKey(strKey)) {
                    lexMap.put(strKey, new ArrayList<>());
                }
                lexMap.get(strKey).add(currLine);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        return lexMap;
    }

    public static void main(String[] args) {
        HashMap<String, List<String>> lexMap = indexedWordArray();
        if (lexMap.isEmpty()) {
            System.out.println("No words loaded from lexicon\nCannot continue");
        } else {
            Scanner inScanner = new Scanner(System.in);
            String input;
// The first loop is for finding an anagram
// If input doesn't contain valid characters it will ask you to enter another string
            boolean aLoop = true;
            while (aLoop) {
                System.out.println("Please enter a string (single word or phrase)");
                input = inScanner.nextLine();
                String strKey = sortString(input.replaceAll("\\s+", "").toLowerCase());
                if (!strKey.chars().allMatch(Character::isLetter)) {
                    System.out.println("\"" + input + "\" is an invalid word or phrase");
                } else {
                    if (lexMap.containsKey(strKey)) {
                        System.out.println("Possible anagrams for \"" + input + "\": " + lexMap.get(strKey));
                    } else {
                        System.out.println("No anagrams for \"" + input + "\"");
                    }
// The second loop is reached after the user's first input and will either exit the first loop, exit the second loop, or repeat
                    boolean bLoop = true;
                    while (bLoop) {
                        System.out.println("Try again (1) or Exit (0) > 1");
                        input = inScanner.nextLine();
                        if ("0".equals(input)) {
                            aLoop = false;
                            bLoop = false;
                        } else if ("1".equals(input)) {
                            bLoop = false;
                        }
                    }
                }
            }

        }
    }
}
