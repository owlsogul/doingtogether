package kr.ac.cau.jomingyu.doingtogether.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.ac.cau.jomingyu.doingtogether.ui.controller.Content;

@SuppressWarnings("serial")
public class ContentPanel extends JPanel{
	
	public Content content;
	public JLabel contentLabel; // �ۼ��ڴ��� ������ �����ϼ̽��ϴ�.
	public JLabel writeTimeLabel; // �ۼ� : ----
	public JLabel titleLabel; // (����)
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
					"<html><strong>%s</strong> ���� �� ���� �����߽��ϴ�</html>", 
					content.writer));
			
			this.writeTimeLabel.setText(String.format(
					"<html>�ۼ� �ð� : %s</html>", 
					content.writeTime));
			this.titleLabel.setText(content.title);
			this.priorityLabel.setText(String.format("�߿䵵 : %s / 10", content.priority));
			if (content.peopleStr == null ||content.peopleStr.length() < 1){
				this.peopleLabel.setText("ȥ��");
			}
			else{
				this.peopleLabel.setText(String.format("%s �� ����", content.peopleStr));
			}
			this.dueLabel.setText(String.format("���� �ð� : %s", content.dueDate));
			this.memoLabel.setText(String.format("<html>MEMO<br>%s</html>", content.memo));
		}
	}
}