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

	/**
	 * Privat hjälpmetod som är till nytta vid borttag. Ni behöver inte
	 * skriva/utnyttja denna metod om ni inte vill.
	 * 
	 * @return det minsta elementet i det (sub)träd som noden utgör root i.
	 */
	private T findMin() {
		return null;
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

		theRoot = checkRoot(theRoot, data);

		findLeftNodeBefore(theRoot, data, this);

		findRightNodeBefore(theRoot, data, this);
		return theRoot;
	}
	
	private BinarySearchTreeNode<T> checkRoot(BinarySearchTreeNode<T> root, T data) {
//		System.out.println("Current node: " + root.data + " :: Searching for: " + data);
		if(root.data == data) {
			if(root.left != null) {
				System.out.println("[ROOT] Removing: " + data);
				BinarySearchTreeNode<T> rootRight = root.right;
				root = root.left;
				root.addLostBranch(rootRight, root);
			}
			else if(root.right != null) {
				root = root.right;
			}
		}
		
		return root;
	}
	
	private BinarySearchTreeNode<T> findLeftNodeBefore(BinarySearchTreeNode<T> node, T data, BinarySearchTreeNode<T> root) {
//		System.out.println("Current node: " + node.data + " :: Searching for: " + data);
		int cmp;
		if(node.left != null) {
			cmp = data.compareTo(node.left.data);
			if(cmp < 0) {
				return findLeftNodeBefore(node.left, data, root);
			}else if(cmp > 0) {
				if(node.right != null) // TODO: Remove??
					return findLeftNodeBefore(node.right, data, root);
			}else if(cmp == 0) {
				System.out.println("[LEFT] Next is: " + node.left.data + " :: found a match");
				BinarySearchTreeNode<T> tempRight = node.left.right;
				BinarySearchTreeNode<T> toBeRemoved = node.left;
				System.out.println("[LEFT] Removing: " + toBeRemoved.data);
				node.left = node.left.left;
				
				if(tempRight != null)
					root.addLostBranch(tempRight, root);

				return root;
			}
		}
		

		
		return root;
	}
	
	private BinarySearchTreeNode<T> findRightNodeBefore(BinarySearchTreeNode<T> node, T data, BinarySearchTreeNode<T> root) {
//		System.out.println("Current node: " + node.data + " :: Searching for: " + data);
		int cmp;
		
		

		if(node.right != null) {
			cmp = data.compareTo(node.right.data);
			if(cmp < 0 && node.left != null)
				return findRightNodeBefore(node.left, data, root);
			else if(cmp > 0 && node.right != null)
				return findRightNodeBefore(node.right, data, root);
			else if(cmp == 0) {
				System.out.println("[RIGHT] Next is: " + node.right.data + " :: found a match");
				BinarySearchTreeNode<T> tempLeft = node.right.left;
				BinarySearchTreeNode<T> toBeRemoved = node.right;
				System.out.println("[RIGHT] Removing: " + toBeRemoved.data);
				node.right = node.right.right;
				
//				if(tempLeft != null && node.right != null)
//					node.right.left = tempLeft;
				if(tempLeft != null)
					root.addLostBranch(tempLeft, root);
				
				return root;
			}
		}
		
		return root;
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
//		System.out.println("Current node is: " + node.data + " : the size = " + size);
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