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
        for (int i = 0; i < 1000; i++) { 
            StringBuilder inputBuilder = new StringBuilder(); 
            for (int j = 0; j < 10 + rnd.nextInt(1000); j++) {
                inputBuilder.append(rnd.nextInt(2) % 2 == 0 ? "0" : "1"); 
            }
            input = new String(inputBuilder); 
            String reverseInput = bytesToBits(bitsToByte(input));
            boolean testResult = input.equals(reverseInput);
            if (!testResult) {
                System.out.println();
                System.out.println("Result : " + testResult);
                System.out.println(input);
                System.out.println(reverseInput);
                System.out.println("Length Difference : " + (input.length()-reverseInput.length()));
                // System.out.print((input.length()-reverseInput.length()));
                numberOfWrongResults++;
            }
        }
        System.out.println("Finished -- Wrong results: " + numberOfWrongResults);
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
            
            // byte viimeinenTavu = tavut[tavut.length-1];
            // viimeinenTavu = viimeinenTavu >> 3;
            // System.out.println("Viimeinen : " + Integer.toBinaryString((viimeinenTavu >>> 3) & 0xFF));

            // tavut[tavut.length-1] = (byte) ((viimeinenTavu^00000000)>>> 3); 
            // System.out.println("Viimeinen : " + Integer.toBinaryString(((viimeinenTavu ^ 00000000)) & 0xFF));

            
            // Integer.toBinaryString((b & 0xFF)


            
            // System.out.println("Tavulaskuri " + tavulaskuri);
            // System.out.println("Osoitin " + osoitin);
            // System.out.println("Viimeinen tavu : " + viimeinenTavuOffset);
            // System.out.println("Viimeinen bit : " + (byte) (viimeinenTavuOffset));
            // System.out.println(Arrays.toString(tavut));

            return bytes;
        }
        
        public static String bytesToBits(byte[] bytes ) {

            StringBuilder bitBuilder = new StringBuilder(); 

            int lastLength = bytes[0];
            int bitLength = 8; 
                       
            for (int i = 1; i < bytes.length; i++) {

                byte b = bytes[i];
            
                if (i == bytes.length-1) {
                    // bitLength = lastLength; 
                    // bitLength = lastLength == 0 ? lastLength : 1; 
                    bitLength = lastLength == 0 ? 8 : lastLength; 
                }

                for (int j = 7; j >= 8-bitLength; j--) {  
                    bitBuilder.append((b >> j) & 1);
                    // System.out.print((b >> j) & 1);
                }        
                
            }
                    
            return new String(bitBuilder);
        }
               
            
                

}
    


    


