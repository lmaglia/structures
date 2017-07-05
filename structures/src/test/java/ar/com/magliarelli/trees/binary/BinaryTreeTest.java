package ar.com.magliarelli.trees.binary;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Lucas Magliarelli
 *
 */
@RunWith(JUnit4.class)
public class BinaryTreeTest {
	@Test
	public void nil(){
		BinaryTree<Integer> nil= BinaryTree.nil();
		Assert.assertTrue(nil.isNil());
		Assert.assertFalse(nil.isLeaf());
		Assert.assertEquals(0, nil.height());
		Assert.assertEquals(0, nil.size());
	}
	@Test
	public void leaf(){
		BinaryTree<Integer> tree= BinaryTree.leaf(1);
		Assert.assertFalse(tree.isNil());
		Assert.assertEquals(new Integer(1), tree.root());
		Assert.assertEquals(1, tree.height());
		Assert.assertEquals(1, tree.size());
		Assert.assertTrue(tree.left().isNil());
		Assert.assertTrue(tree.right().isNil());
		Assert.assertTrue(tree.isLeaf());
	}
	@Test
	public void binWithLeftNode(){
		BinaryTree<Integer> left= BinaryTree.leaf(1);
		BinaryTree<Integer> tree= BinaryTree.bin(left, 2, BinaryTree.nil());
		Assert.assertFalse(tree.isNil());
		Assert.assertEquals(new Integer(2), tree.root());
		Assert.assertEquals(2, tree.height());
		Assert.assertEquals(2, tree.size());
		Assert.assertFalse(tree.left().isNil());
		Assert.assertTrue(tree.right().isNil());
		Assert.assertEquals(new Integer(1), tree.left().root());
		Assert.assertTrue(tree.left().isLeaf());
	}
	@Test
	public void binWithRightNode(){
		BinaryTree<Integer> right= BinaryTree.leaf(1);
		BinaryTree<Integer> tree= BinaryTree.bin(BinaryTree.nil(), 2, right);
		Assert.assertFalse(tree.isNil());
		Assert.assertEquals(new Integer(2), tree.root());
		Assert.assertEquals(2, tree.height());
		Assert.assertEquals(2, tree.size());
		Assert.assertTrue(tree.left().isNil());
		Assert.assertFalse(tree.right().isNil());
		Assert.assertEquals(new Integer(1), tree.right().root());
		Assert.assertTrue(tree.right().isLeaf());
	}
	@Test
	public void binWithBothChildren(){
		BinaryTree<Integer> left= BinaryTree.leaf(1);
		BinaryTree<Integer> right= BinaryTree.leaf(3);
		BinaryTree<Integer> tree= BinaryTree.bin(left, 2, right);
		Assert.assertFalse(tree.isNil());
		Assert.assertEquals(new Integer(2), tree.root());
		Assert.assertEquals(2, tree.height());
		Assert.assertEquals(3, tree.size());
		Assert.assertFalse(tree.left().isNil());
		Assert.assertFalse(tree.right().isNil());
		Assert.assertEquals(new Integer(1), tree.left().root());
		Assert.assertTrue(tree.left().isLeaf());
		Assert.assertEquals(new Integer(3), tree.right().root());
		Assert.assertTrue(tree.right().isLeaf());
	}
	@Test
	public void preorderIterator(){
		List<String> expected= Arrays.asList("F","B","A", "D", "C","E", "G", "I", "H");
		BinaryTree<String> leafA= BinaryTree.leaf("A");
		BinaryTree<String> leafC= BinaryTree.leaf("C");
		BinaryTree<String> leafE= BinaryTree.leaf("E");
		BinaryTree<String> leafH= BinaryTree.leaf("H");
		BinaryTree<String> nodeD= BinaryTree.bin(leafC, "D", leafE);
		BinaryTree<String> nodeI= BinaryTree.bin(leafH, "I", BinaryTree.nil());
		BinaryTree<String> nodeB= BinaryTree.bin(leafA, "B", nodeD);
		BinaryTree<String> nodeG= BinaryTree.bin(BinaryTree.nil(), "G", nodeI);
		BinaryTree<String> nodeF= BinaryTree.bin(nodeB, "F", nodeG);
		
		List<String> actual= new LinkedList<String>();
		Iterator<String> i= nodeF.preOrderIterator();
		while(i.hasNext()){ 
			actual.add(i.next());
		}
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void preorderIteratorNil(){
		List<String> expected= new LinkedList<String>();		
		BinaryTree<String> nil= BinaryTree.nil();
		List<String> actual= new LinkedList<String>();
		Iterator<String> i= nil.preOrderIterator();
		while(i.hasNext()){ 
			actual.add(i.next());
		}
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void inOrderIterator(){
		List<String> expected= Arrays.asList("A","B","C", "D", "E","F", "G", "H", "I");
		BinaryTree<String> leafA= BinaryTree.leaf("A");
		BinaryTree<String> leafC= BinaryTree.leaf("C");
		BinaryTree<String> leafE= BinaryTree.leaf("E");
		BinaryTree<String> leafH= BinaryTree.leaf("H");
		BinaryTree<String> nodeD= BinaryTree.bin(leafC, "D", leafE);
		BinaryTree<String> nodeI= BinaryTree.bin(leafH, "I", BinaryTree.nil());
		BinaryTree<String> nodeB= BinaryTree.bin(leafA, "B", nodeD);
		BinaryTree<String> nodeG= BinaryTree.bin(BinaryTree.nil(), "G", nodeI);
		BinaryTree<String> nodeF= BinaryTree.bin(nodeB, "F", nodeG);
		
		List<String> actual= new LinkedList<String>();
		Iterator<String> i= nodeF.inOrderIterator();
		while(i.hasNext()){ 
			actual.add(i.next());
		}
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void inOrderIteratorNil(){
		List<String> expected= new LinkedList<String>();		
		BinaryTree<String> nil= BinaryTree.nil();
		List<String> actual= new LinkedList<String>();
		Iterator<String> i= nil.inOrderIterator();
		while(i.hasNext()){ 
			actual.add(i.next());
		}
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void postOrderIterator(){
		List<String> expected= Arrays.asList("A", "C", "E", "D", "B", "H", "I", "G", "F");
		BinaryTree<String> leafA= BinaryTree.leaf("A");
		BinaryTree<String> leafC= BinaryTree.leaf("C");
		BinaryTree<String> leafE= BinaryTree.leaf("E");
		BinaryTree<String> leafH= BinaryTree.leaf("H");
		BinaryTree<String> nodeD= BinaryTree.bin(leafC, "D", leafE);
		BinaryTree<String> nodeI= BinaryTree.bin(leafH, "I", BinaryTree.nil());
		BinaryTree<String> nodeB= BinaryTree.bin(leafA, "B", nodeD);
		BinaryTree<String> nodeG= BinaryTree.bin(BinaryTree.nil(), "G", nodeI);
		BinaryTree<String> nodeF= BinaryTree.bin(nodeB, "F", nodeG);
		
		List<String> actual= new LinkedList<String>();
		Iterator<String> i= nodeF.postOrderIterator();
		while(i.hasNext()){ 
			actual.add(i.next());
		}
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void postOrderIteratorNil(){
		List<String> expected= new LinkedList<String>();		
		BinaryTree<String> nil= BinaryTree.nil();
		List<String> actual= new LinkedList<String>();
		Iterator<String> i= nil.postOrderIterator();
		while(i.hasNext()){ 
			actual.add(i.next());
		}
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void bfsIterator(){
		List<String> expected= Arrays.asList("F", "B", "G", "A", "D", "I", "C", "E", "H");
		BinaryTree<String> leafA= BinaryTree.leaf("A");
		BinaryTree<String> leafC= BinaryTree.leaf("C");
		BinaryTree<String> leafE= BinaryTree.leaf("E");
		BinaryTree<String> leafH= BinaryTree.leaf("H");
		BinaryTree<String> nodeD= BinaryTree.bin(leafC, "D", leafE);
		BinaryTree<String> nodeI= BinaryTree.bin(leafH, "I", BinaryTree.nil());
		BinaryTree<String> nodeB= BinaryTree.bin(leafA, "B", nodeD);
		BinaryTree<String> nodeG= BinaryTree.bin(BinaryTree.nil(), "G", nodeI);
		BinaryTree<String> nodeF= BinaryTree.bin(nodeB, "F", nodeG);
		
		List<String> actual= new LinkedList<String>();
		Iterator<String> i= nodeF.bfsIterator();
		while(i.hasNext()){ 
			actual.add(i.next());
		}
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void bfsIteratorNil(){
		List<String> expected= new LinkedList<String>();		
		BinaryTree<String> nil= BinaryTree.nil();
		List<String> actual= new LinkedList<String>();
		Iterator<String> i= nil.bfsIterator();
		while(i.hasNext()){ 
			actual.add(i.next());
		}
		Assert.assertEquals(expected, actual);
	}
}
