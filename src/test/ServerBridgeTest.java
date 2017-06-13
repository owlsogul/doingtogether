package test;

import java.io.IOException;
import java.util.LinkedHashMap;

import kr.ac.cau.jomingyu.doingtogether.server.ServerBridge;
import kr.ac.cau.jomingyu.doingtogether.utility.JSONUtil;

public class ServerBridgeTest {

	/** TEST를 위한 main 함수*/
	public static void main(String[] data){
		try {
			ServerBridge sb = new ServerBridge("165.194.17.32", 8765);
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("DATATYPE", "REGISTER");
			map.put("ID", "REGISTER");
			map.put("PW", "REGISTER");
			map.put("EMAIL", "REGISTER");
			map.put("NAME", "REGISTER");
			map.put("PHONE", "REGISTER");
			sb.sendRawData(JSONUtil.translateMapToJson(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
