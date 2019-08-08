package com.lemon.cases;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lemon.executor.ApiExecutor;
import com.lemon.executor.FormSubmitApiExcutor;
import com.lemon.executor.JsonSubmitApiExcutor;
import com.lemon.pojo.Case;
import com.lemon.utils.ApiInfoUtils;
import com.lemon.utils.CaseUtils;
import com.lemon.utils.ExcelUtils;
import com.lemon.utils.PropertiesUtils;

public class RegisterCase extends BaseCase{

		@DataProvider(name = "datas")
		@Override
		public Object[][] datas() {
			Object[][] datas = CaseUtils.datas("1", cellName);
			return datas;
		}
}
 	