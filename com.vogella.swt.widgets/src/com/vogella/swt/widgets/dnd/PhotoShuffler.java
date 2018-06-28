package com.vogella.swt.widgets.dnd;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class PhotoShuffler {

	public static void main(String[] args) {
		// setup the SWT window
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("Photo Shuffler");

		// initialize a parent composite with a grid layout manager
		Composite parent = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginWidth=20;
		parent.setLayout(gridLayout);

		// determine the path where the pictures are stored
		String path = System.getProperty("user.dir") + "/images/";
		// initialize an array with the photograph names

		File imageDir= new File(path);
		
		// loop over the photo array and establish all listeners
		List<File> files = Arrays.stream(imageDir.listFiles())
					 	.filter(f -> f.isFile() && f.getName().endsWith(".png"))
						.collect(Collectors.toList());
		
		for (File file : files) {
			// labels serve as containers for the images
			Label label = new Label(parent, SWT.NONE);
			Image img = new Image(display,file.getAbsolutePath());
			label.setImage(img);

			// enable each label to be draggable
			DragSource source = new DragSource(label, DND.DROP_NONE);
			source.setTransfer(TextTransfer.getInstance()); // varargs are supported as of 4.7
			// add a drag listener
			source.addDragListener(new MyDragSourceListener(parent, source));

			// enable each label to be a drop target
			DropTarget target = new DropTarget(label, DND.DROP_NONE);
			target.setTransfer(new Transfer[] { TextTransfer.getInstance() }); // varargs are not yet supported see https://git.eclipse.org/r/#/c/92236			// add a drop listener
			target.addDropListener(new MyDropTargetListener(parent, target));
		}

		// show the SWT window
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		// tear down the SWT window
		display.dispose();
	}

	private static class MyDragSourceListener extends DragSourceAdapter {

		private Composite parentComposite;
		private DragSource source;

		/**
		 * @param parentComposite - the composite that holds all pictures
		 * @param source - the drag source
		 * 
		 */
		public MyDragSourceListener(Composite parentComposite, DragSource source) {
			this.parentComposite = parentComposite;
			this.source = source;
		}

		/**
		 * The method computes the position / index of the source control
		 * (label) in the children array of the parent composite. This index is
		 * passed to the drop target using the data field of the drag source
		 * event.
		 */
		public void dragSetData(DragSourceEvent event) {
			for (int i = 0; i < parentComposite.getChildren().length; i++) {
				if (parentComposite.getChildren()[i].equals(source.getControl())) {
					event.data = new Integer(i).toString();
					break;
				}
			}
		}

	}
	

public static class MyDropTargetListener extends DropTargetAdapter {

    private Composite parentComposite;
    private DropTarget target;

    /**
     * @param parentComposite - the composite that holds all pictures
     * @param target - the drop target
     */
    public MyDropTargetListener(Composite parentComposite, DropTarget target) {
        this.parentComposite = parentComposite;
        this.target = target;
    }

    /**
     * This method moves the dragged picture to the new position and shifts the
     * old picture to the right or left.
     */
    public void drop(DropTargetEvent event) {

        // retrieve the stored index
        int sourceIndex = Integer.valueOf(event.data.toString());

        // compute the index of target control
        Control targetControl = target.getControl();
        int targetIndex = -1;
        for (int i = 0; i < parentComposite.getChildren().length; i++) {
            if (parentComposite.getChildren()[i].equals(targetControl)) {
                targetIndex = i;
                break;
            }
        }

        Control sourceControl = parentComposite.getChildren()[sourceIndex];
        // do not do anything if the dragged photo is dropped at the same
        // position
        if (targetIndex == sourceIndex)
            return;

        // if dragged from left to right
        // shift the old picture to the left
        if (targetIndex > sourceIndex)
            sourceControl.moveBelow(targetControl);
        // if dragged from right to left
        // shift the old picture to the right
        else
            sourceControl.moveAbove(targetControl);

        // repaint the parent composite
        parentComposite.requestLayout();
    }

}


}
