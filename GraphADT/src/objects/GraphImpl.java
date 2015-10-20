package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GraphImpl implements Graph{
	
	private ArrayList<Vertex> vertexList;
	private ArrayList<HashMap<String, Integer>> vertexAttrList;
	
	/*			Design of Matrix
	 * 				targets
	 * 				columns
	 *  		# | 1 | 2 | 3
	 *  		1 | f | f | f
	 *  sources	2 | t | f | f
	 *  rows	3 | f | f | f
	 *  ---> one edge from 2 to 1
	 */
	private boolean[][] edgeMatrix;
	private HashMap<String, Integer>[][] edgeAttrMatrix;
	
	//GraphImpl :: Vertex -> Graph :: (vertex)
	GraphImpl(Vertex vertex) {
		vertexList.add(vertex);
		vertexAttrList.add(new HashMap<String, Integer>());
		constructEdgeMatrix();	//generate EdgeMatrix	
	}
	
	//constructEdgeMatrix :: -> Graph
	//constructs a new EdgeMatrix for this graph adding a row and a column
	private void constructEdgeMatrix() {
		int len = vertexList.size();
		edgeMatrix = new boolean[len][len];
		edgeAttrMatrix = new HashMap[len][len];
		for (int row = 0; row <= len; row++) {
			for (int col = 0; col <= len; col++) {
				edgeMatrix[row][col] = false;
				edgeAttrMatrix[row][col] = new HashMap<String, Integer>();
			}
		}
	}
	
	//updateMatrixOnInsert :: int -> Graph :: (index)
	//updates Matrix after deleteVertex (removes the corresponding rows and colunms)
	private void updateMatrixOnDelete(int index) {
		int len = vertexList.size();
		boolean[][] newMatrix = new boolean[len-1][len-1];
		HashMap<String, Integer>[][] attrMap = new HashMap[len-1][len-1];
		for(int row = 0; row<index; row++) {
			for(int col = 0; col<index; col++) {
				newMatrix[row][col] = edgeMatrix[row][col];
				attrMap[row][col] = edgeAttrMatrix[row][col];
			}	//copy old matrix values before index of removed item
			for(int col = index+1; col <= len; col++) {
				newMatrix[row][col-1] = edgeMatrix[row][col];
				attrMap[row][col-1] = edgeAttrMatrix[row][col];
			}   //shift all items in column after index 1 to the left
		}
		//ignore row of index
		for(int row = index+1; row <= len; row++) {
			for(int col = 0; col < index; col ++) {
				newMatrix[row-1][col] = edgeMatrix[row][col];
				attrMap[row-1][col] = edgeAttrMatrix[row][col];
			}	//shift all items in row after index 1 up
			for(int col = index+1; col <= len; col++) {
				newMatrix[row-1][col-1] = edgeMatrix[row][col];
				attrMap[row-1][col-1] = edgeAttrMatrix[row][col];
			}	//shift all items in row and column after index 1 to the left and up
		}
		edgeMatrix = newMatrix; //save new Matrix
		edgeAttrMatrix = attrMap;
	}
	
	//updateMatrixOnInsert :: -> Graph
	//updates a EdgeMatrix for this graph adding a row and a column
	private void updateMatrixOnInsert() {
		int len = vertexList.size();
		boolean[][] newMatrix = new boolean[len][len];
		HashMap<String, Integer>[][] attrMap = new HashMap[len][len];
		for(int row = 0; row<len; row++) {
			for(int col = 0; col<len; col++) {
				newMatrix[row][col] = edgeMatrix[row][col];
				attrMap[row][col] = edgeAttrMatrix[row][col];
			}	//save old edges
			newMatrix[row][len] = false;
			attrMap[row][len] = new HashMap<String, Integer>();
		} 	//no edges for the new vertex (last column)
		for(int col = 0; col <= len; col++) {
			newMatrix[len][col] = false;
			attrMap[len][col] = new HashMap<String, Integer>();
		}	//no edges for the new vertex (last row)
		edgeMatrix = newMatrix; //save new Matrix
		edgeAttrMatrix = attrMap;
	}

	//addVertex :: Vertex -> Graph :: (vertex)
	//add a vertex to the graph
	@Override
	public void addVertex(Vertex vertex) {
		if(!vertexList.contains(vertex)) {
			vertexList.add(vertex);
			vertexAttrList.add(new HashMap<String, Integer>());
			updateMatrixOnInsert();	//add entry for this vertex to the edgeMap - with no edges
		}
	}
	
	//deleteVertex :: Vertex -> Graph :: (vertex)
	//removes a vertex from graph
	@Override
	public void deleteVertex(Vertex vertex) {
		if(vertexList.size() <= 1) {
			int index = vertexList.indexOf(vertex);
			updateMatrixOnDelete(index); //remove all edges of this vertex
			vertexList.remove(index);
			vertexAttrList.remove(index);
			}
	}

	//addEdge :: Vertex x Vertex -> Graph :: (vSource, vTarget)
	//adds an edge that is directed from vSource to vTarget to the graph
	@Override
	public void addEdge(Vertex vSource, Vertex vTarget) {
		int sIndex = vertexList.indexOf(vSource);
		int tIndex = vertexList.indexOf(vTarget); 	//get indices of vertices
		if(sIndex != -1 && tIndex != -1) {			//when both vertices exist in this graph, add the edge to the edgeMatrix
			edgeMatrix[sIndex][tIndex] = true;
		}
	}

	//deleteEdge :: Vertex -> Vertex -> Graph :: (vSource, vTarget)
	//removes the directed edge from vSource to vTarget from graph
	@Override
	public void deleteEdge(Vertex vSource, Vertex vTarget) {
		int sIndex = vertexList.indexOf(vSource);
		int tIndex = vertexList.indexOf(vTarget);	//get vertex-indices
		if(sIndex != -1 && tIndex != -1) {
			edgeMatrix[sIndex][tIndex] = false;		//delete edge
		}
	}

	//setAtE :: Vertex x Vertex x String x int -> Graph :: (vSource, vTarget, attr_name, value)
	//sets the attribute >attr_name< to >value< for the edge vSource -> vTarget
	@Override
	public void setAtE(Vertex vSource, Vertex vTarget, String attr_name, int value) {
		int sIndex = vertexList.indexOf(vSource);
		int tIndex = vertexList.indexOf(vTarget);	//get vertex-indices
		if(sIndex != -1 && tIndex != -1 && edgeMatrix[sIndex][tIndex] == true) {	//check if the needed edge exists
			edgeAttrMatrix[sIndex][tIndex].put(attr_name, value);					//and add/overwrite attribute attr_name with value
		}
	}

	//setAtV :: Vertex x String x int -> Graph :: (vertex, attr_name, value)
	//sets the attribute >attr_name< to >value< for >vertex<
	@Override
	public void setAtV(Vertex vertex, String attr_name, int value) {
		int index = vertexList.indexOf(vertex);					
		if(index != -1) {
			vertexAttrList.get(index).put(attr_name, value);	//if vertex is an element of graph, add/overwrite attribute attr_name with value
		}
	}

	@Override
	public void exportG(String filename) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Vertex> getIncident(Vertex vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Vertex> getAdjacent(Vertex vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Vertex> getTarget(Vertex vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Vertex> getSource(Vertex vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Vertex> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Vertex> getVertexes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getValE(Vertex vSource, Vertex vTarget, String attr_name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getValV(Vertex vertex, String attr_name) {
		// TODO Auto-generated method stub
		return 0;
	}
}
