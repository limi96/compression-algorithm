package algorithms.utils; 

//Simple node datastructure for the PriorityQueue and Huffman Tree
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

    public int getFreq() {
        return this.freq; 
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

    @Override
    public int compareTo(Node other) {
        return this.freq-other.freq;
    }

    @Override
    public String toString() {
        return this.letter + " : " + this.freq;
    }


}


