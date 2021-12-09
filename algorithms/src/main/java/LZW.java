import java.util.ArrayList;
import java.util.HashMap;

public class LZW {

    ArrayList<Integer> encoded = new ArrayList<>(); 

    public void compress(String input) {

        int inputChar = 256;        
        HashMap<String, Integer> hm = new HashMap<>();
        // Initialize the standard character HashMap for codes 0 to 256 

        for (int i = 0; i < 256; i++) {
            String code = Character.toString((char) i); 
            hm.put(code, i);
        }
        
        char[] inputArr = input.toCharArray(); 
        String currentString = Character.toString(inputArr[0]);

        ArrayList<String> stringList = new ArrayList<>(); 

        // Looks for reoccuring patterns. If patterns are occuring more than once
        // Assign a new code for the pattern by incrementing starting from 256
        // Then add new code to the existing HashMap 
        String nextChar = "";

        for (int i = 0; i < input.length(); i++) {

            if (i < input.length()-1) {
                nextChar = Character.toString(inputArr[i+1]);
            }

            String nextRead = currentString + nextChar; 
            
            if (hm.containsKey(nextRead)) {
                currentString = nextRead; 
            }

            else {
                hm.put(nextRead, inputChar);
                inputChar++; 

                if (i == input.length()-1) {
                    currentString = nextChar;
                }   
                
                encoded.add(hm.get(currentString));
                
                stringList.add(currentString);
                currentString = nextChar; 
            }
        }
        
        // System.out.println("Encoded   --> " + encoded.toString());
        // System.out.println("StringList--> " + stringList.toString());
    }

    public long outputByteSize() {
        return encoded.size(); 
    }

    public ArrayList<Integer> getEncodedList() {
        return encoded; 
    }

    public String uncompress(ArrayList<Integer> encoded) {

        HashMap<Integer, String> hm = new HashMap<>();

        // Initialize the standard character HashMap for codes 0 to 256 

        for (int i = 0; i < 256; i++) {
            String code = Character.toString((char) i); 
            hm.put(i, code);
        }

        // Get first code 
        int inputCode = encoded.get(0); 

        // Get output of first code
        String currentString = hm.get(inputCode); 

        // Helper to form letter combinations
        String previousCharacter = ""; 

        // Output 
        String decodedString = currentString;

        int inputChar = 256; 

        for (int i = 0; i < encoded.size()-1; i++) {

            int nextCode = encoded.get(i+1); 
             
            //If dictionary doesn't contain the value of nextcode
            //It means we've encountered a reoccuring pattern (Its code value is higher than 256)
            //This means we have to add the previousCharacter to currentString
            //To reproduce a letter combination for the pattern

            if (!hm.containsKey(nextCode)) {
                currentString = hm.get(inputCode);
                currentString += previousCharacter; 
            }

            else {
                currentString = hm.get(nextCode); 
            }

            //Append currentString to form decoded message
            decodedString += currentString;

            // Form a letter combination
            previousCharacter = "" + currentString.charAt(0);

            //Put the letter combination into the dictionary
            //This will enable reusing letter combinations that have been encountered before
            hm.put(inputChar, hm.get(inputCode) + previousCharacter);

            inputChar++; // starts from 256 now 257
            inputCode = nextCode;
        }
        return decodedString; 
    }


    public static void main(String[] args) throws Exception {
        
        LZW lzw = new LZW();
        
        lzw.compress("A brown fox jumps quickly over the lazy dog");
        // lzw.compress("ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ");
        // lzw.compress("1234568790");

        // lzw.compress("Lorem ipsum Lorem Lorem Lorem Lorem");
        // lzw.compress("1234");        
        // Testing a known edge case
        // lzw.compress("cScSc"); 
    }   
}
