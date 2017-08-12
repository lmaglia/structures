/**
 * 
 */
package ar.com.magliarelli.trees.binary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author lucas
 *
 */
public class BinaryTreeWithParent<T> implements Iterable<T> {
	private T root;
	private BinaryTreeWithParent<T> left;
	private BinaryTreeWithParent<T> right;
	private BinaryTreeWithParent<T> parent;
	
	public static <T> BinaryTreeWithParent<T> bin(BinaryTreeWithParent<T> left, T root, BinaryTreeWithParent<T> right) {
		return new BinaryTreeWithParent<T>(left, root, right);
	}

	public static <T> BinaryTreeWithParent<T> nil() {
		return new Nil<T>();
	}

	public static <T> BinaryTreeWithParent<T> leaf(T root) {
		return BinaryTreeWithParent.bin(nil(), root, nil());
	}

	private BinaryTreeWithParent(BinaryTreeWithParent<T> left, T root, BinaryTreeWithParent<T> right) {
		this.root = root;
		this.left = left;
		this.left.parent= this;
		this.right = right;
		this.right.parent= this;
	}

	private BinaryTreeWithParent() {

	}
	
	public void setLeft(BinaryTreeWithParent<T> theLeft){
		this.left= theLeft;
		this.left.parent= this;
	}
	public BinaryTreeWithParent<T> left(){
		return left;
	}
	public void setRight(BinaryTreeWithParent<T> theRight){
		this.right= theRight;
		this.right.parent= this;
	}	
	public BinaryTreeWithParent<T> right(){
		return right;
	}
	public void setRoot(T theRoot){
		this.root= theRoot;
	}
	public T root(){
		return root;
	}
	public void setParent(BinaryTreeWithParent<T> theParent){
		this.parent= theParent;
	}
	public BinaryTreeWithParent<T> parent(){
		return this.parent;
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
	public boolean isLeaf() {
		return this.left.isNil() && this.right.isNil();
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
		private Queue<BinaryTreeWithParent<T>> queue;
		public BFSIterator(BinaryTreeWithParent<T> tree) {
			this.queue= new LinkedList<BinaryTreeWithParent<T>>();
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
			BinaryTreeWithParent<T> next= this.queue.poll();
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
		private Stack<BinaryTreeWithParent<T>> stack;
		public PreOrderIterator(BinaryTreeWithParent<T> tree) {
			this.stack= new Stack<BinaryTreeWithParent<T>>();
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
			BinaryTreeWithParent<T> current= this.stack.pop();
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
		private Stack<BinaryTreeWithParent<T>> stack;
		public InOrderIterator(BinaryTreeWithParent<T> tree) {
			this.stack= new Stack<BinaryTreeWithParent<T>>();
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
			BinaryTreeWithParent<T> theNext= null;
			while(!this.stack.peek().isLeaf()){
				theNext= this.stack.pop();
				if(!theNext.right().isNil()){
					this.stack.push(theNext.right());
				}
				this.stack.push(BinaryTreeWithParent.leaf(theNext.root()));
				if(!theNext.left().isNil()){
					this.stack.push(theNext.left());
				}
			}
			theNext= this.stack.pop();
			return theNext.root();
		}		
	}
	private class PostOrderIterator implements Iterator<T>{
		private Stack<BinaryTreeWithParent<T>> stack;
		public PostOrderIterator(BinaryTreeWithParent<T> tree) {
			this.stack= new Stack<BinaryTreeWithParent<T>>();
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
			BinaryTreeWithParent<T> theNext= null;
			while(!this.stack.peek().isLeaf()){
				theNext= this.stack.pop();
				this.stack.push(BinaryTreeWithParent.leaf(theNext.root()));
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
	private static class Nil<T> extends BinaryTreeWithParent<T> {

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

}
