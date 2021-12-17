package algorithms.utils;

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

public class HuffmanUtils {
        
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

}
