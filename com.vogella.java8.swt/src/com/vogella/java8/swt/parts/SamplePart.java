package com.vogella.java8.swt.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.vogella.java8.swt.factory.SWTLambdaHandlers;

public class SamplePart {

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Button buttonWithLambdaListener = new Button(parent, SWT.PUSH);
		buttonWithLambdaListener.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		buttonWithLambdaListener.setText("Lambda");
		buttonWithLambdaListener.addSelectionListener(SWTLambdaHandlers.widgetSelected(e -> {
			System.out.println("Button has been clicked!");
		}));
		
	}
}
