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
		// Width �� 16
		// Height �� 39
		// �� �߰��� ������.
		//
		

	}

}
