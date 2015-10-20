package objects;

import java.util.ArrayList;

public interface Graph {
	
	//modify graph
	
	public void addVertex(Vertex vertex);
	
	public void deleteVertex(Vertex vertex);
	
	public void addEdge(Vertex vSource, Vertex vTarget);
	
	public void deleteEdge(Vertex vSource, Vertex vTarget);
	
	public void setAtE(Vertex vSource, Vertex vTarget, String attr_name, int value);
	
	public void setAtV(Vertex vertex, String attr_name, int value);
	
	//export
	
	public void exportG(String filename);
	
	//get
	
	public ArrayList<Vertex> getIncident(Vertex vertex);
	
	public ArrayList<Vertex> getAdjacent(Vertex vertex);
	
	public ArrayList<Vertex> getTarget(Vertex vertex);
	
	public ArrayList<Vertex> getSource(Vertex vertex);
	
	public ArrayList<Vertex> getEdges();
	
	public ArrayList<Vertex> getVertexes();
	
	public int getValE(Vertex vSource, Vertex vTarget, String attr_name);
	
	public int getValV(Vertex vertex, String attr_name);
}
