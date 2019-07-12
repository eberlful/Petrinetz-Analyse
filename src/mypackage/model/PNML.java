package mypackage.model;
/**
 * Containerklasse eines Petrinetzes.
 * @author marku
 * @version 1.0.0
 */
public class PNML {
	
	private PNMLNet net;
	
	/**
	 * Konstruktor, der parameterlos diese Objekt erzeugt.
	 */
	public PNML() {
		net = new PNMLNet();
	}
	
	/**
	 * Getter-Methode, die ein Petrinetz zurück gibt.
	 * @return Petrinetz
	 */
	public PNMLNet getNet() {
		return this.net;
	}

}
