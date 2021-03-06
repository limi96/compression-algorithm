package algorithms.benchmarks;

import algorithms.Huffman;
import algorithms.LZW;
import algorithms.utils.FileUtils;

public class CompressionBenchmark {

    public static void huffmanTest(String fileName) throws java.io.IOException {
        Huffman h = new Huffman();
        
        fileName = "test_files/" + fileName; 

        String inputString = ""; 
        inputString = FileUtils.readTextFile(fileName);
        long inputByteSize = inputString.getBytes().length; 

        String encodedMessage = h.encode(inputString);
        
        h.writeEncodedFile("huffmanCompressionTest");
        
        long huffmanByteSize = FileUtils.readFile("huffmanCompressionTest.hf").length;
        Double compRateHuffman = ((double) huffmanByteSize / inputByteSize)*100;
        Double compRateHuffmanNoTree = ((double) (huffmanByteSize - h.getTreeData().length) / inputByteSize) * 100;

        System.out.println("_____________Testing Huffman Compression Rates_____________");
        System.out.println("Filename                                    : " + fileName);
        System.out.println("Input   Message length        (in bits)     : " + inputString.getBytes().length * 16);
        System.out.println("Encoded Message length        (in bits)     : " + encodedMessage.length());
        System.out.println("Input File size               (in bytes)    : " + inputByteSize);
        System.out.println("Huffman Compressed File size  (in bytes)    : " + huffmanByteSize);
        System.out.println("Huffman Tree File size        (in bytes)    : " + h.getTreeData().length);
        System.out.println("Huffman Message File size     (in bytes)    : " + h.getMessageData().length);
        System.out.println("Huffman compression rate                    : " + String.format("%.2f",compRateHuffman) + " %");
        System.out.println("Compression rate Without Tree               : " + String.format("%.2f",compRateHuffmanNoTree) + " %");
        System.out.println();
    }

    public static void lzwTest(String fileName) throws java.io.IOException {
        LZW lzw = new LZW(); 
        
        fileName = "test_files/" + fileName; 

        String inputString = ""; 
        inputString = FileUtils.readTextFile(fileName);
        long inputByteSize = inputString.getBytes().length; 

        lzw.compress(inputString); 
        lzw.writeLZWFile("lzwCompressionTest");
        
        byte[] outputFile = FileUtils.readFile("lzwCompressionTest.lzw");

        long lzwByteSize = outputFile.length;
        Double compRateLZW = ((double) lzwByteSize/inputByteSize)*100;

        long encodedMessageLength = lzwByteSize * 8; 

        System.out.println("_____________Testing LZW Compression Rates_____________");
        System.out.println("Filename                                    : " + fileName);
        System.out.println("Input   Message length        (in bits)     : " + inputString.getBytes().length * 16);
        System.out.println("Encoded Message length        (in bits)     : " + encodedMessageLength);
        System.out.println("Input File size               (in bytes)    : " + inputByteSize);
        System.out.println("LZW Compressed File size  (in bytes)        : " + lzwByteSize);
        System.out.println("LZW compression rate                        : " + String.format("%.2f",compRateLZW) + " %");
        System.out.println();
    }
    public static void main(String[] args) throws java.io.IOException {

        // Example of how to use the CompressionBenchmark
        huffmanTest("100_KB_lorem.txt");
        huffmanTest("artofwar.txt");
        huffmanTest("warandpeace.txt");
        huffmanTest("100_KB_cScSc.txt");
        huffmanTest("ASCII_256.txt");
        huffmanTest("Large Lorem.txt");
    
        lzwTest("100_KB_lorem.txt");
        lzwTest("artofwar.txt");
        lzwTest("warandpeace.txt");
        lzwTest("100_KB_cScSc.txt");
        lzwTest("Large Lorem.txt");
        lzwTest("ASCII_256.txt");
    
    }

}
