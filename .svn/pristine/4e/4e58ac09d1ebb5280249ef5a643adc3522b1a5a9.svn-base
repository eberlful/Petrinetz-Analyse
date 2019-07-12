package mypackage.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;

import mypackage.controller.PetriNetController;
import mypackage.controller.ClickListener.DecisionClickListener;
import mypackage.controller.ClickListener.PetriNetClickListener;
/**
 * Darstellungsklasse, die vom JFramework erbt. Hiermit wird ein Fenster erzeugt,
 * das zur Darstellung des Hauptfensters und damit auch für die Grundfunktionen dient.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class PetriNetFrame extends JFrame{
	
	private PetriNetFrame frame = this;
	
	private static boolean externGraph = false;
	private static int toolBarButtonHeight = 20;
	private static int toolBarButtonWidth = 20;
	
	private static final long serialVersionUID = 1L;
	
	// Controller für das Frame
	private PetriNetController controller;
	
	//-----------------------------------------------------------------
	
	// Panel für Petrinetze mittels Graphstream
	private ViewPanel petriNetsPanel;
	private ViewPanel petriNetsSavePanel;
	
	// Panel für Entscheidungsgraph mitels GraphStream
	private ViewPanel decisionGraphPanel;
	
	//-----------------------------------------------------------------
	
	// Toobar für die Steuerung der GUI
	private JToolBar toolBar;
	
	// Buttons für die Toolbar
	private JButton deleteEGButton;
	private JButton markePlusButton;
	private JButton markeMinusButton;
	private JButton resetButton;
	private JButton analyseButton;
	
	//-----------------------------------------------------------------
	
	// Menübar für die Steuerung der GUI
	private JMenuBar menuBar;
	
	private JMenu menuData;
	
	// Menüitmes für das Menü
	private JMenuItem openMenuData;
	
	private JMenuItem loadMenuData;
	
	private JMenuItem analyseMoreDataMenuData;
	
	private JMenuItem closeMenuData;
	
	private JMenu visu;
	
	private JCheckBoxMenuItem optionalVisu;
	
	private JMenu graphStream;
	
	private JMenuItem resetCamera;
	
	//-----------------------------------------------------------------
	
	// Layoutelemnte für das Frame
	private JSplitPane horizontalSplit;
	
	private JSplitPane verticalSplit;
	
	private BorderLayout borderLayout;
	
	private JScrollPane scrollTextArea;
	
	private JPanel leftComponent;
	
	private JPanel rightComponent;
	
	//-----------------------------------------------------------------
	
	// Textfeld für das visuelle Feedback der Software
	private JTextArea textField;
	
	// Label zur Visualisierung der geöffneten Datei
	private JLabel eventLabel;
	
	//-----------------------------------------------------------------
	
	// GraphStream-Komponenten
	private Viewer petriNetViewer;
	private Viewer decisionViewer;
	
	private ViewerPipe petriNetViewerPipe;
	private ViewerPipe decisionViewerPipe;
	
	//-----------------------------------------------------------------
	
	// ClickListener
	private PetriNetClickListener petriNetCLicklistener;
	private DecisionClickListener decisionClicklistener;
	
	//-----------------------------------------------------------------
	
	private boolean petriNetIsLoad = false;
	
	//-----------------------------------------------------------------
	
	/**
	 * Konstruktor zum Erzeugen des Fensters
	 * @param title Name des Fensters
	 */
	public PetriNetFrame(String title) {
		// Konstruktoraufruf der Suuperklasse Frame
		super(title);
		
		// Rendereinstellungen der Libary setzen
		System.setProperty("org.graphstream.ui.renderer","org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		// Controller erzeugen
		controller = new PetriNetController(this);
		
		// Layout für das Fenster
		borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		
		// Funktionsaufruf um die Konfiguration des Fensters zu laden
		setConfigFrame();
		
		// Initialisierung des Fensters und deren enthaltende Elemente
		initFrame();
	}
	
	/**
	 * Funktion setzt alle Einstellungen des Frames
	 */
	private void setConfigFrame() {
		// Aktion für das Benutzen des X im Rahmen des Fensters
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Größe des Fensters für den Start einstellen (1000 x 700)
		this.setSize(1000, 700);
		
		// Größe wird veränderbar gesetzt
		this.setResizable(true);
		
		// Setze Startposition -> Startposition Center
		this.setLocationRelativeTo(null);
		
		// Fenster sichtbar machen
		this.setVisible(true);
	}
	
	/**
	 * Methode initialisiert das Viewer-Objekt und dazugehörigen Komponenten um mit dem 
	 * Petrinetz-Graph zu interagieren.
	 * @param graph Graph zur Darstellung des Petrinetzes.
	 */
	public void initPetriNetViewer(MultiGraph graph) {
		// Viewer-Objekt erstellen
		petriNetViewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		petriNetViewer.disableAutoLayout();
		
		// Graphpanel registrieren
		petriNetsPanel = petriNetViewer.addDefaultView(externGraph);
		horizontalSplit.setLeftComponent(petriNetsPanel);
		
		petriNetViewerPipe = petriNetViewer.newViewerPipe();
		
		// Clicklistener erzeugen und anmelden
		petriNetCLicklistener = new PetriNetClickListener(controller);
		petriNetViewerPipe.addViewerListener(petriNetCLicklistener);
		
		// Mouselistener erzeugen und anmelden
		petriNetsPanel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				petriNetViewerPipe.pump();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				petriNetViewerPipe.pump();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				petriNetViewerPipe.pump();
			}
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				super.mouseWheelMoved(e);
				petriNetViewerPipe.pump();
			}
			
		});
		
		horizontalSplit.setDividerLocation(getWidth()/2);
		
		this.addEventLabel(this.controller.getPath());
	}
	
	/**
	 * Initialisierungsvorgang für DecisionGraphViewer
	 * @param graph Vorher erzeugter Graph vom Typ MultiGraph
	 */
	public void initDecisionGraphViewer(MultiGraph graph) {
		
		// Viewer-Objekt erstellen
		decisionViewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		decisionViewer.enableAutoLayout();
		
		// Graphpanel registrieren
		decisionGraphPanel = decisionViewer.addDefaultView(externGraph);
		horizontalSplit.setRightComponent(decisionGraphPanel);
		
		decisionViewerPipe = decisionViewer.newViewerPipe();
		
		horizontalSplit.setDividerLocation(getWidth()/2);
		
		System.out.println("initDecisionGraphViewer fertig");
		
		// Listener registrieren
		decisionClicklistener = new DecisionClickListener(controller);
		decisionViewerPipe.addViewerListener(decisionClicklistener);
		decisionGraphPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				System.out.println("PetriNetFrame -> Zeile: 314, MouseAdapter");
				decisionViewerPipe.pump();
			}
		});
		// Strg-Taste zum Infodialog zu aktivieren
		decisionGraphPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				controller.setKeyPressed(e.getKeyCode());
				super.keyPressed(e);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				controller.setKeyReleased(e.getKeyCode());
				super.keyReleased(e);
			}
		});
	}
	
	/**
	 * Initialisierung des Fensters und deren Objekte
	 */
	private void initFrame() {
		
		// Registriere Listener an Fenster für Größenveränderung
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
				// Sorgt fuer richtiges Verhältnis nach Veraenderung an der Groeße des Fensters
				horizontalSplit.setDividerLocation(getWidth()/2);
				verticalSplit.setDividerLocation((int)Math.round(getHeight()*0.6));
				super.componentResized(e);
			}
		});
		
		toolBar = new JToolBar();
		
		// Textfeld initialisieren
		textField = new JTextArea();
		
		// Größe der Toolbar
		toolBar.setSize(230, 40);
		
		// Buttons für die Toolbar initialisieren
		deleteEGButton = new JButton("EG Löschen");
		markePlusButton = new JButton("Marke Plus");
		markeMinusButton = new JButton("Marke Minus");
		resetButton = new JButton("Reset");
		analyseButton = new JButton("Analyse");
		
		// Icon-Objekt erzeugen
		ImageIcon deleteEGIcon = new ImageIcon(PetriNetFrame.class.getResource("/images/icons8-löschen-48.png"));
		ImageIcon markePlusIcon = new ImageIcon(PetriNetFrame.class.getResource("/images/icons8-minus-48.png"));
		ImageIcon markeMinusIcon = new ImageIcon(PetriNetFrame.class.getResource("/images/icons8-plus-4.png"));
		ImageIcon resetIcon = new ImageIcon(PetriNetFrame.class.getResource("/images/icons8-neustart-48.png"));
		ImageIcon analyseIcon = new ImageIcon(PetriNetFrame.class.getResource("/images/icons8-fernglas-48.png"));
		
		// Icon-Objekt Größe einstellen
		deleteEGIcon.setImage(deleteEGIcon.getImage().getScaledInstance(toolBarButtonHeight, toolBarButtonWidth,Image.SCALE_DEFAULT));
		markePlusIcon.setImage(markePlusIcon.getImage().getScaledInstance(toolBarButtonHeight, toolBarButtonWidth,Image.SCALE_DEFAULT));
		markeMinusIcon.setImage(markeMinusIcon.getImage().getScaledInstance(toolBarButtonHeight, toolBarButtonWidth,Image.SCALE_DEFAULT));
		resetIcon.setImage(resetIcon.getImage().getScaledInstance(toolBarButtonHeight, toolBarButtonWidth, Image.SCALE_DEFAULT));
		analyseIcon.setImage(analyseIcon.getImage().getScaledInstance(toolBarButtonHeight, toolBarButtonWidth, Image.SCALE_DEFAULT));
		
		// Icon für Buttons setzen
		deleteEGButton.setIcon(deleteEGIcon);
		markePlusButton.setIcon(markePlusIcon);
		markeMinusButton.setIcon(markeMinusIcon);
		resetButton.setIcon(resetIcon);
		analyseButton.setIcon(analyseIcon);
		
		// Tooltips erstellen
		deleteEGButton.setToolTipText("Lösche Erreichbarkeitsgraph");
		markePlusButton.setToolTipText("Marke inkremmentieren");
		markeMinusButton.setToolTipText("Marke dekremmentieren");
		resetButton.setToolTipText("Reset des Petrinetzes");
		analyseButton.setToolTipText("Analyse - Erreichbarkeitsgraph");
		
		// Listener für die Buttons in der Toolbar
		deleteEGButton.addActionListener(new ActionListener() {
			// Der Erreichbarkeitsgraph soll gelöscht werden und
			// das Petrinetz in den Startzustand gesetzt
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("deleteEGButton gedrückt");
				addTextOutput("--------------------------------------------------------------------------");
				if(frame.petriNetIsLoad) {
					addTextOutput("Starte Rücksetzvorgang des Petrinetzes und lösche den Erreichbarkeitsgraph");
					controller.loescheEGButton();
					addTextOutput("Rücksetzvorgang des Petrinetzes erfolgreich !!!");
				} else {
					addTextOutput("Bitte öffnen Sie zuerst ein Petrinetz");
				}
				addTextOutput("--------------------------------------------------------------------------");
			}
		});
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("resetButton gedrückt");
				addTextOutput("--------------------------------------------------------------------------");
				if(frame.petriNetIsLoad) {
					addTextOutput("Starte Rücksetzvorgang des Petrinetzes");
					controller.resetPetriNet();
					addTextOutput("Rücksetzvorgang des Petrinetzes erfolgreich !!!");
				} else {
					addTextOutput("Bitte öffnen Sie zuerst ein Petrinetz");
				}
				addTextOutput("--------------------------------------------------------------------------");
			}
		});
		markePlusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("markePlusButton gedrückt");
				addTextOutput("--------------------------------------------------------------------------");
				if(frame.petriNetIsLoad) {
					if(controller.decrementPlace()) {
						addTextOutput(controller.getLastSelectedElement() + " wurde dekrementiert !!!");
					} else {
						addTextOutput("Bitte wählen Sie zur Dekrementierung eine Stelle aus !!!");
					}
				} else {
					addTextOutput("Bitte öffnen Sie zuerst ein Petrinetz");
				}
				addTextOutput("--------------------------------------------------------------------------");
				
			}
		});
		markeMinusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("markeMinusButton gedrückt");
				addTextOutput("--------------------------------------------------------------------------");
				if(frame.petriNetIsLoad) {
					if(controller.inkrementPlace()) {
						addTextOutput(controller.getLastSelectedElement() + " wurde inkrementiert !!!");
					} else {
						addTextOutput("Bitte wählen Sie zur Inkrementierung eine Stelle aus !!!");
					}
				} else {
					addTextOutput("Bitte öffnen Sie zuerst ein Petrinetz");
				}
				addTextOutput("--------------------------------------------------------------------------");
				
				
			}
		});
		analyseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("analyseButton gedrückt");
				addTextOutput("--------------------------------------------------------------------------");
				if(frame.petriNetIsLoad) {
					controller.startNarrownessAnalysis();
				} else {
					addTextOutput("Bitte öffnen Sie zuerst ein Petrinetz");
				}
				addTextOutput("--------------------------------------------------------------------------");	
			}
		});
		
		// Schaltflächen in die Toolbar hinzufügen
		toolBar.add(deleteEGButton);
		toolBar.add(resetButton);
		toolBar.add(markePlusButton);
		toolBar.add(markeMinusButton);
		toolBar.add(analyseButton);
		
		// Schaltflächen in die Menübar initialisieren und an Menüleiste anbinden
		menuBar = new JMenuBar();
		
		// Menu-Objekte erzeugen
		menuData = new JMenu("Datei");
		graphStream = new JMenu("GraphStream");
		visu = new JMenu("Visualisierung");
		
		// MenuItem-Objekte erzeugen
		openMenuData = new JMenuItem("Öffnen");
		loadMenuData = new JMenuItem("Neu Laden");
		analyseMoreDataMenuData = new JMenuItem("Analyse mehrerer Dateien");
		closeMenuData = new JMenuItem("Beenden");
		
		resetCamera = new JMenuItem("Kameraposition reseten");
		
		optionalVisu = new JCheckBoxMenuItem("Beschränktheitsanalyse Visu");
		
		// Optionen an Menübar anheften
		menuBar.add(menuData);
		menuBar.add(graphStream);
		menuBar.add(visu);
		
		// Einzelne Optionen an Menüfeld anheften
		menuData.add(openMenuData);
		menuData.addSeparator();
		menuData.add(loadMenuData);
		menuData.addSeparator();
		menuData.add(analyseMoreDataMenuData);
		menuData.addSeparator();
		menuData.add(closeMenuData);
		
		graphStream.add(resetCamera);
		
		visu.add(optionalVisu);
		
		// Menubar anheften
		this.setJMenuBar(menuBar);
		
		// Eventhandling für Schaltflächen auf der Menüleiste
		optionalVisu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		openMenuData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.openMenu();
			}
			
		});
		loadMenuData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(frame.petriNetIsLoad) {
					controller.loadMenu();
				} else {
					addTextOutput("--------------------------------------------------------------------------");
					addTextOutput("Bitte laden Sie zuerst ein Petrinetz !!!");
					addTextOutput("--------------------------------------------------------------------------");
				}
			}
			
		});
		analyseMoreDataMenuData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.analyseMoreData();
			}
			
		});
		closeMenuData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.closeMenu();
			}
			
		});
		
		resetCamera.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addTextOutput("--------------------------------------------------------------------------");
				if(frame.petriNetIsLoad) {
					addTextOutput("Kamerapositionen werden zurückgesetzt!!!");
					petriNetViewer.getDefaultView().getCamera().resetView();
					decisionViewer.getDefaultView().getCamera().resetView();
				} else {
					addTextOutput("Bitte öffnen Sie zuerst ein Petrinetz");
				}
				addTextOutput("--------------------------------------------------------------------------");
			}
		});
		
		// Splitverwaltung
		horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		horizontalSplit.setDividerLocation(getWidth()/2);
		
		leftComponent = new JPanel();
		rightComponent = new JPanel();
		horizontalSplit.setLeftComponent(leftComponent);
		horizontalSplit.setRightComponent(rightComponent);
		
		verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalSplit.setTopComponent(horizontalSplit);
		verticalSplit.setDividerLocation((int)Math.round(getHeight()*0.6));
		
		// ScrollPane
		scrollTextArea = new JScrollPane(textField, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
	            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// Einstellungen für TextArea
		textField.setEditable(true);
		textField.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		// Elemente an Frame ankoppeln
		this.add(toolBar, BorderLayout.PAGE_START);
		this.add(verticalSplit, BorderLayout.CENTER);
		verticalSplit.setBottomComponent(scrollTextArea);
	}
	
	/**
	 * Funktion für die Ausgabe an der Textbox
	 * @param text Informationen vom Typ String
	 */
	public void addTextOutput(String text) {
		textField.append(new Date().toString() + " : " + text + "\n");
	}
	
	/**
	 * Funktion für die Ausgabe an der Textbox, aber ohne Datum im Präfix.
	 * @param text Text der ausgegeben werden soll.
	 */
	public void addTextOutputAnalysis(String text) {
		textField.append(text);
	}
	
	/**
	 * Label mit Absolutpfad der geöffneten Datei
	 * @param text String mit dem Pfad, der als Text im Label steht
	 */
	public void addEventLabel(String text) {
		// Check ob Objekt schon exisitert
		if(text != null) {
			if(eventLabel == null) {
				eventLabel = new JLabel(text);
				this.add(eventLabel, BorderLayout.SOUTH);
			this.setSize(this.getWidth(), this.getHeight() + 1);
			} else {
				eventLabel.setText(text);
			}
		}
	}
	
	/**
	 * Setzt Variable beim Laden eines Petrinetzes
	 * @param value Wahrheitswert für geladenem Petrinetz
	 */
	public void setPetriNetIsLoad(boolean value) {
		this.petriNetIsLoad = value;
	}
	
	/**
	 * Getter-Methode, die den Status der externen Ausgabe der Beschränktheitsanalyse freigibt.
	 * @return Freigabe externe Anzeige verfügbar.
	 */
	public boolean getOptionalCheck() {
		return this.optionalVisu.getState();
	}

	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Startfenster erzeugen
				new PetriNetFrame("Markus Eberl - 7642300");
			}
			
		});
	}

}
