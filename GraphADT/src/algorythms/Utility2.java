package algorythms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import objects.Factory;
import objects.Graph;
import objects.Utility;
import objects.Vertex;

public class Utility2 {
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
		return (endTime - startTime)/1000000;
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
			return "\r\nZeit der Matrixanfangsinitialisierung: " +(initE - initS) +" ns" + 
					"\r\nZeit der Initialisierung durch Kanten: " + (initFE - initFS) +" ns"+
					"\r\nZeit der gesamten Initialisierung: "+((initE - initS)+(initFE - initFS))+" ns"+
					"\r\nZeit des Algorithmus: " + (algE - algS)+" ns";
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
			return "\r\nNegativen Kreis gefunden";
		}
		if(counter == 0){
			return "Lesezugriffe: "+ r.get(0);
		} else if(counter == 1){
			return "Schreibezugriffe: "+r.get(1);
		} else if(counter == 2){
			return "Zugriffe auf GraphADT: "+r.get(2);
		} else {
			return "\r\nZugriffe insgesamt: "+(r.get(0)+r.get(1)) +"\r\nLesezugriffe: "+ r.get(0) + "\r\nSchreibezugriffe: "+r.get(1) +"\r\nZugriffe auf GraphADT: "+r.get(2);
		}
	}
	
	public static void printData(String[] fileNameOfGraph, String[] start, String[] destination) {
		try {
			File file = new File(System.getProperty("user.dir")+"\\MessungFloyd.txt");
			FileWriter fw = new FileWriter(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			ArrayList<String> names = new ArrayList<String>();
			names.add("cost");
			Factory.setAttrNames(names);
			for(int i = 0; i < fileNameOfGraph.length; i++){
				bw.write("\r\n"+fileNameOfGraph[i]+" - von "+start[i]+" nach "+destination[i]+"\r\n");
				Graph g3 = Factory.importG(fileNameOfGraph[i]);
				ArrayList<Vertex> path = Utility.bellf(g3, Factory.createV(start[i]), Factory.createV(destination[i]));
				String weg = new String("Weg: ");
				if(path == null) {
					weg = weg.concat("kein Weg gefunden");
				} else {
					for(Vertex v : path) {
						weg = weg.concat(v.getName() + ", ");
					}
				}
				bw.write(weg.substring(0, weg.length() - 2));
				bw.write(floydW_accesses_exact(g3, Factory.createV(start[i]), Factory.createV(destination[i]),4));
				bw.write(floydW_runtime_exact(g3, Factory.createV(start[i]), Factory.createV(destination[i]),""));
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
