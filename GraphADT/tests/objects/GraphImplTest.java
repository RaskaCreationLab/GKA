package objects;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
		assertEquals(true, g.getVertexes().contains(v1));
		//Default -999999999 wenn attributeName oder Edge nicht vorhanden
		assertEquals(error, g.getValE(v1, v3, "attr1"));
		assertEquals(error, g.getValE(v1, v2, "attr2"));
		g.setAtE(v1, v2, "attr1", 5);
		//Das Attribut wird mit einem weiteren Setzen überschrieben
		assertEquals(5, g.getValE(v1, v2, "attr1"));
	}

	@Test
	public void testSetAtV() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Setzen mehrerer Attribute funktioniert
		g.setAtV(v1, "attr1", 1);
		g.setAtV(v1, "attr,2", 2);
		//Setzen von einer nicht vorhandenen Vertex wird ignoriert
		g.setAtV(v2, "attr3", 3);
		assertEquals(1, g.getValV(v1, "attr1"));
		assertEquals(2, g.getValV(v1, "attr,2"));
		assertEquals(-999999999, g.getValV(v1, "attr3"));
		assertEquals(-999999999, g.getValV(v2, "attr1"));
		assertEquals(false,g.getVertexes().contains(v2));
		//Value wird überschrieben bei weiterem Setzen
		g.setAtV(v1, "attr1", 5);
		assertEquals(5, g.getValV(v1, "attr1"));
	}
	
	@Test
	public void testExportG() {
		//Standardgraph
		Graph g = Factory.createG(v1);		
		ArrayList<String> imArray = new ArrayList<String>();
		imArray.add("#gerichtet");
		imArray.add("eins,drei,30,20");
		imArray.add("zwei,eins");
		//Befüllung
		g.addVertex(v2);
		g.addVertex(v3);
		g.addEdge(v1, v3);
		g.addEdge(v2, v1);
		//Setzen der Attribute
		g.setAtE(v1, v3, "kosten", 30);
		g.setAtE(v1, v3, "weg", 20);
		g.exportG("graph");		
		//Das richtig gebaute Array muss dem exportierten geschriebenene Graphen gleichen
		//Wenn das exportierte aus dem erstellten gelöscht wird, muss das erstellte leer sein
		ArrayList<String> lines = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+ "\\graph.graph")))
		{
			String currentLine;
			while((currentLine = br.readLine()) != null) {
				lines.add(currentLine);
			}
		} catch (IOException e) {} 	
		imArray.removeAll(lines);
		assertEquals(true,imArray.isEmpty());		
	}
	
	@Test
	public void testGetIncident() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Befüllung
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addVertex(v3);
		//Größe entspricht der Anzahl der Inzidenten
		//Falls Vertex nicht im Graph wird sie nicht hinzugefügt und es ändert nicht den Graphen
		assertEquals(0, g.getIncident(v3).size());
		assertEquals(2, g.getIncident(v1).size());
		assertEquals(0, g.getIncident(vspecial1).size());
		assertEquals(true, g.getIncident(v2).contains(v1));
		assertEquals(true, g.getIncident(v2).contains(v2));
		assertEquals(false, g.getIncident(v3).contains(vspecial1));
	}

	@Test
	public void testGetAdjacent() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Befüllung
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addVertex(v3);
		//Größe entspricht der Anzahl der Adjazenten
		//Falls Vertex nicht im Graph wird sie nicht hinzugefügt; keine Auswirkung auf den Graphen
		assertEquals(0, g.getAdjacent(v3).size());
		assertEquals(1, g.getAdjacent(v1).size());
		assertEquals(1, g.getAdjacent(v2).size());
		assertEquals(0, g.getAdjacent(vspecial1).size());
		assertEquals(true, g.getAdjacent(v2).contains(v1));
		assertEquals(true, g.getAdjacent(v1).contains(v2));
		//Neue Adjazenten werden nach Hinzufügen dazugerechnet
		g.addEdge(v3, v1);
		assertEquals(2, g.getAdjacent(v1).size());
		
	}

	@Test
	public void testGetTarget() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Befüllung
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addVertex(v3);
		//Vertex die nicht in im Graphen hat, hat auch kein Target
		//Vertex ohne Edge hat kein Target
		//Edge = Source, Target -> Source hat Target
		assertEquals(0, g.getTarget(v3).size());
		assertEquals(1, g.getTarget(v1).size());
		assertEquals(true, g.getTarget(v1).contains(v2));
		assertEquals(false,g.getTarget(v2).contains(v1));
		assertEquals(0, g.getTarget(v2).size());
		assertEquals(0, g.getTarget(vspecial1).size());
		//Für Targetvertex ändert sich die Anzahl der targets nicht
		g.addEdge(v3, v1);
		assertEquals(1, g.getTarget(v1).size());
		assertEquals(1, g.getTarget(v3).size());
	}

	@Test
	public void testGetSource() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Befüllung
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.addVertex(v3);
		//Vertex die in keiner Edge ist = keine Source
		//Edge(Source,Target) = Source ist source von Target
		//nicht vorhandene Vertex im Graphen geben keinen Fehler, haben keine Source
		assertEquals(0, g.getSource(v3).size());
		assertEquals(0, g.getSource(v1).size());
		assertEquals(1, g.getSource(v2).size());
		assertEquals(0, g.getSource(vspecial1).size());
		//Für Sourcevertex ändert sich die Anzahl der sources nicht
		g.addEdge(v3, v1);
		assertEquals(1, g.getSource(v1).size());
		assertEquals(0, g.getSource(v3).size());
		assertEquals(false, g.getSource(v3).contains(v1));
		assertEquals(true, g.getSource(v2).contains(v1));
	}

	@Test
	public void testGetEdges() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Befüllung
		g.addVertex(v2);
		//Ohne Edge ist getEdges leer
		assertEquals(0, g.getEdges().size());
		g.addEdge(v1, v2);
		//Für jede Edge kommen zwei Vertexes in edgeList, also size + 2/edge
		assertEquals(2, g.getEdges().size());
		assertEquals(true, g.getEdges().contains(v1));
		assertEquals(true, g.getEdges().contains(v2));
		assertEquals(false, g.getEdges().contains(vspecial2));		
	}

	@Test
	public void testGetVertexes() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Bei Erschaffung eines Graphen muss eine Vertex schon hinzugefügt werden
		assertEquals(1, g.getVertexes().size());
		g.addVertex(v2);
		g.addVertex(vspecial2);
		g.addVertex(v3);
		//Größe ändert sich nach hinzufügen von Vertexes je nach Anzahl hinzugefügter Vertexes
		//Hinzugefügte Vertexes sind enthalten
		assertEquals(4,g.getVertexes().size());
		g.deleteVertex(v1);
		assertEquals(3,g.getVertexes().size());
		assertEquals(true,g.getVertexes().contains(v2));
		assertEquals(false,g.getVertexes().contains(v1));
	}

	@Test
	public void testGetValE() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Befüllung
		g.addVertex(v2);
		g.addEdge(v1, v2);
		g.setAtE(v1, v2, "attr", 1);
		g.setAtE(v1, v2, "attr1", 2000);
		//Error wenn es kein passendes Attribut mit dem gegebenen Namen gibt
		//Gesetzter Wert wird gegeben wenn das Attribut gesetzt wurde
		//Wenn Vertex nicht im Graph, wird error herausgegeben
		assertEquals(1, g.getValE(v1, v2, "attr"));
		assertEquals(2000, g.getValE(v1, v2, "attr1"));
		assertEquals(error, g.getValE(v1, v2, "attr2"));
		assertEquals(error, g.getValE(v2, v1, "attr"));
		assertEquals(error, g.getValE(v3, vspecial1, "attr"));
		//Geänderter Wert wird nach Veränderung auf Anfrage ausgegeben
		g.setAtE(v1, v2, "attr", 30);
		assertEquals(30,g.getValE(v1, v2, "attr"));
	}

	@Test
	public void testGetValV() {
		//Standardgraph
		Graph g = Factory.createG(v1);
		//Setzen des Values von vorhandener Vertex
		g.setAtV(v1, "attr", 1);
		//Error wenn die Vertex kein Attribut mit gegebenem Namen besitzt oder die Vertex nicht im Graph vorhanden ist
		assertEquals(1, g.getValV(v1, "attr"));
		assertEquals(error, g.getValV(v1, "attr2"));
		assertEquals(error, g.getValV(v2, "attr"));
		//Bei schon vergebenem Namen wird error ausgebenen
		g.setAtV(v2, "attr", 20);
		assertEquals(error, g.getValV(v2, "attr"));
		assertEquals(1, g.getValV(v1, "attr"));				
	}

}
