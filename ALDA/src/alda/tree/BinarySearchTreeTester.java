package alda.tree;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.*;
import static org.junit.Assert.*;

public class BinarySearchTreeTester {

	private BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
	private BinarySearchTree<Integer> tree2 = new BinarySearchTree<Integer>();
	private BinarySearchTree<Integer> tree3 = new BinarySearchTree<Integer>();

	@Test
	public void testEmptyTree() {
		tree.clear();
		assertEquals(0, tree.size());
		assertEquals(-1, tree.depth());
		assertEquals("[]", tree.toString());
	}


	@Before
	public void setUp() throws Exception {
		tree.add(5);
		tree.add(4);
		tree.add(2);
		tree.add(3);
		tree.add(6);
		tree.add(1);
	}
	
	@Before
	public void setUp2() throws Exception {
		tree2.add(1);
		tree2.add(6);
		tree2.add(3);
		tree2.add(2);
		tree2.add(4);
		tree2.add(5);
	}
	
	@Before
	public void setUp3() throws Exception {
		tree3.add(8);
		tree3.add(7);
		tree3.add(0);
		tree3.add(4);
		tree3.add(9);
		tree3.add(5);
	}
	
	@Test
	public void additionalRemoveTest1() {
		SortedSet<Integer> oracle = new TreeSet<Integer>();
		oracle.add(8);
		oracle.add(7);
		oracle.add(0);
		oracle.add(4);
		oracle.add(9);
		oracle.add(5);
		
		assertEquals(oracle.size(), tree3.size());
		assertEquals(oracle.add(5), tree3.add(5)); // 0
		assertEquals(oracle.remove(0), tree3.remove(0));
		assertEquals(oracle.size(), tree3.size());
		
		int checkContains = 8;
		assertEquals(oracle.contains(checkContains), 
		tree3.contains(checkContains));
		assertEquals(oracle.size(), tree3.size());
		assertEquals(oracle.toString(), tree3.toString());
	}
	
	@Test
	public void additionalRemoveTest2() {
			SortedSet<Integer> oracle = new TreeSet<Integer>();
			for (int n = 1; n <= 6; n++)
				oracle.add(n);
			
			assertEquals(oracle.add(5), tree2.add(5)); // 0
			assertEquals(oracle.remove(8), tree2.remove(8));
			
			assertEquals(oracle.add(9), tree2.add(9)); // 1
			assertEquals(oracle.remove(7), tree2.remove(7));
			
			assertEquals(oracle.add(3), tree2.add(3)); // 2
			assertEquals(oracle.remove(1), tree2.remove(1));
			
			assertEquals(oracle.add(2), tree2.add(2)); // 3
			assertEquals(oracle.remove(0), tree2.remove(0));
			
			assertEquals(oracle.add(8), tree2.add(8)); // 4
			assertEquals(oracle.remove(3), tree2.remove(3));
			
			assertEquals(oracle.add(0), tree2.add(0)); // 5
			assertEquals(oracle.remove(4), tree2.remove(4));
			
			assertEquals(oracle.add(8), tree2.add(8)); // 6
			assertEquals(oracle.remove(6), tree2.remove(6));
			
			int checkContains = 9;
			assertEquals(oracle.contains(checkContains), 
			tree2.contains(checkContains));
			assertEquals(oracle.size(), tree2.size());
			assertEquals(oracle.toString(), tree2.toString());
			
	}
	
	@Test
	public void additionalRemoveTest3() {
			SortedSet<Integer> oracle = new TreeSet<Integer>();
			for (int n = 1; n <= 6; n++)
				oracle.add(n);
			
			assertEquals(oracle.add(0), tree2.add(0)); // 0
			assertEquals(oracle.remove(9), tree2.remove(9));
			
			assertEquals(oracle.add(9), tree2.add(9)); // 1
			assertEquals(oracle.remove(4), tree2.remove(4));
			
			assertEquals(oracle.add(7), tree2.add(7)); // 2
			assertEquals(oracle.remove(6), tree2.remove(6));
			
			int checkContains = 9;
			assertEquals(oracle.contains(checkContains), 
			tree2.contains(checkContains));
			assertEquals(oracle.size(), tree2.size());
			assertEquals(oracle.toString(), tree2.toString());

	}
	
	
	@Test
	public void additionalRemoveTest4() {
		SortedSet<Integer> oracle = new TreeSet<Integer>();
		for (int n = 1; n <= 6; n++)
			oracle.add(n);
		
		assertEquals(oracle.add(3), tree2.add(3)); // 0
		assertEquals(oracle.remove(5), tree2.remove(5));
		
		assertEquals(oracle.add(6), tree2.add(6)); // 1
		assertEquals(oracle.remove(6), tree2.remove(6));
		
		assertEquals(oracle.add(3), tree2.add(3)); // 2
		assertEquals(oracle.remove(5), tree2.remove(5));
		
		assertEquals(oracle.add(8), tree2.add(8)); // 3
		assertEquals(oracle.remove(3), tree2.remove(3));
		
		assertEquals(oracle.add(5), tree2.add(5)); // 4
		assertEquals(oracle.remove(9), tree2.remove(9));
		
		assertEquals(oracle.add(7), tree2.add(7)); // 5
		assertEquals(oracle.remove(9), tree2.remove(9));
		
		assertEquals(oracle.add(2), tree2.add(2)); // 6
		assertEquals(oracle.remove(0), tree2.remove(0));
		
		assertEquals(oracle.add(6), tree2.add(6)); // 7
		assertEquals(oracle.remove(8), tree2.remove(8));
		
		assertEquals(oracle.add(1), tree2.add(1)); // 8
		assertEquals(oracle.remove(8), tree2.remove(8));
		
		assertEquals(oracle.add(6), tree2.add(6)); // 9
		assertEquals(oracle.remove(6), tree2.remove(6));
		
		int checkContains = 9;
		assertEquals(oracle.contains(checkContains), 
		tree2.contains(checkContains));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.toString(), tree2.toString());
	}
	
	@Test
	public void additionalRemoveTest5() {
		SortedSet<Integer> oracle = new TreeSet<Integer>();
		for (int n = 1; n <= 6; n++)
			oracle.add(n);
		
		assertEquals(oracle.add(6), tree2.add(6)); // 0
		assertEquals(oracle.remove(1), tree2.remove(1));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(8), tree2.add(8)); // 1
		assertEquals(oracle.remove(3), tree2.remove(3));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(2), tree2.add(2)); // 2
		assertEquals(oracle.remove(5), tree2.remove(5));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(9), tree2.add(9)); // 3
		assertEquals(oracle.remove(9), tree2.remove(9));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(9), tree2.add(9)); // 4
		assertEquals(oracle.remove(3), tree2.remove(3));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(9), tree2.add(9)); // 5
		assertEquals(oracle.remove(8), tree2.remove(8));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(3), tree2.add(3)); // 6
		assertEquals(oracle.remove(0), tree2.remove(0));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(9), tree2.add(9)); // 7
		assertEquals(oracle.remove(8), tree2.remove(8));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(1), tree2.add(1)); // 8
		assertEquals(oracle.remove(4), tree2.remove(4));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(5), tree2.add(5)); // 9
		assertEquals(oracle.remove(1), tree2.remove(1));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(1), tree2.add(1)); // 10
		assertEquals(oracle.remove(3), tree2.remove(3));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(2), tree2.add(2)); // 11
		assertEquals(oracle.remove(6), tree2.remove(6));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(3), tree2.add(3)); // 12
		assertEquals(oracle.remove(7), tree2.remove(7));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(4), tree2.add(4)); // 13
		assertEquals(oracle.remove(8), tree2.remove(8));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(7), tree2.add(7)); // 14
		assertEquals(oracle.remove(8), tree2.remove(8));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(4), tree2.add(4)); // 15
		assertEquals(oracle.remove(3), tree2.remove(3));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.add(1), tree2.add(1)); // 16
		assertEquals(oracle.remove(2), tree2.remove(2));
		assertEquals(oracle.toString(), tree2.toString());
		
		assertEquals(oracle.size(), tree2.size());
		
		int checkContains = 9;
		assertEquals(oracle.contains(checkContains), 
		tree2.contains(checkContains));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.toString(), tree2.toString());
	}
	
	@Test
	public void additionalRemoveTest6() {
		SortedSet<Integer> oracle = new TreeSet<Integer>();
		for (int n = 1; n <= 6; n++)
			oracle.add(n);
		
		assertEquals(oracle.add(8), tree2.add(8)); // 0
		assertEquals(oracle.remove(4), tree2.remove(4));
		
		assertEquals(oracle.add(6), tree2.add(6)); // 1
		assertEquals(oracle.remove(7), tree2.remove(7));
		
		assertEquals(oracle.add(6), tree2.add(6)); // 2
		assertEquals(oracle.remove(0), tree2.remove(0));
		
		assertEquals(oracle.add(5), tree2.add(5)); // 3
		assertEquals(oracle.remove(5), tree2.remove(5));
		
		assertEquals(oracle.add(8), tree2.add(8)); // 4
		assertEquals(oracle.remove(4), tree2.remove(4));
		
		assertEquals(oracle.add(9), tree2.add(9)); // 5
		assertEquals(oracle.remove(0), tree2.remove(0));
		
		assertEquals(oracle.add(5), tree2.add(5)); // 6
		assertEquals(oracle.remove(3), tree2.remove(3));
		
		assertEquals(oracle.add(3), tree2.add(3)); // 7
		assertEquals(oracle.remove(2), tree2.remove(2));
		
		assertEquals(oracle.add(7), tree2.add(7)); // 8

		assertEquals(oracle.remove(5), tree2.remove(5));
		
		assertEquals(oracle.size(), tree.size());
		
		int checkContains = 9;
		assertEquals(oracle.contains(checkContains), 
		tree2.contains(checkContains));
		assertEquals(oracle.size(), tree2.size());

		assertEquals(oracle.toString(), tree2.toString());

	}
	
	@Test
	public void additionalRemoveTest7() {
		SortedSet<Integer> oracle = new TreeSet<Integer>();
		for (int n = 1; n <= 6; n++)
			oracle.add(n);
		
		int toAdd = 8;
		int toRemove = 8;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 0
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 2;
		toRemove = 8;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 1
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 6;
		toRemove = 2;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 2
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 8;
		toRemove = 6;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 3
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 2;
		toRemove = 2;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 4
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 2;
		toRemove = 0;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 5
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 7;
		toRemove = 1;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 6
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 5;
		toRemove = 0;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 7
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 6;
		toRemove = 1;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 8
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 6;
		toRemove = 9;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 9
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 4;
		toRemove = 1;
		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 10
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
		
		toAdd = 7;
		toRemove = 5;

		assertEquals(oracle.add(toAdd), tree2.add(toAdd)); // 11
		assertEquals(oracle.remove(toRemove), tree2.remove(toRemove));
		assertEquals(oracle.size(), tree2.size());
		assertEquals(oracle.contains(toAdd), 
		tree2.contains(toAdd));
	}

	@Test
	public void testAddUnique() {
		for (int n = 1; n <= 6; n++) {
			assertTrue(tree.contains(n));
		}
	}

	@Test
	public void testSize() {
		assertEquals(6, tree.size());
	}

	@Test
	public void testDepth() {
		assertEquals(3, tree.depth());
	}

	@Test
	public void testToString() {
		assertEquals("[1, 2, 3, 4, 5, 6]", tree.toString());
	}

	@Test
	public void testAddDuplicates() {
		for (int n = 1; n <= 6; n += 2)
			assertFalse(tree.add(n));
	}

	@Test
	public void testRemoveExistingLeaf() {
		assertTrue(tree.remove(1));
		assertEquals(5, tree.size());
		assertEquals("[2, 3, 4, 5, 6]", tree.toString());
	}
	
	@Test
	public void testRemoveExistingLeaf2() {
		assertTrue(tree.remove(3));
		assertEquals(5, tree.size());
		assertEquals("[1, 2, 4, 5, 6]", tree.toString());
	}

	@Test
	public void testRemoveExistingMiddleItemWithEmptyRightChild() {
		assertTrue(tree.remove(4));
		assertEquals(5, tree.size());
		assertEquals("[1, 2, 3, 5, 6]", tree.toString());
	}

	@Test
	public void testRemoveExistingMiddleItemWithEmptyLeftChild() {
		tree.add(7);
		assertTrue(tree.remove(6));
		assertEquals(6, tree.size());
		assertEquals("[1, 2, 3, 4, 5, 7]", tree.toString());
	}

	@Test
	public void testRemoveExistingMiddleItemWithTwoChildren() {
		assertTrue(tree.remove(2));
		assertEquals(5, tree.size());
		assertEquals("[1, 3, 4, 5, 6]", tree.toString());
	}

	@Test
	public void testRemoveRoot() {
		assertTrue(tree.remove(5));
		assertEquals(5, tree.size());
		assertEquals("[1, 2, 3, 4, 6]", tree.toString());
	}

	@Test
	public void testRandomAddAndRemove() {
		Random rnd = new Random();

		SortedSet<Integer> oracle = new TreeSet<Integer>();
		for (int n = 1; n <= 6; n++)
			oracle.add(n);
		

		for (int n = 0; n < 500000; n++) {
			int toAdd = rnd.nextInt(10);
			assertEquals(oracle.add(toAdd), tree.add(toAdd));
			int toRemove = rnd.nextInt(10);
			assertEquals(oracle.remove(toRemove), tree.remove(toRemove));
			int checkExists = rnd.nextInt(10);
			assertEquals(oracle.contains(checkExists), 
					tree.contains(checkExists));
			assertEquals(oracle.size(), tree.size());
			assertEquals(oracle.toString(), tree.toString());
		}
	}

	@Test
	public void testOtherType(){
		BinarySearchTree<String> stringTree = new BinarySearchTree<String>();
		stringTree.add("D");
		stringTree.add("A");
		stringTree.add("C");
		stringTree.add("A");
		stringTree.add("B");
		assertEquals(4, stringTree.size());
		assertTrue(stringTree.contains("C"));
		stringTree.remove("C");
		assertFalse(stringTree.contains("C"));
	}
	
}