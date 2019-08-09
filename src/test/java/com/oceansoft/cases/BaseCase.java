package com.oceansoft.cases;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import org.apache.commons.logging.impl.Log4JLogger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.log.Log;
import com.oceansoft.executor.ApiExecutor;
import com.oceansoft.pojo.SQLData;
import com.oceansoft.utils.ApiInfoUtils;
import com.oceansoft.utils.CaseUtils;
import com.oceansoft.utils.DBCheckUtils;
import com.oceansoft.utils.ExcelUtils;

public abstract class BaseCase {
	public String[] cellName = {"CaseId","ApiId","Params","ValidateSql"};
	
	//创建日志对象
	private Logger Log = Logger.getLogger(BaseCase.class);
	
	//创建初始化方法
	@BeforeSuite
	public void init(){
		System.out.println("++++++++++开始执行套件++++++++++++++");
	}
	
	@Test(dataProvider = "datas")
	public void test(String caseId,String apiId ,String params,String validateSql) {
		String url = ApiInfoUtils.getUrlByApiId(apiId);
		String type = ApiInfoUtils.getTypeByApiId(apiId);
		//执行替换方法
		params = CaseUtils.replaceVariable(params);
		validateSql = CaseUtils.replaceVariable(validateSql);
		
		
		//执行接口调用之前的数据库操作
		DBCheckUtils.query(caseId, "BeforeResult", validateSql);
		//执行接口
		String result = ApiExecutor.doService(url, type, params);
		//执行接口调用之后的数据库操作
		DBCheckUtils.query(caseId, "AfterResult", validateSql);

		
		//批量回写
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
