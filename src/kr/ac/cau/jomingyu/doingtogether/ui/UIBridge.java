package kr.ac.cau.jomingyu.doingtogether.ui;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

import kr.ac.cau.jomingyu.doingtogether.DoingTogetherProgram;
import kr.ac.cau.jomingyu.doingtogether.server.ServerBridge;
import kr.ac.cau.jomingyu.doingtogether.server.ServerConstants;
import kr.ac.cau.jomingyu.doingtogether.todo.ToDo;
import kr.ac.cau.jomingyu.doingtogether.todo.ToDoDriver;
import kr.ac.cau.jomingyu.doingtogether.todo.ToDoManager;
import kr.ac.cau.jomingyu.doingtogether.ui.page.HomePage;
import kr.ac.cau.jomingyu.doingtogether.utility.JSONUtil;
import kr.ac.cau.jomingyu.doingtogether.utility.Log;
import kr.ac.cau.jomingyu.doingtogether.utility.MsgBox;

public class UIBridge {

	DoingTogetherProgram program;
	MainFrame mainFrame;
	public ToDoManager todoManager;
	public ToDoDriver todoDriver;

	public ServerBridge serverBridge;

	public String userId = null;
	public int userKey = -1;
	
	public UIBridge(){
		todoManager = new ToDoManager();
		todoDriver = new ToDoDriver();
		System.out.println(todoDriver.initToDoDriver());
		try {
			serverBridge = new ServerBridge(this, ServerConstants.HOST, ServerConstants.PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPrograme(DoingTogetherProgram program){
		this.program = program;
	}


	/*
	 * ###################################################
	 * SERVER SIDE
	 * Method List
	 * 		to Server
	 * 			requestRegister
	 * 		from Server
	 * 			responseRegister		
	 * ###################################################
	 */

	public String requestRegister(LinkedHashMap<String, String> data){

		// data type check
		if (!data.containsKey(ServerConstants.KEY_DATATYPE)){
			data.put(ServerConstants.KEY_DATATYPE, ServerConstants.KEY_REGISTER);
		}
		String send = JSONUtil.translateMapToJson(data);
		serverBridge.sendRawData(send);
		return send;
	}

	public void responseRegister(LinkedHashMap<String, String> map) {
		String result = map.get(ServerConstants.KEY_REGISTER_RESULT);
		if (result.equals("777")){
			MsgBox.show("Success", "ȸ�����Կ� �����Ͽ����ϴ�");
		}
		else if (result.equals("400")){
			MsgBox.show("Error", "�̹� �ִ� �����Դϴ�");
		}
		else if (result.equals("401")){
			MsgBox.show("Error", "ȸ�����Կ� �����߽��ϴ�.");
		}
	}

	public void requestLogin(LinkedHashMap<String, String> data){
		String send = JSONUtil.translateMapToJson(data);
		serverBridge.sendRawData(send);
		Log.info(this.getClass(), "Client send login data " + send);
	}
	
	public void responseLogin(LinkedHashMap<String, String> data){
		String result = data.get(ServerConstants.KEY_LOGIN_RESULT);
		String id = data.get(ServerConstants.KEY_LOGIN_ID);
		String key = data.get(ServerConstants.KEY_LOGIN_KEY);
		if (result.equals("777")){
			Log.info(this.getClass(), id + "�� �α��� ����");
			MsgBox.show("Success", id + "�� ȯ���մϴ�");
			userId = id;
			userKey = Integer.parseInt(key);
			mainFrame.successLogin();
		}
		else {
			MsgBox.show("Error", "�α��ο� �����Ͽ����ϴ�");
		}
	}
	
	public void requestUpload(){
		LinkedHashMap<String, String> data = new LinkedHashMap<>();
		data.put(ServerConstants.KEY_DATATYPE, ServerConstants.KEY_UPLOAD);
		data.put(ServerConstants.KEY_UPLOAD_ID, userId);
		data.put(ServerConstants.KEY_UPLOAD_CONTENT, todoDriver.todoToString(todoManager));
		String send = JSONUtil.translateMapToJson(data);
		serverBridge.sendRawData(send);
		Log.info(this.getClass(), "Client send upload data " + send);
	}
	
	public void responseUpload(LinkedHashMap<String, String> data){
		String result = data.get(ServerConstants.KEY_DOWNLOAD_RESULT);
		if (result.equals("777")){
			MsgBox.show("Success", "���ε尡 �Ϸ�Ǿ����ϴ�.");
		}
		else {
			MsgBox.show("Error", "���ε忡 �����Ͽ����ϴ�.");
		}
	}
	
	public void requestDownload(){
		
		LinkedHashMap<String, String> data = new LinkedHashMap<>();
		data.put(ServerConstants.KEY_DATATYPE, ServerConstants.KEY_DOWNLOAD);
		data.put(ServerConstants.KEY_DOWNLOAD_ID, userId);
		
		String send = JSONUtil.translateMapToJson(data);
		serverBridge.sendRawData(send);
		Log.info(this.getClass(), "Client send download data "+ send);
		
	}
	
	public void responseDownload(LinkedHashMap<String, String> data){
		
		String result = data.get(ServerConstants.KEY_DOWNLOAD_RESULT);
		if (result.equals("400")){
			MsgBox.show("Error", "������ �ٿ�ε忡 �����߽��ϴ�.");
			return;
		}
		MsgBox.show("Success", "������ �ٿ�ε忡 �����߽��ϴ�.");
		String content = data.get(ServerConstants.KEY_DOWNLOAD_CONTENT);
		todoDriver.stringToToDo(todoManager, content);
		HomePage homePage = (HomePage) mainFrame.pageManager.getHomePage();
		homePage.controller.loadCellList();
		homePage.recreateCellPart();
	}

	public void requestShare(ToDo todo){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(ServerConstants.KEY_DATATYPE, ServerConstants.KEY_SHARE);
		map.put(ServerConstants.KEY_SHARE_WRITER_KEY, String.valueOf(userKey));
		map.put(ServerConstants.KEY_SHARE_WRITER_ID, userId);
		map.put(ServerConstants.KEY_SHARE_WRITE_TIME, String.valueOf(todo.writeTime));
		map.put(ServerConstants.KEY_SHARE_TITLE, todo.title);
		map.put(ServerConstants.KEY_SHARE_PRIORITY, String.valueOf(todo.priority));
		String people = "";
		for (int i = 0; i < todo.people.length; i++){
			people += todo.people[i];
			if (i != todo.people.length-1){
				people += ":";
			}
		}
		map.put(ServerConstants.KEY_SHARE_PEOPLE, people);
		map.put(ServerConstants.KEY_SHARE_DUEDATE, String.valueOf(todo.dueTime));
		map.put(ServerConstants.KEY_SHARE_MEMO, todo.memo);
		map.put(ServerConstants.KEY_SHARE_IMAGES, "");
		
		String send = JSONUtil.translateMapToJson(map);
		serverBridge.sendRawData(send);
		Log.info(this.getClass(), "Client send share data "+ send);
		
	}
	
	public void responseShare(LinkedHashMap<String, String> data){
		
		String result = data.get(ServerConstants.KEY_SHARE_RESULT);
		if (result.equals("400")){
			MsgBox.warning("�����ϱⰡ �����Ͽ����ϴ�.");
			return;
		}
		MsgBox.show("Success", "�����ϱ⸦ �����Ͽ����ϴ�.");
		return;
	}



	public void loadDataFromLocal(){
		// TODO: Thread �� �۾��ϱ�
		todoDriver.loadDataFromLocal(todoManager);
	}

	public void saveDataToLocal(){
		todoDriver.saveDataToLocal(todoManager);
	}





	// main frame
	public void initMainFrame(){
		mainFrame = new MainFrame(this);
		mainFrame.preLoad();
	}

	public void startMainFrame(){
		mainFrame.Load();
	}

	public MainFrame getMainFrame(){
		return this.mainFrame;
	}




}
