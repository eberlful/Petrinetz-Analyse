package mypackage.model;

import java.util.ArrayList;
import java.util.HashMap;

import Exceptions.PNMLParserException;
import Exceptions.TransitionContainerException;
/**
 * Containerklasse, die ein Petrinetz repräsentiert und die dazugehörige Datenstruktur erstellt.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class PNMLNet {
	
	private HashMap<String, Place> places;
	
	private HashMap<String, Transition> transitions;
	
	private Transition currentTransiton = null;
	
	/**
	 * Konstruktor, der eine Liste für die Transitionen erstellt.
	 */
	public PNMLNet() {
		transitions = new HashMap<String, Transition>();
	}
	
	/**
	 * Methode speichert eine übergebene Stelle in einer Liste.
	 * @param place Stelle, die in einer Liste gespeichert werden soll.
	 */
	public void addPlaces(Place place) {
		if(places == null) {
			places = new HashMap<String, Place>();
		}
		places.put(place.getID(), place);
	}
	
	/**
	 * Methode speichert eine übergebene Transition in einer Liste.
	 * @param transition Transition, die in einer Liste gespeichert werden soll.
	 */
	public void addTransition(Transition transition) {
		if(transitions == null) {
			transitions = new HashMap<String, Transition>();
		}
		transitions.put(transition.getID(), transition);
	}
	
	/**
	 * Methode speichert einen übergebenen Namen, der zu einer ID gehört.
	 * @param id ID des Elements.
	 * @param name Namen des Elements.
	 * @throws PNMLParserException Fehler bei dem Parsingaktion.
	 */
	public void addName(String id, String name) throws PNMLParserException {
		if(places.containsKey(id)) {
			places.get(id).setName(name);
		} else if(transitions.containsKey(id)) {
			transitions.get(id).setName(name);
		} else {
			throw new PNMLParserException("Name einfügen ohne gültige Referenz zu ID");
		}
	}
	
	/**
	 * Methode speichert übergebene Positionen, die zu einer ID gehören.
	 * @param id ID des Knotens.
	 * @param x Position auf der X-Achse des Knotens.
	 * @param y Position auf der Y-Achse des Knotens.
	 * @throws PNMLParserException Fehler bei dem Parsingaktion.
	 */
	public void addPosition(String id, int x, int y) throws PNMLParserException {
		if(places.containsKey(id)) {
			places.get(id).setPosition(x, y);
		} else if(transitions.containsKey(id)) {
			transitions.get(id).setPosition(x, y);
		} else {
			throw new PNMLParserException("Position einfügen ohne gültige Referenz zu ID");
		}
	}
	
	/**
	 * Methode speichert übergebene Markierung, die zu einer ID gehört.
	 * @param id ID des Knotens.
	 * @param marking Markierung des Knotens.
	 * @throws PNMLParserException Fehler bei dem Parsingaktion.
	 */
	public void addMarking(String id, int marking) throws PNMLParserException {
		if (places.containsKey(id)) {
			places.get(id).initialMarking(marking);
		} else {
			throw new PNMLParserException("Marken einfügen ohne gültige Referenz zu ID");
		}
	}
	
	/**
	 * Getter-Methode, die eine Stelle mittels ID sucht.
	 * @param id ID der Stelle.
	 * @return Referenz der Stelle.
	 */
	public Place getPlace(String id) {
		if(places == null) {
			return null;
		} else {
			if(places.containsKey(id)) {
				return places.get(id);
			} else {
				return null;
			}
		}
	}
	
	/**
	 * Getter-Methode, die eine Transition mittels ID sucht.
	 * @param id ID der Transition.
	 * @return Referenz der Transition.
	 */
	public Transition getTransition(String id) {
		if(transitions == null) {
			return null;
		} else {
			if(transitions.containsKey(id)) {
				return transitions.get(id);
			} else {
				return null;
			}
		}
	}
	
	/**
	 * Getter-Methode, die eine Liste der Stellen zurück gibt.
	 * @return Liste aller Stellen im Petrinetz.
	 */
	public HashMap<String, Place> getPlaces(){
		return this.places;
	}
	
	/**
	 * Getter-Methode, die eine Liste der Transitionen zurück gibt.
	 * @return Liste aller Transitionen im Petrinetz.
	 */
	public HashMap<String, Transition> getTransitions(){
		return this.transitions;
	}
	
	/**
	 * Getter-Methode, die das GraphStream-Objekt zur visuellen Darstellung zurück gibt.
	 * @param <T> Datentyp des GraphStream-Objektes zur visuellen Darstellung.
	 * @param id ID des Objekts.
	 * @return Objekt des GraphStream-Frameworks.
	 * @throws PNMLParserException Fehler bei der Suche einer Referenz.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Element> T getElement(String id) throws PNMLParserException {
		
		// Suche in Transitionenliste
		if(transitions != null) {
			if(transitions.containsKey(id)) {
				return (T) transitions.get(id);
			}
		}
		
		// Suche in Stellenliste
		if(places != null) {
			if(places.containsKey(id)) {
				return (T) places.get(id);
			}
		}
		throw new Exceptions.PNMLParserException("Keine Referenz zu einer ID gefunden");
	}
	
	/**
	 * Methode erzeugt die Darstellung der bisherigen Datenstruktur.
	 * @param pnml Petrinetz, das visuell dargestellt werden soll.
	 * @return Petrinetzgraph, der diese Datenstruktur repräsentiert.
	 */
	public PetriNetGraph createPetriNetGraph(PNML pnml) {
		PetriNetGraph graph = new PetriNetGraph("Graph", pnml);
		graph.createPetriNetGraphStructed();
		return graph;
	}
	
	/**
	 * Funktion erstellt Anfang des Erreichbarkeitsgraphen eines Petrinetzes.
	 * @return Anfangs-DecisionGraph aus aktuellem Petrinetz.
	 */
	public mypackage.model.DecisionTree createEGDataStructur() {
		DecisionTree eg = new DecisionTree(this);
		return eg;
	}
	
	/**
	 * Methode prüft Petrinetz und markiert Transitionen nach Schaltbedingungen.
	 */
	public void checkFirstTransitions() {
		// Iteration durch alle Transitionen
		for(java.util.Map.Entry<String, Transition> entry : transitions.entrySet()) {
			if(entry.getValue().checkActivation()) {
				// Transition schaltbar
				entry.getValue().getElement().addAttribute("ui.class", "activationTrue");
			} else {
				// Transition nicht schaltbar
				entry.getValue().getElement().addAttribute("ui.class", "activationFalse");
			}
		}
		java.util.Map.Entry<String, Transition> entry = transitions.entrySet().iterator().next();
		this.currentTransiton = entry.getValue();
	}
	
	/**
	 * Getter-Methode, die eine schaltbare Transiton zurück gibt, falls eine existiert.
	 * @return Schaltbare Transition.
	 */
	public mypackage.model.Transition getSwitchableTransition() {
		for(java.util.Map.Entry<String, Transition> entry : transitions.entrySet()) {
			if(entry.getValue().checkActivation()) {
				// Aktivierbare Transistion
				return entry.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Getter-Methode, die eine Liste aller schaltbaren Transitionen zurück gibt.
	 * @return Liste aller Transitionen.
	 * @throws TransitionContainerException Fehler bei der Erzeugung eines Container-Objektes.
	 */
	public java.util.ArrayList<mypackage.model.TransitionContainer> getSwitchableTransitions() throws TransitionContainerException {
		java.util.ArrayList<mypackage.model.TransitionContainer> transitionArray = new java.util.ArrayList<TransitionContainer>();
		// Iteration über alle Transitionen
		for(java.util.Map.Entry<String, Transition> entry : transitions.entrySet()) {
			if(entry.getValue().checkActivation()) {
				if(transitionArray == null) {
					transitionArray = new java.util.ArrayList<TransitionContainer>();
				}
				// Aktivierbare Transistion
				TransitionContainer container = new TransitionContainer(entry.getValue());
				transitionArray.add(container);
			}
		}
		return transitionArray;
	}
}
