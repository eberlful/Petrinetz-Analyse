package mypackage.model;

import org.graphstream.graph.implementations.MultiGraph;
/**
 * Klasse erzeugt Konten und Kanten zur Darstellung. Diese Objekt wird nur zur Darstellung verwendet
 * und hat keine Abhängigkeit zu einer Datenstruktur.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class DecisionGraph extends MultiGraph{
	
	// Link zur CSS Datei
	private static String CSS_FILE = "url(" + DecisionGraph.class.getResource("/decisionGraph.css") + ")";
	
	private mypackage.view.PetriNetFrame frame = null;
	
	/**
	 * Konstruktor mit einem Übergabeparameter und Aufruf des Konstruktors der Mutterklasse
	 * @param id ID des Graphens.
	 */
	public DecisionGraph(String id) {
		super(id);
		this.addAttribute("ui.stylesheet", CSS_FILE);
	}
	 
	/**
	 * Konstruktor mit einem Übergabeparameter und Aufruf des Konstruktors der Mutterklasse
	 */
	public DecisionGraph() {
		super("Erreichbarkeitsgraph");
		this.addAttribute("ui.stylesheet", CSS_FILE);
	}
	
	/**
	 * Erzeuge Startknoten mit dem übergebenen Knoten-Objekt
	 * @param node Knoten der im Graph registriert werden soll.
	 */
	public void createStartNode(DecisionNode node) {
		this.addAttribute("ui.stylesheet", CSS_FILE);
		node.setNode(this.addNode(String.valueOf(node.getID())));
	}
	
	/**
	 * Erzeugt das Node-Objekt aus der Graphstreambibliothek und registriert dieses im DecisionNode-Objekt
	 * @param node DecisionNode-Objekt, das im Graph erzeugt werden soll
	 */
	public void createNode(DecisionNode node) {
		if(node.getNode() != null) {
			
		} else {
			node.setNode(this.addNode(String.valueOf(node.getID())));
			node.setAttribute(false);
		}
	}
	
	/**
	 * Erzeugt das Edge-Objekt aus der Graphstreambibliothek und registriert dieses im DecisionEdge-Objekt
	 * @param edge DecisionEdge-Objekt, das im Graph erzeugt werden soll
	 */
	public void createEdge(DecisionEdge edge) {
		edge.setEdge(this.addEdge(String.valueOf(edge.getID()), edge.getSourceNode().getNode().getId(), edge.getDestinationNode().getNode().getId(), true));
		edge.setAttribute();
	}
	
	/**
	 * Getter-Funktion für das Frame-Objekt.
	 * @return Referenz eines Fensters.
	 */
	public mypackage.view.PetriNetFrame getFrame(){
		return this.frame;
	}
	
	/**
	 * Setter-Funktion für das Frame-Objekt.
	 * @param frame Referenz eines Fensters.
	 */
	public void setFrame(mypackage.view.PetriNetFrame frame) {
		this.frame = frame;
	}
	
}
