package com.vogella.css.fonts;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.ILog;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Loads the fonts shipped in the bundle's fonts folder for this process, so the
 * CSS can use them without installing them.
 */
public class LifeCycle {

	@PostContextCreate
	void loadBundledFonts() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		Enumeration<URL> fonts = bundle.findEntries("fonts", "*.ttf", false);
		if (fonts == null) {
			return;
		}
		// Runs before the first window is rendered, so the CSS engine already sees the fonts
		Display display = Display.getDefault();
		for (URL font : Collections.list(fonts)) {
			try {
				String path = new File(FileLocator.toFileURL(font).getPath()).getAbsolutePath();
				if (!display.loadFont(path)) {
					ILog.of(getClass()).warn("Could not load font " + path);
				}
			} catch (IOException e) {
				ILog.of(getClass()).error("Could not extract font " + font, e);
			}
		}
	}
}
