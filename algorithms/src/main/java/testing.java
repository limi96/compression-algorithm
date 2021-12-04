
import java.util.ArrayList;

import java.util.Collections;

public class testing {

    public static int reps = 15; 

    Huffman h; 
    LZW lzw; 
    FileUtils fileReader;

    public void initialize() {
        h = new Huffman();
        fileReader = new FileUtils();
    }

    public static void encodingTime(String testString, boolean testHuffman, boolean testLZW) {
        
        Huffman h = new Huffman();
        LZW lzw = new LZW(); 

        FileUtils fileReader = new FileUtils();
        String testInputString = fileReader.fileReaderOutput(testString);
        
        long start = 0;
        long end   = 0; 

        String algorithm = testHuffman ? "Huffman" : "LZW";

        if (testHuffman && testLZW) { algorithm = "both"; }

        ArrayList<Double> resultsHuffman = new ArrayList<>();
        ArrayList<Double> resultsLZW     = new ArrayList<>();

        for (int i = 0; i < reps; i++) { 
            if (testHuffman) {
                start = System.nanoTime();
                h.encode(testInputString);
                end = System.nanoTime(); 

                double result = (end - start) / 1E6;
                addResult(result, resultsHuffman);
            }

            else if (testLZW) {
                start = System.nanoTime();
                lzw.compress(testInputString);
                end = System.nanoTime(); 
                
                double result = (end - start) / 1E6;
                addResult(result, resultsLZW);
            }
        }       
        processResults(resultsHuffman, "Huffman", "encoding", testString);
        processResults(resultsHuffman, "LZW", "encoding", testString);
    }

    public static void addResult(Double result, ArrayList<Double> list) {
        list.add(Double.parseDouble(String.format("%.2f",result)));
    }

    public static void processResults(ArrayList<Double> list, String algorithm, String method, String testString) {
        list.remove(Collections.min(list));
        list.remove(Collections.max(list));
        list.remove(Collections.max(list));

        double sum = 0; 

        for (Double result : list) { sum += result; }

        System.out.println("Encoding file : " + testString);
        System.out.println("Algorithm     : " + algorithm);
        System.out.println("Method        : " + method);
        System.out.println("Time taken    : " + String.format("%.2f", sum / (reps-2)) + " ms");
        System.out.println();
    }

    public static void edgeCaseLZW() {
        String edgeCase = "";

        LZW lzw = new LZW(); 

        ArrayList<Double> resultList = new ArrayList<>();

        // int[] inputArray = new int[]{1000, 5000, 10000, 15000, 20000, 40000, 80000, 150000};
        int[] inputArray = new int[]{1000, 5000, 10000};

        for (int i = 0; i < inputArray.length; i++) {
            double max = Integer.MIN_VALUE; 
            double min = Integer.MAX_VALUE;
            int maxIndex = 0; 
            int minIndex = 0;

            for (int j = 0; j < inputArray[i]; j++) { 
                edgeCase += "cScSc" + " ";
            }

            for (int k = 0; k < 10; k++) { 


            }
            long start = System.nanoTime();
            lzw.compress(edgeCase);
            long end = System.nanoTime(); 

            double result = (end - start) / 1E6;      
            resultList.add(Double.parseDouble(String.format("%.2f",result)));
            edgeCase = ""; 
        }
        System.out.println(resultList);
    }

    public static void main(String[] args) {
        encodingTime("100_KB_repeating_lorem_ipsum.txt", true, true);
        encodingTime("100_KB_lorem.txt", true, true);
        encodingTime("100_KB_cScSc.txt", true, true);
        encodingTime("ASCII_256.txt", true, true);
        // edgeCaseLZW(); 
    }
}
