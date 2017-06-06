package test;

import java.io.IOException;

import kr.ac.cau.jomingyu.doingtogether.server.ServerBridge;

public class ServerBridgeTest {

	/** TEST를 위한 main 함수*/
	public static void main(String[] data){
		try {
			ServerBridge sb = new ServerBridge("localhost", 8765);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
