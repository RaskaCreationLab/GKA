package objects;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FactoryTest {

	@Test
	public void testCreateV() {
		assertEquals("Hamburg", Factory.createV("Hamburg").getName());
		assertEquals("haha,no", Factory.createV("haha,no").getName());
		assertEquals("hehe$", Factory.createV("hehe$").getName());
		assertEquals("Wandsbek Markt", Factory.createV("Wandsbek Markt").getName());
		assertEquals("", Factory.createV("").getName());
	}

	@Test
	public void testCreateG() {
		Vertex v1 = Factory.createV("eins");
		Vertex v2 = Factory.createV("zwei");
		Vertex v3 = null;
		assertEquals(1,Factory.createG(v1).getVertexes().size());
		assertEquals(1,Factory.createG(v2).getVertexes().size());
		assertEquals(1,Factory.createG(v3).getVertexes().size());
	}
	
	@Test
	public void testImportG() {
		File folder = new File(System.getProperty("user.dir"));
		File tmpFile = null;
		try{
			tmpFile = File.createTempFile("graph", "graph", folder);
			assertEquals(null, Factory.importG("graphe")); //teste mit nicht existenter Datei
			assertEquals(null, Factory.importG("graph")); //teste mit leerer Datei
			
			ArrayList<String> test = new ArrayList<String>(); 
			test.add("#gerichtet");
			test.add("vertex1,vertex2,kosten 50,weg 10"); //teste mit richtiger datei mit attributen
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
			
			
		} catch (IOException e) {
			
		} finally {
			tmpFile.delete();
		}
	}

}
