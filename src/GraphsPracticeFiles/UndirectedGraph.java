package GraphsPracticeFiles;

import java.util.*;

/**
 * A class that implements the ADT undirected graph.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.0
 */
public class UndirectedGraph<T> extends DirectedGraph<T> implements GraphInterface<T> {

	public UndirectedGraph() {
		super();
	} 

	public boolean addEdge(T begin, T end, double edgeWeight) {
		return super.addEdge(begin, end, edgeWeight) && super.addEdge(end, begin, edgeWeight);
		// Assertion: edge count is twice its correct value due to calling addEdge twice
	} 

	public boolean addEdge(T begin, T end) {
		return this.addEdge(begin, end, 0);
	} 

	public int getNumberOfEdges() {
		return super.getNumberOfEdges() / 2;
	} 

	public Stack<T> getTopologicalOrder() {
		throw new UnsupportedOperationException("Topological sort is illegal in an undirected graph.");
	} 
	
	public boolean isCyclic() {
		resetVertices();
		
		Iterator<Vertex<T>> vertexIterator = vertices.values().iterator();
		while(vertexIterator.hasNext()) {
			Vertex<T> vertex = vertexIterator.next();
			if(!vertex.isVisited()) {
				vertex.visit();
				boolean dfsResult =  depthFirstCycleDetector(vertex, null);
				if(dfsResult==true) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	private boolean depthFirstCycleDetector(Vertex<T> vertex, Vertex<T> parent) {
		Iterator<Vertex<T>> neighborIterator = vertex.getNeighborIterator();
		while(neighborIterator.hasNext()) {
			Vertex<T> neighbor = neighborIterator.next();
			if(!neighbor.isVisited()) {
				neighbor.visit();
				return depthFirstCycleDetector(neighbor, vertex);
			} else if(parent!=null && !neighbor.equals(parent)) {
				return true;
			}
		}
		return false;
	}
	
	
 
 

} 
