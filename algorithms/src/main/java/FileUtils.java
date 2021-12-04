
import java.io.File; 
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static byte[] byteArray;
    
    public String fileReaderOutput(String fileName) {
        
        StringBuffer inputBuffer = new StringBuffer(); 
        File testing = new File("test_files/" + fileName);
        String filePath = testing.getAbsolutePath();
        
        try {
            Files.lines(Paths.get(filePath)).forEach(newLine -> inputBuffer.append(newLine));
        } catch (Exception e) {
            System.out.println("Error! Could not read file : " + e.getMessage());
        }        

        String stringInput = new String(inputBuffer); 
        byteArray = stringInput.getBytes(StandardCharsets.UTF_8);
        return stringInput; 
    }

    public byte[] getByteArray() {
        return byteArray; 
    }

}


