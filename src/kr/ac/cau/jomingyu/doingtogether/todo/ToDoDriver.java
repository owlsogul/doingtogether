package kr.ac.cau.jomingyu.doingtogether.todo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ToDoDriver {

	public File dataFolder;
	public String todoDataFileName = "todo.json";
	public String reviewDataFileName = "review.json";
	public File todoDataFile;
	public File reviewDataFile;

	public ToDoDriver(){
		
	}
	
	public boolean initToDoDriver(){
		dataFolder = new File("data");
		if (!dataFolder.exists()){
			System.out.println(dataFolder.mkdirs());
		}
		todoDataFile = new File(dataFolder, todoDataFileName);
		reviewDataFile = new File(dataFolder, reviewDataFileName);
		if (!todoDataFile.exists()){
			try {
				todoDataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		if (!reviewDataFile.exists()){
			try {
				reviewDataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public void loadDataFromLocal(ToDoManager manager){
		if (!manager.todoList.isEmpty()){
			manager.todoList.clear();
		}
		try {
			JSONParser parser = new JSONParser();
			// UTF-8 Issue
			// Object obj = parser.parse(new FileReader(TestDataFile));
			Object obj = parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(todoDataFile), "UTF-8")));
			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(jsonObject.toString());
			
			JSONArray msg = (JSONArray) jsonObject.get("todo");
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = msg.iterator();
			while (iterator.hasNext()) {
				JSONObject todoData = iterator.next();
				String title = (String) todoData.get("title");
				int priority = ((Long)todoData.get("priority")).intValue();
				long dueTime = (long) todoData.get("dueTime");
				long writeTime = (long) todoData.get("writeTime");
				String memo = (String) todoData.get("memo");
				
				ArrayList<String> people = new ArrayList<>();
				
				@SuppressWarnings("unchecked")
				Iterator<JSONObject> pi = ((JSONArray) todoData.get("people")).iterator();
				while (pi.hasNext()){
					people.add(""+pi.next());
				}
				String[] tmp = new String[people.size()];
				for (int i = 0; i < people.size(); i++){
					tmp[i] = people.get(i);
				}
				ToDo todo = new ToDo(title, priority, dueTime, writeTime, memo, tmp);
				manager.addToDo(todo);
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void loadDataFromServer(){
		
	}
	
	
}
