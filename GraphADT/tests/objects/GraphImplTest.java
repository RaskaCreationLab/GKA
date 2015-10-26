package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GraphImplTest {

	Vertex v1 = Factory.createV("eins");
	Vertex v2 = Factory.createV("zwei");
	Vertex v3 = Factory.createV("drei");
	Vertex vspecial = Factory.createV("#vier");
	Vertex vnull = null;
	
	@Test
	public void testAddVertex() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		assertEquals(2, g.getVertexes().size());
		assertEquals(1, g.getVertexes().indexOf(v2));
		g.addVertex(vspecial);
		assertEquals(3, g.getVertexes().size());
		assertEquals(2, g.getVertexes().indexOf(vspecial));
		g.addVertex(vnull);
		assertEquals(4, g.getVertexes().size());
	}

	@Test
	public void testDeleteVertex() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.deleteVertex(vspecial);
		assertEquals(3, g.getVertexes().size());
		g.deleteVertex(v2);
		assertEquals(2, g.getVertexes().size());
		assertEquals(1, g.getVertexes().indexOf(v3));
	}

	@Test
	public void testAddEdge() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addEdge(v1, v3);
		g.addEdge(v3, vspecial);
		assertEquals(2, g.getEdges().size());
		assertEquals(true, g.getEdges().indexOf(v1) == 0);
		assertEquals(true, g.getEdges().indexOf(v2) == 1);
	}

	@Test
	public void testDeleteEdge() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addEdge(v2, v1);
		assertEquals(4, g.getEdges().size());
		g.deleteEdge(v1,v2);
		assertEquals(2, g.getEdges().size());
		g.deleteEdge(v1, v3);
		assertEquals(2, g.getEdges().size());
		assertEquals(true, g.getEdges().indexOf(v2) == 0);
		assertEquals(true, g.getEdges().indexOf(v1) == 1);
	}

	@Test
	public void testSetAtE() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.setAtE(v1, v2, "attr1", 1);
		g.setAtE(v1, v2, "attr,2", 2);
		g.setAtE(v1, v3, "attr3", 3);
		assertEquals(1, g.getValE(v1, v2, "attr1"));
		assertEquals(2, g.getValE(v1, v2, "attr,2"));
		assertEquals(-999999999, g.getValE(v1, v3, "attr1"));
		assertEquals(-999999999, g.getValE(v1, v2, "attr2"));
		g.setAtE(v1, v2, "attr1", 5);;
		assertEquals(5, g.getValE(v1, v2, "attr1"));
	}

	@Test
	public void testSetAtV() {
		Graph g = Factory.createG(v1);
		g.setAtV(v1, "attr1", 1);
		g.setAtV(v1, "attr,2", 2);
		g.setAtV(v2, "attr3", 3);
		assertEquals(1, g.getValV(v1, "attr1"));
		assertEquals(2, g.getValV(v1, "attr,2"));
		assertEquals(-999999999, g.getValV(v1, "attr3"));
		assertEquals(-999999999, g.getValV(v2, "attr1"));
		g.setAtV(v1, "attr1", 5);
		assertEquals(5, g.getValV(v1, "attr1"));
	}
	
	@Test
	public void testExportG() {
		Vertex v1 = Factory.createV("eins");
		Vertex v2 = Factory.createV("zwei");
		Vertex v3 = Factory.createV("drei");
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addEdge(v1, v3);
		g.addEdge(v2, v1);
		g.setAtE(v1, v3, "kosten", 30);
		g.setAtE(v1, v3, "weg", 20);
		g.exportG("graph");
	}
	
	@Test
	public void testGetIncident() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addVertex(v3);
		assertEquals(0, g.getIncident(v3).size());
		assertEquals(2, g.getIncident(v1).size());
		assertEquals(0, g.getIncident(vspecial).size());
		assertEquals(0, g.getIncident(v2).indexOf(v1));
		assertEquals(1, g.getIncident(v2).indexOf(v2));
		assertEquals(true, g.getIncident(v2).contains(v1));
	}

	@Test
	public void testGetAdjacent() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addVertex(v3);
		assertEquals(0, g.getAdjacent(v3).size());
		assertEquals(1, g.getAdjacent(v1).size());
		assertEquals(1, g.getAdjacent(v2).size());
		assertEquals(0, g.getAdjacent(vspecial).size());
		assertEquals(0, g.getAdjacent(v2).indexOf(v1));
		assertEquals(0, g.getAdjacent(v1).indexOf(v2));
		g.addEdge(v3, v1);
		assertEquals(2, g.getAdjacent(v1).size());
		
	}

	@Test
	public void testGetTarget() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addVertex(v3);
		assertEquals(0, g.getTarget(v3).size());
		assertEquals(1, g.getTarget(v1).size());
		assertEquals(0, g.getTarget(v2).size());
		assertEquals(0, g.getTarget(vspecial).size());
		g.addEdge(v3, v1);
		assertEquals(1, g.getTarget(v1).size());
	}

	@Test
	public void testGetSource() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addVertex(v3);
		assertEquals(0, g.getSource(v3).size());
		assertEquals(0, g.getSource(v1).size());
		assertEquals(1, g.getSource(v2).size());
		assertEquals(0, g.getSource(vspecial).size());
		g.addEdge(v3, v1);
		assertEquals(1, g.getSource(v1).size());
	}

	@Test
	public void testGetEdges() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		assertEquals(0, g.getEdges().size());
		g.addEdge(v1, v2);
		assertEquals(2, g.getEdges().size());
		assertEquals(0, g.getEdges().indexOf(v1));
		assertEquals(1, g.getEdges().indexOf(v2));
	}

	@Test
	public void testGetVertexes() {
		Graph g = Factory.createG(v1);
		assertEquals(1, g.getVertexes().size());
	}

	@Test
	public void testGetValE() {
		Graph g = Factory.createG(v1);
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.setAtE(v1, v2, "attr", 1);
		assertEquals(1, g.getValE(v1, v2, "attr"));
		assertEquals(-999999999, g.getValE(v1, v2, "attr2"));
		assertEquals(-999999999, g.getValE(v2, v1, "attr"));
		assertEquals(-999999999, g.getValE(v3, vspecial, "attr"));
	}

	@Test
	public void testGetValV() {
		Graph g = Factory.createG(v1);
		g.setAtV(v1, "attr", 1);
		assertEquals(1, g.getValV(v1, "attr"));
		assertEquals(-999999999, g.getValV(v1, "attr2"));
		assertEquals(-999999999, g.getValV(v2, "attr"));
	}

}
