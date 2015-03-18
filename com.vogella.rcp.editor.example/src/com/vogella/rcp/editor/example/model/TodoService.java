package com.vogella.rcp.editor.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoService {

	private static TodoService todoService = new TodoService();
	private List<Todo> todos = new ArrayList<Todo>();

	private TodoService() {
		Todo todo = new Todo(0, "Fix Bugs", "Fix Eclipse Bug 123", false, new Date());
		todos.add(todo);
		todo = new Todo(1, "Write Tutorial", "Write a tutorial about editors", true, new Date());
		todos.add(todo);
	}

	public static TodoService getInstance() {
		return todoService;
	}

	public List<Todo> getPersons() {
		return todos;
	}

	public Todo getTodoById(long id) {
		for (Todo todo : todos) {
			if (todo.getId() == id) {
				return todo;
			}
		}
		return null;
	}
}
