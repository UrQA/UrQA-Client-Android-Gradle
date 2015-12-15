package com.urqa.eventpath;

public class EventPath {
	

	public EventPath(String datetime, String classname, String methodname, String label,
			int linenum) {
		super();
		this.datetime = datetime;
		this.classname = classname;
		this.methodname = methodname;
		this.linenum = linenum;
		this.label = label;
	}
	private String  datetime;
	private String 	classname;
	private String 	methodname;
	private String	label;
	private int		linenum;
	public String getClassName() {
		return classname;
	}
	public void setClassName(String className) {
		classname = className;
	}
	public String getMethodName() {
		return methodname;
	}
	public void setMethodName(String methodName) {
		methodname = methodName;
	}
	public int getLine() {
		return linenum;
	}
	public void setLine(int line) {
		this.linenum = line;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}