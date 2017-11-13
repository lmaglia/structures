/**
 * 
 */
package ar.com.magliarelli.trees.binary.search;

import java.util.Iterator;

import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import ar.com.magliarelli.trees.binary.BinaryTreeWithParent;

/**
 * @author lucas
 *
 */
public class RedBlackTreeSearch<T extends Comparable<T>> implements Search<T> {
	private int size;
	private BinaryTreeWithParent<RBNode<T>> tree;

	public RedBlackTreeSearch() {
		this.size = 0;
		this.tree = BinaryTreeWithParent.nil();
	}

	@Override
	public Iterator<T> iterator() {
		return this.new IteratorWrapper(this.tree.iterator());
	}

	@Override
	public boolean insert(T elem) {
		boolean inserted = false;
		BinaryTreeWithParent<RBNode<T>> node = this.find(this.tree, elem);
		if (node.isNil()) {
			inserted = true;
			this.size++;
			RBNode<T> rbNode = new RBNode<T>();
			rbNode.value = elem;
			rbNode.color = Color.RED;
			BinaryTreeWithParent<RBNode<T>> leaf = BinaryTreeWithParent.leaf(rbNode);
			BinaryTreeWithParent<RBNode<T>> parent = node.parent();
			if (parent == null) {
				// This is the first node
				this.tree = leaf;
				this.tree.root().color = Color.BLACK;
			} else {
				if (parent.left() == node) {
					parent.setLeft(leaf);
				} else {
					parent.setRight(leaf);
				}
				// Now, we have to make the tree suffix the Red-Black invariant
				this.accomodate(leaf);
			}
		} else {
			// We only replace the current value
			node.root().value = elem;
		}
		return inserted;
	}

	@Override
	public T search(T elem) {
		BinaryTreeWithParent<RBNode<T>> t = this.find(tree, elem);
		T found = null;
		if (!t.isNil()) {
			found = t.root().value;
		}
		return found;
	}

	@Override
	public T maximum() {
		BinaryTreeWithParent<RBNode<T>> max = this.max(tree);
		T theMax = null;
		if (!max.isNil()) {
			theMax = max.root().value;
		}
		return theMax;
	}

	@Override
	public T minimum() {
		BinaryTreeWithParent<RBNode<T>> min = this.min(tree);
		T theMin = null;
		if (!min.isNil()) {
			theMin = min.root().value;
		}
		return theMin;
	}

	@Override
	public T delete(T elem) {
		BinaryTreeWithParent<RBNode<T>> toRemove= this.find(this.tree, elem);
		T removed= null;
		if(toRemove!= null && !toRemove.isNil()){
			this.size--;
			removed= toRemove.root().value;
		}
		return removed;
	}

	@Override
	public boolean contains(T elem) {
		BinaryTreeWithParent<RBNode<T>> t = this.find(tree, elem);
		return !t.isNil();
	}

	@Override
	public T successor(T elem) {
		BinaryTreeWithParent<RBNode<T>> sucTree = this.successor(tree, elem);
		T suc = null;
		if (!sucTree.isNil()) {
			suc = sucTree.root().value;
		}
		return suc;
	}

	@Override
	public T predecessor(T elem) {
		BinaryTreeWithParent<RBNode<T>> predTree = this.predecessor(tree, elem);
		T pred = null;
		if (!predTree.isNil()) {
			pred = predTree.root().value;
		}
		return pred;
	}

	@Override
	public int size() {
		return this.size;
	}

	private BinaryTreeWithParent<RBNode<T>> find(BinaryTreeWithParent<RBNode<T>> t, T elem) {
		BinaryTreeWithParent<RBNode<T>> current = t;
		while (!current.isNil() && current.root().value.compareTo(elem) != 0) {
			if (current.root().value.compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		return current;
	}

	private BinaryTreeWithParent<RBNode<T>> max(BinaryTreeWithParent<RBNode<T>> t) {
		BinaryTreeWithParent<RBNode<T>> max = BinaryTreeWithParent.nil();
		BinaryTreeWithParent<RBNode<T>> current = t;
		while (!current.isNil()) {
			max = current;
			current = current.right();
		}
		return max;
	}

	private BinaryTreeWithParent<RBNode<T>> min(BinaryTreeWithParent<RBNode<T>> t) {
		BinaryTreeWithParent<RBNode<T>> min = BinaryTreeWithParent.nil();
		BinaryTreeWithParent<RBNode<T>> current = t;
		while (!current.isNil()) {
			min = current;
			current = current.left();
		}
		return min;
	}

	private BinaryTreeWithParent<RBNode<T>> successor(BinaryTreeWithParent<RBNode<T>> t, T elem) {
		BinaryTreeWithParent<RBNode<T>> base = this.find(t, elem);
		BinaryTreeWithParent<RBNode<T>> suc = BinaryTreeWithParent.nil();
		if (!base.isNil()) {
			suc = min(base.right());
			if (suc.isNil()) {
				BinaryTreeWithParent<RBNode<T>> parent = suc.parent();
				while (parent != null && parent.left() != suc) {
					suc = parent;
					parent = parent.parent();
				}
				if (parent != null && !parent.isNil()) {
					suc = parent;
				} else {
					suc = BinaryTreeWithParent.nil();
				}
			}
		}
		return suc;
	}

	private BinaryTreeWithParent<RBNode<T>> predecessor(BinaryTreeWithParent<RBNode<T>> t, T elem) {
		BinaryTreeWithParent<RBNode<T>> base = this.find(t, elem);
		BinaryTreeWithParent<RBNode<T>> pred = BinaryTreeWithParent.nil();
		if (!base.isNil()) {
			pred = max(base.left());
			if (pred.isNil()) {
				BinaryTreeWithParent<RBNode<T>> parent = pred.parent();
				while (parent != null && parent.right() != pred) {
					pred = parent;
					parent = parent.parent();
				}
				if (parent != null && !parent.isNil()) {
					pred = parent;
				} else {
					pred = BinaryTreeWithParent.nil();
				}
			}
		}
		return pred;
	}

	private BinaryTreeWithParent<RBNode<T>> grandParent(BinaryTreeWithParent<RBNode<T>> t) {
		BinaryTreeWithParent<RBNode<T>> gp = t.parent();
		if (gp != null) {
			gp = gp.parent();
		}
		return gp;
	}

	private BinaryTreeWithParent<RBNode<T>> sibiling(BinaryTreeWithParent<RBNode<T>> t) {
		BinaryTreeWithParent<RBNode<T>> s = t.parent();
		if (s != null) {
			if (s.left() == t) {
				s = s.right();
			} else {
				s = s.left();
			}
		}
		return s;
	}

	private BinaryTreeWithParent<RBNode<T>> uncle(BinaryTreeWithParent<RBNode<T>> t) {
		BinaryTreeWithParent<RBNode<T>> u = t.parent();
		if (u != null) {
			u = sibiling(u);
		}
		return u;
	}

	private void accomodate(BinaryTreeWithParent<RBNode<T>> leaf) {
		if (leaf.parent() == null) {
			leaf.root().color = Color.BLACK;
		} else if (leaf.parent().root().color.equals(Color.RED)) {
			BinaryTreeWithParent<RBNode<T>> u = this.uncle(leaf);
			if (!u.isNil() && u.root().color.equals(Color.RED)) {
				BinaryTreeWithParent<RBNode<T>> gp= this.grandParent(leaf);
				u.root().color= Color.BLACK;
				leaf.parent().root().color= Color.BLACK;
				gp.root().color= Color.RED;
				this.accomodate(gp);
			} else {
				BinaryTreeWithParent<RBNode<T>> parent= leaf.parent();
				BinaryTreeWithParent<RBNode<T>> grandParent= this.grandParent(leaf);
				if(grandParent.left().right() == leaf){
					this.rotateLeft(parent);
					leaf= leaf.left();
				}else if(grandParent.right().left() == leaf){
					this.rotateRight(parent);
					leaf= leaf.right();
				}
				parent= leaf.parent();
				grandParent= this.grandParent(leaf);
				if(parent.left() == leaf){
					leaf= this.rotateRight(grandParent);					
				}else{
					leaf=this.rotateLeft(grandParent);
					
				}
				parent.root().color= Color.BLACK;
				grandParent.root().color= Color.RED;
				while(leaf.parent()!=null){
					leaf= leaf.parent();
				}
				this.tree= leaf;
			}
		}
	}
	private BinaryTreeWithParent<RBNode<T>> rotateLeft(BinaryTreeWithParent<RBNode<T>> t){
		BinaryTreeWithParent<RBNode<T>> tmp= t.right();
		BinaryTreeWithParent<RBNode<T>> p= t;
		if(!tmp.isNil()){
			BinaryTreeWithParent<RBNode<T>> parent= t.parent();
			if(parent.left() == t){
				parent.setLeft(tmp);
			}else{
				parent.setRight(tmp);
			}
			t.setRight(tmp.left());			
			tmp.setLeft(t);			
			p= tmp;
		}
		return p;
	}
	private BinaryTreeWithParent<RBNode<T>> rotateRight(BinaryTreeWithParent<RBNode<T>> t){
		BinaryTreeWithParent<RBNode<T>> tmp= t.left();
		BinaryTreeWithParent<RBNode<T>> p= t;
		if(!tmp.isNil()){
			BinaryTreeWithParent<RBNode<T>> parent= t.parent();
			if(parent.left() == t){
				parent.setLeft(tmp);
			}else{
				parent.setRight(tmp);
			}
			t.setLeft(tmp.right());			
			tmp.setRight(t);			
			p= tmp;
		}
		return p;
	}

	private enum Color {
		RED, BLACK
	};

	private static class RBNode<T> {
		public T value;
		public Color color;
	}

	private class IteratorWrapper implements Iterator<T> {
		private Iterator<RBNode<T>> it;

		public IteratorWrapper(Iterator<RBNode<T>> it) {
			this.it = it;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public T next() {
			RBNode<T> elem = it.next();
			return elem.value;
		}

	}
}
