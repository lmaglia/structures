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
							T suc = this.successor(tuple.tree, tuple.elem);
							if (suc != null) {
								Tuple<T> leftTree = new Tuple<T>(tuple.tree.left(), suc, tuple.tree.root(), true, true,
										true);
								stack.push(leftTree);
								Tuple<T> rightTree = new Tuple<T>(tuple.tree.right(), null, suc, false, false, false);
								stack.push(rightTree);
							} else {
								T pred = this.predecessor(tuple.tree, tuple.elem);
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
		// return this.successorAndParent(this.tree, elem).first;
		// return this.successor(this.tree, elem);
		// return this.recursiveSuc(tree, elem);
		return this.iSuccessor(this.tree, elem);
	}

	private T successor(BinaryTree<T> aTree, T elem) {
		BinaryTree<T> current = aTree;
		Stack<BinaryTree<T>> parents = new Stack<BinaryTree<T>>();
		while (!current.isNil() && current.root().compareTo(elem) != 0) {
			parents.push(current);
			if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		T suc = null;
		if (!current.isNil()) {
			if (current.right().isNil()) {
				while (!parents.isEmpty() && parents.peek().right() == current) {
					current = parents.pop();
				}
				if (!parents.isEmpty()) {
					suc = parents.peek().root();
				}
			} else {
				current = current.right();
				BinaryTree<T> sucT = BinaryTree.nil();
				while (!current.isNil()) {
					sucT = current;
					current = current.left();
				}
				if (!sucT.isNil()) {
					suc = sucT.root();
				}
			}
		}
		return suc;
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
		// return this.predecessor(this.tree, elem);
	}

	private T predecessor(BinaryTree<T> aTree, T elem) {
		BinaryTree<T> current = aTree;
		BinaryTree<T> parent = null;
		while (!current.isNil() && current.root().compareTo(elem) != 0) {
			parent = current;
			if (current.root().compareTo(elem) > 0) {
				current = current.left();
			} else {
				current = current.right();
			}
		}
		T pred = null;
		if (!current.isNil()) {
			if (current.left().isNil()) {
				if (parent != null && parent.right() == current) {
					pred = parent.root();
				}
			} else {
				current = current.left();
				BinaryTree<T> predT = BinaryTree.nil();
				while (!current.isNil()) {
					predT = current;
					current = current.right();
				}
				if (!predT.isNil()) {
					pred = predT.root();
				}
			}
		}
		return pred;
	}

	private T recursiveSuc(BinaryTree<T> t, T e) {
		T suc = null;
		if (!t.isNil()) {
			if (t.root().compareTo(e) == 0) {
				suc = min(t.right());
			} else {
				if (t.root().compareTo(e) > 0) {
					suc = this.recursiveSuc(t.left(), e);
					if (suc == null) {
						suc = t.root();
					}
				} else {
					suc = this.recursiveSuc(t.right(), e);
				}
			}
		}
		return suc;
	}

	private T iSuccessor(BinaryTree<T> t, T x) {
		T r = null;
		Stack<Pair<BinaryTree<T>, Boolean>> p = new Stack<Pair<BinaryTree<T>, Boolean>>();
		Pair<BinaryTree<T>, Boolean> se = new Pair<BinaryTree<T>, Boolean>();
		se.first = t;
		se.second = true;
		p.push(se);
		while (!p.isEmpty()) {
			se = p.pop();
			if (se.second) {
				if (se.first.isNil()) {
					Pair<BinaryTree<T>, Boolean> newSe = new Pair<BinaryTree<T>, Boolean>();
					newSe.first = se.first;
					newSe.second = false;
					p.push(newSe);
					r = null;
				} else {
					if (se.first.root().compareTo(x) == 0) {
						Pair<BinaryTree<T>, Boolean> newSe = new Pair<BinaryTree<T>, Boolean>();
						newSe.first = se.first;
						newSe.second = false;
						p.push(newSe);
						r = min(se.first.right());
					} else {
						if (se.first.root().compareTo(x) > 0) {
							Pair<BinaryTree<T>, Boolean> newSe = new Pair<BinaryTree<T>, Boolean>();
							newSe.first = se.first;
							newSe.second = false;
							p.push(newSe);
							newSe = new Pair<BinaryTree<T>, Boolean>();
							newSe.first = se.first.left();
							newSe.second = true;
							p.push(newSe);
						} else {
							Pair<BinaryTree<T>, Boolean> newSe = new Pair<BinaryTree<T>, Boolean>();
							newSe.first = se.first;
							newSe.second = false;
							p.push(newSe);
							newSe = new Pair<BinaryTree<T>, Boolean>();
							newSe.first = se.first.right();
							newSe.second = true;
							p.push(newSe);
						}
					}
				}
			} else {
				if (!se.first.isNil() && se.first.root().compareTo(x) > 0) {
					if (r == null) {
						r = se.first.root();
					}
				}
			}
		}
		return r;
	}

	private Pair<T, Boolean> iPredecessor(BinaryTree<T> t, T x) {
		Pair<T, Boolean> r = new Pair<T, Boolean>();
		r.first = null;
		r.second = false;
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
			r = new Pair<T, Boolean>();
			if (t.second) {
				r.first = x;
				r.second = true;
			} else {
				r.first = null;
				r.second = false;
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
	}
}
