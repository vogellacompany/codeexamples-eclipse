package com.vogella.rcp.svg.currentcolor.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import jakarta.annotation.PostConstruct;

/**
 * Explains the ways to set the {@code currentColor} of SVG icons.
 */
public class InfoPart {

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout());
		Text text = new Text(parent, SWT.MULTI | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		text.setText("""
				SVG icons that use fill="currentColor" or stroke="currentColor" are rasterized \
				with one global color, black by default.

				This application sets it in three ways, before the workbench window is created:

				1. Program argument: -currentColor 1E88E5
				   The lifecycle class reads it and preselects it in the login dialog.
				   Leave out the #, the launcher ini and shells treat it as a comment.

				2. Login dialog: the color picked there is passed to
				   SVGFileFormat.setCurrentColor(RGB) before the window is rendered.

				3. System property: -Dswt.svg.currentColor=1E88E5
				   SWT reads it itself; it is preselected when no program argument is given.

				Images keep the color they were rasterized with, so the color is set once at \
				startup. The CSS property swt-svg-current-color (Eclipse platform UI pull request \
				4497) will let a theme set the same value.
				""");
	}
}
