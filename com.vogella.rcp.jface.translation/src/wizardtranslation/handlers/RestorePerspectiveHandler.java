package wizardtranslation.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class RestorePerspectiveHandler {
	@Execute
	public void execute(EModelService modelService, MWindow window, EPartService partService) {

		// get active perpective and find stored snippet of this perspective
		MPerspective activePerspective = modelService.getActivePerspective(window);
		MUIElement perspectiveSnippet = modelService.cloneSnippet(window, activePerspective.getElementId(), window);

		// remove existing active perspective
		MElementContainer<MUIElement> parent = activePerspective.getParent();
		modelService.removePerspectiveModel(activePerspective, window);

		// add stored perspective snippet and switch to it
		parent.getChildren().add(perspectiveSnippet);
		partService.switchPerspective((MPerspective) perspectiveSnippet);
	}

	@CanExecute
	public boolean canExecute(EModelService modelService, MWindow window) {

		// check whether a snippet for the active perspective exists
		MPerspective activePerspective = modelService.getActivePerspective(window);
		return modelService.findSnippet(window, activePerspective.getElementId()) != null;
	}
}
