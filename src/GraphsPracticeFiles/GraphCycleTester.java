package GraphsPracticeFiles;

import java.util.List;

public class GraphCycleTester {

	public static void main(String[] args) {
		
		// this is the graph on slide 7
		GraphInterface<String> unweightedUndirectedAcyclic = new UndirectedGraph<String>();
		buildUnweightedUndirectedAcyclic(unweightedUndirectedAcyclic);
		unweightedUndirectedAcyclic.display();
		System.out.println("Cyclic? Should be false: " + unweightedUndirectedAcyclic.isCyclic());
		System.out.println();
		
		
		// this is the graph on slide 10
		GraphInterface<String> weightedUndirectedCyclic = new UndirectedGraph<String>();
		buildWeightedUndirectedCyclic(weightedUndirectedCyclic);
		weightedUndirectedCyclic.display();
		System.out.println("Cyclic? Should be true: " + weightedUndirectedCyclic.isCyclic());
		System.out.println();
		
		// this is the graph on slide 11
		GraphInterface<String> unweightedDigraphCyclic = new DirectedGraph<String>();
		buildUnweightedDigraphCyclic(unweightedDigraphCyclic);
		unweightedDigraphCyclic.display();
		System.out.println("Cyclic?  Should be true: " + unweightedDigraphCyclic.isCyclic());
		System.out.println();
	
		// this is the graph on slide 158
		GraphInterface<String> unweightedDigraphAcyclic = new DirectedGraph<String>();
		buildUnweightedAcyclicDigraph(unweightedDigraphAcyclic);
		unweightedDigraphAcyclic.display();
		System.out.println("Cyclic?  Should be false: " + unweightedDigraphAcyclic.isCyclic());
		System.out.println();
		
		
		

	}
	
	private static void buildWeightedUndirectedCyclic(GraphInterface<String> graph) { 
		graph.addVertex("G");
		graph.addVertex("H");
		graph.addVertex("J");
		graph.addVertex("K");
		graph.addVertex("M");
		graph.addVertex("N");

		graph.addEdge("G","H", 3);
		graph.addEdge("G","K", 2);
		graph.addEdge("G","N", 2);
		graph.addEdge("H","J", 4);
		graph.addEdge("J","K", 6);
		graph.addEdge("K","M", 4);
		graph.addEdge("M","N", 5);
	}
	
	private static void buildUnweightedUndirectedAcyclic(GraphInterface<String> graph) {
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("Z");
		graph.addVertex("Y");
		
		graph.addEdge("A", "B");
		graph.addEdge("A", "Y");
		graph.addEdge("B", "Z");
	}
	
	
	private static void buildUnweightedDigraphCyclic(GraphInterface<String> graph) {
		graph.addVertex("G");
		graph.addVertex("H");
		graph.addVertex("J");
		graph.addVertex("K");
		graph.addVertex("M");
		graph.addVertex("N");
		graph.addVertex("P");
		
		graph.addEdge("G", "N");
		graph.addEdge("G", "H");
		graph.addEdge("H", "N");
		graph.addEdge("J", "H");
		graph.addEdge("J", "K");
		graph.addEdge("M", "N");
		graph.addEdge("N", "H");
		graph.addEdge("N", "K");
		graph.addEdge("N", "M");
		graph.addEdge("N", "P");
	}	
	
	private static void buildUnweightedAcyclicDigraph(GraphInterface<String> graph) {
		graph.addVertex("B");
		graph.addVertex("V");
		graph.addVertex("N");
		graph.addVertex("A");
		graph.addVertex("C");
		graph.addVertex("W");
		graph.addVertex("X");
		graph.addVertex("Q");
		
		graph.addEdge("B", "V");
		graph.addEdge("B", "A");
		graph.addEdge("V", "N");
		graph.addEdge("N", "C");
		graph.addEdge("N", "Q");
		graph.addEdge("A", "V");
		graph.addEdge("C", "X");
		graph.addEdge("C", "Q");
		graph.addEdge("W", "A");
		graph.addEdge("W", "X");
	}	

}
