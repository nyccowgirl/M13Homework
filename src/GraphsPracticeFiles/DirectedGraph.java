package GraphsPracticeFiles;

import java.util.*;

/**
 * A class that implements the ADT directed graph.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 */
public class DirectedGraph<T> implements GraphInterface<T> {
	private Map<T, Vertex<T>> vertices;
	private int edgeCount;

	public DirectedGraph() {
		vertices = new HashMap<>();
		edgeCount = 0;
	}

	public boolean addVertex(T vertexData) {
		Vertex<T> addOutcome = vertices.put(vertexData, new Vertex<>(vertexData));
		return addOutcome == null; // Was addition to dictionary successful? if this is a new vertex, this returns true
		// if the vertex is already in the map (graph), this returns false
	}

	public boolean addEdge(T begin, T end, double edgeWeight) {
		boolean result = false;
		Vertex<T> beginVertex = vertices.get(begin);
		Vertex<T> endVertex = vertices.get(end);
		if ((beginVertex != null) && (endVertex != null)) {
			result = beginVertex.connect(endVertex, edgeWeight);
		}
		if (result) {
			edgeCount++;
		}
		return result;
	}

	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	}

	public boolean hasEdge(T begin, T end) {
		boolean found = false;
		Vertex<T> beginVertex = vertices.get(begin);
		Vertex<T> endVertex = vertices.get(end);
		if ((beginVertex != null) && (endVertex != null)) {
			Iterator<Vertex<T>> neighbors = beginVertex.getNeighborIterator();
			while (!found && neighbors.hasNext()) {
				Vertex<T> nextNeighbor = neighbors.next();
				if (endVertex.equals(nextNeighbor)) {
					found = true;
				}
			}
		}

		return found;
	}

	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	public void clear() {
		vertices.clear();
		edgeCount = 0;
	}

	public int getNumberOfVertices() {
		return vertices.size();
	}

	public int getNumberOfEdges() {
		return edgeCount;
	}

	protected void resetVertices() {
		Iterator<Vertex<T>> vertexIterator = vertices.values().iterator();
		while (vertexIterator.hasNext()) {
			Vertex<T> nextVertex = vertexIterator.next();
			nextVertex.unvisit();
		}
	}

	public Queue<T> getBreadthFirstTraversal(T origin) {
		resetVertices();
		Queue<T> traversalOrder = new LinkedList<>();
		Queue<Vertex<T>> vertexQueue = new LinkedList<>();

		Vertex<T> originVertex = vertices.get(origin);
		originVertex.visit();
		traversalOrder.add(origin); // Enqueue vertex label
		vertexQueue.add(originVertex); // Enqueue vertex

		while (!vertexQueue.isEmpty()) {
			Vertex<T> frontVertex = vertexQueue.remove();
			Iterator<Vertex<T>> neighbors = frontVertex.getNeighborIterator();

			while (neighbors.hasNext()) {
				Vertex<T> nextNeighbor = neighbors.next();
				if (!nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					traversalOrder.add(nextNeighbor.getData());
					vertexQueue.add(nextNeighbor);
				}
			}
		}

		return traversalOrder;
	}

	public Queue<T> getDepthFirstTraversal(T origin) {
		// Assumes graph is not empty
		resetVertices();
		Queue<T> traversalOrder = new LinkedList<T>();
		Stack<Vertex<T>> vertexStack = new Stack<>();

		Vertex<T> originVertex = vertices.get(origin);
		originVertex.visit();
		traversalOrder.add(origin); // Enqueue vertex label
		vertexStack.push(originVertex); // Enqueue vertex

		while (!vertexStack.isEmpty()) {
			Vertex<T> topVertex = vertexStack.peek();
			Vertex<T> nextNeighbor = topVertex.getUnvisitedNeighbor();

			if (nextNeighbor != null) {
				nextNeighbor.visit();
				traversalOrder.add(nextNeighbor.getData());
				vertexStack.push(nextNeighbor);
			} else {// All neighbors are visited
				vertexStack.pop();
			}
		}

		return traversalOrder;
	}

	public Stack<T> getTopologicalOrder() {
		resetVertices();

		Stack<T> vertexStack = new Stack<>();
		int numberOfVertices = getNumberOfVertices();
		for (int counter = 1; counter <= numberOfVertices; counter++) {
			Vertex<T> nextVertex = findTerminal();
			nextVertex.visit();
			vertexStack.push(nextVertex.getData());
		}

		return vertexStack;
	}

	protected Vertex<T> findTerminal() {
		boolean found = false;
		Vertex<T> result = null;

		Iterator<Vertex<T>> vertexIterator = vertices.values().iterator();

		while (!found && vertexIterator.hasNext()) {
			Vertex<T> nextVertex = vertexIterator.next();
			// If nextVertex is unvisited AND has only visited neighbors)
			if (!nextVertex.isVisited()) {
				if (nextVertex.getUnvisitedNeighbor() == null) {
					found = true;
					result = nextVertex;
				} 
			} 
		}

		return result;
	}

	public void display() {
		System.out.println("Graph has " + getNumberOfVertices() + " vertices and " + getNumberOfEdges() + " edges.");
		Iterator<Vertex<T>> vertexIterator = vertices.values().iterator();
		while (vertexIterator.hasNext()) {
			((Vertex<T>) (vertexIterator.next())).display();
		}
		System.out.println();
	}
	
	public double getCheapestPath(T begin, T end, Stack<T> path) {
		resetVertices();
		boolean done = false;
		
		Vertex<T> originVertex = vertices.get(begin);
		Vertex<T> endVertex = vertices.get(end);
		
		PriorityQueue<VertexCPData<T>> priorityQueue = new PriorityQueue<>();
		
		// holding the "extra data" about each vertex once we know the final values
		Map<Vertex<T>, VertexCPData<T>> vertexFinalDataMap = new HashMap<>();
		
		priorityQueue.add(new VertexCPData<T>(originVertex, 0, null));
		
		while(!done && !priorityQueue.isEmpty()) {
			
			VertexCPData<T> frontVertexData = priorityQueue.remove();
			Vertex<T> frontVertex = frontVertexData.getVertex();
			
			if(!frontVertex.isVisited()) {
				frontVertex.visit();
				vertexFinalDataMap.put(frontVertex, frontVertexData);
				
				if(frontVertex.equals(endVertex)) {
					done = true;
				} else {
					Iterator<Vertex<T>> neighbors = frontVertex.getNeighborIterator();
					Iterator<Double> edgeWeights = frontVertex.getWeightIterator();
					
					while(neighbors.hasNext()) {
						Vertex<T> neighbor = neighbors.next();
						Double neighborEdgeWeight = edgeWeights.next();
						
						if(!neighbor.isVisited()) {
							double neighborCost = frontVertexData.getCost() + neighborEdgeWeight;
							priorityQueue.add(new VertexCPData(neighbor, neighborCost, frontVertex));
						}
					}
				}
			}
		}
		
		// build the path
		path.clear();
		
		VertexCPData<T> vertexData = vertexFinalDataMap.get(endVertex);
		double cheapestPathCost = vertexData.getCost();
		path.push(vertexData.getVertex().getData()); // or path.push(endVertex.getData());
		
		while(vertexData.hasPredecessor()) {
			Vertex<T> predecessor = vertexData.getPredecessor();
			path.push(predecessor.getData());
			vertexData = vertexFinalDataMap.get(predecessor);
		}
				
		return cheapestPathCost;
	}

	public boolean isCyclic() {
		HashSet<Integer> white = new HashSet<>();
		HashSet<Integer> gray = new HashSet<>();
		HashSet<Integer> black = new HashSet<>();

		// Put all vertices in white set
		for (int i = 0; i < vertices.size(); i++) {
			white.add(i);
		}

		// Traverse white vertices
		for (int i = 0; i < edgeCount; i++) {
			if (isCyclic(i, white, gray, black)) {
				return true;
			}
		}
		return false;
	}

	public boolean isCyclic(int vertex, HashSet<Integer> white, HashSet<Integer> gray, HashSet<Integer> black) {
		// For specific vertex, turn gray from white
		white.remove(vertex);
		gray.add(vertex);

		// Visit neighbors
		for (int i = 0; i < vertices.get(vertex).size(); i++) {
			int adj = vertices.get(vertex).get(i);

			// If vertex is in gray, cycle is found
			if (gray.contains(adj)) {
				return true;
			}

			// If vertex is in black, vertex is done
			if (black.contains(adj)) {
				continue;
			}

			// Traverse from vertex
			if (isCyclic(adj, white, gray, black)) {
				return true;
			}
		}
		// If not cycle from vertex, turn black from gray
		gray.remove(vertex);
		black.add(vertex);
		return false;
	}
	
}
