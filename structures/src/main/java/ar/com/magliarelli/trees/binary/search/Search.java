/**
 * 
 */
package ar.com.magliarelli.trees.binary.search;

/**
 * @author lucas
 *
 */
public interface Search<T extends Comparable<T>> extends Iterable<T> {
	public boolean insert(T elem);
	public T search(T elem);
	public T maximum();
	public T minimum();
	public T delete(T elem);
	public boolean contains(T elem);
	public T successor(T elem);
	public T predecessor(T elem);
	public int size();
}
