package kr.ac.cau.jomingyu.doingtogether.todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kr.ac.cau.jomingyu.doingtogether.utility.Log;

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
				int priority = new Long(Long.parseLong((String) todoData.get("priority"))).intValue();
				long dueTime = Long.parseLong((String) todoData.get("dueTime"));
				long writeTime = Long.parseLong((String) todoData.get("writeTime"));
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
			e.printStackTrace();
		}
	}
	
	public void stringToToDo(ToDoManager manager, String str){
		if (!manager.todoList.isEmpty()){
			manager.todoList.clear();
		}
		try {
			JSONParser parser = new JSONParser();
			// UTF-8 Issue
			// Object obj = parser.parse(new FileReader(TestDataFile));
			Object obj = parser.parse(str);
			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(jsonObject.toString());
			
			JSONArray msg = (JSONArray) jsonObject.get("todo");
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = msg.iterator();
			while (iterator.hasNext()) {
				JSONObject todoData = iterator.next();
				String title = (String) todoData.get("title");
				int priority = new Long(Long.parseLong((String) todoData.get("priority"))).intValue();
				long dueTime = Long.parseLong((String) todoData.get("dueTime"));
				long writeTime = Long.parseLong((String) todoData.get("writeTime"));
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
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String todoToString(ToDoManager manager){
		JSONArray jArray = new JSONArray();
		for (ToDo todo : manager.todoList){
			JSONObject jObject = new JSONObject();
			jObject.put("title", todo.title);
			jObject.put("priority", String.valueOf(todo.priority));
			jObject.put("dueTime", String.valueOf(todo.dueTime));
			jObject.put("writeTime", String.valueOf(todo.writeTime));
			jObject.put("memo", todo.memo);
			JSONArray peopleArray = new JSONArray();
			for (String people : todo.people){
				peopleArray.add(people);
			}
			jObject.put("people", peopleArray);
			jArray.add(jObject);
		}
		JSONObject jObject = new JSONObject();
		jObject.put("todo", jArray);
		return jObject.toJSONString();
	}
	
	public void saveDataToLocal(ToDoManager manager){
		
		String str = todoToString(manager);
		Log.info(this.getClass(), str);
		
		try {
			if (!todoDataFile.exists()){
				todoDataFile.createNewFile();
			}
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(todoDataFile), "UTF-8"));
			output.write(str);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
