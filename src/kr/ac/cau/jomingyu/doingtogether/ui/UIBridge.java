package kr.ac.cau.jomingyu.doingtogether.ui;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

import kr.ac.cau.jomingyu.doingtogether.DoingTogetherProgram;
import kr.ac.cau.jomingyu.doingtogether.server.ServerBridge;
import kr.ac.cau.jomingyu.doingtogether.server.ServerConstants;
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
		if (result.equals("777")){
			Log.info(this.getClass(), id + "로 로그인 성공");
			MsgBox.show("Success", id + "님 환영합니다");
			userId = id;
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
		homePage.updateAllCell();
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
