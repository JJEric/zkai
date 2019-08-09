package com.oceansoft.cases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.oceansoft.executor.ApiExecutor;
import com.oceansoft.utils.ApiInfoUtils;
import com.oceansoft.utils.CaseUtils;

public class LoginCase extends BaseCase{

	@DataProvider(name = "datas")
	@Override
	public Object[][] datas() {
		Object[][] datas = CaseUtils.datas("2",cellName);
		return datas;
	}
}
