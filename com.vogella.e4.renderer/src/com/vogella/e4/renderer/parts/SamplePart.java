package com.vogella.e4.renderer.parts;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.model.application.ui.menu.MTrimContribution;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

public class SamplePart {

	@PostConstruct
	public void createComposite(Composite parent, IPresentationEngine presentationEngine, EModelService modelService, MApplication app, IEclipseContext context) {
		List<MTrimContribution> trimContributions = app.getTrimContributions();
		
		Optional<MTrimElement> findAny = trimContributions.stream().flatMap(tbc -> tbc.getChildren().stream()).filter(tbe -> "com.vogella.e4.renderer.toolbar".equals(tbe.getElementId())).findAny();

		findAny.ifPresent(uiElement -> {
			ToolBar toolBar = new ToolBar(parent, SWT.NONE);
			presentationEngine.createGui(uiElement, toolBar, context);
		});
	}

}