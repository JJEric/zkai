package com.oceansoft.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	//当别人调用propertiesUtile的属性时，就加载config.properties文件
	public static Properties prop =new Properties();
	static{	
		//静态代码块     当这个类第一次被别人调用的时候    这段代码就会执行
 		try {
 			FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
