package kr.ac.cau.jomingyu.doingtogether.ui;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.json.simple.JSONObject;

import kr.ac.cau.jomingyu.doingtogether.DoingTogetherProgram;
import kr.ac.cau.jomingyu.doingtogether.server.ServerBridge;
import kr.ac.cau.jomingyu.doingtogether.server.ServerConstants;
import kr.ac.cau.jomingyu.doingtogether.todo.ToDo;
import kr.ac.cau.jomingyu.doingtogether.todo.ToDoDriver;
import kr.ac.cau.jomingyu.doingtogether.todo.ToDoManager;
import kr.ac.cau.jomingyu.doingtogether.ui.controller.Content;
import kr.ac.cau.jomingyu.doingtogether.ui.page.HomePage;
import kr.ac.cau.jomingyu.doingtogether.ui.page.TimeLinePage;
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
			MsgBox.show("Success", "회원가입에 성공하였습니다");
		}
		else if (result.equals("400")){
			MsgBox.show("Error", "이미 있는 계정입니다");
		}
		else if (result.equals("401")){
			MsgBox.show("Error", "회원가입에 실패했습니다.");
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
			Log.info(this.getClass(), id + "로 로그인 성공");
			MsgBox.show("Success", id + "님 환영합니다");
			userId = id;
			userKey = Integer.parseInt(key);
			mainFrame.successLogin();
		}
		else {
			MsgBox.show("Error", "로그인에 실패하였습니다");
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
			MsgBox.show("Success", "업로드가 완료되었습니다.");
		}
		else {
			MsgBox.show("Error", "업로드에 실패하였습니다.");
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
			MsgBox.show("Error", "데이터 다운로드에 실패했습니다.");
			return;
		}
		MsgBox.show("Success", "데이터 다운로드에 성공했습니다.");
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
			MsgBox.warning("공유하기가 실패하였습니다.");
			return;
		}
		MsgBox.show("Success", "공유하기를 성공하였습니다.");
		return;
	}

	public void requestTimeline(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(ServerConstants.KEY_DATATYPE, ServerConstants.KEY_TIMELINE);
		String send = JSONUtil.translateMapToJson(map);
		serverBridge.sendRawData(send);
		Log.info(this.getClass(), "Client send timeline data "+ send);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void responseTimeline(LinkedHashMap<String, String> data){
		
		Log.info(this.getClass(), "client receive timeline data " + JSONUtil.translateMapToJson(data));
		
		String result = data.get(ServerConstants.KEY_TIMELINE_RESULT);
		if (result.equals("400")){
			MsgBox.warning("타임라인 데이터를 가져오는 것에 실패하였습니다.");
			return;
		}

		// init contents list
		TimeLinePage page = (TimeLinePage) mainFrame.pageManager.getTimelinePage();
		page.controller.clearContents();
		
		// set contents list
		JSONObject jObj = new JSONObject(data);
		LinkedList jArr = (LinkedList) jObj.get(ServerConstants.KEY_TIMELINE_CONTENTS);
		for (Object obj : jArr){
			LinkedHashMap<String, String> content = (LinkedHashMap<String, String>) obj;
			/*String writer = content.get(ServerConstants.KEY_TIMELINE_WRITER_ID);
			String writetime = content.get(ServerConstants.KEY_TIMElINE_WRITE_TIME);
			String title = content.get(ServerConstants.KEY_TIMELINE_TITLE);
			String priority = content.get(ServerConstants.KEY_TIMELINE_PRIORITY);
			String people = content.get(ServerConstants.KEY_TIMELINE_PEOPLE);
			String dueDate = content.get(ServerConstants.KEY_TIMELINE_DUE_DATE);
			String memo = content.get(ServerConstants.KEY_TIMELINE_MEMO);*/
			page.controller.addContent(Content.createFromHashMap(content));
		}
		
		// reload content page
		page.controller.loadContent();
		MsgBox.show("Success", "타임라인 로딩을 성공하였습니다.");
		return;
	}
	


	public void loadDataFromLocal(){
		// TODO: Thread 로 작업하기
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
