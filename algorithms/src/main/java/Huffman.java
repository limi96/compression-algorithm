import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class Huffman {
    //Initialize data structures
    public static HashMap<String, String> huffmanCodes = new HashMap<>();
    public static HashMap<String, Integer> codeFreq = new HashMap<>(); 

    //Required for encoding and writing
    public static Node rootNode;
    public static String encodedMessage; 
    
    //Required for reading and decoding
    public static Node inputRootNode;
    public static byte[] messageData; 
    public static byte[] treeData;
        
    public static void main(String[] args) throws java.io.IOException {
        Huffman h = new Huffman(); 
        
        // rootNode is the resulting root Node for the Huffman Tree after encoding
        
        String message = ""; 
        // message = "abckjlaksdjflakds";
        message = "123123123";
        // message = FileUtils.fileReaderOutput("100_KB_lorem.txt");
        // message = FileUtils.fileReaderOutput("100_KB_repeating_lorem_ipsum.txt");

        long start = System.currentTimeMillis();

        //create EncodedFile
        encodedMessage = h.encode(message);
        test = message;
        
        System.out.println(encodedMessage);

        writeEncodedFile("huffmanTest");
        // Read File 
        readEncodedFile("huffmanTest.hf");
    
        
        String encodedMessageFromMessageData = new String(messageData, StandardCharsets.UTF_8);
        // byte b = Byte.parseByte(encodedMessageFromMessageData, 2);
        // byte[] btest = new BigInteger(encodedMessageFromMessageData, 2).toByteArray();
        // byte[] btest = new BigInteger(encodedMessage, 2).toByteArray();
        // System.out.println("Byte : " + btest.length);

        System.out.println(encodedMessageFromMessageData.length());
        // System.out.println(encodedMessageToBytes(encodedMessage).length);
        // Control test
        // String controlMessage = new String(messageData, StandardCharsets.UTF_8);
        // System.out.println("CONTROL OUTCOME : " + controlMessage.equals(encodedMessage));

        

        String output = h.decode(inputRootNode, encodedMessageFromMessageData); 
        System.out.println("ENCODE-DECODE OUTCOME : " + message.equals(output));
        System.out.println("output" + " : " + output);
        long end = System.currentTimeMillis();
        System.out.println("Time taken : " + (end-start)/1E3 + " s");
    }

    public static String test; 

    public static void writeEncodedFile(String outputName) throws java.io.IOException {
        //Combine treeData with encodedMessage Data
        // byte[] messageData = encodedMessageToBytes(encodedMessage);
        
        byte[] messageData = encodedMessageToBytes(test);        
        byte[] treeData = serializeToBytes(rootNode);
        byte[] outputBytes = combineTreeDataWithMessage(treeData, messageData);

        System.out.println("messageData : " + Arrays.toString(messageData));
        bytesToBits(messageData);
        System.out.println("TreeData : " + treeData.length);
        System.out.println("messageData : " + messageData.length);

        //Write File
        FileUtils.writeFile(outputName, outputBytes);
    }

    public static byte[] encodedMessageToBytes(String input) {

        // byte[] byteArray = new BigInteger(input, 2).toByteArray();

        // return input.getBytes(StandardCharsets.UTF_8);

        int byteOffset = 8; 

        BitSet bitBuilder = new BitSet();

        System.out.println("Input Length : " + input.length());
        System.out.println("Test : " + test);
        
        //This is wrong
        for (int i = 0; i < input.length(); i++) { 
            String currentCode = huffmanCodes.get("" + input.charAt(i));

            for (int j = 0; j < currentCode.length(); j++) { 
                if (currentCode.charAt(j) == 0) {
                    bitBuilder.set(j + byteOffset, false);
                }
                else {
                    bitBuilder.set(j + byteOffset, true);
                }
                // bitBuilder.set(j + byteOffset, currentCode.charAt(j));
            }
            byteOffset += currentCode.length(); 
        }
        
        System.out.println("BitBuilder : " + bitBuilder.toString());

        return bitBuilder.toByteArray();
    }

    public static void bytesToBits(byte[] messageData) {
        BitSet bitBuilder = new BitSet();

        // for (int i = 0; i < messageData.length * 8; i++) {
        //     if ((messageData[messageData.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
        //       bitBuilder.set(i);
        //     }
        //   }

        for (int i = 0; i < messageData.length; i++) {
            for (int j = 0; j < 8; j++) { 
                
                // System.out.println((1 << j) & messageData[i]);

                if (((1 << j) & messageData[i]) == 0) {
                    // System.out.print("0");
                    
                }
                else {
                    // System.out.print("1");
                }
            }
        }
        System.out.println();
        System.out.println("BitBuilder : " + bitBuilder.toString());
    }

    public String constructCodedMessage(String input) {

        StringBuilder messageBuilder = new StringBuilder(); 

        long start = System.currentTimeMillis();
        for (int i = 0; i < input.length(); i++) { 
            messageBuilder.append(huffmanCodes.get("" + input.charAt(i)));
        }
        long end = System.currentTimeMillis();

        // System.out.println("Time taken : " + (end-start)/1E3 + " s");
        return new String(messageBuilder); 
    }

    public String encode(String input) {

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
        encodedMessage = constructCodedMessage(input);
        return encodedMessage;
        // return "";
    }


    public static void readEncodedFile(String inputName) throws java.io.IOException {
        // Read File 
        byte[] inputBytes = FileUtils.readFile(inputName);
        byte[] treeLengthData = Arrays.copyOfRange(inputBytes, 0, 4);

        ByteBuffer bufferForTreeLength = ByteBuffer.wrap(treeLengthData);
        int treeLength = bufferForTreeLength.getInt();

        treeData = Arrays.copyOfRange(inputBytes, 4, 4+treeLength);
        messageData = Arrays.copyOfRange(inputBytes, 4+treeLength, inputBytes.length);
        inputRootNode = deserializeFromBytes(treeData);
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

    public void createHuffmanHashMap() {

    }
  


    // Recursively travels the tree to create the codes for each character
    // The code is essentially the same as the exercise "Alipuut" from TiraMooc 2020
    // Travelling to the left corresponds to a code output of 0
    // Travelling to the right yields 1

    public void createTreeCodes(Node currentNode, String code) {

        if (currentNode.left == null && currentNode.right == null) {
            huffmanCodes.put(currentNode.letter, code); 
            codeFreq.put(code, currentNode.freq); 
            // encodedMessage += code;
            return;
        }

        createTreeCodes(currentNode.left, code + "0");
        createTreeCodes(currentNode.right, code + "1");
    }

    //Encodes the message according to the given HuffmanCodes
    //Requires the original message as input
    //Currently the bottleneck... takes 32 ms


    public Node getRootNode() {
        return rootNode; 
    }

    public String getEncodedMessage(String input) {
    //   public String getEncodedMessage() {
    //    return encodedMessage;
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

    
    public long outputByteSize() {
        long size = 0; 
        for (String code : codeFreq.keySet()) {
            size += code.length()*codeFreq.get(code);
        }
        return size/8; 
    }

    public HashMap<String, Integer> getCodeFreq() {
        return codeFreq; 
    }


}    

