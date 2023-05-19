import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Dictionary {
    private PerfectHashing hashTable;

    public Dictionary(int[] words) {
        hashTable = new PerfectHashing(words, 8); // use 8-bit hash values
    }

    public void insert(String word) {
        int index = hashTable.get(word.hashCode());
        if (index == -1) {
            throw new IllegalArgumentException("Word already exists in dictionary");
        }
        // store the word at the computed index
    }

    public void delete(String word) {
        int index = hashTable.get(word.hashCode());
        if (index == -1) {
            throw new IllegalArgumentException("Word does not exist in dictionary");
        }
        // delete the word at the computed index
    }

    public boolean search(String word) {
        int index = hashTable.get(word.hashCode());
        return index != -1 && // word exists in dictionary
                // check if the word at the computed index is equal to the searched word
                word.equals(getWordAtIndex(index));
    }

    public void batchInsert(String filename) throws IOException {
        HashSet<String> existingWords = new HashSet<String>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                if (existingWords.contains(line)) {
                    // word already exists in dictionary
                    System.out.println("Word '" + line + "' already exists in dictionary");
                } else {
                    try {
                        insert(line);
                        System.out.println("Inserted word '" + line + "'");
                    } catch (IllegalArgumentException e) {
                        // word already exists in dictionary
                        System.out.println("Word '" + line + "' already exists in dictionary");
                    }
                    existingWords.add(line);
                }
            }
        }
        reader.close();
    }

    public void batchDelete(String filename) throws IOException {
        int numDeleted = 0;
        int numNonexisting = 0;
        HashSet<String> existingWords = new HashSet<String>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                if (existingWords.contains(line)) {
                    // word already deleted
                    System.out.println("Word '" + line + "' already deleted from dictionary");
                } else {
                    try {
                        delete(line);
                        System.out.println("Deleted word '" + line + "'");
                        numDeleted++;
                    } catch (IllegalArgumentException e) {
                        // word does not exist in dictionary
                        System.out.println("Word '" + line + "' does not exist in dictionary");
                        numNonexisting++;
                    }
                    existingWords.add(line);
                }
            }
        }
        reader.close();
        System.out.println("Deleted " + numDeleted + " words");
        System.out.println(numNonexisting + " words did not exist in dictionary");
    }

    private String getWordAtIndex(int index) {
        // retrieve the word at the computed index
    }
}