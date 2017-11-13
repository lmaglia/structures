/**
 * 
 */
package ar.com.magliarelli.trees.binary.search;

/**
 * @author lucas
 *
 */
public class RedBlackTreeSearchTest extends SearchTest{

	@Override
	public Search<Integer> createSearch() {		
		return new RedBlackTreeSearch<Integer>();
	}

}
