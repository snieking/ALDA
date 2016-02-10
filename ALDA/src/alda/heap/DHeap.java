package alda.heap;

/**
 * Implements a d-ary heap. 
 * 
 * @author Viktor Plane (vipl4364) - viktorplane.sonie@gmail.com
 * @author Olof Hofstedt (olho8226) - olof.hofstedt93@gmail.com 
 */
public class DHeap<AnyType extends Comparable<? super AnyType>> {

	/**
	 * Construct a binary heap or a 2-ary heap.
	 */
	public DHeap() {
		this(DEFAULT_CAPACITY, 2);
	}
	
	public DHeap(int d) {
		this(DEFAULT_CAPACITY, d);
	}

	/**
	 * Construct the d-ary heap.
	 * 
	 * @param capacity
	 *            the capacity of the d-ary heap.
	 */
	public DHeap(int capacity, int d) {
		currentSize = 0;
		if (d == 1)
			throw new IllegalArgumentException();

		dary = d;
		array = (AnyType[]) new Comparable[capacity + 1];
	}

	/**
	 * Calculates the index of a nodes parent.
	 * 
	 * @param node
	 * @return index
	 */
	public int parentIndex(int node) {
		if(node < 2)
			throw new IllegalArgumentException();
		
		int first = (node-1)/dary;
		double rest = (node-1)%dary;
		if(rest > 0)
			first++;
		
		return first;
	}
	
	public int firstChildIndex(int node) {
		if(node < 1)
			throw new IllegalArgumentException();
		
		int first = node*dary;
		
		int goLeft = 0;
		for(int i=2; i<dary; i++)
			goLeft++;
		
		return first-goLeft;
	}

	/**
	 * Insert into the priority queue, maintaining heap order. Duplicates are
	 * allowed.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(AnyType x) {
		if (currentSize == array.length - 1)
			enlargeArray(array.length * 2 + 1);
		
		int hole = ++currentSize;
		array[hole] = x;
		percolateUp(hole);
	}

	private void enlargeArray(int newSize) {
		AnyType[] old = array;
		array = (AnyType[]) new Comparable[newSize];
		for (int i = 0; i < old.length; i++)
			array[i] = old[i];
	}

	/**
	 * Find the smallest item in the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return array[1];
	}

	/**
	 * Remove the smallest item from the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType deleteMin() {
		if (isEmpty())
			throw new UnderflowException();

		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);
		
		return minItem;
	}

	/**
	 * Test if the priority queue is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/**
	 * Make the priority queue logically empty.
	 */
	public void makeEmpty() {
		currentSize = 0;
	}

	private static final int DEFAULT_CAPACITY = 10;
	private int currentSize; // Number of elements in heap
	private AnyType[] array; // The heap array
	private int dary = 0;
	
	/**
	 * Internal method to find the child with the lowest value.
	 * 
	 * @param node
	 * @return index of the children with the lowest value.
	 */
	private int smallestChildIndex(int node) {
		int first = firstChildIndex(node);
		int smallest = first;
		for(int i=1; i<dary; i++) {
			if(first+i <= currentSize)
				if(array[smallest].compareTo(array[first+i]) > 0)
					smallest = first+i;
		}
		
		return smallest;
	}

	/**
	 * Internal method to percolate down in the heap.
	 * 
	 * @param hole the index at which the percolate begins.
	 */
	private void percolateDown(int hole) {
		while(hole * dary - (dary/2) <= currentSize) {
			AnyType tmp = array[hole];
			int smallestChild = smallestChildIndex(hole);
				
			if(array[hole].compareTo(array[smallestChild]) > 0) {
				array[hole] = array[smallestChild];
				array[smallestChild] = tmp;
			}
				
			hole = smallestChild;
		}
	}
	
	/**
	 * Internal method to perculate up in the heap.
	 * @param hole the index at which the percolate begins.
	 */
	private void percolateUp(int hole) {
		if(hole > 1)
			if(array[hole].compareTo(array[parentIndex(hole)]) < 0) {
				AnyType temp = array[parentIndex(hole)];
				array[parentIndex(hole)] = array[hole];
				array[hole] = temp;
				percolateUp(parentIndex(hole));
			}
	}

	/**
	 * Return node at index provided.
	 * @param i
	 * @return node
	 */
	public AnyType get(int i) {
		return array[i];
	}

	/**
	 * Returns the current size of the heap.
	 * @return size
	 */
	public int size() {
		return currentSize;
	}

}
