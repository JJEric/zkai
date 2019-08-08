package com.lemon.cases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lemon.executor.ApiExecutor;
import com.lemon.utils.ApiInfoUtils;
import com.lemon.utils.CaseUtils;

public class LoginCase extends BaseCase{

	@DataProvider(name = "datas")
	@Override
	public Object[][] datas() {
		Object[][] datas = CaseUtils.datas("2",cellName);
		return datas;
	}
}
