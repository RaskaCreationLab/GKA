package algorythms;

import java.util.ArrayList;

import objects.Graph;
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
			return "Zeit der Matrixanfangsinitialisierung " +(initE - initS)/1000000 +" ms";
		} else if(time.equals("initFM")){
			return "Zeit der Initialisierung durch Kanten " + (initFE - initFS)/1000000 +" ms";
		} else if(time.equals("algo")){
			return "Zeit des Algorithmus " + (algE - algS)/1000000 +" ms";
		} else if(time.equals("init")){
			return "Zeit der gesamten Initialisierung "+((initE - initS)+(initFE - initFS))/1000000 +" ms";	
		} else {
			return "Zeit " + ((endTime - startTime)/1000000)+ " ms";
		}
	}
	
	public static int floydW_accesses(Graph graph, Vertex start, Vertex destination){
		ArrayList<Integer> r = FloydWarshallAccesses.algo(graph,start,destination);
		if(r == null){
			return -1;
		}
		return r.get(0)+r.get(1)+r.get(2);
	}
	
	public static String floydW_accesses_exakt(Graph graph, Vertex start, Vertex destination, int counter){
		ArrayList<Integer> r = FloydWarshallAccesses.algo(graph,start,destination);
		if(r == null){
			return "Negativer Kreis";
		}
		if(counter == 0){
			return "Lesezugriffe: "+ r.get(0);
		} else if(counter == 1){
			return "Schreibezugriffe: "+r.get(1);
		} else if(counter == 2){
			return "Zugriffe auf GraphADT: "+r.get(2);
		} else {
			return "Zugriffe insgesamt: "+(r.get(0)+r.get(1));
		}
	}
}
