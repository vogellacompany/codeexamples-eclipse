package com.vogella.java8.swt.parts;

import static com.vogella.java8.swt.factory.SWTLambdaHandlers.widgetSelected;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class SamplePart {

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		button.setText("Lambda");
		button.addSelectionListener(widgetSelected(e -> {
			System.out.println("Button has been clicked!");
		}));

	}
}
