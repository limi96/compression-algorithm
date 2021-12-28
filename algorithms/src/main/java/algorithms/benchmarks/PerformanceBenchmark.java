package algorithms.benchmarks; 

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import algorithms.Huffman;
import algorithms.LZW;
import algorithms.utils.FileUtils;
import algorithms.utils.Node;


public class PerformanceBenchmark {

    public static int reps = 10; 
    public static Huffman h = new Huffman(); 
    public static LZW lzw = new LZW(); 
    public static long start;
    public static long end;  
    public static HashMap<String, HashMap<String, ArrayList<Double>>> results = new HashMap<>();
     

    public static void testingHuffman(String testString) throws java.io.IOException {

        String input = FileUtils.readTextFile("test_files/" + testString);
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

    public static void testingLZW(String testString) throws java.io.IOException {

        String input = FileUtils.readTextFile("test_files/" + testString);
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

    public static void printResults(String algorithm) {

        System.out.println("__________________________Testing " + algorithm + " Performance__________________________");
        
        System.out.format("%16s%16s%16s%16s%16s%n", "Name of file", "Encode","Decode","writeFile", "readFile"); 

        for (String inputName : results.keySet()) {
            Double value1 = results.get(inputName).get("encode").get(0);
            Double value2 = results.get(inputName).get("decode").get(0);
            Double value3 = results.get(inputName).get("writeFile").get(0);
            Double value4 = results.get(inputName).get("readFile").get(0);
            System.out.format("%16s%16s%16s%16s%16s%n", inputName, value1, value2, value3, value4); 
        }
        System.out.println();
        results.clear();
    }

    public static void processResultList(String inputName, HashMap<String, ArrayList<Double>> results) {
        for (ArrayList<Double> list : results.values()) {
            list.remove(Collections.min(list));
            list.remove(Collections.max(list));
            list.remove(Collections.max(list));
            Collections.sort(list); 
            double result = list.get(list.size() / 2) / 1E6; 
            list.clear();
            list.add(Double.parseDouble(String.format("%.2f",result)));
        }

    }

    public static void main(String[] args) throws java.io.IOException {
        testingHuffman("100_KB_lorem.txt");
        testingHuffman("artofwar.txt");
        testingHuffman("100_KB_cScSc.txt");
        testingHuffman("ASCII_256.txt");
        printResults("Huffman");
        testingLZW("100_KB_lorem.txt");
        testingLZW("artofwar.txt");
        testingLZW("100_KB_cScSc.txt");
        testingLZW("ASCII_256.txt");
        printResults("LZW");
    }
}
