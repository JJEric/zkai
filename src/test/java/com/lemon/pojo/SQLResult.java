package com.lemon.pojo;

import java.util.HashMap;
import java.util.Map;

public class SQLResult {
	//[{no:"1",columnLabelsAndValues:{totalNum:0}}]
	private String no;
	//[]中括号在java中可以转成集合或者数据，{}大括号在java中可以转成对象或者是map
	private Map<String, Object> columnLabelsAndValues = new HashMap<String, Object>();
	
	
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Map<String, Object> getColumnLabelsAndValues() {
		return columnLabelsAndValues;
	}
	public void setColumnLabelsAndValues(Map<String, Object> columnLabelsAndValues) {
		this.columnLabelsAndValues = columnLabelsAndValues;
	}
	@Override
	public String toString() {
		return "SQLResult [no=" + no + ", columnLabelsAndValues=" + columnLabelsAndValues + "]";
	}
	
	
	
	
	
	
}
