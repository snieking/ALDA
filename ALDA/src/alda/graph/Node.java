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
	
	public boolean addConnection(Node<T> other, int cost) {
		for(Edge<T> e : connections) {
			if(other.equals(e.getConnection())) {
				e.setCost(cost);
				return true;
			}
		}
	
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
		if(!this.checkConnection(other))
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
		
		@SuppressWarnings("unchecked") // Eftersom metoden returnar falskt ändå om det är fel class.
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
