package mypackage.model;

import java.util.HashMap;
import java.util.Map.Entry;

import org.graphstream.graph.Node;
/**
 * Klasse repräsentiert eine Transition im Petrinetz-Modell. In diese wird die ganze
 * Funktionalität implementiert, die in einem Petrinetz gefordet sind.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class Transition extends Element<Node>{
	
	/**
	 * Konstruktor, die übergeben ID setzt.
	 * @param id ID der Transition.
	 */
	public Transition(String id) {
		super(id);
	}
	
	/**
	 * Methode setzt Attribute im GraphStream-Objekt
	 */
	@Override
	public void setAttributes() {
		this.getElement().addAttribute("ui.label", "[" + this.getID() + "] " + this.getName());
		this.getElement().addAttribute("xy", this.getPosY()*(-1), this.getPosX());
		this.getElement().addAttribute("ui.class", "transition");
	}
	
	/**
	 * Checkt die Schaltregel. Wenn alle Stellen im Vorbereich mindestens eine Makierung besitzen,
	 * dann kann diese Transition geschalten werden.
	 * @return false = kann nicht geschalten werden, true = kann geschalten werden.
	 */
	public boolean checkActivation() {
		HashMap<String, DirectedEdge> hashMap = this.getArcsIn();
		// Checke Referenz auf gültige Adresse
		if(hashMap == null) {
			System.out.println("checkActivation, model.Transition, Zeile: 30 -> HashMap hat keine gültige Referenz");
			return false;
		} else {
			
			for(Entry<String, DirectedEdge> entry : hashMap.entrySet()) {
				if(!((Place)(entry.getValue().getSource())).checkActivation()) {
					return false;
				}
			}
		}
		return true;
	}
}
