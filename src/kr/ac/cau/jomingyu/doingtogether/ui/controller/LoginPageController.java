package kr.ac.cau.jomingyu.doingtogether.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import kr.ac.cau.jomingyu.doingtogether.server.ServerConstants;
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
				String id = page.idField.getText();
				String pw = new String(page.pwField.getPassword());
				LinkedHashMap<String, String> map = new LinkedHashMap<>();
				map.put(ServerConstants.KEY_DATATYPE, ServerConstants.KEY_LOGIN);
				map.put(ServerConstants.KEY_LOGIN_ID, id);
				map.put(ServerConstants.KEY_LOGIN_PW, pw);
				mainFrame.uiBridge.requestLogin(map);
				mainFrame.uiBridge.loadDataFromLocal();
			}
			else {
				MsgBox.warning("LACK OF CHARACTER!");
			}
		}
		else if (e.getSource() == page.registerButton){
			page.showRegisterFrame();
		}
	}
	
	public void sendRegisterData(String id, String pw, String name, String email, String phone){
		Log.info(this.getClass(), String.format("From RegisterForm id : %s, pw : %s, name : %s, email : %s, phone : %s", id, pw, name, email, phone));
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		data.put(ServerConstants.KEY_REGISTER_ID, id);
		data.put(ServerConstants.KEY_REGISTER_PW, pw);
		data.put(ServerConstants.KEY_REGISTER_NAME, name);
		data.put(ServerConstants.KEY_REGISTER_EMAIL, email);
		data.put(ServerConstants.KEY_REGISTER_PHONE, phone);
		Log.info(this.getClass(), "sending data is " + page.mainFrame.uiBridge.requestRegister(data));
	}
}

