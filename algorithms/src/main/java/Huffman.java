
import java.util.PriorityQueue;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Huffman {
    //Initialize data structures

    public static HashMap<String, String> huffmanDict = new HashMap<>();
    public static HashMap<String, String> huffmanDictReverse = new HashMap<>();
    public static HashMap<String, Integer> codeFreq = new HashMap<>(); 
    public static Node rootNode = null;
    public static String encodedMessage = "";

    public static void testingSerializingNodeTree() throws java.io.IOException {
        Node node = new Node("a"); 
        node.setLeftNode(new Node("b"));
        node.left.setLeftNode(new Node("c"));
        node.setRightNode(new Node("d"));
        node.left.left.setLeftNode(new Node("e"));

    //      a
    //     / \
    //     b  d
    //    / \
    //   c
    //  /
    //  e
    // a, b, d, c, null, null, null, e, null, null, null
        
        Huffman h = new Huffman(); 
        // testSerialization(node);

        // rootNode is the resulting Huffman Node Tree after encoding
 
        h.encode("ab ba ÄÄÖÖpowfälwqkeffadöl cscscsjdflkasjdf   asd fa sdf a sfdÅÄ*");
        testSerialization(rootNode);
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
        
        return byteOutput.toByteArray();
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
        
        Node node = deserializeBFS(serializedInput);
        return node; 
    }



    public static Node deserializeBFS(ArrayList<String> serializedInput) {
        
        LinkedList<Node> queue = new LinkedList<>();
        //Root node
        Node node = new Node(serializedInput.get(0));
        
        queue.add(node);
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
        return node;
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


}    

