package com.vogella.rcp.svg.currentcolor;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.ILog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * The SVG icons of this bundle, which all use {@code currentColor}.
 */
public final class Icons {

	public static final String[] NAMES = { "home", "folder", "save", "star", "settings", "info", "exit" };

	private Icons() {
	}

	/**
	 * Creates the icon with the {@code currentColor} that is set now; the caller
	 * disposes it.
	 */
	public static Image create(Display display, String name) {
		try (InputStream in = Icons.class.getResourceAsStream("/icons/" + name + ".svg")) {
			return in != null ? new Image(display, in) : null;
		} catch (IOException e) {
			ILog.of(Icons.class).error("Could not load icon " + name, e);
			return null;
		}
	}
}
