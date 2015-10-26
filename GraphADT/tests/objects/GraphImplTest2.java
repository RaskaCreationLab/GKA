package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

public class GraphImplTest2 {


	@Test
	public void testCreateV() {
		
		
		
//		- „normalen“ Namen (ohne Sonderzeichen, Leerzeichen, Komma, ...)
//
		Vertex A1=Factory.createV("A");
		Vertex A2=Factory.createV("A");
		Vertex B =Factory.createV("B");
		assertNotNull(A1);
		assertNotNull(A2);
		assertNotNull(B );
		assertEquals(A1, A2);
		assertNotEquals(A1, B);
		
//		- Name mit Leerzeichen
		A1=Factory.createV("A 2");
		A2=Factory.createV("A 2");
		B =Factory.createV("B 2");
		assertNotNull(A1);
		assertNotNull(A2);
		assertNotNull(B );
		assertEquals(A1, A2);
		assertNotEquals(A1, B);

//		- Name mit Komma
		A1=Factory.createV("A,2");
		A2=Factory.createV("A,2");
		B =Factory.createV("B,2");
		assertNotNull(A1);
		assertNotNull(A2);
		assertNotNull(B );
		assertEquals(A1, A2);
		assertNotEquals(A1, B);
		
//		- leerer String „“
		A1=Factory.createV("");
		A2=Factory.createV("");
		B =Factory.createV("B,2");
		assertNotNull(A1);
		assertNotNull(A2);
		assertNotNull(B );
		assertEquals(A1, A2);
		assertNotEquals(A1, B);
		
//		- Name mit Sonderzeichen
		A1=Factory.createV("A@2");
		A2=Factory.createV("A@2");
		B =Factory.createV("B%2");
		assertNotNull(A1);
		assertNotNull(A2);
		assertNotNull(B );
		assertEquals(A1, A2);
		assertNotEquals(A1, B);
	}
		
	@Test
	public void testImportG() {
		fail("Not yet implemented");
	}

//		- korrekt angegebene Datei mit korrektem Inhalt
//
//		- nicht vorhandene Datei
//
//		- Datei ohne Header
//
//		- Datei mit fehlenden Attributwerten
//
//		- Datei mit leeren Attributwerten (zwei aufeinanderfolgende Komma)
//
//		- leere Datei (0 Byte)

		
	@Test
	public void testCreateG() {
//		- normales, korrektes Vertex-Objekt übergeben
		Vertex a=Factory.createV("A");
		Graph g1=Factory.createG(a);
		assertNotNull(g1);
		assertEquals(1 ,g1.getVertexes().size());
		assertEquals(a ,g1.getVertexes().get(0));
		
//		- Vertex-Objekt mit Sonderzeichen im Namen (siehe createV)
		Vertex s=Factory.createV("A@B%");
		Graph g2=Factory.createG(s);
		assertNotNull(g2);
		assertEquals(1 ,g2.getVertexes().size());
		assertEquals(s ,g2.getVertexes().get(0));
		
		Vertex n=Factory.createV("");
		Graph g3=Factory.createG(n);
		assertNotNull(g3);
		assertEquals(1 ,g3.getVertexes().size());
		assertEquals(n ,g3.getVertexes().get(0));

	
		

//		- null-Objekt		
		//TODO:How the fuck check for null 
	}


	@Test
	public void testAddVertex() {
//		- normales, korrektes Vertex-Objekt übergeben
		Vertex a1=Factory.createV("A");
		Vertex a2=Factory.createV("B");
		Graph g1=Factory.createG(a1);
		assertNotNull(g1);
		assertEquals(1 ,g1.getVertexes().size());
		assertEquals(true,g1.getVertexes().contains(a1));
		g1.addVertex(a2);
		assertEquals(2 ,g1.getVertexes().size());
		assertEquals(true,g1.getVertexes().contains(a1));
		assertEquals(true,g1.getVertexes().contains(a2));
		
//		- Vertex-Objekt mit Sonderzeichen im Namen (siehe createV)
		Vertex s1=Factory.createV("A@B%");
		Vertex s2=Factory.createV("A@C%B");
		Graph g2=Factory.createG(s1);
		assertNotNull(g2);
		assertEquals(1, g2.getVertexes().size());
		assertEquals(true,g2.getVertexes().contains(s1));
		g2.addVertex(s2);
		assertEquals(2,g2.getVertexes().size());
		assertEquals(true,g2.getVertexes().contains(s1));
		assertEquals(true,g2.getVertexes().contains(s2));
		

		Vertex n1=Factory.createV("");
		Vertex n2=Factory.createV("_");
		Graph g3=Factory.createG(n1);
		assertNotNull(g3);
		assertEquals(1 ,g3.getVertexes().size());
		assertEquals(true,g3.getVertexes().contains(n1));
		g3.addVertex(n2);
		assertEquals(2 ,g1.getVertexes().size());
		assertEquals(true,g3.getVertexes().contains(n1));
		assertEquals(true,g3.getVertexes().contains(n2));
	}
		

	@Test
	public void testDeleteVertex() {
		Vertex a1=Factory.createV("A");
		Vertex a2=Factory.createV("B");
		Graph g1=Factory.createG(a1);
		g1.addVertex(a2);
		
		assertEquals(2 ,g1.getVertexes().size());
		assertEquals(true,g1.getVertexes().contains(a1));
		assertEquals(true,g1.getVertexes().contains(a2));
		
		g1.deleteVertex(a1);
		assertEquals(1 ,g1.getVertexes().size());
		assertEquals(false,g1.getVertexes().contains(a1));
		assertEquals(true,g1.getVertexes().contains(a2));
		
		g1.addVertex(a1);
		g1.deleteVertex(a2);
		assertEquals(1 ,g1.getVertexes().size());
		assertEquals(true,g1.getVertexes().contains(a1));
		assertEquals(false,g1.getVertexes().contains(a2));
		
		
		g1.deleteVertex(Factory.createV("NotAvailable"));
		assertEquals(1 ,g1.getVertexes().size());
		assertEquals(true,g1.getVertexes().contains(a1));
		assertEquals(false,g1.getVertexes().contains(a2));
		
		
		
	}
//
//		- vorhandenes Vertex-Objekt löschen
//
//		- nicht vorhandenes Vertex-Objekt löschen

	@Test
	public void testAddEdge() {
		Vertex a1=Factory.createV("A");
		Vertex a2=Factory.createV("B");
		Vertex a3=Factory.createV("C");
		Graph g1=Factory.createG(a1);
		g1.addVertex(a2);
		
		g1.addEdge(a1, a2);

		assertEquals(2,g1.getEdges().size());
		assertEquals(a1,g1.getEdges().get(0));
		assertEquals(a2,g1.getEdges().get(1));

		g1.addEdge(a1, a3);

		assertEquals(2,g1.getEdges().size());
		assertEquals(a1,g1.getEdges().get(0));
		assertEquals(a2,g1.getEdges().get(1));
	}

//		- Kante für zwei vorhandene Ecken erstellen
//
//		- Kante für eine vorhandene und eine nicht vorhandene Ecke erstellen
//
//		- Kante für zwei nicht vorhandenen Ecken erstellen
//
//		- jeweils abfragen, ob Kante vorhanden
//
//		- jeweils überprüfen, ob Kante mit vertauschter Quelle und Ziel nicht vorhanden

	@Test
	public void testDeleteEdge() {
		Vertex a1=Factory.createV("A");
		Vertex a2=Factory.createV("B");
		Vertex a3=Factory.createV("C");
		Graph g1=Factory.createG(a1);
		g1.addVertex(a2);
		
		g1.addEdge(a1, a2);
		g1.addEdge(a2, a1);
		
		
		assertEquals(4,g1.getEdges().size());
		assertEquals(a1,g1.getEdges().get(0));
		assertEquals(a2,g1.getEdges().get(1));
		assertEquals(a2,g1.getEdges().get(2));
		assertEquals(a1,g1.getEdges().get(3));

		g1.deleteEdge(a2, a1);
		assertEquals(2,g1.getEdges().size());
		assertEquals(a1,g1.getEdges().get(0));
		assertEquals(a2,g1.getEdges().get(1));
		
		g1.deleteEdge(a2, a3);
		assertEquals(2,g1.getEdges().size());
		assertEquals(a1,g1.getEdges().get(0));
		assertEquals(a2,g1.getEdges().get(1));
		
		
		
	}

//		- vorhandene Kante entfernen
//
//		- nicht vorhandene Kante entfernen
//
//		- überprüfen, ob Kante nach entfernen entfernt ist
//
//		- überprüfen, ob Kante mit vertauschter Quelle und Ziel nicht entfernt ist

	@Test
	public void testSetAtE() {
		Vertex a1=Factory.createV("A");
		Vertex a2=Factory.createV("B");
		Graph g1=Factory.createG(a1);
		g1.addVertex(a2);
		
		g1.addEdge(a1, a2);

		
		g1.setAtE(a1, a2, "key", 22);
		g1.setAtE(a1, a2, "key@,?X", 42);
		
		g1.setAtE(a2, a1, "key", 22);
		g1.setAtE(a2, a1, "key@,?X", 42);
		
		g1.setAtE(a1, a2, "key", 21);


		assertEquals(21, g1.getValE(a1, a2, "key"));
		assertEquals(42, g1.getValE(a1, a2, "key@,?X"));
		
		assertEquals(-999999999, g1.getValE(a1, a2, "WrongKey"));
		
	}

//		- „normales“ Attribut für vorhandene Kante erstellen
//
//		- Attribut mit Sonderzeichen (und Komma) für vorhandene Kante erstellen
//
//		- Attribut für nicht vorhandene Kante erstellen
//
//		- gesetztes Attribut überschreiben
//
//		- gesetztes Attribut mit korrektem Namen abfragen
//
//		- gesetztes Attribut mit falschen Namen abfragen

		
		@Test
		public void testSetAtV() {
			Vertex a1=Factory.createV("A");
			Vertex a2=Factory.createV("B");
			Graph g1=Factory.createG(a1);
			
			g1.setAtV(a1, "key", 22);
			g1.setAtV(a1, "key@,?X", 42);
			g1.setAtV(a2, "key", 42);
			g1.setAtV(a1, "key", 21);

			assertEquals(21, g1.getValV(a1, "key"));
			assertEquals(42, g1.getValV(a1, "key@,?X"));
			assertEquals(-999999999, g1.getValV(a1, "notExist"));
		}
		

//		- „normales“ Attribut für vorhandene Ecke erstellen
//
//		- Attribut mit Sonderzeichen (und Komma) für vorhandene Ecke erstellen
//
//		- Attribut für nicht vorhandene Ecke erstellen
//
//		- gesetztes Attribut überschreiben
//
//		- gesetztes Attribut mit korrektem Namen abfragen
//
//		- gesetztes Attribut mit falschen Namen abfragen

		
		@Test
		public void testExportG() {
			fail("Not yet implemented");
		}

//		- korrekten Dateinamen angeben
//
//		- unzulässigen Dateinamen angeben
//
//		- leeren String angegeben
//
//		- exportierte Datei wieder einlesen (importG)

		
		@Test
		public void testGetIncident() {
			Vertex a1=Factory.createV("A");
			Vertex a2=Factory.createV("B");
			Vertex a3=Factory.createV("C");
			Graph g1=Factory.createG(a1);
			g1.addVertex(a2);
			g1.addVertex(a3);
			
			g1.addEdge(a1, a2);
			

			assertEquals(2, g1.getIncident(a1).size());
			assertEquals(a1, g1.getIncident(a1).get(0));
			assertEquals(a2, g1.getIncident(a1).get(1));

			assertEquals(2, g1.getIncident(a2).size());
			assertEquals(a1, g1.getIncident(a2).get(0));
			assertEquals(a2, g1.getIncident(a2).get(1));

			assertEquals(0, g1.getIncident(a3).size());


		}

//
//		- Liste für Ecke mit Kanten abfragen
//
//		- Liste für Ecke ohne Kanten abfragen
//
//		- Liste für nicht vorhandene Ecke abfragen
//

		
		@Test
		public void testGetAdjacent() {
			Vertex a1=Factory.createV("A");
			Vertex a2=Factory.createV("B");
			Vertex a3=Factory.createV("C");
			Graph g1=Factory.createG(a1);
			g1.addVertex(a2);
			g1.addVertex(a3);
			
			g1.addEdge(a1, a2);
			

			assertEquals(1, g1.getAdjacent(a1).size());
			assertEquals(a2,g1.getAdjacent(a1).get(0));

			assertEquals(1, g1.getAdjacent(a2).size());
			assertEquals(a1,g1.getAdjacent(a2).get(0));

			assertEquals(0,g1.getAdjacent(a3).size());
		}

//		- Liste für Ecke mit Kanten abfragen
//
//		- Liste für Ecke ohne Kanten abfragen
//
//		- Liste für nicht vorhandene Ecke abfragen

		
		@Test
		public void testGetTarget() {
			Vertex a1=Factory.createV("A");
			Vertex a2=Factory.createV("B");
			Vertex a3=Factory.createV("C");
			Graph g1=Factory.createG(a1);
			g1.addVertex(a2);
			
			g1.addEdge(a1, a2);
			

			assertEquals(1, g1.getTarget(a1).size());
			assertEquals(a2,g1.getTarget(a1).get(0));

			assertEquals(0, g1.getTarget(a2).size());
			assertEquals(0, g1.getTarget(a3).size());
		}

//		- Liste für Ecke mit Zielen abfragen
//
//		- Liste für Ecke ohne Ziele abfragen
//
//		- Liste für nicht vorhandene Ecke abfragen
//
//		
		@Test
		public void testGetSource() {
			Vertex a1=Factory.createV("A");
			Vertex a2=Factory.createV("B");
			Vertex a3=Factory.createV("C");
			Graph g1=Factory.createG(a1);
			g1.addVertex(a2);
			
			g1.addEdge(a1, a2);
			

			assertEquals(1, g1.getSource(a2).size());
			assertEquals(a1,g1.getSource(a2).get(0));

			assertEquals(0, g1.getSource(a1).size());
			
			assertEquals(0, g1.getSource(a3).size());
			
		}

//		- Liste für Ecke mit Quellen abfragen
//
//		- Liste für Ecke ohne Quelle abfragen
//
//		- Liste für nicht vorhandene Ecke abfragen

		
		@Test
		public void testGetEdges() {
			Vertex a1=Factory.createV("A");
			Vertex a2=Factory.createV("B");
			Graph g1=Factory.createG(a1);
			Graph g2=Factory.createG(a1);
			g1.addVertex(a2);
			g2.addVertex(a2);
	
			g1.addEdge(a1, a2);
			
			assertEquals(2, g1.getEdges().size());
			assertEquals(true,g1.getEdges().contains(a1));
			assertEquals(true,g1.getEdges().contains(a2));

			assertEquals(0, g2.getEdges().size());


		}

//		- Abfrage auf einen Graph mit Kanten
//
//		- Abfrage auf einen Graph ohne Kanten

		
		@Test
		public void testGetVertexes() {
			Vertex a1=Factory.createV("A");
			Vertex a2=Factory.createV("B");
			Graph g1=Factory.createG(a1);
			g1.addVertex(a2);

			assertEquals(2, g1.getVertexes().size());
			assertEquals(true,g1.getVertexes().contains(a1));
			assertEquals(true,g1.getVertexes().contains(a2));
			
			
			// Abfrage auf einen Graph ohne Ecken : What the fuck O.o
			
		}
//
//		- Abfrage auf einen Graph mit Ecken
//
//		- Abfrage auf einen Graph ohne Ecken

		
		@Test
		public void testGetValE() {
			Vertex a1=Factory.createV("A");
			Vertex a2=Factory.createV("B");
			Graph g1=Factory.createG(a1);
			g1.addVertex(a2);
			
			g1.addEdge(a1, a2);

			
			g1.setAtE(a1, a2, "key", 22);
			g1.setAtE(a1, a2, "key@,?X", 42);
			
			g1.setAtE(a2, a1, "key", 22);
			g1.setAtE(a2, a1, "key@,?X", 42);
			
			g1.setAtE(a1, a2, "key", 21);


			assertEquals(21, g1.getValE(a1, a2, "key"));
			assertEquals(42, g1.getValE(a1, a2, "key@,?X"));
			
			assertEquals(-999999999, g1.getValE(a1, a2, "WrongKey"));
	}
		
//
//		- Abfrage eines gültigen Wertes
//
//		- Abfrage eines Wertes mit vertauschter Quelle und Ziel
//
//		- Abfrage eines ungültigen Wertes (Name falsch)
//
//		- Abfrage auf eine nicht vorhandene Kante (beide Ecken im Graph)
//
//		- Abfrage auf eine nicht vorhandene Kante (beide Ecken nicht im Graph)

		
		@Test
		public void testGetValV() {
			Vertex a1=Factory.createV("A");
			Vertex a2=Factory.createV("B");

			Graph g1=Factory.createG(a1);
			
			g1.setAtV(a1, "key", 22);
			g1.setAtV(a1, "key@,?X", 42);
			g1.setAtV(a2, "key", 42);
			g1.setAtV(a1, "key", 21);

			assertEquals(21, g1.getValV(a1, "key"));
			assertEquals(42, g1.getValV(a1, "key@,?X"));
			assertEquals(-999999999, g1.getValV(a1, "notExist"));
			assertEquals(-999999999, g1.getValV(a2, "notExist"));
		}
//
//		- Abfrage eines gültigen Wertes
//
//		- Abfrage eines ungültigen Wertes (Name falsch)
//
//		- Abfrage auf eine nicht vorhandene Ecke
}
