package com.oceansoft.utils;

import java.awt.List;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.oceansoft.cases.BaseCase;
import com.oceansoft.pojo.Case;
import com.oceansoft.pojo.Variable;

public class CaseUtils {
	
	//创建日志对象
	private Logger Log = Logger.getLogger(CaseUtils.class);
	public static ArrayList<Case> list = new ArrayList<Case>();
	public static ArrayList<Variable> variablesList = new ArrayList<Variable>();
	static{
		 list = ExcelUtils.read("用例", Case.class);
		 variablesList = ExcelUtils.read("变量", Variable.class);
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
	
	
	/**
	 * 					替换参数
	 * @param str		未替换参数的字符串
	 * @return			已经替换参数的字符串
	 */
	public static String replaceVariable(String str) {
		//str  ==   {"mobilephone":"${toBeRegisterMobilephone}","pwd":"${toBeRegisterPassword}"}
		for (Variable var : variablesList) {
			if (StringUtils.isNotBlank(str)) {
				if (str.contains(var.getName())) {
					str = str.replace(var.getName(),var.getValue());
				}
			}
		}
		return str;
	}
}
