package alda.tree_project;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class MyTreeTester<T extends Comparable<T>> {
	MyTreeSet<Integer> intTree = new MyTreeSet<>();
	
	public void constructIntTree() {
		intTree.add(10);
		intTree.add(4);
		intTree.add(14);
		intTree.add(2);
		intTree.add(7);
		intTree.add(9);
		intTree.add(14);
	}

	@Test
	public void testSimpleOperations() {
		constructIntTree();
		
		// [2, 4, 7, 9, 10, 14]
		assertEquals(6, intTree.size());
		assertTrue(intTree.remove(2));
		assertFalse(intTree.remove(20));
		assertEquals("[4, 7, 9, 10, 14]", intTree.toString());
		assertEquals("[4, 7, 9, 10, 14]", intTree.printString());
		assertEquals(3, intTree.depth());
		assertEquals("4", intTree.findSmallest().toString());
		Iterator<?> iter = intTree.iterator();
		assertEquals(4, iter.next());
		assertEquals(7, iter.next());
		assertTrue(intTree.remove(9));
		assertEquals(10, iter.next());
	}
	
	@Test
	public void testRandomOperations() {
		Random rnd = new Random();
		
		SortedSet<Integer> oracle = new TreeSet<Integer>();
		
		for(int n = 0; n < 100000; n++) {
			int toAdd = rnd.nextInt(100);
			assertEquals(oracle.add(toAdd), intTree.add(toAdd));
			int toRemove = rnd.nextInt(100);
			assertEquals(oracle.remove(toRemove), intTree.remove(toRemove));
			int checkExists = rnd.nextInt(100);
			assertEquals(oracle.contains(checkExists), intTree.contains(checkExists));
			assertEquals(intTree.printString(), intTree.toString());
			assertEquals(intTree.printString(), oracle.toString());
			assertEquals(oracle.size(), intTree.size());
			
		}
	}

}
