import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Go to CompressionBenchmark and run to see the compression rates.
        // Go to PerformanceBenchmark and run to see the performance speeds.mvn
        // PerformanceBenchmark.main(args);
        // Huffman.main(args);

        //Tarkistetaan, että saadaan bitit tavuiksi ja tavut takas biteiksi
        String input = "11111111000000110101010";
        int numberOfWrongResults = 0; 
        Random rnd = new Random();     
        for (int i = 0; i < 10; i++) { 
            StringBuilder inputBuilder = new StringBuilder(); 
            for (int j = 0; j < 10 + rnd.nextInt(1000); j++) {
                inputBuilder.append(rnd.nextInt(2) % 2 == 0 ? "0" : "1"); 
            }
            input = new String(inputBuilder); 
            // String reverseInput = bytesToBits(bitsToByte(input));
            String reverseInput = bytesToBits(bitsToByte(input, 8, true), 8, true);
            boolean testResult = input.equals(reverseInput);
            if (!testResult) {
                // System.out.println();
                // System.out.println("Result : " + testResult);
                // System.out.println(input);
                // System.out.println(reverseInput);
                // System.out.println("Length Difference : " + (input.length()-reverseInput.length()));
                // System.out.print((input.length()-reverseInput.length()));
                numberOfWrongResults++;
            }
        }
        System.out.println("Finished -- Wrong results: " + numberOfWrongResults);


        // Test for converting java ints to 12-bit numbers
        // for (int i = 0; i < 4095; i++) { 
        //     if (tester(i,12).equals(my(i))) {
        //         // System.out.println("ok");
        //     }
        //     else {
        //         System.out.println("no");
        //     }
        // }
    

        // for (int i = 0; i < 4095; i++) { 
        //     if (i == bitsToInt(intToBits(i))) {
        //         // System.out.print("ok");
        //     }
        //     else {
        //         System.out.println("no");
        //     }
        // }
    }   

    public static byte[] bitsToByteLZW(String input, int bitLength) {
        int length = input.length();
        byte[] bytes = new byte[(int) Math.ceil((double)length / bitLength)+1];

        int byteCounter = 1;
        // int byteCounter = huffman ? 1 : 0;
        int bitPointer = 0;
        int currentByte = 0;

        while (bitPointer < length) {
            currentByte = currentByte * 2;
            if (input.charAt(bitPointer) == '1') {
                currentByte++;
            }
            bitPointer++;
            if (bitPointer % bitLength == 0 || bitPointer == length) {
                if (bitPointer % bitLength != 0) {
                    for (int i = 0; i < (bitLength - length % bitLength); i++) {
                        currentByte = currentByte * 2;
                    }
                }
                bytes[byteCounter] = (byte) currentByte;
                currentByte = 0;
                byteCounter++;
            }
        }

        int lastByteLength = (bitPointer) % bitLength; 
        
        // bytes[0] = huffman ? (byte) (lastByteLength) : bytes[0]; 
        bytes[0] = (byte) (lastByteLength);
        
        return bytes;
    }
    


    //Works fine now
    //Although bitsToByte is now wrong lmao 
    public static String bytesToBits(byte[] bytes, int bitLength, boolean huffman) {

        StringBuilder bitBuilder = new StringBuilder(); 

        // int lastLength = bytes[0];
        // bitLength = 8; 

        int lastLength = 0; 
        int startIndex = 0;
        
        int bitLengthConstant = bitLength; 

        if (huffman) {
            startIndex = 1; 
            lastLength = bytes[0];
        }

        for (int i = startIndex; i < bytes.length; i++) {

            byte b = bytes[i];
        
            if (i == bytes.length-1 && huffman) {
                bitLength = lastLength == 0 ? bitLength : lastLength; 
            }

            for (int j = bitLengthConstant-1; j >= bitLengthConstant-bitLength; j--) {  
                bitBuilder.append((b >> j) & 1);
            }        

        }
                
        return new String(bitBuilder);
    }


    public static byte[] bitsToByte(String input, int bitLength, boolean huffman) {
        int length = input.length();
        byte[] bytes = new byte[(int) Math.ceil((double)length / bitLength)+1];

        int byteCounter = 1;
        // int byteCounter = huffman ? 1 : 0;
        int bitPointer = 0;
        int currentByte = 0;

        while (bitPointer < length) {
            currentByte = currentByte * 2;
            if (input.charAt(bitPointer) == '1') {
                currentByte++;
            }
            bitPointer++;
            if (bitPointer % bitLength == 0 || bitPointer == length) {
                if (bitPointer % bitLength != 0) {
                    for (int i = 0; i < (bitLength - length % bitLength); i++) {
                        currentByte = currentByte * 2;
                    }
                }
                bytes[byteCounter] = (byte) currentByte;
                currentByte = 0;
                byteCounter++;
            }
        }

        int lastByteLength = (bitPointer) % bitLength; 
        
        // bytes[0] = huffman ? (byte) (lastByteLength) : bytes[0]; 
        bytes[0] = (byte) (lastByteLength);
        
        return bytes;
    }
    


    public static String intToBits(int input) {
        int bitLength = 12; 
        StringBuilder bitBuilder = new StringBuilder(); 

        for (int j = 11; j >= 12-bitLength; j--) {  
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

                        
            

}
    


    


