package algorithms; 

import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Arrays;
import java.nio.ByteBuffer;

import algorithms.utils.FileUtils;
import algorithms.utils.HuffmanUtils;
import algorithms.utils.Node;

public class Huffman {
    //Initialize data structures
    public static HashMap<String, String> huffmanCodes = new HashMap<>();

    //Required for encoding and writing
    // rootNode is the resulting root Node for the Huffman Tree after encoding
    public static Node rootNode;
    public static String encodedMessage; 
    
    //Required for reading and decoding
    public static Node inputRootNode;
    public static byte[] messageData; 
    public static byte[] treeData;
    public static byte[] outputBytes; 
        
    //Encodes the message according to the given HuffmanCodes
    //Requires the original message as input

    public String encode(String input) {

        PriorityQueue<Node> huffmanHeap = createHuffmanHeap(input); 

        // Create the tree by making a parent node out of two nodes with the lowest frequencies
        // Repeat until size is 1 meaning only root node is left

        while (huffmanHeap.size() > 1) {
            Node leftNode = huffmanHeap.poll();
            Node rightNode = huffmanHeap.poll();
            
            int parentFreq = leftNode.freq + rightNode.freq;
            Node parentNode = new Node("", parentFreq, leftNode, rightNode);

            rootNode = parentNode;
            huffmanHeap.add(parentNode);
        }

        createTreeCodes(rootNode, "");
        encodedMessage = constructCodedMessage(input);
        return encodedMessage;
    }

    public String constructCodedMessage(String input) {

        StringBuffer messageBuffer = new StringBuffer(); 
        
        for (int i = 0; i < input.length(); i++) { 
            messageBuffer.append(huffmanCodes.get("" + input.charAt(i)));
        }

        return new String(messageBuffer); 
    }

    public static PriorityQueue<Node> createHuffmanHeap(String input) {

        HashMap<String, Integer> characterFrequency = new HashMap<>();
        PriorityQueue<Node> huffmanHeap = new PriorityQueue<Node>();
        //Create a Hashmap that contains characters and their respective frequencies
        
        for (int i = 0; i < input.length(); i++) {
            String c = input.charAt(i) + ""; 
            characterFrequency.put(c, characterFrequency.getOrDefault(c, 0) + 1);
        }
        //Create nodes with characters and their frequencies using a HashMap

        for (String key : characterFrequency.keySet()) {
            Node newNode = new Node(key, characterFrequency.get(key));
            huffmanHeap.add(newNode);
        }

        return huffmanHeap; 
    }

    // Recursively travels the tree to create the codes for each character
    // The code is essentially the same as the exercise "Alipuut" from TiraMooc 2020
    // Travelling to the left corresponds to a code output of 0
    // Travelling to the right yields 1

    public void createTreeCodes(Node currentNode, String code) {

        if (currentNode.left == null && currentNode.right == null) {
            huffmanCodes.put(currentNode.letter, code); 
            return;
        }

        createTreeCodes(currentNode.left, code + "0");
        createTreeCodes(currentNode.right, code + "1");
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
                currentNode = inputRootNode; 
            }
        }

        return decodedMessage;
    } 


    //File I/O utilities

    public void writeEncodedFile(String outputName) throws java.io.IOException {
        
        messageData = FileUtils.bitsToByte(encodedMessage);        
        treeData = HuffmanUtils.serializeToBytes(rootNode);

        outputBytes = HuffmanUtils.combineTreeDataWithMessage(treeData, messageData);

        //Write File
        FileUtils.writeFile(outputName+".hf", outputBytes);
    }

    

    public void readEncodedInput(String input, boolean fromFile) throws java.io.IOException {
        // Read File 
        byte[] inputBytes = fromFile ? FileUtils.readFile(input) : FileUtils.bitsToByte(input) ;

        byte[] treeLengthData = Arrays.copyOfRange(inputBytes, 0, 4);

        ByteBuffer bufferForTreeLengthData = ByteBuffer.wrap(treeLengthData);
        int treeLength = bufferForTreeLengthData.getInt();

        treeData = Arrays.copyOfRange(inputBytes, 4, 4+treeLength);
        messageData = Arrays.copyOfRange(inputBytes, 4+treeLength, inputBytes.length);
        encodedMessage = FileUtils.bytesToBits(messageData); 
        inputRootNode = HuffmanUtils.deserializeFromBytes(treeData);
    }

    // Getters

    public HashMap<String, String> getHuffmanDict() {
        return huffmanCodes; 
    }
  
    public Node getRootNode() {
        return rootNode; 
    }

    public Node getInputRootNode() {
        return inputRootNode; 
    }

    public String getAndConstructEncodedMessage(String input) {
        return constructCodedMessage(input); 
    }

    public String getEncodedMessage() {
        return encodedMessage;
    }

    public byte[] getTreeData() {
        return treeData; 
    }

    public byte[] getMessageData() {
        return messageData; 
    }

    public String getFullEncodedMessage() {
        return FileUtils.bytesToBits(outputBytes);
    }
}    

