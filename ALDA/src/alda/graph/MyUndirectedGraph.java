package alda.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	private int numberOfNodes = 0;
	private int numberOfEdges = 0;
	private HashMap<T, Node<T>> graph = new HashMap<>();
	ArrayList<T> shortest = new ArrayList<>();
	
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
			numberOfNodes++;
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
			numberOfEdges++;
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
	
	private void clearNodes() {
		for(Node<T> node : graph.values())
			node.visited = false;
	}

	@Override
	public List depthFirstSearch(Object start, Object end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List breadthFirstSearch(T start, T end) {
		shortest.clear();
		LinkedHashSet<T> bfsNodes = new LinkedHashSet<>();
		ArrayList<T> path = new ArrayList<>();
		
		Node<T> n = graph.get(start);
		Node<T> goal = graph.get(end);
		
		if(start.equals(end)) {
			path.add(start);
			return path;
		}
		
		
		Queue<Node<T>> queue = new LinkedList<>();
		queue.offer(n);
		
		n.checkAndMark();
		bfsNodes.add(n.data); // Lägger till första noden.
		
		while(!queue.isEmpty()) {
			n = queue.poll(); // Sätter n till första platsen i kön.
//			n.checkAndMark();
			for(Edge edge : n.connections) {
				Node<T> neighbour = edge.getConnection();
				if(neighbour.checkAndMark()) {
					// TODO: Remove print
//					System.out.println("Stod på (" + n + "), queuar (" + edge.getConnection() + ")");
//					queue.offer(edge.getConnection());
					path.add(neighbour.data);
					path.add(n.data);
					
					if(neighbour.data.equals(end)) {
						return preparePath(start, end, path);
					} else {
							queue.offer(neighbour);
					}
				}
			}
		}
		System.out.println(bfsNodes);
		clearNodes();
		return null;
//		return new ArrayList<T>(bfsNodes);
	}
	
	private List preparePath(T start, T end, List<T> path) {
		
		// Tar reda på vad slutnoden direkt kom ifrån.
		int index = path.indexOf(end);
		T begin = path.get(index + 1);
		// TODO: Remove print
//		System.out.println("Begin är: " + begin);
		
		// Lägger till slutnoden till kortaste vägen.
		shortest.add(0, end);
		
		if(begin.equals(start)) {
			// Ursprungliga noden är funnen.
			shortest.add(0, start);
			// TODO: Remove print
//			System.out.println(shortest);
			return shortest;
		} else {
			// Rekursiv metod för att leta vart slutnåden kom ifrån.
			// TODO: Remove print
//			System.out.println("Rekursivt anrop");
			return preparePath(start, begin, path);
		}
	}

	@Override
	public UndirectedGraph minimumSpanningTree() {
		// TODO Auto-generated method stub
		return null;
	}

}
