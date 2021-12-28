package algorithms.utils;

import java.util.ArrayList;
import java.util.LinkedList;

import java.nio.ByteBuffer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class HuffmanUtils {
    
    /**
     * Creates a new byte[] containing the treeData as well as the length of the tree data as a 32-bit (4-byte) java int
     * @param byte[] treeBytes containing treeData
     * @param int treeLength, length of the treeBytes array
     * @return resulting byte[]
     */

    public static byte[] combineTreeBytesWithLength(byte[] treeBytes, int treeLength) {
        ByteBuffer buffer = ByteBuffer.allocate(treeBytes.length + 4);
        ByteBuffer bufferForTreeLength = ByteBuffer.allocate(4);
        bufferForTreeLength.putInt(treeBytes.length);
        byte[] treeLengthBytes = bufferForTreeLength.array();
        buffer.put(treeLengthBytes);
        buffer.put(treeBytes);
        return buffer.array();
    }

    /**
     * Creates a byte array that contains both tree data and message data
     * @param byte[] treeData, byte array containing tree data
     * @param byte[] messageData, byte array containing message data
     * @return combined byte array
     */
    public static byte[] combineTreeDataWithMessage(byte[] treeData, byte[] messageData) {
        ByteBuffer buffer = ByteBuffer.allocate(treeData.length + messageData.length);
        buffer.put(treeData);
        buffer.put(messageData);
        return buffer.array(); 
    }

    /**
     * Converts a given huffman node tree into a byte array, in order for it to be saved as a file
     * Utilizes the method serializeBFS to create an ArrayList<String>
     * @param Node root, root Node of the huffman tree
     * @return the resulting byte[] containing tree data along with length of the byte array
     */
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

    /**
     * Creates a root node for a huffman tree from a byte array input.
     * @param byte[] input, the byte array infromation of a serialized huffman node tree
     * @return huffman tree root node created from the serialized tree information
     * @throws java.io.IOException, file IOExceptions
     */
    public static Node deserializeFromBytes(byte[] input) throws java.io.IOException {
        
        ByteArrayInputStream byteInput = new ByteArrayInputStream(input);
        DataInputStream dataStream = new DataInputStream(byteInput);
        ArrayList<String> serializedInput = new ArrayList<>();
        
        while (dataStream.available() != 0) {
            String current = dataStream.readUTF();
            if (current.equals("null")) {
                serializedInput.add("null");
            } else {
                serializedInput.add(current);
            }
        }
        
        dataStream.close();
        Node node = deserializeBFS(serializedInput);
        return node; 
    }

    /**
     * Uses the root node of the Huffman tree and serializes into an ArrayList
     * Implements BFS
     */
    public static ArrayList<String> serializeBFS(Node root) {
        ArrayList<String> result = new ArrayList<>();
        LinkedList<Node> queue = new LinkedList<>();

        queue.addFirst(root);
        
        while (!queue.isEmpty()) {
            Node first = queue.pollFirst(); 
            if (first == null || first.letter.equals("null")) { 
                result.add("null");
            } else {
                result.add(first.getLetter());   
                queue.addLast(first.getLeftNode());
                queue.addLast(first.getRightNode());
            }
        }
        return result; 
    }
    /**
     * Uses the serialized ArrayList<String> format of a Huffman node tree to convert into a root node
     * implements BFS
     */
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
            } else {
                first.setLeftNode(null);
            }
            
            index++; 
            
            if (!serializedInput.get(index).equals("null")) {
                Node newRightNode = new Node(serializedInput.get(index));
                first.setRightNode(newRightNode);
                queue.addLast(newRightNode);
            } else {
                first.setRightNode(null);
            }
            index++; 
        }    
        return rootNode;
    }



}
