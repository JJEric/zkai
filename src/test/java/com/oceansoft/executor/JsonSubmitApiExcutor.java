package com.oceansoft.executor;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class JsonSubmitApiExcutor {
	
	
	
	public static String  testByGet(String url,String parameters , String charset) {

		//1、创建请求连接
		//String parameterString = URLEncodedUtils.format(params, charset);
		String parameterString = "";
		//把json字符串转成map  ,然后遍历map,再把map添加到list中去
		Map map = JSONObject.parseObject(parameters,Map.class);
		/*
		 * 获取map中的所有的key
		 */
		Set<String> keySet = map.keySet();
		//遍历map,根据key来获取value
		for (String key : keySet) {
			//根据key来获取value
			String value = (String) map.get(key);
			//判断parameterString是否为空，并且拼接parameterString
			if(!"".equals(parameterString)) {
				parameterString += "&" ;
			}
			parameterString += key + "=" + value;
		}
		//2、填入参数
		//String params = "mobilephone=13358192191&pwd=123456";
		url += "?" + parameterString;
		//3、创建http get请求
		HttpGet get = new HttpGet(url);
		
		//4、发送请求
		HttpClient client = HttpClients.createDefault();
		String result = "";
		try {
			//get请求设置请求头，设置为json的方式
			get.setHeader("Content-Type","application/json;charset=UTF-8");
			//4、1发出请求，获得响应对象
			HttpResponse response = client.execute(get);
			//4、2根据响应对象，获取状态码
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("状态码:" + statusCode );
			//格式化   输出响应头信息
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			System.out.println("响应头结果:" + result);
			//4.3根据响应对象，查看响应头信息
			String headerInfo = Arrays.toString(response.getAllHeaders());
			System.out.println("响应头信息：" + headerInfo);
		} catch (IOException e) {
			//IOException 是编译时异常。
			//处理异常的代码。
			e.printStackTrace();	
		}
		return result;
	}

	public static String testByPost(String url,String parameters, String charset) {
		//1、创建request连接.
		//2、填写url和参数
		HttpPost post = new HttpPost(url);
		//2.1创建client对象
		HttpClient client = HttpClients.createDefault();
		String result ="";
		try {
			//3、绑定参数
			//3.1、设置请求头，以表单的方式提交参数
			post.setHeader("Content-Type","application/json;charset=UTF-8");
			/*HashMap<String, String> map = new HashMap<String, String>();
			if (parameters != null) {
				for (BasicNameValuePair bnv : parameters) {
					map.put(bnv.getName(), bnv.getValue());
				}
			}*/
			//把map 转成json字符串
			//String jsonString = JSONObject.toJSONString(map);
			post.setEntity(new StringEntity(parameters,charset));
			//4、发送请求	
			//4.1、发出请求获得响应对象
			HttpResponse response = client.execute(post);
			//5、获取响应报文
			//5.1、获取状态码
			//链式编程 相当于下面两行代码
			int statusCode = response.getStatusLine().getStatusCode();	//200 404
			//6、格式化
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			//6.1、获取头信息
			String allHeaders = Arrays.toString(response.getAllHeaders());
			// ctrl + 2 + l
			System.out.println("响应报文头信息：" + allHeaders);
			System.out.println("状态码：" + statusCode);
			System.out.println("响应报文头报文：" + result);
			
		} catch (IOException e) {
			//IOException 是编译时异常。
			//处理异常的代码。
			e.printStackTrace();
		}
		return result;
	}	
}
