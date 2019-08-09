package com.oceansoft.cases;

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

import com.oceansoft.executor.ApiExecutor;
import com.oceansoft.executor.FormSubmitApiExcutor;
import com.oceansoft.executor.JsonSubmitApiExcutor;
import com.oceansoft.pojo.Case;
import com.oceansoft.utils.ApiInfoUtils;
import com.oceansoft.utils.CaseUtils;
import com.oceansoft.utils.ExcelUtils;
import com.oceansoft.utils.PropertiesUtils;

public class RegisterCase extends BaseCase{

		@DataProvider(name = "datas")
		@Override
		public Object[][] datas() {
			Object[][] datas = CaseUtils.datas("1", cellName);
			return datas;
		}
}
 	