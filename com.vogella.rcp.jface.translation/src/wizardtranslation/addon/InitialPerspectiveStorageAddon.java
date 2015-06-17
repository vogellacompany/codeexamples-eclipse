
package wizardtranslation.addon;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;

@SuppressWarnings("restriction")
public class InitialPerspectiveStorageAddon {

	@Inject
	@Optional
	public void selectedElement(@EventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event, EModelService modelService) {
		if (!UIEvents.isSET(event)) {
			return;
		}

		Object newlySelectedPerspective = event.getProperty(EventTags.NEW_VALUE);
		if (newlySelectedPerspective instanceof MPerspective) {
			MPerspective perspectiveToBeCloned = (MPerspective) newlySelectedPerspective;

			MWindow topLevelWindow = modelService.getTopLevelWindowFor(perspectiveToBeCloned);
			
			// try to find already existing snippet
			if (null == modelService.findSnippet(topLevelWindow, perspectiveToBeCloned.getElementId())) {
				// clone perspective in case there is no snippet yet
				modelService.cloneElement(perspectiveToBeCloned, topLevelWindow);
			}
		}
	}
}
