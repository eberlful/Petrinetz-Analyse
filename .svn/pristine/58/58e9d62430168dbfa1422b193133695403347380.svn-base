package mypackage.model;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import org.graphstream.graph.implementations.MultiGraph;

/**
 * Konstruktionsklasse, die ein übergebenes Petrinetz graphisch mittel GraphStream-Framework darstellt.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class PetriNetGraph extends MultiGraph {
	
	private static String CSS_FILE = "url(" + PetriNetGraph.class.getResource("/petriNetGraph.css") + ")";
	
	private PNML pnml;
	
	/**
	 * Konstruktor, der eine ID und eine Petrinetzdatenstruktur übergeben bekommt.
	 * @param id ID des Graphens.
	 * @param pnml Referenz der Datenstruktur.
	 */
	public PetriNetGraph(String id, PNML pnml) {
		super("");
		this.pnml = pnml;
	}
	
	public void createPetriNetGraphStructed() {
		// Setze CSS-Datei
		this.addAttribute("ui.stylesheet", CSS_FILE);
		
		// Attribute für die Nodes setzen und Knoten erzeugen
		for (Entry<String, Place> entry : pnml.getNet().getPlaces().entrySet()) {
			Node node = this.addNode(entry.getValue().getID());
			System.out.println("Node mit dem Namen " + entry.getValue().getName() + " und ID: " + entry.getValue().getID());
			entry.getValue().addElement(node);
			entry.getValue().setAttributes();
		}
		
		// Attribute für die Transitionen setzen und diese erzeugen
		for (Entry<String, Transition> entry : pnml.getNet().getTransitions().entrySet()) {
			Node node = this.addNode(entry.getValue().getID());
			System.out.println("Transition mit dem Namen " + entry.getValue().getName() + " und ID: " + entry.getValue().getID());
			entry.getValue().addElement(node);
			entry.getValue().setAttributes();
		}
		
		// Attribute für die Kanten setzen und diese erzeugen
		for (Entry<String, Place> entry : pnml.getNet().getPlaces().entrySet()) {
			for(Entry<String, DirectedEdge> edges : entry.getValue().getArcsOut().entrySet()) {
				System.out.println("Kante " + edges.getValue().getID() + " von " + edges.getValue().getSource().getID() + " zu " + edges.getValue().getDestination().getID());
				Edge edge = this.addEdge(edges.getValue().getID(), (Node)edges.getValue().getSource().getElement(), (Node)edges.getValue().getDestination().getElement(), true);
				edges.getValue().setEdge(edge);
				edges.getValue().setAttributes();
			}
		}
		
		// Attribute für die Kanten setzen und diese erzeugen
		for (Entry<String, Transition> entry : pnml.getNet().getTransitions().entrySet()) {
			for(Entry<String, DirectedEdge> edges : entry.getValue().getArcsOut().entrySet()) {
				System.out.println("Kante " + edges.getValue().getID() + " von " + edges.getValue().getSource().getID() + " zu " + edges.getValue().getDestination().getID());
				Edge edge = this.addEdge(edges.getValue().getID(), (Node)edges.getValue().getSource().getElement(), (Node)edges.getValue().getDestination().getElement(), true);
				edges.getValue().setEdge(edge);
				edges.getValue().setAttributes();
			}
		}
	}

}
