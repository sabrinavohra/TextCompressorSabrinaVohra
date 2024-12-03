/******************************************************************************
 *  Compilation:  javac TextCompressor.java
 *  Execution:    java TextCompressor - < input.txt   (compress)
 *  Execution:    java TextCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   abra.txt
 *                jabberwocky.txt
 *                shakespeare.txt
 *                virus.txt
 *
 *  % java DumpBinary 0 < abra.txt
 *  136 bits
 *
 *  % java TextCompressor - < abra.txt | java DumpBinary 0
 *  104 bits    (when using 8-bit codes)
 *
 *  % java DumpBinary 0 < alice.txt
 *  1104064 bits
 *  % java TextCompressor - < alice.txt | java DumpBinary 0
 *  480760 bits
 *  = 43.54% compression ratio!
 ******************************************************************************/

import java.util.ArrayList;

/**
 *  The {@code TextCompressor} class provides static methods for compressing
 *  and expanding natural language through textfile input.
 *
 *  @author Zach Blick, Sabrina Vohra
 */
public class TextCompressor {
    private static ArrayList<String> words;

    private static void compress() {
        // TODO: Complete the compress() method
        words = new ArrayList<>();
        String file = BinaryStdIn.readString();
        String[] splitUp = file.split(" ");
        for(String word: splitUp) {
            if(!words.contains(word)) {
                words.add(word);
                int index = words.indexOf(word);
                BinaryStdOut.write(index, 16);
            }
            else {
                int index = words.indexOf(word);
                BinaryStdOut.write(index, 16);
            }
        }
        // Make a map and assign a new integer value to each new word (8 bits/word rather than 8 bits/char)
        // Add each new word to the map
        // Print out the new value of the word according to the map
        BinaryStdOut.close();
    }

    private static void expand() {
        // TODO: Complete the expand() method
        while(!BinaryStdIn.isEmpty()) {
            int binary = BinaryStdIn.readInt(16);
            String theWord = words.get(binary);
            BinaryStdOut.write(theWord + " ");
        }
        // Use the map to find the word that corresponds to the integer value
        // Print out the word that corresponds--do this for every word
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
