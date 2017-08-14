/**
 * 
 */
package ar.com.magliarelli.trees.binary.search;

import java.util.Iterator;

import ar.com.magliarelli.trees.binary.BinaryTreeWithParent;

/**
 * @author lucas
 *
 */
public class AVLSearch<T extends Comparable<T>> implements Search<T> {
	private BinaryTreeWithParent<BalancedNode<T>> tree;
	private int size;

	public AVLSearch() {
		this.tree = BinaryTreeWithParent.nil();
		this.size = 0;
	}

	@Override
	public Iterator<T> iterator() {
		return this.new IteratorWrapper<T>(this.tree.iterator());
	}

	@Override
	public boolean insert(T elem) {
		BalancedNode<T> node= new BalancedNode<T>();
		node.value= elem;
		boolean  inserted= false;
		
		BinaryTreeWithParent<BalancedNode<T>> current= this.tree;
		while(!current.isNil() && current.root().compareTo(elem) != 0){
			if(current.root().compareTo(elem) > 0){
				current= current.left();
			}else{
				current= current.right();
			}
		}
		if(current.isNil()){
			node.balance= 0;
			inserted= true;
			this.size++;
			BinaryTreeWithParent<BalancedNode<T>> newTree= BinaryTreeWithParent.leaf(node);
			if(this.tree.isNil()){
				this.tree= newTree;
			}else{
				BinaryTreeWithParent<BalancedNode<T>> parent= current.parent();
				if(parent.left() == current){
					parent.setLeft(newTree);
				}else{
					parent.setRight(newTree);
				}
				//TODO we have to rebalance the tree
			}
		}else{
			//current has the same key. We just update the value
			current.root().value= elem;
		}
		return inserted;
	}

	@Override
	public T search(T elem) {
		BinaryTreeWithParent<BalancedNode<T>> current = this.tree;
		BalancedNode<T> found = null;
		while (!current.isNil() && found == null) {
			if (current.root().compareTo(elem) == 0) {
				found = current.root();
			} else if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		T elemFound = null;
		if (found != null) {
			elemFound = found.value;
		}
		return elemFound;
	}

	@Override
	public T maximum() {
		return max(this.tree);
	}

	private T max(BinaryTreeWithParent<BalancedNode<T>> current) {
		T max = null;
		if (!current.isNil()) {			
			while (!current.right().isNil()) {
				current = current.right();
			}
			max = current.root().value;
		}
		return max;
	}

	@Override
	public T minimum() {
		return min(this.tree);
	}

	private T min(BinaryTreeWithParent<BalancedNode<T>> current) {
		T min = null;
		if (!current.isNil()) {			
			while (!current.left().isNil()) {
				current = current.left();
			}
			min = current.root().value;
		}
		return min;
	}

	@Override
	public T delete(T elem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(T elem) {
		boolean found = false;
		BinaryTreeWithParent<BalancedNode<T>> current = this.tree;
		while (!current.isNil() && !found) {
			if (current.root().compareTo(elem) == 0) {
				found = true;
			} else if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		return found;
	}

	@Override
	public T successor(T elem) {
		T suc = null;
		BinaryTreeWithParent<BalancedNode<T>> current = this.tree;
		while (!current.isNil() && current.root().compareTo(elem) != 0) {
			if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		if(!current.isNil()){
			suc= min(current.right());
			if(suc == null){
				BinaryTreeWithParent<BalancedNode<T>> parent= current.parent();
				if(parent!=null && parent.left() == current){
					suc= parent.root().value;
				}
			}
		}
		return suc;
	}

	@Override
	public T predecessor(T elem) {
		T pred = null;
		BinaryTreeWithParent<BalancedNode<T>> current = this.tree;
		while (!current.isNil() && current.root().compareTo(elem) != 0) {
			if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		if(!current.isNil()){
			pred= max(current.left());
			if(pred == null){
				BinaryTreeWithParent<BalancedNode<T>> parent= current.parent();
				if(parent!=null && parent.right() == current){
					pred= parent.root().value;
				}
			}
		}
		return pred;
	}

	@Override
	public int size() {
		return this.size;
	}

	private class BalancedNode<T extends Comparable<T>> implements Comparable<T> {
		public T value;
		public int balance;

		@Override
		public int compareTo(T arg0) {
			return this.value.compareTo(arg0);
		}

	}

	private class IteratorWrapper<T extends Comparable<T>> implements Iterator<T> {
		private Iterator<BalancedNode<T>> it;

		public IteratorWrapper(Iterator<BalancedNode<T>> it) {
			this.it = it;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public T next() {
			BalancedNode<T> theNext = this.it.next();
			return theNext.value;
		}

	}

}
