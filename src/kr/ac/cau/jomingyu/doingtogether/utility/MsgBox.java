package kr.ac.cau.jomingyu.doingtogether.utility;

import javax.swing.JOptionPane;

public class MsgBox {
	public static void show(String title, String msg){
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.DEFAULT_OPTION);
	}
	public static void warning(String msg){
		JOptionPane.showMessageDialog(null, msg, "WARNING", JOptionPane.WARNING_MESSAGE);
	}
}
