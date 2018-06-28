package com.vogella.swt.widgets.customwidgets;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class PictureLabel extends Canvas {
	private Color white;

	PictureLabel(Composite parent, int style) {
		super(parent, style);
		white = new Color(null, 255, 255, 255);
		setBackground(white);
	}

}
