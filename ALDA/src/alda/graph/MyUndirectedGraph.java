package alda.graph;

import java.util.HashMap;
import java.util.List;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	
	private class Node<T> {
		T data;
		HashMap<Node<T>, Integer> adj = new HashMap<>();
		
		public Node(T data) {
			this.data = data;
		}
		
		public boolean addConnection(Node<T> other, int cost) {
			if(adj.containsKey(other)) {
				adj.put(other, cost);
				return false;
			} else {
				adj.put(other, cost);
				return true;
			}
		}
		
		public boolean checkConnection(Node<T> other) {
			return adj.containsKey(other);
		}
		
		public int getCost(Node<T> other) {
			if(!this.checkConnection(other)) {
				System.out.println("Fanns ej");
				return -1;
			}
			return adj.get(other);
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
			Node<T> other = (Node<T>) obj;

			if (data == null) {
				if (other.data != null)
					return false;
			} else if (!data.equals(other.data))
				return false;
			return true;
		}
		
	}
	
	private int numberOfNodes = 0;
	private int numberOfEdges = 0;
	private HashMap<T, Node<T>> graph = new HashMap<>();
	
	@Override
	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	@Override
	public int getNumberOfEdges() {
		return numberOfEdges;
	}

	@Override
	public boolean add(T newNode) {
		if(graph.containsKey(newNode))
			return false;
		else {
			Node<T> node = new Node<T>(newNode);
			graph.put(newNode, node);
			return true;
		}
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		if(cost < 1)
			return false;
		
		Node<T> n1 = graph.get(node1);
		Node<T> n2 = graph.get(node2);
		
		if(n1 != null && n2 != null) {
			n1.addConnection(n2, cost);
			n2.addConnection(n1, cost);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		Node<T> n1 = graph.get(node1);
		Node<T> n2 = graph.get(node2);
		
		return n1.checkConnection(n2);
	}

	@Override
	public int getCost(T node1, T node2) {
		Node<T> n1 = graph.get(node1);
		Node<T> n2 = graph.get(node2);
		if(n1 == null || n2 == null)
			return -1;
		return n1.getCost(n2);
	}

	@Override
	public List depthFirstSearch(Object start, Object end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List breadthFirstSearch(Object start, Object end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UndirectedGraph minimumSpanningTree() {
		// TODO Auto-generated method stub
		return null;
	}

}
