package com.lemon.reflect;

import com.lemon.pojo.Case;

public class Demo {
	public static void main(String[] args) throws Exception {
		//反射	就是根据一些特性（字节码对象）信息，创建对象
		Case case1 = new Case();
		
		
		//第一种     大多数情况使用第一种
		Class clazz1 = Class.forName("com.lemon.reflect.Case");
		
		//第二种
		Class clazz2 = Case.class;
		
		//第三种
		Class clazz3 = case1.getClass();
		
		System.out.println(clazz1);
		System.out.println(clazz2);
		System.out.println(clazz3);

	}
}
