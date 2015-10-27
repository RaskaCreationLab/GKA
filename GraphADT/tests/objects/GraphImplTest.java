package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GraphImplTest {
	Vertex v1 = Factory.createV("eins");
	Vertex v2 = Factory.createV("zwei");
	Vertex v3 = Factory.createV("drei");
	Vertex vspecial1 = Factory.createV("#vier");
	Vertex vspecial2 = Factory.createV("L3@-t");	
	Vertex vnull = null;
	int error = -999999999;
	
	@Test
	public void testAddVertex() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Befüllung
		g.addVertex(v2);
		//Muss pro Vertex auf die Vertexlist rechnene
		//Muss Vertex enthalten
		assertEquals(2, g.getVertexes().size());
		assertEquals(true,g.getVertexes().contains(v1));
		
		g.addVertex(vspecial2);
		//Muss Namen mit Sonderzeichen akzeptieren
		assertEquals(3, g.getVertexes().size());
		assertEquals(false,g.getVertexes().contains(v3));
		assertEquals(true,g.getVertexes().contains(vspecial2));
		assertEquals(false,g.getVertexes().contains(Factory.createV("L3@-t")));
		//null-Wert muss hinzugefügt werden können
		g.addVertex(vnull);
		//Muss null-Werte akzeptieren
		assertEquals(4, g.getVertexes().size());
		assertEquals(false,g.getVertexes().contains(Factory.createV(null)));
	}

	@Test
	public void testDeleteVertex() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Befüllung
		g.addVertex(v2);
		g.addVertex(v3);
		g.deleteVertex(vspecial1);
		//Löschen von nicht beinhalteten Elementen wird ignoriert
		assertEquals(3, g.getVertexes().size());
		assertEquals(false, g.getVertexes().contains(vspecial1));
		assertEquals(true, g.getVertexes().contains(v3));
		//Gültiges Löschen
		g.deleteVertex(v2);
		//Größe der vertexList wird reduziert, andere Elemente sind noch enthalten
		assertEquals(2, g.getVertexes().size());
		assertEquals(true, g.getVertexes().contains(v3));
		assertEquals(false, g.getVertexes().contains(v2));
		assertEquals(false, g.getVertexes().contains("Hallo"));
	}

	@Test
	public void testAddEdge() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Befüllung
		g.addVertex(v2);
		g.addEdge(v1, v2);
		//addEdge gibt den Graph zurück wenn Vertex nicht vorhanden
		g.addEdge(v1, v3);
		g.addEdge(v3, vspecial1);
		//Größe der vertexList entspricht der Anzahl gültig hinzugefügter Vertexes
		//Edge = sourceVertex,targetVertex
		assertEquals(2, g.getEdges().size());
		//Ungültige Edges dürfen nicht vorhanden sein, gültige müssen
		assertEquals(true, g.getEdges().contains(v1));
		assertEquals(true, g.getEdges().contains(v2));
		assertEquals(false, g.getEdges().contains(v3));
	}

	@Test
	public void testDeleteEdge() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//gültige Beffüllung
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addEdge(v2, v1);
		//Größe entspricht der Anzahl der Vertexes in den Edges
		assertEquals(4, g.getEdges().size());
		g.deleteEdge(v1,v2);
		assertEquals(2, g.getEdges().size());
		assertEquals(true, g.getEdges().contains(v1));
		//LÖschen nicht vorhandener Edges wird ignoriert
		g.deleteEdge(v1, v3);
		assertEquals(2, g.getEdges().size());
		assertEquals(true, g.getEdges().contains(v2));
		assertEquals(true, g.getEdges().contains(v1));
		assertEquals(false, g.getEdges().contains(vnull));
		assertEquals(false, g.getEdges().contains(v3));
	}

	@Test
	public void testSetAtE() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Befüllung
		g.addVertex(v2);
		g.addEdge(v1, v2);
		//Setzen der Attribute vorhandener Edges
		//(sourceVertex, targetVertex, attributeName, value 
		g.setAtE(v1, v2, "attr1", 1);
		g.setAtE(v1, v2, "attr,2", 2);
		//Setzen von Attributen für nicht vorhandene Edges wird ignoriert
		g.setAtE(v1, v3, "attr3", 3);
		//Value zum attributeName wird ausgegeben falls Edge vorhanden
		assertEquals(1, g.getValE(v1, v2, "attr1"));
		assertEquals(2, g.getValE(v1, v2, "attr,2"));
		//Default -999999999 wenn attributeName oder Edge nicht vorhanden
		assertEquals(error, g.getValE(v1, v3, "attr1"));
		assertEquals(error, g.getValE(v1, v2, "attr2"));
		g.setAtE(v1, v2, "attr1", 5);;
		//Das Attribut wird mit einem weiteren Setzen überschrieben
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
		assertEquals(0, g.getIncident(vspecial1).size());
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
		assertEquals(0, g.getAdjacent(vspecial1).size());
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
		assertEquals(0, g.getTarget(vspecial1).size());
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
		assertEquals(0, g.getSource(vspecial1).size());
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
		assertEquals(-999999999, g.getValE(v3, vspecial1, "attr"));
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
