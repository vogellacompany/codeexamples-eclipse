package com.vogella.eclipse.dndcontrols.parts;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

public class FormsPart {
	public static final String ID = "de.vogella.rcp.intro.forms.view";

	private TableViewer viewer;

	@PostConstruct
	public void createPartControl(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		ScrolledForm form = toolkit.createScrolledForm(parent);
		toolkit.decorateFormHeading(form.getForm());

		form.setText("Eclipse Forms API Example");

		createFirstSection(parent, toolkit);
		createSecondSection(form, toolkit);
	}

	private void createFirstSection(Composite parent, FormToolkit toolkit) {
		// Creating the Screen
		Section section = toolkit.createSection(parent, Section.DESCRIPTION | Section.TITLE_BAR);
		section.setText("Section 1 for demonstration"); //$NON-NLS-1$
		section.setDescription("This demonstrates the usage of section");
		// Composite for storing the data
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout  layout = new GridLayout(2, true);
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		Table t = toolkit.createTable(client, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		t.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		Button b = toolkit.createButton(client, "Do something", SWT.PUSH); //$NON-NLS-1$
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		b.setLayoutData(gd);
		section.setClient(client);
		viewer = new TableViewer(t);

		viewer.setContentProvider(ArrayContentProvider.getInstance());

		TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		viewerColumn.getColumn().setWidth(100);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return element.toString();
			};

		}

		);
		viewer.setInput(Arrays.asList("A", "B", "C"));
	}
	
	private void createSecondSection( ScrolledForm form, FormToolkit toolkit) {
		ExpandableComposite ec = toolkit.createExpandableComposite(form.getBody(), 
			     ExpandableComposite.TREE_NODE|
			     ExpandableComposite.CLIENT_INDENT);
			 ec.setText("Expandable Composite title");
			 String ctext = "We will now create a somewhat long text so that "+
			 "we can use it as content for the expandable composite. "+
			 "Expandable composite is used to hide or show the text using the " +
			 "toggle control";
			 Label client = toolkit.createLabel(ec, ctext, SWT.WRAP);
			 ec.setClient(client);
			 TableWrapData  td = new TableWrapData();
			 td.colspan = 2;
			 ec.setLayoutData(td);
			 ec.addExpansionListener(new ExpansionAdapter() {
			  @Override
			public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });

		
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
