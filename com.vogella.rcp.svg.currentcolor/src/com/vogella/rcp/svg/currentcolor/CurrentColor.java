package com.vogella.rcp.svg.currentcolor;

import java.util.Optional;

import org.eclipse.swt.graphics.RGB;

/**
 * Converts between {@link RGB} and the {@code RRGGBB} notation, with an optional
 * leading {@code #}, that the {@code -currentColor} program argument and SWT's
 * {@code swt.svg.currentColor} system property use.
 */
public final class CurrentColor {

	private CurrentColor() {
	}

	public static Optional<RGB> parse(String value) {
		if (value == null || !value.trim().matches("#?[0-9A-Fa-f]{6}")) {
			return Optional.empty();
		}
		int rgb = Integer.parseInt(value.trim().replaceFirst("^#", ""), 16);
		return Optional.of(new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF));
	}

	public static String toHex(RGB rgb) {
		return "#%02X%02X%02X".formatted(rgb.red, rgb.green, rgb.blue);
	}
}
