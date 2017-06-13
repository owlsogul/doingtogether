package kr.ac.cau.jomingyu.doingtogether.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import kr.ac.cau.jomingyu.doingtogether.utility.Log;

public class ServerBridge implements Runnable{

	public Socket socket;
	public Thread thread;
	public BufferedReader reader;
	public PrintWriter writer;
	public ServerBridge(String host, int port) throws UnknownHostException, IOException{
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
