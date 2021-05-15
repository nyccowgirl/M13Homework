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
} 
