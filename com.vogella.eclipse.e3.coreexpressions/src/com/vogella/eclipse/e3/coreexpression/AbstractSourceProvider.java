package com.vogella.eclipse.e3.coreexpression;

import java.util.HashMap;
import java.util.Map;

public class AbstractSourceProvider extends org.eclipse.ui.AbstractSourceProvider {

	private static final String SOME_VAR = "com.example.someVariable";
	
	public AbstractSourceProvider() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Map getCurrentState() {
		System.out.println("getCurrentState");
		HashMap<String, String> values = new HashMap<String, String>();
		values.put(SOME_VAR, "someValue");
		return values;
	}

	@Override
	public String[] getProvidedSourceNames() {
		System.out.println("getProvidedSourceNames");
		return new String[] { SOME_VAR };
	}

}
