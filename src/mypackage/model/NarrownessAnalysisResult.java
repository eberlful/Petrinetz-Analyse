package mypackage.model;

/**
 * Containerklasse die nach der Beschränktheitsanalyse desen Ergebnis und dazugehörigen Informationen speichert.
 * Außerdem bereitet diese Klasse auch die Ausgabe vor.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class NarrownessAnalysisResult {
	
	private PNML pnml;
	
	private DecisionTree decisionTree;
	
	private NarrownessAnalysis narrownessAnalysis;
	
	private String name;
	
	private String path;
	
	private java.io.File file;
	
	/**
	 * Konstruktor, dem alle wichtigen Informationen übergeben werden.
	 * @param pnml Petrinetz, das zur Beschränktheitsanalyse benutzt wurde.
	 * @param decisionTree Entscheidungsbaum, der zur Beschränktheitsanalyse benutzt wurde.
	 * @param narrownessAnalysis Beschränktheitsanalyse-Objekt, das die Ergebnisse enthält.
	 */
	public NarrownessAnalysisResult(PNML pnml, DecisionTree decisionTree, NarrownessAnalysis narrownessAnalysis) {
		this.pnml = pnml;
		this.decisionTree = decisionTree;
		this.narrownessAnalysis = narrownessAnalysis;
	}
	
	/**
	 * Setter-Methode, die eine Datei übergeben bekommt und die dazugehörigen Informationen setzt.
	 * @param file Datei, dessen Informationen gespeichert werden soll.
	 */
	public void setFileInformation(java.io.File file) {
		name = file.getName();
		path = file.getAbsolutePath();
		this.file = file;
	}
	
	/**
	 * Getter-Methode, die den Namen der Datei, in der das Petrinetz gespeichert ist, zurück gibt.
	 * @return Name der PNML-Datei.
	 */
	public String getFileName() {
		return this.name;
	}
	
	/**
	 * Getter-Methode, die das Ergebnis der Analyse zurück gibt.
	 * @return Ergebnis der Beschränktheitsanalyse.
	 */
	public String getLimitedResult() {
		if(narrownessAnalysis.isLimited()) {
			return "ja";
		} else {
			return "nein";
		}
	}
	
	/**
	 * Getter-Methode, die Informationen zur Analyse zurück gibt.
	 * @return Informationen zur Beschränktheitsanalyse.
	 */
	public String getResult() {
		if(narrownessAnalysis.isLimited()) {
			return narrownessAnalysis.getNodesNumber() + "/" + narrownessAnalysis.getEdgesNumber();
		} else {
			return narrownessAnalysis.getMStartNode().getLabel() + ", " + narrownessAnalysis.getMEndNode().getLabel() + "; " + narrownessAnalysis.getPathInformation();
		}
	}
	
	/**
	 * Überschriebene Methode, die für die String-Ausgabe dieses Objektes benutzt wird.
	 * @return Tabelleninformationen über die Analyse.
	 */
	@Override
	public String toString() {
		String result = "<tr><td>";
		result += name + "</td><td>";
		if(narrownessAnalysis.isLimited()) {
			// beschränkt
			result += "ja</td><td>";
			result += narrownessAnalysis.getNodesNumber() + "/" + narrownessAnalysis.getEdgesNumber() + "</td></tr>";
		} else {
			// unbeschränkt
			result += "nein</td><td>";
			result += narrownessAnalysis.getMStartNode().getLabel() + ", " + narrownessAnalysis.getMEndNode().getLabel() + "; " + narrownessAnalysis.getPathInformation();
		}
		return result;
	}

}
