
import java.io.*;
import java.util.*;

public class ApplicationRunner {
// Adding every line of the file into an ArrayList

    public static ArrayList<String> wordArrayFromFile() {
        ArrayList<String> wordArray = new ArrayList<>();
        try (BufferedReader dataBuffer = new BufferedReader(new FileReader("wordlist.txt"))) {
            String currentLine;
            while ((currentLine = dataBuffer.readLine()) != null) {
                wordArray.add(currentLine);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        return wordArray;
    }
// Sorts array of guesses and the uses functional expression to print each character in the way shown in the spec

    public static void printGuesses(ArrayList<String> myGuesses) {
        Collections.sort(myGuesses);
        System.out.print("[ ");
        myGuesses.forEach((guess) -> System.out.print(guess + " "));
        System.out.println("]");
    }

    public static boolean guessContains(ArrayList<String> myGuesses, String myString) {
        return myGuesses.stream().anyMatch((guess) -> (guess.equals(myString)));
    }

    public static void wordGuessRound(ArrayList<String> wordArray, Scanner inScanner) {
        String randomWord = wordArray.get(new Random().nextInt(wordArray.size()));
        ArrayList<String> myGuesses = new ArrayList<>();
        ArrayList<String> wrongGuesses = new ArrayList<>();
        boolean won = true;
// Run a max of 11 times
// Last time only prints result and doesn't take an input
        guessLoop:
        for (int i = 0; i < 11; i++) {
            won = true;

            for (int k = 0; k < randomWord.length(); k++) {
                String wordCharString = Character.toString(randomWord.charAt(k));
                if (guessContains(myGuesses, wordCharString)) {
                    System.out.print(randomWord.charAt(k) + " ");
                } else {
                    won = false;
                    System.out.print("_ ");
                }
            }
// You've won if there's no underscores left, in which case we break from the loop
            if (won | i == 11) {
                break;
            }

            System.out.print("\n" + wrongGuesses.size() + " wrong guesses so far ");
            printGuesses(wrongGuesses);
// Only register inputs that are just one lowercase letter and haven't been guessed before
            while (i < 10) {
                System.out.println("Have a guess(lower case letter or * to give up)");
                String input = inScanner.nextLine();
                if (input.length() == 1 && Character.isLowerCase(input.charAt(0)) && (!guessContains(myGuesses, input))) {
                    if (!(randomWord.contains(input))) {
                        wrongGuesses.add(input);
                    }

                    myGuesses.add(input);
                    break;
                } else if ("*".equals(input)) {
                    break guessLoop;
                }
            }
        }

        System.out.println("\nThe hidden word was " + randomWord.toUpperCase());
        if (won) {
            System.out.println("You Win! :-)\n");
        } else {
            System.out.println("You lose :-(\n");
        }
    }

    public static void main(String[] args) {

        System.out.println("Word Guessing Game");
        ArrayList<String> wordArray = wordArrayFromFile();
        if (!wordArray.isEmpty()) {
            Scanner inScanner = new Scanner(System.in);

            mainLoop:
            while (true) {
                System.out.println("Play (1) or Exit (0) > 1");
                switch (inScanner.nextLine()) {
                    case "0":
                        break mainLoop;
                    case "1":
                        wordGuessRound(wordArray, inScanner);
                }
            }
        } else {
            System.out.println("No words loaded\nCannot continue");
        }
    }
}
