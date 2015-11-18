package algorithms;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import objects.Factory;
import objects.Graph;
import objects.Vertex;


public class UtilityTest {

	@Test
	public void testBellf() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("cost");
		Factory.setAttrNames(names);
		Graph g = Factory.importG("graph_03"); //test directed without negative
		ArrayList<Vertex> erg = Utility.bellf(g, Factory.createV("s"), Factory.createV("u"));
		ArrayList<Vertex> expected = new ArrayList<Vertex>();
		expected.add(Factory.createV("s"));
		expected.add(Factory.createV("x"));
		expected.add(Factory.createV("u"));
		assertEquals(true, erg.equals(expected));
		erg = Utility.bellf(g, Factory.createV("u"), Factory.createV("s")); //second test on same graph
		expected.clear();
		expected.add(Factory.createV("u"));
		expected.add(Factory.createV("x"));
		expected.add(Factory.createV("y"));
		expected.add(Factory.createV("s"));
		assertEquals(true, erg.equals(expected));
		//test with negative circle, undirected
		g = Factory.importG("graph_04");
		erg = Utility.bellf(g, Factory.createV("v5"), Factory.createV("v9"));
		assertNull(erg);
		//test with negative, no circle, directed;
		g = Factory.importG("graph_06");
		erg = Utility.bellf(g, Factory.createV("v1"), Factory.createV("v9"));
		expected.clear();
		expected.add(Factory.createV("v1"));
		expected.add(Factory.createV("v4"));
		expected.add(Factory.createV("v6"));
		expected.add(Factory.createV("v7"));
		expected.add(Factory.createV("v8"));
		expected.add(Factory.createV("v9"));
		assertEquals(true, erg.equals(expected));
		//test without a possible way
		erg = Utility.bellf(g, Factory.createV("v9"), Factory.createV("v8")); 
		assertNull(erg);
	}
}
