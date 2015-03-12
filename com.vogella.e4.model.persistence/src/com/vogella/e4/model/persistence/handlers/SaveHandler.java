package com.vogella.e4.model.persistence.handlers;

import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.internal.workbench.E4XMIResourceFactory;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class SaveHandler {

	@Execute
	public void execute(EModelService modelService, MWindow window) {

		// store model of the active perspective
		MPerspective activePerspective = modelService.getActivePerspective(window);

		// create a resource, which is able to store e4 model elements
		E4XMIResourceFactory e4xmiResourceFactory = new E4XMIResourceFactory();
		Resource resource = e4xmiResourceFactory.createResource(null);

		// You must clone the perspective as snippet, otherwise the running
		// application would break, because the saving process of the resource
		// removes the element from the running application model
		MUIElement clonedPerspective = modelService.cloneElement(activePerspective, window);

		// add the cloned model element to the resource so that it may be stored
		resource.getContents().add((EObject) clonedPerspective);

		FileOutputStream outputStream = null;
		try {

			// Use a stream to save the model element
			outputStream = new FileOutputStream("/home/simon/simon.xmi");

			resource.save(outputStream, null);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
