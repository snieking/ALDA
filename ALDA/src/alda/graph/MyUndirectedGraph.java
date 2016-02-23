package alda.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
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
	public List<T> depthFirstSearch(T start, T end) {
		Node<T> n = graph.get(start);
		
		Stack<Node<T>> s = new Stack<>();
		ArrayList<T> path = new ArrayList<>();
		
		if(start.equals(end)) {
			path.add(start);
			return path;
		}
		
		s.push(n);
		
		n.checkAndMark();
		
		while(!s.isEmpty()) {
			if(n.getData().equals(end)) {
				ArrayList<T> data = new ArrayList<>();
				for(int i=0; i<s.size(); i++) {
					data.add(s.pop().getData());
				}
				return preparePath(start, end, path);
			}
			if(n.allNeighboursVisited()) {
				// TODO: Remove print
//				System.out.println("(" + n.data + ") alla grannar var besökta redan. Ny 'n' är (" + s.peek() + ") efter pop()");
				n = s.pop();
			}
			else {
				for(Edge<T> e : n.connections) {
					Node<T> m = e.getConnection();
					
					if(!m.visited) {
						// TODO: Remove print
//						System.out.println("Letar connection åt (" + n.data + ")" + " hittade (" + m.data + ")");
						path.add(m.getData());
						path.add(n.getData());
						s.push(m);
						m.checkAndMark();
						break; // Behöver inte fortsätta
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		shortest.clear();

		ArrayList<T> path = new ArrayList<>();
		
		Node<T> n = graph.get(start);
		
		if(start.equals(end)) {
			path.add(start);
			return path;
		}
		
		
		Queue<Node<T>> queue = new LinkedList<>();
		queue.offer(n);
		
		n.checkAndMark();

		
		while(!queue.isEmpty()) {
			n = queue.poll(); // Sätter n till första platsen i kön.
//			n.checkAndMark();
			for(Edge<T> edge : n.connections) {
				Node<T> neighbour = edge.getConnection();
				if(neighbour.checkAndMark()) {
					// TODO: Remove print
//					System.out.println("Stod på (" + n + "), queuar (" + edge.getConnection() + ")");
//					queue.offer(edge.getConnection());
					path.add(neighbour.getData());
					path.add(n.getData());
					
					if(neighbour.getData().equals(end)) {
						return preparePath(start, end, path);
					} else {
							queue.offer(neighbour);
					}
				}
			}
		}

		clearNodes();
		return null;
//		return new ArrayList<T>(bfsNodes);
	}
	
	private List<T> preparePath(T start, T end, List<T> path) {
		
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
	public UndirectedGraph<T> minimumSpanningTree() {
		Entry<T, Node<T>> entry=graph.entrySet().iterator().next();
		HashMap<T, Node<T>> addedNodes = new HashMap<>();
		T first = entry.getKey();
		
		// Börja med ett tomt träd och en tom prioritetskö.
		UndirectedGraph<T> miniTree = new MyUndirectedGraph<T>();
		PriorityQueue<Edge<T>> pq = new PriorityQueue<Edge<T>>();
		
		// Lägg till första noden i trädet.
		Node<T> node = new Node<T>(first);
		miniTree.add(first);
		addedNodes.put(first, node);
		node.visited = true;
		graph.get(first).visited = true;
		
		System.out.println("Står på nod: (" + first + ")");
		// Lägg till alla bågar som utgår från första i prioritetskön.
		for(Edge<T> e : graph.get(first).connections) {
			System.out.println("Queueing: " + e.getConnection() + " with cost = " + e.getCost());
			pq.add(e);
		}
		
		// Ta ut ur PK den båge som har lägst vikt.
		Edge<T> edge = pq.poll();
		
		while(miniTree.getNumberOfNodes() < getNumberOfNodes()) {
			// Om noden m inte redan finns i vårat träd. Lägg både noden och bågen.
			System.out.println("loopie");
			Node<T> next = edge.getConnection();
			if(!next.visited) {
				if(!node.equals(next)) {
					System.out.println("Adding (" + next.getData() + ")");
					miniTree.add(next.getData());
					addedNodes.put(next.getData(), new Node<T>(next.getData()));
					miniTree.connect(edge.getConnectedFrom().getData(), next.getData(), edge.getCost());
					System.out.println("Connected: (" + edge.getConnectedFrom().getData() + ") with (" + next.getData() + "), cost = " + edge.getCost());
				}
				next.visited = true;
				node = next;
				edge = pq.poll();
				System.out.println("no.. here:)");
				System.out.println("next innan getConnection() = (" + next.getData() + ")");
			} else {
				System.out.println("here:)");
				edge = pq.poll();
			}
			
			for(Edge<T> e : graph.get(next.getData()).connections) {
				if(!e.getConnection().visited) {
					System.out.println("Queueing: " + e.getConnection() + " with cost = " + e.getCost());
					pq.add(e);
				}
			}
			
			System.out.println("Graph size: " + miniTree.getNumberOfNodes());
		}
		
		
		return (UndirectedGraph<T>) miniTree;

	}

}
