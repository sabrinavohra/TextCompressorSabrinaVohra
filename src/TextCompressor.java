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
        ArrayList<String> words = new ArrayList<>();
        String[] theWords = new String[1000];
        FileReader fr = new FileReader("commonWords.txt");
        BufferedReader br = new BufferedReader(fr);
        for(int i = 0; i < theWords.length; i++) {
            String currentWord = br.readLine();
            words.add(currentWord);
        }
        ArrayList<String> letters = new ArrayList<String>();
        FileReader letterFr = new FileReader("letters.txt");
        BufferedReader letterBr = new BufferedReader(letterFr);
        for(int i = 0; i < 26; i++) {
            String currentLetter = letterBr.readLine();
            letters.add(currentLetter);
        }
        while(!BinaryStdIn.isEmpty()) {
            String file = BinaryStdIn.readString();
            String[] splitUp = file.split(" ");
            for(String word: splitUp) {
                if(!words.contains(word)) {
                    for(int i = 0; i < word.length(); i++) {
                        BinaryStdOut.write('l');
                        BinaryStdOut.write(word.length(), 10);
                        BinaryStdOut.write(letters.indexOf(word.substring(i, i + 1)), 10);
                    }
                }
                else {
                    int index = words.indexOf(word);
                    BinaryStdOut.write('s');
                    BinaryStdOut.write(index, 10);
                }
            }
        }
        // Make a map and assign a new integer value to each new word (8 bits/word rather than 8 bits/char)
        // Add each new word to the map
        // Print out the new value of the word according to the map
        BinaryStdOut.close();
    }

    private static void expand() throws IOException {
        // TODO: Complete the expand() method
        ArrayList<String> words = new ArrayList<>();
        FileReader fr = new FileReader("commonWords.txt");
        BufferedReader br = new BufferedReader(fr);
        for(int i = 0; i < 1000; i++) {
            String currentWord = br.readLine();
            words.add(currentWord);
        }
        ArrayList<String> letters = new ArrayList<String>();
        FileReader letterFr = new FileReader("letters.txt");
        BufferedReader letterBr = new BufferedReader(letterFr);
        for(int i = 0; i < 26; i++) {
            String currentLetter = letterBr.readLine();
            letters.add(currentLetter);
        }
        while(!BinaryStdIn.isEmpty()) {
            char start = BinaryStdIn.readChar();
            if(start == 'l') {
                int length = BinaryStdIn.readInt(10);
                for(int i = 0; i < length; i++) {
                    int current = BinaryStdIn.readInt(10);
                    String theLetter = letters.get(current);
                    BinaryStdOut.write(theLetter);
                }
            }
            if(start == 's') {
                int index = BinaryStdIn.readInt(10);
                String theCommonWord = words.get(index);
                BinaryStdOut.write(theCommonWord);
            }
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
