package objects;

public class VertexImpl implements Vertex{
	
	private String name;
	
	VertexImpl(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

}
