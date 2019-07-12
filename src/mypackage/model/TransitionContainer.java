package mypackage.model;

import Exceptions.TransitionContainerException;
/**
 * Containerklasse die bei der rekursiven Beschränktheitsanalyse Transitionen speichert und deren Zustand.
 * Im Anschluss werden die Informationen benutzt um wieder zu eine alten Stand zurück zu kommen. 
 * @author Markus Eberl
 * @version 1.0.0
 */
public class TransitionContainer {
	
	private Transition transition = null;
	private boolean isSwitchable = false;
	private boolean switched = false;
	
	/**
	 * Konstruktor, der eine Transition übergeben bekommt und diese auf Schaltbarkeit prüft.
	 * @param transition Transition die diese Klasse speichert.
	 * @throws TransitionContainerException Fehler, die beim Schalten einer Transition auftretten.
	 */
	public TransitionContainer(Transition transition) throws TransitionContainerException {
		this.transition = transition;
		this.isSwitchable = this.transition.checkActivation();
		if(!this.isSwitchable) {
			throw new Exceptions.TransitionContainerException(1);
		}
	}
	
	/**
	 * Setter-Methode, die der Transition den Zustand geschaltet setzt.
	 */
	public void setTransitionSwitched() {
		this.switched = true;
		this.isSwitchable = false;
	}
	
	/**
	 * Getter-Methode, die abfrägt, ob Transition noch schaltbar ist.
	 * @return Zustand der Transition.
	 */
	public boolean isAlreadySwitchable() {
		if(isSwitchable) {
			return true;
		} else {
			// Wird als Fehler gewertet
			return false;
		}
	}
	
	/**
	 * Schaltet die Transition.
	 */
	public void switchTransition() {
		// Ändere alle Stellen vor der Transition
		for(java.util.Map.Entry<String, DirectedEdge> entry : transition.getArcsIn().entrySet()) {
			((Place)(entry.getValue().getSource())).decMarkingOnlyValue();
		}
		// Ändere alle Stellen nach der Transition
		for(java.util.Map.Entry<String, DirectedEdge> entry : transition.getArcsOut().entrySet()) {
			((Place)(entry.getValue().getDestination())).inkMarkingOnlyValue();
		}
		this.isSwitchable = false;
		this.switched = true;
	}
	
	/**
	 * Getter-Methode, die die Transition zurück gibt.
	 * @return Transition als Referenz
	 */
	public Transition getTransition() {
		return this.transition;
	}
	
}
