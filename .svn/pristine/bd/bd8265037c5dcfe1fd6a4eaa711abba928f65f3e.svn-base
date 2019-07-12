package Exceptions;

/**
 * Containerklasse für Fehler die bei Schalten der Transitionen auftretten können.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class TransitionContainerException extends Exception{
	
	private String fualtText = "";
	private int fualtNumber = 0;
	
	/**
	 * Konstruktor, der das Objekt mittels Fehlernummer einen Fehlertext zuweist.
	 * @param faultNumber Fehlernummer die Fehlertext auswählt.
	 */
	public TransitionContainerException(int faultNumber) {
		this.fualtNumber = faultNumber;
		switch(faultNumber) {
		case 1:
			// TransitionContainer -> Inhalt nicht kohärent
			this.fualtText = "TransitionContainer -> Inhalt nicht kohärent";
			break;
		case 2:
			// TransitionContainer -> Inhalt nicht schaltbar
			this.fualtText = "TransitionContainer -> Inhalt nicht schaltbar";
			break;
		}
	}
	
	/**
	 * Überschreiben der toString-Methode, die den Fehlertext und den Stacktrace als Rückgabe aufweist.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n" + this.fualtText + "\n" + getStackTraceAsString();
	}
	
	/**
	 * Methode erzeugt einen String mit dem Inhalt eines Stacktraces füllt.
	 * @return String mit Stacktrace der Exception.
	 */
	private String getStackTraceAsString() {
		String stackTraceString = "";
		for (StackTraceElement stackTraceElement : super.getStackTrace()) {
			stackTraceString += "Klasse: " + stackTraceElement.getClassName() + ", Methode: " + stackTraceElement.getMethodName() + ", Zeile: " + stackTraceElement.getLineNumber() + "\n";
		}
		return stackTraceString;
	}
}
