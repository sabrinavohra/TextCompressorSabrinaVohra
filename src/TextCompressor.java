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
    public static final int BEGINNING_ADD = 256;

    private static void compress() throws IOException {
        // TODO: Complete the compress() method
        TST prefixes = new TST();
        for(int i = 0; i < BEGINNING_ADD; i++) {
            char j = (char) i;
            String toInsert = Character.toString(j);
            prefixes.insert(toInsert, i);
        }
        String text = BinaryStdIn.readString();
        int index = 0;
        int add = BEGINNING_ADD;
        while(index < text.length()) {
            int lookup = prefixes.lookup(text.substring(index, index + 1));
            String currentPrefix = "";
            while(lookup != TST.EMPTY) {
                currentPrefix += text.substring(index, index + 1);
                index++;
                lookup = prefixes.lookup(text.substring(index, index + 1));
            }
            if(lookup == TST.EMPTY) {
                prefixes.insert(currentPrefix, add);
                BinaryStdOut.write(prefixes.lookup(currentPrefix));
                add++;
            }
        }
        BinaryStdOut.close();
    }

    private static void expand() throws IOException {
        // TODO: Complete the expand() method
        String[] codes = new String[BEGINNING_ADD];
        for(int i = 0; i < BEGINNING_ADD; i++) {
            char j = (char) i;
            String toInsert = Character.toString(j);
            codes[i] = toInsert;
        }
        int add = BEGINNING_ADD;
        int current = BinaryStdIn.readInt(2);
        while(!BinaryStdIn.isEmpty()) {
            if(current < BEGINNING_ADD) {
                BinaryStdOut.write(codes[current]);
            }
            else {
                // How to use the next value to add?
            }
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
