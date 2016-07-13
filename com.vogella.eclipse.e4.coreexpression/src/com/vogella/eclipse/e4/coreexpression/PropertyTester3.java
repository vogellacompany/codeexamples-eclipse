package com.vogella.eclipse.e4.coreexpression;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class PropertyTester3 extends PropertyTester {

	public PropertyTester3() {
		System.out.println("Hubbabuba");
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		
		System.out.println("Hubbabuba test");
		IWorkbench workbench =null;
		try {
			workbench = PlatformUI.getWorkbench();
		} catch (IllegalStateException ex) {
			return true;
		}
		if (workbench== null) {
			return true;
		}
		
		IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return true;
		}
		
		System.out.println("Hubbabuba test spaeter");
		
//		MWindow window = activeWorkbenchWindow.getService(MWindow.class);
//		IEclipseContext context = window.getContext();
//		
//		EModelService eModelService = context.get(EModelService.class);
//		MPerspective activePerspective = eModelService.getActivePerspective(window);
//		
//		if (window!=null) {
//			System.out.println(window);
//			List<MPerspectiveStack> findElements = eModelService.findElements(app, null, MPerspectiveStack.class, null);
//			if (findElements.size()>0) {
//			}
//		}
		return true;
	}

}
