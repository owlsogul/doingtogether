package kr.ac.cau.jomingyu.doingtogether.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import kr.ac.cau.jomingyu.doingtogether.ui.page.Page;
import kr.ac.cau.jomingyu.doingtogether.utility.Log;

public class TabBar extends JPanel implements ActionListener{
	// SIZE OF TAB BAR IS 42
	private static final long serialVersionUID = 1L;
	public PageManager pageManager;
	public ArrayList<JButton> buttons;

	public TabBar(PageManager pageManager){
		this.pageManager = pageManager;
	}

	public void initTabBar(){
		this.setLayout(new FlowLayout());
		ArrayList<Page> pageList = pageManager.pageList;
		for (Page page : pageList){
			JButton button = new JButton();
			button.setName(page.getName());
			button.setIcon(page.getIcon());
			button.addActionListener(this);
			button.setBackground(new Color(0,0,0,0));
			button.setOpaque(false);
			button.setBorder(null);
			this.add(button);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton){
			JButton button = (JButton) e.getSource();
			Log.info(this.getClass(), button.getName() + " is pressed");
			// ���� �������� �ҷ����� �ʰ� �ϱ� ���� ���� ��ư�� �����Ϳ� ���� �������� �̸��� ����
			if (!button.getName().equals(pageManager.mainFrame.currentPage.getName())){
				for (Page page : pageManager.pageList){
					if (page.getName().equals(button.getName())){
						pageManager.mainFrame.changePage(page);
						break;
					}
				}
			}	
		}
	}
	
}
