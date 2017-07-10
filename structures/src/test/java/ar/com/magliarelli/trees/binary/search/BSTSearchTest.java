/**
 * 
 */
package ar.com.magliarelli.trees.binary.search;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author lucas
 *
 */
@RunWith(JUnit4.class)
public class BSTSearchTest {
	@Test
	public void insert(){
		BSTSearch<Integer> search= new BSTSearch<Integer>();
		search.insert(4);
		Assert.assertTrue(search.contains(4));
		Assert.assertEquals(1, search.size());
		Assert.assertEquals(new Integer(4), search.maximum());
		Assert.assertEquals(new Integer(4), search.minimum());
		Assert.assertEquals(new Integer(4), search.search(4));
		Assert.assertNull(search.predecessor(4));
		Assert.assertNull(search.successor(4));
		List<Integer> actual= new LinkedList<Integer>();
		for(Integer i:search){
			actual.add(i);
		}
		Assert.assertEquals(Arrays.asList(4), actual);
	}
	@Test
	public void insertDelete(){
		BSTSearch<Integer> search= new BSTSearch<Integer>();
		search.insert(4);
		search.delete(4);
		Assert.assertFalse(search.contains(4));
		Assert.assertEquals(0, search.size());
		Assert.assertNull(search.maximum());
		Assert.assertNull(search.minimum());
		Assert.assertNull(search.search(4));
		Assert.assertNull(search.predecessor(4));
		Assert.assertNull(search.successor(4));
		List<Integer> actual= new LinkedList<Integer>();
		for(Integer i:search){
			actual.add(i);
		}
		Assert.assertEquals(new LinkedList<Integer>(), actual);
	}
	@Test
	public void empty(){
		BSTSearch<Integer> search= new BSTSearch<Integer>();		
		Assert.assertFalse(search.contains(4));
		Assert.assertEquals(0, search.size());
		Assert.assertNull(search.maximum());
		Assert.assertNull(search.minimum());
		Assert.assertNull(search.search(4));
		Assert.assertNull(search.predecessor(4));
		Assert.assertNull(search.successor(4));
		List<Integer> actual= new LinkedList<Integer>();
		for(Integer i:search){
			actual.add(i);
		}
		Assert.assertEquals(new LinkedList<Integer>(), actual);
	}
	@Test
	public void balancedInsert(){
		BSTSearch<Integer> search= new BSTSearch<Integer>();
		search.insert(4);
		search.insert(3);
		search.insert(5);
		search.insert(1);
		search.insert(2);
		search.insert(6);
		search.insert(7);
		
		
		Assert.assertEquals(7, search.size());
		Assert.assertEquals(new Integer(7), search.maximum());
		Assert.assertEquals(new Integer(1), search.minimum());
		Assert.assertEquals(new Integer(4), search.search(4));
		
		List<Integer> expected= Arrays.asList(1,2,3,4,5,6,7);
		List<Integer> actual= new LinkedList<Integer>();
		for(Integer i: search){
			actual.add(i);			
		}
		Assert.assertEquals(expected, actual);
		for(Integer i:expected){
			Assert.assertTrue(search.contains(i));
			Assert.assertEquals((i==1)?null:i-1,search.predecessor(i));
			Assert.assertEquals((i==7)?null:i+1,search.successor(i));
			Assert.assertEquals(i, search.search(i));
		}		
	}
}
