
//Simple node datastructure for the PriorityQueue and Huffman Tree

public class Node implements Comparable<Node> {
    String letter;
    int freq; 
    Node left;
    Node right;

    public Node(String letter, int freq, Node left, Node right) {
        this.letter = letter; 
        this.freq = freq; 
        this.left = left;
        this.right = right; 
    }

    public Node(String letter, int freq) {
        this.letter = letter; 
        this.freq = freq; 
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


