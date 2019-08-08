package com.lemon.utils;

import java.awt.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.lemon.cases.WriteBackData;
import com.lemon.pojo.SQLData;
import com.lemon.pojo.SQLResult;

public class DBCheckUtils {

		public static void query(String caseId,String cellName,String validatesql) {
			if (StringUtils.isBlank(validatesql)) {
				return;
			}
			//解析JSON字符串
			java.util.List<SQLData> list = JSONObject.parseArray(validatesql,SQLData.class);
			ArrayList<SQLResult> results = new ArrayList<SQLResult>();
			
			//遍历List
			for (SQLData sqlData : list) {
				//查询数据库     ,dbutils
				QueryRunner qr  = new QueryRunner();
				Connection conn = JDBCUtils.getconnection();
				String sql = sqlData.getSql();
				String no = sqlData.getNo();
				try {
					Map<String, Object> queryMap = qr.query(conn, sql, new MapHandler());
					//System.out.println(queryMap);
					
					SQLResult sResult = new SQLResult();
					sResult.setNo(no);
					sResult.setColumnLabelsAndValues(queryMap);
					results.add(sResult);
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
			String jsonString = JSONObject.toJSONString(results);
			//System.out.println(jsonString);
			WriteBackData wbd = new WriteBackData(caseId, cellName, jsonString);
			ExcelUtils.wbdList.add(wbd);
		}
		
		
		
		
		/*public static void query() {
			QueryRunner qr  = new QueryRunner();
			Connection conn = JDBCUtils.getconnection();
			String sql = "select count(*) as totalNum from member where mobilephone='18813989555'";
			try {
				Map<String, Object> map = qr.query(conn, sql, new MapHandler());
				System.out.println(map);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public static void main(String[] args) {
			query();
		}*/
}
