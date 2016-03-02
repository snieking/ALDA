package alda.tree_project;

import java.util.List;

import alda.tree.BinarySearchTreeNode;

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
	public Node(T data) {
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
	public boolean add(T data) {
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
	 * Removes an element from the tree. 
	 * If the element doesn't exist, nothing will happen.
	 * @param data
	 * @return
	 */
	public Node<T> remove(T data) {
		Node<T> theRoot = this;
		theRoot = checkRoot(this, data);
		checkNormal(theRoot, data);
		return theRoot;
	}
	
	/**
	 * Privat rekursiv hjälpmetod för remove som används för att kolla en vanlig nod, 
	 * d.v.s. inte root noden, och den kallar på checkRoot för att göra ändringar på 
	 * subroots ifall data matchar.
	 * 
	 * @param node 		som den börjar att titta på. 
	 * @param data 		att leta efter.
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
	 * Privat hjälpmetod för remove som kollar och jämför en root/subroot och byter platser ifall ändring sker.
	 * 
	 * @param root 		som utgör noden som den ska kolla.
	 * @param data 		som noden ska jämföras med.
	 * @return newRoot 	vilket är den nya rooten/subrooten.
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
				/* Sätter newRoot till det lägsta värdet  på höger sidan */
				Node<T> newRoot = temp.leftChild; 

				/* Tar bort kopplingen till nya rooten */
				temp.leftChild = null;
				
				/* Letar upp det högsta värdet från nya rooten och lägger till värdet som
					var innan. */ 
				findMax(newRoot).rightChild = root.rightChild;
				
				/* Sätter nya rootens left till vänster av ursprungliga root */
				newRoot.leftChild = tempLeft;
				return newRoot;
			}
		}
		
		return root;
	}
	
	/**
	 * Privat hjälpmetod som är till nytta vid borttag.
	 * Letar upp det lägsta elementet i sub(trädet) som noden utgör root i.
	 * Vilket är tänkt att ta den borttagna rootens plats.
	 * 
	 * @param node 		vilket utgör rooten i (sub)trädet.
	 * @return det minsta elementet i det (sub)träd som noden utgör root i.
	 */
	private Node<T> findMin(Node<T> node) {
		if(node.leftChild.leftChild != null)
			return findMin(node.leftChild);
		else
			return node;
	}
	
	/**
	 * Privat rekursiv hjälpmetod som hittar det mest högra värdet under noden.
	 * Användbart vid borttagning.
	 * 
	 * @param node 		som den ska leta under.
	 * @return node		vilket är den mest högra.
	 */
	private Node<T> findMax(Node<T> node) {
		if(node.rightChild != null)
			return findMax(node.rightChild);
		else
			return node;
	}
	
	/**
	 * Privat rekursiv hjälpmetod som används för att hitta noden innan det lägsta värdet,
	 * som checkRoot använder som hjälp när den ska flytta om i trädet.
	 * 
	 * @param node 		som den ska leta vidare i.
	 * @return den lägsta noden.
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
	
	public boolean contains(T data) {
		return false; // TODO
	}
	
	/**
	 * Storleken på det (sub)träd som noden utgör root i.
	 * 
	 * @return det totala antalet noder i det (sub)träd som noden utgör root i.
	 */
	public int size() {
		int size = 0;
		size += sizeCalculator(this, size);
		return size;
	}
	
	/**
	 * Privat rekursiv hjälpmetod för size som räknar ut storleken.
	 * 
	 * @param node 		som den börjar räkna ifrån.
	 * @param size		räknaren som en int.
	 * @return
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
	 * Det högsta djupet i det (sub)träd som noden utgör root i.
	 * Metoden använder sig av Math.max för att välja den djupaste vägen.
	 * 
	 * @return djupet.
	 */
	public int depth() {
		int leftDep = 0, rightDep = 0;
		if(leftChild != null)
			leftDep = depthCalculator(this.leftChild, 1);
		if(rightChild != null)
			rightDep = depthCalculator(this.rightChild, 1);
		return Math.max(leftDep, rightDep);
	}
	
	/**
	 * Privat hjälpmetod för depth som används för att göra den faktiska uträkningen.
	 * Går rekursivt neråt så långt det går från den ursprungliga noden. Metoden använder
	 * sig av Math.max för att välja den djupaste vägen.
	 * 
	 * @param node		noden den ska klättra neråt från.
	 * @param dep		storleken på djupet som en int.
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
	 * Returnerar en strängrepresentation fär det (sub)träd som noden utgär root
	 * i. Denna representation bestär av elementens dataobjekt i sorterad
	 * ordning med ", " mellan elementen.
	 * 
	 * @return strängrepresentationen för det (sub)träd som noden utgör root i.
	 */
	public String printString() {
		String sb = "";
		sb += buildString(this, sb);
		return sb;
	}
	
	/**
	 * Privat hjälpmetod för toString som rekursivt tar fram alla följande noder.
	 * @param node 		som den kollar från.
	 * @param sb		den nuvarande strängen.
	 * @return
	 */
	private String buildString(Node<T> node, String string) {
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
