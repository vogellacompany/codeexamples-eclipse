 
package com.vogella.rcp.perspective.addpart.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ContributePerspective {
	@Inject
	public ContributePerspective() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		label.setText("This MPart has been contributed by an different model fragment from a different plguin.");
		
	}
	
	
	
	
}