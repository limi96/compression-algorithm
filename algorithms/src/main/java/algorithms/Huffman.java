package algorithms; 

import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Arrays;
import java.nio.ByteBuffer;

import algorithms.utils.FileUtils;
import algorithms.utils.HuffmanUtils;
import algorithms.utils.Node;

public class Huffman {
    /**
     * Initialize data structures
     */
    
    public static HashMap<String, String> huffmanCodes = new HashMap<>();

    /**
     * Required for encoding and writing. 
     * RootNode is the resulting root Node for the Huffman Tree after encoding
     */
     
    public static Node rootNode;
    public static String encodedMessage; 
    
    /**
     * Required for reading and decoding
     * InputRootNode is the resulting huffman Tree needed to retrieve the original character
     * given the decoded input
     */
    public static Node inputRootNode;
    public static byte[] messageData; 
    public static byte[] treeData;
    public static byte[] outputBytes; 

    /**
     * Performs the encoding process for  the Huffman algorithm. The method itself 
     *  By constructing a huffman node tree. Auxiliary methods, createTreeCodes and 
     * constructCodedMessage are called to create the final encoded message as a String
     *  @param input String input to be encoded
     */

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

    /**
     *
     * Using the input and the huffmanCodes-dictionary to create a binary string
     * that is the encoded form of the input message.
     * @param String input the original input message (given to the method encode)
     * @return new String, the encoded message as a binary string
     */

    public String constructCodedMessage(String input) {

        StringBuffer messageBuffer = new StringBuffer(); 
        
        for (int i = 0; i < input.length(); i++) { 
            messageBuffer.append(huffmanCodes.get("" + input.charAt(i)));
        }

        return new String(messageBuffer); 
    }


    /**
    * Creates a huffman heap as a PriorityQueue which places the Nodes in the order of the one  
    * with the most frequently occurring character first for the encoding process. 
    * First creates a Hashmap that contains characters and their respective frequency. 
    * Then creates nodes with characters and their frequencies using a HashMap   
    */

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

    /**
     * Creates the HuffmanCodes dictionary/HashMap which translates each character into huffman encoding in binary.
     * Recursively travels the tree to create the codes for each character
     * Travelling to the left corresponds to a code output of 0
     * Travelling to the right yields 1
     * @param Node currentNode The current node being traversed. Initial value is root node from huffman encoding
     * @param String code Encoded instruction to traverse to the current node
     */

    public void createTreeCodes(Node currentNode, String code) {

        if (currentNode.left == null && currentNode.right == null) {
            huffmanCodes.put(currentNode.letter, code); 
            return;
        }
        createTreeCodes(currentNode.left, code + "0");
        createTreeCodes(currentNode.right, code + "1");
    }

    /**
     * Decodes the binary string back to the original message
     * Go through each of the 0s and 1s along the tree until we find a character
     * If a character is found, add it to the decoded message
     * Then return back to the root
     * @param Node currentNode, given as the root node of the encoded huffman tree
     * @param String message, the binary string encoded message to be decoded
     * @return String decoded message, the original message decoded
     */

    public String decode(Node currentNode, String message) {

        StringBuffer outputBuffer = new StringBuffer(); 
        //Go through each of the 0s and 1s along the tree until we find a character
        for (int i = 0; i < message.length(); i++) { 
            String c = message.charAt(i) + ""; 

            if (c.equals("1")) {
                currentNode = currentNode.right;
            } else if (c.equals("0")) {
                currentNode = currentNode.left;
            }

            //If a character is found, add it to the decoded message
            //Then return back to the root 
            if (currentNode.left == null && currentNode.right == null && currentNode.letter != null) {
                outputBuffer.append(currentNode.letter); 
                currentNode = inputRootNode; 
            }
        }

        return outputBuffer.toString();
    } 

    /**
     * File I/O Method
     * Creates a file that contains the resulting Huffman node tree and encoded message.
     * Saves the file with an .hf-extension
     * @param String outputName, the desired name for the file
     */
    public void writeEncodedFile(String outputName) throws java.io.IOException {
        
        messageData = FileUtils.bitsToByte(encodedMessage);        
        treeData = HuffmanUtils.serializeToBytes(rootNode);
        outputBytes = HuffmanUtils.combineTreeDataWithMessage(treeData, messageData);

        FileUtils.writeFile(outputName + ".hf", outputBytes);
    }

    
    /**
     * File I/O Method
     * Reads a .hf-file that should contain both the Huffman node tree as well as the encoded message
     * Sets global variables, treeData,messageData,encodedMessage, and inputRootNode (Huffman tree root node for decoding)
     * @param String inputName, name of the Huffman encoded file.
     *  
     */
    public void readEncodedInput(String input, boolean fromFile) throws java.io.IOException {

        byte[] inputBytes = fromFile ? FileUtils.readFile(input) : FileUtils.bitsToByte(input);
        byte[] treeLengthData = Arrays.copyOfRange(inputBytes, 0, 4);

        ByteBuffer bufferForTreeLengthData = ByteBuffer.wrap(treeLengthData);
        int treeLength = bufferForTreeLengthData.getInt();

        treeData = Arrays.copyOfRange(inputBytes, 4, 4 + treeLength);
        messageData = Arrays.copyOfRange(inputBytes, 4 + treeLength, inputBytes.length);
        encodedMessage = FileUtils.bytesToBits(messageData); 
        inputRootNode = HuffmanUtils.deserializeFromBytes(treeData);
    }


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

