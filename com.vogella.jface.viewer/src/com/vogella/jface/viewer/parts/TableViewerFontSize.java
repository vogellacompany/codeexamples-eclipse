
package com.vogella.jface.viewer.parts;

import javax.annotation.PostConstruct;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class TableViewerFontSize {

	@PostConstruct
	public void postConstruct(Composite parent) {

		ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources(), parent);

		TreeViewer viewer = new TreeViewer(parent);
		viewer.setContentProvider(new TreeContentProvider());
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setFont(resourceManager.createFont(FontDescriptor.createFrom("Arial", 32, SWT.ITALIC)));

		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		viewerColumn.getColumn().setWidth(300);
		viewerColumn.getColumn().setText("Names");
		viewerColumn.setLabelProvider(new ColumnLabelProvider());

		viewer.setInput(new String[] { "Simon Scholz", "Lars Vogel", "Dirk Fauth", "Wim Jongman", "Tom Schindl" });

		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

}
