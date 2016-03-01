package alda.tree_project;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
	 * Adds a new element to the tree. Calls the {@link #addAndUpdate(Comparable)}.
	 * If <code>true</code>, uses the {@link #updateNextSmallestAndLargest()} 
	 * method to update the next smallest and next largest element in the tree of the nodes.
	 * Else, does nothing, which happens if you try to add an element which already exists.
	 * 
	 * @param data to be added.
	 * @return <code>true</code> if data was successely added, else <code>false</code>.
	 * @see #addAndUpdate(Comparable)
	 * @see #updateNextSmallestAndLargest()
	 */
	public boolean add(T data) {
		if(addAndUpdate(data)) {
			updateNextSmallestAndLargest();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Private help method for adding an element. It is called by {@link #add(Comparable)}, 
	 * in order to be able to call {@link #updateNextSmallestAndLargest()} before returning.
	 * 
	 * If the root is null, it will set the new data to be added as the root. Else it will 
	 * add the data to the root and return <code>true</code> if it could do that successively.
	 * 
	 * @param data		to be added.
	 * @return <code>true</code> if data was successely added, else <code>false</code>.
	 * @see #add(Comparable)
	 * @see #updateNextSmallestAndLargest()
	 */
	private boolean addAndUpdate(T data) {
		if (root == null) {
			root = new Node<T>(data);
			return true;
		} else {
			if(root.add(data)) {
				return true;
			} else
				return false;
		}
	}
	
	/**
	 * Removes an element from the tree. It calls {@link #removeAndUpdate(Comparable)},
	 * and if it returns <code>true</code> it calls 
	 * {@link #updateNextSmallestAndLargest()} and returns 
	 * <code>true</true>. Else it returns false.
	 * 
	 * @param data to be removed.
	 * @return <code>true</code> if an element has been removed, else <code>false</code>.
	 */
	public  boolean remove(T data) {
		if(removeAndUpdate(data)) {
			updateNextSmallestAndLargest();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Private help method for {@link #remove(Comparable)}.
	 * Checks if the root of the tree is not <code>null</code>. If so calls, 
	 * {@link Node#remove(Comparable)} to remove an element from the tree. 
	 * Returns true if the new size of the tree is less than the old size, 
	 * which means an element has been removed.
	 * 
	 * @param data to be removed.
	 * @return <code>true</code> if an element has been removed, else <code>false</code>.
	 * @see #remove(Comparable)
	 * @see Node#remove(Comparable)
	 */
	private boolean removeAndUpdate(T data) {
		int originalSize = size();
		if (root != null)
			root = root.remove(data);
		return size()<originalSize;
	}
	
	/**
	 * Private method intended to be called when the tree structure 
	 * has been changed, which is after an element has been added or removed.
	 * 
	 * It uses {@link #traverse()} to generate an list with sorted nodes.
	 * Then it assigns the next smallest & largest element to each node.
	 * 
	 * @see #traverse()
	 */
	private void updateNextSmallestAndLargest() {
		ArrayList<Node<T>> tree = (ArrayList<Node<T>>) traverse();
		
		for(int i=0; i<tree.size(); i++) {
			if(i == 0)
				tree.get(0).nextSmallest = null;
			else
				tree.get(i).nextSmallest = tree.get(i-1);
			
			if(i == tree.size())
				tree.get(tree.size()).nextLargest = null;
			else
				tree.get(i).nextLargest = tree.get(i+1);
		}
		
	}
	
	/**
	 * Private help method for {@link #updateNextSmallestAndLargest()} which
	 * generates a list of nodes from the traversed tree in sorted order. 
	 * The method creates an empty list and fills it with help from the recursive 
	 * method {@link #treeTraverse(Node, List)}, to fill up the tree.
	 * 
	 * @return list of sorted nodes from the tree.
	 * @see #updateNextSmallestAndLargest()
	 * @see #treeTraverse(Node, List)
	 */
	private List<Node<T>> traverse() {
		ArrayList<Node<T>> tree = new ArrayList<>();
		
		if(root.leftChild != null)
			treeTraverse(root.leftChild, tree);
		
		tree.add(root);
		
		if(root.rightChild != null)
			treeTraverse(root.rightChild, tree);
		
		return tree;
	}
	
	/**
	 * Private recursive help method for {@link #traverse()}, which attempts
	 * to move as far left as possible, and add those nodes which is the smallest nodes. 
	 * After that the current node gets added, and then it checks for bigger nodes, 
	 * which is the nodes to the right.
	 * 
	 * @param node to be have it's children and itself checked and added.
	 * @param tree which is the list of all the added nodes.
	 * @return list which is the list of all the added nodes.
	 * @see #traverse()
	 */
	private List<Node<T>> treeTraverse(Node<T> node, List<Node<T>> tree) {
		if(node.leftChild != null)
			treeTraverse(node.leftChild, tree);
		
		tree.add(node);
		
		if(node.rightChild != null)
			treeTraverse(node.rightChild, tree);
		
		return tree;
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
	 * Method that will print out the tree. Calls {@link Node#toString()} to
	 * generate the <code>String</code> of all the nodes.
	 */
	public String toString() {
		return "[" + (root == null ? "" : root.toString()) + "]";
	}

}
