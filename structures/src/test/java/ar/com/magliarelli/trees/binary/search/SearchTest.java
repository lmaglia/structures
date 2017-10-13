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
public abstract class SearchTest {
	@Test
	public void insert() {
		Search<Integer> search = this.createSearch();
		search.insert(4);
		Assert.assertTrue(search.contains(4));
		Assert.assertEquals(1, search.size());
		Assert.assertEquals(new Integer(4), search.maximum());
		Assert.assertEquals(new Integer(4), search.minimum());
		Assert.assertEquals(new Integer(4), search.search(4));
		Assert.assertNull(search.predecessor(4));
		Assert.assertNull(search.successor(4));
		List<Integer> actual = new LinkedList<Integer>();
		for (Integer i : search) {
			actual.add(i);
		}
		Assert.assertEquals(Arrays.asList(4), actual);
	}

	@Test
	public void insertDelete() {
		Search<Integer> search = this.createSearch();
		search.insert(4);
		search.delete(4);
		Assert.assertFalse(search.contains(4));
		Assert.assertEquals(0, search.size());
		Assert.assertNull(search.maximum());
		Assert.assertNull(search.minimum());
		Assert.assertNull(search.search(4));
		Assert.assertNull(search.predecessor(4));
		Assert.assertNull(search.successor(4));
		List<Integer> actual = new LinkedList<Integer>();
		for (Integer i : search) {
			actual.add(i);
		}
		Assert.assertEquals(new LinkedList<Integer>(), actual);
	}

	@Test
	public void empty() {
		Search<Integer> search = this.createSearch();
		Assert.assertFalse(search.contains(4));
		Assert.assertEquals(0, search.size());
		Assert.assertNull(search.maximum());
		Assert.assertNull(search.minimum());
		Assert.assertNull(search.search(4));
		Assert.assertNull(search.predecessor(4));
		Assert.assertNull(search.successor(4));
		List<Integer> actual = new LinkedList<Integer>();
		for (Integer i : search) {
			actual.add(i);
		}
		Assert.assertEquals(new LinkedList<Integer>(), actual);
	}

	@Test
	public void balancedInsert() {
		Search<Integer> search = this.createSearch();
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

		List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
		List<Integer> actual = new LinkedList<Integer>();
		for (Integer i : search) {
			actual.add(i);
		}
		Assert.assertEquals(expected, actual);
		for (Integer i : expected) {
			Assert.assertTrue(search.contains(i));
			Assert.assertEquals((i == 1) ? null : i - 1, search.predecessor(i));
			Assert.assertEquals((i == 7) ? null : i + 1, search.successor(i));
			Assert.assertEquals(i, search.search(i));
		}
	}

	@Test
	public void deleteLeaves() {
		Search<Integer> search = this.createSearch();
		search.insert(4);
		search.insert(3);
		search.insert(5);
		search.insert(1);
		search.insert(2);
		search.insert(6);
		search.insert(7);

		List<Integer> deletionOrder = Arrays.asList(2, 7, 1, 6, 3, 5, 4);
		int expectedSize = deletionOrder.size();
		for (Integer i : deletionOrder) {
			Assert.assertEquals("It should have returned " + i, i, search.delete(i));
			expectedSize--;
			Assert.assertEquals("The size is not expected", expectedSize, search.size());
			Assert.assertFalse(search.contains(i));
		}
	}

	@Test
	public void deleteRoots() {
		Search<Integer> search = this.createSearch();
		search.insert(4);
		search.insert(3);
		search.insert(5);
		search.insert(1);
		search.insert(2);
		search.insert(6);
		search.insert(7);

		List<Integer> deletionOrder = Arrays.asList(4, 3, 5, 1, 6, 2, 7);
		int expectedSize = deletionOrder.size();
		for (Integer i : deletionOrder) {
			Assert.assertEquals("The element returned by delete is not expected", i, search.delete(i));
			expectedSize--;
			Assert.assertEquals("The size is not expected", expectedSize, search.size());
			Assert.assertFalse("It should not have contained " + i, search.contains(i));
		}
	}

	@Test
	public void deleteBadBranch() {
		Search<Integer> search = this.createSearch();
		search.insert(3);
		search.insert(1);
		search.insert(2);

		List<Integer> deletionOrder = Arrays.asList(3, 1, 2);
		int expectedSize = deletionOrder.size();
		for (Integer i : deletionOrder) {
			Assert.assertEquals("The element returned by delete is not expected", i, search.delete(i));
			expectedSize--;
			Assert.assertEquals("The size is not expected", expectedSize, search.size());
			Assert.assertFalse("It should not have contained " + i, search.contains(i));
		}
	}

	@Test
	public void deleteNotContainedElement() {
		Search<Integer> search = this.createSearch();
		search.insert(3);
		search.insert(1);
		search.insert(2);
		Integer elem= search.delete(4);
		Assert.assertEquals("Size is not expected",3, search.size());
		Assert.assertNull("Delete should have returned null",elem);
		
	}
	public abstract Search<Integer> createSearch();
}
