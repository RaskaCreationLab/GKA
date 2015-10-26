package objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Factory {
	
	public static String getDirectory() {
		return System.getProperty("user.dir");
	}
	
	private static HashMap<String, Vertex> h = new HashMap<String, Vertex>();
	
	public static Vertex createV(String name) {
		if(!h.containsKey(name))
			h.put(name, VertexImpl.valueOf(name));
		return h.get(name);
	}
	
	public static Graph createG(Vertex vertex) {
		return GraphImpl.valueOf(vertex);
	}
	
	public static Graph importG(String filename) {
		ArrayList<String> lines = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(getDirectory()+ "\\" +filename+".graph")))
		{
			
			String currentLine;
			while((currentLine = br.readLine()) != null) {
				lines.add(currentLine);
			}
			
			if(lines.isEmpty()) 
				return null;

		} catch (IOException e) {
			return null;
		} 
		return importCreateG(lines);
	}
	
	static Graph importCreateG(ArrayList<String> lines) {
		boolean isDirected;
		Graph g = null;
		if(lines.get(0) == "#gerichtet")
			isDirected = true;
		else if(lines.get(0) == "#ungerichtet")
			isDirected = false;
		else 
			return g;
	
		for (int i = 1; i < lines.size(); i++) {
			String splitLine[] = lines.get(i).split(",");
			Vertex v1 = Factory.createV(splitLine[0]);
			Vertex v2 = Factory.createV(splitLine[1]);
			if (g == null) {
				g = Factory.createG(v1);
				g.addVertex(v2);
			} else {
				g.addVertex(v1);
				g.addVertex(v2);
			}
			g.addEdge(v1, v2);
			for(int j = 2; j < splitLine.length; j++) {
				String splitAttr[] = splitLine[j].split(" ");
				g.setAtE(v1, v2, splitAttr[0], Integer.parseInt(splitAttr[1]));
			}
			if(isDirected == false) {
				g.addEdge(v2, v1);
				for(int j = 2; j < splitLine.length; j++) {
					String splitAttr[] = splitLine[j].split(" ");
					g.setAtE(v2, v1, splitAttr[0], Integer.parseInt(splitAttr[1]));
				}
			}
		}
		return g;
	}
}
