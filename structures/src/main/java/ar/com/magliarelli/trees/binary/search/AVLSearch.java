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
	private BinaryTreeWithParent<BalancedNode> tree;
	private int size;

	public AVLSearch() {
		this.tree = BinaryTreeWithParent.nil();
		this.size = 0;
	}

	@Override
	public Iterator<T> iterator() {
		return this.new IteratorWrapper(this.tree.iterator());
	}

	@Override
	public boolean insert(T elem) {
		BalancedNode node = new BalancedNode();
		node.value = elem;
		boolean inserted = false;

		BinaryTreeWithParent<BalancedNode> current = this.find(this.tree, elem);		
		if (current.isNil()) {
			node.balance = 0;
			inserted = true;
			this.size++;
			BinaryTreeWithParent<BalancedNode> newTree = BinaryTreeWithParent.leaf(node);
			if (this.tree.isNil()) {
				this.tree = newTree;
			} else {
				BinaryTreeWithParent<BalancedNode> parent = current.parent();
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

	private void balance(BinaryTreeWithParent<BalancedNode>	newTree) {
		BinaryTreeWithParent<BalancedNode> current = newTree;
		while (current != null && current.parent() != null) {
			if (current.parent().right() == current) {
				current = this.balanceRight(current);
			} else {
				current = this.balanceLeft(current);
			}
		}

	}

	private BinaryTreeWithParent<BalancedNode> balanceLeft(BinaryTreeWithParent<AVLSearch<T>.BalancedNode> z) {
		BinaryTreeWithParent<BalancedNode> next = null;
		BinaryTreeWithParent<BalancedNode> x = z.parent();
		if (x.root().balance < 0) {
			next = x.parent();
			BinaryTreeWithParent<BalancedNode> n = null;
			if (z.root().balance > 0) {
				n = this.rotateLR(x, z);
			} else {
				n = this.rotateR(x, z);
			}
			if (next != null) {
				next.setLeft(n);
			} else {
				this.tree = n;
			}
		} else {
			x.root().balance--;
			if (x.root().balance < 0) {
				next = x;
			}
		}
		return next;

	}

	private BinaryTreeWithParent<BalancedNode> balanceRight(BinaryTreeWithParent<AVLSearch<T>.BalancedNode> z) {
		BinaryTreeWithParent<BalancedNode> next = null;
		BinaryTreeWithParent<BalancedNode> x = z.parent();
		if (x.root().balance > 0) {
			next = x.parent();
			BinaryTreeWithParent<BalancedNode> n = null;
			if (z.root().balance < 0) {
				n = this.rotateRL(x, z);
			} else {
				n = this.rotateL(x, z);
			}
			if (next != null) {
				next.setRight(n);
			} else {
				this.tree = n;
			}
		} else {
			x.root().balance++;
			if (x.root().balance > 0) {
				next = x;
			}
		}
		return next;
	}

	private BinaryTreeWithParent<BalancedNode> rotateRL(BinaryTreeWithParent<BalancedNode> x,
			BinaryTreeWithParent<BalancedNode> z) {
		BinaryTreeWithParent<BalancedNode> xParent = x.parent();
		BinaryTreeWithParent<BalancedNode> lz = z.left();
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

	private BinaryTreeWithParent<BalancedNode> rotateL(BinaryTreeWithParent<BalancedNode> x,
			BinaryTreeWithParent<BalancedNode> z) {
		BinaryTreeWithParent<BalancedNode> xParent = x.parent();
		BinaryTreeWithParent<BalancedNode> lz = z.left();
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

	private BinaryTreeWithParent<BalancedNode> rotateLR(BinaryTreeWithParent<BalancedNode> x,
			BinaryTreeWithParent<BalancedNode> z) {
		BinaryTreeWithParent<BalancedNode> xParent = x.parent();
		BinaryTreeWithParent<BalancedNode> rz = z.right();
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

	private BinaryTreeWithParent<BalancedNode> rotateR(BinaryTreeWithParent<BalancedNode> x,
			BinaryTreeWithParent<BalancedNode> z) {
		BinaryTreeWithParent<BalancedNode> xParent = x.parent();
		BinaryTreeWithParent<BalancedNode> rz = z.right();
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
		BinaryTreeWithParent<BalancedNode> current = this.find(this.tree,elem);
		T elemFound= null;
		if(!current.isNil()){
			elemFound= current.root().value;
		}
		return elemFound;
	}

	@Override
	public T maximum() {
		return max(this.tree);
	}

	private T max(BinaryTreeWithParent<BalancedNode> current) {
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

	private T min(BinaryTreeWithParent<BalancedNode> current) {
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
		BinaryTreeWithParent<BalancedNode> current = this.find(this.tree,elem);
		
		if (!current.isNil()) {
			removed = current.root().value;
			this.size--;
			if (current.isLeaf()) {
				if (current == this.tree) {
					this.tree = BinaryTreeWithParent.nil();
				} else {
					BinaryTreeWithParent<BalancedNode> parent = current.parent();
					if (parent.left() == current) {
						parent.setLeft(BinaryTreeWithParent.nil());
						this.balance(parent.right()); // We can think this as we
														// added a node to right
														// side instead of
														// removing from left.
														// Thus, we can reuse
					} else {
						parent.setRight(BinaryTreeWithParent.nil());
						this.balance(parent.left());
					}
				}

			} else if (current.right().isNil()) {
				if (current == this.tree) {
					this.tree = current.left();
					this.tree.setParent(null);
				} else {
					BinaryTreeWithParent<BalancedNode> parent = current.parent();
					if (parent.left() == current) {
						parent.setLeft(current.left());
						this.balance(parent.right());
					} else {
						parent.setRight(current.left());
						this.balance(parent.left());
					}
				}
			} else if (current.left().isNil()) {
				if (current == this.tree) {
					this.tree = current.right();
					this.tree.setParent(null);
				} else {
					BinaryTreeWithParent<BalancedNode> parent = current.parent();
					if (parent.left() == current) {
						parent.setLeft(current.right());
						this.balance(parent.right());
					} else {
						parent.setRight(current.right());
						this.balance(parent.left());
					}
				}
			} else {
				BinaryTreeWithParent<BalancedNode> min = current.right();
				while (!min.left().isNil()) {
					min = min.left();
				}
				current.root().value = min.root().value;
				BinaryTreeWithParent<BalancedNode> minParent = min.parent();
				if (minParent.right() == min) {
					minParent.setRight(min.right());
					this.balance(minParent.left());
				} else {
					minParent.setLeft(min.right());
					this.balance(minParent.right());
				}
			}
		}
		return removed;
	}

	@Override
	public boolean contains(T elem) {		
		BinaryTreeWithParent<BalancedNode> current = this.find(this.tree,elem);		
		return !current.isNil();
	}

	@Override
	public T successor(T elem) {
		T suc = null;
		BinaryTreeWithParent<BalancedNode> current = this.find(this.tree,elem);		
		if (!current.isNil()) {
			suc = min(current.right());
			if (suc == null) {
				BinaryTreeWithParent<BalancedNode> parent = current.parent();
				if (parent != null) {
					if (parent.left() == current) {
						suc = parent.root().value;
					} else {
						if (parent.parent() != null && parent.parent().left() == parent) {
							suc = parent.parent().root().value;
						}
					}
				}
			}
		}
		return suc;
	}

	@Override
	public T predecessor(T elem) {
		T pred = null;
		BinaryTreeWithParent<BalancedNode> current = this.find(this.tree,elem);		
		if (!current.isNil()) {
			pred = max(current.left());
			if (pred == null) {
				BinaryTreeWithParent<BalancedNode> parent = current.parent();
				if (parent != null) {
					if (parent.right() == current) {
						pred = parent.root().value;
					} else {
						if (parent.parent() != null && parent.parent().right() == parent) {
							pred = parent.parent().root().value;
						}
					}
				}
			}
		}
		return pred;
	}

	@Override
	public int size() {
		return this.size;
	}

	private BinaryTreeWithParent<BalancedNode> find(BinaryTreeWithParent<BalancedNode> t, T elem) {
		BinaryTreeWithParent<BalancedNode> current = t;
		while (!current.isNil() && current.root().compareTo(elem) != 0) {
			if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		return current;
	}

	private class BalancedNode implements Comparable<T> {
		public T value;
		public int balance;

		@Override
		public int compareTo(T arg0) {
			return this.value.compareTo(arg0);
		}

	}

	private class IteratorWrapper implements Iterator<T> {
		private Iterator<BalancedNode> it;

		public IteratorWrapper(Iterator<BalancedNode> it) {
			this.it = it;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public T next() {
			BalancedNode theNext = this.it.next();
			return theNext.value;
		}

	}

}
