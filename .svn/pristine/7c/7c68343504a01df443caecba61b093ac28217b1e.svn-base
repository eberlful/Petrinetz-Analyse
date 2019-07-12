package mypackage.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import mypackage.model.NarrownessAnalysisResult;
/**
 * Darstellungsklasse, die vom JFramework erbt. Hiermit wird ein Fenster erstellt, das
 * zur Darstellung des Ergebnisses der Analyse mehrerer Dateien und einzelner Netze dient.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class NarrownessAnalysisDialog extends JFrame{
	// Style-Attribute
	final static String CSS_Horizontal_Dividers = "th, td {\r\n" + 
			"  border-bottom: 1px solid #000000;\r\n" + 
			"}";
	final static String CSS_Hoover = "tr:hover {background-color: #f5f5f5;}\r\n";
	final static String CSS_Header = "th {\r\n" + 
			"  background-color: #4CAF50;\r\n" + 
			"  color: white;\r\n" + 
			"}";
	
	private boolean table = false;
	
	private JEditorPane htmlPane = new JEditorPane();
	
	/**
	 * Konstruktor, der das Fenster initialisiert.
	 */
	public NarrownessAnalysisDialog() {
		// Einstellungen des Fensters
		this.setVisible(true);
		this.setSize(600, 500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(new JScrollPane(htmlPane));
		
		htmlPane.setEditable(false);
		// EditorKit erzeugen
		javax.swing.text.html.HTMLEditorKit htmlKit = new javax.swing.text.html.HTMLEditorKit();
	    
		// EditorKit setzen
		htmlPane.setEditorKit(htmlKit);
	}
	
	/**
	 * Konstruktor, der das Fenster im Bezug zum Hauptfenster erzeugt.
	 * @param frame Hauptfenster als Maß
	 */
	public NarrownessAnalysisDialog(PetriNetFrame frame) {
		this();
		this.setLocationRelativeTo(frame);
	}
	
	/**
	 * Konstruktor, für das Fenster um die Analyse mehrerer Dateien darstellen zu können.
	 * @param results Liste der Ergebnisse.
	 * @param frame Fenster als Positionsreferenz.
	 */
	public NarrownessAnalysisDialog(java.util.ArrayList<NarrownessAnalysisResult> results, mypackage.view.PetriNetFrame frame) {
		this();
		this.setLocationRelativeTo(frame);
		table = true;
		this.setSize(800, 600);
		String text = "<table><tr><th>Dateiname</th><th>beschränkt</th><th>Knoten / Kanten bzw. m, m'; Pfadlänge:Pfad</th></tr>";
		for(NarrownessAnalysisResult result : results) {
			text += result.toString();
		}
		text += "</table>";
		setHTML(text);
	}
	
	/**
	 * Setter-Methode, die einen Text in HTML dem Fenster übergibt.
	 * @param html HTML Tabelle zur Darstellung.
	 */
	public void setHTML(String html) {
		String startTag =  "<html><head></head><body>";
		String endTag = "</body></html>";
		if(table) {
			startTag = "<html><head><style>" + CSS_Hoover + CSS_Horizontal_Dividers + CSS_Header + "</style></head><body>";
		}
		htmlPane.setText(startTag + html + endTag);
	}
}
