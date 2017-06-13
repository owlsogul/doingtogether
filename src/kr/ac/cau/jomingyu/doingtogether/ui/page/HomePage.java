package kr.ac.cau.jomingyu.doingtogether.ui.page;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import kr.ac.cau.jomingyu.doingtogether.todo.ToDo;
import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.ToDoCell;
import kr.ac.cau.jomingyu.doingtogether.ui.controller.HomePageController;

public class HomePage extends Page{

	private static final long serialVersionUID = 1L;

	/** �� ��Ʈ�ѷ� */
	public HomePageController controller;

	/** �� ��Ʈ�� ��ũ�Ѹ� �ϰ� �ϱ� ���� ��ũ�� �� */
	public JScrollPane cellScroller;
	/** �� ��ϵ� */
	public ArrayList<ToDoCell> cellList;
	/** ������ �׷����� JPanel */
	public JPanel cellPart;

	/** ��ư�� �׷����� Panel */
	public JPanel tabPart;
	public JButton addButton;
	public JButton deleteButton;

	public HomePage(MainFrame mainFrame, String name, ImageIcon icon) {
		super(mainFrame, name, icon);
		this.setLayout(new BorderLayout());

		cellList = new ArrayList<>();

		cellScroller = new JScrollPane();
		cellScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cellScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		cellPart = new JPanel();
		BoxLayout boxLayout = new BoxLayout(cellPart, BoxLayout.Y_AXIS);
		cellPart.setLayout(boxLayout);
		cellScroller.setViewportView(cellPart);

		tabPart = new JPanel();
		addButton = new JButton("ADD");
		addButton.setName("ADD");
		addButton.addActionListener(controller);
		deleteButton = new JButton("DEL");
		deleteButton.setName("DEL");
		deleteButton.addActionListener(controller);
		tabPart.add(addButton);
		tabPart.add(deleteButton);

		this.add(cellScroller, BorderLayout.CENTER);
		this.add(tabPart, BorderLayout.SOUTH);
	}
	
	//really table height
	int realTableHeight = 482;
	//
	
	public void updateAllCell(){
		for (ToDoCell cell : cellList){
			cell.setTodo(cell.getTodo());
		}
		updateCellPart();
		this.revalidate();
		this.repaint();
	}

	public void updateCellPart(){

		//setSize �� setPreferredSize �� ���̴� �����ΰ�...�Ф� �ѹ��� �ذ��ع�����..��
		cellPart.removeAll();
		int height = cellList.size() * ToDoCell.CELL_HEIGHT;
		cellPart.setSize(new Dimension(MainFrame.FRAME_WIDTH, realTableHeight));
		
		JPanel voidPanel = null;
		if (height < realTableHeight){
			voidPanel = new JPanel();
			voidPanel.setLayout(null);
			voidPanel.setPreferredSize(new Dimension(MainFrame.FRAME_WIDTH, realTableHeight - height));
		}

		for (ToDoCell cell : cellList){
			cellPart.add(cell);
		}

		if (voidPanel != null){
			cellPart.add(voidPanel);
			System.out.println(voidPanel.getSize());
		}
	}

	public void clearCellList(){
		this.cellList = new ArrayList<ToDoCell>();
		this.cellList.clear();
	}

	public void addCell(ToDo todo){
		ToDoCell cell = new ToDoCell(this);
		cell.setTodo(todo);
		cellList.add(cell);
		cell.arrangeCell();
	}

	@Override
	public void setController() {
		this.pageController = new HomePageController(this, mainFrame);
		this.controller = (HomePageController) pageController;
	}
}