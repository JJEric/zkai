package com.lemon.cases;

import org.testng.annotations.DataProvider;

import com.lemon.utils.CaseUtils;

public class ReChargeCase extends BaseCase{
	
	@DataProvider(name = "datas")
	@Override
	public Object[][] datas() {
		Object[][] datas = CaseUtils.datas("3", cellName);
		return datas;
	}
}
