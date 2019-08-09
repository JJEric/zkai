package com.oceansoft.pojo;

public class Variable {
	//Name(变量名)	Value(变量值)	Remarks(备注信息)
	private String name;
	private String value;
	private String remarks;
	
	
	
	public Variable() {
		super();
	}
	
	public Variable(String name, String value, String remarks) {
		super();
		this.name = name;
		this.value = value;
		this.remarks = remarks;
	}
	
	@Override
	public String toString() {
		return "Variable [name=" + name + ", value=" + value + ", remarks=" + remarks + "]";
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
