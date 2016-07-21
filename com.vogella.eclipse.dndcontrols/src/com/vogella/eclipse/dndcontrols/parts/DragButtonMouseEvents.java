package com.vogella.eclipse.dndcontrols.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class DragButtonMouseEvents {
  public static void main(String[] args) {
    Display display = new Display();
    final Shell shell = new Shell(display);
    final Composite composite = new Composite(shell, SWT.NONE);
    composite.setEnabled(false);
    composite.setLayout(new FillLayout());
    Button button = new Button(composite, SWT.PUSH);
    button.setText("Button");
    composite.pack();
    composite.setLocation(10, 10);
    final Point[] offset = new Point[1];
    Listener listener = new Listener() {
      public void handleEvent(Event event) {
        switch (event.type) {
        case SWT.MouseDown:
          Rectangle rect = composite.getBounds();
          if (rect.contains(event.x, event.y)) {
            Point pt1 = composite.toDisplay(0, 0);
            Point pt2 = shell.toDisplay(event.x, event.y);
            offset[0] = new Point(pt2.x - pt1.x, pt2.y - pt1.y);
          }
          break;
        case SWT.MouseMove:
          if (offset[0] != null) {
            Point pt = offset[0];
            composite.setLocation(event.x - pt.x, event.y - pt.y);
          }
          break;
        case SWT.MouseUp:
          offset[0] = null;
          break;
        }
      }
    };
    shell.addListener(SWT.MouseDown, listener);
    shell.addListener(SWT.MouseUp, listener);
    shell.addListener(SWT.MouseMove, listener);
    shell.setSize(300, 300);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }
}