package com.lemon.utils;

import java.util.ArrayList;
import java.util.List;

import com.lemon.pojo.ApiInfo;
import com.lemon.pojo.Case;

public class ApiInfoUtils {
	public static ArrayList<ApiInfo> list = new ArrayList<ApiInfo>();
	static{
		 list = ExcelUtils.read("接口信息", ApiInfo.class);
	}
	
	
	public static String getUrlByApiId(String apiId) {
		if (apiId == null) {
			return null;
		}
		for (ApiInfo apiInfo : list) {
			if (apiId.equals(apiInfo.getApiId())) {
				return apiInfo.getUrl();
			}
		}
		return null;
	}
	
	
	public static String getTypeByApiId(String apiId) {
		if (apiId == null) {
			return null;
		}
		for (ApiInfo apiInfo : list) {
			if (apiId.equals(apiInfo.getApiId())) {
				return apiInfo.getType();
			}
		}
		return null;
	}
	
	
	
	
}
