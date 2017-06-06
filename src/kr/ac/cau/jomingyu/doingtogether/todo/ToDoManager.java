package kr.ac.cau.jomingyu.doingtogether.todo;

import java.util.ArrayList;

import kr.ac.cau.jomingyu.doingtogether.utility.Log;

public class ToDoManager {

	public ArrayList<ToDo> todoList;
	public ToDoManager(){
		todoList = new ArrayList<>();
	}
	
	public void addToDo(String title, int priority, long dueTime, long writeTime, String memo){
		addToDo(title, priority, dueTime, writeTime, memo, new String[0]);
	}
	
	public void addToDo(String title, int priority, long dueTime, long writeTime, String memo, String...people){
		addToDo(new ToDo(title, priority, dueTime, writeTime, memo, people));
	}
	
	public void addToDo(ToDo todo){
		todoList.add(todo);
	}

	public void removeToDo(ToDo todo) {
		if (todoList.contains(todo)){
			todoList.remove(todo);
		}
		else{
			Log.info(this.getClass(), "NOT-EXIST TODO");
		}
	}
	
	
	
}
