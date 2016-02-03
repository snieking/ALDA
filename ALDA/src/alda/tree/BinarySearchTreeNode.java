package alda.tree;

/**
 * Vår implementation av ett binärt sökträd,
 * där allt görs rekursivt.
 * 
 * @author Viktor Plane - viktorplane.sonie@gmail.com
 * @author Olof Hofstedt - olof.hofstedt93@gmail.com
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
	
	/**
	 * Privat rekursiv hjälpmetod som används för att hitta noden innan det lägsta värdet,
	 * som checkRoot använder som hjälp när den ska flytta om i trädet.
	 * 
	 * @param node
	 *             som den ska leta vidare i.
	 * @return den lägsta noden.
	 */
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
	 *             elementet som ska tas bort ur trädet.
	 * @return en referens till nodens subträd med eller utan borttagning.
	 */
	public BinarySearchTreeNode<T> remove(T data) {
		BinarySearchTreeNode<T> theRoot = this;
		theRoot = checkRoot(this, data);
		checkNormal(theRoot, data);
		return theRoot;
	}
	
	/**
	 * Privat rekursiv hjälpmetod som hittar det mest högra värdet under noden.
	 * 
	 * @param node 
	 *             som den ska leta under.
	 * @return mest högra noden.
	 */
	private BinarySearchTreeNode<T> findMostRight(BinarySearchTreeNode<T> node) {
		if(node.right != null)
			return findMostRight(node.right);
		else
			return node;
	}
	
	/**
	 * Privat hjälpmetod för remove som kollar och jämför en root/subroot och byter platser ifall ändring sker.
	 * 
	 * @param root 
	 *             noden som den ska kolla.
	 * @param data 
	 *             som noden ska jämföras med.
	 * @return den nya rooten/subrooten.
	 */
	private BinarySearchTreeNode<T> checkRoot(BinarySearchTreeNode<T> root, T data) {
		if(root.data == data) {
			BinarySearchTreeNode<T> tempLeft = root.left;
			BinarySearchTreeNode<T> temp = findNodeBeforeMin(root);

			if(root.data == temp.data) {
				BinarySearchTreeNode<T> tempRight;
				if(root.right != null)
					tempRight = root.right.right;
				else
					tempRight = null;
				
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
				/* Sätter newRoot till det lägsta värdet  på höger sidan */
				BinarySearchTreeNode<T> newRoot = temp.left; 

				/* Tar bort kopplingen till nya rooten */
				temp.left = null;
				
				/* Letar upp det högsta värdet från nya rooten och lägger till värdet som
					var innan. */ 
				findMostRight(newRoot).right = root.right;
				
				/* Sätter nya rootens left till vänster av ursprungliga root */
				newRoot.left = tempLeft;
				return newRoot;
			}
		}
		
		return root;
	}
	
	/**
	 * Privat rekursiv hjälpmetod för remove som används för att kolla en vanlig nod, 
	 * d.v.s. inte root noden, och den kallar på checkRoot för att göra ändringar på 
	 * subroots ifall data matchar.
	 * 
	 * @param node 
	 *             som den börjar att titta på. 
	 * @param data 
	 * 			   att leta efter.
	 */
	private void checkNormal(BinarySearchTreeNode<T> node, T data) {
		int cmp;
		if(node != null) {
			cmp = data.compareTo(node.data);

			if(node.left != null || node.right != null) {
				cmp = data.compareTo(node.data);
				if(node.right != null && node.right.data == data) {
					cmp = data.compareTo(node.right.data);
				}
				else if(node.left != null && node.left.data == data) {
					cmp = data.compareTo(node.left.data);
				}
				if(cmp < 0 && node.left != null) {
					checkNormal(node.left, data); //
				} else if(cmp > 0 && node.right != null) {
						checkNormal(node.right, data); //
				} else if(cmp == 0) {
					BinarySearchTreeNode<T> newSubRoot;
					if(node.left != null && node.left.data == data) {
						newSubRoot = checkRoot(node.left, data);
						node.left = newSubRoot;
					}
					else {
						newSubRoot = checkRoot(node.right, data);
						node.right = newSubRoot;
					}
				}
			}
		}
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
	 * En privat hjälpmetod som används rekursivt vid contains för att leta upp elementet.
	 * 
	 * @param node
	 *            nästa node den ska leta i
	 * @return noden som har samma data som contains elementet ifall det finns, annars rooten.
	 */
	private BinarySearchTreeNode<T> search(BinarySearchTreeNode<T> node, T data) {
		int cmp = data.compareTo(node.data);
		if(cmp < 0) {
			if(node.left != null)
				return search(node.left, data);
		}
		else if(cmp > 0) {
			if(node.right != null)
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
	
	/**
	 * Privat rekursiv hjälpmetod för size som räknar ut storleken.
	 * @param node
	 * 			   som den börjar räkna ifrån.
	 * @param siz
	 *            storleken som en int.
	 * @return
	 */
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
	 * Metoden använder sig av Math.max för att välja den djupaste vägen.
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
	
	/**
	 * Privat hjälpmetod för depth som används för att göra den faktiska uträkningen.
	 * Går rekursivt neråt så långt det går från den ursprungliga noden. Metoden använder
	 * sig av Math.max för att välja den djupaste vägen.
	 * 
	 * @param node
	 *             noden den ska klättra neråt från.
	 * @param dep
	 * 			  storleken på djupet som en int.
	 * @return
	 */
	private int depthCalculator(BinarySearchTreeNode<T> node, int dep) {
		int leftDep = dep;
		int rightDep = dep;
		if(node.left != null)
			leftDep = depthCalculator(node.left, leftDep+1);
		if(node.right != null)
			rightDep = depthCalculator(node.right, rightDep+1);
		
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
	
	/**
	 * Privat hjälpmetod för toString som rekursivt tar fram alla följande noder.
	 * @param node
	 * 			   som den kollar från.
	 * @param sb
	 * 			 vilket är strängen.
	 * @return
	 */
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