package Exceptions;

/**
 * Containerklasse für Fehler die bei der Analyse auf Beschränktheit auftretten können.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class NarrownesAnalysisException extends Exception{
	
	private int faultNumber = 0;
	private String faultText = null;
	
	/**
	 * Konstruktor, der den richtigen Fehlertext speichert.
	 * @param faultNumber Integer, der verantwortlich für die Fehlertextauswahl ist.
	 */
	public NarrownesAnalysisException(int faultNumber) {
		this.faultNumber = faultNumber;
		switch(faultNumber) {
		// Fehler bei Vergleich zweier Nodes
		case 1:
			this.faultText = "Beim Vergleich der Anzahl der Stellen zweier DecisionNodes wurde ein Fehler gefunden";
			break;
		case 2:
			this.faultText = "Fehler bei der Markenwiederherstellung!" + "\n" + "Die Anzahl der Places stimmt nicht mit dem Place-Cache überein";
		}
	}
	
	/**
	 * Überschreiben der toString-Methode, die den Fehlertext und den Stacktrace als Rückgabe aufweist.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n" + this.faultText + "\n" + getStackTraceAsString();
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
