package com.lemon.reflect;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class Demo2 {
	public static void main(String[] args) throws Exception {
		//通过反射创建对象
		
		//1、获取字节码对象
		Class clazz1 = Class.forName("com.lemon.reflect.Case");
		//2、调用空参构造
		Object obj1 = clazz1.newInstance();
		System.out.println(obj1);
		
		//通过其他构造方法创建对象
		//Constructor[] constructors = clazz1.getConstructors();
		//System.out.println(Arrays.toString(constructors));
		
		//3.1获得空参构造
		Constructor con = clazz1.getConstructor();
		Object obj2 = con.newInstance();
		System.out.println(obj2);
		
		//获取有参构造    创建对象
		Constructor con2 = clazz1.getConstructor(String.class,String.class,String.class,String.class);
		Object obj3 = con2.newInstance("1","2","3","4");
		System.out.println(obj3);
	}
	
	public void method(int ... arr) {		//可变参数   可以传0-n个参数
		
	}
}

