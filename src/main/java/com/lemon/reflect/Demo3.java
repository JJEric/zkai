package com.lemon.reflect;

import java.lang.reflect.Method;

public class Demo3 {
	public static void main(String[] args) throws Exception {
		//1、获取字节码对象
			Class clazz1 = Class.forName("com.lemon.reflect.Case");
		//2、调用空参构造
			Object obj1 = clazz1.newInstance();
			Object obj2 = clazz1.newInstance();

		//获取指定方法
		Method method2 = clazz1.getMethod("setCaseId", String.class);
		Method method = clazz1.getMethod("getCaseId");
		
		//调用方法
		Object value1 = method2.invoke(obj1, "4396");
		Object value2 = method2.invoke(obj2, "443");
		
		Object value3 = method.invoke(obj1);
		Object value4 = method.invoke(obj2);
		System.out.println(value3);
		System.out.println(value4);
	}
}
