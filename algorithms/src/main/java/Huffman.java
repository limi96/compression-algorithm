import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import java.nio.ByteBuffer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;


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
        
    public static void main(String[] args) throws java.io.IOException {
        Huffman h = new Huffman(); 
        String input = ""; 
        // input = "abckjlaksdjflakds";
        input = FileUtils.fileReaderOutput("100_KB_lorem.txt");
        // input = FileUtils.fileReaderOutput("100_KB_repeating_lorem_ipsum.txt");

        long start = System.currentTimeMillis();

        //create EncodedFile
        encodedMessage = h.encode(input);
        writeEncodedFile("huffmanTest.hf");
        //Reset encodedMessage
        encodedMessage = ""; 
        //Read file. EncodedMessage gets updated now
        readEncodedFile("huffmanTest.hf");
        
        String output = h.decode(inputRootNode, encodedMessage); 
        System.out.println("ENCODE-DECODE OUTCOME : " + input.equals(output));
        long end = System.currentTimeMillis();
        System.out.println("Time taken : " + (end-start)/1E3 + " s");
    }

    public byte[] getTreeData() {
        return treeData; 
    }

    public byte[] getMessageData() {
        return messageData; 
    }
    
    public static void writeEncodedFile(String outputName) throws java.io.IOException {
        
        byte[] messageData = FileUtils.bitsToByte(encodedMessage);        
        byte[] treeData = serializeToBytes(rootNode);
        byte[] outputBytes = combineTreeDataWithMessage(treeData, messageData);

        //Write File
        FileUtils.writeFile(outputName, outputBytes);
    }

    public static void readEncodedFile(String inputName) throws java.io.IOException {
        // Read File 
        byte[] inputBytes = FileUtils.readFile(inputName);
        byte[] treeLengthData = Arrays.copyOfRange(inputBytes, 0, 4);

        ByteBuffer bufferForTreeLengthData = ByteBuffer.wrap(treeLengthData);
        int treeLength = bufferForTreeLengthData.getInt();

        treeData = Arrays.copyOfRange(inputBytes, 4, 4+treeLength);
        messageData = Arrays.copyOfRange(inputBytes, 4+treeLength, inputBytes.length);
        encodedMessage = FileUtils.bytesToBits(messageData); 
        inputRootNode = deserializeFromBytes(treeData);
    }

    //Encodes the message according to the given HuffmanCodes
    //Requires the original message as input

    public String constructCodedMessage(String input) {

        StringBuilder messageBuilder = new StringBuilder(); 

        long start = System.currentTimeMillis();
        for (int i = 0; i < input.length(); i++) { 
            messageBuilder.append(huffmanCodes.get("" + input.charAt(i)));
        }
        long end = System.currentTimeMillis();

        System.out.println("Time taken : " + (end-start)/1E3 + " s");
        return new String(messageBuilder); 
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

    public static byte[] combineTreeDataWithMessage(byte[] treeData, byte[] messageData) {
        ByteBuffer buffer = ByteBuffer.allocate(treeData.length + messageData.length);
        buffer.put(treeData);
        buffer.put(messageData);
        return buffer.array(); 
    }

    public static byte[] serializeToBytes(Node root) throws java.io.IOException {

        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteOutput);
        ArrayList<String> serializedInput = serializeBFS(root);

        for (String input : serializedInput) {
            dataStream.writeUTF(input);
        }
        dataStream.close();
        byte[] byteOutputArray = byteOutput.toByteArray();
        return combineTreeBytesWithLength(byteOutputArray, byteOutputArray.length);
    }

    public static byte[] combineTreeBytesWithLength(byte[] treeBytes, int treeLength) {
        ByteBuffer buffer = ByteBuffer.allocate(treeBytes.length + 4);
        ByteBuffer bufferForTreeLength = ByteBuffer.allocate(4);
        bufferForTreeLength.putInt(treeBytes.length);
        byte[] treeLengthBytes = bufferForTreeLength.array();
        buffer.put(treeLengthBytes);
        buffer.put(treeBytes);
        return buffer.array();
    }

    public static Node deserializeFromBytes(byte[] input) throws java.io.IOException {
        
        ByteArrayInputStream byteInput = new ByteArrayInputStream(input);
        DataInputStream dataStream = new DataInputStream(byteInput);
        ArrayList<String> serializedInput = new ArrayList<>();
        
        while (dataStream.available() != 0) {
            String current = dataStream.readUTF();
            if (current.equals("null")) {
                serializedInput.add("null");
            }
            else {
                serializedInput.add(current);
            }
        }
        
        dataStream.close();
        Node node = deserializeBFS(serializedInput);
        return node; 
    }

    public static Node deserializeBFS(ArrayList<String> serializedInput) {
        
        LinkedList<Node> queue = new LinkedList<>();

        Node rootNode = new Node(serializedInput.get(0));
        queue.add(rootNode);
        int index = 1; 

        while (!queue.isEmpty()) {
            Node first = queue.pollFirst();  

            if (!serializedInput.get(index).equals("null")) {        
                Node newLeftNode = new Node(serializedInput.get(index));
                first.setLeftNode(newLeftNode);
                queue.addLast(newLeftNode);
            }
            else {
                first.setLeftNode(null);
            }

            index++; 

            if (!serializedInput.get(index).equals("null")) {
                Node newRightNode = new Node(serializedInput.get(index));
                first.setRightNode(newRightNode);
                queue.addLast(newRightNode);
            }
            else {
                first.setRightNode(null);
            }

            index++; 
        }    
        return rootNode;
    }

    public static ArrayList<String> serializeBFS(Node root) {
        ArrayList<String> result = new ArrayList<>();
        LinkedList<Node> queue = new LinkedList<>();

        queue.addFirst(root);
        
        while(!queue.isEmpty()) {
            Node first = queue.pollFirst(); 
            if (first == null || first.letter.equals("null")) { 
                result.add("null");
            }
            else {
                result.add(first.getLetter());   
                queue.addLast(first.getLeftNode());
                queue.addLast(first.getRightNode());
            }
        }

        return result; 
    }

    public HashMap<String, String> getHuffmanDict() {
        return huffmanCodes; 
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

    public Node getRootNode() {
        return rootNode; 
    }

    public String getEncodedMessage(String input) {
        return constructCodedMessage(input); 
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
}    

