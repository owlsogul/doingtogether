package kr.ac.cau.jomingyu.doingtogether.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.ac.cau.jomingyu.doingtogether.todo.ToDo;
import kr.ac.cau.jomingyu.doingtogether.ui.page.HomePage;
import kr.ac.cau.jomingyu.doingtogether.utility.Log;
import kr.ac.cau.jomingyu.doingtogether.utility.MsgBox;

public class ToDoCell extends JPanel implements ActionListener{
	public static final int CELL_HEIGHT = 50;
	public static final int CELL_OPEN_HEIGHT = 100;
	private static final long serialVersionUID = 1L;
	public HomePage page;

	ToDo todo;
	JButton statusButton;
	JLabel priorityLabel;
	JLabel titleLabel;
	long date;
	JLabel dateLabel;

	JButton moreButton;
	JButton editButton;
	JButton delButton;
	JButton shareButton;
	
	
	JLabel peopleLabel;
	JLabel memoLabel;
	JPanel belowPanel;
	JPanel abovePanel;


	boolean isFlipped = true;


	public ToDoCell(HomePage page){
		this.page = page;
		statusButton = new JButton("완료");
		priorityLabel = new JLabel("-1");
		titleLabel = new JLabel("NULL TITLE");
		dateLabel = new JLabel("NULL DATE");
		moreButton = new JButton("▼");
		peopleLabel = new JLabel("NULL PEOPLE");
		memoLabel = new JLabel("memo");
		
		editButton = new JButton();
		delButton = new JButton();
		shareButton = new JButton();
		
	}

	public void arrangeCell(){

		abovePanel = new JPanel();
		JPanel aboveDataPanel = new JPanel();

		aboveDataPanel.setLayout(new GridLayout(4, 1));
		aboveDataPanel.add(titleLabel);
		aboveDataPanel.add(priorityLabel);
		aboveDataPanel.add(dateLabel);

		belowPanel = new JPanel();
		belowPanel.setLayout(new GridLayout(2, 1));
		belowPanel.add(peopleLabel);
		belowPanel.add(memoLabel);
		belowPanel.setPreferredSize(new Dimension(MainFrame.FRAME_WIDTH, CELL_OPEN_HEIGHT - CELL_HEIGHT));

		JPanel modifyPanel = new JPanel();
		modifyPanel.setLayout(new GridLayout(2, 1));
		
		JPanel buttonPanel  = new JPanel();
		buttonPanel.add(editButton);
		buttonPanel.add(delButton);
		buttonPanel.add(shareButton);
		
		modifyPanel.add(buttonPanel);
		modifyPanel.add(moreButton);
		// button setting
		editButton.setOpaque(false);
		editButton.setBorder(null);
		editButton.setBackground(new Color(0,0,0,0));
		editButton.setIcon(page.mainFrame.resourceManager.getIcon(ResourceManager.ICON_CELL_EDIT));
		
		delButton.setOpaque(false);
		delButton.setBorder(null);
		delButton.setBackground(new Color(0,0,0,0));
		delButton.setIcon(page.mainFrame.resourceManager.getIcon(ResourceManager.ICON_CELL_DELETE));
		
		shareButton.setOpaque(false);
		shareButton.setBorder(null);
		shareButton.setBackground(new Color(0,0,0,0));
		shareButton.setIcon(page.mainFrame.resourceManager.getIcon(ResourceManager.ICON_CELL_SHARE));
		// button setting
		
		BorderLayout bLayout = new BorderLayout();
		bLayout.setHgap(0);
		bLayout.setVgap(0);
		abovePanel.setLayout(bLayout);
		abovePanel.add(statusButton, BorderLayout.WEST);
		abovePanel.add(aboveDataPanel, BorderLayout.CENTER);
		abovePanel.add(modifyPanel, BorderLayout.EAST);
		
		this.setLayout(new GridLayout(1, 1));
		this.add(abovePanel);

		this.setPreferredSize(new Dimension(MainFrame.FRAME_WIDTH, CELL_HEIGHT));
		// Register Action Listener
		if (statusButton.getActionListeners() == null || statusButton.getActionListeners().length == 0){
			statusButton.addActionListener(this);
		}
		
		if (moreButton.getActionListeners() == null || moreButton.getActionListeners().length == 0){
			moreButton.addActionListener(this);
		}
		if (editButton.getActionListeners() == null || editButton.getActionListeners().length == 0){
			editButton.addActionListener(this);
		}
		if (delButton.getActionListeners() == null || delButton.getActionListeners().length == 0){
			delButton.addActionListener(this);
		}
		if (shareButton.getActionListeners() == null || shareButton.getActionListeners().length == 0){
			shareButton.addActionListener(this);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == moreButton){
			if (isFlipped){
				this.moreButton.setText("▲");
				this.setPreferredSize(new Dimension(MainFrame.FRAME_WIDTH, CELL_OPEN_HEIGHT));
				this.setLayout(new GridLayout(2, 1));
				this.add(belowPanel);
				this.revalidate();
				this.repaint();
				Log.info(this.getClass(), "open!");
			}
			else{
				this.moreButton.setText("▼");
				this.setLayout(new GridLayout(1, 1));
				this.remove(belowPanel);
				this.setPreferredSize(new Dimension(MainFrame.FRAME_WIDTH, CELL_HEIGHT));
				this.revalidate();
				this.repaint();
				Log.info(this.getClass(), "close!");
			}
			isFlipped = !isFlipped;
		}
		else if (e.getSource() == editButton){
			System.out.println("EDIT");
			page.controller.showEditFrame(todo);
		}
		else if (e.getSource() == delButton){
			System.out.println("DELETE");
			page.controller.removeToDo(this, todo);
		}
		else if (e.getSource() == shareButton){
			System.out.println("SHARE");
			page.mainFrame.uiBridge.requestShare(todo);
		}
		else if (e.getSource() == statusButton){
			System.out.println("STATUS");
			page.mainFrame.uiBridge.todoManager.changeToDoToReview(todo);
			page.controller.loadCellList();
			page.recreateCellPart();
			MsgBox.show("Success", todo.title + " 이 완료되었습니다");
		}
		
	}

	//GETTER AND SETTER
	public void setTodo(ToDo todo){
		this.todo = todo;
		this.setTitle(todo.title);
		this.setPriority(todo.priority);
		this.setPeople(todo.people);
		this.setDate(todo.dueTime);
		this.setMemo(todo.memo);
	}
	public ToDo getTodo(){return this.todo;}
	public void setTitle(String title){	titleLabel.setText(title);}
	public String getTitle(){return titleLabel.getText();}
	public void setPriority(int i){priorityLabel.setText(""+i);}
	public int getPriority(){return Integer.parseInt(priorityLabel.getText());}
	public void setDate(long date){
		this.date = date;
		int year = (int) (date/100000000);
		date -= (long)year * 100000000;
		int mon = (int) (date/1000000);
		date -= mon * 1000000;
		int day = (int) (date/10000);
		date -= day*10000;
		int hour = (int) (date/100);
		date -= hour*100;
		int min = (int) date;
		dateLabel.setText(String.format("%d년 %d월 %d일 %d시 %d분", year, mon, day, hour, min));
	}
	public long getDate(){return date;}
	public void setPeople(String[] people){
		String tmp = "";
		for (String name : people){
			tmp += name + " ";
		}
		peopleLabel.setText(tmp);
	}
	public String[] getPeople(){return null;}
	public void setMemo(String memo){memoLabel.setText(memo);}
	public String getMemo(){return memoLabel.getText();}

}
