public class CompressionBenchmark {

    public static void main(String[] args) {
        performTest("100_KB_lorem.txt");
        performTest("100_KB_repeating_lorem_ipsum.txt");
        performTest("100_KB_cScSc.txt");
        performTest("ASCII_256.txt");
    }

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
        System.out.println("Huffman compression rate : " + compRateHuffman + " %");
        System.out.println("LZW     compression rate : " + compRateLZW + " %");
        
    }
    
}
