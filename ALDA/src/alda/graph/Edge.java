package alda.graph;


public class Edge<T> implements Comparable<Edge<T>>{
	private Node<T> connection;
	private Node<T> connectedFrom;
	private int cost;
	
	public Edge(Node<T> connection, int cost, Node<T>connectedFrom) {
		this.connection = connection;
		this.cost = cost;
		this.connectedFrom = connectedFrom;
	}
	
	public Node<T> getConnectedFrom() {
		return connectedFrom;
	}
	
	public Node<T> getConnection() {
		return connection;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public int compareTo(Edge<T> o) {
		return this.cost - o.cost;
	}
}
