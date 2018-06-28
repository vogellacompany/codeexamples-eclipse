package com.vogella.swt.widgets.customwidgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Instances of this class represent a composite with two layers
 * 
 * The layers can contain arbitrary layouts and widgets
 * <p>
 * 
 * <ul>
 * <li>{@link #getContentComposite()} allows to retrieve the Composite for the
 * main content</li>
 * <li>{@link #getOverlayComposite()} Retrieve the Composite for the
 * overlay</li>
 * </ul>
 * 
 * </p>
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>TOP, BOTTOM, LEFT, RIGHT</dd>
 * </dl>
 * <p>
 * Note: Only one of the styles TOP, BOTTOM, RIGHT, and CENTER may be specified.
 * </p>
 * <p>
 * </p>
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */

public class LayeredComposite extends Composite {
	private Color backgroundColor;
	private Composite mainContentArea;
	private Composite overlayComposite;

	public LayeredComposite(Composite parent, int style) {
		super(parent, style);
		// Uncommend to test if the clients are fill the full area
		// backgroundColor = new Color(null, 255, 0, 0);
		// setBackground(backgroundColor);
		// this.addDisposeListener(e -> {
		// backgroundColor.dispose();
		// });

		FormLayout formLayout = new FormLayout();
		this.setLayout(formLayout);
		mainContentArea = new Composite(this, SWT.NONE);
		mainContentArea.setLayout(new FillLayout());
		mainContentArea.setLayoutData(fillCompleteArea());

		overlayComposite = new Composite(this, SWT.NONE);
		overlayComposite.setLayout(new FillLayout());
		overlayComposite.moveAbove(null);
		FormData formDataUpperLayer = definePosition(style);
		overlayComposite.setLayoutData(formDataUpperLayer);

	}

	/**
	 * Retrieve the main Composite. Content of this composite maybe overlayed by
	 * the overlayComposite
	 * 
	 * @return Composite the Composite that represents the main content
	 */
	public Composite getContentComposite() {
		return mainContentArea;
	}

	/**
	 * Retrieve the overlay Composite.
	 * 
	 * @return Composite the Composite that represents the main content
	 */

	public Composite getOverlayComposite() {
		return overlayComposite;
	}

	private FormData definePosition(int style) {
		checkStyle(style);
		FormData formDataUpperLayer = new FormData();
		if (style == SWT.BOTTOM) {
			formDataUpperLayer.bottom = new FormAttachment(100, 0);
			formDataUpperLayer.left = new FormAttachment(0, 0);
			formDataUpperLayer.right = new FormAttachment(100, 0);
		} else if (style == SWT.TOP) {
			formDataUpperLayer.top = new FormAttachment(0, 0);
			formDataUpperLayer.left = new FormAttachment(0, 0);
			formDataUpperLayer.right = new FormAttachment(100, 0);
		} else if (style == SWT.RIGHT) {
			formDataUpperLayer.top = new FormAttachment(0, 0);
			formDataUpperLayer.right = new FormAttachment(100, 0);
		} else if (style == SWT.LEFT) {
			formDataUpperLayer.top = new FormAttachment(0, 0);
			formDataUpperLayer.left = new FormAttachment(0, 0);
			formDataUpperLayer.right = new FormAttachment(100, 0);
		}

		return formDataUpperLayer;
	}

	/**
	 * Ensure that only valid styles are passed to the container
	 * 
	 * @param style
	 */
	static void checkStyle(int style) {
		if ((style & (SWT.BOTTOM | SWT.TOP | SWT.RIGHT | SWT.LEFT)) == 0) {
			throw new IllegalArgumentException("Style must be one of SWT.BOTTOM, SWT.TOP, SWT.RIGHT, SWT.LEFT");
		}
	}

	private FormData fillCompleteArea() {
		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 0);
		formData.bottom = new FormAttachment(100, 0);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		return formData;
	}

}
