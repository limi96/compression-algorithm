package algorithms; 

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math; 

import algorithms.utils.FileUtils;

public class LZW {

    public static ArrayList<Integer> encoded; 
    public static String encodedBits = ""; 
    public static int bitLength = 12; 
    public static int maxDictSize = (int) Math.pow(2, bitLength); 
    public static StringBuffer outputBitBuffer; 

    // Looks for reoccuring patterns. If patterns are occuring more than once
    // Assign a new code for the pattern by incrementing starting from 256
    // Then add new code to the existing HashMap 

    public void setBitLength(int inputBitLength) {
        bitLength = inputBitLength; 
        maxDictSize = (int) Math.pow(2, bitLength); 
    }

    public String compress(String input) {
        int inputChar = 256;        
        encoded = new ArrayList<>(); 
        HashMap<String, Integer> dict = createCompressDictionary(); 
        String currentString = "" + input.charAt(0); 
        String nextChar = "";

        for (int i = 0; i < input.length(); i++) {
            nextChar = i < input.length() - 1 ? "" + input.charAt(i + 1) : "";  
            String pattern = currentString + nextChar; 
            
            if (dict.containsKey(pattern)) {
                currentString = pattern; 
            } else {
                if (inputChar < maxDictSize) {
                    dict.put(pattern, inputChar);
                    inputChar++; 
                }
                encoded.add(dict.get(currentString));
                currentString = nextChar; 
            }
        }
        encoded.add(dict.get(currentString)); 
        return constructEncodedMessage(); 
    }

    public String constructEncodedMessage() {

        ArrayList<Integer> input = encoded; 

        outputBitBuffer = new StringBuffer();

        for (int i = 0; i < input.size(); i++) {
            String bit = FileUtils.intToBits(input.get(i), bitLength);
            outputBitBuffer.append(bit); 
        }

        return outputBitBuffer.toString(); 
    }

    public void writeLZWFile(String outputName) throws java.io.IOException {
        ArrayList<Integer> input = encoded; 

        byte[] bytes = new byte[input.size()];

        bytes = FileUtils.bitsToByte(new String(outputBitBuffer));     
        FileUtils.writeFile(outputName + ".lzw", bytes);
    }

    
    // File I/O
    public void readLZWFile(String inputName) throws java.io.IOException {
        byte[] bytes = FileUtils.readFile(inputName); 
        String input = FileUtils.bytesToBits(bytes);               
        decodeLZWEncodedString(input);
    }

    public void decodeLZWEncodedString(String input) {

        encoded = new ArrayList<>(); 
        
        StringBuffer bitBuffer = new StringBuffer(); 
        outputBitBuffer = new StringBuffer(); 

        for (int i = 0; i < input.length(); i++) { 
            outputBitBuffer.append(input.charAt(i)); 
            bitBuffer.append(input.charAt(i)); 

            if (bitBuffer.length() == bitLength) {
                encoded.add(FileUtils.bitsToInt(bitBuffer.toString())); 
                bitBuffer = new StringBuffer(); 
            }
        }
    }

    public String uncompress(ArrayList<Integer> encoded) {

        HashMap<Integer, String> dict = createUncompressDictionary(); 
        
        // Get first code 
        int inputCode = encoded.get(0); 

        // Get output of first code
        String currentString = dict.get(inputCode); 

        // Helper to form letter combinations
        String previousCharacter = ""; 

        // Output 
        StringBuffer outputBuffer = new StringBuffer(); 
        outputBuffer.append(currentString); 
        String decodedString = currentString; 
        int inputChar = 256; 

        for (int i = 0; i < encoded.size() - 1; i++) {

            int nextCode = encoded.get(i + 1); 
             
            //If dictionary doesn't contain the value of nextcode
            //It means we've encountered a reoccuring pattern (Its code value is higher than 256)
            //This means we have to add the previousCharacter to currentString
            //To reproduce a letter combination for the pattern

            if (!dict.containsKey(nextCode)) {
                currentString = dict.get(inputCode);
                currentString += previousCharacter; 
            } else {
                currentString = dict.get(nextCode); 
            }
            
            //Append currentString to form decoded message

            outputBuffer.append(currentString); 
            // decodedString += currentString; 
            
            // Form a letter combination
            previousCharacter = "" + currentString.charAt(0);
            
            if (inputChar < maxDictSize) {
                //Put the letter combination into the dictionary
                //This will enable reusing letter combinations that have been encountered before
                dict.put(inputChar, dict.get(inputCode) + previousCharacter);
                inputChar++;
            }
            inputCode = nextCode;
        }
        return outputBuffer.toString(); 
        // return decodedString;
    }

    // Initialize the standard character HashMap for codes 0 to 256 
    public static HashMap<String, Integer> createCompressDictionary() {
        HashMap<String, Integer> dict = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            String code = Character.toString((char) i); 
            dict.put(code, i);
        }
        return dict; 
    }

    public static HashMap<Integer, String> createUncompressDictionary() {
        HashMap<Integer, String> dict = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            String code = Character.toString((char) i); 
            dict.put(i, code);
        }
        return dict; 
    }

    public ArrayList<Integer> getEncoded() {
        return encoded; 
    }

    public String getEncodedAsBits() {
        return outputBitBuffer.toString(); 
    }


}
