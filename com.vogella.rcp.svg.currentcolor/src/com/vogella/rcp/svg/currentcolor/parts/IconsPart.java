package com.vogella.rcp.svg.currentcolor.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.image.SVGFileFormat;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.vogella.rcp.svg.currentcolor.CurrentColor;
import com.vogella.rcp.svg.currentcolor.Icons;
import com.vogella.rcp.svg.currentcolor.LifeCycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Named;

/**
 * Shows the bundled SVG icons, which all use {@code currentColor}, with the color
 * set at login.
 */
public class IconsPart {

	private final List<Image> images = new ArrayList<>();

	@SuppressWarnings("restriction")
	@PostConstruct
	public void createControls(Composite parent, @Named(LifeCycle.USER) String user) {
		GridLayoutFactory.swtDefaults().numColumns(2).margins(10, 10).spacing(10, 8).applyTo(parent);

		Label header = new Label(parent, SWT.WRAP);
		header.setText("Logged in as " + user + ". SVG currentColor is "
				+ CurrentColor.toHex(SVGFileFormat.getCurrentColor())
				+ ", so the icons in the menu, the toolbar, the part tabs and below use it.");
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(400, SWT.DEFAULT).applyTo(header);

		for (String name : Icons.NAMES) {
			Label icon = new Label(parent, SWT.NONE);
			Image image = Icons.create(parent.getDisplay(), name);
			if (image != null) {
				images.add(image);
				icon.setImage(image);
			}
			new Label(parent, SWT.NONE).setText(name + ".svg");
		}
	}

	@PreDestroy
	public void dispose() {
		images.forEach(Image::dispose);
	}
}
