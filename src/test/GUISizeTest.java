package test;

import javax.swing.JFrame;

public class GUISizeTest {

	public static void main(String[] args) {
		
		JFrame f = new JFrame("hi");
		f.setSize(400 + 16, 400 + 39);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println(f.getContentPane().getSize());
		//
		// Width 값 16
		// Height 값 39
		// 가 추가로 들어가야함.
		//
		

	}

}
