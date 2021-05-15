package GraphsPracticeFiles;

public class VertexCPData<T> implements Comparable<VertexCPData<T>>{
	
	private Vertex<T> vertex, predecessor;
	private double cost;
	
	public VertexCPData(Vertex<T> vertex, double cost, Vertex<T> predecessor) {
		this.vertex = vertex;
		this.cost = cost;
		this.predecessor = predecessor;

	}
	public Vertex<T> getVertex() {
		return vertex;
	}
	public void setVertex(Vertex<T> vertex) {
		this.vertex = vertex;
	}
	public Vertex<T> getPredecessor() {
		return predecessor;
	}
	public void setPredecessor(Vertex<T> predecessor) {
		this.predecessor = predecessor;
	}
	public boolean hasPredecessor() {
		return this.predecessor!=null;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	@Override
	public int compareTo(VertexCPData<T> other) {
		return Long.compare(Double.doubleToLongBits(this.cost), Double.doubleToLongBits(other.cost));
	}

}
