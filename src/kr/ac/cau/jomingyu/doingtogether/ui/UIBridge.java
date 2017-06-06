package kr.ac.cau.jomingyu.doingtogether.ui;

import kr.ac.cau.jomingyu.doingtogether.DoingTogetherProgram;
import kr.ac.cau.jomingyu.doingtogether.server.ServerBridge;
import kr.ac.cau.jomingyu.doingtogether.todo.ToDoDriver;
import kr.ac.cau.jomingyu.doingtogether.todo.ToDoManager;

public class UIBridge {

	DoingTogetherProgram program;
	MainFrame mainFrame;
	public ToDoManager todoManager;
	public ToDoDriver todoDriver;
	
	public ServerBridge serverBridge;
	
	public UIBridge(){
		todoManager = new ToDoManager();
		todoDriver = new ToDoDriver();
		System.out.println(todoDriver.initToDoDriver());
	}
	
	public void setPrograme(DoingTogetherProgram program){
		this.program = program;
	}
	
	
	public void requestLogin(){
		mainFrame.successLogin();
	}
	
	public void loadDataFromLocal(){
		// TODO: Thread 로 작업하기
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
