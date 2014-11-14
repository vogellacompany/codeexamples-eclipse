 
package wizardtranslation.handlers;


import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SwitchPerspectiveHandler {
	@Execute
	public void execute(EModelService modelService, EPartService partService,
			MWindow window) {
		MPerspective activePerspective = modelService
				.getActivePerspective(window);

		List<MPerspective> perspectives = modelService.findElements(window,
				null, MPerspective.class, null);

		// as we only have two perspectives simply switch to the non active
		// perspective.
		for (MPerspective perspective : perspectives) {
			if (!perspective.equals(activePerspective)) {
				partService.switchPerspective(perspective);
			}
		}
	}
		
}