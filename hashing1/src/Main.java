import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private static Dictionary dict;


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the file containing dictionary words: ");
        String dictFilename = scanner.nextLine();
        int[] words = readWordsFromFile(dictFilename);
        dict = new Dictionary(words);

        while (true) {
            System.out.println("------------------------------");
            System.out.println("Dictionary Operations:");
            System.out.println("1. Insert a string");
            System.out.println("2. Delete a string");
            System.out.println("3. Search for a string");
            System.out.println("4. Batch insert a list of strings from a file");
            System.out.println("5. Batch delete a list of strings from a file");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    insertString(scanner);
                    break;
                case 2:
                    deleteString(scanner);
                    break;
                case 3:
                    searchString(scanner);
                    break;
                case 4:
                    batchInsertFromFilename(scanner);
                    break;
                case 5:
                    batchDeleteFromFilename(scanner);
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 6.");
            }
        }
    }

    private static int[] readWordsFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        HashSet<String> words = new HashSet<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                words.add(line);
            }
        }
        reader.close();

        int[] wordArray = new int[words.size()];
        int i = 0;
        for (String word : words) {
            wordArray[i] = word.hashCode();
            i++;
        }
        return wordArray;
    }

    private static void insertString(Scanner scanner) {
        System.out.print("Enter the string to insert: ");
        String word = scanner.nextLine();
        try {
            dict.insert(word);
            System.out.println("String '" + word + "' inserted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteString(Scanner scanner) {
        System.out.print("Enter the string to delete: ");
        String word = scanner.nextLine();
        try {
            dict.delete(word);
            System.out.println("String '" + word + "' deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchString(Scanner scanner) {
        System.out.print("Enter the string to search: ");
        String word = scanner.nextLine();
        boolean exists = dict.search(word);
        if (exists) {
            System.out.println("String '" + word + "' exists in the dictionary.");
        } else {
            System.out.println("String '" + word + "' does not exist in the dictionary.");
        }
    }

    private static void batchInsertFromFilename(Scanner scanner) throws IOException {
        System.out.print("Enter the path of the file containing the strings to insert: ");
        String filename = scanner.nextLine();
        int numNewStrings = 0;
        int numExistingStrings = 0;
        HashSet<String> existingStrings = new HashSet<String>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                if (existingStrings.contains(line)) {
                    System.out.println("String '" + line + "' already exists in the dictionary.");
                    numExistingStrings++;
                } else {
                    try {
                        dict.insert(line);
                        System.out.println("String '" + line + "' inserted successfully.");
                        numNewStrings++;
                    } catch (IllegalArgumentException e){
                        System.out.println("String '" + line + "' already exists in the dictionary.");
                        numExistingStrings++;
                    }
                    existingStrings.add(line);
                }
            }
        }
        reader.close();
        System.out.println(numNewStrings + " new strings inserted.");
        System.out.println(numExistingStrings + " strings already existed in the dictionary.");
    }

    private static void batchDeleteFromFilename(Scanner scanner) throws IOException {
        System.out.print("Enter the path of the file containing the strings to delete: ");
        String filename = scanner.nextLine();
        int numDeletedStrings = 0;
        int numNonExistingStrings = 0;
        HashSet<String> nonExistingStrings = new HashSet<String>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                if (nonExistingStrings.contains(line)) {
                    System.out.println("String '" + line + "' already deleted from the dictionary.");
                } else {
                    try {
                        dict.delete(line);
                        System.out.println("String '" + line + "' deleted successfully.");
                        numDeletedStrings++;
                    } catch (IllegalArgumentException e) {
                        System.out.println("String '" + line + "' does not exist in the dictionary.");
                        numNonExistingStrings++;
                        nonExistingStrings.add(line);
                    }
                }
            }
        }
        reader.close();
        System.out.println(numDeletedStrings + "strings deleted.");
        System.out.println(numNonExistingStrings + " strings did not exist in the dictionary.");
    }
}