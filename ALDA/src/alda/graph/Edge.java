package alda.graph;


public class Edge<T> implements Comparable<Edge<T>>{
	private Node<T> connection;
	private int cost;
	
	public Edge(Node<T> connection, int cost) {
		this.connection = connection;
		this.cost = cost;
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
