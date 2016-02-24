package alda.graph;

import java.util.ArrayList;

/**
 * Acts like a wrapper class around the data stored 
 * in the graph in our implementation.
 * 
 * @author Viktor Plane (vipl4364) - viktorplane.sonie@gmail.com
 * @author Olof Hofstedt (olho8226) - olof.hofstedt93@gmail.com
 */
public class Node<T> {
	private T data;
	boolean visited = false;
	ArrayList<Edge<T>> connections = new ArrayList<>();
	
	/**
	 * Constructs a node with the data given.
	 * 
	 * @param data to wrap in the node.
	 */
	public Node(T data) {
		this.data = data;
	}
	
	/**
	 * Method used to check if a node has been visited, 
	 * returns <code>false</code> if it has already been visited. 
	 * <code>true</code> if not, and then marks it visited.
	 * 
	 * @return state of the node.
	 */
	public boolean checkAndMark() {
		if(visited == true)
			return false;
		else {
			visited = true;
			return true;
		}
		
	}
	
	/**
	 * Connects the node to another one, and assigns the cost of it.
	 * 
	 * @param other node to link the node to.
	 * @param cost to assign the connection.
	 * @return <code>true</code> if added a new connection or, overrid an old cost. 
	 * Otherwise <code>false</code> if couldn't add or update.
	 */
	public boolean addConnection(Node<T> other, int cost) {
		for(Edge<T> e : connections) {
			if(other.equals(e.getConnection())) {
				e.setCost(cost);
				return true;
			}
		}
	
		return connections.add(new Edge<T>(other, cost, this));
	}
	
	/**
	 * Checks if the node is connected another one.
	 * 
	 * @param other node to check.
	 * @return <code>true</code> or <code>false</code> 
	 * depending on if they are connected or not.
	 */
	public boolean checkConnection(Node<T> other) {
		for(Edge<T> e : connections) {
			if(other.equals(e.getConnection()))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Method to get the cost of the connection to another node.
	 * @param other node to check the cost of connection with.
	 * @return <code>-1</code> if it's not connected, otherwise the cost.
	 */
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
	
	/**
	 * Method to check if all neighbours(connections) 
	 * of a node, has been visited.
	 * 
	 * @return <code>true</code> if all has been visited, otherwise <code>false</code>.
	 */
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
	
	/**
	 * Returns the data of the node, which it serves as a wrapper around.
	 * 
	 * @return data of the node.
	 */
	public T getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
}
