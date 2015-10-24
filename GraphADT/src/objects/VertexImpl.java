package objects;

public class VertexImpl implements Vertex{
	
	private String name;
	
	VertexImpl(String name) {
		this.name = name;
	}
	
	public static Vertex valueOf(String name) {
		return new VertexImpl(name);
	}
	
	@Override
	public String getName() {
		return name;
	}
}
