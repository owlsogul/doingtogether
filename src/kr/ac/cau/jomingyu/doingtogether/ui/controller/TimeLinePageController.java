package kr.ac.cau.jomingyu.doingtogether.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.page.Page;
import kr.ac.cau.jomingyu.doingtogether.ui.page.TimeLinePage;
import kr.ac.cau.jomingyu.doingtogether.utility.Log;
import kr.ac.cau.jomingyu.doingtogether.utility.MsgBox;

public class TimeLinePageController extends PageController implements ActionListener{

	public ArrayList<Content> contentList;
	public int contentCursor = 0;
	public TimeLinePage page;
	
	public TimeLinePageController(Page controllPage, MainFrame mainFrame) {
		super(controllPage, mainFrame);
		contentList = new ArrayList<>();
		page = (TimeLinePage) controllPage;
	}
	
	public void clearContents(){
		contentList.clear();
		contentCursor = 0;
	}
	
	public void addContent(Content c){
		contentList.add(c);
	}
	
	public void loadContent(){
		if (!contentList.isEmpty()){
			page.contentPanel.setContent(contentList.get(contentCursor));
			Log.info(this.getClass(), "content setting");
		}
		else {
			MsgBox.warning("There is no contents");
		}
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
		
		Log.info(this.getClass(), "Show Timeline Page");
		page.mainFrame.uiBridge.requestTimeline();
		
		// LOAD...
		
		// next/ prev
		
	}

	@Override
	public void onUnshow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == page.nextButton){
			if (contentCursor < contentList.size()-1){
				contentCursor++;
				page.contentPanel.setContent(contentList.get(contentCursor));
			}
			else {
				MsgBox.warning("last page");
			}
		}
		else if (e.getSource() == page.prevButton){
			if (contentCursor > 0){
				contentCursor--;
				page.contentPanel.setContent(contentList.get(contentCursor));
			}
			else {
				MsgBox.warning("last page");
			}
		}
		
	}

}
