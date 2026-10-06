package com.vogella.css.fonts.parts;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Shows labels styled by CSS font-weight, font-size and font rules on Windows,
 * each with the font the CSS engine actually resolved, plus an editor to change
 * the rules live.
 */
public class SamplePart {

	private static final String CSS_URL = "platform:/plugin/com.vogella.css.fonts/css/default.css";
	private static final String CSS_CLASS_KEY = "org.eclipse.e4.ui.css.CssClassName";
	private static final String SAMPLE = "Hamburgefonstiv 0123";

	private final List<Label[]> samples = new ArrayList<>();
	private ScrolledComposite scroll;
	private Text editor;
	private Label status;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		SashForm sash = new SashForm(parent, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(GridData.FILL_BOTH));

		createEditor(sash);
		createSamples(sash);
		sash.setWeights(22, 78);

		parent.getDisplay().asyncExec(this::updateInfos);
	}

	private void createEditor(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new GridLayout(2, false));
		editor = new Text(c, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		editor.setText(readStyleSheet());
		editor.addListener(SWT.KeyDown, e -> {
			if (e.keyCode == SWT.CR && (e.stateMask & SWT.MOD1) != 0) {
				e.doit = false;
				applyCss();
			}
		});
		Button apply = new Button(c, SWT.PUSH);
		apply.setText("Apply (Ctrl+Enter)");
		apply.addListener(SWT.Selection, e -> applyCss());
		status = new Label(c, SWT.NONE);
		status.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void createSamples(Composite parent) {
		scroll = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite content = new Composite(scroll, SWT.NONE);
		content.setLayout(new GridLayout(2, false));

		String[] families = { "", "bahnschrift", "calibri" };
		String[] familyTitles = { "inherited family (Segoe UI)", "Bahnschrift", "Calibri" };

		Group weights = group(content, "font-weight", 4);
		weights.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 3));
		header(weights, "rule", familyTitles);
		for (String cls : new String[] { "w100", "w200", "w300", "w400", "w500", "w600", "w700", "w800", "w900",
				"w-normal", "w-bold", "w-bolder", "w-lighter", "w-light", "w-medium", "w-semibold" }) {
			row(weights, cls, families);
		}

		Group sizes = group(content, "font-size", 2);
		header(sizes, "rule", new String[] { "inherited family" });
		for (String cls : new String[] { "s-unitless", "s-pt", "s-px", "s-em", "s-pct", "s-larger", "s-smaller" }) {
			row(sizes, cls, new String[] { "" });
		}

		Group shorthand = group(content, "font shorthand", 2);
		header(shorthand, "rule", new String[] { "result" });
		for (String cls : new String[] { "sh-weight", "sh-italic", "sh-relative", "sh-legacy" }) {
			row(shorthand, cls, new String[] { "" });
		}

		Group hierarchy = group(content, "Hierarchy", 2);
		header(hierarchy, "rule", new String[] { "result" });
		for (String cls : new String[] { "h1", "h2", "h3", "body", "caption" }) {
			row(hierarchy, cls, new String[] { "" });
		}

		scroll.setContent(content);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		scroll.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private static Group group(Composite parent, String title, int columns) {
		Group g = new Group(parent, SWT.NONE);
		g.setText(title);
		g.setLayout(new GridLayout(columns, false));
		g.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		return g;
	}

	private static void header(Composite parent, String first, String[] titles) {
		new Label(parent, SWT.NONE).setText(first);
		for (String t : titles) {
			new Label(parent, SWT.NONE).setText(t);
		}
	}

	private void row(Composite parent, String cls, String[] families) {
		Label rule = new Label(parent, SWT.NONE);
		rule.setText("." + cls);
		rule.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, false, false));
		for (String family : families) {
			Composite cell = new Composite(parent, SWT.NONE);
			GridLayout l = new GridLayout(1, false);
			l.marginHeight = l.marginWidth = 2;
			l.verticalSpacing = 0;
			cell.setLayout(l);
			cell.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			Label sample = new Label(cell, SWT.NONE);
			sample.setText(SAMPLE);
			sample.setData(CSS_CLASS_KEY, family.isEmpty() ? cls : cls + " " + family);
			Label info = new Label(cell, SWT.NONE);
			info.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
			info.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			samples.add(new Label[] { sample, info });
		}
	}

	private void applyCss() {
		Display display = editor.getDisplay();
		CSSEngine engine = WidgetElement.getEngine(display);
		try {
			engine.reset();
			engine.parseStyleSheet(new StringReader(editor.getText()));
			engine.applyStyles(editor.getShell(), true);
			status.setText("Applied");
		} catch (IOException | RuntimeException ex) {
			status.setText("Error: " + ex.getMessage());
		}
		display.asyncExec(this::updateInfos);
	}

	private void updateInfos() {
		for (Label[] pair : samples) {
			Label sample = pair[0];
			if (sample.isDisposed()) {
				return;
			}
			FontData fd = sample.getFont().getFontData()[0];
			GC gc = new GC(sample);
			int width;
			try {
				gc.setFont(sample.getFont());
				width = gc.textExtent(SAMPLE).x;
			} finally {
				gc.dispose();
			}
			pair[1].setText(String.format("%s %.1fpt %s %dpx%s", fd.getName(), fd.height, style(fd), width,
					platformDescription(fd)));
		}
		Composite content = (Composite) scroll.getContent();
		content.layout(true, true);
		scroll.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private static String style(FontData fd) {
		int s = fd.getStyle();
		if (s == SWT.NORMAL) {
			return "normal";
		}
		return (((s & SWT.BOLD) != 0 ? "bold " : "") + ((s & SWT.ITALIC) != 0 ? "italic" : "")).trim();
	}

	// The win32 LOGFONT names the face GDI was asked for and its weight, read
	// reflectively because FontData.data only exists in the win32 SWT fragment.
	private static String platformDescription(FontData fd) {
		try {
			Object logFont = FontData.class.getField("data").get(fd);
			char[] face = (char[]) logFont.getClass().getField("lfFaceName").get(logFont);
			int weight = logFont.getClass().getField("lfWeight").getInt(logFont);
			int len = 0;
			while (len < face.length && face[len] != 0) {
				len++;
			}
			return " | " + new String(face, 0, len) + " " + weight;
		} catch (ReflectiveOperationException | ClassCastException e) {
			return "";
		}
	}

	private static String readStyleSheet() {
		try (InputStream in = URI.create(CSS_URL).toURL().openStream()) {
			return new String(in.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			return "/* could not read " + CSS_URL + ": " + e.getMessage() + " */";
		}
	}
}
