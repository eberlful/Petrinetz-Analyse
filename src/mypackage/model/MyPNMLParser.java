package mypackage.model;

import java.io.File;

import propra.pnml.PNMLWopedParser;
/**
 * Veränderte Klasse, der Klasse PNMLWopedParser. Klasse implemnetiert die Funktionaliät um eine Datei in ein Petrinetz umzuwandeln.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class MyPNMLParser extends PNMLWopedParser{
	
	private PNML pnmlModel = null;
	
	/**
	 * Konstruktor, der eine Datei braucht um ihre Mutterklasse zu konstruieren.
	 * @param pnml Datei mit der Endung pnml, die eine Beschreibung von einem Petrinetz enthält.
	 */
	public MyPNMLParser(File pnml) {
		super(pnml);
		pnmlModel = new PNML();
		System.out.println("Parserobjekt erzeugt");
	}
	
	/**
	 * Überschriebene Methode, die Aufgerufen wird, wenn mittels der Datei eine Kante erkannt wurde.
	 * Im Anschluss wird eine Kanate erzeugt und der Datenstruktur angehängt.
	 */
	@Override
	public void newArc(String id, String source, String target) {
		try {
			DirectedEdge arc = new DirectedEdge(id, pnmlModel.getNet().getElement(source), pnmlModel.getNet().getElement(target));
		} catch (Exceptions.PNMLParserException ex) {
			System.out.println(ex);
		}
		super.newArc(id, source, target);
	}
	
	/**
	 * Überschriebene Methode, die Aufgerufen wird, wenn mittels der Datei eine Stelle erkannt wurde.
	 * Im Anschluss wird eine Stelle erzeugt und der Datenstruktur angehängt.
	 */
	@Override
	public void newPlace(String id) {
		pnmlModel.getNet().addPlaces(new Place(id));
		super.newPlace(id);
	}
	
	/**
	 * Überschriebene Methode, die Aufgerufen wird, wenn mittels der Datei eine Transition erkannt wurde.
	 * Im Anschluss wird eine Transition erzeugt und der Datenstruktur angehängt.
	 */
	@Override
	public void newTransition(String id) {
		pnmlModel.getNet().addTransition(new Transition(id));
		super.newTransition(id);
	}
	
	/**
	 * Überschriebene Methode, die Aufgerufen wird, wenn mittels der Datei Attribute erkannt wurden.
	 * Im Anschluss wird ein Namen zu einer ID verknüpft.
	 */
	@Override
	public void setName(String id, String name) {
		try {
			pnmlModel.getNet().addName(id, name);
		} catch(Exceptions.PNMLParserException ex) {
			System.out.println(ex);
		}
		super.setName(id, name);
	}
	
	/**
	 * Überschriebene Methode, die Aufgerufen wird, wenn mittels der Datei Attribute erkannt wurden.
	 * Im Anschluss wird eine Position zu einer ID übergeben und in dem dazugehörigem Objekt gespeichert.
	 */
	@Override
	public void setPosition(String id, String x, String y) {
		try {
			pnmlModel.getNet().addPosition(id, Integer.parseInt(x), Integer.parseInt(y));
		} catch(Exceptions.PNMLParserException ex) {
			System.out.println(ex);
		}
		super.setPosition(id, x, y);
	}
	
	/**
	 * Überschriebene Methode, die Aufgerufen wird, wenn mittels der Datei Attribute erkannt wurden.
	 * Im Anschluss wird die Markierung zu einer ID übergeben und in der dazugehörigen Stelle gespeichert.
	 */
	@Override
	public void setTokens(String id, String tokens) {
		try {
			pnmlModel.getNet().addMarking(id, Integer.parseInt(tokens));
		} catch (Exceptions.PNMLParserException ex) {
			System.out.println(ex);
		}
		super.setTokens(id, tokens);
	}
	
	/**
	 * Getter-Methode, die das fertige Petrinetz zurück gibt.
	 * @return Fertiges Petrinetz.
	 */
	public PNML getPNML() {
		return this.pnmlModel;
	}

}
