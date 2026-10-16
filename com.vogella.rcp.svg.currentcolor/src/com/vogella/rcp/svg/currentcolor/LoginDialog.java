package com.vogella.rcp.svg.currentcolor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.image.SVGFileFormat;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Login screen which also lets the user pick the color of the {@code currentColor}
 * SVG icons. The preview rasterizes the icons again whenever the color changes.
 */
@SuppressWarnings("restriction")
public class LoginDialog extends TitleAreaDialog {

	private record NamedColor(String name, RGB rgb) {
	}

	private static final List<NamedColor> PRESETS = List.of(
			new NamedColor("Black", new RGB(0, 0, 0)),
			new NamedColor("Light gray (dark theme)", new RGB(0xCC, 0xCC, 0xCC)),
			new NamedColor("Blue", new RGB(0x1E, 0x88, 0xE5)),
			new NamedColor("Green", new RGB(0x2E, 0x7D, 0x32)),
			new NamedColor("Orange", new RGB(0xEF, 0x6C, 0x00)));

	private final List<NamedColor> colors = new ArrayList<>(PRESETS);
	private final List<Image> previewImages = new ArrayList<>();
	private final List<Label> previewLabels = new ArrayList<>();

	private RGB currentColor;
	private String user = "";

	private Text userText;
	private Combo colorCombo;
	private Label swatch;

	public LoginDialog(Shell parentShell, RGB initialColor) {
		super(parentShell);
		this.currentColor = initialColor;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Login");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Login");
		setMessage("Log in and choose the color of the SVG icons");
		Composite area = (Composite) super.createDialogArea(parent);

		Composite container = new Composite(area, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(container);
		GridLayoutFactory.swtDefaults().numColumns(3).margins(10, 10).applyTo(container);

		new Label(container, SWT.NONE).setText("User:");
		userText = new Text(container, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(userText);
		userText.addModifyListener(e -> updateOkButton());

		new Label(container, SWT.NONE).setText("Password:");
		Text passwordText = new Text(container, SWT.BORDER | SWT.PASSWORD);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(passwordText);

		new Label(container, SWT.NONE).setText("Icon color:");
		colorCombo = new Combo(container, SWT.READ_ONLY);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(colorCombo);
		colorCombo.addListener(SWT.Selection, e -> {
			currentColor = colors.get(colorCombo.getSelectionIndex()).rgb();
			updatePreview();
		});

		Button chooseButton = new Button(container, SWT.PUSH);
		chooseButton.setText("Choose...");
		chooseButton.addListener(SWT.Selection, e -> chooseColor());

		new Label(container, SWT.NONE);
		swatch = new Label(container, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).hint(SWT.DEFAULT, 20).span(2, 1).applyTo(swatch);

		new Label(container, SWT.NONE).setText("Preview:");
		Composite preview = new Composite(container, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(preview);
		GridLayoutFactory.fillDefaults().numColumns(Icons.NAMES.length).spacing(12, 0).applyTo(preview);
		for (int i = 0; i < Icons.NAMES.length; i++) {
			previewLabels.add(new Label(preview, SWT.NONE));
		}

		selectColor(currentColor);
		return area;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setText("Login");
		updateOkButton();
	}

	private void chooseColor() {
		ColorDialog dialog = new ColorDialog(getShell());
		dialog.setRGB(currentColor);
		RGB rgb = dialog.open();
		if (rgb != null) {
			selectColor(rgb);
		}
	}

	/**
	 * Selects the preset with this color, or adds it as a custom entry, for example
	 * for a color passed with {@code -currentColor}.
	 */
	private void selectColor(RGB rgb) {
		int index = -1;
		for (int i = 0; i < colors.size(); i++) {
			if (colors.get(i).rgb().equals(rgb)) {
				index = i;
				break;
			}
		}
		if (index < 0) {
			NamedColor custom = new NamedColor("Custom " + CurrentColor.toHex(rgb), rgb);
			if (colors.size() > PRESETS.size()) {
				colors.set(PRESETS.size(), custom);
			} else {
				colors.add(custom);
			}
			index = PRESETS.size();
		}
		colorCombo.setItems(colors.stream().map(NamedColor::name).toArray(String[]::new));
		colorCombo.select(index);
		currentColor = rgb;
		updatePreview();
	}

	private void updatePreview() {
		swatch.setBackground(new Color(currentColor));
		swatch.setToolTipText(CurrentColor.toHex(currentColor));

		// SVGs are rasterized with the color that is set when the image is created
		SVGFileFormat.setCurrentColor(currentColor);
		previewImages.forEach(Image::dispose);
		previewImages.clear();
		for (int i = 0; i < Icons.NAMES.length; i++) {
			Image image = Icons.create(getShell().getDisplay(), Icons.NAMES[i]);
			if (image != null) {
				previewImages.add(image);
			}
			previewLabels.get(i).setImage(image);
		}
		previewLabels.getFirst().getParent().layout();
	}

	private void updateOkButton() {
		Button ok = getButton(IDialogConstants.OK_ID);
		if (ok != null) {
			ok.setEnabled(!userText.getText().isBlank());
		}
	}

	@Override
	protected void okPressed() {
		user = userText.getText().trim();
		super.okPressed();
	}

	@Override
	public boolean close() {
		previewImages.forEach(Image::dispose);
		previewImages.clear();
		return super.close();
	}

	public RGB getCurrentColor() {
		return currentColor;
	}

	public String getUser() {
		return user;
	}
}
