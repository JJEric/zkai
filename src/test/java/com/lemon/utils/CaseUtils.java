package com.lemon.utils;

import java.awt.List;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.lemon.pojo.Case;

public class CaseUtils {
	public static ArrayList<Case> list = new ArrayList<Case>();
	static{
		 list = ExcelUtils.read("用例", Case.class);
	}
	
	//把list转换成object[][] 二维数组
	public static Object[][] datas(String apiId,String[] cellName){
		ArrayList<Case> filterList;
		if (StringUtils.isEmpty(apiId)) {
			filterList = list;
		}else {
			filterList = filterCaseByApiId(apiId);
		}
		Object[][] datas = new Object[filterList.size()][cellName.length];
		for (int i = 0; i < filterList.size(); i++) {		//循环行
			Case case1 = filterList.get(i);
			for (int j = 0; j < cellName.length; j++) {		//循环列
				//使用反射调用get方法
				String methodName  = "get" + cellName[j];
				try {
					Method method = Case.class.getMethod(methodName);
					Object value = method.invoke(case1);
					datas[i][j] = value;
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		return datas;
	}
	
	public static ArrayList<Case> filterCaseByApiId(String apiId) {
		ArrayList<Case> filterList = new ArrayList<Case>();
		//遍历集合中所有的case
		for (Case case1 : list) {
			//判断传入的参数apiId    和集合Case中的apiId是否相等     相等的话就把case1放入 集合中
			if (apiId.equals(case1.getApiId())) {
				filterList.add(case1);
			}
		}
		return filterList;
	}
}
