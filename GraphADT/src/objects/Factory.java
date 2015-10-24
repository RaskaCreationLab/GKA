package objects;

public class Factory {
	
	public static Vertex createV(String name) {
		return VertexImpl.valueOf(name);
	}
	
	public static Graph createG(Vertex vertex) {
		return GraphImpl.valueOf(vertex);
	}
	/*
	public static Graph importG(String filename) {
		Graph graph;
		return graph;
	}
	*/
	
}
