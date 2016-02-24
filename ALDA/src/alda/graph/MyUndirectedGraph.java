package alda.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * Implements a Graph.
 * 
 * @author Viktor Plane (vipl4364) - viktorplane.sonie@gmail.com
 * @author Olof Hofstedt (olho8226) - olof.hofstedt93@gmail.com
 */
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
	
	/**
	 * Privat metod för att återställa alla noder till obesökta.
	 */
	private void clearNodes() {
		for(Node<T> node : graph.values())
			node.visited = false;
	}

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		shortest.clear();
		clearNodes();
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
				return gatherPath(start, end, path);
			}
			if(n.allNeighboursVisited())
				n = s.pop();
			else {
				for(Edge<T> e : n.connections) {
					Node<T> m = e.getConnection();
					if(!m.visited) {
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
		clearNodes();

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
			n = queue.poll();
			for(Edge<T> edge : n.connections) {
				Node<T> neighbour = edge.getConnection();
				if(neighbour.checkAndMark()) {
					path.add(neighbour.getData());
					path.add(n.getData());
					if(neighbour.getData().equals(end)) {
						return gatherPath(start, end, path);
					} else 
							queue.offer(neighbour);
				}
			}
		}

		return null;
	}
	
	/**
	 * Privat hjälpmetod för {@link #breadthFirstSearch(T, T} 
	 * och {@link #depthFirstSearch(T, T)} för att ta fram vägen som togs.
	 * @param start data
	 * @param end data that it's looking for.
	 * @param path vilket är alla noder som nämns i vägen.
	 * @return list med datan som var rätt väg.
	 */
	private List<T> gatherPath(T start, T end, List<T> path) {
		// Tar reda på vad slutnoden direkt kom ifrån.
		int index = path.indexOf(end); 
		T begin = path.get(index + 1);
		
		// Lägger till slutnoden till kortaste vägen.
		shortest.add(0, end); 
		
		if(begin.equals(start)) {
			// Ursprungliga noden är funnen.
			shortest.add(0, start);
			return shortest;
		} else {
			// Rekursion för att fortsätta leta vart slutnåden kom ifrån.
			return gatherPath(start, begin, path);
		}
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		Entry<T, Node<T>> entry=graph.entrySet().iterator().next();
		HashMap<T, Node<T>> addedNodes = new HashMap<>();
		// Tar fram den första noden att använda som startpunkt.
		T first = entry.getKey(); 
		
		UndirectedGraph<T> miniTree = new MyUndirectedGraph<T>();
		PriorityQueue<Edge<T>> pq = new PriorityQueue<Edge<T>>();
		
		Node<T> node = new Node<T>(first);
		miniTree.add(first);
		addedNodes.put(first, node);
		node.visited = true;
		graph.get(first).visited = true;
		
		for(Edge<T> e : graph.get(first).connections)
			pq.add(e);

		Edge<T> edge = pq.poll();
		
		while(miniTree.getNumberOfNodes() < getNumberOfNodes()) {
			Node<T> next = edge.getConnection();
			if(!next.visited) {
				if(!node.equals(next)) {
					miniTree.add(next.getData());
					addedNodes.put(next.getData(), new Node<T>(next.getData()));
					miniTree.connect(edge.getConnectedFrom().getData(), next.getData(), edge.getCost());
				}
				
				next.visited = true;
				node = next;
			}
			
			for(Edge<T> e : graph.get(next.getData()).connections)
				if(!e.getConnection().visited)
					pq.add(e);

			edge = pq.poll();
		}
		
		return (UndirectedGraph<T>) miniTree;
	}

}
