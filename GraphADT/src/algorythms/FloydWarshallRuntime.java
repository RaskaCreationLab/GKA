package algorythms;

import java.util.ArrayList;
import java.util.Collections;

import objects.Graph;
import objects.Vertex;

public class FloydWarshallRuntime {
		
		// Distanzmatrix: Kürzester Weg von v1 zu v2
		public static ArrayList<ArrayList<Integer>> dmat = new ArrayList<ArrayList<Integer>>();
		
		// Transitmatrix: Höchste Ecke auf dem Weg
		public static ArrayList<ArrayList<Integer>> tmat = new ArrayList<ArrayList<Integer>>();
		
		// Errorwert & Maxwert
		static int MAX_VALUE = 99999999;
		static int ERROR_VALUE = -999999999;
		
		//Times
		static long initializeStart;
		static long initializeEnd;
		static long initializeFromGraphStart;
		static long initializeFromGraphEnd;
		static long algorythmStart;
		static long algorythmEnd;
		
		//Getter
		public static long getInitializeStart(){
			return initializeStart;
		}
		
		public static long getInitializeEnd(){
			return initializeEnd;
		}

		public static long getInitializeFromGraphStart(){
			return initializeFromGraphStart;
		}

		public static long getInitializeFromGraphEnd(){
			return initializeFromGraphEnd;
		}
	
		public static long getAlgorythmStart(){
			return algorythmStart;
		}

		public static long getAlgorythmEnd(){
			return algorythmEnd;
		}

		public FloydWarshallRuntime() {}

		//Initialisierung der Matrizen über die Vertexlist
		public static void initializeMatrixes(Graph alGraph, ArrayList<Vertex> vertexList){
			initializeStart = System.nanoTime();
			dmat.clear();
			tmat.clear();
			int vSize = vertexList.size();		
			//Initialisierung dmat,tmat
			for(int j = 0; j < vSize; j++){
				dmat.add(new ArrayList<Integer>());
				tmat.add(new ArrayList<Integer>());
				for(int i = 0; i < vSize; i++){
					if(i == j){
						dmat.get(j).add(0);
					} else {
						dmat.get(j).add(MAX_VALUE);
					}
					//Setzung auf -1 um Indexproblemen entgegenzukommen
					tmat.get(j).add(-1);
				}
			}
			initializeEnd = System.nanoTime();
		}
		
		// Initialisierung der dmat durch die Werte der Kanten
		public static void initializeFromGraph(Graph graph, ArrayList<Vertex> vertexList) {
			initializeMatrixes(graph,vertexList);
			initializeFromGraphStart = System.nanoTime();
			//Vertex auf targets untersuchen
			for (int i = 0; i < vertexList.size(); i++) {					
				Vertex vertex = vertexList.get(i);
				ArrayList<Vertex> targetList = graph.getTarget(vertex);
				int tsize = targetList.size();
			//Alle Targetvertexes auf Kanten	
				for(int j = 0; j < tsize; j++) {
					Vertex target = targetList.get(j);
					int cost = graph.getValE(vertex,target,"cost");
					if (cost != ERROR_VALUE) {
						dmat.get(i).set(vertexList.indexOf(target),cost);
					}
				}
			}
			initializeFromGraphEnd = System.nanoTime();
		}
		
		// Algorithmus
		public static ArrayList<Long> algo(Graph graph, Vertex start, Vertex destination) {
			ArrayList<Vertex> vertexList = graph.getVertexes();
			int vSize = vertexList.size();
			initializeFromGraph(graph, vertexList);
			algorythmStart = System.nanoTime();
			for (int j = 0; j < vSize; j++) {
				for (int i = 0; i < vSize; i++) {	
					if(i == j){dmat.get(i).set(j,0);}
					for (int k = 0; k < vSize; k++) {
						if(k != j){
							//dik Berechnungen
							int dik = dmat.get(i).get(k);
							int dijk = dmat.get(i).get(j) + dmat.get(j).get(k);
							int min = dik > dijk ? dijk : dik;
							//Setzung des Minwerts für schnelleren Weg
							dmat.get(i).set(k,min);
							//Änderung von tmat
							int newDik = dmat.get(i).get(k);
							if(dik != newDik){
								tmat.get(i).set(k,j);
							}
							//Wenn Negativ-Kreis
							if(0 > dmat.get(i).get(i)) {
								return null;
							}
						}
					}
				}
			}
			ArrayList<Vertex> resultList = new ArrayList<Vertex>();
			Vertex tmp = destination;
			int startIndex = vertexList.indexOf(start);
			int tmpIndex = vertexList.indexOf(tmp);
			//Durchgang durch die tmat zur Findung des schnellten Weges
			do{
				resultList.add(tmp);
				int predecessorIndex = tmat.get(startIndex).get(tmpIndex);
				if(predecessorIndex == -1)
					tmp = start;
				else {
					tmp = vertexList.get(predecessorIndex);
				}
				tmpIndex = vertexList.indexOf(tmp);
			}
			while(tmp != start);
			resultList.add(start);
			Collections.reverse(resultList);
			algorythmEnd = System.nanoTime();
			ArrayList<Long> timeList = new ArrayList<Long>();	
			timeList.add(initializeStart);
			timeList.add(initializeEnd);
			timeList.add(initializeFromGraphStart);
			timeList.add(initializeFromGraphEnd);
			timeList.add(algorythmStart);
			timeList.add(algorythmEnd);
			return timeList;
		}
}
