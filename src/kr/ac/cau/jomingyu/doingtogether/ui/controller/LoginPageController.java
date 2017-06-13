package kr.ac.cau.jomingyu.doingtogether.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.page.LoginPage;
import kr.ac.cau.jomingyu.doingtogether.ui.page.Page;
import kr.ac.cau.jomingyu.doingtogether.utility.Log;
import kr.ac.cau.jomingyu.doingtogether.utility.MsgBox;

public class LoginPageController extends PageController implements ActionListener{

	public LoginPage page;
	public LoginPageController(Page controllPage, MainFrame mainFrame) {
		super(controllPage, mainFrame);
		page = (LoginPage) controllPage;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onShow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnshow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == page.loginButton){
			Log.info(this.getClass(), "user press login button");
			if (page.idField.getText().length() >= 4 && page.pwField.getPassword() != null && page.pwField.getPassword().length >= 4){

				mainFrame.uiBridge.loadDataFromLocal();
				mainFrame.uiBridge.requestLogin();
			}
			else {
				MsgBox.warning("LACK OF CHARACTER!");
			}
		}
		else{
			Log.info(this.getClass(), "user press register button");
		}
		
	}
}

