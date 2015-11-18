package algorythms;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import algorythms.FloydWarshallAlgo;
import objects.Factory;
import objects.Graph;
import objects.Vertex;

public class FloydWarshallTest {
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
	public void testAlgo() {
		ArrayList<Vertex> r = new ArrayList<Vertex>();
		Graph h = Factory.createG(v1);
		
		h.addVertex(v2);
		h.addVertex(v3);
		h.addVertex(v4);
		
		h.addEdge(v1, v2);
		h.addEdge(v2, v3);
		h.addEdge(v3, v2);
		h.addEdge(v4, v3);
		h.addEdge(v2, v4);
		h.addEdge(v1, v4);
		h.addEdge(v4, v1);
		
		h.setAtE(v1, v2, "cost", 1);
		h.setAtE(v2, v3, "cost", 2);
		h.setAtE(v3, v2, "cost", 2);
		h.setAtE(v4, v3, "cost", 2);
		h.setAtE(v2, v4, "cost", 1);
		h.setAtE(v4, v1, "cost", 2);
		h.setAtE(v1, v4, "cost", 3);
		
		r.add(v3);
		r.add(v2);
		assertEquals(r, FloydWarshallAlgo.algo(h, v3, v2));
		r.clear();
		
		r.add(v4);
		r.add(v1);
		r.add(v2);
		assertEquals(r, FloydWarshallAlgo.algo(h, v4, v2));
		
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
		
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		result.add(v1);
		result.add(v2);
		result.add(v3);
		result.add(v4);
		
		assertEquals(result, FloydWarshallAlgo.algo(g, v1, v4));
		
		result.clear();
		result.add(v1);
		result.add(v2);
		result.add(v10);
		
		assertEquals(result, FloydWarshallAlgo.algo(g, v1, v10));
		
		g.addEdge(v2,v2);
		g.setAtE(v2, v2, "cost", 0);
		
		result.clear();
		result.add(v2);
		result.add(v2);
		
		assertEquals(result,FloydWarshallAlgo.algo(g, v2, v2));
		
		result.clear();
		result.add(v2);
		result.add(v3);
		result.add(v12);
		
		assertEquals(result,FloydWarshallAlgo.algo(g, v2, v12));
		assertEquals(null,FloydWarshallAlgo.algo(g,v7,v8));
		
		result.clear();
		
		Graph g2 = Factory.createG(v1);
		g2.addVertex(v2);
		g2.addVertex(v3);
		g2.addVertex(v4);
		g2.addVertex(v5);
		g2.addVertex(v6);
		
		g2.addEdge(v1, v2);
		g2.addEdge(v2, v3);
		g2.addEdge(v3, v4);
		g2.addEdge(v4, v5);
		g2.addEdge(v5, v1);
		g2.addEdge(v5, v6);
		
		g2.setAtE(v1, v2, "cost", -1);
		g2.setAtE(v2, v3, "cost", -2);
		g2.setAtE(v3, v7, "cost", -3);
		g2.setAtE(v7, v2, "cost", -4);
		g2.setAtE(v7, v1, "cost", -4);
		g2.setAtE(v2, v10, "cost", -4);
		
		result.add(v1);
		result.add(v2);
		result.add(v3);
		result.add(v4);

		assertEquals(result,FloydWarshallAlgo.algo(g2,v1,v4));
		//Vertex nicht vorhanden -> null
		result.clear();
		assertEquals(null, FloydWarshallAlgo.algo(g2,v2,v7));
		assertEquals(null, FloydWarshallAlgo.algo(g2,v6,v1));
		
	}
}
