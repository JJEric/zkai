package com.oceansoft.cases;

import org.testng.annotations.DataProvider;

import com.oceansoft.utils.CaseUtils;

public class ReChargeCase extends BaseCase{
	
	@DataProvider(name = "datas")
	@Override
	public Object[][] datas() {
		Object[][] datas = CaseUtils.datas("3", cellName);
		return datas;
	}
}
