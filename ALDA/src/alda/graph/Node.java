package alda.graph;

import java.util.ArrayList;


public class Node<T> {
	private T data;
	boolean visited = false;
	ArrayList<Edge<T>> connections = new ArrayList<>();
	
	public Node(T data) {
		this.data = data;
	}
	
	public boolean checkAndMark() {
		if(visited == true)
			return false;
		else {
			visited = true;
			return true;
		}
		
	}
	
	/**
	 * Finds the least costly edge from the node. Is supposed to be used to assist finding the minimal spanning tree.
	 * @return least costly edge from the node.
	 */
	public Edge<T> leastCostlyEdge() {
		Edge<T> leastCostly = connections.get(1);
		leastCostly.getConnection().visited = true;
		for(int i = 0; i<connections.size(); i++) {
			Edge<T> next = connections.get(i);
			if(leastCostly.getCost() > next.getCost() && !next.getConnection().visited) {
				System.out.println("Händer detta?");
				leastCostly = next;
			}
			if(!next.getConnection().visited) {
				next.getConnection().visited = true;	
				System.out.println("Markerade: " + next.getConnection());
			}
		}

		System.out.println("Node är: (" + data + ")" + " - Least costly: (" + leastCostly.getConnection() + ")");
		return leastCostly;
	}
	
	public boolean hasUnvisitedEdge() {
		boolean unvisited = false;
		for(int i=0; i<connections.size(); i++){
			if(!connections.get(i).getConnection().visited) {
				unvisited = true;
			}
		}
		
		return unvisited;
	}
	
	public boolean addConnection(Node<T> other, int cost) {
		for(Edge<T> e : connections) {
			if(other.equals(e.getConnection())) {
				e.setCost(cost);
//				System.out.println("[1] Connected: " + this.data + " -> " + other.data + " with cost (" + cost +")");
				return true; //TODO: eller sant eller falskt?
			}
		}
	
//		System.out.println("[2] Connected: " + this.data + " -> " + other.data + " with cost (" + cost +")" + " [" + added + "]");
		return connections.add(new Edge<T>(other, cost, this));
	}
	
	public boolean checkConnection(Node<T> other) {
		for(Edge<T> e : connections) {
			if(other.equals(e.getConnection()))
				return true;
		}
		
		return false;
	}
	
	public int getCost(Node<T> other) {
		if(!this.checkConnection(other)) //TODO: Redundant?
			return -1;
		
		for(Edge<T> e : connections) {
			if(other.equals(e.getConnection()))
				return e.getCost();
		}
		
		return -1;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		@SuppressWarnings("unchecked") // TODO: Är det OK?
		Node<T> other = (Node<T>) obj;

		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
	
	public boolean allNeighboursVisited() {
		boolean all = true;
		for(Edge<T> e : connections) {
			if(e.getConnection().visited == false) {
				all = false;
				break; // Behöver inte fortsätta
			}
		}
		
		return all;
	}
	
	public T getData() {
		return data;
	}
	
	public String toString() {
		return data.toString();
	}
}
