package mypackage.model;
/**
 * Klasse repräsentiert die Verbindung im Petrinetz zwischen Transition und Stelle oder Stelle und Transition.
 * @author Markus Eberl
 * @version 1.0.0
 * @param <S> Typ einer Stelle oder einer Transition
 * @param <D> Typ einer Stelle oder einer Transition
 */
public class DirectedEdge <S extends Element, D extends Element> {
	
	private S source = null;
	private D destination = null;
	
	private String id = null;
	
	private org.graphstream.graph.Edge edge;
	
	/**
	 * Konstruktor, der beim Erzeugen gleich die ID und die Referenzen des Zieles und der Quelle benutzt.
	 * @param id ID der Kante.
	 * @param source Referenz des Quellknotens.
	 * @param destination Referenz des Zielknotens.
	 */
	public DirectedEdge(String id, S source, D destination) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		source.addArcOut(id, this);
		destination.addArcIn(id, this);
	}
	
	/**
	 * Getter-Methode, die das Zielobjekt dieser Kante zurück gibt.
	 * @return Zielobjekt dieser Kante.
	 */
	public D getDestination() {
		return destination;
	}
	
	/**
	 * Getter-Methode, die das Quellobjekt dieser Kante zurück gibt.
	 * @return Quellobjekt dieser Kante.
	 */
	public S getSource() {
		return source;
	}
	
	/**
	 * Setter-Methode, die das Quellobjekt übergibt und speichert.
	 * @param source Quellobjekt dieser Kante.
	 */
	public void setSource(S source) {
		this.source = source;
	}
	
	/**
	 * Setter-Methode, die das Zielobjekt übergibt und speichert.
	 * @param destination Zielobjekt dieser Kante.
	 */
	public void setDestination(D destination) {
		this.destination = destination;
	}
	
	/**
	 * Getter-Methode, die die ID dieser Kante zurück gibt.
	 * @return ID dieser Kante.
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * Setter-Methode, die die GraphStream-Kante zur Visualisierung setzt.
	 * @param edge GraphStream-Kante zur Visualisierung.
	 */
	public void setEdge(org.graphstream.graph.Edge edge) {
		this.edge = edge;
	}
	
	/**
	 * Getter-Methode, die die GraphStream-Kante zur Visualisierung zurück gibt.
	 * @return GraphStream-Kante zur Visualisierung.
	 */
	public org.graphstream.graph.Edge getEdge(){
		return this.edge;
	}
	
	/**
	 * Methode setzt Attribute in dem GraphStream-Objekt zur Visualisierung.
	 */
	public void setAttributes() {
		// Attribute für Kante setzen
		edge.addAttribute("ui.label", "[" + id + "]");
	}
}
