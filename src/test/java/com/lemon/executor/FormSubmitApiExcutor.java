package com.lemon.executor;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.lemon.utils.AuthenticationCookieUtils;

public class FormSubmitApiExcutor {
	
	
	
	public static String  testByGet(String url,ArrayList<BasicNameValuePair> params , String charset) {
		//1、创建请求连接
		String parameterString = URLEncodedUtils.format(params, charset);
		//String url = "http://test.lemonban.com/futureloan/mvc/api/member/login";
		//2、填入参数
		//String params = "mobilephone=13358192191&pwd=123456";
		url = url + "?" + parameterString;
		//3、创建http get请求
		HttpGet get = new HttpGet(url);
		//4、发送请求
		HttpClient client = HttpClients.createDefault();
		String result = "";
		try {
			get.setHeader("Content-Type","application/x-www-form-urlencoded;charset = " + charset);
			//4、1发出请求，获得响应对象
			AuthenticationCookieUtils.addCookieInRequest(get);
			HttpResponse response = client.execute(get);
			AuthenticationCookieUtils.getCookieByResponse(response);
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

	public static String testByPost(String url,ArrayList<BasicNameValuePair> parameters, String charset) {
		//1、创建request连接.
		//2、填写url和参数
		HttpPost post = new HttpPost(url);
		//2.1创建client对象
		HttpClient client = HttpClients.createDefault();
		String result = "";
		try {
			//3、绑定参数
			post.setEntity(new UrlEncodedFormEntity(parameters,charset));
			//4、发送请求	
			//4.1、发出请求获得响应对象
			AuthenticationCookieUtils.addCookieInRequest(post);
			HttpResponse response = client.execute(post);
			AuthenticationCookieUtils.getCookieByResponse(response);
			//5、获取响应报文
			//5.1、获取状态码
			int statusCode = response.getStatusLine().getStatusCode();//200 404
			//6、格式化
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			//6.1、获取头信息
			String allHeaders = Arrays.toString(response.getAllHeaders());
			System.out.println("状态码：" + statusCode);
			System.out.println("响应报文头信息：" + allHeaders);
			System.out.println("响应报文头报文：" + result);
			
		} catch (IOException e) {
			//IOException 是编译时异常。
			//处理异常的代码。
			e.printStackTrace();
		}
		return result;
	}
	
}
