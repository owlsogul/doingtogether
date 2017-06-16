package kr.ac.cau.jomingyu.doingtogether.ui.controller;

import java.util.LinkedHashMap;

import kr.ac.cau.jomingyu.doingtogether.server.ServerConstants;

public class Content {

	public String 
	writer,
	writeTime,
	title,
	priority,
	peopleStr,
	dueDate,
	memo;
	
	public Content(String writer, String writeTime, String title, String priority, String peopleStr, String dueDate, String memo){
		this.writer = writer;
		this.writeTime = writeTime;
		this.title = title;
		this.priority = priority;
		this.peopleStr = peopleStr;
		this.dueDate = dueDate;
		this.memo = memo;
	}
	
	public static Content createFromHashMap(LinkedHashMap<String, String> map){
		String writer = map.get(ServerConstants.KEY_TIMELINE_WRITER_ID);
		String writeTime = map.get(ServerConstants.KEY_TIMElINE_WRITE_TIME);
		String title = map.get(ServerConstants.KEY_TIMELINE_TITLE);
		String priority = map.get(ServerConstants.KEY_TIMELINE_PRIORITY);
		String peopleStr = map.get(ServerConstants.KEY_TIMELINE_PEOPLE).replaceAll(":", ", ");
		String dueDate = map.get(ServerConstants.KEY_TIMELINE_DUE_DATE);
		String memo = map.get(ServerConstants.KEY_TIMELINE_MEMO);
		return new Content(writer, writeTime, title, priority, peopleStr, dueDate, memo);
	}
}
