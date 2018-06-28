package com.vogella.swt.widgets.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ManualLayout {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);

		for (int i = 0; i <= 10; i++) {
			Button button = new Button(shell, SWT.PUSH);
			button.setText("Button " + 1);
			button.setBounds(i*10, i*10, 200, 200);
			button.moveAbove(null);
			button.addSelectionListener(new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					Control control = (Control) e.widget;
					control.moveAbove(null);
				}
				
			});
		}
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
