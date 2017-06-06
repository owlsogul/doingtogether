package kr.ac.cau.jomingyu.doingtogether.ui.page;

import javax.swing.ImageIcon;

import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.controller.TimeLinePageController;

public class TimeLinePage extends Page{

	public TimeLinePageController controller;
	public TimeLinePage(MainFrame mainFrame, String name, ImageIcon icon) {
		super(mainFrame, name, icon);
	}


	private static final long serialVersionUID = 1L;


	@Override
	public void setController() {
		this.pageController = new TimeLinePageController(this, mainFrame);
		this.controller = (TimeLinePageController) pageController;
		
	}

}
