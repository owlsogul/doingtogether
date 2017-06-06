package kr.ac.cau.jomingyu.doingtogether.todo;

public class ToDo {

	public String title;
	public int priority;
	
	public long dueTime;
	public long writeTime;
	
	public String[] people;
	public String memo;
	
	public ToDo(String title, int priority, long dueTime, long writeTime, String memo){
		this.title = title;
		this.priority = priority;
		this.dueTime = dueTime;
		this.writeTime = writeTime;
		this.memo = memo;
		this.people = null;
	}
	
	public ToDo(String title, int priority, long dueTime, long writeTime, String memo, String... people){
		this.title = title;
		this.priority = priority;
		this.dueTime = dueTime;
		this.writeTime = writeTime;
		this.memo = memo;
		this.people = people;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s:%d:%d:%d:%s", title, priority, dueTime, writeTime, memo);
	}
}