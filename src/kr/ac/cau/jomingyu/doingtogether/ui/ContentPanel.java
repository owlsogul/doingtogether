package kr.ac.cau.jomingyu.doingtogether.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.ac.cau.jomingyu.doingtogether.ui.controller.Content;

@SuppressWarnings("serial")
public class ContentPanel extends JPanel{
	
	public Content content;
	public JLabel contentLabel; // 작성자님이 할일을 공유하셨습니다.
	public JLabel writeTimeLabel; // 작성 : ----
	public JLabel titleLabel; // (제목)
	public JLabel priorityLabel;
	public JLabel peopleLabel;
	public JLabel dueLabel;
	public JLabel memoLabel;
	
	public ContentPanel(Content content){
		contentLabel = new JLabel();
		writeTimeLabel = new JLabel();
		titleLabel = new JLabel();
		priorityLabel = new JLabel();
		peopleLabel = new JLabel();
		dueLabel = new JLabel();
		memoLabel = new JLabel();
		
		setContent(content);
		
		this.setLayout(new GridLayout(7, 1));
		this.add(contentLabel);
		this.add(writeTimeLabel);
		this.add(titleLabel);
		this.add(priorityLabel);
		this.add(peopleLabel);
		this.add(dueLabel);
		this.add(memoLabel);
	}
	
	public void setContent(Content content){
		this.content = content;
		if (content == null){
			this.contentLabel.setText("");
			this.writeTimeLabel.setText("");
			this.titleLabel.setText("");
			this.priorityLabel.setText("");
			this.peopleLabel.setText("");
			this.dueLabel.setText("");
			this.memoLabel.setText("");
		}
		else {
			
			this.contentLabel.setText(String.format(
					"<html><strong>%s</strong> 님이 할 일을 공유했습니다</html>", 
					content.writer));
			
			this.writeTimeLabel.setText(String.format(
					"<html>작성 시간 : %s</html>", 
					content.writeTime));
			this.titleLabel.setText(content.title);
			this.priorityLabel.setText(String.format("중요도 : %s / 10", content.priority));
			if (content.peopleStr == null ||content.peopleStr.length() < 1){
				this.peopleLabel.setText("혼자");
			}
			else{
				this.peopleLabel.setText(String.format("%s 와 같이", content.peopleStr));
			}
			this.dueLabel.setText(String.format("마감 시간 : %s", content.dueDate));
			this.memoLabel.setText(String.format("<html>MEMO<br>%s</html>", content.memo));
		}
	}
}