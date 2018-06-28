package com.vogella.swt.widgets.customwidgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TestLayeredComposite {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		FillLayout layout = new FillLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		shell.setLayout(layout);
		LayeredComposite composite = new LayeredComposite(shell, SWT.BOTTOM);

		// Build the main area of the user interface
		Composite mainComposite = new Composite(composite.getContentComposite(), SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, false));

		for (int i = 0; i < 20; i++) {
			Button button = new Button(mainComposite, SWT.CHECK);
			button.setText("Testing " + i);

		}

		// Build the top layer of the user interface
		Composite layeredComposite = new Composite(composite.getOverlayComposite(), SWT.NONE);
		layeredComposite.setLayout(new GridLayout(1, true));
		for (int i = 0; i < 3; i++) {
			Button button = new Button(layeredComposite, SWT.PUSH);
			button.setText("Control Center" + i);
		}

		shell.setSize(800, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}
}
