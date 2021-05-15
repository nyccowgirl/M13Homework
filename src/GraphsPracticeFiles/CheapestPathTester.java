package GraphsPracticeFiles;

import java.util.*;

public class CheapestPathTester {

	public static void main(String[] args) {

		// this is the graph on slide 94 (and page 836, section 29.23 in the textbook)
		DirectedGraph<String> weightedDigraph = new DirectedGraph<String>();
		buildWeightedDigraph(weightedDigraph);
		
		Stack<String> cheapestPath = new Stack<String>();
		String start = "A", end = "C";
		double cost = weightedDigraph.getCheapestPath(start, end, cheapestPath);
		System.out.println("Cost is " + cost);
		System.out.println("Path:");
		while(!cheapestPath.isEmpty()) {
			System.out.print(cheapestPath.pop() + " " );
		}
		System.out.println();

	}
	
	private static void buildWeightedDigraph(GraphInterface<String> graph) { 
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		graph.addVertex("G");
		graph.addVertex("H");
		graph.addVertex("I");

		graph.addEdge("A","B", 2);
		graph.addEdge("A","D", 5);
		graph.addEdge("A","E", 4);
		graph.addEdge("B","E", 1);
		graph.addEdge("C","B", 3);
		graph.addEdge("D","G", 2);
		graph.addEdge("E","F", 3);
		graph.addEdge("E","H", 6);
		graph.addEdge("F","C", 4);
		graph.addEdge("F","H", 3);
		graph.addEdge("G","H", 1);
		graph.addEdge("H","I", 1);
		graph.addEdge("I","F", 1);

	}
	
	
	

}
