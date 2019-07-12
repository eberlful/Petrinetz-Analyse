package Exceptions;

/**
 * Exceptionklasse, für Exceptions die beim Erzeugen des Petrinetzes instanziert werden.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class PNMLParserException extends Exception{
	
	/**
	 * Konstruktor ohne Übergabeparameter.
	 */
	public PNMLParserException() {
		super();
	}
	
	/**
	 * Konstruktor mit String als Übergabeparameter.
	 * @param fault String, der zu der Superklasse weitergegeben.
	 */
	public PNMLParserException(String fault) {
		super(fault);
	}
	
}
