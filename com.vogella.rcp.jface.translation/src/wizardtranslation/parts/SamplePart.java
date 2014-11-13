/*******************************************************************************
 * Copyright (c) 2010 - 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <lars.Vogel@gmail.com> - Bug 419770
 *******************************************************************************/
package wizardtranslation.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import wizardtranslation.wizard.SampleWizard;

public class SamplePart {

	@Inject
	private Shell shell;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		button.setText("Show Sample Wizard");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WizardDialog wizardDialog = new WizardDialog(shell,
						new SampleWizard());
				wizardDialog.open();
			}
		});
		
	}
}
