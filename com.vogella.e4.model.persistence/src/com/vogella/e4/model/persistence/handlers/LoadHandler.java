package com.vogella.e4.model.persistence.handlers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.internal.workbench.E4XMIResourceFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.ecore.resource.Resource;

public class LoadHandler {

	@Execute
	public void execute(EPartService partService, EModelService modelService, MWindow window) throws IOException {

		// create a resource, which is able to store e4 model elements
		E4XMIResourceFactory e4xmiResourceFactory = new E4XMIResourceFactory();
		Resource resource = e4xmiResourceFactory.createResource(null);

		FileInputStream inputStream = null;
		try {

			inputStream = new FileInputStream("/home/simon/simon.xmi");

			// load the stored model element
			resource.load(inputStream, null);

			if (!resource.getContents().isEmpty()) {

				// after the model element is loaded it can be obtained from the
				// contents of the resource
				MPerspective loadedPerspective = (MPerspective) resource.getContents().get(0);

				// get the parent perspective stack, so that the loaded
				// perspective can be added to it.
				MPerspective activePerspective = modelService.getActivePerspective(window);
				MElementContainer<MUIElement> perspectiveParent = activePerspective.getParent();

				// remove the current perspective, which should be replaced by
				// the loaded one
				List<MPerspective> alreadyPresentPerspective = modelService.findElements(window,
						loadedPerspective.getElementId(), MPerspective.class, null);
				for (MPerspective perspective : alreadyPresentPerspective) {
					modelService.removePerspectiveModel(perspective, window);
				}

				// add the loaded perspective and switch to it
				perspectiveParent.getChildren().add(loadedPerspective);

				partService.switchPerspective(loadedPerspective);
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
