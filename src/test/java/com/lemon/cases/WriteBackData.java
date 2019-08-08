package com.lemon.cases;

public class WriteBackData {
	private String caseId;
	private String cellName;
	private String result;
	
	
	
	
	public WriteBackData() {
		super();
	}
	
	public WriteBackData(String caseId, String cellName, String result) {
		super();
		this.caseId = caseId;
		this.cellName = cellName;
		this.result = result;
	}
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "WriteBackData [caseId=" + caseId + ", cellName=" + cellName + ", result=" + result + "]";
	}
	
	
	
}
