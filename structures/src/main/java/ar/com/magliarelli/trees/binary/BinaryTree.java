package ar.com.magliarelli.trees.binary;

/**
 * @author Lucas Magliarelli
 *
 */
public class BinaryTree<T> {
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
}
