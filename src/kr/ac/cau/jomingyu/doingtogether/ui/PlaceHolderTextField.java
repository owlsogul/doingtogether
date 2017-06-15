package kr.ac.cau.jomingyu.doingtogether.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PlaceHolderTextField extends JTextField {
	
	public String placeHolder;
	public PlaceHolderTextField(String placeHolder){
		super();
		this.placeHolder = placeHolder;
	}
	
	public PlaceHolderTextField(int i, String placeHolder) {
		super(i);
		this.placeHolder = placeHolder;
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
	    super.paintComponent(g);

	    if(getText().isEmpty()){
	        Graphics2D g2 = (Graphics2D)g.create();
	        g2.setColor(Color.gray);
	        g2.setFont(getFont().deriveFont(Font.ITALIC));
	        g2.drawString(placeHolder, 5, 15); //figure out x, y from font's FontMetrics and size of component.
	        g2.dispose();
	    }
	  }
}
