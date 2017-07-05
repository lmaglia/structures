package ar.com.magliarelli.trees.binary;

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
}
