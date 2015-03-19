package com.vogella.rcp.editor.example.editor.text;

import org.eclipse.ui.editors.text.TextEditor;

import com.vogella.rcp.editor.example.editor.text.config.TaskSourceViewerConfiguration;

public class TaskTextEditor extends TextEditor {

	public static final String ID = "com.vogella.rcp.editor.example.editor.tasktexteditor";
	
	public TaskTextEditor() {
		System.out.println("Task Text Editor opened");
		setSourceViewerConfiguration(new TaskSourceViewerConfiguration());
	}

}
