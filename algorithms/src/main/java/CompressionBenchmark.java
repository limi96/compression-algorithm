public class CompressionBenchmark {

    // public static void performTest(String testString) {
    //     Huffman h = new Huffman(); 
    
    //     FileUtils fileReader = new FileUtils();
    //     String testInputString = fileReader.fileReaderOutput(testString);
    //     long testInputByteSize = fileReader.getByteArray().length;
    
    //     //Huffman
    //     // h.encode(testInputString);
    //     // long huffmanByteSize = 0;
    //     // Double compRateHuffman = ((double) huffmanByteSize/testInputByteSize)*100;
    
    //     //LZW 
    //     LZW lzw = new LZW(); 
    //     lzw.compress(testInputString); 
    //     long lzwByteSyze = lzw.outputByteSize();
    //     Double compRateLZW = ((double) lzwByteSyze/testInputByteSize)*100;

    //     //Result
    //     System.out.println();
    //     System.out.println("Testing file : " + testString);
    //     System.out.println("Huffman compression rate : " + String.format("%.2f",compRateHuffman) + " %");
    //     System.out.println("LZW     compression rate : " + String.format("%.2f",compRateLZW) + " %");
    // }

    public static void huffmanTest(String fileName) throws java.io.IOException {
        Huffman h = new Huffman();
        
        //Need to make a method that encodes lol
        String inputString = ""; 
        inputString = FileUtils.fileReaderOutput(fileName);
        long inputByteSize = FileUtils.getByteArray().length; 
        String encodedMessage = h.encode(inputString);
        h.writeEncodedFile("huffmanCompressionTest");
        h.readEncodedFile("huffmanCompressionTest.hf");
        
        long huffmanByteSize = FileUtils.readFile("huffmanCompressionTest.hf").length;
        Double compRateHuffman = ((double) huffmanByteSize/inputByteSize)*100;
        Double compRateHuffmanNoTree = ((double) (huffmanByteSize-h.getTreeData().length)/inputByteSize)*100;

        System.out.println("_____________Testing Huffman Compression Rates_____________");
        System.out.println("Filename                                    : " + fileName);
        System.out.println("Input   Message length        (in bits)     : " + inputString.getBytes().length*8);
        System.out.println("Encoded Message length        (in bits)     : " + encodedMessage.length());
        System.out.println("Input File size               (in bytes)    : " + inputByteSize);
        System.out.println("Huffman Compressed File size  (in bytes)    : " + huffmanByteSize);
        System.out.println("Huffman Tree File size        (in bytes)    : " + h.getTreeData().length);
        System.out.println("Huffman Message File size     (in bytes)    : " + h.getMessageData().length);
        System.out.println("Huffman compression rate                    : " + String.format("%.2f",compRateHuffman) + " %");
        System.out.println("Compression rate Without Tree               : " + String.format("%.2f",compRateHuffmanNoTree) + " %");
        System.out.println();
    }

    public static void LZWTest(String fileName) throws java.io.IOException {

    }

    public static void main(String[] args) throws java.io.IOException {
        // performTest("100_KB_lorem.txt");
        // performTest("100_KB_repeating_lorem_ipsum.txt");
        // performTest("100_KB_cScSc.txt");
        // performTest("ASCII_256.txt");

        huffmanTest("100_KB_lorem.txt");
        huffmanTest("100_KB_repeating_lorem_ipsum.txt");
        huffmanTest("100_KB_cScSc.txt");
        huffmanTest("ASCII_256.txt");
    }

    public static void HuffmanTest() {

    }



    
}
