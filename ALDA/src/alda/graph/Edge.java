package alda.graph;

/**
 * A class which stores the connection of nodes and the cost, 
 * which basically is an edge.
 * 
 * @author Viktor Plane (vipl4364) - viktorplane.sonie@gmail.com
 * @author Olof Hofstedt (olho8226) - olof.hofstedt93@gmail.com
 */
public class Edge<T> implements Comparable<Edge<T>>{
	private Node<T> connection;
	private Node<T> connectedFrom;
	private int cost;
	
	/**
	 * Constructor for creating an edge.
	 * 
	 * @param connection which is the node to connect to.
	 * @param cost which is the cost of the connection.
	 * @param connectedFrom which is the node that is connected to the connected
	 * node.
	 */
	public Edge(Node<T> connection, int cost, Node<T>connectedFrom) {
		this.connection = connection;
		this.cost = cost;
		this.connectedFrom = connectedFrom;
	}
	
	/**
	 * Returns the node that has the connection.
	 * 
	 * @return node
	 */
	public Node<T> getConnectedFrom() {
		return connectedFrom;
	}
	
	/**
	 * Returns which node that it is connected to.
	 * 
	 * @return node
	 */
	public Node<T> getConnection() {
		return connection;
	}
	
	/**
	 * Returns the cost of the connection.
	 * 
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Method for change the cost of the connection.
	 * 
	 * @param cost which is the new cost value.
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public int compareTo(Edge<T> o) {
		return this.cost - o.cost;
	}
}
