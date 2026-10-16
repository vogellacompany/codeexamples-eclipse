package com.vogella.rcp.svg.currentcolor;

import java.util.Optional;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.image.SVGFileFormat;

/**
 * Sets the color SVG images use for {@code currentColor} before the workbench
 * window is rendered.
 * <p>
 * The color comes from the login dialog, which preselects the color of the
 * {@code -currentColor RRGGBB} program argument if one is given, otherwise
 * SWT's current value (black, or the {@code -Dswt.svg.currentColor=RRGGBB}
 * system property).
 * </p>
 * <p>
 * The Eclipse CSS property {@code swt-svg-current-color} is not available yet,
 * so the color is set with the SWT method
 * {@code SVGFileFormat.setCurrentColor(RGB)}. The color is global and is used
 * when an image is rasterized, so it must be set before the first SVG icon is
 * created.
 * </p>
 */
public class LifeCycle {

	static final String CURRENT_COLOR_ARGUMENT = "-currentColor";

	/** Context key of the name entered in the login dialog. */
	public static final String USER = "com.vogella.rcp.svg.currentcolor.user";

	@SuppressWarnings("restriction")
	@PostContextCreate
	void login(IEclipseContext workbenchContext) {
		RGB initialColor = colorFromArguments().orElseGet(SVGFileFormat::getCurrentColor);

		LoginDialog dialog = new LoginDialog(null, initialColor);
		if (dialog.open() != Window.OK) {
			// no workbench window exists yet, so ending the process is the simplest way to cancel the start
			System.exit(0);
		}

		SVGFileFormat.setCurrentColor(dialog.getCurrentColor());
		workbenchContext.set(USER, dialog.getUser());
	}

	private Optional<RGB> colorFromArguments() {
		String[] args = Platform.getApplicationArgs();
		for (int i = 0; i < args.length - 1; i++) {
			if (CURRENT_COLOR_ARGUMENT.equalsIgnoreCase(args[i])) {
				Optional<RGB> color = CurrentColor.parse(args[i + 1]);
				if (color.isEmpty()) {
					ILog.of(getClass()).warn("Ignoring " + CURRENT_COLOR_ARGUMENT + " " + args[i + 1]
							+ ", expected a color like 1E88E5");
				}
				return color;
			}
		}
		return Optional.empty();
	}
}
