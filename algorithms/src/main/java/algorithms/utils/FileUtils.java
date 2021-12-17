package algorithms.utils; 

import java.io.File; 
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class FileUtils {

    public static byte[] byteArray;
    public static String stringInput; 
    
    public static void writeFile(String outputName, byte[] bytes) throws java.io.IOException {
        FileOutputStream outputStream = new FileOutputStream("./" + outputName);
        outputStream.write(bytes); 
        outputStream.close();
    }

    public static byte[] readFile(String inputName) throws java.io.IOException {
        FileInputStream inputStream = new FileInputStream(inputName);
        byte[] byteArray = inputStream.readAllBytes(); 
        inputStream.close();
        return byteArray; 
    }

    public static String readTextFile(String fileName) {
        StringBuffer inputBuffer = new StringBuffer(); 
    
        try {
            Files.lines(Paths.get(fileName)).forEach(newLine -> inputBuffer.append(newLine));
        } catch (Exception e) {
            System.out.println("Error! Could not read file : " + e.getMessage());
        }        

        stringInput = new String(inputBuffer); 
        return stringInput; 
    }

    public static void writeTextFile(String fileName, String output) {
        try {
            FileWriter file = new FileWriter(fileName + ".txt"); 
            file.write(output); 
            file.close();
        } catch (Exception e) {
            System.out.println("Error! Could not write file : " + e.getMessage());
        }
    }
    
    

    public static byte[] bitsToByte(String input) {
        int length = input.length();
        byte[] bytes = new byte[(int) Math.ceil(length / 8.0)+1];
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
    
    public static String bytesToBits(byte[] bytes ) {

        StringBuilder bitBuilder = new StringBuilder(); 

        int lastLength = bytes[0];
        int bitLength = 8; 
                   
        for (int i = 1; i < bytes.length; i++) {

            byte b = bytes[i];
        
            if (i == bytes.length-1) {
                bitLength = lastLength == 0 ? 8 : lastLength; 
            }

            for (int j = 7; j >= 8-bitLength; j--) {  
                bitBuilder.append((b >> j) & 1);
            }        
            
        }
                
        return new String(bitBuilder);
    }

    public static String intToBits(int input, int bitLength) { 
        StringBuilder bitBuilder = new StringBuilder(); 

        for (int j = bitLength-1; j >= 0; j--) {  
            bitBuilder.append((input >> j) & 1);
        }        
        return bitBuilder.toString();
    }
    
    public static int bitsToInt(String bits) {

        int output = 0; 
        int bitLength = bits.length()-1; 

        for (int i = bitLength; i >= 0; i--) { 
            if (bits.charAt(i) == '1') {
                output += Math.pow(2, bitLength-i);
            }   
        }
    
        return output; 
    }

    public static byte[] getByteArray() {
        byteArray = stringInput.getBytes(StandardCharsets.UTF_8);
        return byteArray; 
    }
    public static void main(String[] args) {

    }



}


