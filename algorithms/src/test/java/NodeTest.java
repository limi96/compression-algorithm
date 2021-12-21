import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import algorithms.utils.Node;
import java.util.PriorityQueue;

public class NodeTest {
    
    Node firstNode;
    Node secondNode; 
    Node thirdNode; 
    
    @Before
    public void setUp() {
        firstNode = new Node("a", 12);        
        secondNode = new Node("b", 10, firstNode, null); 
        thirdNode = new Node("c");    
    }

    @Test 
    public void correctConstruction() {
        assertEquals("a", firstNode.getLetter());
        assertEquals("b", secondNode.getLetter());
        assertEquals(12, firstNode.freq);
        assertEquals(10, secondNode.freq);
        
        assertEquals(firstNode, secondNode.getLeftNode());
        assertEquals(null, secondNode.getRightNode());

        assertEquals("c", thirdNode.getLetter());
        assertEquals(-100000, thirdNode.freq);
        assertEquals(null, thirdNode.getRightNode());
        assertEquals(null, thirdNode.getLeftNode());

        assertEquals("c : -100000", thirdNode.toString());
    }

    @Test
    public void testSettingLeftAndRightNodes() {
        firstNode.setLeftNode(secondNode);
        firstNode.setRightNode(thirdNode); 
        assertEquals(null, firstNode.getLeftNode().getRightNode());
        assertEquals(firstNode, firstNode.getLeftNode().getLeftNode());
    }
    
    @Test
    public void testComparable() {
        assertEquals(2, firstNode.compareTo(secondNode)); 
        assertEquals(100010, secondNode.compareTo(thirdNode)); 
        assertEquals(-100012, thirdNode.compareTo(firstNode)); 

        PriorityQueue<Node> heap = new PriorityQueue<>(); 
        heap.add(secondNode); 
        heap.add(thirdNode); 
        heap.add(firstNode); 

        assertEquals(thirdNode, heap.poll()); 
        assertEquals(secondNode, heap.poll()); 
        assertEquals(firstNode, heap.poll()); 
    }    
}
