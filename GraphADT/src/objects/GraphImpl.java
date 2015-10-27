package objects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphImpl implements Graph{
	
	private static int ERROR_VALUE = -999999999;
	
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
	//boolean[source][target]
	private ArrayList<ArrayList<Boolean>> edgeMatrix;
	private ArrayList<ArrayList<HashMap<String, Integer>>> edgeAttrMatrix;
	
	//GraphImpl :: Graph x Vertex -> Graph :: (vertex)
	private GraphImpl(Vertex vertex) {
		vertexList = new ArrayList<Vertex>();
		vertexAttrList = new ArrayList<HashMap<String, Integer>>();
		edgeMatrix = new ArrayList<ArrayList<Boolean>>();
		edgeAttrMatrix = new ArrayList<ArrayList<HashMap<String, Integer>>>();
		
		addVertex(vertex);
	}
	
	public static Graph valueOf(Vertex vertex) {
		return new GraphImpl(vertex);
	}
	
	//updateMatrixOnInsert :: Graph x int -> Graph :: (index)
	//updates Matrix after deleteVertex (removes the corresponding rows and colunms)
	private void updateMatrixOnDelete(int index) {
		// remove vertex as source from edgeMatrix
		edgeMatrix.remove(index);
		edgeAttrMatrix.remove(index);
		// remove vertex as target from edgeMatrix
		for(ArrayList<Boolean> row : edgeMatrix)
			row.remove(index);
		for(ArrayList<HashMap<String, Integer>> row : edgeAttrMatrix) 
			row.remove(index);
	}
	
	//updateMatrixOnInsert :: Graph -> Graph
	//updates a EdgeMatrix for this graph adding a row and a column
	private void updateMatrixOnInsert() {
		int lastIndex = vertexList.size() - 1;
		//add a column for the new vertex being target;
		for(ArrayList<Boolean> row : edgeMatrix)
			row.add(false);
		for(ArrayList<HashMap<String, Integer>> row : edgeAttrMatrix)
			row.add(new HashMap<String, Integer>());
		//add a row for the new vertex being source;
		edgeMatrix.add(new ArrayList<Boolean>());
		edgeAttrMatrix.add(new ArrayList<HashMap<String, Integer>>());
		//fill the new row with false-values;
		for(int i = lastIndex; i >= 0; i--) {
			edgeMatrix.get(lastIndex).add(false);
			edgeAttrMatrix.get(lastIndex).add(new HashMap<String, Integer>());
		}
	}

	//addVertex :: Graph x Vertex -> Graph :: (vertex)
	//add a vertex to the graph
	@Override
	public void addVertex(Vertex vertex) {
		if(!vertexList.contains(vertex)) {
			vertexList.add(vertex);
			vertexAttrList.add(new HashMap<String, Integer>());
			updateMatrixOnInsert();	//add entry for this vertex to the edgeMap - with no edges
		}
	}
	
	//deleteVertex :: Graph x Vertex -> Graph :: (vertex)
	//removes a vertex from graph
	@Override
	public void deleteVertex(Vertex vertex) {
		int index = vertexList.indexOf(vertex);
		if(vertexList.size() > 1 && index != -1) {
			updateMatrixOnDelete(index); //remove all edges of this vertex
			vertexList.remove(index);
			vertexAttrList.remove(index);
			}
	}

	//addEdge :: Graph x Vertex x Vertex -> Graph :: (vSource, vTarget)
	//adds an edge that is directed from vSource to vTarget to the graph
	@Override
	public void addEdge(Vertex vSource, Vertex vTarget) {
		int sIndex = vertexList.indexOf(vSource);
		int tIndex = vertexList.indexOf(vTarget); 	//get indices of vertices
		if(sIndex != -1 && tIndex != -1) {			//when both vertices exist in this graph, add the edge to the edgeMatrix
			edgeMatrix.get(sIndex).set(tIndex, true);
		}
	}

	//deleteEdge :: Graph x Vertex -> Vertex -> Graph :: (vSource, vTarget)
	//removes the directed edge from vSource to vTarget from graph
	@Override
	public void deleteEdge(Vertex vSource, Vertex vTarget) {
		int sIndex = vertexList.indexOf(vSource);
		int tIndex = vertexList.indexOf(vTarget);		//get vertex-indices
		if(sIndex != -1 && tIndex != -1) {
			edgeMatrix.get(sIndex).set(tIndex, false);	//delete edge
		}
	}

	//setAtE :: Graph x Vertex x Vertex x String x int -> Graph :: (vSource, vTarget, attr_name, value)
	//sets the attribute >attr_name< to >value< for the edge vSource -> vTarget
	@Override
	public void setAtE(Vertex vSource, Vertex vTarget, String attr_name, int value) {
		int sIndex = vertexList.indexOf(vSource);
		int tIndex = vertexList.indexOf(vTarget);	//get vertex-indices
		if(sIndex != -1 && tIndex != -1 && edgeMatrix.get(sIndex).get(tIndex) == true) {	//check if the needed edge exists
			edgeAttrMatrix.get(sIndex).get(tIndex).put(attr_name, value);					//and add/overwrite attribute attr_name with value
		}
	}

	//setAtV :: Graph x Vertex x String x int -> Graph :: (vertex, attr_name, value)
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
		try {
			File file = new File(System.getProperty("user.dir")+"\\"+filename+".graph");
			
			FileWriter fw = new FileWriter(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("#gerichtet");
			ArrayList<Vertex> edgeList = getEdges();
			for(int i = 0; i < edgeList.size(); i = i+2) {
				bw.newLine();
				Vertex sVertex = edgeList.get(i);
				Vertex tVertex = edgeList.get(i+1);
				String line = sVertex.getName() + "," + tVertex.getName();
				int sIndex = vertexList.indexOf(sVertex);
				int tIndex = vertexList.indexOf(tVertex);
				for(Map.Entry<String, Integer> entry: edgeAttrMatrix.get(sIndex).get(tIndex).entrySet()) {
					line = line.concat(","+entry.getValue());
				}
				bw.write(line);
				
			}
			

			bw.close();
		} catch (IOException e) {}
	}
	
	//getIncident :: Graph x Vertex -> ArrayList<Vertex> :: (vertex)
	//get all edges that are incident to the vertex in an ArrayList as pairs (source, target)
	@Override
	public ArrayList<Vertex> getIncident(Vertex vertex) {
		ArrayList<Vertex> resultList = new ArrayList<Vertex>();
		int index = vertexList.indexOf(vertex) ;
		if (index != -1) {
			for(int i = 0; i < vertexList.size(); i++) 
			{
				if(edgeMatrix.get(index).get(i)) {//if there is an edge to another vertex, add the sourcevertex and the  targetvertex to resultList
					resultList.add(vertex);
					resultList.add(vertexList.get(i));
				}
				if(edgeMatrix.get(i).get(index)) {//if there is an edge from another vertex, add the sourcevertex and the targetvertex 
					resultList.add(vertexList.get(i));
					resultList.add(vertex);
				}
			}
		}
		return resultList;
	}

	//getAdjacent :: Graph x Vertex -> ArrayList<Vertex> :: (vertex)
	//returns all vertices, that are adjacent to >vertex<, in an ArrayList
	@Override
	public ArrayList<Vertex> getAdjacent(Vertex vertex) {
		ArrayList<Vertex> resultList = new ArrayList<Vertex>();
		int index = vertexList.indexOf(vertex) ;
		if (index != -1) {
			for(int i = 0; i < vertexList.size(); i++) 
			{
				if(edgeMatrix.get(index).get(i) || edgeMatrix.get(i).get(index)) //if there is an edge to a vertex, add that vertex to my resultList
					resultList.add(vertexList.get(i));
				
			}
		}
		return resultList;
	}

	//getTarget :: Graph x Vertex -> ArrayList<Vertex> :: (vertex)
	//returns all vertices, that are targetable from >vertex<, in an ArrayList
	@Override
	public ArrayList<Vertex> getTarget(Vertex vertex) {
		ArrayList<Vertex> resultList = new ArrayList<Vertex>();
		int index = vertexList.indexOf(vertex) ;
		if (index != -1) {
			for(int i = 0; i < vertexList.size(); i++) 
			{
				if(edgeMatrix.get(index).get(i)) //if there is an edge to a vertex and that vertex is a targetVertex, add that vertex to my resultList
					resultList.add(vertexList.get(i));
				
			}
		}
		return resultList;
	}

	//getSource :: Graph x Vertex -> ArrayList<Vertex> :: (vertex)
	//returns all vertices, that >vertex< can be reached from, in an ArrayList
	@Override
	public ArrayList<Vertex> getSource(Vertex vertex) {
		ArrayList<Vertex> resultList = new ArrayList<Vertex>();
		int index = vertexList.indexOf(vertex) ;
		if (index != -1) {
			for(int i = 0; i < vertexList.size(); i++) 
			{
				if(edgeMatrix.get(i).get(index)) //if there is an edge to a vertex, add that vertex to the resultList
					resultList.add(vertexList.get(i));
				
			}
		}
		return resultList;
	}

	//getEdges :: Graph -> ArrayList<Vertex> :: 
	//returns all edges in an ArrayList in pairs (source, target)
	@Override
	public ArrayList<Vertex> getEdges() {
		ArrayList<Vertex> resultList = new ArrayList<Vertex>();
		for(int row = 0; row < vertexList.size(); row++) {
			for(int col = 0; col < vertexList.size(); col++) {
				if(edgeMatrix.get(row).get(col)) {
					resultList.add(vertexList.get(row));
					resultList.add(vertexList.get(col));
				}
			}
		}
		return resultList;
	}

	//getVertexes :: Graph -> ArrayList<Vertex> ::
	//returns all vertices as an ArrayList
	@Override
	public ArrayList<Vertex> getVertexes() {
		return vertexList;
	}

	//getValE :: Graph x Vertex x Vertex x String -> int :: (vSource, vTarget, attr_ame)
	//returns value of a specific edge
	@Override
	public int getValE(Vertex vSource, Vertex vTarget, String attr_name) {
		int sIndex = vertexList.indexOf(vSource);
		int tIndex = vertexList.indexOf(vTarget);
		if(sIndex != -1 && tIndex != -1 && edgeMatrix.get(sIndex).get(tIndex) && edgeAttrMatrix.get(sIndex).get(tIndex).containsKey(attr_name))
			return edgeAttrMatrix.get(sIndex).get(tIndex).get(attr_name);
		return ERROR_VALUE;
	}

	//getValV :: Graph x Vertex x String -> int :: (vertex, attr_ame)
	//returns value of a specific vertex
	@Override
	public int getValV(Vertex vertex, String attr_name) {
		int index = vertexList.indexOf(vertex);
		if(index != -1 && vertexAttrList.get(index).containsKey(attr_name))
			return vertexAttrList.get(index).get(attr_name);
		return ERROR_VALUE;
	}
}
