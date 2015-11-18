package algorithms;

import java.util.ArrayList;

import org.junit.Test;

import algorithms.Utility2;
import objects.Factory;
import objects.Graph;
import objects.Vertex;

public class UtilityTest2 {
	Vertex v1 = Factory.createV("eins");
	Vertex v2 = Factory.createV("zwei");
	Vertex v3 = Factory.createV("drei");
	Vertex v4 = Factory.createV("vier");
	Vertex v5 = Factory.createV("f�nf");
	Vertex v6 = Factory.createV("sechs");
	Vertex v7 = Factory.createV("sieben");
	Vertex v8 = Factory.createV("acht");
	Vertex v9 = Factory.createV("neun");
	Vertex v10 = Factory.createV("zehn");
	Vertex v11 = Factory.createV("elf");
	Vertex v12 = Factory.createV("zw�lf");
	Vertex v13 = Factory.createV("13");
	Vertex v14 = Factory.createV("14");

	@Test
	public void runtimeTest() {
		//Importvorbereitung
		ArrayList<String> names = new ArrayList<String>();
		names.add("cost");
		Factory.setAttrNames(names);
		
		//Imports
		Graph z = Factory.importG("graph_03");
		ArrayList<Vertex> vertexList = z.getVertexes();
		
		Graph y = Factory.importG("graph_04");
		ArrayList<Vertex> vertexList2 = y.getVertexes();
		
		Graph x = Factory.importG("graph_06");
		ArrayList<Vertex> vertexList3 = x.getVertexes();
		
		Graph v = Factory.importG("graph_ung");
		ArrayList<Vertex> vertexList4 = v.getVertexes();
		
//		Graph h = Factory.importG("graph_02");
//		ArrayList<Vertex> vertexList2 = h.getVertexes();
		
		//Eigener Graph durch Bef�llung
		Graph g = Factory.createG(v1);
		//Bef�llung
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);
		g.addVertex(v6);
		g.addVertex(v7);
		g.addVertex(v8);
		g.addVertex(v9);
		g.addVertex(v10);
		g.addVertex(v11);
		g.addVertex(v12);
		g.addVertex(v13);
		g.addVertex(v14);
		
		g.addEdge(v1, v2);
		g.addEdge(v2, v3);
		g.addEdge(v3, v4);
		g.addEdge(v4, v5);
		g.addEdge(v5, v1);
		g.addEdge(v5, v6);
		g.addEdge(v6, v1);
		g.addEdge(v4, v7);
		g.addEdge(v10, v2);
		g.addEdge(v8, v6);
		g.addEdge(v8, v1);
		g.addEdge(v11, v1);
		g.addEdge(v12, v2);
		g.addEdge(v11, v8);
		g.addEdge(v14, v2);
		g.addEdge(v13, v9);
		g.addEdge(v9, v13);
		g.addEdge(v2, v9);
		g.addEdge(v5, v7);
		g.addEdge(v2, v10);
		g.addEdge(v10, v5);
		g.addEdge(v10, v3);
		g.addEdge(v5, v12);
		g.addEdge(v3, v12);
		g.addEdge(v4, v7);
		
		g.setAtE(v1, v2, "cost", 1);
		g.setAtE(v2, v3, "cost", 2);
		g.setAtE(v3, v4, "cost", 1);
		g.setAtE(v4, v5, "cost", 3);
		g.setAtE(v5, v6, "cost", 2);
		g.setAtE(v5, v1, "cost", 1);
		g.setAtE(v6, v1, "cost", 5);
		g.setAtE(v4, v7, "cost", 5);
		g.setAtE(v10, v2, "cost", 2);
		g.setAtE(v8, v6, "cost", 1);
		g.setAtE(v11, v8, "cost", 3);
		g.setAtE(v12, v2, "cost", 4);
		g.setAtE(v11, v1, "cost", 2);
		g.setAtE(v9, v13, "cost", 5);
		g.setAtE(v2, v9, "cost", 2);
		g.setAtE(v5, v7, "cost", 3);
		g.setAtE(v2, v10, "cost", 2);
		g.setAtE(v10, v5, "cost", 7);
		g.setAtE(v10, v3, "cost", 6);
		g.setAtE(v5, v12, "cost", 5);
		g.setAtE(v3, v12, "cost", 6);
		g.setAtE(v4, v7, "cost", 3);
		
		
		//Runtimeversuche
		System.out.println("Versuch1");
		
		System.out.println(Utility2.floydW_runtime_exact(g,v1,v2,"all"));
		System.out.println(Utility2.floydW_runtime_exact(g,v1,v2,"init"));
		System.out.println(Utility2.floydW_runtime_exact(g,v1,v2,"initM"));
		System.out.println(Utility2.floydW_runtime_exact(g,v1,v2,"initFM"));
		System.out.println(Utility2.floydW_runtime_exact(g,v1,v2,"algo"));
		System.out.println(Utility2.floydW_runtime_exact(g,v1,v2,""));
		
		System.out.println("Versuch2");
		
		System.out.println(Utility2.floydW_runtime_exact(g,v5,v13,"all"));
		
		System.out.println("Versuch3");
		System.out.println(Utility2.floydW_runtime_exact(g,v1,v7,"all"));
		
//		System.out.println("Gro�er Graph1");
//		
//		Vertex l1 = h.getVertexes().get(1);
//		Vertex l2 = h.getVertexes().get(20);
//		System.out.println(Utility2.floydW_runtime_exact(h,l1,l2,"all"));
		
		
		//Zugriffsversuche
		System.out.println("Zugriffversuch 0g");
		
		System.out.println(Utility2.floydW_accesses(g,v1,v5));
		
		System.out.println("Zugriffversuch 1g");
		
		System.out.println(Utility2.floydW_accesses_exact(g,v1,v5,0));
		System.out.println(Utility2.floydW_accesses_exact(g,v1,v5,1));
		System.out.println(Utility2.floydW_accesses_exact(g,v1,v5,2));

		System.out.println("Zugriffversuch 2g");
		
		System.out.println(Utility2.floydW_accesses_exact(g,v2,v6,3));
		
		//Versuchsaufbau
		System.out.println("Versuch u - s");
		
		System.out.println(Utility2.floydW_accesses_exact(z,vertexList.get(1),vertexList.get(0),0));
		System.out.println(Utility2.floydW_accesses_exact(z,vertexList.get(1),vertexList.get(0),1));
		System.out.println(Utility2.floydW_accesses_exact(z,vertexList.get(1),vertexList.get(0),2));
		System.out.println(Utility2.floydW_accesses_exact(z,vertexList.get(1),vertexList.get(0),3));
		
		System.out.println(Utility2.floydW_runtime_exact(z,vertexList.get(1),vertexList.get(0),"all"));
		System.out.println(Utility2.floydW_runtime_exact(z,vertexList.get(1),vertexList.get(0),"init"));
		System.out.println(Utility2.floydW_runtime_exact(z,vertexList.get(1),vertexList.get(0),"initM"));
		System.out.println(Utility2.floydW_runtime_exact(z,vertexList.get(1),vertexList.get(0),"initFM"));
		System.out.println(Utility2.floydW_runtime_exact(z,vertexList.get(1),vertexList.get(0),"algo"));
		System.out.println(Utility2.floydW_runtime_exact(z,vertexList.get(1),vertexList.get(0),""));
		
		System.out.println("Versuch graph_04");
		
		System.out.println(Utility2.floydW_accesses(y,vertexList2.get(0),vertexList2.get(8)));
		System.out.println(Utility2.floydW_accesses_exact(y,vertexList2.get(0),vertexList2.get(8),1));
		
		System.out.println(Utility2.floydW_runtime(y,vertexList2.get(0),vertexList2.get(8)));
		System.out.println(Utility2.floydW_runtime_exact(y,vertexList2.get(0),vertexList2.get(8),"all"));
		
		System.out.println("Versuch graph_06");

		System.out.println(Utility2.floydW_accesses(x,vertexList3.get(0),vertexList3.get(8)));
		System.out.println(Utility2.floydW_accesses_exact(x,vertexList3.get(0),vertexList3.get(8),1));
		
		System.out.println(Utility2.floydW_runtime(x,vertexList3.get(0),vertexList3.get(8)));
		System.out.println(Utility2.floydW_runtime_exact(x,vertexList3.get(0),vertexList3.get(8),"all"));
		
		System.out.println("Versuch graph_ung");
		
		System.out.println(Utility2.floydW_runtime(v,vertexList4.get(2),vertexList4.get(0)));
		System.out.println(Utility2.floydW_runtime_exact(v,vertexList4.get(2),vertexList4.get(0),"all"));
		
		
//		[17:46:23] Mr.Lowbob: graph_03 (ist relativ klein) von u zu s
//		[17:46:37] Mr.Lowbob: graph_04 als test f�r negativen kreis
//		[17:47:08] Mr.Lowbob: graph_06 ohne negative kreise aber mit negativen kanten von v1 zu v9
		
		
	}
}
