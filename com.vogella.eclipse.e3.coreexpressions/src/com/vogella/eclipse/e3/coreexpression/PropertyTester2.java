package com.vogella.eclipse.e3.coreexpression;

import org.eclipse.core.expressions.PropertyTester;

public class PropertyTester2 extends PropertyTester {

	public PropertyTester2() {
		System.out.println("Hubbabuba");
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		System.out.println("Hubbabuba test");
//		MApplication app = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(MApplication.class);
//		MWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(MWindow.class);
//		IEclipseContext context = app.getContext();
//		EModelService eModelService = context.get(EModelService.class);
//		
//		if (window!=null) {
//			System.out.println(window);
//			List<MPerspectiveStack> findElements = eModelService.findElements(app, null, MPerspectiveStack.class, null);
//			if (findElements.size()>0) {
////				findElements.get(0).
//			}
//		}
		return true;
	}

}
