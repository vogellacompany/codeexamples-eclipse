package com.vogella.eclipse.dndcontrols;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.vogella.eclipse.dndcontrols.parts.View;

public class View1 extends View 
{

	@Override
	protected Control[] createChildren(final Composite parent)
	{
		final Label label = new Label(parent, SWT.BORDER);
		label.setText("A Plain Label");

		final Button button = new Button(parent, SWT.PUSH);
		button.setText("And a button");

		return new Control[] { label, button };
	}
}