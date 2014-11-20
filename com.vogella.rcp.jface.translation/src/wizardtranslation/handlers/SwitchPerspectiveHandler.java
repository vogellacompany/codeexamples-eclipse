 
package wizardtranslation.handlers;


import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SwitchPerspectiveHandler {
	@Execute
	public void execute(EModelService modelService, EPartService partService,
			MWindow window,
			@Named("com.vogella.rcp.jface.translation.commandparameter.perspectiveid") String id) {
		MPerspective activePerspective = modelService
				.getActivePerspective(window);


		MUIElement find = modelService.find(id, window);
		if (find == null || activePerspective.equals(find)) {
			return;
		}

		partService.switchPerspective((MPerspective) find);
	}
		
}