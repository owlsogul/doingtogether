package kr.ac.cau.jomingyu.doingtogether.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

import kr.ac.cau.jomingyu.doingtogether.ui.UIBridge;
import kr.ac.cau.jomingyu.doingtogether.utility.JSONUtil;
import kr.ac.cau.jomingyu.doingtogether.utility.Log;
import kr.ac.cau.jomingyu.doingtogether.utility.MsgBox;

public class ServerBridge implements Runnable{

	public UIBridge uiBridge;
	public Socket socket;
	public Thread thread;
	public String id;
	public BufferedReader reader;
	public PrintWriter writer;
	public ServerBridge(UIBridge uiBridge, String host, int port) throws UnknownHostException, IOException{
		this.uiBridge = uiBridge;
		id = null;
		socket = new Socket(host, port);
		thread = new Thread(this);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream(), true);
		thread.start();
	}
	
	@Override
	public void run() {
		 while (true){
			 String rawData = receiveRawData();
			 Log.info(this.getClass(), "서버로부터 받아온 데이터 : " + rawData);
			 processInput(rawData);
		 }
	}
	
	public void processInput(String input){
		LinkedHashMap<String, String> map = JSONUtil.translateJsonToMap(input);
		String dataType = map.get(ServerConstants.KEY_DATATYPE);
		
		if (dataType.equalsIgnoreCase(ServerConstants.KEY_REGISTER)){
			uiBridge.responseRegister(map);
		}
		else if (dataType.equalsIgnoreCase(ServerConstants.KEY_LOGIN)){
			uiBridge.responseLogin(map);
		}
	}
	
	
	
	
	
	/**
	 * 서버로부터 데이터를 가져오는 메소드
	 * @return 서버로부터 가져온 String
	 * */
	public String receiveRawData() {
		try {
			String ret = reader.readLine();
			System.out.println("SocketIO: Read " + ret);
			return ret;
		} catch (IOException e) {
			System.out.println("1. " + e.getCause());
			System.out.println("2. " + e.getMessage()); // Connection reset
			e.printStackTrace();
			if (e.getMessage().equals("Connection reset")){
				MsgBox.show("ERROR", "서버와의 연결이 끊겼습니다. ㅠㅠ");
			}
			return null;
		}
	}
	
	/**
	 * 서버로 데이터를 보내는 메소드
	 * @return 서버로 보낸 String
	 * */
	public String sendRawData(String raw){
		System.out.println("SocketIO: Send " + raw);
		writer.println(raw);
		writer.flush();
		return raw;
	}
	
	
	
	
	
	
	
	
	
	
}
