import java.io.File; 
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtils {

    public static byte[] byteArray;
    public static String stringInput; 
    

    public static void writeFile(String outputName, byte[] bytes) throws java.io.IOException {
        FileOutputStream outputStream = new FileOutputStream("./" + outputName + ".hf");
        outputStream.write(bytes); 
        outputStream.close();
    }

    public static byte[] readFile(String inputName) throws java.io.IOException {
        FileInputStream inputStream = new FileInputStream(inputName);
        byte[] byteArray = inputStream.readAllBytes(); 
        inputStream.close();
        return byteArray; 
    }


    public static String fileReaderOutput(String fileName) {
        StringBuffer inputBuffer = new StringBuffer(); 
        File testing = new File("test_files/" + fileName);
        String filePath = testing.getAbsolutePath();

        try {
            Files.lines(Paths.get(filePath)).forEach(newLine -> inputBuffer.append(newLine));
        } catch (Exception e) {
            System.out.println("Error! Could not read file : " + e.getMessage());
        }        

        stringInput = new String(inputBuffer); 
        return stringInput; 
    }
    
    public static byte[] getByteArray() {
        byteArray = stringInput.getBytes(StandardCharsets.UTF_8);
        return byteArray; 
    }



    public static void main(String[] args) {
        File testing = new File("./" + "moi");
        String filePath = testing.getAbsolutePath();
        System.out.println(filePath);
    }



}


