package kr.ac.cau.jomingyu.doingtogether.ui.controller;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import kr.ac.cau.jomingyu.doingtogether.todo.ToDo;
import kr.ac.cau.jomingyu.doingtogether.ui.MainFrame;
import kr.ac.cau.jomingyu.doingtogether.ui.PlaceHolderTextField;
import kr.ac.cau.jomingyu.doingtogether.ui.ToDoCell;
import kr.ac.cau.jomingyu.doingtogether.ui.page.HomePage;
import kr.ac.cau.jomingyu.doingtogether.ui.page.Page;
import kr.ac.cau.jomingyu.doingtogether.utility.MsgBox;

public class HomePageController extends PageController implements ActionListener{

	public ArrayList<ToDo> todoList;
	public HomePage page;
	
	public HomePageController(Page controllPage, MainFrame mainFrame) {
		super(controllPage, mainFrame);
		todoList = new ArrayList<>();
		page = (HomePage) controllPage;
	}

	@Override
	public void onCreate() {}

	@Override
	public void onDestroy() {}

	public void loadCellList(){
		page.clearCellList();
		todoList = mainFrame.uiBridge.todoManager.todoList;
		for (ToDo todo : todoList){
			page.addCell(todo);
		}
		page.updateCellPart();
	}
	
	@Override
	public void onShow() {
		loadCellList();
	}

	@Override
	public void onUnshow() {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton){
			JButton button = (JButton) e.getSource();
			if (button.getName().equalsIgnoreCase("Add")){
				showEditFrame(null);		
			}
			else if (button.getName().equalsIgnoreCase("Upload")){
				page.mainFrame.uiBridge.requestUpload();
			}
			else if (button.getName().equalsIgnoreCase("download")) {
				page.mainFrame.uiBridge.requestDownload();
			}
			else if (button.getName().equalsIgnoreCase("save")){
				page.mainFrame.uiBridge.saveDataToLocal();
			}
		}
	}
	
	public void showEditFrame(ToDo todo){
		EditFrame f = new EditFrame(this, todo);
		f.init();
	}

	public void removeToDo(ToDoCell toDoCell, ToDo todo) {
		page.mainFrame.uiBridge.todoManager.removeToDo(todo);
		page.cellList.remove(toDoCell);
		page.updateCellPart();
	}
	
	public void addToDo(ToDo todo){
		page.mainFrame.uiBridge.todoManager.addToDo(todo);
		page.addCell(todo);
	}
	
}
@SuppressWarnings("serial")
class EditFrame extends JFrame implements ActionListener, ChangeListener{
	HomePageController con;
	ToDo todo;
	public EditFrame(HomePageController con, ToDo todo){
		super("Edit ToDo");
		this.con = con;
		this.todo = todo;
		this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT/2);
		this.setVisible(true);
	}
	
	JButton addButton;
	
	JLabel titleLabel;
	JLabel priorityLabel;
	JLabel dueLabel;
	JLabel peopleLabel;
	JLabel memoLabel;
	
	PlaceHolderTextField titleField;
	JSlider prioritySlider;
	JLabel scoreLabel;
	
	PlaceHolderTextField yearField;
	PlaceHolderTextField monField;
	PlaceHolderTextField dayField;
	PlaceHolderTextField hourField;
	PlaceHolderTextField minField;
	
	PlaceHolderTextField peopleField;
	PlaceHolderTextField memoField;
	public void init(){
		Container con = this.getContentPane();
		GridLayout f = new GridLayout(12,1);
		con.setLayout(f);
		
		addButton = new JButton("ADD");
		addButton.addActionListener(this);
		con.add(addButton);
		
		titleLabel = new JLabel("Title");
		priorityLabel = new JLabel("Priority");
		dueLabel = new JLabel("Due Date");
		peopleLabel = new JLabel("People");
		memoLabel = new JLabel("Memo");
		
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		priorityLabel.setHorizontalAlignment(JLabel.CENTER);
		dueLabel.setHorizontalAlignment(JLabel.CENTER);
		peopleLabel.setHorizontalAlignment(JLabel.CENTER);
		memoLabel.setHorizontalAlignment(JLabel.CENTER);
		
		titleField = new PlaceHolderTextField("Title");
		prioritySlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
		prioritySlider.addChangeListener(this);
		scoreLabel = new JLabel(String.valueOf(prioritySlider.getValue()));
		JPanel duePanel = new JPanel();
		GridLayout g = new GridLayout(1, 5);
		g.setVgap(1);
		g.setHgap(1);
		duePanel.setLayout(g);
		yearField = new PlaceHolderTextField("YYYY");
		monField = new PlaceHolderTextField("MM");
		dayField = new PlaceHolderTextField("DD");
		hourField = new PlaceHolderTextField("HH");
		minField = new PlaceHolderTextField("MM");
		duePanel.add(yearField);
		duePanel.add(new JLabel("-"));
		duePanel.add(monField);
		duePanel.add(new JLabel("-"));
		duePanel.add(dayField);
		duePanel.add(new JLabel("-"));
		duePanel.add(hourField);
		duePanel.add(new JLabel("-"));
		duePanel.add(minField);
		peopleField = new PlaceHolderTextField(", 로 구분");
		memoField = new PlaceHolderTextField("memo");
		
		if (todo != null){
			addButton.setText("EDIT");
			
			titleField.setText(todo.title);
			prioritySlider.setValue(todo.priority);
			
			long tmp = todo.dueTime;
			long year = tmp/100000000;
			yearField.setText(String.valueOf(year));
			
			tmp -= year*100000000;
			long mon = tmp/1000000;
			monField.setText(String.valueOf(mon));
			
			tmp -= mon*1000000;
			long day = tmp/10000;
			dayField.setText(String.valueOf(day));
			
			tmp -= day*10000;
			long hour = tmp/100;
			hourField.setText(String.valueOf(hour));
			
			tmp -= hour*100;
			minField.setText(String.valueOf(tmp));
			
			String result = "";
			for (int i = 0; i < todo.people.length; i++){
				result += todo.people[i];
				if (i < todo.people.length - 1){
					result += ", ";
				}
			}
			peopleField.setText(result);
			memoField.setText(todo.memo);
		}

		con.add(titleLabel);
		con.add(titleField);
		con.add(priorityLabel);
		con.add(scoreLabel);
		con.add(prioritySlider);
		con.add(dueLabel);
		con.add(duePanel);
		con.add(peopleLabel);
		con.add(peopleField);
		con.add(memoLabel);
		con.add(memoField);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String title = titleField.getText();
		if (title.length() < 3){
			MsgBox.warning("TOO SHORT TITLE");
			return;
		}
		int priority = prioritySlider.getValue();
		long dueDate = 0;
		try{
		dueDate =
				Long.parseLong(yearField.getText()) * 100000000 + 
				Long.parseLong(monField.getText())  * 1000000 + 
				Long.parseLong(dayField.getText())  * 10000 + 
				Long.parseLong(hourField.getText()) * 100 + 
				Long.parseLong(minField.getText());
		} catch(NumberFormatException exception){
			MsgBox.warning("NOT NUMBER IN DUE DATE");
			return;
		}
		
		String[] people = peopleField.getText().split(",");
		String memo = memoField.getText();
		
		
		if (todo == null){
			System.out.println("현재 시간 구하기 :: by Calendar..!!");
			// (1) Calendar객체를 얻는다.
			Calendar cal = Calendar.getInstance();
			// (2) 출력 형태를 지정하기 위해 Formatter를 얻는다.
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
			// (3) 출력형태에 맞는 문자열을 얻는다.
			String datetime1 = sdf1.format(cal.getTime());
			System.out.println("--> " + datetime1);
			long writeTime = Long.parseLong(datetime1)/100;
			todo = new ToDo(title,priority, dueDate, writeTime, memo, people);
			con.addToDo(todo);
		}
		else{
			
			todo.title = title;
			todo.priority = priority;
			todo.dueTime = dueDate;
			todo.people = people;
			todo.memo = memo;
			
		}
		
		this.con.page.updateAllCell();
		this.dispose();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		scoreLabel.setText(String.valueOf(prioritySlider.getValue()));
	}
	
}
