package algorithms; 

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math; 

import algorithms.utils.FileUtils;

public class LZW {

    /**
     * Global variable for passing on the encoded message
     */
    public static ArrayList<Integer> encoded; 
    /**
     * Global variable for building a bitString
     */
     public static StringBuffer outputBitBuffer; 

    /**
     * Please see the method setBitLength for further details
     */
    public static int bitLength = 12; 
    public static int maxDictSize = (int) Math.pow(2, bitLength); 


    /**
     * Adjusts the length of bits per encoded message element. 
     * Default is set at 12, but can be changed from 8 to 32 bits. 
     * The higher number of bits, the higher the maximum dictionary size that stores reoccuring patterns
     * With messages of large numbers of frequently occuring patterns, the higher the better. 
     * However, this will result in a longer encoded message. 
     * Nevertheless, the optimal bit length is usually around 9-12 bits. 
     * @param inputBitLength
     */

    public void setBitLength(int inputBitLength) {
        bitLength = inputBitLength; 
        maxDictSize = (int) Math.pow(2, bitLength); 
    }

    /**
     *  Looks for reoccuring patterns. If patterns are occuring more than once
     *  Assign a new code for the pattern by incrementing starting from 256
     *  Then add new code to the existing HashMap
     *  
     *    @param input String -input to be encoded
     */

    public String compress(String input) {

        int inputChar = 256;        
        HashMap<String, Integer> dict = createCompressDictionary(); 

        //Output
        encoded = new ArrayList<>(); 

        //Denotes the current part of the input that is processed
        String currentString = "" + input.charAt(0); 
        
        //Looks at the next character of the input
        String nextChar = "";

        for (int i = 0; i < input.length(); i++) {
            nextChar = i < input.length() - 1 ? "" + input.charAt(i + 1) : "";  

            //Form a String pattern from the input
            String pattern = currentString + nextChar; 

            if (dict.containsKey(pattern)) {
                currentString = pattern; 
            } else {
                //Put the letter combination into the dictionary
                //This will enable reusing letter combinations that have been encountered before
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

    /**
     *  Writes the encoded output as an .lzw-file. Reads the input from the global arraylist variable called "encoded"
     *  @param outputName the desired name for the output file
     */
    public void writeLZWFile(String outputName) throws java.io.IOException {
        ArrayList<Integer> input = encoded; 

        byte[] bytes = new byte[input.size()];

        bytes = FileUtils.bitsToByte(new String(outputBitBuffer));     
        FileUtils.writeFile(outputName + ".lzw", bytes);
    }

    /**
     *  Reads the encoded.lzw-file and outputs it as a global arraylist variable called "encoded"
     *  The method itself forms a String output from the given file name. The actual formation of the String
     *  to the variable "encoded" is performed by the method "decodeLZWEncodedString"
     *  @param inputName The file name of the input file. 
     */

    public void readLZWFile(String inputName) throws java.io.IOException {
        byte[] bytes = FileUtils.readFile(inputName); 
        String input = FileUtils.bytesToBits(bytes);               
        decodeLZWEncodedString(input);
    }

    /**
     * The method that does the actual conversion from a given String input into Bytes and finally 
     * as the global arraylist variable "Encoded"
     * @param input the encoded binary String input
     */

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

    /**
     *  Decodes the encoded message to the original message. 
     *  If dictionary doesn't contain the value of nextcode
     *  It means we've encountered a reoccuring pattern (Its code value is higher than 256)
     *  This means we have to add the previousCharacter to currentString
     *  To reproduce a letter combination for the pattern
     *  Put the letter combination into the dictionary
     * This will enable reusing letter combinations that have been encountered before
     *  
     */

    public String uncompress(ArrayList<Integer> encoded) {

        HashMap<Integer, String> dict = createUncompressDictionary(); 
        int inputChar = 256; 
        
        // Get first code 
        int inputCode = encoded.get(0); 

        // Get output of first code
        String currentString = dict.get(inputCode); 

        // Helper to form letter combinations
        String previousChar = ""; 

        // Output 
        StringBuffer outputBuffer = new StringBuffer(); 
        outputBuffer.append(currentString); 
        // String decodedString = currentString; 

        for (int i = 0; i < encoded.size() - 1; i++) {

            int nextCode = encoded.get(i + 1); 
    
            if (!dict.containsKey(nextCode)) {
                currentString = dict.get(inputCode);
                currentString += previousChar; 
            } else {
                currentString = dict.get(nextCode); 
            }
            
            //Append currentString to form decoded message

            outputBuffer.append(currentString); 
            // decodedString += currentString; 
            
            // Form a letter combination
            previousChar = "" + currentString.charAt(0);
            
            //Put the letter combination into the dictionary
            //This will enable reusing letter combinations that have been encountered before
            if (inputChar < maxDictSize) {
                dict.put(inputChar, dict.get(inputCode) + previousChar);
                inputChar++;
            }
            inputCode = nextCode;
        }
        return outputBuffer.toString(); 
        // return decodedString;
    }

    /**
     * Initialize the standard character HashMap for codes 0 to 256 for compression
     * @return HashMap dictionary of the ASCII 256 codes and their respective characters
     */
    
    public static HashMap<String, Integer> createCompressDictionary() {
        HashMap<String, Integer> dict = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            String code = Character.toString((char) i); 
            dict.put(code, i);
        }
        return dict; 
    }

    /**
     * Initialize the standard character HashMap for codes 0 to 256 for uncompressing
     * @return HashMap dictionary of the ASCII 256 codes and their respective characters
     */
    
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
