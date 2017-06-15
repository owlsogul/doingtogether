package kr.ac.cau.jomingyu.doingtogether.ui;

import java.util.ArrayList;

import kr.ac.cau.jomingyu.doingtogether.ui.page.HomePage;
import kr.ac.cau.jomingyu.doingtogether.ui.page.Page;
import kr.ac.cau.jomingyu.doingtogether.ui.page.TimeLinePage;

public class PageManager {

	public MainFrame mainFrame;
	
	public ArrayList<Page> pageList;
	
	public TabBar tabBar;
	
	public PageManager(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		this.pageList = new ArrayList<>();
		this.tabBar = new TabBar(this);
	}

	
	public void registerPages(){
		
		HomePage hPage = new HomePage(mainFrame, "Home", mainFrame.resourceManager.getIcon(ResourceManager.ICON_TAB_HOME));
		pageList.add(hPage);
		
		TimeLinePage tPage = new TimeLinePage(mainFrame, "Timeline", mainFrame.resourceManager.getIcon(ResourceManager.ICON_TAB_TIMELINE));
		pageList.add(tPage);
		
	}
	
	public Page getHomePage(){
		return pageList.get(0);
	}
	
	public Page getTimelinePage(){
		return pageList.get(1);
	}
}
