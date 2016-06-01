package com.vogella.eclipse.e3.coreexpression;

import org.eclipse.core.expressions.PropertyTester;

public class PropertyTester2 extends PropertyTester {

	public PropertyTester2() {
		System.out.println("Hubbabuba");
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		return false;
	}

}
