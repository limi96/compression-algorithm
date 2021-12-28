package algorithms.utils; 

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class FileUtils {

    /**
     * Write a file output after encoding an input
     * 
     * @param outputName Desired file name for the output
     * @param bytes Input byte array to be saved as a file
     * @throws java.io.IOException
     */
    public static void writeFile(String outputName, byte[] bytes) throws java.io.IOException {
        FileOutputStream outputStream = new FileOutputStream("./" + outputName);
        outputStream.write(bytes); 
        outputStream.close();
    }

    /**
     * Reads the file containing the encoded messages 
     * @param inputName The name of the saved file
     * @return A byte array of the data from the saved file
     * @throws java.io.IOException
     */
    public static byte[] readFile(String inputName) throws java.io.IOException {
        FileInputStream inputStream = new FileInputStream(inputName);
        byte[] byteArray = inputStream.readAllBytes(); 
        inputStream.close();
        return byteArray; 
    }

    /**
     * Constructs a String output given a text file
     * @param fileName File name of the text file
     * @return String output
     */
    public static String readTextFile(String fileName) {
        String output = ""; 
    
        try {
            output = Files.readString(Paths.get(fileName));
        } catch (Exception e) {
            System.out.println("Error! Could not read file : " + e.getMessage());
        }        

        return output; 
    }
    /**
     * Writes the given String input into a text file
     * @param fileName The desired name of the output text file
     * @param input String input
     */
    public static void writeTextFile(String fileName, String input) {
        try {
            FileWriter file = new FileWriter(fileName + ".txt"); 
            file.write(input); 
            file.close();
        } catch (Exception e) {
            System.out.println("Error! Could not write file : " + e.getMessage());
        }
    }
    
    /**
     * Converts the given binary String into a byte array
     * @param input binary String
     * @return resulting byte Array
     */
    public static byte[] bitsToByte(String input) {
        int length = input.length();
        byte[] bytes = new byte[(int) Math.ceil(length / 8.0) + 1];
        int byteCounter = 1;
        int bitPointer = 0;
        int currentByte = 0;

        while (bitPointer < length) {
            currentByte = currentByte * 2;
            if (input.charAt(bitPointer) == '1') {
                currentByte++;
            }
            bitPointer++;
            if (bitPointer % 8 == 0 || bitPointer == length) {
                if (bitPointer % 8 != 0) {
                    for (int i = 0; i < (8 - length % 8); i++) {
                        currentByte = currentByte * 2;
                    }
                }
                bytes[byteCounter] = (byte) currentByte;
                currentByte = 0;
                byteCounter++;
            }
        }

        int lastByteLength = (bitPointer) % 8; 
        bytes[0] = (byte) (lastByteLength); 
        
        return bytes;
    }
    
    /**
     * Converts a given byte into a binary String with each byte encoded into 8 bits
     * @param bytes Input byte array
     * @return binary String 
     */
    public static String bytesToBits(byte[] bytes) {

        StringBuffer bitBuffer = new StringBuffer(); 

        int lastLength = bytes[0];
        int bitLength = 8; 
                   
        for (int i = 1; i < bytes.length; i++) {

            byte b = bytes[i];
        
            if (i == bytes.length - 1) {
                bitLength = lastLength == 0 ? 8 : lastLength; 
            }

            for (int j = 7; j >= 8 - bitLength; j--) {  
                bitBuffer.append((b >> j) & 1);
            }                    
        }
                
        return new String(bitBuffer);
    }

    /**
     * Converts the Java primitive data type int, from 32 bits to the desired bitLength
     * @param input The given 32-bit int input
     * @param bitLength The desired bitLength conversion
     * @return binary String 
     */
    public static String intToBits(int input, int bitLength) { 
        StringBuffer bitBuffer = new StringBuffer(); 

        for (int j = bitLength - 1; j >= 0; j--) {  
            bitBuffer.append((input >> j) & 1);
        }        
        return bitBuffer.toString();
    }
    /**
     * Converts a given bit back into a Java int of 32 bits. 
     * @param bits binary String input
     * @return int output
     */
    public static int bitsToInt(String bits) {

        int output = 0; 
        int bitLength = bits.length() - 1; 

        for (int i = bitLength; i >= 0; i--) { 
            if (bits.charAt(i) == '1') {
                output += Math.pow(2, bitLength - i);
            }   
        }
    
        return output; 
    }
}


