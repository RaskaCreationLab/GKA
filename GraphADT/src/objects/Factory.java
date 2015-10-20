package objects;

public class Factory {
	
	public static Vertex createV(String name) {
		return new VertexImpl(name);
	}
	
	public static Graph createG(Vertex vertex) {
		return new GraphImpl(vertex);
	}
	
	public static Graph importG(String filename) {
		Graph graph;
		return graph;
	}
	
}
