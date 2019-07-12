package mypackage.model;

import java.util.HashMap;
import java.util.Map.Entry;
/**
 * Mutterklasse für Datenstruktur des Petrinetzes. Hiermit werden die 
 * Funktionalitäten repräsentiert, die sowohl bei Stellen als auch bei Transitionen gebraucht werden.
 * @author Markus Eberl
 * @version 1.0.0
 * @param <T> GraphStream Typ
 */
public class Element<T extends org.graphstream.graph.Element> {
	
	private String id;
	
	private HashMap<String, Object> attributes;
	
	private HashMap<String, DirectedEdge> arcsIn;
	private HashMap<String, DirectedEdge> arcsOut;
	
	private T element = null;
	
	private int posX = 0;
	private int posY = 0;
	
	private String name;
	
	/**
	 * Konstruktor, der die Attribute initialisiert.
	 */
	public Element() {
		attributes = new HashMap();
		arcsIn = new HashMap();
		arcsOut = new HashMap();
	}
	
	/**
	 * Konstruktor, der ID setzt und Attribute initialisiert.
	 * @param id ID des Elementes.
	 */
	public Element(String id) {
		attributes = new HashMap();
		arcsIn = new HashMap();
		arcsOut = new HashMap();
		this.id = id;
	}
	
	/**
	 * Methode fügt Attribute einer Liste hinzu.
	 * @param attribut Attribut im Typen eines Strings.
	 * @param object Attributobjekt passend zum Schlüssel.
	 */
	public void addAttribut(String attribut, Object object) {
		if(element != null) {
			attributes.put(attribut, object);
		}
	}
	
	/**
	 * Methode setzt die gespeicherten Attribute im GraphStream-Objekt
	 */
	public void setAttributes() {
		for (Entry<String, Object> entry : attributes.entrySet()) {
		    element.addAttribute(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Methode speichert GraphStream-Objekt.
	 * @param element GraphStream-Objekt, das diese Objekt repräsentiert.
	 */
	public void addElement(T element) {
		this.element = element;
	}
	
	/**
	 * Methode speichert übergebene Kante in der Liste der eingehenden Kanten.
	 * @param id ID der eingehenden Kante.
	 * @param arc Objekt der eingehenden Kante.
	 */
	public void addArcIn(String id, DirectedEdge arc) {
		arcsIn.put(id, arc);
	}
	
	/**
	 * Methode speichert übergebene Kante in der Liste der ausgehenden Kanten.
	 * @param id ID der ausgehenden Kante.
	 * @param arc Objekt der ausgehenden Kante.
	 */
	public void addArcOut(String id, DirectedEdge arc) {
		arcsOut.put(id, arc);
	}
	
	/**
	 * Getter-Methode, die die Liste der ausgehenden Kanten zurück gibt.
	 * @return Liste der ausgehenden Kanten.
	 */
	public HashMap<String, DirectedEdge> getArcsOut(){
		return this.arcsOut;
	}
	
	/**
	 * Getter-Methode, die die Liste der eingehenden Kanten zurück gibt.
	 * @return Liste der eingehenden Kanten.
	 */
	public HashMap<String, DirectedEdge> getArcsIn(){
		return this.arcsIn;
	}
	
	/**
	 * Methode setzt übergebene Position zu den Attributen.
	 * @param x Positionswert der X-Achse.
	 * @param y Positionswert der Y-Achse.
	 */
	public void setPosition(int x, int y) {
		this.posX = x;
		this.posY = y;
	}
	
	/**
	 * Getter-Methode für die X-Achsen-Position.
	 * @return Positionswert der X-Achse.
	 */
	public int getPosX() {
		return this.posX;
	}
	
	/**
	 * Getter-Methode für die Y-Achsen-Position.
	 * @return Positionswert der Y-Achse.
	 */
	public int getPosY() {
		return this.posY;
	}
	
	/**
	 * Setter-Methode des Namens für dieses Objekt.
	 * @param name Name dieses Objektes.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter-Methode des Namens des Objektes.
	 * @return Name dieses Objektes.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter-Methode der ID des Objekts.
	 * @return ID des Objekts
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * Getter-Methode, die das GraphStream-Objekt zurück gibt.
	 * @return GraphStream-Objekt
	 */
	public T getElement() {
		return element;
	}

}
