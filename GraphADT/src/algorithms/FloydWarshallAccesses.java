package algorithms;

import java.util.ArrayList;
import java.util.Collections;

import objects.Graph;
import objects.Vertex;

public class FloydWarshallAccesses {
	// Distanzmatrix: K�rzester Weg von v1 zu v2
		public static ArrayList<ArrayList<Integer>> dmat = new ArrayList<ArrayList<Integer>>();
		// Transitmatrix: H�chste Ecke auf dem Weg
		public static ArrayList<ArrayList<Integer>> tmat = new ArrayList<ArrayList<Integer>>();
		// Errorwert & Maxwert
		static int MAX_VALUE = 99999999;
		static int ERROR_VALUE = -999999999;
		
		static int counterRead;
		static int counterWrite;
		static int counterGraph;

		public FloydWarshallAccesses() {}

		public static void initializeMatrixes(Graph alGraph, ArrayList<Vertex> vertexList){
			dmat.clear();
			tmat.clear();
			counterWrite++;
			counterWrite++;		
			int vSize = vertexList.size();
			counterRead++;
			//Initialisierung dmat,tmat
			for(int j = 0; j < vSize; j++){
				dmat.add(new ArrayList<Integer>());
				tmat.add(new ArrayList<Integer>());
				counterWrite++;
				counterWrite++;
				for(int i = 0; i < vSize; i++){
					if(i == j){
						dmat.get(j).add(0);
						counterWrite++;
					} else {
						dmat.get(j).add(MAX_VALUE);
						counterWrite++;
					}
					tmat.get(j).add(-1);
					counterWrite++;
				}
			}
		}
		
		// Initialisierung
		public static void initializeFromGraph(Graph graph, ArrayList<Vertex> vertexList) {
			initializeMatrixes(graph,vertexList);
			//Vertex auf targets untersuchen
			for (int i = 0; i < vertexList.size(); i++) {					
				Vertex vertex = vertexList.get(i);
				counterRead++;
				ArrayList<Vertex> targetList = graph.getTarget(vertex);
				counterGraph++;
				counterRead++;
				int tsize = targetList.size();
				counterRead++;
			//Alle Targetvertexes auf Kanten	
				for(int j = 0; j < tsize; j++) {
					Vertex target = targetList.get(j);
					counterRead++;
					int cost = graph.getValE(vertex,target,"cost");
					counterGraph++;
					counterRead++;
					if (cost != ERROR_VALUE) {
						dmat.get(i).set(vertexList.indexOf(target),cost);
						counterWrite++;
						counterRead++;					
					}
					counterRead++;
				}
			}
		}
		
		// Algorithmus
		public static ArrayList<Integer> algo(Graph graph, Vertex start, Vertex destination) {
			counterWrite = 0;
			counterRead = 1;
			counterGraph = 0;
			ArrayList<Vertex> vertexList = graph.getVertexes();
			for(int o = 0; o < vertexList.size(); o++){
				counterGraph++;
				counterWrite++;
			}
			int vSize = vertexList.size();
			counterRead++;
			initializeFromGraph(graph, vertexList);
			for (int j = 0; j < vSize; j++) {
				for (int i = 0; i < vSize; i++) {	
					if(i == j){
						dmat.get(i).set(j,0); 
						counterWrite++;
					}
					for (int k = 0; k < vSize; k++) {
						if(k != j){
							//dik Berechnungen
							int dik = dmat.get(i).get(k);
							int dijk = dmat.get(i).get(j) + dmat.get(j).get(k);
							counterRead++;
							counterRead++;
							counterRead++;
							counterWrite++;
							counterWrite++;
							int min = dik > dijk ? dijk : dik;
							counterWrite++;
							counterRead++;
							counterRead++;
							//Setzung des Minwerts f�r schnelleren Weg
							dmat.get(i).set(k,min);
							counterWrite++;
							//�nderung von tmat
							int newDik = dmat.get(i).get(k);
							counterRead++;
							if(dik != newDik){
								tmat.get(i).set(k,j);
								counterWrite++;
							}
//							Wenn Negativ-Kreis
							if(0 > dmat.get(i).get(i)) {
								return null;
							}
						}
					}
				}
			}
			ArrayList<Vertex> resultList = new ArrayList<Vertex>();
			Vertex tmp = destination;
			counterWrite++;
			int startIndex = vertexList.indexOf(start);
			counterRead++;
			int tmpIndex = vertexList.indexOf(tmp);
			counterRead++;
			do{
				resultList.add(tmp);
				counterWrite++;
				int predecessorIndex = tmat.get(startIndex).get(tmpIndex);
				counterWrite++;
				counterRead++;
				if(predecessorIndex == -1){
					tmp = start;
					counterWrite++;
				}
				else {
					tmp = vertexList.get(predecessorIndex);
					counterRead++;
				}
				tmpIndex = vertexList.indexOf(tmp);
				counterRead++;
			}
			while(tmp != start);
			resultList.add(start);
			counterWrite++;
			Collections.reverse(resultList);
			counterWrite++;
			ArrayList<Integer> r = new ArrayList<Integer>();
			r.add(counterRead);
			r.add(counterWrite);
			r.add(counterGraph);
			return r;
		}
}

