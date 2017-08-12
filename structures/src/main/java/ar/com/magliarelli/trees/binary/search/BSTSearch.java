/**
 * 
 */
package ar.com.magliarelli.trees.binary.search;

import java.util.Iterator;

import java.util.Stack;

import ar.com.magliarelli.trees.binary.BinaryTree;

/**
 * @author lucas
 *
 */
public class BSTSearch<T extends Comparable<T>> implements Search<T> {
	private BinaryTree<T> tree;
	private int size;

	public BSTSearch() {
		this.tree = BinaryTree.nil();
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
		BinaryTree<T> aux = BinaryTree.nil();
		Stack<Tuple<T>> stack = new Stack<Tuple<T>>();
		Tuple<T> tuple = new Tuple<T>(this.tree, null, null, false, false, false);
		stack.push(tuple);
		while (!stack.isEmpty()) {
			tuple = stack.pop();
			if (tuple.finished) {
				if (tuple.left) {
					aux = BinaryTree.bin(tuple.tree, tuple.parent, aux);
				} else {
					aux = BinaryTree.bin(aux, tuple.parent, tuple.tree);
				}
			} else {
				if (tuple.tree.isNil()) {
					aux = BinaryTree.leaf(elem);
				} else {
					if (tuple.tree.root().compareTo(elem) > 0) {
						Tuple<T> rightTree = new Tuple<T>(tuple.tree.right(), tuple.tree.root(), null, false, true,
								false);
						stack.push(rightTree);
						Tuple<T> leftTree = new Tuple<T>(tuple.tree.left(), null, null, true, false, false);
						stack.push(leftTree);
					} else if (tuple.tree.root().compareTo(elem) < 0) {
						Tuple<T> leftTree = new Tuple<T>(tuple.tree.left(), tuple.tree.root(), null, true, true, false);
						stack.push(leftTree);
						Tuple<T> rightTree = new Tuple<T>(tuple.tree.right(), null, null, false, false, false);
						stack.push(rightTree);
					} else {
						aux = BinaryTree.bin(tuple.tree.left(), elem, tuple.tree.right());
						increment = 0;
						inserted = false;
					}
				}
			}
		}
		this.tree = aux;
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
		BinaryTree<T> current = this.tree;
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

	private T max(BinaryTree<T> t) {
		BinaryTree<T> max = BinaryTree.nil();
		BinaryTree<T> current = t;
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

	private T min(BinaryTree<T> t) {
		BinaryTree<T> min = BinaryTree.nil();
		BinaryTree<T> current = t;
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
		BinaryTree<T> aux = BinaryTree.nil();
		Stack<Tuple<T>> stack = new Stack<Tuple<T>>();
		Tuple<T> tuple = new Tuple<T>(this.tree, null, elem, false, false, false);
		stack.push(tuple);
		while (!stack.isEmpty()) {
			tuple = stack.pop();
			if (tuple.finished) {
				if (tuple.left) {
					if (tuple.erased) {
						removed = tuple.elem;
					}
					aux = BinaryTree.bin(tuple.tree, tuple.parent, aux);
				} else {
					if (tuple.erased) {
						removed = tuple.elem;
					}
					aux = BinaryTree.bin(aux, tuple.parent, tuple.tree);
				}
			} else {
				if (tuple.tree.isNil()) {
					removed = null;
					aux = BinaryTree.nil();
				} else {
					if (tuple.tree.root().compareTo(tuple.elem) > 0) {
						Tuple<T> rightTree = new Tuple<T>(tuple.tree.right(), tuple.tree.root(), null, false, true,
								false);
						stack.push(rightTree);
						Tuple<T> leftTree = new Tuple<T>(tuple.tree.left(), null, tuple.elem, true, false, false);
						stack.push(leftTree);
					} else if (tuple.tree.root().compareTo(tuple.elem) < 0) {
						Tuple<T> leftTree = new Tuple<T>(tuple.tree.left(), tuple.tree.root(), tuple.elem, true, true,
								false);
						stack.push(leftTree);
						Tuple<T> rightTree = new Tuple<T>(tuple.tree.right(), null, tuple.elem, false, false, false);
						stack.push(rightTree);
					} else {
						if (tuple.tree.root().compareTo(tuple.elem) == 0) {
							T suc = this.iSuccessor(tuple.tree, tuple.elem).first;
							if (suc != null) {
								Tuple<T> leftTree = new Tuple<T>(tuple.tree.left(), suc, tuple.tree.root(), true, true,
										true);
								stack.push(leftTree);
								Tuple<T> rightTree = new Tuple<T>(tuple.tree.right(), null, suc, false, false, false);
								stack.push(rightTree);
							} else {
								T pred = this.iPredecessor(tuple.tree, tuple.elem).first;
								if (pred != null) {
									Tuple<T> rightTree = new Tuple<T>(tuple.tree.right(), pred, tuple.tree.root(),
											false, true, true);
									stack.push(rightTree);
									Tuple<T> leftTree = new Tuple<T>(tuple.tree.left(), null, pred, true, false, false);
									stack.push(leftTree);
								} else {
									removed = tuple.tree.root();
									aux = BinaryTree.nil();
								}
							}
						}
					}
				}
			}
		}
		this.tree = aux;
		if (removed != null) {
			this.size--;
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
		BinaryTree<T> current = this.tree;
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

	private Pair<T, Boolean> iSuccessor(BinaryTree<T> t, T x) {
		Pair<T, Boolean> r = new Pair<T, Boolean>(null, false);
		boolean b = false;
		Stack<BinaryTree<T>> p = new Stack<BinaryTree<T>>();
		p.push(t);

		while (!p.isEmpty()) {
			BinaryTree<T> a = p.pop();
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

	private Pair<T, Boolean> iPredecessor(BinaryTree<T> t, T x) {
		Pair<T, Boolean> r = new Pair<T, Boolean>(null, false);
		Stack<BinaryTree<T>> p = new Stack<BinaryTree<T>>();
		boolean b = false;
		p.push(t);

		while (!p.isEmpty()) {
			BinaryTree<T> a = p.pop();
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

	private static class Tuple<T> {
		public BinaryTree<T> tree;
		public T parent;
		public T elem;
		public boolean left;
		public boolean finished;
		public boolean erased;

		public Tuple(BinaryTree<T> tree, T parent, T elem, boolean left, boolean finished, boolean erased) {
			super();
			this.tree = tree;
			this.parent = parent;
			this.elem = elem;
			this.left = left;
			this.finished = finished;
			this.erased = erased;
		}

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
