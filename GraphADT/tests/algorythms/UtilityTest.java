package algorythms;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import objects.Factory;
import objects.Graph;
import objects.Vertex;

public class UtilityTest {
	Vertex v1 = Factory.createV("eins");
	Vertex v2 = Factory.createV("zwei");
	Vertex v3 = Factory.createV("drei");
	Vertex v4 = Factory.createV("vier");
	Vertex v5 = Factory.createV("fünf");
	Vertex v6 = Factory.createV("sechs");
	Vertex v7 = Factory.createV("sieben");
	Vertex v8 = Factory.createV("acht");
	Vertex v9 = Factory.createV("neun");
	Vertex v10 = Factory.createV("zehn");
	Vertex v11 = Factory.createV("elf");
	Vertex v12 = Factory.createV("zwölf");
	Vertex v13 = Factory.createV("13");
	Vertex v14 = Factory.createV("14");

	@Test
	public void runtimeTest() {
		Graph g = Factory.createG(v1);
		//Befüllung
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
		
		
		
		System.out.println("Versuch1");
		
		System.out.println(Utility.floydW_runtime_exact(g,v1,v2,"all"));
		System.out.println(Utility.floydW_runtime_exact(g,v1,v2,"init"));
		System.out.println(Utility.floydW_runtime_exact(g,v1,v2,"initM"));
		System.out.println(Utility.floydW_runtime_exact(g,v1,v2,"initFM"));
		System.out.println(Utility.floydW_runtime_exact(g,v1,v2,"algo"));
		System.out.println(Utility.floydW_runtime_exact(g,v1,v2,""));
		
		System.out.println("Versuch2");
		
		System.out.println(Utility.floydW_runtime_exact(g,v5,v13,"all"));
		
		System.out.println("Versuch3");
		System.out.println(Utility.floydW_runtime_exact(g,v1,v7,"all"));
//		
//		System.out.println("Großer Graph1");
//		Graph z = Factory.importG("gra");
//		System.out.println(z);
//		ArrayList<Vertex> vertexList = z.getVertexes();
//		
//		System.out.println("Großer Graph2");
//		Graph h = Factory.importG("graph");
//		ArrayList<Vertex> vertexList2 = h.getVertexes();
//		Vertex l1 = h.getVertexes().get(1);
//		Vertex l2 = h.getVertexes().get(20);
//		System.out.println(Utility.floydW_runtime_exact(h,l1,l2,"all"));
		
		System.out.println("Zugriffversuch 0");
		
		System.out.println(Utility.floydW_accesses(g,v1,v5));
		
		System.out.println("Zugriffversuch 1");
		
		System.out.println(Utility.floydW_accesses_exakt(g,v1,v5,0));
		System.out.println(Utility.floydW_accesses_exakt(g,v1,v5,1));
		System.out.println(Utility.floydW_accesses_exakt(g,v1,v5,2));

		System.out.println("Zugriffversuch 2");
		
		System.out.println(Utility.floydW_accesses_exakt(g,v2,v6,3));
	}
}
