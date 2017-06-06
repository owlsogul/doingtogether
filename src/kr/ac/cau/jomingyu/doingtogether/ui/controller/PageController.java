package kr.ac.cau.jomingyu.doingtogether.ui.controller;

import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.page.Page;

public abstract class PageController {

	public Page controllPage;
	public MainFrame mainFrame;
	public PageController(Page controllPage, MainFrame mainFrame){
		this.controllPage = controllPage;
		this.mainFrame = mainFrame;
	}
	
	public final Page getPage(){
		return controllPage;
	}
	
	public abstract void onCreate();
	public abstract void onDestroy();
	
	public abstract void onShow();
	public abstract void onUnshow();
	
	
}
