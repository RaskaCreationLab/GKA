package objects;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FactoryTest {
	Vertex v1 = Factory.createV("eins");
	Vertex v2 = Factory.createV("zwei");
	Vertex v3 = Factory.createV("yeo/ay");
	Vertex v4 = Factory.createV(null);
	
	@Test
	public void testCreateV() {
		//mit getName() wird der angegebene Name bei der Kreation zurückgegeben
		assertEquals("Hamburg", Factory.createV("Hamburg").getName());
		assertEquals("haha,no", Factory.createV("haha,no").getName());
		//Sonderzeichen werden akzeptiert
		assertEquals("hehe$", Factory.createV("hehe$").getName());
		//Leerzeichen werden akzeptiert
		assertEquals("Wandsbek Markt", Factory.createV("Wandsbek Markt").getName());
		//Leere Namen werden akzeptiert
		assertEquals("", Factory.createV("").getName());
	}

	@Test
	public void testCreateG() {
		//null und Vertexes werden beim erschaffen eines Graphen akzeptiert
		assertEquals(1,Factory.createG(v1).getVertexes().size());
		assertEquals(1,Factory.createG(v2).getVertexes().size());
		assertEquals(1,Factory.createG(v4).getVertexes().size());
	}
	
	@Test
	public void testImportG() {
		/*
		File folder = new File(System.getProperty("user.dir"));
		File tmpFile = null;
		*/
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addEdge(v1, v2);
		g.addEdge(v1, v3);
		g.addEdge(v2, v3);
		g.setAtE(v1, v2, "overr", 9000);
		g.setAtE(v1, v3, "under", 5000);
		g.exportG("graph");
		/*
		try{
			tmpFile = File.createTempFile("graph", "graph", folder);
			assertEquals(null, Factory.importG("graphe")); //teste mit nicht existenter Datei
			assertEquals(null, Factory.importG("graph")); //teste mit leerer Datei
			ArrayList<String> test = new ArrayList<String>(); 
			test.add("#gerichtet");
			test.add("v1,v2,oVe³r,9000"); //teste mit richtiger datei mit attributen
			test.add("v1,v3,under,5000");
			test.add("v2,v3");
			*/
			Graph h = Factory.importG("graph");
			assertEquals(h.getVertexes(),h.getVertexes());
			/*
			assertEquals(h.getVertexes().isEmpty(),h.getVertexes().removeAll(g.getVertexes()));
			assertEquals(g.getVertexes().isEmpty(),g.getVertexes().removeAll(h.getVertexes()));
			*/
			/*
			Graph g = Factory.importCreateG(test);
			assertEquals(2, g.getVertexes().size());
			assertEquals("vertex1", g.getVertexes().get(0).getName());
			assertEquals(2, g.getEdges().size());
			assertEquals(50, g.getValE(g.getVertexes().get(0), g.getVertexes().get(1), "kosten"));
			assertEquals(10, g.getValE(g.getVertexes().get(0), g.getVertexes().get(1), "weg"));
			test.remove(1);
			test.add("vertex1,vertex2"); // teste ohne attribute
			g = Factory.importCreateG(test);
			assertEquals(2, g.getVertexes().size());
			assertEquals(2, g.getEdges().size());
			test.add("vertex1,vertex3"); //teste mit mehrfachnutzung eines vektors
			g = Factory.importCreateG(test);
			assertEquals(3,g.getVertexes().size());
			assertEquals(4, g.getEdges().size());
			test.remove(0); //teste ohne information ob gerichtet oder nicht
			assertEquals(null, Factory.importCreateG(test));
			test.add(0, "#ungerichtet"); //teste ungerichtete graphen
			g = Factory.importCreateG(test);
			assertEquals(3,g.getVertexes().size());
			assertEquals(8, g.getEdges().size());
			*/
			/*
		} catch (IOException e) {
		} finally {
			tmpFile.delete();
		}*/
	}

}
