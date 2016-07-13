package com.vogella.eclipse.dndcontrols.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Abstract class with adds a DropTarget to a composite.
 * Can be used as base class for view which can add DragSources to their controls
 *
 */
public abstract class View {
	
	@PostConstruct
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout());
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Control[] children = createChildren(composite);

		for (Control control : children) {
			addDragListener(control);
		}

		addDropListener(composite);
	}

	private void addDragListener(Control control) {
		LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();

		DragSourceAdapter dragAdapter = new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				transfer.setSelection(new StructuredSelection(control));
			}
		};

		DragSource dragSource = new DragSource(control, DND.DROP_MOVE | DND.DROP_COPY);
		dragSource.setTransfer(new Transfer[] { transfer });
		dragSource.addDragListener(dragAdapter);
	}

	private void addDropListener(Composite parent) {
		LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();

		DropTargetAdapter dragAdapter = new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				Control droppedObj = (Control) ((StructuredSelection) transfer.getSelection()).getFirstElement();

				// Get the existing parent of the dragged control
				Composite oldParent = droppedObj.getParent();

				if (oldParent == parent) {
					return;
				}

				// handle the drop
				if (droppedObj instanceof Label) {
					Label droppedLabel = (Label) droppedObj;
					droppedLabel.setParent(parent); // Change parent
				}

				if (droppedObj instanceof Button) {
					Button droppedButton = (Button) droppedObj;
					droppedButton.setParent(parent); // Change parent
				}

				// request a layout pass
				oldParent.requestLayout();
				// If you change that to layout the layout will be correct
				parent.requestLayout();
				parent.getParent().requestLayout();
			}
		};

		DropTarget dropTarget = new DropTarget(parent, DND.DROP_MOVE | DND.DROP_COPY);
		dropTarget.setTransfer(new Transfer[] { transfer });
		dropTarget.addDropListener(dragAdapter);
	}

	@Focus
	public void setFocus() {

	}

	abstract protected Control[] createChildren(Composite parent);
}