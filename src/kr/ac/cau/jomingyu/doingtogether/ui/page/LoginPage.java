package kr.ac.cau.jomingyu.doingtogether.ui.page;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.controller.LoginPageController;
import kr.ac.cau.jomingyu.doingtogether.utility.MsgBox;

public class LoginPage extends Page{
	
	private static final long serialVersionUID = 1L;

	public JLabel idLabel;
	public JTextField idField;
	public JLabel pwLabel;
	public JPasswordField pwField;
	public JButton loginButton;
	public JButton registerButton;
	
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
		
		loginButton = new JButton("Sign In");
		loginButton.addActionListener(controller);
		
		registerButton = new JButton("Sign Up");
		registerButton.addActionListener(controller);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		loginPanel.add(idPanel);
		loginPanel.add(pwPanel);
		loginPanel.add(loginButton);
		loginPanel.add(registerButton);
		
		this.add(loginPanel);
	}


	@Override
	public void setController() {
		this.pageController = new LoginPageController(this, mainFrame);
		this.controller = (LoginPageController) pageController;
	}
	
	public void showRegisterFrame(){
		new RegisterFrame(controller);
	}
	
}

@SuppressWarnings("serial")
class RegisterFrame extends JFrame implements ActionListener{

	JLabel 	   idLabel;
	JTextField idField;
	JLabel     pwLabel;
	JTextField pwField;
	JLabel     pwChkLabel;
	JTextField pwChkField;
	JLabel     nameLabel;
	JTextField nameField;
	JLabel     emailLabel;
	JTextField emailField;
	JLabel     phoneLabel;
	JTextField phoneField;
	JButton doneButton;
	
	LoginPageController controller;
	public RegisterFrame(LoginPageController controller) {
		super("Register");
		this.controller = controller;
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(4, 3));
		
		idLabel = new JLabel("ID");
		idField = new JTextField(10);
		
		pwLabel = new JLabel("PW");
		pwField = new JTextField(10);
		
		pwChkLabel = new JLabel("PW Check");
		pwChkField = new JTextField(10);
		
		nameLabel = new JLabel("Name");
		nameField = new JTextField(10);
		
		emailLabel = new JLabel("E-mail");
		emailField = new JTextField(10);
		
		phoneLabel = new JLabel("Phone");
		phoneField = new JTextField(10);
		
		contentPanel.add(idLabel);
		contentPanel.add(pwLabel);
		contentPanel.add(pwChkLabel);
		contentPanel.add(idField);
		contentPanel.add(pwField);
		contentPanel.add(pwChkField);
		
		contentPanel.add(nameLabel);
		contentPanel.add(emailLabel);
		contentPanel.add(phoneLabel);
		contentPanel.add(nameField);
		contentPanel.add(emailField);
		contentPanel.add(phoneField);
		
		doneButton = new JButton("Done");
		doneButton.addActionListener(this);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.getContentPane().add(doneButton, BorderLayout.EAST);
		this.setVisible(true);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (chkValidation()){
			controller.sendRegisterData(idField.getText(), pwField.getText(), nameField.getText(), emailField.getText(), phoneField.getText());
			this.dispose();
		}
		
	}
	
	private boolean chkValidation(){
		// id chk
		if (idField.getText() == null || idField.getText().length() < 3){
			MsgBox.show("Error", "ID는 4글자 이상이여야 합니다.");
			return false;
		}
		
		// pw chk
		if (pwField.getText() == null || pwField.getText().length() < 5){
			MsgBox.show("Error", "Password는 6글자 이상이여야 합니다.");
			return false;
		}
		
		// pw equality chk
		if (!pwField.getText().equals(pwChkField.getText())){
			MsgBox.show("Error", "Password가 일치하지 않습니다.");
			return false;
		}
		
		// name chk
		if (nameField.getText() == null || nameField.getText().length() < 2){
			MsgBox.show("Error", "이름은 2글자 이상이여야 합니다.");
			return false;
		}
		
		// email chk
		if (emailField.getText() == null || emailField.getText().length() < 4 || !emailField.getText().contains("@")){
			MsgBox.show("Error", "올바르지 않은 이메일 형식입니다.");
			return false;
		}
		
		// phone chk
		if (phoneField.getText() == null || phoneField.getText().length() < 4){
			MsgBox.show("Error", "폰번호 형식이 옳지 않습니다.");
			return false;
		}
		else {
			String phone = phoneField.getText();
			phone = phone.trim();
			phone = phone.replaceAll(" ", "");
			phone = phone.replaceAll("-", "");
			if (phone.length() < 8){
				MsgBox.show("Error", "폰번호 형식이 옳지 않습니다.");
				return false;
			}
			
			if (!phone.matches("^[0-9]*$")){
				MsgBox.show("Error", "폰번호 형식이 옳지 않습니다.");
				return false;
			}
			phoneField.setText(phone);
		}
		
		return true;
	}
	
}
