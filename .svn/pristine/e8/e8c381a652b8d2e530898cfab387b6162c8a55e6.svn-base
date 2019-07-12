package mypackage.controller;

import java.io.File;
import java.text.ParseException;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.graphstream.graph.Graph;

import com.sun.webkit.dom.KeyboardEventImpl;

import Exceptions.CatchPrinter;
import mypackage.model.DecisionEdge;
import mypackage.model.DecisionGraph;
import mypackage.model.DecisionNode;
import mypackage.model.DecisionTree;
import mypackage.model.MyPNMLParser;
import mypackage.model.NarrownessAnalysis;
import mypackage.model.NarrownessAnalysisResult;
import mypackage.model.PNML;
import mypackage.model.PetriNetGraph;
import mypackage.view.NarrownessAnalysisDialog;
import mypackage.view.PetriNetFrame;

/**
 * Controllerklasse für MVC-Modell. Hier werden alle Funktionalitäten der Anwendungen gesteuert.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class PetriNetController {
	
	// Hauptfenster der Anwendung
	private PetriNetFrame frame;
	
	//-----------------------------------------------------------------
	
	// Graphen für das Fenster
	private PetriNetGraph petriNetGraph;
	private DecisionGraph decisionGraph;
	
	//-----------------------------------------------------------------
	
	// Globaler Pfad für FileChooser-Auswahl
	private String filePath = null;
	private File file = null;
	
	//-----------------------------------------------------------------
	
	// Datenstrukturen für die Automaten
	private PNML petriNetDataStructur = null;
	private DecisionTree decisionDataStructur = null;
	
	//-----------------------------------------------------------------
	
	private String lastSelectedElement = null;
	
	//-----------------------------------------------------------------
	
	// Variablen fuer bestimmte Tasten
	private boolean strgPressed = false;	// KeyCode = 17
	
	//-----------------------------------------------------------------
	
	private DecisionNode secondLastNode = null;
	private DecisionEdge secondLastEdge = null;
	
	/**
	 * Konstruktor
	 * @param petriNetFrame PetriNetFrame-Objekt fungiert als View-Modell
	 */
	public PetriNetController(PetriNetFrame petriNetFrame) {
		this.frame = petriNetFrame;
	}
	
	/**
	 * Funktion für normales Öffnen einer Datei mit anschließender Umwandlung in ein
	 * Petrinetz mit anfänglichem partiellem Entscheidungsgraph.
	 */
	public void openMenu() {
		
		// FileChooser-Objekt erstellen
		JFileChooser chooser = new JFileChooser();
		
		// Filter für PNML-Dateien erstellen
		FileFilter filter = new FileNameExtensionFilter("PNML", "pnml");
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
		
		// Setze Startpfad
		chooser.setCurrentDirectory(new File("../ProPra-SS19-Basis/Beispiele"));
		
		int returnValue = 0;
		if(filePath == null) {
			returnValue = chooser.showOpenDialog(frame);
		} else {
			// Bei erneutem Öffen wird alter Pfad vorgewählt
			chooser.setSelectedFile(new File(filePath));
			returnValue = chooser.showOpenDialog(frame);
		}
		
		// Vergleich des Rückgabewertes des Auswahldialogs für Dateien
		if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            filePath = chooser.getSelectedFile().getAbsolutePath();
            System.out.println(filePath + " wird geladen...");
            file = chooser.getSelectedFile();
            loadNetFromSingleData(filePath);
            frame.setPetriNetIsLoad(true);
        }
	}
	
	/**
	 * Datei wird von einem Parser-Objekt durchlaufen und dieses erzeugt
	 * sukzessive das Petrinetz. Im Anschluss wird der dazugehörige Graph initialisiert.
	 * @param path Pfad der Datei, in der die Informationen zur Erstellung der Datenstruktur steht
	 */
	private void loadNetFromSingleData(String path) {
		try {
			File file = new File(path);
			frame.addTextOutput("Lade Graph aus Datei: " + path);
			
			// Parser-Objekt erzeugen und Vorgang starten
			MyPNMLParser parser = new MyPNMLParser(file);
			parser.initParser();
			parser.parse();
			
			// Petrinetz-Datenstruktur erzeugen
			petriNetDataStructur = parser.getPNML();
			
			// Petrinet-Graph initialisieren
			petriNetGraph = petriNetDataStructur.getNet().createPetriNetGraph(parser.getPNML());
			frame.initPetriNetViewer(petriNetGraph);
			
			// Entscheidungsbaum erzeugen
			decisionDataStructur = petriNetDataStructur.getNet().createEGDataStructur();
			
			// Erreichbarkeitsgraph initialisieren
			decisionGraph = decisionDataStructur.createDecisionGraph();
			decisionGraph.setFrame(frame);
			frame.initDecisionGraphViewer(decisionGraph);
			
			// Checke erste Transition unter der Anfangsmarkierung
			petriNetDataStructur.getNet().checkFirstTransitions();
		} catch (Exception ex) {
			System.out.println(ex);
			Exceptions.CatchPrinter.printException(ex);
		}
	}
	
	/**
	 * Methode des Buttons "Neu laden". Hier wird Petrinetz erneut geladen.
	 */
	public void loadMenu() {
		// Lade Petrinetz neu
		// Check, ob schon ein Petrinetz geladen wurde
		if(this.file != null) {
			this.frame.addTextOutput("--------------------------------------------------------------------------");
			this.frame.addTextOutput("Petrinetz zurücksetzen!!!");
			this.loadNetFromSingleData(file.getAbsolutePath());
			this.frame.addTextOutput("--------------------------------------------------------------------------");
		} else {
			this.frame.addTextOutput("--------------------------------------------------------------------------");
			this.frame.addTextOutput("Kein File-Objekt gefunden.");
			this.frame.addTextOutput("Bitte öffnen Sie zuerst ein Petrinetz");
			this.frame.addTextOutput("--------------------------------------------------------------------------");
		}
	}
	
	/**
	 * Funktion des Menüpunktes "Mehrere Dateien Auswählen". Startet Beschränktheitsanalysen für mehrere Dateien
	 * gibt die Ergebnisse in einem eigenem Fenster wieder.
	 */
	public void analyseMoreData() {
		// FileChooser-Objekt erstellen
		JFileChooser chooser = new JFileChooser();
				
		// Filter für PNML-Dateien erstellen
		FileFilter filter = new FileNameExtensionFilter("PNML", "pnml");
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(true);
				
		// Setze Startpfad
		chooser.setCurrentDirectory(new File("../ProPra-SS19-Basis/Beispiele"));
				
		int returnValue = 0;
		if(filePath == null) {
			returnValue = chooser.showOpenDialog(frame);
		} else {
			chooser.setSelectedFile(new File(filePath));
			returnValue = chooser.showOpenDialog(frame);
		}
		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			// (Ausgewählte) Markierte Dateien
		    File[] filesChoose = chooser.getSelectedFiles();
		    java.util.ArrayList<NarrownessAnalysisResult> results = new java.util.ArrayList<NarrownessAnalysisResult>();
		    
		    // Sortiere Dateien
		    java.util.TreeMap<String, File> files = new java.util.TreeMap<String, File>();
		    for(File file : filesChoose) {
		    	files.put(file.getName(), file);
		    }
		    
		    // Iteration aller ausgewählten Dateien
		    for(java.util.Map.Entry<String, File> file : files.entrySet()) {
		    	System.out.println("Datei: " + file.getValue().getAbsolutePath());
		    	frame.addTextOutput("Lade Graph aus Datei: " + file.getValue().getAbsolutePath());
		    	// Parser erzeugen und parsen
		    	MyPNMLParser parser = new MyPNMLParser(file.getValue());
		    	parser.initParser();
		    	parser.parse();
		    	
		    	// Petrinetz erzeugen
		    	PNML pnml = parser.getPNML();
		    	
		    	// Erreichbarkeitsbaum erzeugen
		    	DecisionTree decisionTree = pnml.getNet().createEGDataStructur();
		        
		    	// Beschränktheitsanalyse starten
		    	NarrownessAnalysis analysis = new NarrownessAnalysis(pnml.getNet(), decisionTree);
		        analysis.startNarrownessAnalysis();
		        
		        // Ergebnis der Analyse speichern
		        NarrownessAnalysisResult narrownessAnalysisResult = new NarrownessAnalysisResult(pnml, decisionTree, analysis);
		        narrownessAnalysisResult.setFileInformation(file.getValue());
		        results.add(narrownessAnalysisResult);
		     }
		     
		    // Auswertung darstellen
		    if(frame.getOptionalCheck()) {
		    	// Extra Fenster zu Darstellung erzeugen
		    	NarrownessAnalysisDialog dialog = new NarrownessAnalysisDialog(results, this.frame);
		    	dialog.show(true);
		    } else {
		    	// Auswertung wird in Textfeld des normalen Fensters geladen
		    	int[] fieldNumber = new int[3];
		    	fieldNumber[0] = 0;
		    	
		    	// Suche Reihenlänge
		    	for(java.util.Map.Entry<String, File> entry : files.entrySet()) {
		    		if(entry.getKey().length() > fieldNumber[0]) {
		    			fieldNumber[0] = entry.getKey().length();
		    		}
		    	}
		    	fieldNumber[1] = 15;
		    	fieldNumber[2] = 42;
		    	for(NarrownessAnalysisResult result : results) {
		    		if(fieldNumber[2] < result.getResult().length()) {
		    			fieldNumber[2] = result.getResult().length();
		    		}
		    	}
		    	
		    	// Formate festlegen
		    	String headerFormat = "+%1$-" + fieldNumber[0] + "s+%2$-" + fieldNumber[1] + "s+%3$-" + fieldNumber[2] + "s+\n";
		    	String format = "|%1$-" + fieldNumber[0] + "s|%2$-" + fieldNumber[1] + "s|%3$-" + fieldNumber[2] + "s|\n";
		    	
		    	// Linien erzeugen
		    	String[] line = new String[3];
		    	line[0] = "";
		    	for (int i = 0; i < fieldNumber[0]; i++) {
					line[0] += "-";
				}
		    	line[1] = "";
		    	for (int i = 0; i < fieldNumber[1]; i++) {
					line[1] += "-";
				}
		    	line[2] = "";
		    	for (int i = 0; i < fieldNumber[2]; i++) {
					line[2] += "-";
				}
		    	
		    	// Ausgabe
		    	System.out.format(headerFormat, line[0], line[1], line[2]);
		    	frame.addTextOutputAnalysis(String.format(headerFormat, line[0], line[1], line[2]));
		    	System.out.format(format, "Dateiname", "beschränkt", "Knoten / Kanten bzw. m, m'; Pfadlänge:Pfad");
		    	frame.addTextOutputAnalysis(String.format(format, "Dateiname", "beschränkt", "Knoten / Kanten bzw. m, m'; Pfadlänge:Pfad"));
		    	System.out.format(headerFormat, line[0], line[1], line[2]);
		    	frame.addTextOutputAnalysis(String.format(headerFormat, line[0], line[1], line[2]));
		    	for(NarrownessAnalysisResult result : results) {
		    		System.out.format(format, result.getFileName(), result.getLimitedResult(), result.getResult());
		    		frame.addTextOutputAnalysis(String.format(format, result.getFileName(), result.getLimitedResult(), result.getResult()));
		    	}
		    	System.out.format(headerFormat, line[0], line[1], line[2]);
		    	frame.addTextOutputAnalysis(String.format(headerFormat, line[0], line[1], line[2]));
		    }
		    
		}
	}
	
	/**
	 * Funktion beendet die Anwendung
	 */
	public void closeMenu() {
		// Beenden
		frame.dispose();
	}
	
	/**
	 * Wird bei einem Click auf den Loesche-EG-Button ausgeführt.
	 * 1. Der Erreichbarkeitsgraph wird geloescht
	 * 2. Markierungen des Petrinetzes werden auf die Anfangsmakierungen gersetzt
	 */
	public void loescheEGButton() {
		// Startmarkierungen setzen
		for(Entry<String, mypackage.model.Place> entry : petriNetDataStructur.getNet().getPlaces().entrySet()) {
			entry.getValue().setStartMarking();
		}
		
		// Erreichbarkeitsgraph löschen
		// Lade Erreichbarkeitsgraph
		decisionDataStructur = petriNetDataStructur.getNet().createEGDataStructur();
		decisionGraph = decisionDataStructur.createDecisionGraph();
		frame.initDecisionGraphViewer(decisionGraph);
		
		// Checke erste Transition unter der Anfangsmarkierung
		petriNetDataStructur.getNet().checkFirstTransitions();
	}
	
	/**
	 * Funktion speichert das zuletzt makierte Element,
	 * um es weiter bearbeiten zu können.
	 * @param id ID des ausgewählten Elementes
	 */
	public void setLastSelectedElement(String id) {
		if(petriNetDataStructur.getNet().getPlaces().containsKey(id)) {
			this.lastSelectedElement = id;
			petriNetDataStructur.getNet().getPlaces().get(id).getElement().addAttribute("ui.style", "stroke-color: #26619C;");
		}
	}
	
	/**
	 * Getter für die Variable lastSelectedElement
	 * @return ID des zuletzt markierten Elementes
	 */
	public String getLastSelectedElement() {
		return this.lastSelectedElement;
	}
	
	/**
	 * Funktion dekrementiert markierte Stelle und gibt Feedback
	 * @return False = keine Stelle markiert, True = erfolgreich dekrementiert
	 */
	public boolean decrementPlace() {
		if(lastSelectedElement == null) {
			return false;
		} else {
			petriNetDataStructur.getNet().getPlaces().get(lastSelectedElement).decMarking(true);
			deleteUISelectedStyle(petriNetDataStructur.getNet().getPlaces().get(lastSelectedElement).getElement());
			this.checkAllTransitions();
			
			// Erreichbarkeitsgraph löschen
			// Lade Erreichbarkeitsgraph
			decisionDataStructur = petriNetDataStructur.getNet().createEGDataStructur();
			decisionGraph = decisionDataStructur.createDecisionGraph();
			frame.initDecisionGraphViewer(decisionGraph);
			
			// Checke erste Transition unter der Anfangsmarkierung
			petriNetDataStructur.getNet().checkFirstTransitions();
			return true;
		}
	}
	
	/**
	 * Funktion inkrementiert markierte Stelle und gibt Feedback
	 * @return False = keine Stelle markiert, True = erfolgreich inkrementiert
	 */
	public boolean inkrementPlace() {
		if(lastSelectedElement == null) {
			return false;
		} else {
			petriNetDataStructur.getNet().getPlaces().get(lastSelectedElement).inkMarking(true);
			deleteUISelectedStyle(petriNetDataStructur.getNet().getPlaces().get(lastSelectedElement).getElement());
			this.checkAllTransitions();
			// Erreichbarkeitsgraph löschen
			// Lade Erreichbarkeitsgraph
			decisionDataStructur = petriNetDataStructur.getNet().createEGDataStructur();
			decisionGraph = decisionDataStructur.createDecisionGraph();
			frame.initDecisionGraphViewer(decisionGraph);
			
			// Checke erste Transition unter der Anfangsmarkierung
			petriNetDataStructur.getNet().checkFirstTransitions();
			return true;
		}
	}
	
	
	/**
	 * Löscht graphische Markierung des übergebenen Knotens
	 * @param node Knoten des Typs Node aus der Graphstream Libary
	 */
	private void deleteUISelectedStyle(org.graphstream.graph.Node node) {
		node.addAttribute("ui.style", "stroke-color: #000000;");
	}
	
	
	/**
	 * Petrinetz wird auf Anfangsmarkierung zurückgesetzt.
	 */
	public void resetPetriNet() {
		// Iteration über alle Stellen
		for(Entry<String, mypackage.model.Place> entry : this.petriNetDataStructur.getNet().getPlaces().entrySet()) {
			entry.getValue().setStartMarking();
		}
		this.decisionDataStructur.setLastNode(this.decisionDataStructur.getStartNode());
		this.checkAllTransitions();
	}
	
	/**
	 * Methode, die bei einem Klick auf ein Knoten im Petrinet-Graph aufgerufen wird.
	 * @param id ID des Kontens, auf dem geklickt wurde.
	 */
	public void clickNodeInPetriNetGraph(String id) {
		org.graphstream.graph.Node node = this.getPetriNetGraph().getNode(id);
		
		// Checke Schaltregel
		System.out.println("Checke Schaltregel: " + id);
		
		// Suche das Objekt des geklickten Knotens
		for(Entry<String, mypackage.model.Transition> entry : this.petriNetDataStructur.getNet().getTransitions().entrySet()) {
			if(id.equals(entry.getKey())) {
				System.out.println("id gleich mit key");
				// Checke Knoten auf aktivierbare Transition
				if(entry.getValue().checkActivation()) {
					System.out.println("Check true");
					// Schalte Transition
					switchTransition(entry.getValue());
					node.addAttribute("ui.class", "activationTrue");
					
					// Partieller Erreichbarkeitsgraph wird erweitert
					DecisionEdge edge = this.decisionDataStructur.addPartialDecisionNodeReturn(entry.getValue(), this.petriNetDataStructur.getNet().getPlaces());
					
					// Setze Herkunftskante
					edge.getDestinationNode().setFromFirstNode(edge);
					
					if(edge.getEdge() == null) {
						this.decisionGraph.createNode(this.decisionDataStructur.getLastNode());
						this.decisionGraph.createEdge(edge);
					}
					
					// Letzte Kante und Knoten werden hervorgehoben
					setLastNodeCreateStyle();
					
					// Checke neues Petrinetz auf schaltbare Transitionen
					checkAllTransitions();
					
					// Prüfe Erreichbarkeitsgraph auf Beschränktheit
					this.decisionDataStructur.checkPathForAccessibility();
				} else {
					// Transition nicht schaltbar
					System.out.println("Check false");
					node.addAttribute("ui.class", "activationFalse");
				}
			}
		}
	}
	
	/**
	 * Letzte erzeugte Kante und Knoten wird hervorgehoben
	 */
	private void setLastNodeCreateStyle() {
		if(this.secondLastNode == null && this.secondLastEdge == null) {
			// Erstes mal ohne alten Vorgang rückgängig zu machen
			this.secondLastEdge = this.decisionDataStructur.getLastNode().getFromFirstNode();
			this.secondLastNode = this.decisionDataStructur.getLastNode();
			
			this.decisionDataStructur.getLastNode().getNode().setAttribute("ui.class", "lastNode");
			this.decisionDataStructur.getLastNode().getFromFirstNode().getEdge().setAttribute("ui.class", "lastEdge");
		} else {
			// Mit alten Vorgang rückgängig zu machen
			secondLastEdge.getEdge().removeAttribute("lastEdge");
			secondLastEdge.getEdge().setAttribute("ui.class", "default");
			secondLastNode.getNode().removeAttribute("lastNode");
			secondLastNode.getNode().setAttribute("ui.class", "default");
			this.decisionDataStructur.getLastNode().getNode().setAttribute("ui.class", "lastNode");
			this.decisionDataStructur.getLastNode().getFromFirstNode().getEdge().setAttribute("ui.class", "lastEdge");
			this.secondLastEdge = this.decisionDataStructur.getLastNode().getFromFirstNode();
			this.secondLastNode = this.decisionDataStructur.getLastNode();
		}
	}
	
	/**
	 * Methode checkt aktuelles Petrinetz auf schaltbare Transitionen, falls schaltbar wird dieser markiert.
	 */
	public void checkAllTransitions() {
		for(Entry<String, mypackage.model.Transition> entry : this.petriNetDataStructur.getNet().getTransitions().entrySet()) {
			boolean activation = entry.getValue().checkActivation();
			if(activation) {
				System.out.println("checkAllTransitions: Z230 - PetriNetController - True");
				entry.getValue().getElement().setAttribute("ui.class", "activationTrue");
			} else {
				System.out.println("checkAllTransitions: Z230 - PetriNetController - False");
				entry.getValue().getElement().setAttribute("ui.class", "activationFalse");
			}
		}
	}
	
	/**
	 * Diese Funktion schaltet nach dem Check eine übergebene Transition
	 * @param transition Transition, die geschalten wird.
	 */
	private void switchTransition(mypackage.model.Transition transition) {
		// Iteriere über alle einkommenden Kanten und dekremmentieren jede Stelle um 1
		for(Entry<String, mypackage.model.DirectedEdge> entry : transition.getArcsIn().entrySet()) {
			System.out.println("Dekrementiere " + entry.getValue().getSource().getID());
			((mypackage.model.Place)entry.getValue().getSource()).decMarking(false);
		}
		
		// Iteriere über alle abgehenden Kanten und inkremmentiere jede Stelle um 1
		for(Entry<String, mypackage.model.DirectedEdge> entry : transition.getArcsOut().entrySet()) {
			System.out.println("Inkrementiere " + entry.getValue().getDestination().getID());
			((mypackage.model.Place)entry.getValue().getDestination()).inkMarking(false);
		}
	}
	
	/**
	 * Getter-Methode für den Petrinetz-Graph.
	 * @return Petrinetz-Graph.
	 */
	public Graph getPetriNetGraph() {
		return petriNetGraph;
	}
	
	/**
	 * Getter-Methode für den Entscheidungsgraph.
	 * @return Aktueller Entscheidungsgraph.
	 */
	public Graph getDecisionGraph() {
		return decisionGraph;
	}
	
	/**
	 * Getter-Methode für den Entscheidungsbaum.
	 * @return Aktueller Entscheidungsbaum.
	 */
	public DecisionTree getDecisionDataStructur() {
		return decisionDataStructur;
	}
	
	/**
	 * Getter-Methode für das Petrinetz-Objekt.
	 * @return Aktuelles Petrinetz-Objekt.
	 */
	public PNML getPNML() {
		return this.petriNetDataStructur;
	}
	
	/**
	 * Getter-Methode für den Pfad der zuletzt geöffneten Datei.
	 * @return Zuletzt geöffneter Pfad.
	 */
	public String getPath() {
		return this.filePath;
	}
	
	/**
	 * Methode startet Beschränktheitsanalyse des aktuellen Petrinetzes
	 */
	public void startNarrownessAnalysis() {
		try {
			// Setze Petrinetz auf Anfangsmarkierung
			resetPetriNet();
			
			// Lösche Fortschritt im EG
			loescheEGButton();
			
			// Beschränktheitsanalyse starten
			NarrownessAnalysis narrownessAnalysis = new NarrownessAnalysis(this.getPNML().getNet(), this.getDecisionDataStructur());
			narrownessAnalysis.startNarrownessAnalysis();
			String html = "";
			
			// Checke Analyse
			if(narrownessAnalysis.isLimited()) {
				
				// Petrinetz ist beschränkt
				// Erstelle DecisionNodes im DecisionGraph
				for(java.util.Map.Entry<Integer, DecisionNode> entry : this.decisionDataStructur.getDecisionNodes().entrySet()) {
					System.out.println(entry.getValue().getLabel());
					if(this.decisionGraph.getNode(entry.getValue().getID()) == null) {
						this.decisionGraph.createNode(entry.getValue());
					}
				}
				
				// Erstelle DecisionEdges im DecisionGraph
				for(java.util.Map.Entry<Integer, DecisionNode> entry : this.decisionDataStructur.getDecisionNodes().entrySet()) {
					for(java.util.Map.Entry<String, DecisionEdge> edge : entry.getValue().getOutputEdges().entrySet()) {
						if(this.decisionGraph.getEdge(String.valueOf(edge.getValue().getID())) == null) {
							this.decisionGraph.createEdge(edge.getValue());
						}
					}
				}
				
				// Text für Ausgabe erzeugen
				html = "<center><h2 style=\"font-family: Helvetica\">Das Petrinetz ist beschränkt</h2></center>";
				frame.addTextOutput("Das Petrinetz ist beschränkt !!!");
				frame.addTextOutput("Anzahl der Kanten: " + this.decisionDataStructur.getEdgeCounter());
				frame.addTextOutput("Anzahl der Knoten: " + this.decisionDataStructur.getNodeCounter());
				
				System.out.println("Grapf fertig erzeugt");
				
			} else {
				
				// Petrinetz ist unbeschränkt
				// Erzeuge pEG
				// Text für die Ausgabe erzeugen
				html = "<center><h2 style=\"font-family: Helvetica\">Das Petrinetz ist unbeschränkt</h2></center>";
				frame.addTextOutput("Das Petrinetz ist unbeschränkt !!!");
				frame.addTextOutput("m = " + narrownessAnalysis.getMStartNode().getLabel());
				frame.addTextOutput("m' = " + narrownessAnalysis.getMEndNode().getLabel());
				
				// Erstelle DecisionNodes im DecisionGraph
				for(java.util.Map.Entry<Integer, DecisionNode> entry : this.decisionDataStructur.getDecisionNodes().entrySet()) {
					System.out.println(entry.getValue().getLabel());
					if(this.decisionGraph.getNode(entry.getValue().getID()) == null) {
						this.decisionGraph.createNode(entry.getValue());
					}
				}
				
				// Erstelle DecisionEdges im DecisionGraph
				for(java.util.Map.Entry<Integer, DecisionNode> entry : this.decisionDataStructur.getDecisionNodes().entrySet()) {
					for(java.util.Map.Entry<String, DecisionEdge> edge : entry.getValue().getOutputEdges().entrySet()) {
						if(this.decisionGraph.getEdge(String.valueOf(edge.getValue().getID())) == null) {
							this.decisionGraph.createEdge(edge.getValue());
						}
					}
				}
				
				// Setze m-Markierung und m'-Markierung
				narrownessAnalysis.getMStartNode().getNode().setAttribute("ui.class", "mStartNode");
				narrownessAnalysis.getMEndNode().getNode().setAttribute("ui.class", "mEndNode");
				
				// Hebe Kanten in EG hervor
				for(mypackage.model.DecisionEdge decisionEdge : narrownessAnalysis.getUnlimitedPath()) {
					System.out.println(decisionEdge.getID() + " rot färben");
					decisionEdge.getEdge().setAttribute("ui.class", "unlimited");
				}
			}
			
			// Ausgabe in Tabelle vorbereiten
			html += "<center><table style=\"border:1px solid #000000;\"><tr><td>Dateiname:</td><td>" + this.file.getName() + "</td></tr><tr><td>Beschränkt:</td><td>"; //+  "</td></tr></table></center>";
			if(narrownessAnalysis.isLimited()) {
				html += "ja";
			} else {
				html += "nein";
			}
			html += "</td></tr>";
			String pathInformation = "";
			
			// Ausgabe auf dem Textfeld des Hauptfensters
			if(narrownessAnalysis.isLimited()) {
				pathInformation = narrownessAnalysis.getNodesNumber() + " / " + narrownessAnalysis.getEdgesNumber();
				this.frame.addTextOutput("--------------------------------------------------------------------------");
				this.frame.addTextOutput("Petrinetz ist beschränkt !!!");
				this.frame.addTextOutput("Erreichbarkeitsgraph fertig erstellt.");
				this.frame.addTextOutput("--------------------------------------------------------------------------");
			} else {
				pathInformation = narrownessAnalysis.getMStartNode().getLabel() + ", " + narrownessAnalysis.getMEndNode().getLabel() + "; ";
				this.frame.addTextOutput("--------------------------------------------------------------------------");
				this.frame.addTextOutput("Petrinetz ist unbeschränkt !!!");
				this.frame.addTextOutput("Unbeschränktheitsbedingung: m=" + narrownessAnalysis.getMStartNode().getLabel() + ", m'=" + narrownessAnalysis.getMEndNode().getLabel());
				this.frame.addTextOutput("Partieller Erreichbarkeitsgraph fertig erstellt.");
				this.frame.addTextOutput("--------------------------------------------------------------------------");
			}
			
			html += "<tr><td>Knoten / Kanten bzw. m, m'; Pfadlänge:Pfad</td><td>" + pathInformation + "</td></tr>";
			html += "</table></center>";
			
			// Auswertefenster erzeugen
			NarrownessAnalysisDialog analysisDialog = new NarrownessAnalysisDialog(this.frame);
			analysisDialog.setHTML(html);
			analysisDialog.show(true);
		} catch (Exception e) {
			CatchPrinter.printException(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode erzeugt einen Graphen zu einem Entscheidungsbaum.
	 * @param graph Entscheidungsbaum der nun visuell dargestellt werden soll.
	 * @param startNode Startknoten des Entscheidungsgraphes.
	 */
	private void createGraph(DecisionTree graph, DecisionNode startNode) {
		
		// Erzeuge Graph und Startknoten
		for(java.util.Map.Entry<String, DecisionEdge> entry : startNode.getOutputEdges().entrySet()) {
			this.decisionGraph.createNode(entry.getValue().getDestinationNode());
			createGraph(graph, entry.getValue().getDestinationNode());
			this.decisionGraph.createEdge(entry.getValue());
		}
	}
	
	/**
	 *  Speichert Taste zwischen
	 * @param keyCode Ziffer, die die Taste darstellt.
	 */
	public void setKeyPressed(int keyCode) {
		System.out.println("Pressed");
		switch(keyCode) {
		// STRG-Taste
		case 17:
			strgPressed = true;
			break;
		}
	}
	
	/**
	 * Löcht Taste wieder
	 * @param keyCode Ziffer, die die Taste darstellt.
	 */
	public void setKeyReleased(int keyCode) {
		System.out.println("setKeyReleaseds");
		switch(keyCode) {
		// STRG-Taste
		case 17:
			strgPressed = false;
			break;
		}
	}
	
	/**
	 * Gibt Taste zurück.
	 * @param keyCode Tastencode, der abgefragt werden soll.
	 * @return Zustand des Tastendruckes.
	 */
	public boolean getKeyCodeValue(int keyCode) {
		switch(keyCode) {
		// STRG-Taste
		case 17:
			return strgPressed;
		default:
			return false;
		}
	}
}
