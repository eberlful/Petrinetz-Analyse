package mypackage.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
/**
 * Darstellungsklasse, die vom JFramework erbt. Hiermit wird ein Fenster erzeugt,
 * das zu einzelnen Knoten genauere Informationen liefert.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class NodeDialog extends JFrame{
	// Style-Attribute
	final static String CSS_Horizontal_Dividers = "th, td {\r\n" + 
			"  border-bottom: 1px solid #000000;\r\n" + 
			"}";
	final static String CSS_Hoover = "tr:hover {background-color: #f5f5f5;}\r\n";
	final static String CSS_Header = "th {\r\n" + 
			"  background-color: #4CAF50;\r\n" + 
			"  color: white;\r\n" + 
			"}";
	
	private mypackage.model.DecisionNode node = null;
	
	private JEditorPane htmlPane = new JEditorPane();
	
	/**
	 * Konstruktor, der ein Knoten übergibt, dessen Informationen dargestellt werden sollen.
	 * @param node Konten über dem die Informationen dargestellt werden sollen.
	 */
	public NodeDialog(mypackage.model.DecisionNode node) {
		// Einstellungen des Fensters setzen
		this.setVisible(true);
		this.setTitle("Knoten-Information");
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	    this.setSize(420, 400);
	    this.setResizable(false);
		this.node = node;
		this.add(new JScrollPane(htmlPane));
		htmlPane.setEditable(false);
		
		// EditorKit erzeugen
		javax.swing.text.html.HTMLEditorKit htmlKit = new javax.swing.text.html.HTMLEditorKit();
	    
		// EditorKit setzen
		htmlPane.setEditorKit(htmlKit);
		
		// Ausgehende Kanten listen
		String places = "<tr><td>Ausgeende Kanten:</td><td>";
		for(java.util.Map.Entry<String, mypackage.model.DecisionEdge> entry : node.getOutputEdges().entrySet()) {
			places += "" + entry.getValue().getDestinationNode().getLabel() + " "; 
		}
		
		// Eingehende Kanten listen
		places += "</td></tr><tr><td>Eingehende Kanten:</td><td>";
		for(java.util.Map.Entry<String, mypackage.model.DecisionEdge> entry : node.getInputEdges().entrySet()) {
			places += entry.getValue().getSourceNode().getLabel() + " ";
		}
		places += "</td></tr>";
		htmlPane.setText("<html><head><style>" + CSS_Header + CSS_Horizontal_Dividers + "</style></head><body><center><table><tr><td>Label:</td><td>" + node.getLabel()
				+ "</td>" + "</tr>" + places
						+ "</table></center></body></html>");
	}
	
}
