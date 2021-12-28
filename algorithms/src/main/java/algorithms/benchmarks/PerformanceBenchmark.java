package algorithms.benchmarks; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import algorithms.Huffman;
import algorithms.LZW;
import algorithms.utils.FileUtils;
import algorithms.utils.Node;

public class PerformanceBenchmark {

    public static int reps = 1; 
    public static Huffman h = new Huffman(); 
    public static LZW lzw = new LZW(); 
    public static long start;
    public static long end;  
    public static HashMap<String, HashMap<String, ArrayList<Double>>> results = new HashMap<>();

    public static Random rnd = new Random(); 
    
    public static String getInput(String testString, long lengthOfInput) {
        String input = "";
        if (lengthOfInput == 0) {
            input = FileUtils.readTextFile("test_files/" + testString);
        } else {
            input = randomStringGenerator(lengthOfInput); 
        }
        return input;
    }

    public static void testingHuffman(String testString, long lengthOfInput) throws java.io.IOException {

        String input = getInput(testString, lengthOfInput); 

        HashMap<String, ArrayList<Double>> resultsHuffman = new HashMap<>(); 
        resultsHuffman.put("encode", new ArrayList<>());
        resultsHuffman.put("decode", new ArrayList<>());
        resultsHuffman.put("writeFile", new ArrayList<>());
        resultsHuffman.put("readFile", new ArrayList<>());

        for (int i = 0; i < reps; i++) { 
            start = System.nanoTime();
            h.encode(input);
            end = System.nanoTime(); 
            resultsHuffman.get("encode").add((end-start) + 0.0);

            start = System.nanoTime(); 
            h.writeEncodedFile("PerformanceBenchHuffman");
            end = System.nanoTime(); 
            resultsHuffman.get("writeFile").add((end-start) + 0.0);

            start = System.nanoTime(); 
            h.readEncodedInput("PerformanceBenchHuffman.hf", true);
            end = System.nanoTime(); 
            resultsHuffman.get("readFile").add((end-start) + 0.0);

            Node inputRootNode = h.getInputRootNode(); 
            String inputMessage = h.getEncodedMessage(); 

            start = System.nanoTime(); 
            h.decode(inputRootNode, inputMessage); 
            end = System.nanoTime(); 
            resultsHuffman.get("decode").add((end-start) + 0.0);
        }       
        processResultList(testString, resultsHuffman);
        results.put(testString, resultsHuffman); 
    }

    public static void testingLZW(String testString, long lengthOfInput) throws java.io.IOException {

        String input = getInput(testString, lengthOfInput); 

        HashMap<String, ArrayList<Double>> resultsLZW = new HashMap<>(); 
        resultsLZW.put("encode", new ArrayList<>());
        resultsLZW.put("decode", new ArrayList<>());
        resultsLZW.put("writeFile", new ArrayList<>());
        resultsLZW.put("readFile", new ArrayList<>());

        for (int i = 0; i < reps; i++) { 
            start = System.nanoTime();
            lzw.compress(input);
            end = System.nanoTime(); 
            resultsLZW.get("encode").add((end-start) + 0.0);

            start = System.nanoTime(); 
            lzw.writeLZWFile("PerformanceBenchLZW");
            end = System.nanoTime(); 
            resultsLZW.get("writeFile").add((end-start) + 0.0);

            start = System.nanoTime(); 
            lzw.readLZWFile("PerformanceBenchLZW.lzw");
            end = System.nanoTime(); 
            resultsLZW.get("readFile").add((end-start) + 0.0);

            ArrayList<Integer> inputEncoded = lzw.getEncoded(); 

            start = System.nanoTime(); 
            lzw.uncompress(inputEncoded); 
            end = System.nanoTime(); 
            resultsLZW.get("decode").add((end-start) + 0.0);
        }       
        processResultList(testString, resultsLZW);
        results.put(testString, resultsLZW); 
    }

    public static void printResults(String algorithm, boolean sortKeys) {

        System.out.println("__________________________Testing " + algorithm + " Performance__________________________");
        
        System.out.format("%16s%16s%16s%16s%16s%n", "Name of file", "Encode","Decode","writeFile", "readFile"); 

        ArrayList<String> keys = new ArrayList<>(results.keySet());

        long[] sortHelper = new long[keys.size()]; 
    
        if (sortKeys) {
            for (int i = 0; i < keys.size(); i++) { 
                sortHelper[i] = Long.parseLong(keys.get(i)); 
            }
            Arrays.sort(sortHelper); 
            keys.clear();

            for (int i = 0; i < sortHelper.length; i++) { 
                keys.add("" + sortHelper[i]); 
            }
        }

        for (String inputName : keys) {
            String value1 = results.get(inputName).get("encode").get(0).toString() + " ms";
            String value2 = results.get(inputName).get("decode").get(0).toString() + " ms";
            String value3 = results.get(inputName).get("writeFile").get(0).toString() + " ms";
            String value4 = results.get(inputName).get("readFile").get(0).toString() + " ms";
            System.out.format("%16s%16s%16s%16s%16s%n", inputName, value1, value2, value3, value4); 
        }

        System.out.println();
        results.clear();
    }

    public static void processResultList(String inputName, HashMap<String, ArrayList<Double>> results) {
        for (ArrayList<Double> list : results.values()) {
            // list.remove(Collections.min(list));
            // list.remove(Collections.max(list));
            // list.remove(Collections.max(list));
            Collections.sort(list); 
            double result = list.get(list.size() / 2) / 1E6; 
            list.clear();
            list.add(Double.parseDouble(String.format("%.2f",result)));
        }

    }

    public static String randomStringGenerator(long lengthOfInput) {
        StringBuffer inputBuffer = new StringBuffer(); 

        for (int j = 0; j < lengthOfInput; j++) {
            inputBuffer.append((char)rnd.nextInt(256)); 
        }

        return inputBuffer.toString(); 
    }

    public static void main(String[] args) throws java.io.IOException {

        long[] inputSizes = new long[]{1000, 3000, 5000, 7000, 10000, 100000, 300000, 500000, 700000, 1000000, 3000000, 5000000, 7000000, 10000000}; 

        for (long inputSize : inputSizes) {
            testingHuffman("" + inputSize, inputSize);     
        }
        printResults("Huffman", true);
        
        for (long inputSize : inputSizes) {
            testingLZW("" + inputSize, inputSize); 
        }
        printResults("LZW", true);

        testingHuffman("100_KB_lorem.txt", 0);
        testingHuffman("artofwar.txt", 0);
        testingHuffman("warandpeace.txt", 0);
        testingHuffman("100_KB_cScSc.txt", 0);
        testingHuffman("ASCII_256.txt", 0);
        testingHuffman("Large Lorem.txt", 0);
        printResults("Huffman", false);

        testingLZW("100_KB_lorem.txt", 0);
        testingLZW("artofwar.txt", 0);
        testingLZW("warandpeace.txt", 0);
        testingLZW("100_KB_cScSc.txt", 0);
        testingLZW("ASCII_256.txt", 0);
        testingLZW("Large Lorem.txt", 0);
        printResults("LZW", false);
    }
}
