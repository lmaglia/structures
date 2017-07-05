package ar.com.magliarelli.trees.binary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Lucas Magliarelli
 *
 */
public class BinaryTree<T> implements Iterable<T>{
	private T root;
	private BinaryTree<T> left;
	private BinaryTree<T> right;

	public static <T> BinaryTree<T> bin(BinaryTree<T> left, T root, BinaryTree<T> right) {
		return new BinaryTree<T>(left, root, right);
	}

	public static <T> BinaryTree<T> nil() {
		return new Nil<T>();
	}

	public static <T> BinaryTree<T> leaf(T root) {
		return BinaryTree.bin(nil(), root, nil());
	}

	private BinaryTree(BinaryTree<T> left, T root, BinaryTree<T> right) {
		this.root = root;
		this.left = left;
		this.right = right;
	}

	private BinaryTree() {

	}

	public T root() {
		return root;
	}

	public boolean isLeaf() {
		return this.left.isNil() && this.right.isNil();
	}

	public BinaryTree<T> left() {
		return left;
	}

	public BinaryTree<T> right() {
		return right;
	}

	public int height() {
		return 1 + Math.max(this.left.height(), this.right.height());
	}

	public int size() {
		return 1 + this.left.size() + this.right.size();
	}

	public boolean isNil() {
		return false;
	}

	private static class Nil<T> extends BinaryTree<T> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see ar.com.magliarelli.trees.binary.BinaryTree#height()
		 */
		@Override
		public int height() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see ar.com.magliarelli.trees.binary.BinaryTree#size()
		 */
		@Override
		public int size() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see ar.com.magliarelli.trees.binary.BinaryTree#isNill()
		 */
		@Override
		public boolean isNil() {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see ar.com.magliarelli.trees.binary.BinaryTree#isLeaf()
		 */
		@Override
		public boolean isLeaf() {
			return false;
		}

	}
	public Iterator<T> preOrderIterator(){
		return this.new PreOrderIterator(this);
	}
	public Iterator<T> inOrderIterator(){
		return this.new InOrderIterator(this);
	}
	public Iterator<T> postOrderIterator(){
		return this.new PostOrderIterator(this);
	}
	@Override
	public Iterator<T> iterator() {		
		return this.inOrderIterator();
	}
	public Iterator<T> bfsIterator(){
		return this.new BFSIterator(this);
	}
	private class BFSIterator implements Iterator<T>{
		private Queue<BinaryTree<T>> queue;
		public BFSIterator(BinaryTree<T> tree) {
			this.queue= new LinkedList<BinaryTree<T>>();
			if(!tree.isNil()){
				this.queue.offer(tree);
			}
		}
		@Override
		public boolean hasNext() {			
			return !this.queue.isEmpty();
		}

		@Override
		public T next() {
			BinaryTree<T> next= this.queue.poll();
			if(!next.left().isNil()){
				this.queue.offer(next.left());
			}
			if(!next.right().isNil()){
				this.queue.offer(next.right());
			}
			return next.root();
		}
		
	}
	private class PreOrderIterator implements Iterator<T>{
		private Stack<BinaryTree<T>> stack;
		public PreOrderIterator(BinaryTree<T> tree) {
			this.stack= new Stack<BinaryTree<T>>();
			if(!tree.isNil()){
				this.stack.push(tree);
			}
		}
		@Override
		public boolean hasNext() {			
			return !this.stack.isEmpty();
		}

		@Override
		public T next() {
			BinaryTree<T> current= this.stack.pop();
			if(!current.right().isNil()){
				this.stack.push(current.right());
			}
			if(!current.left().isNil()){
				this.stack.push(current.left());
			}
			return current.root();
		}		
	}
	private class InOrderIterator implements Iterator<T>{
		private Stack<BinaryTree<T>> stack;
		public InOrderIterator(BinaryTree<T> tree) {
			this.stack= new Stack<BinaryTree<T>>();
			if(!tree.isNil()){
				this.stack.push(tree);
			}
		}
		@Override
		public boolean hasNext() {			
			return !this.stack.isEmpty();
		}

		@Override
		public T next() {
			BinaryTree<T> theNext= null;
			while(!this.stack.peek().isLeaf()){
				theNext= this.stack.pop();
				if(!theNext.right().isNil()){
					this.stack.push(theNext.right());
				}
				this.stack.push(BinaryTree.leaf(theNext.root()));
				if(!theNext.left().isNil()){
					this.stack.push(theNext.left());
				}
			}
			theNext= this.stack.pop();
			return theNext.root();
		}		
	}
	private class PostOrderIterator implements Iterator<T>{
		private Stack<BinaryTree<T>> stack;
		public PostOrderIterator(BinaryTree<T> tree) {
			this.stack= new Stack<BinaryTree<T>>();
			if(!tree.isNil()){
				this.stack.push(tree);
			}
		}
		@Override
		public boolean hasNext() {			
			return !this.stack.isEmpty();
		}

		@Override
		public T next() {
			BinaryTree<T> theNext= null;
			while(!this.stack.peek().isLeaf()){
				theNext= this.stack.pop();
				this.stack.push(BinaryTree.leaf(theNext.root()));
				if(!theNext.right().isNil()){
					this.stack.push(theNext.right());
				}
				if(!theNext.left().isNil()){
					this.stack.push(theNext.left());
				}
			}
			theNext= this.stack.pop();
			return theNext.root();
		}
		
	}
	
}
