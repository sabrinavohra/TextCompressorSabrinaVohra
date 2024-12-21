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

/**
 *  The {@code TextCompressor} class provides static methods for compressing
 *  and expanding natural language through textfile input.
 *
 *  @author Zach Blick, Sabrina Vohra
 */
public class TextCompressor {
    public static final int BEGINNING_ADD = 257;
    public static final int EOF = 256;
    public static final int BYTE_SIZE = 16;
    public static final int MAX_SIZE = 1000;

    private static void compress() {
        TST prefixes = new TST();
        // Adds all letters into a TST
        for(int i = 0; i < BEGINNING_ADD; i++) {
            char j = (char) i;
            String toInsert = Character.toString(j);
            prefixes.insert(toInsert, i);
        }
        String text = BinaryStdIn.readString();
        int add = BEGINNING_ADD;
        int index = 0;
        while(index < text.length()) {
            // Looks up longest prefix that matches
            String lookup = prefixes.getLongestPrefix(text, index);
            // Writes code out from looked up prefix
            BinaryStdOut.write(prefixes.lookup(lookup), BYTE_SIZE);
            // Adds prefix to TST / next character
            if(index + lookup.length() < text.length() && add < MAX_SIZE) {
                prefixes.insert(text.substring(index, index + lookup.length() + 1), add);
                add++;
            }
            // Moves to next character
            index += lookup.length();
        }
        BinaryStdOut.write(EOF, BYTE_SIZE);
        BinaryStdOut.close();
    }

    private static void expand() {
        // Adds prefixes into array
        String[] codes = new String[MAX_SIZE];
        for(int i = 0; i < BEGINNING_ADD; i++) {
            char j = (char) i;
            String toInsert = Character.toString(j);
            codes[i] = toInsert;
        }
        int add = BEGINNING_ADD;
        int current = BinaryStdIn.readInt(BYTE_SIZE);
        // Ends code if file has ended
        if(current == EOF) {
            return;
        }
        String currentString = codes[current];
        while(!BinaryStdIn.isEmpty()) {
            BinaryStdOut.write(currentString);
            // Reads in next String and checks to make sure the file has not ended
            int lookAhead = BinaryStdIn.readInt(BYTE_SIZE);
            if (lookAhead == EOF) {
                break;
            }
            String next;
            // Initializes next code as next variable
            if (lookAhead < add) {
                next = codes[lookAhead];
            }
            // Takes care of edge case
            else {
                next = currentString + currentString.charAt(0);
            }
            // Adds current String and first character of the next String into the array
            if(add < codes.length) {
                codes[add] = currentString + next.charAt(0);
                add++;
            }
            // Makes the current String the next to increment
            currentString = next;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
