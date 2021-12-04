
import java.util.ArrayList;
public class PerformanceBenchmark {
    

    public static int reps = 10; 

    public static void encodingTime(String testString) {
        Huffman h = new Huffman();
        LZW lzw = new LZW(); 

        FileUtils fileReader = new FileUtils();
        String testInputString = fileReader.fileReaderOutput(testString);
        
        ArrayList<Double> resultList = new ArrayList<>();

        double max = Integer.MIN_VALUE; 
        double min = Integer.MAX_VALUE;
        int maxIndex = 0; 
        int minIndex = 0;

        for (int i = 0; i < reps; i++) { 
            long start = System.nanoTime();

            // h.encode(testInputString);
            lzw.compress(testInputString);
            
            long end = System.nanoTime(); 
            
            double result = (end-start)/1E6;
            if (max < result) {max = result; maxIndex = i;}
            if (min > result) {min = result; minIndex = i;}
            
            resultList.add(Double.parseDouble(String.format("%.2f",result)));
        }

        resultList.remove(minIndex);
        resultList.remove(maxIndex);
        
        double sum = 0; 

        for (Double result : resultList) {
            sum+=result; 
        }
        System.out.println("Encoding file : " + testString);
        System.out.println("Method        : Huffman");
        System.out.println("Time taken    : " + String.format("%.2f",sum/(reps-2)) + " ms");
        System.out.println();
    }
    public static void main(String[] args) {
        encodingTime("100_KB_lorem.txt");
        encodingTime("100_KB_repeating_lorem_ipsum.txt");
        encodingTime("100_KB_cScSc.txt");
        encodingTime("ASCII_256.txt");
    }
}
