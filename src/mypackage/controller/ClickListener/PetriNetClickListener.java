package mypackage.controller.ClickListener;

import org.graphstream.ui.view.ViewerListener;

import mypackage.controller.PetriNetController;

/**
 * Kindklasse von ViewerListener. Hiermit werden die Aktionen gesteuert, die zum Interagieren
 * des Petrinetzes nötig sind.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class PetriNetClickListener implements ViewerListener{
	
	private PetriNetController controller;
	
	/**
	 * Konstruktor, in dem ein Controller-Objekt mit übergeben wird.
	 * @param controller Objekt, in dem die Funktionalität implementiert ist.
	 */
	public PetriNetClickListener(PetriNetController controller) {
		this.controller = controller;
	}
	
	/**
	 * Aufruf beim Schließen des Erreichbarkeitsgraphen.
	 */
	@Override
	public void viewClosed(String viewName) {
		
	}
	
	/**
	 * Methode wird beim Klicken auf einen Knoten aufgerufen.
	 */
	@Override
	public void buttonPushed(String id) {
		System.out.println("PetriNetClickListener - buttonPushed: " + id);
		
		if(controller != null) {
			for(java.util.Map.Entry<String, mypackage.model.Place> entry : controller.getPNML().getNet().getPlaces().entrySet()) {
				if(!entry.getKey().equals(id)) {
					entry.getValue().getElement().setAttribute("ui.style", "stroke-color: black;");
				}
			}
			
			// Falls möglich, Transition schalten
			controller.clickNodeInPetriNetGraph(id);
			
			// Speichere zuletzt makiertes Element
			controller.setLastSelectedElement(id);
		}
	}
	
	/**
	 * Methode wird beim Loslassen eines Knoten aufgerufen.
	 */
	@Override
	public void buttonReleased(String id) {
		
	}

}
