package com.vogella.java8.swt.factory;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class SWTLambdaHandlers {
	public static SelectionListener widgetSelected(final Consumer<SelectionEvent> c) {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				c.accept(e);
			}
		};
	}
}
