package alda.tree_project;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Our implementation of TreeSet.
 * 
 * @param <T> which is the generic type. So that the TreeSet can handle all type of data, 
 * that extends Comparable<T>.
 * @author Viktor Plane - viktorplane.sonie@gmail.com (vipl4364)
 * @author Olof Hofstedt - olof.hofstedt93@gmail.com (olho8226)
 */
public class MyTreeSet<T extends Comparable<T>> {
	private Node<T> head, tail, root;
	
	/**
	 * Constructs a new empty TreeSet with an empty head and tail node.
	 */
	public MyTreeSet() {
		head = new Node<T>(null);
		tail = new Node<T>(null);
		
	}
	
	/**
	 * Private help method for adding an element. It is called by {@link #add(Comparable)}, 
	 * in order to be able to call {@link #updateNextSmallestAndLargest()} before returning.
	 * 
	 * If the root is null, it will set the new data to be added as the root. Else it will 
	 * add the data to the root and return <code>true</code> if it could do that successively.
	 * 
	 * Additionally, it places the node in the right place in the <code>LinkedList</code>.
	 * 
	 * @param data		to be added.
	 * @return <code>true</code> if data was successely added, else <code>false</code>.
	 * @see #add(Comparable)
	 * @see #updateNextSmallestAndLargest()
	 */
	public boolean add(T data) {
		if (root == null) {
			root = new Node<T>(data);
			head.nextLargest = root;
			tail.nextSmallest = root;
			root.nextSmallest = head;
			root.nextLargest = tail;
			return true;
		} else {
			if(root.add(data)) {
				Node<T> newNode = search(root, data);
				Node<T> current = head.nextLargest;
				boolean normal = true;
				while(current.nextLargest != tail && current.nextLargest.data.compareTo(newNode.data) < 0) {
					current = current.nextLargest;
				}
				
				if(newNode.data.compareTo(head.nextLargest.data) < 0) {
					normal = false;
					newNode.nextLargest = head.nextLargest;
					head.nextLargest.nextSmallest = newNode;
					head.nextLargest = newNode;
					newNode.nextSmallest = head;
					
				}
				if(newNode.data.compareTo(tail.nextSmallest.data) > 0) {
					normal = false;
						newNode.nextSmallest = tail.nextSmallest;
						tail.nextSmallest.nextLargest = newNode;
						tail.nextSmallest = newNode;
						newNode.nextLargest = tail;
				}
				if(normal) {
					newNode.nextSmallest = current;
					newNode.nextLargest = current.nextLargest;
					current.nextLargest.nextSmallest = newNode;
					current.nextLargest = newNode;
				}
				
				return true;
			} else
				return false;
		}
	}
	
	/**
	 * Private help method for {@link #remove(Comparable)}.
	 * Checks if the root of the tree is not <code>null</code>. If so calls, 
	 * {@link Node#remove(Comparable)} to remove an element from the tree. 
	 * Returns true if the new size of the tree is less than the old size, 
	 * which means an element has been removed.
	 * 
	 * Additionally, it updates the links in the <code>LinkedList</code> for the remove 
	 * nodes before and after the removed one.
	 * 
	 * @param data to be removed.
	 * @return <code>true</code> if an element has been removed, else <code>false</code>.
	 * @see #remove(Comparable)
	 * @see Node#remove(Comparable)
	 */
	public boolean remove(T data) {
		int originalSize = size();
		if (root != null) {
			Node<T> delNode = search(root, data);

			if(delNode != null) {
				if(delNode.nextSmallest == head) {
					head.nextLargest = delNode.nextLargest;
					delNode.nextLargest.nextSmallest = head;
				}
				if(delNode.nextLargest == tail) {
					tail.nextSmallest = delNode.nextSmallest;
					delNode.nextSmallest.nextLargest = tail;
				}
				
				delNode.nextSmallest.nextLargest = delNode.nextLargest;
				delNode.nextLargest.nextSmallest = delNode.nextSmallest;
				root = root.remove(data);
			}
		}
		
		return size()<originalSize;
	}
	
	/**
	 * A private assist method that is used by {@link #add(Comparable)} and {@link #remove(Comparable)} 
	 * to find the node holding the data. If it doesn't find the exact node, it returns <code>null</code> 
	 * and it's the method using this method's responsibility to check for null.
	 * 
	 * @param node		the next one it should search under.
	 * @return node 	that has the same data as the contains element, else <code>null</code>.
	 */
	private Node<T> search(Node<T> node, T data) {
		int cmp = data.compareTo(node.data);
		if(cmp < 0) {
			if(node.leftChild != null)
				return search(node.leftChild, data);
		}
		else if(cmp > 0) {
			if(node.rightChild != null)
				return search(node.rightChild, data);
		}
		
		if(node.data.equals(data))
			return node;
		else
			return null; // Node with searched data doesn't excist.
	}
	
	/**
	 * Iterator for the TreeSet, it starts from the smallest node, 
	 * and goes to the largest node. Works like a LinkedList by calling 
	 * {@link Node#nextLargest}, which is possible because of {@link #updateNextSmallestAndLargest()}.
	 * @return iterator for the TreeSet.
	 * @see Node#nextLargest
	 * @see #updateNextSmallestAndLargest()
	 */
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Node<T> current = null;
			
			@Override
			public boolean hasNext() {
				return size() > 0 && current != tail.nextSmallest;
			}

			@Override
			public T next() {
				if(current == null) {
					current = head.nextLargest;
					return current.data;
				}
				if(current.nextLargest == null)
					throw new NoSuchElementException();
				current = current.nextLargest;
				return current.data;
			}	
		};
	}
	
	/**
	 * Package only method intended for testing only.
	 * Returns the smallest element of the TreeSet.
	 * @return element which is the smallest one.
	 */
	T findSmallest() {
		return head.nextLargest.data;
	}
	
	/**
	 * Package only method intended for testing only.
	 * Returns the largest element of the TreeSet.
	 * @return element which is the largest one.
	 */
	T findLargest() {
		return tail.nextSmallest.data;
	}
	
	/**
	 * Method to check if data exists in the tree.
	 * @param data to look for.
	 * @return <code>true</code> if the data exists, else <code>false</code>.
	 */
	public boolean contains(T data) {
		return root == null ? false : root.contains(data);
	}
	
	/**
	 * Method that returns the size of the tree. If the root is <code>null</code> 
	 * it will return 0. Else it will call {@link Node#size()} to calculate the size.
	 * 
	 * @return size of the tree.
	 * @see Node#size()
	 */
	public int size() {
		if (root == null)
			return 0;
		else
			return root.size();
	}
	
	/**
	 * Method that returns the depth of the tree. If the root is <code>null</code> 
	 * it will return -1. Else it will call {@link Node#depth()} to calcuate the depth.
	 * 
	 * @return the depth of the tree.
	 * @see Node#depth()
	 */
	public int depth() {
		if (root == null)
			return -1;
		else
			return root.depth();
	}
	
	/**
	 * Clears the tree and linkedlist.
	 */
	public void clear() {
		head.nextLargest = null;
		tail.nextSmallest = null;
		root = null;
	}
	
	
	/**
	 * Package only method that is intended for testing only. 
	 * It will print out the tree. Calls {@link Node#toString()} to
	 * generate the <code>String</code> of all the nodes.
	 */
	String printString() {
		return "[" + (root == null ? "" : root.printString()) + "]";
	}

	@Override
	public String toString() {
		String str = "[";
		Node<T> current = head.nextLargest;
		if(head.nextLargest != null)
			while(current.nextLargest.nextLargest != null) {
				str += current.data + ", ";
				current = current.nextLargest;
			}
		str += current + "]";
		
		return str;
	}
}
