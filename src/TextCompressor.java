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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *  The {@code TextCompressor} class provides static methods for compressing
 *  and expanding natural language through textfile input.
 *
 *  @author Zach Blick, Sabrina Vohra
 */
public class TextCompressor {

    private static void compress() throws IOException {
        // TODO: Complete the compress() method
        TST prefixes = new TST();
        String text = BinaryStdIn.readString();
        int index = 0;
        int add = 0;
        while(index < text.length()) {
            int lookup = prefixes.lookup(text.substring(index, index + 1));
        }
        BinaryStdOut.close();
    }

    private static void expand() throws IOException {
        // TODO: Complete the expand() method
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
