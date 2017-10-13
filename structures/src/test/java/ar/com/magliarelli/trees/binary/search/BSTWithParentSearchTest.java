/**
 * 
 */
package ar.com.magliarelli.trees.binary.search;

/**
 * @author lucas
 *
 */
public class BSTWithParentSearchTest extends SearchTest{

	@Override
	public Search<Integer> createSearch() {		
		return new BSTWithParentSearch<Integer>();
	}
	
}
