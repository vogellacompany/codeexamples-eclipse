package com.vogella.rcp.editor.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskService {

	private static TaskService taskService = new TaskService();
	private List<Task> tasks = new ArrayList<Task>();

	private TaskService() {
		Task task = new Task(0, "Fix Bugs", "Fix Eclipse Bug 123", false, new Date());
		tasks.add(task);
		task = new Task(1, "Write Tutorial", "Write a tutorial about editors", true, new Date());
		tasks.add(task);
	}

	public static TaskService getInstance() {
		return taskService;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public Task getTaskById(long id) {
		for (Task todo : tasks) {
			if (todo.getId() == id) {
				return todo;
			}
		}
		return null;
	}
}
