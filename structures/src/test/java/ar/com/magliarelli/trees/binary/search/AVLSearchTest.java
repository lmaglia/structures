/**
 * 
 */
package ar.com.magliarelli.trees.binary.search;



/**
 * @author lucas
 *
 */
public class AVLSearchTest extends SearchTest {

	@Override
	public Search<Integer> createSearch() {		
		return new AVLSearch<Integer>();
	}
	
}
