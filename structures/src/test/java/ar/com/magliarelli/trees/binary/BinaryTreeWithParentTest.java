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
public class BinaryTreeWithParentTest {
	@Test
	public void nil(){
		BinaryTreeWithParent<Integer> nil= BinaryTreeWithParent.nil();
		Assert.assertTrue(nil.isNil());
		Assert.assertFalse(nil.isLeaf());
		Assert.assertEquals(0, nil.height());
		Assert.assertEquals(0, nil.size());
		Assert.assertNull(nil.parent());
	}
	@Test
	public void leaf(){
		BinaryTreeWithParent<Integer> tree= BinaryTreeWithParent.leaf(1);
		Assert.assertFalse(tree.isNil());
		Assert.assertEquals(new Integer(1), tree.root());
		Assert.assertEquals(1, tree.height());
		Assert.assertEquals(1, tree.size());
		Assert.assertTrue(tree.left().isNil());
		Assert.assertTrue(tree.right().isNil());
		Assert.assertTrue(tree.isLeaf());
		Assert.assertEquals(tree.left().parent(), tree);
		Assert.assertEquals(tree.right().parent(), tree);
	}
	@Test
	public void binWithLeftNode(){
		BinaryTreeWithParent<Integer> left= BinaryTreeWithParent.leaf(1);
		BinaryTreeWithParent<Integer> tree= BinaryTreeWithParent.bin(left, 2, BinaryTreeWithParent.nil());
		Assert.assertFalse(tree.isNil());
		Assert.assertEquals(new Integer(2), tree.root());
		Assert.assertEquals(2, tree.height());
		Assert.assertEquals(2, tree.size());
		Assert.assertFalse(tree.left().isNil());
		Assert.assertTrue(tree.right().isNil());
		Assert.assertEquals(new Integer(1), tree.left().root());
		Assert.assertTrue(tree.left().isLeaf());
		Assert.assertEquals(left.parent(), tree);
		Assert.assertEquals(tree.right().parent(), tree);
	}
	@Test
	public void binWithRightNode(){
		BinaryTreeWithParent<Integer> right= BinaryTreeWithParent.leaf(1);
		BinaryTreeWithParent<Integer> tree= BinaryTreeWithParent.bin(BinaryTreeWithParent.nil(), 2, right);
		Assert.assertFalse(tree.isNil());
		Assert.assertEquals(new Integer(2), tree.root());
		Assert.assertEquals(2, tree.height());
		Assert.assertEquals(2, tree.size());
		Assert.assertTrue(tree.left().isNil());
		Assert.assertFalse(tree.right().isNil());
		Assert.assertEquals(new Integer(1), tree.right().root());
		Assert.assertTrue(tree.right().isLeaf());
		Assert.assertEquals(right.parent(), tree);
		Assert.assertEquals(tree.left().parent(), tree);
	}
	@Test
	public void binWithBothChildren(){
		BinaryTreeWithParent<Integer> left= BinaryTreeWithParent.leaf(1);
		BinaryTreeWithParent<Integer> right= BinaryTreeWithParent.leaf(3);
		BinaryTreeWithParent<Integer> tree= BinaryTreeWithParent.bin(left, 2, right);
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
		Assert.assertEquals(left.parent(), tree);
		Assert.assertEquals(right.parent(), tree);
	}
	@Test
	public void preorderIterator(){
		List<String> expected= Arrays.asList("F","B","A", "D", "C","E", "G", "I", "H");
		BinaryTreeWithParent<String> leafA= BinaryTreeWithParent.leaf("A");
		BinaryTreeWithParent<String> leafC= BinaryTreeWithParent.leaf("C");
		BinaryTreeWithParent<String> leafE= BinaryTreeWithParent.leaf("E");
		BinaryTreeWithParent<String> leafH= BinaryTreeWithParent.leaf("H");
		BinaryTreeWithParent<String> nodeD= BinaryTreeWithParent.bin(leafC, "D", leafE);
		BinaryTreeWithParent<String> nodeI= BinaryTreeWithParent.bin(leafH, "I", BinaryTreeWithParent.nil());
		BinaryTreeWithParent<String> nodeB= BinaryTreeWithParent.bin(leafA, "B", nodeD);
		BinaryTreeWithParent<String> nodeG= BinaryTreeWithParent.bin(BinaryTreeWithParent.nil(), "G", nodeI);
		BinaryTreeWithParent<String> nodeF= BinaryTreeWithParent.bin(nodeB, "F", nodeG);
		
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
		BinaryTreeWithParent<String> nil= BinaryTreeWithParent.nil();
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
		BinaryTreeWithParent<String> leafA= BinaryTreeWithParent.leaf("A");
		BinaryTreeWithParent<String> leafC= BinaryTreeWithParent.leaf("C");
		BinaryTreeWithParent<String> leafE= BinaryTreeWithParent.leaf("E");
		BinaryTreeWithParent<String> leafH= BinaryTreeWithParent.leaf("H");
		BinaryTreeWithParent<String> nodeD= BinaryTreeWithParent.bin(leafC, "D", leafE);
		BinaryTreeWithParent<String> nodeI= BinaryTreeWithParent.bin(leafH, "I", BinaryTreeWithParent.nil());
		BinaryTreeWithParent<String> nodeB= BinaryTreeWithParent.bin(leafA, "B", nodeD);
		BinaryTreeWithParent<String> nodeG= BinaryTreeWithParent.bin(BinaryTreeWithParent.nil(), "G", nodeI);
		BinaryTreeWithParent<String> nodeF= BinaryTreeWithParent.bin(nodeB, "F", nodeG);
		
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
		BinaryTreeWithParent<String> nil= BinaryTreeWithParent.nil();
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
		BinaryTreeWithParent<String> leafA= BinaryTreeWithParent.leaf("A");
		BinaryTreeWithParent<String> leafC= BinaryTreeWithParent.leaf("C");
		BinaryTreeWithParent<String> leafE= BinaryTreeWithParent.leaf("E");
		BinaryTreeWithParent<String> leafH= BinaryTreeWithParent.leaf("H");
		BinaryTreeWithParent<String> nodeD= BinaryTreeWithParent.bin(leafC, "D", leafE);
		BinaryTreeWithParent<String> nodeI= BinaryTreeWithParent.bin(leafH, "I", BinaryTreeWithParent.nil());
		BinaryTreeWithParent<String> nodeB= BinaryTreeWithParent.bin(leafA, "B", nodeD);
		BinaryTreeWithParent<String> nodeG= BinaryTreeWithParent.bin(BinaryTreeWithParent.nil(), "G", nodeI);
		BinaryTreeWithParent<String> nodeF= BinaryTreeWithParent.bin(nodeB, "F", nodeG);
		
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
		BinaryTreeWithParent<String> nil= BinaryTreeWithParent.nil();
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
		BinaryTreeWithParent<String> leafA= BinaryTreeWithParent.leaf("A");
		BinaryTreeWithParent<String> leafC= BinaryTreeWithParent.leaf("C");
		BinaryTreeWithParent<String> leafE= BinaryTreeWithParent.leaf("E");
		BinaryTreeWithParent<String> leafH= BinaryTreeWithParent.leaf("H");
		BinaryTreeWithParent<String> nodeD= BinaryTreeWithParent.bin(leafC, "D", leafE);
		BinaryTreeWithParent<String> nodeI= BinaryTreeWithParent.bin(leafH, "I", BinaryTreeWithParent.nil());
		BinaryTreeWithParent<String> nodeB= BinaryTreeWithParent.bin(leafA, "B", nodeD);
		BinaryTreeWithParent<String> nodeG= BinaryTreeWithParent.bin(BinaryTreeWithParent.nil(), "G", nodeI);
		BinaryTreeWithParent<String> nodeF= BinaryTreeWithParent.bin(nodeB, "F", nodeG);
		
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
		BinaryTreeWithParent<String> nil= BinaryTreeWithParent.nil();
		List<String> actual= new LinkedList<String>();
		Iterator<String> i= nil.bfsIterator();
		while(i.hasNext()){ 
			actual.add(i.next());
		}
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void setRoot(){
		BinaryTreeWithParent<Integer> tree= BinaryTreeWithParent.bin(BinaryTreeWithParent.nil(), 5, BinaryTreeWithParent.nil());
		tree.setRoot(6);
		Assert.assertEquals(new Integer(6), tree.root());
	}
	@Test
	public void setRight(){
		BinaryTreeWithParent<Integer> right1= BinaryTreeWithParent.leaf(3);
		BinaryTreeWithParent<Integer> right2= BinaryTreeWithParent.leaf(2);
		BinaryTreeWithParent<Integer> left= BinaryTreeWithParent.leaf(4);
		BinaryTreeWithParent<Integer> tree= BinaryTreeWithParent.bin(left, 1, right1);
		Assert.assertNull(right2.parent());
		Assert.assertEquals(right1.parent(),tree);
		tree.setRight(right2);
		Assert.assertEquals(right2.parent(),tree);
		Assert.assertEquals(tree.right(), right2);
	}
	@Test
	public void setLeft(){
		BinaryTreeWithParent<Integer> left1= BinaryTreeWithParent.leaf(3);
		BinaryTreeWithParent<Integer> left2= BinaryTreeWithParent.leaf(2);
		BinaryTreeWithParent<Integer> right= BinaryTreeWithParent.leaf(4);
		BinaryTreeWithParent<Integer> tree= BinaryTreeWithParent.bin(left1, 1, right);
		Assert.assertNull(left2.parent());
		Assert.assertEquals(left1.parent(),tree);
		tree.setLeft(left2);
		Assert.assertEquals(left2.parent(),tree);
		Assert.assertEquals(tree.left(), left2);
	}
}
