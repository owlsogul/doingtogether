package kr.ac.cau.jomingyu.doingtogether.ui.page;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.controller.PageController;

public abstract class Page extends JPanel{

	private static final long serialVersionUID = 1L;

	
	
	// 페이지의 이름 값을 저장하고 있는 변수
	private String name;
	// 해당 페이지의 Icon을 저장하고 있는 변수
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
	 *  이름 값을 리턴하는 메소드
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * 아이콘을 리턴하는 메소드
	 */
	public ImageIcon getIcon(){
		return this.icon;
	}
	
	
}
