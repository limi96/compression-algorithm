package algorithms.utils; 

/**
 * Simple node datastructure for the PriorityQueue and Huffman Tree required for encoding and decoding 
 * Each node has a letter that it represents as well as its frequency. 
 * Additionally, each node has two children in order to form the tree as a Binary Tree. 
 */

public class Node implements Comparable<Node> {
    public String letter;
    public int freq; 
    public Node left;
    public Node right;

    public Node(String letter, int freq, Node left, Node right) {
        this.letter = letter; 
        this.freq = freq; 
        this.left = left;
        this.right = right; 
    }

    public Node(String letter, int freq) {
        this.letter = letter; 
        this.freq = freq; 
        this.left = null; 
        this.right = null; 
    }

    public Node(String letter) {
        this.letter = letter; 
        this.freq = -100000; 
        this.left = null; 
        this.right = null; 
    }

    public String getLetter() {
        return this.letter; 
    }

    public void setLeftNode(Node leftNode) {
        this.left = leftNode; 
    }

    public void setRightNode(Node rightNode) {
        this.right = rightNode; 
    }

    public Node getLeftNode() {
        return this.left; 
    }

    public Node getRightNode() {
        return this.right; 
    }

    /**
     * Comparable for the heap datastructure implemented a PriorityQueue for Huffman encoding and decoding
     */
    @Override
    public int compareTo(Node other) {
        return this.freq - other.freq;
    }

    @Override
    public String toString() {
        return this.letter + " : " + this.freq;
    }


}


