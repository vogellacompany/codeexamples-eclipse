package com.vogella.rcp.svg.currentcolor.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.internal.image.SVGFileFormat;
import org.eclipse.swt.widgets.Shell;

import com.vogella.rcp.svg.currentcolor.CurrentColor;

public class AboutHandler {
	@SuppressWarnings("restriction")
	@Execute
	public void execute(Shell shell) {
		MessageDialog.openInformation(shell, "About",
				"SVG currentColor example\n\nThe icons use " + CurrentColor.toHex(SVGFileFormat.getCurrentColor()));
	}
}
