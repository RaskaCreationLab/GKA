package objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Factory {
	
	private static ArrayList<String> attrNames;
	
	public static String getDirectory() {
		return System.getProperty("user.dir");
	}
	
	public static Vertex createV(String name) {
		return VertexImpl.valueOf(name);
	}
	
	public static Graph createG(Vertex vertex) {
		return GraphImpl.valueOf(vertex);
	}
	
	public static Graph importG(String filename) {
		System.out.println(getDirectory());
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
		return importCreateG(lines, attrNames);
	}
	
	static Graph importCreateG(ArrayList<String> lines, ArrayList<String> names) {
		boolean isDirected;
		Graph g = null;
//		if(lines.get(0) == "#gerichtet")
//			isDirected = true;
//		else if(lines.get(0) == "#ungerichtet")
//			isDirected = false;
//		else 
//			return g;
	
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
				g.setAtE(v1, v2, names.get(j-2), Integer.parseInt(splitLine[j]));
			}
//			if(isDirected == false) {
//				g.addEdge(v2, v1);
//				for(int j = 2; j < splitLine.length; j++) {
//					g.setAtE(v2, v1, names.get(j-2), Integer.parseInt(splitLine[j]));
//				}
//			}
		}
		return g;
	}
}
