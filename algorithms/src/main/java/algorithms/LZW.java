package algorithms; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.lang.Math; 

public class LZW {

    public static ArrayList<Integer> encoded = new ArrayList<>(); 
    public static int bitLength = 12; 
    public static int maxDictSize = (int) Math.pow(2, bitLength); 
    // public static int maxDictSize = Integer.MAX_VALUE; 

    // Initialize the standard character HashMap for codes 0 to 256 
    public static HashMap<String, Integer> createCompressDictionary() {
        HashMap<String, Integer> dict = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            String code = Character.toString((char) i); 
            dict.put(code, i);
        }
        return dict; 
    }

    // Initialize the standard character HashMap for codes 0 to 256 
    public static HashMap<Integer, String> createUncompressDictionary() {
        HashMap<Integer, String> dict = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            String code = Character.toString((char) i); 
            dict.put(i, code);
        }
        return dict; 
    }

        // Looks for reoccuring patterns. If patterns are occuring more than once
        // Assign a new code for the pattern by incrementing starting from 256
        // Then add new code to the existing HashMap 

    public void compress(String input) {

        int inputChar = 256;        

        HashMap<String, Integer> dict = createCompressDictionary(); 
        
        char[] inputArr = input.toCharArray(); 
        String currentString = Character.toString(inputArr[0]);
        String nextChar = "";

        for (int i = 0; i < input.length(); i++) {

            if (i < input.length()-1) {
                nextChar = Character.toString(inputArr[i+1]);
            }

            String nextRead = currentString + nextChar; 
            
            if (dict.containsKey(nextRead)) {
                currentString = nextRead; 
            }

            else {

                if (inputChar < maxDictSize) {
                    dict.put(nextRead, inputChar);
                    inputChar++; 
                }

                // if (i == input.length()-1) {
                //     currentString = nextChar;
                // }   
                encoded.add(dict.get(currentString));
                
                currentString = nextChar; 
            }
        }
        // System.out.println("Encoded   --> " + encoded.toString());
        
    }

    public String uncompress(ArrayList<Integer> encoded) {


        // System.out.println("Decoded   --> " + encoded.toString());

        HashMap<Integer, String> dict = createUncompressDictionary(); 

        // Get first code 
        int inputCode = encoded.get(0); 

        // Get output of first code
        String currentString = dict.get(inputCode); 

        // Helper to form letter combinations
        String previousCharacter = ""; 

        // Output 
        String decodedString = currentString;

        int inputChar = 256; 

        for (int i = 0; i < encoded.size()-1; i++) {

            int nextCode = encoded.get(i+1); 
             
            //If dictionary doesn't contain the value of nextcode
            //It means we've encountered a reoccuring pattern (Its code value is higher than 256)
            //This means we have to add the previousCharacter to currentString
            //To reproduce a letter combination for the pattern

            if (!dict.containsKey(nextCode)) {
                currentString = dict.get(inputCode);
                currentString += previousCharacter; 
            }

            else {
                currentString = dict.get(nextCode); 
            }

            // System.out.println("IC : " + inputCode +  " NC : " + nextCode + " Dict : " + dict.size());

            // System.out.println("Current : " + currentString);
            //Append currentString to form decoded message
            decodedString += currentString;
            
            // Form a letter combination
            previousCharacter = "" + currentString.charAt(0);
            
            if (inputChar < maxDictSize) {
                //Put the letter combination into the dictionary
                //This will enable reusing letter combinations that have been encountered before
                dict.put(inputChar, dict.get(inputCode) + previousCharacter);
                // starts from 256 now 257
                inputChar++;
            }
                inputCode = nextCode;
                // System.out.println("InputChar : " + inputChar + " Dict: " + dict.size());

        }
        return decodedString; 
    }

    public static void writeLZWFile(String outputName) throws java.io.IOException {
        ArrayList<Integer> input = encoded; 

        StringBuilder bitBuilder = new StringBuilder(); 
        for (int i = 0; i < input.size(); i++) { 
            String bit = FileUtils.intToBits(input.get(i),bitLength);
            bitBuilder.append(bit); 
        }
        
        byte[] bytes = new byte[input.size()];

        bytes = FileUtils.bitsToByte(new String(bitBuilder));     
        FileUtils.writeFile(outputName, bytes);
    }

    public static void readLZWFile(String inputName) throws java.io.IOException {
        byte[] bytes = FileUtils.readFile(inputName); 
        String test = FileUtils.bytesToBits(bytes);       
        
        encoded = new ArrayList<>(); 
        
        StringBuilder bitBuilder = new StringBuilder(); 

        for (int i = 0; i < test.length(); i++) { 
            bitBuilder.append(test.charAt(i)); 
            
            if (bitBuilder.length() == bitLength) {
                encoded.add(FileUtils.bitsToInt(bitBuilder.toString())); 
                bitBuilder = new StringBuilder(); 
            }
        }
    }

    public ArrayList<Integer> getEncoded() {
        return encoded; 
    }

    public static void main(String[] args) throws java.io.IOException {
        
        LZW lzw = new LZW();
        String input = "A brown fox jumps quickly over the lazy dog";

        
        // input = "aku ankka on kiva ankka testataan ypela"; 
        // input = "aku ankka aku ankka omena";
        // input = "aku ankka aku ankka aku ankka";
        // input = "ABRACADABRABRABRA"; 
        
        // input = "123456789112"; 
        // input = "cScSc cScSc";
        // input = "alkjdsöfajsöldjfadölajsölfdjalkdsfajdlewkfjqewlk";
        // input = "ABABABA";
        // input = "ABABABABABABABABABA";
        // input = "ABABABA OTOTOTO";

        // input = FileUtils.fileReaderOutput("100_KB_lorem.txt");
        // input = FileUtils.fileReaderOutput("Large Lorem.txt");
        input = FileUtils.fileReaderOutput("100_KB_repeating_lorem_ipsum.txt");
        
        // long start = System.nanoTime(); 
        lzw.compress(input);
        writeLZWFile("lzwTest.lzw");
        // long end = System.nanoTime();
        // System.out.println("Time taken: " + (end-start)/1E9 + " s");

        readLZWFile("lzwTest.lzw");

        String output = lzw.uncompress(encoded);
        System.out.println(input.length());
        System.out.println(output.length());
        System.out.println("ENCODE-DECODE OUTCOME : " + input.equals(output));
        
        // lzw.compress("ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ");
        // lzw.compress("1234568790");

        // lzw.compress("Lorem ipsum Lorem Lorem Lorem Lorem");
        // lzw.compress("1234");        
        // Testing a known edge case
        // lzw.compress("cScSc"); 
    }   
}
