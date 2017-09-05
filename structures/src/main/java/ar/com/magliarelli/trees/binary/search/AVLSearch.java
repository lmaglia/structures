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
	// BalanceFactor = tree.right().height()-tree.left.height() in [-1,0,1]
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
		BalancedNode<T> node = new BalancedNode<T>();
		node.value = elem;
		boolean inserted = false;

		BinaryTreeWithParent<BalancedNode<T>> current = this.tree;
		while (!current.isNil() && current.root().compareTo(elem) != 0) {
			if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		if (current.isNil()) {
			node.balance = 0;
			inserted = true;
			this.size++;
			BinaryTreeWithParent<BalancedNode<T>> newTree = BinaryTreeWithParent.leaf(node);
			if (this.tree.isNil()) {
				this.tree = newTree;
			} else {
				BinaryTreeWithParent<BalancedNode<T>> parent = current.parent();
				if (parent.left() == current) {
					parent.setLeft(newTree);
				} else {
					parent.setRight(newTree);
				}

				// TODO we might have to update this.tree
				this.balance(newTree);
			}
		} else {
			// current has the same key. We just update the value
			current.root().value = elem;
		}
		return inserted;
	}

	private void balance(BinaryTreeWithParent<BalancedNode<T>> newTree) {
		BinaryTreeWithParent<BalancedNode<T>> current = newTree;
		while (current != null && current.parent() != null) {
			boolean updateRoot= (current.parent() == this.tree);
			if (current.parent().right() == current) {
				current = this.balanceRight(current);
			} else {
				current = this.balanceLeft(current);
			}
			if(updateRoot){
				this.tree= current;
			}
		}

	}

	private BinaryTreeWithParent<BalancedNode<T>> balanceLeft(BinaryTreeWithParent<AVLSearch<T>.BalancedNode<T>> z) {
		BinaryTreeWithParent<BalancedNode<T>> next = null;
		BinaryTreeWithParent<BalancedNode<T>> x = z.parent();
		if (x.root().balance < 0) {
			next = x.parent();
			BinaryTreeWithParent<BalancedNode<T>> n = null;
			if (z.root().balance > 0) {
				n = this.rotateLR(x, z);
			} else {
				n = this.rotateR(x, z);
			}
			if (next != null) {
				next.setLeft(n);
			} else {
				//We have to update this.tree
				next = n;
			}
		} else {
			x.root().balance--;
			if (x.root().balance < 0) {
				next = x;
			}
		}
		return next;

	}

	private BinaryTreeWithParent<BalancedNode<T>> balanceRight(BinaryTreeWithParent<AVLSearch<T>.BalancedNode<T>> z) {
		BinaryTreeWithParent<BalancedNode<T>> next = null;
		BinaryTreeWithParent<BalancedNode<T>> x = z.parent();
		if (x.root().balance > 0) {
			next = x.parent();
			BinaryTreeWithParent<BalancedNode<T>> n = null;
			if (z.root().balance < 0) {
				n = this.rotateRL(x, z);
			} else {
				n = this.rotateL(x, z);
			}
			if (next != null) {
				next.setRight(n);
			} else {
				// We have to update this.tree
				next = n;
			}
		} else {
			x.root().balance++;
			if (x.root().balance > 0) {
				next = x;
			}
		}
		return next;
	}

	private BinaryTreeWithParent<BalancedNode<T>> rotateRL(BinaryTreeWithParent<BalancedNode<T>> x,
			BinaryTreeWithParent<BalancedNode<T>> z) {
		BinaryTreeWithParent<BalancedNode<T>> xParent = x.parent();
		BinaryTreeWithParent<BalancedNode<T>> lz = z.left();
		z.setLeft(lz.right());
		lz.setRight(z);
		x.setRight(lz.left());
		lz.setLeft(x);
		lz.setParent(xParent);
		if (lz.root().balance == 0) {
			x.root().balance = 0;
			z.root().balance = 0;
		} else if (lz.root().balance > 0) {
			z.root().balance = 0;
			x.root().balance = -1;
		} else {
			x.root().balance = 0;
			z.root().balance = 1;
		}
		lz.root().balance = 0;
		return lz;
	}

	private BinaryTreeWithParent<BalancedNode<T>> rotateL(BinaryTreeWithParent<BalancedNode<T>> x,
			BinaryTreeWithParent<BalancedNode<T>> z) {
		BinaryTreeWithParent<BalancedNode<T>> xParent = x.parent();
		BinaryTreeWithParent<BalancedNode<T>> lz = z.left();
		z.setLeft(x);
		x.setRight(lz);
		z.setParent(xParent);
		if (z.root().balance == 0) {
			x.root().balance = 1;
			z.root().balance = -1;
		} else {
			x.root().balance = 0;
			z.root().balance = 0;
		}
		return z;
	}

	private BinaryTreeWithParent<BalancedNode<T>> rotateLR(BinaryTreeWithParent<BalancedNode<T>> x,
			BinaryTreeWithParent<BalancedNode<T>> z) {
		BinaryTreeWithParent<BalancedNode<T>> xParent = x.parent();
		BinaryTreeWithParent<BalancedNode<T>> rz = z.right();
		z.setRight(rz.left());
		rz.setLeft(z);
		x.setLeft(rz.right());
		rz.setRight(x);
		rz.setParent(xParent);
		if (rz.root().balance == 0) {
			x.root().balance = 0;
			z.root().balance = 0;
		} else if (rz.root().balance > 0) {
			z.root().balance = -1;
			x.root().balance = 0;
		} else {
			x.root().balance = 1;
			z.root().balance = 0;
		}
		rz.root().balance = 0;
		return rz;
	}

	private BinaryTreeWithParent<BalancedNode<T>> rotateR(BinaryTreeWithParent<BalancedNode<T>> x,
			BinaryTreeWithParent<BalancedNode<T>> z) {
		BinaryTreeWithParent<BalancedNode<T>> xParent = x.parent();
		BinaryTreeWithParent<BalancedNode<T>> rz = z.right();
		z.setRight(x);
		x.setLeft(rz);
		z.setParent(xParent);
		if (z.root().balance == 0) {
			x.root().balance = -1;
			z.root().balance = 1;
		} else {
			x.root().balance = 0;
			z.root().balance = 0;
		}
		return z;
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
		T removed = null;
		BinaryTreeWithParent<BalancedNode<T>> current = this.tree;
		while (!current.isNil() && current.root().compareTo(elem) != 0) {
			if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		if (!current.isNil()) {
			removed = current.root().value;
			this.size--;
			if (current.isLeaf()) {

			}
		}
		return removed;
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
		if (!current.isNil()) {
			suc = min(current.right());
			if (suc == null) {
				BinaryTreeWithParent<BalancedNode<T>> parent = current.parent();
				if (parent != null && parent.left() == current) {
					suc = parent.root().value;
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
		if (!current.isNil()) {
			pred = max(current.left());
			if (pred == null) {
				BinaryTreeWithParent<BalancedNode<T>> parent = current.parent();
				if (parent != null && parent.right() == current) {
					pred = parent.root().value;
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
