package kr.ac.cau.jomingyu.doingtogether.ui;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

import kr.ac.cau.jomingyu.doingtogether.DoingTogetherProgram;
import kr.ac.cau.jomingyu.doingtogether.server.ServerBridge;
import kr.ac.cau.jomingyu.doingtogether.server.ServerConstants;
import kr.ac.cau.jomingyu.doingtogether.todo.ToDoDriver;
import kr.ac.cau.jomingyu.doingtogether.todo.ToDoManager;
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
		if (result.equals("777")){
			Log.info(this.getClass(), id + "�� �α��� ����");
			MsgBox.show("Success", id + "�� ȯ���մϴ�");
			userId = id;
			mainFrame.successLogin();
		}
		else {
			MsgBox.show("Error", "�α��ο� �����Ͽ����ϴ�");
		}
	}




	public void loadDataFromLocal(){
		// TODO: Thread �� �۾��ϱ�
		todoDriver.loadDataFromLocal(todoManager);
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
