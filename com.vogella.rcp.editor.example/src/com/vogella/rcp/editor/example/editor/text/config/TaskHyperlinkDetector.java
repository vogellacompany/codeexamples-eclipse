package com.vogella.rcp.editor.example.editor.text.config;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetectorExtension;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetectorExtension2;

public class TaskHyperlinkDetector implements IHyperlinkDetector, IHyperlinkDetectorExtension, IHyperlinkDetectorExtension2 {

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		return null;
	}

	@Override
	public int getStateMask() {
		return 0;
	}

	@Override
	public void dispose() {
	}

}
