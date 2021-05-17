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
	protected Map<T, Vertex<T>> vertices;
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

	public Queue<T> getTopologicalOrderIndegree() {

		Queue<T> ordering = new LinkedList<T>();

		// get a set of all vertices in the graph
		Set<Vertex<T>> vertexSet = new HashSet<>(vertices.values());
		Iterator<Vertex<T>> vertexIterator = vertexSet.iterator();
		
		// iterate over all vertices looking for a vertex with indegree 0
		while (vertexIterator.hasNext()) {
			Vertex<T> vertex = vertexIterator.next();
			
			// we find a vertex with indegree 0
			if (vertex.getIndegree() == 0) {				
				ordering.add(vertex.getData()); // add it to the queue
				
				// we are going to remove it from the graph, so we need
				// to find all of its neighbors and update the indegree
				// values to be one less (one less edge coming into those neighbors)
				Iterator<Vertex<T>> neighborIterator = vertex.getNeighborIterator();
				while (neighborIterator.hasNext()) {
					Vertex<T> neighbor = neighborIterator.next();
					neighbor.decrementIndegree();
				}
				// remove the vertex from the set
				vertexIterator.remove();
			}
			
			// if we've iterated through the list but still haven't
			// processed all the vertices, we need to iterate again
			if(!vertexIterator.hasNext() && !vertexSet.isEmpty()) {
				vertexIterator = vertexSet.iterator();
			}
		}

		return ordering;
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

	
	
	
	// make sure each part of the graph is reached with a depth-first search
	public boolean isCyclic() {
		Set<Vertex<T>> white = new HashSet<>();
		Set<Vertex<T>> gray = new HashSet<>();
		Set<Vertex<T>> black = new HashSet<>();
		white.addAll(vertices.values());
		
		while(!white.isEmpty()) {
			Iterator<Vertex<T>> vertexIterator = vertices.values().iterator();
			while(vertexIterator.hasNext()) {
				Vertex<T> vertex = vertexIterator.next();
				if(white.contains(vertex)) {
					boolean dfsResult = depthFirstCycleDetector(vertex, white, gray, black);
					
					// an iterative version, for reference
					// Stack<Vertex<T>> vertexStack = new Stack<>();
					// boolean dfsResult = depthFirstCycleDetectorStack(vertex, white, gray, black, vertexStack);

					if(dfsResult==true) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean depthFirstCycleDetectorStack(Vertex<T> vertexS, Set<Vertex<T>> white, Set<Vertex<T>> gray, Set<Vertex<T>> black, Stack<Vertex<T>> vertexStack) {

		vertexStack.push(vertexS);
		white.remove(vertexS);
		gray.add(vertexS);
		
		while(!vertexStack.isEmpty()) {
			Vertex<T> vertex = vertexStack.pop();
		
			Iterator<Vertex<T>> neighborIterator = vertex.getNeighborIterator();
			while(neighborIterator.hasNext()) {
				Vertex<T> neighbor = neighborIterator.next();
				if(black.contains(neighbor)) {
					// do nothing
				} else if(gray.contains(neighbor)) {
					return true;
				} else {
					vertexStack.push(neighbor);
					white.remove(neighbor);
					gray.add(neighbor);
				}
			}
			gray.remove(vertex);
			black.add(vertex);
		}
		return false;
	}

	
	// "processing" is conducting a depth-first search from that vertex
	// white vertices are unprocessed; gray are in process; black have already been completely processed
	// if we reach a gray vertex during a depth-first search, then we have a cycle
	private boolean depthFirstCycleDetector(Vertex<T> vertex, Set<Vertex<T>> white, Set<Vertex<T>> gray,
			Set<Vertex<T>> black) {
		white.remove(vertex);
		gray.add(vertex);

		Iterator<Vertex<T>> neighborIterator = vertex.getNeighborIterator();
		while (neighborIterator.hasNext()) {
			Vertex<T> neighbor = neighborIterator.next();
			if (black.contains(neighbor)) {
				// do nothing
			} else if (gray.contains(neighbor)) {
				return true;
			} else {
				boolean recursiveResult = depthFirstCycleDetector(neighbor, white, gray, black);
				if (recursiveResult == true) {
					return true;
				}
			}
		}
		gray.remove(vertex);
		black.add(vertex);
		return false;
	}
	
	
}
