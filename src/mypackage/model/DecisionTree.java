package mypackage.model;

import java.util.Map.Entry;

import Exceptions.NarrownesAnalysisException;
import mypackage.view.NarrownessAnalysisDialog;

/**
 * Behälterklasse für die einzelnen Nodes. Enthält die Datenstruktur des Entscheidungsbaum.
 * Alle erzeugten Knoten kann man vom Startknoten aus erreichen.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class DecisionTree {
	
	private DecisionNode startNode = null;
	
	private DecisionNode lastNode = null;
	
	private DecisionGraph decisionGraph = null;
	
	private java.util.HashMap<Integer, DecisionNode> decisionNodes;
	
	private int edgeCounter = 0;
	
	private int nodeCounter = 0;
	
	private DecisionNode mStartNode = null;
	
	private DecisionNode mEndNode = null;
	
	/**
	 * Konstruktor:
	 * Beim Erzeugen eines Objektes wird automatisch erster Knoten erzeugt.
	 * @param net Petrinetz-Referenz zur Erzeugung des Startknotens.
	 */
	public DecisionTree(PNMLNet net) {
		this.startNode = new DecisionNode(net.getPlaces());
		this.lastNode = this.startNode;
		this.startNode.setID(nodeCounter);
		this.nodeCounter++;
		
		// HashMap erzeugen
		this.decisionNodes = new java.util.HashMap<Integer, DecisionNode>();
		this.decisionNodes.put(startNode.getID(), startNode);
	}
	
	/**
	 * Methode erzeugt aus dem Entscheidungsbaum eine Erreichbarkeitsgraph zur Visualisierung.
	 * @return Erreichbarkeitsgraph zur Visualisierung.
	 */
	public DecisionGraph createDecisionGraph() {
		DecisionGraph graph = new DecisionGraph();
		this.decisionGraph = graph;
		graph.createStartNode(startNode);
		startNode.setAttribute(true);
		return graph;
	}
	
	/**
	 * Erzeugt einen Knoten. Nur für die Erstellung des partiellen Erreichbarkeitsgraphen.
	 * @param transition Transition, die geschalten wurde um den aktuellen Knoten zu erzeugen.
	 * @param places Aktuelle Stellen des Petrinetzes.
	 */
	public void addPartialDecisionNode(Transition transition, java.util.HashMap<String, mypackage.model.Place> places) {
		DecisionNode node = new DecisionNode(places);
		node.setID(nodeCounter);
		decisionNodes.put(node.getID(), node);
		nodeCounter++;
		DecisionEdge edge = new DecisionEdge(transition, this.lastNode, node);
		edge.setID(edgeCounter);
		edgeCounter++;
		this.lastNode.addOutputEdge(edge);
		node.addInputEdge(edge);
		this.lastNode = node;
	}
	
	/**
	 * Erzeugt einen Knoten. Nur für die Erstellung des partiellen Erreichbarkeitsgraphen im Beschränktheitsalgorithmus.
	 * @param transition Transition, die geschalten wurde um den aktuellen Knoten zu erzeugen.
	 * @param places Aktuelle Stellen des Petrinetzes.
	 * @param analysis Beschränktheitsanalyse-Objekt, in dem sämtliche Informationen gespeichert sind.
	 * @return Kante, die zu dem neuen Knoten führt.
	 */
	public DecisionEdge addPartialDecisionNodeReturn(Transition transition, java.util.HashMap<String, mypackage.model.Place> places, NarrownessAnalysis analysis) {
		DecisionNode localNode = isNodeAlreadyAdded(places);
		if(localNode != null) {
			// Knoten schon vorhanden
			System.out.println("Knoten schon vorhanden -> " + localNode.getLabel());
			analysis.setNodeAlreadyExist(true);
			
			// Kante erstellen
			DecisionEdge edge = new DecisionEdge(transition, this.lastNode, localNode);
			edge.setID(edgeCounter);
			edgeCounter++;
			this.lastNode.addOutputEdge(edge);
			localNode.addInputEdge(edge);
			this.lastNode = localNode;
			return edge;
		} else {
			// Knoten noch nicht vorhanden
			System.out.println("Knoten noch nicht vorhanden");
			
			// Knoten erstellen
			DecisionNode node = new DecisionNode(places);
			node.setID(nodeCounter);
			decisionNodes.put(node.getID(), node);
			nodeCounter++;
			
			// Kante erstellen
			DecisionEdge edge = new DecisionEdge(transition, this.lastNode, node);
			edge.setID(edgeCounter);
			edgeCounter++;
			this.lastNode.addOutputEdge(edge);
			node.addInputEdge(edge);
			this.lastNode = node;
			return edge;
		}
	}
	
	/**
	 * Erzeugt einen Knoten. Nur für die Erstellung des partiellen Erreichbarkeitsgraphen.
	 * @param transition Transition, die geschalten wurde um den aktuellen Knoten zu erzeugen.
	 * @param places Aktuelle Stellen des Petrinetzes.
	 * @return Kante, die zu dem neuen Knoten führt.
	 */
	public DecisionEdge addPartialDecisionNodeReturn(Transition transition, java.util.HashMap<String, mypackage.model.Place> places) {
		DecisionNode localNode = isNodeAlreadyAdded(places);
		if(localNode != null) {
			DecisionEdge edge = new DecisionEdge(transition, this.lastNode, localNode);
			// Knoten schon vorhanden
			System.out.println("Knoten schon vorhanden -> " + localNode.getLabel());
			if(localNode.getInputEdges().containsKey(transition.getID())) {
				// Kante schon vorhanden
				System.out.println("Kante schon vorhanden");
				edge = localNode.getInputEdges().get(transition.getID());
			} else {
				// Neue Kante
				edge = new DecisionEdge(transition, this.lastNode, localNode);
			}
			edge.setID(edgeCounter);
			edgeCounter++;
			this.lastNode.addOutputEdge(edge);
			localNode.addInputEdge(edge);
			this.lastNode = localNode;
			return edge;
		} else {
			// Knoten noch nicht vorhanden
			System.out.println("Knoten noch nicht vorhanden");
			// Neuer Knoten
			DecisionNode node = new DecisionNode(places);
			node.setID(nodeCounter);
			decisionNodes.put(node.getID(), node);
			nodeCounter++;
			// Neue Kante
			DecisionEdge edge = new DecisionEdge(transition, this.lastNode, node);
			edge.setID(edgeCounter);
			edgeCounter++;
			this.lastNode.addOutputEdge(edge);
			node.addInputEdge(edge);
			this.lastNode = node;
			return edge;
		}
	}
	
	/**
	 * Checkt, ob Knoten schon erzeugt wurde, falls ja muss nur Kanten eingefügt werden
	 * @param places Alle Stellen des aktuellen Petrinetzes
	 * @return Kante falls vorhanden.
	 */
	private DecisionNode isNodeAlreadyAdded(java.util.HashMap<String, mypackage.model.Place> places) {
		java.util.TreeMap<String, Integer> currentMarking = new java.util.TreeMap<String, Integer>();
		// Iteration über alle Stellen
		for(java.util.Map.Entry<String, Place> entry : places.entrySet()) {
			currentMarking.put(entry.getKey(), entry.getValue().getMarking());
		}
		// Iteration über alle Knoten
		for(java.util.Map.Entry<Integer, DecisionNode> entry : this.decisionNodes.entrySet()) {
			if(entry.getValue().compareMarkings(currentMarking)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Getter für LastNode-Objekt
	 * @return DecisionNode-Objekt
	 */
	public DecisionNode getLastNode() {
		return this.lastNode;
	}
	
	/**
	 * Setter für das Objekt lastNode, wird bei ClickListener verwendet
	 * @param lastNode DecisionNode-Objekt
	 */
	public void setLastNode(DecisionNode lastNode) {
		this.lastNode = lastNode;
	}
	
	/**
	 * Getter-Methode, die den Startknoten zurückgibt.
	 * @return Referenz des Startknotens.
	 */
	public DecisionNode getStartNode() {
		return this.startNode;
	}
	
	/**
	 * Getter für HashMap für alle DecisionNodes die erzeugt wurden.
	 * @return Liste alle Entscheidungsknoten.
	 */
	public java.util.HashMap<Integer, DecisionNode> getDecisionNodes(){
		if(this.decisionNodes == null) {
			System.out.println("DecisionTree -> getDecisionNodes: Fehlende Referenz auf HashMap");
		}
		return this.decisionNodes;
	}
	
	/**
	 * Checkt partiellen Erreichbarkeitsgraphen auf Beschränktheit.
	 */
	public void checkPathForAccessibility() {
		try {
			// Checke Pfad auf Beschränktheit
			boolean resultReturn = checkPathForUnlimitedAlongPath();
			if(resultReturn) {
				// Unbeschränkt
				markUnlimitedPath();
				NarrownessAnalysisDialog dialog = new NarrownessAnalysisDialog(this.decisionGraph.getFrame());
				dialog.show();
				String html = "<center><h1>Unbeschränkt</h1></center>";
				dialog.setHTML(html);
			}
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	/**
	 * Prüft Pfad auf Beschränktheitskriterium, indem durch den Pfad iteriert wird.
	 * @return Ergebnis des Beschränktheitsalgorithmuses.
	 * @throws NarrownesAnalysisException
	 */
	private boolean checkPathForUnlimitedAlongPath() throws NarrownesAnalysisException {
		java.util.ArrayList<DecisionNode> path = new java.util.ArrayList<DecisionNode>();
		DecisionNode node = this.getLastNode();
		// Iteriere über Pfad
		while(node != null) {
			if(node != null) {
				if(node != this.getLastNode()) {
					boolean result = this.compareMarkings(node, this.getLastNode());
					if(result) {
						System.out.println(node.getLabel() + " - " + this.getLastNode().getLabel());
						System.out.println("Unbeschränkt -> ");
						this.mEndNode = this.getLastNode();
						this.mStartNode = node;
						return true;
					}
				}
				if(!path.contains(node)) {
					path.add(node);
				} else {
					return false;
				}
			}
			if(node.getFromFirstNode() == null) {
				node = null;
			} else {
				node = node.getFromFirstNode().getSourceNode();
			}
		}
		int counter = 1;
		for(DecisionNode decisionNode : path) {
			System.out.println(counter + " : " + decisionNode.getLabel());
			counter++;
		}
		return false;
	}
	
	/**
	 * Ein Petrinetz ist genau dann unbeschränkt, wenn es eine erreichbare Markierung m und eine von m aus erreichbare Markierung m' gibt, mit folgender Eigenschaft:
	 * m' weist jeder Stelle mindestens so viele Marken zu wie m und dabei mindestens einer Stelle sogar mehr Marken als m.
	 * @param firstNode steht für den Node m
	 * @param secondNode steht für den Node m'
	 * @return Ergebnis des Vergleiches der Marken
	 * @throws NarrownesAnalysisException 
	 */
	private boolean compareMarkings(DecisionNode firstNode, DecisionNode secondNode) throws NarrownesAnalysisException {
		// m' >= m
		// unbeschränkt
		boolean unlimited = false;
		// beschränkt
		boolean limited = false;
		int counterUnlimited = 0;
		int counterLimited = 0;
		if(firstNode.getMarkings().entrySet().size() == secondNode.getMarkings().entrySet().size()) {
			java.util.Iterator<java.util.Map.Entry<String, Integer>> firstIterator = firstNode.getMarkings().entrySet().iterator();
			java.util.Iterator<java.util.Map.Entry<String, Integer>> secondIterator = secondNode.getMarkings().entrySet().iterator();
			while(firstIterator.hasNext() && secondIterator.hasNext()) {
				// Vergleiche von beiden Nodes die Marken (m <= m')
				if(firstIterator.next().getValue() <= secondIterator.next().getValue()) {
					counterUnlimited++;
				} else {
					counterLimited++;
				}
			}
			// Check auf Unbeschränktheit
			if(counterUnlimited == firstNode.getMarkings().size()) {
				// unbeschränkt
				return true;
			} else {
				// beschränkt
				return false;
			}
		} else {
			throw new  Exceptions.NarrownesAnalysisException(1);
		}
	}
	
	/**
	 * Getter-Methode, die den aktuellen Zählerstand für Kanten zurück gibt.
	 * @return Aktueller Zählerstand der Kanten.
	 */
	public int getEdgeCounter() {
		return this.edgeCounter;
	}
	
	/**
	 * Getter-Methode, die den aktuellen Zählerstand für Knoten zurück gibt.
	 * @return Aktueller Zählerstand der Knoten.
	 */
	public int getNodeCounter() {
		return this.nodeCounter;
	}
	
	/**
	 * 
	 */
	public void markUnlimitedPath() {
		this.mStartNode.getNode().setAttribute("ui.class", "mStartNode");
		this.mEndNode.getNode().setAttribute("ui.class", "mEndNode");
		for(mypackage.model.DecisionEdge decisionEdge : getUnlimitedPath()) {
			System.out.println(decisionEdge.getID() + " rot färben");
			decisionEdge.getEdge().setAttribute("ui.class", "unlimited");
			//decisionEdge.getEdge().setAttribute("ui.style", "text-color: red;");
		}
	}
	
	/**
	 * Sucht Kante, die zu eine Unbeschränktheit führte.
	 * @return Kante, die zu einer Unbeschränktheit führte.
	 */
	private java.util.ArrayList<DecisionEdge> getUnlimitedPath() {
		java.util.ArrayList<DecisionEdge> transitions = new java.util.ArrayList<DecisionEdge>();
		DecisionNode node = this.mEndNode;
		// Stack befüllen
		while(node != this.mStartNode) {
			transitions.add(node.getFromFirstNode());
			node = node.getFromFirstNode().getSourceNode();
		}
		return transitions;
	}

}
