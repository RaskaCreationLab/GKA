package objects;

import java.util.HashMap;

public class VertexImpl implements Vertex{
	
	private static HashMap<String, Vertex> h = new HashMap<String, Vertex>();
	private String name;
	
	VertexImpl(String name) {
		this.name = name;
	}
	
	public static Vertex valueOf(String name) {
		if(!h.containsKey(name))
			h.put(name, new VertexImpl(name));
		return h.get(name);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
