package objects;

import static org.junit.Assert.*;

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

}
