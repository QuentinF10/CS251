import java.io.*;
import java.util.*;

/**
 * Name: Quentin FAYE
 * Course: CS 251
 * Lab 6 : Sorting Lines in a File
 */
public class LineSorter {

    /**
     * main method
     *
     * @param args  expects two Command-line arguments:  input and  output file name.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Try again by using this command: java LineSorter <inputFile> <outputFile>");
            return;
        }

        //get filename from command line
        String inputFilename = args[0];
        String outputFilename = args[1];

        //sort file function called
        sortFile(inputFilename, outputFilename);
    }

    /**
     * Sorts the lines from the input file and writes the sorted lines to the output file.
     * Lines in the input file starting with the "%" character are ignored.
     * Sorting is done first by line length and then in reverse lexicographical order.
     *
     * @param inputFilename   input file to read lines from.
     * @param outputFilename output file to write the sorted lines to.
     */
    public static void sortFile(String inputFilename, String outputFilename) {
        // a list to store the lines from the input file
        List<String> lines = new ArrayList<>();

        // read lines from input file
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename))) {
            String line;
            // read each line and add to the list if it doesn't start with "%"
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("%")) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            // Handle any errors that occur while reading the input file
            System.err.println("Error reading from input file: " + e.getMessage());
            return;
        }

        // sort the lines in the list
        // primary sorting is by line length, secondary is by reverse lexicographical order

        lines.sort((line1, line2) -> {
            int compare = Integer.compare(line1.length(), line2.length());
            if (compare != 0) {
                return compare;
            }
            return -line1.compareTo(line2);  // Reverse lexicographical comparison
        });

        // Write sorted lines to output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }
}
