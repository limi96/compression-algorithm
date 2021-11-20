import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;



public class FileReader {
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("/home/limi/Documents/compression-algorithm/algorithms/src/main/java/test.txt"); 
        Scanner scan = new Scanner(file);

        ArrayList<String> testi = new ArrayList<>();

        long alku1 = System.nanoTime();
        while (scan.hasNextLine()) {
            testi.add(scan.nextLine());
        }

        long loppu1 = System.nanoTime();
        //System.out.println(testi);

        ArrayList<String> readInput = new ArrayList<>();
        StringBuffer inputBuffer = new StringBuffer(); 
        
        //This is faster 

        String filePath = "/home/limi/Documents/compression-algorithm/algorithms/src/main/java/test.txt";
        
        long alku2 = System.nanoTime();
        try {
            // Files.lines(Paths.get("/home/limi/Documents/compression-algorithm/algorithms/src/main/java/test.txt"))
            // .forEach(newLine -> readInput.add(newLine));
            Files.lines(Paths.get(filePath)).forEach(newLine -> inputBuffer.append(newLine));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }        
        long loppu2 = System.nanoTime();

        String stringInput = new String(inputBuffer); 

        System.out.println("Eka : " + (loppu1-alku1)/1E6 + " ms");
        System.out.println("Tok : " + (loppu2-alku2)/1E6 + " ms");
        // System.out.println(rivit);
    }

    public String fileReaderOutput() {

        String filePath = "/home/limi/Documents/compression-algorithm/algorithms/src/main/java/test.txt";
        StringBuffer inputBuffer = new StringBuffer(); 
        try {
            Files.lines(Paths.get(filePath)).forEach(newLine -> inputBuffer.append(newLine));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }     
        return new String(inputBuffer); 
    }

}


