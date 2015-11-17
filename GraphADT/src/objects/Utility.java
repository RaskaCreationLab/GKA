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
					int cost = add(distance.get(indexS), graph.getValE(s, t, "cost"));
					if(distance.get(indexT) > cost) {
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
						return null;
					}
				}
			}
		}
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		Vertex tmp = destination;
		path.add(tmp);
		while(tmp != start) {
			if(tmp == null)
				return null;
			tmp = predecessor.get(graph.getVertexes().indexOf(tmp));
			path.add(tmp);
		}
		Collections.reverse(path);
		return path;
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
	
	public static int bellf_access(Graph graph, Vertex start, Vertex destination) {
		return 0;
	}
	
	public static void main(String[] args) {
		try {
			File file = new File(System.getProperty("user.dir")+"\\Messung.txt");
			FileWriter fw = new FileWriter(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("graph_eigener - von 's' nach 'd' \r\n");
			ArrayList<String> names = new ArrayList<String>();
			names.add("cost");
			Factory.setAttrNames(names);
			Graph g3 = Factory.importG("graph_eigener");
			ArrayList<Vertex> path = Utility.bellf(g3, Factory.createV("s"), Factory.createV("d"));
			long zeit = Utility.bellf_runtime(g3, Factory.createV("s"), Factory.createV("d"));
			bw.write("Zeit in ms: " + zeit + "\r\n");
			String weg = new String("Weg: ");
			for(Vertex v : path) {
				weg = weg.concat(v.getName() + ", ");
			}
			bw.write(weg.substring(0, weg.length() - 2));
			bw.close();
		} catch (IOException e) {}
	}
}
