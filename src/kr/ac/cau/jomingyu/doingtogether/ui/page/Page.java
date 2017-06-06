package kr.ac.cau.jomingyu.doingtogether.ui.page;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.controller.PageController;

public abstract class Page extends JPanel{

	private static final long serialVersionUID = 1L;

	
	
	// �������� �̸� ���� �����ϰ� �ִ� ����
	private String name;
	// �ش� �������� Icon�� �����ϰ� �ִ� ����
	private ImageIcon icon;
	
	public MainFrame mainFrame;
	public PageController pageController;
	
	public Page(MainFrame mainFrame, String name, ImageIcon icon){
		this.mainFrame = mainFrame;
		this.name = name;
		this.icon = icon;
		setController();
	}
	
	public abstract void setController();
	
	public PageController getController(){
		return this.pageController;
	}
	
	/**
	 *  �̸� ���� �����ϴ� �޼ҵ�
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * �������� �����ϴ� �޼ҵ�
	 */
	public ImageIcon getIcon(){
		return this.icon;
	}
	
	
}
