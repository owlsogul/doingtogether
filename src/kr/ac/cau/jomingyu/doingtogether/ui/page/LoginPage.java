package kr.ac.cau.jomingyu.doingtogether.ui.page;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.controller.LoginPageController;

public class LoginPage extends Page{
	
	private static final long serialVersionUID = 1L;

	public JLabel idLabel;
	public JTextField idField;
	public JLabel pwLabel;
	public JPasswordField pwField;
	public JButton loginButton;
	
	public LoginPageController controller;
	
	public LoginPage(MainFrame mainFrame) {
		super(mainFrame, null, null);
		JPanel idPanel = new JPanel();
		idLabel = new JLabel("아이디     : ");
		idField = new JTextField(20);
		idPanel.add(idLabel);
		idPanel.add(idField);
		
		JPanel pwPanel = new JPanel();
		pwLabel = new JLabel("비밀번호 : ");
		pwField = new JPasswordField(20);
		pwPanel.add(pwLabel);
		pwPanel.add(pwField);
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(controller);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		loginPanel.add(idPanel);
		loginPanel.add(pwPanel);
		loginPanel.add(loginButton);
		
		this.add(loginPanel);
	}


	@Override
	public void setController() {
		this.pageController = new LoginPageController(this, mainFrame);
		this.controller = (LoginPageController) pageController;
	}
	
	

}
