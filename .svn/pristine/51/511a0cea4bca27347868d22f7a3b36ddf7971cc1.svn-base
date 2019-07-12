package mypackage.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.graphstream.graph.Node;
/**
 * Klasse repräsentiert eine Stelle im Petrinetz-Modell. In diese wird die ganze
 * Funktionalität implementiert, die in einem Petrinetz gefordet sind.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class Place extends Element<Node>{
	
	// Marken der Stelle
	private int marking = 0;
	
	private int startMarking = 0;
	
	// Kanten zu diesem Objekt
	private HashMap<String ,Transition> arcsImport;
	// Kanten von diesem Objekt zu einem anderen
	private HashMap<String, Transition> arcsExport;
	
	/**
	 * Konstruktor mit einem Übergabeparameter
	 * @param id ID der Stelle wird an die Superklasse Element weitergeben
	 */
	public Place(String id) {
		super(id);
	}
	
	/**
	 * Funktion setzt Markenanzahl auf Startwert
	 */
	public void setStartMarking() {
		this.marking = this.startMarking;
		this.getElement().addAttribute("ui.label", "[" + this.getID() + "] " + this.getName() + " <" + this.marking + ">");
		setMarkingLogo();
		System.out.println("Setzte " + this.getID() + " auf Startmarkierung");
	}
	
	/**
	 * Funktion wird zur Initialisierung verwendet
	 * @param marking Anfangswert als Intialwert der Stelle
	 */
	public void initialMarking(int marking) {
		this.marking = marking;
		this.startMarking = marking;
	}
	
	/**
	 * Getter für das private Attribut für die Anzahl der Stellen
	 * @return Anzahl der Marken
	 */
	public int getMarking() {
		return this.marking;
	}
	
	/**
	 * Funktion inkremmentiert Anzahl der Marken, falls eine Stelle geschalten wurde.
	 * Außerdem wird hier die Beschriftung und das Design der Stelle angepasst.
	 * @param initial True = Initialisierung setzten
	 */
	public void inkMarking(boolean initial) {
		this.marking++;
		// Aktualisieren Anzeige der Marken
		this.getElement().addAttribute("ui.label", "[" + this.getID() + "] " + this.getName() + " <" + this.marking + ">");
		setMarkingLogo();
		System.out.println(marking + this.getID());
		if(initial) {
			this.startMarking = this.marking;
		}
	}
	
	/**
	 * Funktion dekremmentiert Anzahl der Marken, falls eine Stelle geschalten wurde.
	 * Außerdem wird hier die Beschriftung und das Design der Stelle angepasst.
	 * @param initial True = Initialisierung setzten
	 */
	public void decMarking(boolean initial) {
		if(this.marking > 0) {
			this.marking--;
			// Aktualisieren Anzeige der Marken
			this.getElement().addAttribute("ui.label", "[" + this.getID() + "] " + this.getName() + " <" + this.marking + ">");
			setMarkingLogo();
			if(initial) {
				this.startMarking = this.marking;
			}
		}
	}
	
	/**
	 * Methode, dekrementiert eine Stelle ohne visuelle Anpassung.
	 */
	public void decMarkingOnlyValue() {
		if(this.marking > 0) {
			this.marking--;
		}
	}
	
	/**
	 * Methode, inkrementiert eine Stelle ohne visuelle Anpassung.
	 */
	public void inkMarkingOnlyValue() {
		this.marking++;
	}
	
	/**
	 * Setter-Methode, die einen übergebenen Wert als Markierung stetzt.
	 * @param value Anzahl der Markierungen.
	 */
	public void setMarkingValue(int value) {
		this.marking = value;
		this.getElement().addAttribute("ui.label", "[" + this.getID() + "] " + this.getName() + " <" + this.marking + ">");
		setMarkingLogo();
	}
	
	/**
	 * Setter-Methode, die einen übergebenen Wert als Markierung setzt ohne visuelle Anpassung.
	 * @param value Anzahl der Markierungen.
	 */
	public void setMarkingWithoutGUI(int value) {
		this.marking = value;
	}
	
	/**
	 * Funktion checkt, ob die Anzahl der Marken größer als 0 ist.
	 * @return true, falls Anzahl der Marken größer als 0.
	 */
	public boolean checkActivation() {
		return marking > 0 ? true : false;
	}
	
	/**
	 * Funktion speichert Transition, dessen Kante auf diese Stelle gerichtet ist.
	 * @param id ID der Transition.
	 * @param transition Referenz der Transition.
	 */
	public void addImportTransistion(String id, Transition transition) {
		arcsImport.put(id, transition);
	}
	
	/**
	 * Funktion speichert Transition, auf die mit einer Kante von dieser Stelle gezielt wird.
	 * @param id ID der Transition.
	 * @param transistion Referenz der Transition.
	 */
	public void addExportTransistion(String id, Transition transistion) {
		arcsExport.put(id, transistion);
	}
	
	/**
	 * Getter einer HashMap, der Transitionen, dessen Kante auf diese Stelle gerichtet ist.
	 * @return HashMap mit Transitionen.
	 */
	public HashMap<?, ?> getImportTransitionList(){
		return this.arcsImport;
	}
	
	/**
	 * Getter einer HashMap, der Transitionen, auf die mit einer Kante von dieser Stelle gezielt wird.
	 * @return HashMap mit Transitionen.
	 */
	public HashMap<?, ?> getExportTransitionList(){
		return this.arcsExport;
	}
	
	/**
	 * Getter der ID dieser Stelle.
	 * @return ID dieser Stelle.
	 */
	public String getID() {
		return super.getID();
	}
	
	/**
	 * Funktion setzt Attribute in der Graphstream Bibliothek
	 */
	@Override
	public void setAttributes() {
		this.getElement().addAttribute("ui.label", "[" + this.getID() + "] " + this.getName() + " <" + this.marking + ">");
		this.getElement().addAttribute("xy", this.getPosY()*(-1), this.getPosX());
		this.getElement().addAttribute("ui.style", "text-mode: truncated;");
		setMarkingLogo();
	}
	
	/**
	 * Passt die Anzahl der Marken zum Stellenbild an.
	 */
	private void setMarkingLogo() {
		switch(this.marking){
		case 0:
			this.getElement().addAttribute("ui.class", "zero");
			break;
		case 1:
			this.getElement().addAttribute("ui.class", "one");
			break;
		case 2:
			this.getElement().addAttribute("ui.class", "two");
			break;
		case 3:
			this.getElement().addAttribute("ui.class", "three");
			break;
		case 4:
			this.getElement().addAttribute("ui.class", "four");
			break;
		case 5:
			this.getElement().addAttribute("ui.class", "five");
			break;
		case 6:
			this.getElement().addAttribute("ui.class", "six");
			break;
		case 7:
			this.getElement().addAttribute("ui.class", "seven");
			break;
		case 8:
			this.getElement().addAttribute("ui.class", "eigth");
			break;
		case 9:
			this.getElement().addAttribute("ui.class", "nine");
			break;
		default:
			if(marking > 9) {
				this.getElement().addAttribute("ui.class", "nineMore");
			}
			break;
		}
	}
}
