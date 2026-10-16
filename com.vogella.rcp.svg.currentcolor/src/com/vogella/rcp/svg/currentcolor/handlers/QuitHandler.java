package com.vogella.rcp.svg.currentcolor.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;

public class QuitHandler {
	@Execute
	public void execute(IWorkbench workbench) {
		workbench.close();
	}
}
