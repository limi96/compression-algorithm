import java.nio.charset.StandardCharsets;
import java.util.PriorityQueue;
import java.util.HashMap;

public class Huffman {
    //Initialize data structures

    public static HashMap<String, String> huffmanDict = new HashMap<>();
    public static HashMap<String, String> huffmanDictReverse = new HashMap<>();
    public static HashMap<String, Integer> codeFreq = new HashMap<>(); 
    public static Node rootNode = null;
    public static String encodedMessage = "";

    public HashMap<String, String> getHuffmanDict() {
        return huffmanDict; 
    }

    public void encode(String input) {

        //Initialize data structures 
        HashMap<String, Integer> hm = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<Node>();

        //Create a Hashmap that contains characters and their respective frequencies
        //Need to get rid of this HashMap later, when optimizing
        //Preferably only 1 for-loop to make the nodes and 1 while-loop 

        for (int i = 0; i < input.length(); i++) {
            String c = input.charAt(i) + ""; 
            hm.put(c, hm.getOrDefault(c, 0) + 1);
        }

        //Create nodes with characters and their frequencies using a HashMap

        for (String key : hm.keySet()) {
            Node newNode = new Node(key, hm.get(key));
            pq.add(newNode);
        }

        // Create the tree by making a parent node out of two nodes with the lowest frequencies
        // Repeat until size is 1 meaning only root node is left

        while (pq.size() > 1) {
            Node leftNode = pq.poll();
            Node rightNode = pq.poll();
            
            int parentFreq = leftNode.freq + rightNode.freq;
            Node parentNode = new Node("", parentFreq, leftNode, rightNode);

            rootNode = parentNode;
            pq.add(parentNode);
        }

        createTreeCodes(rootNode, "");

        //prints(rootNode, input); 
    }

    public Node getRootNode() {
        return rootNode; 
    }

    public String getEncodedMessage(String input) {
        return correctEncode(input); 
    }

    public HashMap<String, Integer> getCodeFreq() {
        return codeFreq; 
    }

    // Prints out output values 
    public void prints(Node rootNode, String input) {
        rootNode = getRootNode();
        String decodedOutput = decode(rootNode, correctEncode(input)); 
    }



    //Encodes the message according to the given HuffmanCodes
    public String correctEncode(String input) {
        String correctMessage = ""; 
    
        for (int i = 0; i < input.length(); i++) { 
            correctMessage += huffmanDictReverse.get("" + input.charAt(i));
        }
        
        return correctMessage; 
    }


    public String decode(Node currentNode, String message) {
        String decodedMessage = "";

        //Go through each of the 0s and 1s along the tree until we find a character
        for (int i = 0; i < message.length(); i++) { 
            String c = message.charAt(i) + ""; 

            if (c.equals("1")) {
                currentNode = currentNode.right;
            }
    
            else if (c.equals("0")) {
                currentNode = currentNode.left;
            }

            //If a character is found, add it to the decoded message
            //Then return back to the root 
            if (currentNode.left == null && currentNode.right == null && currentNode.letter != null) {
                decodedMessage += currentNode.letter;
                currentNode = rootNode; 
            }
   
        }

        return decodedMessage;
    } 

    // Recursively travels the tree to create the codes for each character
    // The code is essentially the same as the exercise "Alipuut" from TiraMooc 2020
    // Travelling to the left corresponds to a code output of 0
    // Travelling to the right yields 1

    public void createTreeCodes(Node currentNode, String code) {

        if (currentNode.left == null && currentNode.right == null) {
            //System.out.println(currentNode.letter + " : " + code);
            //required for encoding
            huffmanDict.put(code, currentNode.letter);

            //required for decoding
            //Should be a better way perhaps without the use of this HashMap. This is taking memory.
            huffmanDictReverse.put(currentNode.letter, code); 
            codeFreq.put(code, currentNode.freq); 

            encodedMessage += code;
            return;
        }

        createTreeCodes(currentNode.left, code + "0");
        createTreeCodes(currentNode.right, code + "1");
    }

    public long outputByteSize() {

        long size = 0; 

        for (String code : codeFreq.keySet()) {
            size += code.length()*codeFreq.get(code);
        }

        return size/8; 
    }

    public static void main(String[] args) {
        
        Huffman h = new Huffman();
        //h.encode("A brown fox jumps quickly over the lazy dog");
        //h.encode("ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ");
    }
}    

