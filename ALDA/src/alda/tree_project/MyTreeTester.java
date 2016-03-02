package alda.tree_project;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class MyTreeTester {
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
		
		assertEquals(6, intTree.size());
		assertTrue(intTree.remove(2));
		assertFalse(intTree.remove(20));
		assertEquals("[4, 7, 9, 10, 14]", intTree.toString());
		assertEquals(3, intTree.depth());
//		assertEquals("[4]", intTree.findSmallest());
//		Iterator<?> iter = intTree.iterator();
//		assertEquals("4", iter.next());
//		assertEquals(7, iter.next());
//		assertTrue(intTree.remove(9));
//		assertEquals(10, iter.next());
	}

}
