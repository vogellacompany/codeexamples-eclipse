package com.vogella.eclipse.dndcontrols.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class View2 extends View
{

	@Override
	protected Control[] createChildren(final Composite parent)
	{
		final Label label = new Label(parent, SWT.BORDER);
		label.setText("Another Label");

		return new Control[] { label };
	}

}