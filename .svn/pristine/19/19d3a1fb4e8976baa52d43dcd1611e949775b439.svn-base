package mypackage.model;

import java.util.Stack;

import Exceptions.NarrownesAnalysisException;

/**
 * Beschränktheitanalyse des Petrinetzes
 * @author Markus Eberl 7642300
 * @version 1.0.0
 */
public class NarrownessAnalysis {
	
	private PNMLNet petriNet = null;
	
	private DecisionTree decisionTree = null;
	
	private boolean isLimited = false;
	
	private int nodes = 0;
	
	private int edges = 0;
	
	private boolean nodeAlreadyExist = false;
	
	private DecisionNode mStartNode = null;		// m
	private DecisionNode mEndNode = null;		// m'
	
	/**
	 * Konstruktor für die Beschränktheitsanalyse-Klasse
	 * @param net {@link PNMLNet}-Objekt mit dem Startzustand
	 * @param decisionTree Referenz der Datenstruktur für Erreichbarkeitsbaum.
	 */
	public NarrownessAnalysis(PNMLNet net, DecisionTree decisionTree) {
		System.out.println("NarrownessAnalysis erzeugt");
		this.petriNet = net;
		this.decisionTree = decisionTree;
	}
	
	/**
	 * Funktion startet die Analyse des im Konstruktor übergebenen Petrinetzes
	 */
	public void startNarrownessAnalysis() {
		createDecisionTree();
	}
	
	/**
	 * Getter-Methode, die das Ergebnis des Algorithmus abfrägt.
	 * @return Ergebnis des Algorithmus.
	 */
	public boolean isLimited() {
		return isLimited;
	}
	
	/**
	 * Entscheidungsbaum Erzeugung wird gestartet.
	 */
	private void createDecisionTree() {
		// Check auf gültige Referenz
		if(this.petriNet != null) {
			// Erzeuge Startknoten mit DecisionTree-Objekt
			if(decisionTree == null) {
				decisionTree = new DecisionTree(petriNet);
			}
			System.out.println("DecisionTree mit erstem Element erzeugt");
			System.out.println("Starte Beschränktheitsanalyse !!!");
			boolean result = createNarrownessAnalysis();
			if(result) {
				// Unbeschränkt
			} else {
				// Beschränkt
				System.out.println("fertig mit beschränkt");
				isLimited = true;
			}
		} else {
			System.out.println("PetriNetz in NarrownessAnalysis NullPointerException");
		}
	}
	
	/**
	 * Rekursive-Methode, die sich durch das Petrinetz hangelt.
	 * Der Entscheidungsbaum wird mittels Tiefensuche erzeugt und durchlaufen.
	 * true -> Break der Rekursion
	 * @return Ergebnis der Analyse
	 */
	private boolean createNarrownessAnalysis() {
		try {
			// Alle schaltbaren Transitionen zum aktuellem Petrinetz
			java.util.ArrayList<mypackage.model.TransitionContainer> switchableTransition = petriNet.getSwitchableTransitions();
			if(switchableTransition.size() == 0) {
				System.out.println("Es wurde wurden keine schaltbaren Transitionen in " + decisionTree.getLastNode().getLabel() + " gefunden");
				// Abbruch, weil es keine schaltbaren transiionen mehr gibt
				return false;
			}
			
			// DecisionNode-Objekt zwischenspeichern
			DecisionNode nodeCache = decisionTree.getLastNode();
			
			// Speicher der aktuellen Markierungen
			java.util.HashMap<String, Integer> markingCache = new java.util.HashMap<String, Integer>();
			for(java.util.Map.Entry<String, Place> entry : this.petriNet.getPlaces().entrySet()) {
				markingCache.put(entry.getKey(), entry.getValue().getMarking());
			}
			
			// Iteriere durch alle schaltbaren Transitionen durch
			java.util.Iterator<TransitionContainer> iterator = switchableTransition.iterator();
		    while (iterator.hasNext()) {
				TransitionContainer transitionContainer = (TransitionContainer) iterator.next();
				
				// Check, ob Transition noch schaltbar ist, wenn nicht -> Fehler
				if(transitionContainer.isAlreadySwitchable()) {
					// Schalten der Transition und rekursiver Aufruf dieser Methode
					transitionContainer.switchTransition();
					// Erzeuge neuen DecisionNode mit neuen Stellen und zugehörigem DecisionEdge
					
					decisionTree.setLastNode(nodeCache);
					
					DecisionEdge edge = decisionTree.addPartialDecisionNodeReturn(transitionContainer.getTransition(), petriNet.getPlaces(), this);
					edge.getDestinationNode().setFromFirstNode(edge);
					
					// Checke ob neuer Knoten die Unbeschränktheit beweist
					if(checkPathForUnlimitedAlongPath()) {
						// Pfad ist unbeschränkt, hier wird NarrownessAnalysis gestoppet und der bisherige Pfad gespeichert um ihn anschließend zu markieren
						this.isLimited = false;
						return true;
					} else {
						boolean result = false;
						if(nodeAlreadyExist) {
							nodeAlreadyExist = false;
						} else {
							result = createNarrownessAnalysis();
						}
						// Pfad ist beschränkt, somit wird ein Rekursionsaufruf gestartet
						
						setMarkingInPetrNet(markingCache, this.petriNet);
						
						// false = bei einem Ende des bisherigen Pfades
						if(result) {
							// Unbeschränktheit wurde festgestellt
							System.out.println("Result => true");
							return true;
						} else {
							// Beschränktheit wurde festgestellt
							System.out.println("Result => false");
						}
					}
				} else {
					throw new Exceptions.TransitionContainerException(2);
				}
			}
		} catch (Exceptions.TransitionContainerException ex) {
			System.out.println(ex.toString());
		} catch (Exceptions.NarrownesAnalysisException ex) {
			System.out.println(ex.toString());
		}
		// Automatisch
		return false;
	}
	
	/**
	 * Setter-Methode, die eine Variable speichert, falls ein Knoten schn existiert.
	 * @param exist Wahrheitswert, ob gewisser Knoten schon exisitert.
	 */
	public void setNodeAlreadyExist(boolean exist) {
		this.nodeAlreadyExist = exist;
	}
	
	/**
	 * Methode prüft einen Pfad auf Unbeschränktheitskriterium.
	 * @return true = unbeschränkt, false = beschränkt
	 * @throws NarrownesAnalysisException 
	 */
	private boolean checkPathForUnlimited() throws NarrownesAnalysisException {
		// Pfad des aktuellen partiellen Erreichbarkeitsgraphen
		java.util.ArrayList<DecisionNode> path = new java.util.ArrayList<DecisionNode>();
		// Iterationsnode erzeugen
		DecisionNode node = this.decisionTree.getLastNode();
		while(node.getFromFirstNode() != null) {
			System.out.println("Von Node: " + node.getLabel() + ", zu Node: " + node.getFromFirstNode().getSourceNode().getLabel());
			if (node != this.decisionTree.getLastNode()) {
				boolean result = this.compareMarkings(node, this.decisionTree.getLastNode());
				// Unbeschränkt
				if (result) {
					System.out.println("Node: " + node.getLabel() + " unbeschränkt");
					return true;
				} else {
					System.out.println("Node: " + node.getLabel() + " beschränkt");
					return false;
				}
			}
			path.add(node);
			node = node.getFromFirstNode().getSourceNode();
		}
		return false;
	}
	
	/**
	 * Methode checkt Pfad auf Unbeschränktheit.
	 * @return Ergebnis ob Pfad unbeschränkt ist.
	 * @throws NarrownesAnalysisException
	 */
	private boolean checkPathForUnlimitedAlongPath() throws NarrownesAnalysisException {
		java.util.ArrayList<DecisionNode> path = new java.util.ArrayList<DecisionNode>();
		DecisionNode node = this.decisionTree.getLastNode();
		// Iteration durch Pfad
		while(node != null) {
			if(node != null) {
				if(node != this.decisionTree.getLastNode()) {
					// Knoten vergleichen
					boolean result = this.compareMarkings(node, this.decisionTree.getLastNode());
					if(result) {
						System.out.println(node.getLabel() + " - " + this.decisionTree.getLastNode().getLabel());
						System.out.println("Unbeschränkt -> ");
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
				// Neuen Knoten laden
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
	 * @return Ergebnis ob zwei übergebene Knoten die Unbeschränktheitskriterien erfüllen.
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
				this.mStartNode = firstNode;
				this.mEndNode = secondNode;
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
	 * Setzt die Marken der Stellen wieder auf einen bestimmten Zeitpunkt zurück.
	 * @param markings Liste der alten Markierungen
	 * @param petriNet Petrinetz, das die neue Markierung bekommt.
	 * @throws NarrownesAnalysisException 
	 */
	private void setMarkingInPetrNet(java.util.HashMap<String, Integer> markings, PNMLNet petriNet) throws NarrownesAnalysisException {
		int placeCounter = 0;
		// Iteration über alle Stellen
		for(java.util.Map.Entry<String, Integer> entry : markings.entrySet()) {
			for(java.util.Map.Entry<String, Place> place : petriNet.getPlaces().entrySet()) {
				if(entry.getKey().equals(place.getKey())) {
					placeCounter++;
					place.getValue().setMarkingWithoutGUI(entry.getValue());
				}
			}
		}
		// Fehler falls nicht alle Stellen berücksichtigt wurden
		if(placeCounter != petriNet.getPlaces().size()) {
			throw new Exceptions.NarrownesAnalysisException(2);
		}
	}
	
	/**
	 * Rückgabefunktion falls Petrinetz unbeschränkt ist.
	 * @return Pfad und Länge des unbeschränkten partiellen Erreichbarkeitsgraph
	 */
	public String getPathInformation() {
		String pathInformation = "";
		DecisionNode node = this.mEndNode;
		java.util.Stack<Transition> transitionStack = new java.util.Stack<Transition>();
		// Stack befüllen
		while(node != this.mStartNode) {
			transitionStack.push(node.getFromFirstNode().getTransition());
			node = node.getFromFirstNode().getSourceNode();
		}
		// Rückgabestring erstellen
		pathInformation += transitionStack.size() + ":(";
		Transition transition = null;
		while(transitionStack.size() != 0) {
			transition = transitionStack.pop();
			pathInformation += transition.getID();
			
			if(transitionStack.size() != 0) {
				pathInformation += ",";
			}
		}
		pathInformation += ")";
		return pathInformation;
	}
	
	/**
	 * Getter-Methode für den Pfad der die Unbeschränktheitskriterien erfüllt.
	 * @return Pfad der die Unbeschränktheitskriterien erfüllt.
	 */
	public java.util.ArrayList<DecisionEdge> getUnlimitedPath() {
		java.util.ArrayList<DecisionEdge> transitions = new java.util.ArrayList<DecisionEdge>();
		DecisionNode node = this.mEndNode;
		// Stack befüllen
		while(node != this.mStartNode) {
			transitions.add(node.getFromFirstNode());
			node = node.getFromFirstNode().getSourceNode();
		}
		return transitions;
	}
	
	/**
	 * Getter-Methode, die den Stand des Knotenzählers zurück gibt.
	 * @return Stand des Knotenzählers.
	 */
	public int getNodesNumber() {
		this.nodes = this.decisionTree.getNodeCounter();
		return this.nodes;
	}
	
	/**
	 * Getter-Methode, die den Stand des Kantenzählers zurück gibt.
	 * @return Stand des Kantenzählers.
	 */
	public int getEdgesNumber() {
		this.edges = this.decisionTree.getEdgeCounter();
		return this.edges;
	}
	
	/**
	 * Getter-Methode, die den ersten Knoten zurück gibt, bei dem der Unbeschränktheitspfad startet.
	 * @return Startknoten des Unbeschränktheitspfades.
	 */
	public DecisionNode getMStartNode() {
		return this.mStartNode;
	}
	
	/**
	 * Getter-Methode, die den letzten Knoten zurück gibt, bei dem der Unbeschränktheitspfad endet.
	 * @return Endknoten des Unbeschränktheitspfades.
	 */
	public DecisionNode getMEndNode() {
		return this.mEndNode;
	}
}
