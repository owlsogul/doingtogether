package kr.ac.cau.jomingyu.doingtogether.ui.page;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import kr.ac.cau.jomingyu.doingtogether.ui.ContentPanel;
import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.controller.TimeLinePageController;

public class TimeLinePage extends Page{

	public TimeLinePageController controller;
	
	public TimeLinePage(MainFrame mainFrame, String name, ImageIcon icon) {
		super(mainFrame, name, icon);
		viewInit();
	}

	
	public ContentPanel contentPanel;
	public JButton prevButton, nextButton;
	public JPanel commandPanel;

	private void viewInit() {
		
		// initialize components
		contentPanel = new ContentPanel(null);
		prevButton = new JButton("¢¸");
		nextButton = new JButton("¢º");
		commandPanel = new JPanel();

		// Action Listener
		prevButton.addActionListener(controller);
		nextButton.addActionListener(controller);
		
		
		// Adding componets to this panel
		this.setLayout(new BorderLayout());
		this.add(contentPanel, BorderLayout.CENTER);
		this.add(prevButton, BorderLayout.WEST);
		this.add(nextButton, BorderLayout.EAST);
		this.add(commandPanel, BorderLayout.SOUTH);
		
		return;
	}


	private static final long serialVersionUID = 1L;


	@Override
	public void setController() {
		this.pageController = new TimeLinePageController(this, mainFrame);
		this.controller = (TimeLinePageController) pageController;
		
	}
}


