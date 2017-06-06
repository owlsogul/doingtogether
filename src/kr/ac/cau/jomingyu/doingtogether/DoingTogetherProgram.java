package kr.ac.cau.jomingyu.doingtogether;

import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.UIBridge;

public class DoingTogetherProgram {

	public static DoingTogetherProgram program;
	public static void main(String[] args) {
		program = new DoingTogetherProgram();
		
	}
	
	public MainFrame mainFrame;
	public UIBridge uiBridge;
	
	public DoingTogetherProgram(){
		uiBridge = new UIBridge();
		uiBridge.initMainFrame();
		uiBridge.startMainFrame();
		mainFrame = uiBridge.getMainFrame();
	}

}
