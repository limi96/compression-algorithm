public class CompressionBenchmark {



    public static void performTest(String testString) {
        Huffman h = new Huffman(); 
        LZW lzw = new LZW(); 

        FileUtils fileReader = new FileUtils();
        String testInputString = fileReader.fileReaderOutput(testString);
        long testInputByteSize = fileReader.getByteArray().length;

        //Huffman
        h.encode(testInputString);
        long huffmanByteSize = h.outputByteSize();
        Double compRateHuffman = ((double) huffmanByteSize/testInputByteSize)*100;

        //LZW 
        lzw.compress(testInputString); 
        long lzwByteSyze = lzw.outputByteSize();
        Double compRateLZW = ((double) lzwByteSyze/testInputByteSize)*100;

        //Result
        System.out.println();
        System.out.println("Testing file : " + testString);
        System.out.println("Huffman compression rate : " + String.format("%.2f",compRateHuffman) + " %");
        System.out.println("LZW     compression rate : " + String.format("%.2f",compRateLZW) + " %");
    }

    public static void replacingTest(String fileName) throws java.io.IOException {
        Huffman h = new Huffman();
        
        //Need to make a method that encodes lol
        String inputString = ""; 
        inputString = FileUtils.fileReaderOutput(fileName);
        long inputByteSize = FileUtils.getByteArray().length; 
        String encodedMessage = h.encode(inputString);
        h.writeEncodedFile("huffmanCompressionTest");
        
        long huffmanByteSize = FileUtils.readFile("huffmanCompressionTest.hf").length;
        Double compRateHuffman = ((double) huffmanByteSize/inputByteSize)*100;
    
        System.out.println("Filename                 : " + fileName);
        System.out.println("Input Message            : " + inputString.length());
        System.out.println("EncodedMsg length        : " + encodedMessage.length());
        System.out.println("InputByteSize            : " + inputByteSize);
        System.out.println("Huffman size             : " + huffmanByteSize);
        System.out.println("Huffman compression rate : " + String.format("%.2f",compRateHuffman) + " %");
        System.out.println();
    }

    public static void main(String[] args) throws java.io.IOException {
        // performTest("100_KB_lorem.txt");
        // performTest("100_KB_repeating_lorem_ipsum.txt");
        // performTest("100_KB_cScSc.txt");
        // performTest("ASCII_256.txt");

        replacingTest("100_KB_lorem.txt");
        // replacingTest("100_KB_repeating_lorem_ipsum.txt");
        // replacingTest("100_KB_cScSc.txt");
        // replacingTest("ASCII_256.txt");
    }

    public static void HuffmanTest() {

    }



    
}
