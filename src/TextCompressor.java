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
    public static final int BYTE_SIZE = 10;
    public static final int MAX_SIZE = 1000;

    private static void compress() {
        // TODO: Complete the compress() method
        TST prefixes = new TST();
        for(int i = 0; i < BEGINNING_ADD; i++) {
            char j = (char) i;
            String toInsert = Character.toString(j);
            prefixes.insert(toInsert, i);
        }
        String text = BinaryStdIn.readString();
        int add = BEGINNING_ADD;
        int index = 0;
        while(index < text.length()) {
            String lookup = prefixes.getLongestPrefix(text, index);
            int code = prefixes.lookup(lookup);
            BinaryStdOut.write(code, BYTE_SIZE);
            int lookupLength = lookup.length();
            if(index + lookupLength < text.length() && add < MAX_SIZE) {
                prefixes.insert(text.substring(index, index + lookupLength + 1), add);
                add++;
            }
            index += lookupLength;
        }
        BinaryStdOut.write(EOF, BYTE_SIZE);
        BinaryStdOut.close();
    }

    private static void expand() {
        // TODO: Complete the expand() method
        String[] codes = new String[MAX_SIZE];
        for(int i = 0; i < BEGINNING_ADD; i++) {
            char j = (char) i;
            String toInsert = Character.toString(j);
            codes[i] = toInsert;
        }
        int add = BEGINNING_ADD;
        int current = BinaryStdIn.readInt(BYTE_SIZE);
        while(!BinaryStdIn.isEmpty()) {
            BinaryStdOut.write(codes[current]);
            int lookAhead = BinaryStdIn.readInt(BYTE_SIZE);
            String associate = codes[lookAhead];
            String toAdd = associate + codes[current];
            codes[add] = toAdd;
            add++;
            current = BinaryStdIn.readInt(BYTE_SIZE);
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
