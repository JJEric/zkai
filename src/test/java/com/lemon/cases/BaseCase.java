package com.lemon.cases;

import java.util.List;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.lemon.executor.ApiExecutor;
import com.lemon.pojo.SQLData;
import com.lemon.utils.ApiInfoUtils;
import com.lemon.utils.CaseUtils;
import com.lemon.utils.DBCheckUtils;
import com.lemon.utils.ExcelUtils;

public abstract class BaseCase {
	public String[] cellName = {"CaseId","ApiId","Params","ValidateSql"};
	
	
	@Test(dataProvider = "datas")
	public void test(String caseId,String apiId ,String params,String validateSql) {
		String url = ApiInfoUtils.getUrlByApiId(apiId);
		String type = ApiInfoUtils.getTypeByApiId(apiId);
		
		//执行接口调用之前的数据库操作
		DBCheckUtils.query(caseId, "BeforeResult", validateSql);
		
		String result = ApiExecutor.doService(url, type, params);
		
	
		//执行接口调用之后的数据库操作
		DBCheckUtils.query(caseId, "AfterResult", validateSql);

		
		//回写
		WriteBackData wbd = new WriteBackData(caseId, "ActualResponseData", result);
		ExcelUtils.wbdList.add(wbd);
		//ExcelUtils.write(wbd);
	}
	
	@AfterSuite
	public 	 void	batchWriteBack() {
			ExcelUtils.batchWriteBack();
	}

	//datas    获取数据的方法交给子类   继承父类来重写实现
	public abstract Object[][] datas();
		
	
}
