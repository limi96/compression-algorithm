
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class Huffman {
    //Initialize data structures

    public static HashMap<String, String> huffmanCodes = new HashMap<>();
    public static HashMap<String, String> huffmanDictReverse = new HashMap<>();
    public static HashMap<String, Integer> codeFreq = new HashMap<>(); 
    public static Node rootNode = null;
    public static String encodedMessage = "";

    public static int treeLengthFromBytes; 

    public static void testingSerializingNodeTree() throws java.io.IOException {
        Node node = new Node("a"); 
        node.setLeftNode(new Node("b"));
        node.left.setLeftNode(new Node("c"));
        node.setRightNode(new Node("d"));
        node.left.left.setLeftNode(new Node("e"));
        
        Huffman h = new Huffman(); 
        
        // testSerialization(node);
        // rootNode is the resulting Huffman Node Tree after encoding

        String message = "ab ba ÄÄÖÖpowfälwqkeffadöl cscscsjdflkasjdf   asd fa sdf a sfdÅÄ*";
        // message = "öapogggggeqjfg uGJÖdfmDNV-LNDkFLVH6019243801923840912384019238410293841093284SFäpvjÄGH  053YH    3RUTNYRT   JRÄGJRW";
        // message = "My balls are dry and my butt is hurt";
        // message = "abajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecdabajölfjqölgjewlfjqwlejfqwöejfqkecd";
        message = FileUtils.fileReaderOutput("100_KB_lorem.txt");

        long start = System.currentTimeMillis();
        String testEncodedMessage = h.encode(message);
        
        //Combine treeData with encodedMessage Data
        byte[] messageData = encodedMessageToBytes(testEncodedMessage);        
        byte[] treeData = serializeToBytes(rootNode);
        byte[] outputBytes = combineTreeDataWithMessage(treeData, messageData);

        FileUtils.writeFile("huffmanTest", outputBytes);
        
        byte[] inputBytes = FileUtils.readFile("huffmanTest.hf");
        byte[] treeLengthBytes = Arrays.copyOfRange(inputBytes, 0, 4);
        
        ByteBuffer bufferForTreeLength = ByteBuffer.wrap(treeLengthBytes);
        int treeLength = bufferForTreeLength.getInt();
        
        // System.out.println(testEncodedMessage);
        System.out.println("Tree Length actually : " + treeLength);
        System.out.println(Arrays.toString(treeLengthBytes));
    
        // byte[] inputForDeserialization = Arrays.copyOfRange(inputBytes, 4, inputBytes.length-2);
        byte[] inputForDeserialization = Arrays.copyOfRange(inputBytes, 4, 4+treeLength);
        Node testNode = deserializeFromBytes(inputForDeserialization);

        
        // byte[] messageBytes = Arrays.copyOfRange(inputBytes, 4+treeLengthFromBytes, inputBytes.length);
        byte[] messageBytes = Arrays.copyOfRange(inputBytes, inputBytes.length-messageData.length, inputBytes.length);

        String testMessage = new String(messageBytes, StandardCharsets.UTF_8);
        // System.out.println("testmessage            : " + testMessage);
        // System.out.println("decoded Output         : " + h.decode(testNode, testMessage));

        String output = h.decode(testNode, testMessage); 

        System.out.println("OUTCOME : " + message.equals(output));

        long end = System.currentTimeMillis();
        System.out.println("Time taken : " + (end-start)/1E3 + " s");
        // Convert inputBytes into two different arrays
        // One is tree array one is messageArray    
    }

    public static void writeEncodedFile(String outputName) {

    }

    public static byte[] combineTreeDataWithMessage(byte[] treeData, byte[] messageData) {
        ByteBuffer buffer = ByteBuffer.allocate(treeData.length + messageData.length);
        buffer.put(treeData);
        buffer.put(messageData);
        return buffer.array(); 
    }

    public static byte[] encodedMessageToBytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }   



    public static void testSerialization(Node node) throws java.io.IOException {
        byte[] bytes = serializeToBytes(node);
        FileUtils.writeFile("testing", bytes);
        byte[] test = FileUtils.readFile("testing.hf");
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

    public static void main(String[] args) throws java.io.IOException {
        Huffman h = new Huffman();
        testingSerializingNodeTree();
    }

    public HashMap<String, String> getHuffmanDict() {
        return huffmanCodes; 
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
        return constructCodedMessage(input);
        // return "";
    }

    // Recursively travels the tree to create the codes for each character
    // The code is essentially the same as the exercise "Alipuut" from TiraMooc 2020
    // Travelling to the left corresponds to a code output of 0
    // Travelling to the right yields 1

    public void createTreeCodes(Node currentNode, String code) {

        if (currentNode.left == null && currentNode.right == null) {
            //required for encoding
            huffmanCodes.put(code, currentNode.letter);
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

    //Encodes the message according to the given HuffmanCodes
    //Requires the original message as input
    //Currently the bottleneck... takes 32 ms
    public String constructCodedMessage(String input) {

        StringBuilder messageBuilder = new StringBuilder(); 
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < input.length(); i++) { 
            messageBuilder.append(huffmanDictReverse.get("" + input.charAt(i)));
        }
        long end = System.currentTimeMillis();

        // System.out.println("Time taken : " + (end-start)/1E3 + " s");
        return new String(messageBuilder); 
    }



    public Node getRootNode() {
        return rootNode; 
    }

    public String getEncodedMessage(String input) {
        return constructCodedMessage(input); 
    }

    // Prints out output values 
    public void prints(Node rootNode, String input) {
        rootNode = getRootNode();
        String decodedOutput = decode(rootNode, constructCodedMessage(input)); 
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

