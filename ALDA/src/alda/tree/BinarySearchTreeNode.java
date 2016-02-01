package alda.tree;

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
		return null;
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
		System.out.println(cmp);
		if(cmp < 0) {
			return search(node.left, data);
		}
		else if(cmp > 0) {
			return search(node.right, data);
		}
		else
			return node;
	}

	/**
	 * Storleken på det (sub)träd som noden utgör root i.
	 * 
	 * @return det totala antalet noder i det (sub)träd som noden utgör root i.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Det högsta djupet i det (sub)träd som noden utgör root i.
	 * 
	 * @return djupet.
	 */
	public int depth() {
		return -1;
	}

	/**
	 * Returnerar en strängrepresentation fär det (sub)träd som noden utgär root
	 * i. Denna representation bestär av elementens dataobjekt i sorterad
	 * ordning med ", " mellan elementen.
	 * 
	 * @return strängrepresentationen för det (sub)träd som noden utgör root i.
	 */
	public String toString() {
		return "";
	}
}