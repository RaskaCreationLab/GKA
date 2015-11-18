package algorithms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import objects.Factory;
import objects.Graph;
import objects.Vertex;

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
		long starttime = System.nanoTime();
		bellf(graph, start, destination);
		return System.nanoTime() - starttime;
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
				bw.write("Bellman-Ford:\r\n");
				Graph g3 = Factory.importG(fileNameOfGraph[i]);
				ArrayList<Vertex> path = Utility.bellf(g3, Factory.createV(start[i]), Factory.createV(destination[i]));
				long zeit = Utility.bellf_runtime(g3, Factory.createV(start[i]), Factory.createV(destination[i]));
				int[] x = bellf_access(g3, Factory.createV(start[i]), Factory.createV(destination[i]));
				bw.write("Zeit in ns: " + zeit + "\r\n");
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
				bw.write("Floyd-Warshall:\r\n");
				bw.write("Zeit in ns: "+floydW_runtime(g3, Factory.createV(start[i]), Factory.createV(destination[i])));
				bw.write(floydW_accesses_exact(g3, Factory.createV(start[i]), Factory.createV(destination[i]),4));
				bw.write("-----\r\n\r\n");
			}
			bw.close();
		} catch (IOException e) {}
	}
	
	//floyd-Wahrshall
	
	public static ArrayList<Vertex> floydW(Graph graph, Vertex start, Vertex destination){
		if(graph.getVertexes().contains(start) && graph.getVertexes().contains(destination)){
			return FloydWarshallAlgo.algo(graph, start, destination);
		}else{
			return null;
		}
	}
	
	public static long floydW_runtime(Graph graph, Vertex start, Vertex destination){
		long startTime = System.nanoTime();
		FloydWarshallRuntime.algo(graph, start, destination);
		long endTime = System.nanoTime();
		return (endTime - startTime);
	}
	
	public static String floydW_runtime_exact(Graph graph, Vertex start, Vertex destination, String time){
		long startTime = System.nanoTime();
		FloydWarshallRuntime.algo(graph, start, destination);
		long endTime = System.nanoTime();
				
		long initS = FloydWarshallRuntime.getInitializeStart();
		long initE = FloydWarshallRuntime.getInitializeEnd();
		long initFS = FloydWarshallRuntime.getInitializeFromGraphStart();
		long initFE = FloydWarshallRuntime.getInitializeFromGraphEnd();
		long algS = FloydWarshallRuntime.getAlgorythmStart();
		long algE = FloydWarshallRuntime.getAlgorythmEnd();
		
		if(time.equals("all")){	
			return "Zeit " + ((endTime - startTime)/1000000)+ " ms";
		} else if(time.equals("initM")){
			return "Zeit der Matrixanfangsinitialisierung: " +(initE - initS)/1000000 +" ms";
		} else if(time.equals("initFM")){
			return "Zeit der Initialisierung durch Kanten: " + (initFE - initFS)/1000000 +" ms";
		} else if(time.equals("algo")){
			return "Zeit des Algorithmus: " + (algE - algS)/1000000 +" ms";
		} else if(time.equals("init")){
			return "Zeit der gesamten Initialisierung: "+((initE - initS)+(initFE - initFS))/1000000 +" ms";	
		} else {
			return ""+((algE - algS)+(initE - initS)+(initFE - initFS));
			//"\r\nZeit der Matrixanfangsinitialisierung: " +(initE - initS) +" ns" + 
			//"\r\nZeit der Initialisierung durch Kanten: " + (initFE - initFS) +" ns"+
			//"\r\nZeit der gesamten Initialisierung: "+((initE - initS)+(initFE - initFS))+" ns"+
			//"\r\nZeit des Algorithmus: " + (algE - algS)+" ns";
		}
	}
	
	public static int floydW_accesses(Graph graph, Vertex start, Vertex destination){
		ArrayList<Integer> r = FloydWarshallAccesses.algo(graph,start,destination);
		if(r == null){
			return -1;
		}
		return r.get(0)+r.get(1)+r.get(2);
	}
	
	public static String floydW_accesses_exact(Graph graph, Vertex start, Vertex destination, int counter){
		ArrayList<Integer> r = FloydWarshallAccesses.algo(graph,start,destination);
		if(r == null){
			return "\r\nNegativen Kreis gefunden\r\n\r\n";
		}
		if(counter == 0){
			return "Lesezugriffe: "+ r.get(0);
		} else if(counter == 1){
			return "Schreibezugriffe: "+r.get(1);
		} else if(counter == 2){
			return "Zugriffe auf GraphADT: "+r.get(2);
		} else {
			return "\r\nZugriffe: Graph lesen: "+ r.get(2) +"; lesen: "+ r.get(0) +"; schreiben: "+ r.get(1) +"\r\n\r\n";
		}
	}
	
	public static void main(String[] args) {
		String[] fileNames = {"graph_eigener", "graph_03", "graph_04", "graph_06", "graph_06"};
		String[] startVertices = {"s", "u", "v5", "v1", "v9"};
		String[] destinationVertices = {"d", "s", "v9", "v9", "v9"};
		
		printData(fileNames, startVertices,destinationVertices);
	}
}
