package objects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Utility {
	public static ArrayList<Vertex> bellf(Graph graph, Vertex start, Vertex destination) {
		//initialize
		ArrayList<Integer> distance = new ArrayList<Integer>(); 
		ArrayList<Vertex> predecessor = new ArrayList<Vertex>();
		
		for(Vertex v : graph.getVertexes()) {
			if(v == start) {
				distance.add(0);
				predecessor.add(v);
			} else {
				distance.add(Integer.MAX_VALUE);
				predecessor.add(null);
			}
		}
		
		//iterate
		//number of iterations
		for(int i = 0; i < graph.getVertexes().size(); i++) {
			for(Vertex s : graph.getVertexes()) {
				int indexS = graph.getVertexes().indexOf(s);
				for(Vertex t : graph.getTarget(s)) {
					int indexT = graph.getVertexes().indexOf(t);
					int cost = add(distance.get(indexS), graph.getValE(s, t, "cost")); //evaluate new costs
					if(distance.get(indexT) > cost) { //if new costs are lower, replace old costs and change predecessor
						distance.set(indexT, cost);
						predecessor.set(indexT, s);
					}
				}
			}
		} //check for negative circle
		for(int i = 0; i < graph.getVertexes().size(); i++) {
			for(Vertex s : graph.getVertexes()) {
				int indexS = graph.getVertexes().indexOf(s);
				for(Vertex t : graph.getTarget(s)) {
					int indexT = graph.getVertexes().indexOf(t);
					int cost = add(distance.get(indexS), graph.getValE(s, t, "cost"));
					if(distance.get(indexT) > cost) {
						System.out.println("negativer Kreis gefunden");
						return null; //if there are lower costs again, return null and an errormessage.
					}
				}
			}
		}
		
		//get vertices for shortest way
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		Vertex tmp = destination; //from destination
		path.add(tmp);
		while(tmp != start) {
			if(tmp == null) //to start
				return null;
			tmp = predecessor.get(graph.getVertexes().indexOf(tmp));
			path.add(tmp);
		}
		Collections.reverse(path); //reverse, so it has right order
		return path;  //and return it.
	}
	
	private static int add(int a, int b) {
		if(a > 0 && b > 0 && a + b < 0)
			return Integer.MAX_VALUE;
		else
			return a + b;
	}
	
	public static long bellf_runtime(Graph graph, Vertex start, Vertex destination) {
		long starttime = System.currentTimeMillis();
		bellf(graph, start, destination);
		return System.currentTimeMillis() - starttime;
	}
	
	//returns an Array with access-counts: read-access on graph, read access in general, write access in general
	public static int[] bellf_access(Graph graph, Vertex start, Vertex destination) {
		int[] readWriteAccess = {0,0,0};
		
		//initialize
		ArrayList<Integer> distance = new ArrayList<Integer>(); 
		ArrayList<Vertex> predecessor = new ArrayList<Vertex>();
			
		for(Vertex v : graph.getVertexes()) {
			readWriteAccess[0]++;
			readWriteAccess[1]++;
			if(v == start) {
				distance.add(0);
				readWriteAccess[2]++;
				predecessor.add(v);
				readWriteAccess[2]++;
			} else {
				distance.add(Integer.MAX_VALUE);
				readWriteAccess[2]++;
				predecessor.add(null);
				readWriteAccess[2]++;
			}
			readWriteAccess[1]++;
		}
				
		//iterate
		//number of iterations
		for(int i = 0; i < graph.getVertexes().size(); i++) {
			readWriteAccess[0]++;
			readWriteAccess[1]++;
			for(Vertex s : graph.getVertexes()) {
				readWriteAccess[0]++;
				readWriteAccess[1]++;
				int indexS = graph.getVertexes().indexOf(s);
				readWriteAccess[2]++;
				readWriteAccess[0]++;
				readWriteAccess[1]++;
				for(Vertex t : graph.getTarget(s)) {
					readWriteAccess[0]++;
					readWriteAccess[1]++;
					int indexT = graph.getVertexes().indexOf(t);
					readWriteAccess[2]++;
					readWriteAccess[0]++;
					readWriteAccess[1]++;
					int cost = add(distance.get(indexS), graph.getValE(s, t, "cost")); //evaluate new costs
					readWriteAccess[2]++;
					readWriteAccess[0]++;
					readWriteAccess[1]++;
					if(distance.get(indexT) > cost) { //if new costs are lower, replace old costs and change predecessor
						distance.set(indexT, cost);
						readWriteAccess[2]++;
						predecessor.set(indexT, s);
						readWriteAccess[2]++;
					}
					readWriteAccess[1]++;
				}
			}
		} //check for negative circle
		for(int i = 0; i < graph.getVertexes().size(); i++) {
			for(Vertex s : graph.getVertexes()) {
				readWriteAccess[0]++;
				readWriteAccess[1]++;
				int indexS = graph.getVertexes().indexOf(s);
				readWriteAccess[2]++;
				readWriteAccess[0]++;
				readWriteAccess[1]++;
				for(Vertex t : graph.getTarget(s)) {
					readWriteAccess[0]++;
					readWriteAccess[1]++;
					int indexT = graph.getVertexes().indexOf(t);
					readWriteAccess[2]++;
					readWriteAccess[0]++;
					readWriteAccess[1]++;
					int cost = add(distance.get(indexS), graph.getValE(s, t, "cost"));
					readWriteAccess[2]++;
					readWriteAccess[0]++;
					readWriteAccess[1]++;
					if(distance.get(indexT) > cost) {
						System.out.println("negativer Kreis gefunden");
						return readWriteAccess; //if there are lower costs again, return null and an errormessage.
					}
					readWriteAccess[1]++;
				}
			}
		}
		
		//get vertices for shortest way
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		Vertex tmp = destination; //from destination
		readWriteAccess[2]++;
		path.add(tmp);
		readWriteAccess[2]++;
		while(tmp != start) {
			readWriteAccess[1]++;
			if(tmp == null) {//to start
				return readWriteAccess;
			}
			readWriteAccess[1]++;
			tmp = predecessor.get(graph.getVertexes().indexOf(tmp));
			readWriteAccess[2]++;
			readWriteAccess[0]++;
			readWriteAccess[1]++;
			path.add(tmp);
			readWriteAccess[2]++;
		}
		Collections.reverse(path); //reverse, so it has right order
		readWriteAccess[1]++;
		readWriteAccess[2]++;
		return readWriteAccess;  //and return it.
	}
	
	public static void printData(String[] fileNameOfGraph, String[] start, String[] destination) {
		try {
			File file = new File(System.getProperty("user.dir")+"\\Messung.txt");
			FileWriter fw = new FileWriter(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			ArrayList<String> names = new ArrayList<String>();
			names.add("cost");
			Factory.setAttrNames(names);
			for(int i = 0; i < fileNameOfGraph.length; i++){
				bw.write(""+fileNameOfGraph[i]+" - von "+start[i]+" nach "+destination[i]+"\r\n");
				Graph g3 = Factory.importG(fileNameOfGraph[i]);
				ArrayList<Vertex> path = Utility.bellf(g3, Factory.createV(start[i]), Factory.createV(destination[i]));
				long zeit = Utility.bellf_runtime(g3, Factory.createV(start[i]), Factory.createV(destination[i]));
				int[] x = bellf_access(g3, Factory.createV(start[i]), Factory.createV(destination[i]));
				bw.write("Zeit in ms: " + zeit + "\r\n");
				String weg = new String("Weg: ");
				if(path == null) {
					weg = weg.concat("kein Weg gefunden");
				} else {
					for(Vertex v : path) {
						weg = weg.concat(v.getName() + ", ");
					}
				}
				bw.write(weg.substring(0, weg.length() - 2));
				bw.write("\r\nZugriffe: Graph lesen: "+ x[0]+"; lesen: "+ x[1]+"; schreiben: "+ x[2]+"\r\n\r\n");
				
			}
			bw.close();
		} catch (IOException e) {}
	}
	
	public static void main(String[] args) {
		String[] fileNames = {"graph_eigener", "graph_03", "graph_04", "graph_06", "graph_06"};
		String[] startVertices = {"s", "u", "v5", "v1", "v9"};
		String[] destinationVertices = {"d", "s", "v9", "v9", "v9"};
		
		printData(fileNames, startVertices,destinationVertices);
	}
}
