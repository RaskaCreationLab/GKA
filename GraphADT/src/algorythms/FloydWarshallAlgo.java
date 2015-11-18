package algorythms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import objects.Graph;
import objects.Vertex;

public class FloydWarshallAlgo {
	// Distanzmatrix: Kürzester Weg von v1 zu v2
	public static ArrayList<ArrayList<Integer>> dmat = new ArrayList<ArrayList<Integer>>();
	// Transitmatrix: Höchste Ecke auf dem Weg
	public static ArrayList<ArrayList<Integer>> tmat = new ArrayList<ArrayList<Integer>>();
	// Errorwert & Maxwert
	static int MAX_VALUE = 99999999;
	static int ERROR_VALUE = -999999999;

	public FloydWarshallAlgo(){}

	public static void initializeMatrixes(Graph alGraph, ArrayList<Vertex> vertexList){
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
				tmat.get(j).add(-1);
			}
		}
	}
	
	// Initialisierung
	public static void initializeFromGraph(Graph graph, ArrayList<Vertex> vertexList) {
		initializeMatrixes(graph,vertexList);
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
	}
	
	// Algorithmus
	public static ArrayList<Vertex> algo(Graph graph, Vertex start, Vertex destination) {
		ArrayList<Vertex> vertexList = graph.getVertexes();
		int vSize = vertexList.size();
		initializeFromGraph(graph, vertexList);
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
		//Berechnung des schnellsten Weges
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
		
		if(resultList.size() == 2){
			Vertex r0 = resultList.get(0);
			Vertex r1 = resultList.get(1);
			int r01 = vertexList.indexOf(r0);
			int r10 = vertexList.indexOf(r1);
			if(r01 == r10 && dmat.get(r01).get(r10) != 0){
				return null;
			}
		}
		//Umdrehen um die Reihenfolge von Start zu Destination zu haben
		Collections.reverse(resultList);
		return resultList;
	}
}
