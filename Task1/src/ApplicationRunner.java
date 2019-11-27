
import java.io.*;
import java.util.*;

public class ApplicationRunner {

// Could potentially error if the file is removed from the directory inbetween IO calls on a very large file?
    public static int[] countLettersFromFile(String file) throws FileNotFoundException, IOException {
        int[] letterCount = new int[26];
        try (BufferedReader dataBuffer = new BufferedReader(new FileReader(file))) {
// Store current char outside of while loop due to scope
            int charInt;
// Read each character sequentially as an int
// -1 is returned at the end of the file which is when the while loop ends
            while ((charInt = dataBuffer.read()) != -1) {
                charInt = Character.toLowerCase((char) charInt);
// Ignores all other characters                
                if ((charInt >= 'a') && (charInt <= 'z')) {
                    letterCount[charInt - 'a']++;
                }
            }
        }
        return letterCount;
    }

// Sort then reverse
    public static int[] sortArrayInt(int[] arr) {
        Arrays.sort(arr);
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
        return arr;
    }

    public static int countArrayInt(int[] arr) {
        int arrayIntTotal = 0;
        for (int i = 0; i < arr.length; i++) {
            arrayIntTotal += arr[i];
        }
        return arrayIntTotal;
    }

    public static void slowPrintFreq(int[] unsortedLetterCount, int[] sortedLetterCount, PrintWriter outputPrint) {
        for (int i = 0; i < sortedLetterCount.length; i++) {
            for (int k = 0; k < sortedLetterCount.length; k++) {
                if (sortedLetterCount[i] == unsortedLetterCount[k]) {
                    dualPrint(outputPrint, ((char) (k + 'a')) + " --> " + unsortedLetterCount[k]);
                }
            }
        }
    }

// Should never throw an IOException at this point
    public static void play(String file, PrintWriter outputPrint) throws IOException {
        int[] unsortedLetterCount = countLettersFromFile(file);
        int[] sortedLetterCount = sortArrayInt(unsortedLetterCount.clone());
        dualPrint(outputPrint, "Total number of letters = " + countArrayInt(sortedLetterCount));
        slowPrintFreq(unsortedLetterCount, sortedLetterCount, outputPrint);
    }

    public static String listTxtInDirectory() {
        File folder = new File("./");
        File[] listOfFiles = folder.listFiles();
        String outString = "";
        for (File listOfFile : listOfFiles) {
            String fileName = listOfFile.getName();
            int lastIndexOf = fileName.lastIndexOf(".");
            if (lastIndexOf != -1 && ".txt".equals(fileName.substring(lastIndexOf)) && !"result.txt".equals(fileName)) {
                outString = outString + listOfFile.getName() + "\n";
            }
        }
        return outString.trim();
    }

    public static String fileSelection() {
        String validInputs = listTxtInDirectory();
        if ("".equals(validInputs)) {
            System.out.println("No valid text files in root of working directory\n");
            return "";
        } else if (validInputs.contains("\n")) {
            System.out.println("Please pick a text file in root of working directory\n" + validInputs + "\n");
            Scanner inputScanner = new Scanner(System.in);
            return inputScanner.nextLine();
        } else {
            System.out.println("Only found one valid file\nUsing " + validInputs + "\n");
            return validInputs;
        }
    }

    public static void dualPrint(PrintWriter outputPrint, String output) {
        System.out.println(output);
        outputPrint.println(output);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("Current working directory is " + System.getProperty("user.dir") + "\n");
// Can't use try-with-resources in this jdk version so get some redundancy :(
        String inputFile = fileSelection();
// If a file isn't found, it just creates one, so it doesn't error
// If write permission is denied, program runs but doesn't save results to file
        if (!"".equals(inputFile)) {
            try (PrintWriter outputPrint = new PrintWriter("result.txt")) {
                play(inputFile, outputPrint);
            } catch (SecurityException | IOException e) {
// Rather than using different code for methods, we just output to nothing
                System.out.println("Write accessed denied \nResults will not be saved to result.txt\n");
                PrintWriter outputPrint = new PrintWriter(new OutputStream() {
                    @Override
                    public void write(int b) {
                    }
                });
                play(inputFile, outputPrint);
            }
        }

    }
}
