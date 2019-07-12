package mypackage.model;

/**
 * Datentyp, der eine Kante im Entscheidungsbaum darstellen soll. Container mit Speicherung
 * der Transition, die diese Kante repräsentiert.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class DecisionEdge {
	
	private mypackage.model.Transition transition;
	
	private org.graphstream.graph.Edge edge;
	private mypackage.model.DecisionNode sourceNode;
	private mypackage.model.DecisionNode destinationNode;
	
	private int id;
	
	/**
	 * Konstruktor, der die repräsentierte Transition und den Zielknoten übergeben bekommt.
	 * @param transition Transition die geschalten wird um nächsten Knoten zu erreichen.
	 * @param destinationNode Zielknoten auf dem dieses Objekt zeigt.
	 */
	public DecisionEdge(mypackage.model.Transition transition, mypackage.model.DecisionNode destinationNode) {
		this.transition = transition;
		this.destinationNode = destinationNode;
	}
	
	/**
	 * Konstruktor, der die repräsentierte Transition, den Startkonten und den Zielknoten übergeben bekommt.
	 * @param transition Transition die geschalten wird um nächsten Knoten zu erreichen.
	 * @param source Startknoten von dem diese Objekt zeigt.
	 * @param destination Zielknoten auf dem dieses Objekt zeigt.
	 */
	public DecisionEdge(mypackage.model.Transition transition, mypackage.model.DecisionNode source, mypackage.model.DecisionNode destination) {
		System.out.println("DecisionEdge erzeugt -> Transition : " + transition.getName() + ", Source : " + source.getLabel() + ", Destination : " + destination.getLabel());
		this.transition =  transition;
		this.sourceNode = source;
		this.destinationNode = destination;
	}
	
	/**
	 * Getter-Methode, die die Transition zurück gibt.
	 * @return Geschaltene Transition um nächsten Knoten zu erreichen.
	 */
	public String getTransitionName() {
		return this.transition.getName();
	}
	
	/**
	 * Getter-Methode, die den Startkonten zurück gibt. Rückgabe des DecisionNode´s, auf dem diese Kante zeigt
	 * @return Startknoten mit der vorherigen Markierung.
	 */
	public mypackage.model.DecisionNode getSourceNode(){
		return this.sourceNode;
	}
	
	/**
	 * Getter-Methode, die den Zielknoten zurück gibt.
	 * @return Zielknoten
	 */
	public mypackage.model.DecisionNode getDestinationNode(){
		return this.destinationNode;
	}
	
	/**
	 * Setter-Methode, die das GraphStream-Objekt zur Visualisierung übergibt.
	 * @param edge GraphStream-Objekt, das eine Kanten repräsentiert.
	 */
	public void setEdge(org.graphstream.graph.Edge edge) {
		this.edge = edge;
	}
	
	/**
	 * Getter-Methode, die das GraphStream-Objekt zur Visualisierung zurück gibt.
	 * @return GraphStream-Objekt, das eine Kante repräsentiert.
	 */
	public org.graphstream.graph.Edge getEdge(){
		return this.edge;
	}
	
	/**
	 * Methode setzt Beschriftung zu Style-Attribute im GraphStream-Objekt
	 */
	public void setAttribute() {
		if(edge != null) {
			edge.addAttribute("ui.style", "arrow-shape: arrow;");
			edge.setAttribute("ui.label", "[" + this.transition.getID() + "] " + this.transition.getName());
		}
	}
	
	/**
	 * Setter-Methode, die die ID des Objektes übergibt.
	 * @param id ID des aktuellen Objektes, zur Speicherung in einem Attribut.
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Getter-Methode, die die ID des Objektes zurück gibt.
	 * @return ID des aktuellen Objektes zur Identifikation.
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Getter-Methode, die die Transition zurück gibt, um zum nächsten Knoten zu gelangen.
	 * @return Transition im Petrinetz, die geschalten werden muss um Zielknoten zu erzeugen.
	 */
	public Transition getTransition() {
		return this.transition;
	}

}
