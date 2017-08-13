/**
 * 
 */
package ar.com.magliarelli.trees.binary.search;

import java.util.Iterator;

import java.util.Stack;
import ar.com.magliarelli.trees.binary.BinaryTreeWithParent;

/**
 * @author lucas
 *
 */
public class BSTWithParentSearch<T extends Comparable<T>> implements Search<T> {
	private BinaryTreeWithParent<T> tree;
	private int size;

	public BSTWithParentSearch() {
		this.tree = BinaryTreeWithParent.nil();
		this.size = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return this.tree.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.magliarelli.trees.binary.search.Search#insert(java.lang.
	 * Comparable)
	 */
	@Override
	public boolean insert(T elem) {
		boolean inserted = true;
		int increment = 1;
		BinaryTreeWithParent<T> aux = this.tree;
		boolean found = false;
		while (!aux.isNil() && !found) {
			if (aux.root().compareTo(elem) == 0) {
				found = true;
			} else if (aux.root().compareTo(elem) > 0) {
				aux = aux.left();
			} else {
				aux = aux.right();
			}
		}
		if (found) {
			aux.setRoot(elem);
			increment = 0;
			inserted = false;
		} else {
			BinaryTreeWithParent<T> newNode = BinaryTreeWithParent.leaf(elem);
			BinaryTreeWithParent<T> parent = aux.parent();
			if (parent != null) {
				// We compare the reference.
				if (parent.left() == aux) {
					parent.setLeft(newNode);
				} else {
					parent.setRight(newNode);
				}
			} else {
				// This is the first node in the BST
				this.tree = newNode;
			}
		}
		this.size += increment;
		return inserted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.magliarelli.trees.binary.search.Search#search(java.lang.
	 * Comparable)
	 */
	@Override
	public T search(T elem) {
		BinaryTreeWithParent<T> current = this.tree;
		while (!current.isNil() && current.root().compareTo(elem) != 0) {
			if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		T found = null;
		if (!current.isNil()) {
			found = current.root();
		}
		return found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.magliarelli.trees.binary.search.Search#maximum()
	 */
	@Override
	public T maximum() {
		return max(this.tree);
	}

	private T max(BinaryTreeWithParent<T> t) {
		BinaryTreeWithParent<T> max = BinaryTreeWithParent.nil();
		BinaryTreeWithParent<T> current = t;
		while (!current.isNil()) {
			max = current;
			current = current.right();
		}
		T maximum = null;
		if (!max.isNil()) {
			maximum = max.root();
		}
		return maximum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.magliarelli.trees.binary.search.Search#minimum()
	 */
	@Override
	public T minimum() {
		return min(this.tree);
	}

	private T min(BinaryTreeWithParent<T> t) {
		BinaryTreeWithParent<T> min = BinaryTreeWithParent.nil();
		BinaryTreeWithParent<T> current = t;
		while (!current.isNil()) {
			min = current;
			current = current.left();
		}
		T minimum = null;
		if (!min.isNil()) {
			minimum = min.root();
		}
		return minimum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.magliarelli.trees.binary.search.Search#delete(java.lang.
	 * Comparable)
	 */
	@Override
	public T delete(T elem) {
		T removed = null;
		BinaryTreeWithParent<T> current = this.tree;
		boolean found = false;
		while (!current.isNil() && !found) {
			if (current.root().compareTo(elem) == 0) {
				found = true;
			} else if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		if (found) {
			removed = current.root();
			this.size--;
			if (current.isLeaf()) {
				BinaryTreeWithParent<T> parent = current.parent();
				if (parent == null) {
					this.tree = BinaryTreeWithParent.nil();
				} else {
					if (parent.left() == current) {
						parent.setLeft(BinaryTreeWithParent.nil());
					} else {
						parent.setRight(BinaryTreeWithParent.nil());
					}
				}
			} else {
				if (!current.right().isNil()) {
					BinaryTreeWithParent<T> aux = current.right();
					while (!aux.left().isNil()) {
						aux = aux.left();
					}
					current.setRoot(aux.root());
					if (current.right() == aux) {
						current.setRight(aux.right());
					} else {
						if (!aux.right().isNil()) {
							aux.parent().setLeft(aux.right());
						} else {
							aux.parent().setLeft(BinaryTreeWithParent.nil());
						}
					}
				} else {
					BinaryTreeWithParent<T> aux = current.left();
					while (!aux.right().isNil()) {
						aux = aux.right();
					}
					current.setRoot(aux.root());
					if (current.left() == aux) {
						current.setLeft(aux.left());
					} else {
						if (!aux.left().isNil()) {
							aux.parent().setRight(aux.left());
						} else {
							aux.parent().setRight(BinaryTreeWithParent.nil());
						}
					}
				}
			}

		}
		return removed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.magliarelli.trees.binary.search.Search#contains(java.lang.
	 * Comparable)
	 */
	@Override
	public boolean contains(T elem) {
		BinaryTreeWithParent<T> current = this.tree;
		while (!current.isNil() && current.root().compareTo(elem) != 0) {
			if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		return !current.isNil();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.magliarelli.trees.binary.search.Search#successor(java.lang.
	 * Comparable)
	 */
	@Override
	public T successor(T elem) {
		return this.iSuccessor(this.tree, elem).first;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.magliarelli.trees.binary.search.Search#predecessor(java.lang.
	 * Comparable)
	 */
	@Override
	public T predecessor(T elem) {
		return this.iPredecessor(this.tree, elem).first;
	}

	private Pair<T, Boolean> iSuccessor(BinaryTreeWithParent<T> t, T x) {
		Pair<T, Boolean> r = new Pair<T, Boolean>(null, false);
		boolean b = false;
		Stack<BinaryTreeWithParent<T>> p = new Stack<BinaryTreeWithParent<T>>();
		p.push(t);

		while (!p.isEmpty()) {
			BinaryTreeWithParent<T> a = p.pop();
			if (b) {
				if (!a.isNil()) {
					b = true;
					r = f(r, a.root());
				}
			} else {
				if (a.isNil()) {
					b = true;
					r.first = null;
					r.second = false;
				} else {
					if (a.root().compareTo(x) == 0) {
						b = true;
						r.first = min(a.right());
						r.second = true;
					} else {
						if (a.root().compareTo(x) > 0) {
							p.push(a);
							p.push(a.left());
							b = false;
						} else {
							p.push(a.right());
							b = false;
						}
					}
				}

			}
		}

		return r;

	}

	private Pair<T, Boolean> iPredecessor(BinaryTreeWithParent<T> t, T x) {
		Pair<T, Boolean> r = new Pair<T, Boolean>(null, false);
		Stack<BinaryTreeWithParent<T>> p = new Stack<BinaryTreeWithParent<T>>();
		boolean b = false;
		p.push(t);

		while (!p.isEmpty()) {
			BinaryTreeWithParent<T> a = p.pop();
			if (b) {
				b = true;
				if (!a.isNil()) {
					r = f(r, a.root());
				}
			} else {
				if (a.isNil()) {
					b = true;
					r.first = null;
					r.second = false;
				} else if (a.root().compareTo(x) == 0) {
					b = true;
					r.first = max(a.left());
					r.second = true;
				} else if (a.root().compareTo(x) > 0) {
					p.push(a.left());
					b = false;
				} else {
					p.push(a);
					p.push(a.right());
					b = false;
				}
			}

		}
		return r;
	}

	private Pair<T, Boolean> f(Pair<T, Boolean> t, T x) {
		Pair<T, Boolean> r = t;
		if (t.first == null) {
			if (t.second) {
				r = new Pair<T, Boolean>(x, true);
			} else {
				r = new Pair<T, Boolean>(null, false);
			}
		}
		return r;
	}

	@Override
	public int size() {
		return this.size;
	}

	private static class Pair<T, U> {
		public T first;
		public U second;

		public Pair(T first, U second) {
			this.first = first;
			this.second = second;
		}
	}
}
