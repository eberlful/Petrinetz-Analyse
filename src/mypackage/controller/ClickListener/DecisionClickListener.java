package mypackage.controller.ClickListener;

import org.graphstream.ui.view.ViewerListener;

import mypackage.controller.PetriNetController;
import mypackage.view.NodeDialog;

/**
 * Kindklasse von ViewerListener. Hiermit werden die Aktionen gesteuert, die zum Interagieren
 * des Erreichbarkeitsgraphens nötig sind.
 * @author Markus Eberl
 * @version 1.0.0
 *
 */
public class DecisionClickListener implements ViewerListener{
	
	private PetriNetController controller;
	
	/**
	 * Konstruktor, in dem ein Controller-Objekt mit übergeben wird.
	 * @param controller Objekt, in dem die Funktionalität implementiert ist.
	 */
	public DecisionClickListener(PetriNetController controller) {
		this.controller = controller;
	}
    
	/**
	 * Aufruf beim Schließen des Erreichbarkeitsgraphen.
	 */
	@Override
	public void viewClosed(String viewName) {
		
	}
	
	/**
	 * Methode wird beim Klicken auf einen Knoten aufgerufen. Es wird entweder ein Knoteninformationsfenster
	 * oder die Markierung im Petrinetz wird auf alten Zustand zurückgesetzt.
	 */
	@Override
	public void buttonPushed(String id) {
		System.out.println("DecisionClickListener -> buttonPushed " + id);
		
		// Checke ob STRG-Taste gedrückt und Controller-Objekt
		if(controller.getKeyCodeValue(17) && controller != null) {
			System.out.println("Zusatzinfos für DecisionNodes gestartet");
			
			// Starte Knoteninformationsfenster
			NodeDialog d = new NodeDialog(controller.getDecisionDataStructur().getDecisionNodes().get(Integer.parseInt(id)));
			d.show(true);
		} else {
		
		// Alte Makierung vom Knoten in das Petrinetz übernehmen
		if(controller != null) {
			for(java.util.Map.Entry<Integer, mypackage.model.DecisionNode> entry : controller.getDecisionDataStructur().getDecisionNodes().entrySet()) {
				// Vergleiche Knoten
				if(entry.getKey() == Integer.parseInt(id)) {
					for(java.util.Map.Entry<String, Integer> decisionNodePlace : entry.getValue().getMarkings().entrySet()) {
						for(java.util.Map.Entry<String, mypackage.model.Place> petriNetPlace : controller.getPNML().getNet().getPlaces().entrySet()) {
							if(decisionNodePlace.getKey().equals(petriNetPlace.getKey())) {
								this.controller.getDecisionDataStructur().setLastNode(entry.getValue());
								petriNetPlace.getValue().setMarkingValue(decisionNodePlace.getValue());
								controller.checkAllTransitions();
							}
						}
					}
				}
			}
		} else {
			System.out.println("DecisionClickListener -> buttonPushed: Fehlende Referenz auf Controller festegestellt.");
		}
		}
	}
	
	/**
	 * Methode wird beim Loslassen eines Knoten aufgerufen.
	 */
	@Override
	public void buttonReleased(String id) {
		
	}

}
