package alda.tree;

import java.util.stream.IntStream;

import org.w3c.dom.Node;

/**
 * Denna klass representerar noderna i ett binärt säkträd utan dubletter.
 * 
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) De ändringar som är tillåtna är dock
 * begränsade av fäljande regler:
 * <ul>
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans.
 * <li>Ni Får lägga till fler metoder, dessa ska då vara privata.
 * </ul>
 * 
 * @author henrikbe
 * 
 * @param <T>
 */
public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	/**
	 * Lägger till en nod i det binära sökträdet. Om noden redan existerar så
	 * lämnas trädet oförändrat.
	 * 
	 * @param data
	 *            datat för noden som ska läggas till.
	 * @return true om en ny nod lades till trädet.
	 */
	public boolean add(T data) {
		int cmp = data.compareTo(this.data);
		if(cmp < 0) {
			if(left == null) {
				left = new BinarySearchTreeNode<T>(data);
				return true;
			} else
				return left.add(data);
		}
		else if(cmp > 0) {
			if(right == null) {
				right = new BinarySearchTreeNode<T>(data);
				return true;
			} else 
				return right.add(data);
		} else
			return false;
	}
	
	private void addLostBranch(BinarySearchTreeNode<T> branch, BinarySearchTreeNode<T> root) {
		if(branch.left != null)
			addLostBranch(branch.left, root);
		if(branch.right != null)
			addLostBranch(branch.right, root);
		
		root.add(branch.data);
	}
	
	String printData() {
		return data.toString();
	}

	/**
	 * Privat hjälpmetod som är till nytta vid borttag. Ni behöver inte
	 * skriva/utnyttja denna metod om ni inte vill.
	 * 
	 * @return det minsta elementet i det (sub)träd som noden utgör root i.
	 */
	
	private BinarySearchTreeNode<T> findMin(BinarySearchTreeNode<T> node) {
		if(node.left.left != null)
			return findMin(node.left);
		else
			return node;
	}
	
	private BinarySearchTreeNode<T> findNodeBeforeMin(BinarySearchTreeNode<T> node) {
		if(node.right != null) {
			if(node.right.left != null)
				return findMin(node.right);
			else
				return node;
		}
		return node;
	}

	/**
	 * Tar bort ett element ur trädet. Om elementet inte existerar så lämnas
	 * trädet oförändrat.
	 * 
	 * @param data
	 *            elementet som ska tas bort ur trädet.
	 * @return en referens till nodens subträd efter borttaget.
	 */
	public BinarySearchTreeNode<T> remove(T data) {
		BinarySearchTreeNode<T> theRoot = this;
		System.out.println("Root is: " + theRoot.printData());
//		System.out.println(theRoot);

		theRoot = checkRoot(theRoot, data);
		
		checkNormal(theRoot, data, theRoot);
//		findLeftNodeBefore(theRoot, data, theRoot);
//
//		findRightNodeBefore(theRoot, data, theRoot);
//		System.out.println("returing root");
//		System.out.println("Root after removes is: " + theRoot.printData());
//		System.out.println(theRoot);
		return theRoot;
	}
	
	private BinarySearchTreeNode<T> checkSubRoot(BinarySearchTreeNode<T> root, T data) {
		if(root.data == data) {
			System.out.println("Rätt data :-)");
			BinarySearchTreeNode<T> tempLeft = root.left;
			BinarySearchTreeNode<T> temp = findNodeBeforeMin(root);
			System.out.println("Root: " + root.data);
			System.out.println("Temp: " + temp.data);
			
			
			if(root.data == temp.data) {
				System.out.println("root.data == temp.data");
				BinarySearchTreeNode<T> tempRight = null;
				if(root.right != null)
					tempRight = root.right;
				
				BinarySearchTreeNode<T> newRoot;
				//newRoot = temp.right;
				if(temp.right != null) {
//					System.out.println("temp.right blire");
					newRoot = temp.right;
//					System.out.println("nya subrooten är: " + newRoot);
					if(tempLeft != null) {
//						System.out.println("lägger till tempLeft: " + tempLeft);
						newRoot.left = tempLeft;
					}
//					if(tempRight != null) {
//						System.out.println("lägger till tempRight: " + tempRight);
//						newRoot.right = tempRight;
//					}
				} else
					newRoot = temp.left;
//				System.out.println("Slutgiltiga subrooten: " + newRoot);
				return newRoot;
			}
			//if(root.left != null) {
				System.out.println("Här är jag");
				BinarySearchTreeNode<T> nodeBefore = findNodeBeforeMin(root);
				BinarySearchTreeNode<T> replacementRoot;
				if(nodeBefore.right != null) {
					replacementRoot = nodeBefore.right;
					nodeBefore.right = null;
				} else {
					replacementRoot = nodeBefore.left;
					nodeBefore.left = null;
				}
				
				replacementRoot.left = root.left;
				replacementRoot.right = root.right;
				return replacementRoot;
				
//			}
//			System.out.println("Här är jag nu...");
//			temp.left = tempLeft;
//			temp.right = root.right; // TODO: remove?
//			return temp;
		}
		
		return root;
	}
	
	private BinarySearchTreeNode<T> checkRoot(BinarySearchTreeNode<T> root, T data) {
		if(root.data == data) {
			System.out.println("Det är rätt data... [checkRoot]");
			BinarySearchTreeNode<T> tempLeft = root.left;
			BinarySearchTreeNode<T> temp = findNodeBeforeMin(root);
//			System.out.println("Vill replaca med: " + temp.data);
			
			if(root.data == temp.data) {
				System.out.println("root.data == temp.data");
				BinarySearchTreeNode<T> tempRight;
				if(root.right != null)
					tempRight = root.right.right;
				else
					tempRight = null;
				
				System.out.println();
				
				BinarySearchTreeNode<T> newRoot;
				if(temp.right != null) {
					newRoot = temp.right;
					if(tempLeft != null)
						newRoot.left = tempLeft;
					if(tempRight != null)
						newRoot.right = tempRight;
				} else
					newRoot = temp.left;
				
				return newRoot;
			}
			else {
				System.out.println("här i checkRoot");
				BinarySearchTreeNode<T> newRoot = temp.left;
				temp.left = null;
				BinarySearchTreeNode<T> tempRight = root.right;
				newRoot.left = tempLeft;
				newRoot.right = tempRight;
				return newRoot;
			}
		}
		
//		System.out.println("Not in root...");
		return root;
	}
	
	private BinarySearchTreeNode<T> checkNormal(BinarySearchTreeNode<T> node, T data, BinarySearchTreeNode<T> root) {
		int cmp;
//		System.out.println("nytt varv i checknormal");
		cmp = data.compareTo(node.data);
		System.out.println("cmp är nu = " + cmp);
//		if(cmp < 0) {
			if(node.left != null || node.right != null) {
				System.out.println("Ska kolla LEFT :: Current node: " + node.data);
				cmp = data.compareTo(node.data);
				if(node.right != null && node.right.data == data) {
					System.out.println("Right cmp");
					cmp = data.compareTo(node.right.data);
				}
				else if(node.left != null && node.left.data == data) {
					System.out.println("Left cmp");
					cmp = data.compareTo(node.left.data);
				}
				System.out.println("cmp = " + cmp + " :: left = " + node.left + " :: right = " + node.right);
				if(cmp < 0 && node.left != null) {
					System.out.println("Går left");
					return checkNormal(node.left, data, root);
				} else if(cmp > 0 && node.right != null) {
					System.out.println("Går right!!");
//					if(node.right != null) // TODO: Remove?
						return checkNormal(node.right, data, root);
				} else if(cmp == 0) {
					System.out.println("[LEFT] Det matchar! Står just nu på: " + node.data);
					BinarySearchTreeNode<T> newSubRoot;
					if(node.left != null && node.left.data == data) {
						System.out.println("In left...");
						newSubRoot = checkSubRoot(node.left, data);
						node.left = newSubRoot;
					}
					else {
						System.out.println("In right...");
						newSubRoot = checkSubRoot(node.right, data);
						node.right = newSubRoot;
						System.out.println(node.right);
					}

					
					return root;
				}
				
				
			} 
//		} else if (cmp > 0) {
//			if(node.right != null) {
//				System.out.println("Ska kolla RIGHT :: Current node: " + node.data);
//				cmp = data.compareTo(node.right.data);
//				if(cmp < 0 && node.left != null) {
//					System.out.println("Går left");
//					return checkNormal(node.left, data, root);
//				} else if(cmp > 0 && node.right != null) {
////					if(node.right != null) // TODO: Remove?
//					System.out.println("Går right");
//						return checkNormal(node.right, data, root);
//				} else if(cmp == 0) {
//					System.out.println("[RIGHT] Det matchar!");
//					BinarySearchTreeNode<T> newSubRoot = checkSubRoot(node.right, data);
//					node.right = newSubRoot;
//					
//					return root;
//				}
//			}
//		}
		
		System.out.println("Fail... Returnerar: " + node.data);
		return node;
	}

	/**
	 * Kontrollerar om ett givet element finns i det (sub)träd som noden utgör
	 * root i.
	 * 
	 * @param data
	 *            det sökta elementet.
	 * @return true om det sökta elementet finns i det (sub)träd som noden utgör
	 *         root i.
	 */
	public boolean contains(T data) {
		if(search(this, data).data == data)
			return true;
		else
			return false;
	}
	
	/**
	 * En hjälpmetod som används rekursivt vid contains för att leta upp elementet.
	 * 
	 * @param node
	 *            nästa node den ska leta i
	 * @return 
	 */
	private BinarySearchTreeNode<T> search(BinarySearchTreeNode<T> node, T data) {
		int cmp = data.compareTo(node.data);
		if(cmp < 0) {
			if(node.left != null) // TODO: Remove??
				return search(node.left, data);
		}
		else if(cmp > 0) {
			if(node.right != null) // TODO: Remove??
				return search(node.right, data);
		}
		
		return node;
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
	
	private int sizeCalculator(BinarySearchTreeNode<T> node, int siz) {
		int size = siz+1;
		if(node.left != null)
			size = sizeCalculator(node.left, size);
		if(node.right != null)
			size = sizeCalculator(node.right, size);
		return size;
	}

	/**
	 * Det högsta djupet i det (sub)träd som noden utgör root i.
	 * 
	 * @return djupet.
	 */
	public int depth() {
		int leftDep = 0, rightDep = 0;
		if(left != null)
			leftDep = depthCalculator(this.left, 1);
		if(right != null)
			rightDep = depthCalculator(this.right, 1);
		return Math.max(leftDep, rightDep);
	}
	
	private int depthCalculator(BinarySearchTreeNode<T> node, int dep) {
//		System.out.println("Current node is: " + node.data + " : depth = " + depth);
		int leftDep = dep;
		int rightDep = dep;
		if(node.left != null)
			leftDep = depthCalculator(node.left, leftDep+1);
		if(node.right != null)
			rightDep = depthCalculator(node.right, rightDep+1);
		
//		System.out.println("Left depth: " + leftDep);
//		System.out.println("Right Depth: " + rightDep);
		
		return Math.max(leftDep, rightDep);

		
	}

	/**
	 * Returnerar en strängrepresentation fär det (sub)träd som noden utgär root
	 * i. Denna representation bestär av elementens dataobjekt i sorterad
	 * ordning med ", " mellan elementen.
	 * 
	 * @return strängrepresentationen för det (sub)träd som noden utgör root i.
	 */
	public String toString() {
		String sb = "";
		sb += buildString(this, sb);
		return sb;
	}
	
	
	private String buildString(BinarySearchTreeNode<T> node, String sb) {
		if(node.left != null) {
			sb = buildString(node.left, sb);
			sb += ", ";
		}
		
		sb += node.data.toString();

		

		if(node.right != null) {
			sb += ", ";
			sb = buildString(node.right, sb);
		}
		
		return sb;
	}
}