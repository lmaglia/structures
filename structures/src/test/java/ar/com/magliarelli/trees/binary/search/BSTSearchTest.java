/**
 * 
 */
package ar.com.magliarelli.trees.binary.search;

/**
 * @author lucas
 *
 */
public class BSTSearchTest extends SearchTest {

	@Override
	public Search<Integer> createSearch() {		
		return new BSTSearch<Integer>();
	}
	
}
