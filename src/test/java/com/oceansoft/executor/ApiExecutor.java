package com.oceansoft.executor;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.oceansoft.utils.PropertiesUtils;

public class ApiExecutor {
	public static String doService(String url,String type,String params) {
		//读取配置文件中的content.type信息
		String contentType = PropertiesUtils.prop.getProperty("api.content.type");
		// 读取配置文件中 charset信息
		String chsrsetStr = PropertiesUtils.prop.getProperty("api.charset");
		//如果是form格式提交
		if ("form".equalsIgnoreCase(contentType)) {
			// json字符串转成List，可以先把json转成map，然后遍历map，把数据添加到List中。
			ArrayList<BasicNameValuePair> parameters = jsonParam2ListParam(params);
			//如果是get请求方式
			if ("get".equalsIgnoreCase(type)) {
				return FormSubmitApiExcutor.testByGet(url, parameters, chsrsetStr);
				//如果是post请求方式
			}else if ("post".equalsIgnoreCase(type)) {
				return FormSubmitApiExcutor.testByPost(url, parameters, chsrsetStr);	
			}
			//如果是json格式提交
		}else if ("json".equalsIgnoreCase(contentType)) {
			//如果是get请求方式
			if ("get".equalsIgnoreCase(type)) {
				return JsonSubmitApiExcutor.testByGet(url, params, chsrsetStr);
				//如果是post请求方式
			}else if ("post".equalsIgnoreCase(type)) {
				return JsonSubmitApiExcutor.testByPost(url, params, chsrsetStr);	
			}
		}
		return null;
	}

	
	public static ArrayList<BasicNameValuePair> jsonParam2ListParam(String params) {
		ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		// 把json字符串转成Map对象.
		Map<String, String> map = JSONObject.parseObject(params, Map.class);
		// 获取map中的所有key
		Set<String> keySet = map.keySet();
		// 遍历所有的key
		for (String key : keySet) {
			String value = map.get(key);
			parameters.add(new BasicNameValuePair(key, value));
		}
		return parameters;
	}
}
