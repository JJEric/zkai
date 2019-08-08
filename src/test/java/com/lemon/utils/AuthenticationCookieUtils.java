package com.lemon.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

public class AuthenticationCookieUtils {
	public static Map<String, String> cookies = new HashMap<String, String>();
	public static final String RESPONSET_HEADER = "Set-Cookie";
	public static final String REQUEST_HEADER = "Cookie";
	public static final String COOKIE_NAME = "JSESSIONID";
	
	//抓取cookie   存到cookies map中
	public static void	getCookieByResponse(HttpResponse response) {
		//从响应头里面获取指定的头字段
		Header header = response.getFirstHeader(RESPONSET_HEADER);
		if (header != null) {
		//获取头字段的值
		String cookie = header.getValue();
		if (StringUtils.isNotBlank(cookie)) {
			String[] values = cookie.split(";");
			for (String value : values) {
				//如果包含JSESSIONID，就放入缓存cookies中
				if (value.contains(COOKIE_NAME)) {
					cookies.put(COOKIE_NAME,value);
				}
			}
		}
	}
}
	
	//把cookie添加到请求头中
	public static void addCookieInRequest(HttpRequest request) {
		String value = cookies.get(COOKIE_NAME);
		//如果cookie不为空  添加到请求头中
		if (StringUtils.isNotBlank(value)) {
			request.setHeader(REQUEST_HEADER,value);
		}
	}
}
