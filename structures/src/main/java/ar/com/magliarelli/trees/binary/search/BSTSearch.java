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
		BinaryTree<T> max = BinaryTree.nil();
		BinaryTree<T> current = this.tree;
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
		//return this.successorAndParent(this.tree, elem).first;
		return this.successor(this.tree, elem);
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
		return this.predecessor(this.tree, elem);
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

	private Pair<T, T> successorAndParent(BinaryTree<T> a, T elem) {
		//TOOD when the successor is the parent, it is not returning the right result
		Pair<T, T> sucAndP = new Pair<T, T>();
		Pair<BinaryTree<T>, Boolean> stackElem = new Pair<BinaryTree<T>, Boolean>();
		stackElem.first = a;
		stackElem.second = true;
		Stack<Pair<BinaryTree<T>, Boolean>> stack = new Stack<Pair<BinaryTree<T>, Boolean>>();
		stack.push(stackElem);
		while (!stack.isEmpty()) {
			stackElem = stack.pop();
			if (stackElem.first.isNil()) {
				sucAndP.first = null;
				sucAndP.second = null;
			} else {
				if (stackElem.second) {
					if (stackElem.first.root().compareTo(elem) == 0) {
						sucAndP.first = this.min(stackElem.first.right());
						sucAndP.second = null;
					} else {
						Pair<BinaryTree<T>, Boolean> se = new Pair<BinaryTree<T>, Boolean>();
						se.first = stackElem.first;
						se.second = false;
						stack.push(se);
						se = new Pair<BinaryTree<T>, Boolean>();
						se.second = true;
						if (stackElem.first.root().compareTo(elem) > 0) {
							se.first = stackElem.first.left();
						} else {
							se.first = stackElem.first.right();
						}
						stack.push(se);
					}
				} else {
					BinaryTree<T> tree = stackElem.first;

					if (tree.root().compareTo(elem) > 0) {
						if (sucAndP.first != null) {
							if (!tree.left().isNil() && tree.left().root().compareTo(elem) == 0) {
								sucAndP.second = tree.root();
							}
						} else {
							if (!tree.left().isNil() && tree.left().root().compareTo(elem) == 0) {
								sucAndP.first = tree.root();
								sucAndP.second = tree.root();
							} else {
								sucAndP.first = sucAndP.second;
							}
						}
					} else {
						if (sucAndP.first != null) {
							if (!tree.right().isNil() && tree.right().root().compareTo(elem) == 0) {
								sucAndP.second = tree.root();
							}
						} else {
							if (!tree.right().isNil() && tree.right().root().compareTo(elem) == 0) {
								sucAndP.first = tree.root();
								sucAndP.second = tree.root();
							} else {
								sucAndP.first = sucAndP.second;
							}
						}
					}
				}
			}
		}
		return sucAndP;
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
