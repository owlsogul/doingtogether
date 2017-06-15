package kr.ac.cau.jomingyu.doingtogether.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import kr.ac.cau.jomingyu.doingtogether.ui.page.LoginPage;
import kr.ac.cau.jomingyu.doingtogether.ui.page.Page;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final int FRAME_WIDTH = 750/4 + 750/6 - 12;
	public static final int FRAME_HEIGHT = 1334/4 + 1334/6;
	
	public UIBridge uiBridge; 
	
	public PageManager pageManager;
	public ResourceManager resourceManager;
	
	public Page currentPage;
	public int currentState;
	
	public MainFrame(UIBridge uiBridge){
		
		this.uiBridge = uiBridge;
		pageManager = new PageManager(this);
		resourceManager = new ResourceManager();
		
	}
	
	public void preLoad(){
		if (resourceManager.loadResources()){
			System.out.println("Loading Successfully");
		}
		else {
			// TODO : 리소스 로딩 실패할 경우 조치하기
		}
		pageManager.registerPages();
	}
	
	public void Load(){
		
		currentState = 0;
		currentPage = new LoginPage(this);
		
		this.setTitle("Doing Together");
		this.setSize(FRAME_WIDTH + 16 + 12/*스크롤바 크기*/, FRAME_HEIGHT + 39);
		this.setLayout(new BorderLayout());
		
		this.getContentPane().add(currentPage, BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void changePage(Page page){
		// Unshow Event	
		currentPage.getController().onUnshow();
		this.getContentPane().remove(currentPage);
		currentPage = page;
		// Show Event
		page.getController().onShow();
		this.getContentPane().add(currentPage, BorderLayout.CENTER);
		
		revalidate();
		repaint();
	}
	
	public void successLogin(){

		pageManager.tabBar.initTabBar();
		this.getContentPane().add(pageManager.tabBar, BorderLayout.NORTH);
		changePage(pageManager.getHomePage());
		
		
	}
}
