package alda.tree_project;


/**
 * The node class intended to be used in our implementation of TreeSet.
 * 
 * @param <T> which is the generic type. So that the TreeSet can handle all type of data, 
 * that extends Comparable<T>.
 * @author Viktor Plane - viktorplane.sonie@gmail.com (vipl4364)
 * @author Olof Hofstedt - olof.hofstedt93@gmail.com (olho8226)
 */
public class Node<T extends Comparable<T>> {
	T data;
	Node<T> leftChild = null;
	Node<T> rightChild = null;
	Node<T> nextSmallest;
	Node<T> nextLargest;
	
	/**
	 * Creates a new Node which acts like a wrapper class around the data. 
	 * Intended to be used for a binary search tree.
	 * 
	 * @param data which the Node is a wrapper class around.
	 */
	Node(T data) {
		this.data = data;
		nextSmallest = null;
		nextLargest = null;
	}
	
	/**
	 * Adds a new node to the TreeSet structure. 
	 * If the node already exists then nothing is changed.
	 * It compares the new data to the current nodes data and decides if it should be 
	 * added to the left or the right (sub)tree.
	 * 
	 * @param data 		for the new node to be added.
	 * @return <code>true</code> if a new node was successfully added, else <code>false</code>
	 */
	boolean add(T data) {
		if(data == null)
			throw new IllegalArgumentException("Can't add null to the tree");
		
		int cmp = data.compareTo(this.data);
		if(cmp < 0) {
			if(leftChild == null) {
				leftChild = new Node<T>(data);
				return true;
			} else
				return leftChild.add(data);
		}
		else if(cmp > 0) {
			if(rightChild == null) {
				rightChild = new Node<T>(data);
				return true;
			} else 
				return rightChild.add(data);
		} else
			return false;
	}
	
	/**
	 * Removes an element from the tree. Calls {@link #checkRoot(Node, Comparable)}.
	 * If the element doesn't exist, nothing will happen.
	 * @param data
	 * @return root of the tree.
	 * @see #checkRoot(Node, Comparable)
	 */
	Node<T> remove(T data) {
		Node<T> theRoot = checkRoot(this, data);
		return theRoot;
	}
	
	/**
	 * Private recursive assist method for {@link #remove(Comparable)} that is used to check 
	 * a normal node, not the root node. It calls {@link #checkRoot(Node, Comparable)} to make the changes on
	 * subroots if the data has matched.
	 * 
	 * @param node 		that it starts looking at.
	 * @param data 		to look for.
	 * @see #remove(Comparable)
	 * @see #checkRoot(Node, Comparable)
	 */
	private void checkNormal(Node<T> node, T data) {
		int cmp;
		if(node != null) {
			cmp = data.compareTo(node.data);

			if(node.leftChild != null || node.rightChild != null) {
				cmp = data.compareTo(node.data);
				if(node.rightChild != null && node.rightChild.data == data) {
					cmp = data.compareTo(node.rightChild.data);
				}
				else if(node.leftChild != null && node.leftChild.data == data) {
					cmp = data.compareTo(node.leftChild.data);
				}
				if(cmp < 0 && node.leftChild != null) {
					checkNormal(node.leftChild, data); //
				} else if(cmp > 0 && node.rightChild != null) {
						checkNormal(node.rightChild, data); //
				} else if(cmp == 0) {
					Node<T> newSubRoot;
					if(node.leftChild != null && node.leftChild.data == data) {
						newSubRoot = checkRoot(node.leftChild, data);
						node.leftChild = newSubRoot;
					}
					else {
						newSubRoot = checkRoot(node.rightChild, data);
						node.rightChild = newSubRoot;
					}
				}
			}
		}
	}
	
	/**
	 * Private assist method for {@link #remove(Comparable)} that 
	 * checks and compares a root/subroot and switches place if changes occur. 
	 * This method only makes changes if the roots data is the same as the data that is to be removed, 
	 * else it will call {@link #checkNormal(Node, Comparable)}.
	 * 
	 * @param root 		which is the node that it checks.
	 * @param data 		which the node should compare to.
	 * @return newRoot 	which is the updated root/subroot.
	 * @see #remove(Comparable)
	 * @see #checkNormal(Node, Comparable)
	 */
	private Node<T> checkRoot(Node<T> root, T data) {
		if(root.data == data) {
			Node<T> tempLeft = root.leftChild;
			Node<T> temp = findNodeBeforeMin(root);

			if(root.data == temp.data) {
				Node<T> tempRight;
				if(root.rightChild != null)
					tempRight = root.rightChild.rightChild;
				else
					tempRight = null;
				
				Node<T> newRoot;
				if(temp.rightChild != null) {
					newRoot = temp.rightChild;
					if(tempLeft != null)
						newRoot.leftChild = tempLeft;
					if(tempRight != null)
						newRoot.rightChild = tempRight;
				} else
					newRoot = temp.leftChild;
				
				return newRoot;
			}
			else {
				/* Sets newRoot to the lowest value on the left side. */
				Node<T> newRoot = temp.leftChild; 

				/* Removes the connection to the new root. */
				temp.leftChild = null;
				
				/* Searches for the highest value from the new root and adds the value which was before */
				findMax(newRoot).rightChild = root.rightChild;
				
				/* Sets the new roots left child to the left child of the original root. */
				newRoot.leftChild = tempLeft;
				return newRoot;
			}
		}
		
		checkNormal(root, data);
		
		return root;
	}
	
	/**
	 * Private assist method which is useful during removal.
	 * Searches for the lowest value in the sub(tree) that the node is root in.
	 * Which is planned to to take the removed roots place.
	 * 
	 * @param node 		which is the root in the (sub)tree.
	 * @return the smallest element in the (sub)tree that the node is root in.
	 */
	private Node<T> findMin(Node<T> node) {
		if(node.leftChild.leftChild != null)
			return findMin(node.leftChild);
		else
			return node;
	}
	
	/**
	 * Private recursive assist method which founds the highest element under the node.
	 * Useful when removing.
	 * 
	 * @param node 		that it should search under.
	 * @return node		which is the most right one.
	 */
	private Node<T> findMax(Node<T> node) {
		if(node.rightChild != null)
			return findMax(node.rightChild);
		else
			return node;
	}
	
	/**
	 * Private recursive assist method that is used to find the node before the lowest value.
	 * Which {@link #checkRoot(Node, Comparable)} uses as help when it's gonna move nodes around the tree.
	 * 
	 * @param node 		which it should continue searching in.
	 * @return the lowest value.
	 * @see #checkRoot(Node, Comparable)
	 */
	private Node<T> findNodeBeforeMin(Node<T> node) {
		if(node.rightChild != null) {
			if(node.rightChild.leftChild != null)
				return findMin(node.rightChild);
			else
				return node;
		}
		return node;
	}
	
	/**
	 * Checks if the given element exists in the (sub)tree that the node is root in. 
	 * Uses {@link #search(Node, Comparable)} to find the node.
	 * 
	 * @param data 		that it is searching for.
	 * @return <code>true</code> if the searched element exists in the (sub)tree that the node
	 * is root in. Else, if it can't be found, returns <code>false</code>.
	 */
	boolean contains(T data) {
		if(search(this, data).data == data)
			return true;
		else
			return false;
	}
	
	/**
	 * Privat assist method that is used recursively by 
	 * {@link #contains(Comparable)} to search for the element.
	 * 
	 * @param node		the next one it should search under.
	 * @return node 	that has the same data as the contains element, else the root.
	 * @see #contains(Comparable)
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
		
		return node;
	}
	
	/**
	 * Size of the (sub)tree that the node is root in. 
	 * Calls {@link #sizeCalculator(Node, int)} to do the calculation.
	 * 
	 * @return size which is the total amount of nodes in the (sub)tree that the node is root in.
	 * @see #sizeCalculator(Node, int)
	 */
	int size() {
		int size = 0;
		size += sizeCalculator(this, size);
		return size;
	}
	
	/**
	 * Private recursive assist method for 
	 * {@link #size()} that calculates the size.
	 * 
	 * @param node 		that it calculate from.
	 * @param size		that is the counter.
	 * @return size		that is the counter.
	 * @see #size()
	 */
	private int sizeCalculator(Node<T> node, int siz) {
		int size = siz+1;
		if(node.leftChild != null)
			size = sizeCalculator(node.leftChild, size);
		if(node.rightChild != null)
			size = sizeCalculator(node.rightChild, size);
		return size;
	}
	
	/**
	 * The highest depth in the (sub)tree that the node is root in.
	 * Calls {@link #depthCalculator(Node, int)} to do the calculation.
	 * Uses Math.max to choose the highest depth from its children.
	 * 
	 * @return depth.
	 * @see #depthCalculator(Node, int)
	 */
	int depth() {
		int leftDep = 0, rightDep = 0;
		if(leftChild != null)
			leftDep = depthCalculator(this.leftChild, 1);
		if(rightChild != null)
			rightDep = depthCalculator(this.rightChild, 1);
		return Math.max(leftDep, rightDep);
	}
	
	/**
	 * Private assist method for depth that is used to do the actual calculations.
	 * Travels recursively as far down as possible from the node. The method uses
	 * Math.max to choose the deepest way.
	 * 
	 * @param node		that it should climb down from.
	 * @param dep		which is the size of the depth.
	 * @return
	 */
	private int depthCalculator(Node<T> node, int dep) {
		int leftDep = dep;
		int rightDep = dep;
		if(node.leftChild != null)
			leftDep = depthCalculator(node.leftChild, leftDep+1);
		if(node.rightChild != null)
			rightDep = depthCalculator(node.rightChild, rightDep+1);
		
		return Math.max(leftDep, rightDep);
	}
	
	/**
	 * Package method for testing purposes. 
	 * Returns a stringrepresentation of the (sub)tree that the node is root in.
	 * This representation contains the data of the elements in a sorted order with comma 
	 * seperating them.
	 * 
	 * @return stringrepresentation of the (sub)tree that the node is root in.
	 */
	String printString() {
		String sb = "";
		sb += buildString(this, sb);
		return sb;
	}
	
	/**
	 * Package only method intended for testing only. 
	 * Assist method for {@link #printString()} which 
	 * recursively gathers all nodes. 
	 * 
	 * @param node 		that it should look from.
	 * @param sb		the current string.
	 * @return
	 */
	String buildString(Node<T> node, String string) {
		if(node.leftChild != null) {
			string = buildString(node.leftChild, string);
			string += ", ";
		}
		string += node.data.toString();
		if(node.rightChild != null) {
			string += ", ";
			string = buildString(node.rightChild, string);
		}
		
		return string;
	}
	
	@Override
	public String toString() {
		return data.toString();
	}

}
