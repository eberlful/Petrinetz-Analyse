package mypackage.model;

import org.graphstream.graph.Node;

/**
 * Instanzklasse für Erreichbarkeitsgraph-Konten.
 * Datentyp, der einen Knoten im Entscheidungsbaum darstellen soll. Container mit Speicherung
 * der Markierungen des Petrinetzes zum Zeitpunkt der Erzeugung des Objektes.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class DecisionNode {
	
	private java.util.HashMap<String, mypackage.model.DecisionEdge> outputEdges;
	private java.util.HashMap<String, mypackage.model.DecisionEdge> inputEdges;
	private DecisionEdge formFirstNode;
	
	private java.util.TreeMap<String, Integer> currentMarking;
	
	private java.util.HashMap<String, Transition> switchableTransitions;
	
	private String label = "";
	
	private int id;
	
	private Node node;
	
	/**
	 * Konstruktor ohne Übergabeparameter
	 */
	public DecisionNode() {
		outputEdges = new java.util.HashMap<String, DecisionEdge>();
		inputEdges = new java.util.HashMap<String, DecisionEdge>();
	}
	
	/**
	 * Konstruktor, indem gleich das erste Node mit Inhalt erzeugt wird
	 * @param places HashMap von allen aktuellen Places, um Markierung zu entnehmen
	 */
	public DecisionNode(java.util.HashMap<String, Place> places) {
		outputEdges = new java.util.HashMap<String, DecisionEdge>();
		inputEdges = new java.util.HashMap<String, DecisionEdge>();
		System.out.println("DecisionNode erzeugt");
		
		// Erzeuge Liste der Places und deren Inhalte
		currentMarking = new java.util.TreeMap<String, Integer>();
		for(java.util.Map.Entry<String, Place> entry : places.entrySet()) {
			currentMarking.put(entry.getKey(), entry.getValue().getMarking());
		}
		
		// Label erzeugen
		int count = currentMarking.entrySet().size();
		int counter = 0;
		label += " ( ";
		for(java.util.Map.Entry<String, Integer> entry : currentMarking.entrySet()) {
			counter++;
			label += entry.getValue();
			if(counter != count) {
				label += " | ";
			}
		}
		label += " ) ";
		System.out.println("Label des neuen Knotens: " + label);
	}
	
	/**
	 * Setter für ID des Nodes
	 * @param id Integer-ID
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Getter für ID des Nodes
	 * @return Integer-ID
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Funktion fügt übergebenes DecisionEdge-Objekt in Output-HashMap ein.
	 * @param edge Kante, die von diesem Objekt referenziert wird.
	 */
	public void addOutputEdge(mypackage.model.DecisionEdge edge) {
		this.outputEdges.put(edge.getTransitionName(), edge);
	}
	
	/**
	 * Funktion fügt Kante, die auf dieses Objekt zeigt ein.
	 * @param edge Kante, die auf dieses Objekt zeigt.
	 */
	public void addInputEdge(mypackage.model.DecisionEdge edge) {
		this.inputEdges.put(edge.getTransitionName(), edge);
	}
	/**
	 * Getter-Methode für ein HashMap, indem alle Kanten gespeichert sind, die von diesem
	 * Knoten weg zeigen.
	 * @return Liste von Kanten, die von diesem Objekt weg zeigen.
	 */
	public java.util.HashMap<String, DecisionEdge> getOutputEdges(){
		return this.outputEdges;
	}
	
	/**
	 * Getter-Methode für ein HashMap, indem alle Kanten gespeichert sind, die auf dieses
	 * Objekt zeigen.
	 * @return Liste von Kanten, die auf dieses Objekt zeigen.
	 */
	public java.util.HashMap<String, DecisionEdge> getInputEdges(){
		return this.inputEdges;
	}
	
	/**
	 * Setter-Methode, die das GraphStream-Objekt zur Visualisierung speichert.
	 * @param node GraphStream-Objekt zur Visualisierung.
	 */
	public void setNode(Node node) {
		this.node = node;
	}
	
	/**
	 * Getter-Methode, die das GraphStream-Objekt zur Visualisierung zurückgibt.
	 * @return GraphStream-Objekt zur Visualisierung.
	 */
	public org.graphstream.graph.Node getNode(){
		return this.node;
	}
	
	/**
	 * Funktion setzt Attribute im GraphStream-Objekt.
	 * @param startNode Freigabe eines Startknoten, weil dieses besondere Attribute bekommt.
	 */
	public void setAttribute(boolean startNode) {
		this.node.addAttribute("ui.label", label);
		if(startNode) {
			this.node.addAttribute("ui.style", "stroke-color: #8397B3;");
			this.node.addAttribute("ui.class", "startNode");
		}
		this.node.addAttribute("ui.style", "size: " + String.valueOf(currentMarking.entrySet().size()*25) + ", 20" + ";");
	}
	
	/**
	 * Getter-Methode, die das Label dieses Objektes als String zurückgibt.
	 * @return Label als String.
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * Getter-Methode, die die Markierung zum Zeitpunkt der Erzeugung diese Objektes wieder gibt.
	 * @return Markierung als Liste zum Zeitpunkt der Erzeugung.
	 */
	public java.util.TreeMap<String, Integer> getMarkings(){
		return this.currentMarking;
	}
	
	/**
	 * Vergleicht die einzelnen Marken, um festzustellen, ob Pfad unbeschränkt ist
	 * @param sourceNode Zu vergleichende Objekt
	 * @return True = unbeschränkt, False = beschränkt
	 */
	public boolean compareMarkings(DecisionNode sourceNode) {
		int counter = 0;
		// Durchlauf aller Markierungen
		for(java.util.Map.Entry<String, Integer> entry : this.currentMarking.entrySet()) {
			for(java.util.Map.Entry<String, Integer> referenceEntry : sourceNode.getMarkings().entrySet()) {
				if(entry.getKey().equals(referenceEntry.getKey())) {
					if(entry.getValue() == referenceEntry.getValue()) {
						counter++;
					}
				}
			}
		}
		if(counter == (this.currentMarking.size()-1) && counter != 0) {
			// True, wenn unbeschränkt
			return true;
		} else {
			// False, wenn beschränkt
			return false;
		}
	}
	
	/**
	 * Prüfung auf Gleichheit der Markierungen
	 * @param treeMap Liste der zu vergleichenden Markierungen
	 * @return Ergebnis der Auswertung, ob gleich oder ungleich
	 */
	public boolean compareMarkings(java.util.TreeMap<String, Integer> treeMap) {
		java.util.Iterator<java.util.Map.Entry<String, Integer>> iteratorReferenceTreeMap =  treeMap.entrySet().iterator();
		java.util.Iterator<java.util.Map.Entry<String, Integer>> iteratorLocalTreeMap = this.currentMarking.entrySet().iterator();
		while(iteratorReferenceTreeMap.hasNext() && iteratorLocalTreeMap.hasNext()) {
			java.util.Map.Entry<String, Integer> entryReference = iteratorReferenceTreeMap.next();
			java.util.Map.Entry<String, Integer> entryLocal = iteratorLocalTreeMap.next();
			if(entryReference.getKey().equals(entryLocal.getKey())) {
				if(entryReference.getValue().equals(entryLocal.getValue())) {
					
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		if(iteratorReferenceTreeMap.hasNext() && !iteratorLocalTreeMap.hasNext() || !iteratorReferenceTreeMap.hasNext() && iteratorLocalTreeMap.hasNext()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Setter-Methode, die eine Kante setzt, die vom Knoten kommt, in Richtung Startknoten.
	 * @param edge Kante in Richtung Startknoten.
	 */
	public void setFromFirstNode(DecisionEdge edge) {
		System.out.println("Node: " + this.label + ", von " + edge.getSourceNode().getLabel());
		this.formFirstNode = edge;
	}
	
	/**
	 * Getter-Methode, die eine Kante zurückgibt, die vom Knoten kommt, in Richtung Startknoten.
	 * @return Kante in Richtung Startknoten
	 */
	public DecisionEdge getFromFirstNode() {
		return this.formFirstNode;
	}

}
