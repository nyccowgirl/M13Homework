package GraphsPracticeFiles;

import java.util.*;

public class GraphTopologicalTester {

	public static void main(String[] args) {

		// this is the graph on slide 158
		DirectedGraph<String> unweightedDAG = new DirectedGraph<String>();
		buildUnweightedAcyclicDigraph(unweightedDAG);

		
		unweightedDAG.display();

		System.out.println("Topological Order Algortihm 1");
		System.out.println(unweightedDAG.getTopologicalOrder());
		
		System.out.println("\nTopological Order Algortihm 2");
		System.out.println(unweightedDAG.getTopologicalOrderIndegree());

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
