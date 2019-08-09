package com.oceansoft.utils;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import com.oceansoft.cases.WriteBackData;
import com.oceansoft.pojo.ApiInfo;
import com.oceansoft.pojo.Case;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {
	
	//1、2、3、4、5、6行          会变化的             数组int[] arrRow= {1,2,3,4,5,6};
	//2、5、6列          会变化的			数组int[] arrCell = {2,5,6};
	 public static String filePath = PropertiesUtils.prop.getProperty("api.excel.path");
	 public static Map<String, Integer> caseIdRownumMapping = new HashMap<String, Integer>();
	 public static Map<String, Integer> cellnameCellnumMapping = new HashMap<String, Integer>();
	 public static ArrayList<WriteBackData>  wbdList = new ArrayList<WriteBackData>(); 
	 static{
		 loadRowAndCellMapping();
	 }
	
	 
	 
	public static Object[][] read(String filePath,int[] rows,int[] cells) {
		Object[][] excelobject = new Object[rows.length][cells.length];
		FileInputStream fis = null;
		try {
			//创建流对象，找到excel文件
			fis = new FileInputStream(filePath);
			//双击打开
			Workbook workbook = WorkbookFactory.create(fis);
			//找到对应的sheet
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i < rows.length; i++) {		//循环row
				//获取row
				Row row = sheet.getRow(rows[i]);		//拿到1,4,6
				
				for (int j = 0; j < cells.length; j++) {		//循环cell  获取cell
					Cell cell = row.getCell(cells[j],MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);		
					String value = cell.getStringCellValue();
					excelobject[i][j] = value;
					}
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return excelobject;
	}
	
	 
	 
	 
	 
	/**
	 * 加载caseId和行号的映射          以及列名和列号的映射
	 */
	private static void loadRowAndCellMapping() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheet("用例");
			//cellnameCellnumMapping     cell名称和列号的映射
			Row titleRow = sheet.getRow(0);
			short lastCellNum = titleRow.getLastCellNum();
			int caseIdCellnum = 0;
			//遍历titleRow的所有列
			for (int i = 0; i < lastCellNum; i++) {
				Cell cell = titleRow.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				String cellname = cell.getStringCellValue();
				cellname = cellname.substring(0,cellname.indexOf("("));
				//如果当前的列名是caseId   获取对应的列号
				if ("CaseId".equals(cellname)) {
					caseIdCellnum = i;
				}
				//列名和列号映射存储
				cellnameCellnumMapping.put(cellname, i);
			}
			
			//caseIdRownumMapping    caseId和行号的映射
			int lastRowNum = sheet.getLastRowNum();
			//从第二行开始    遍历所有行
			for (int i = 1; i <= lastRowNum; i++) {
				Row row = sheet.getRow(i);
				//只获取CaseId列的值
				Cell cell = row.getCell(caseIdCellnum,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				String caseId = cell.getStringCellValue();
				//caseID和行号映射存储
				caseIdRownumMapping.put(caseId, i);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
}





	/**
	 * 读取excel中的指定sheet页数据
	 * @param filePath   excel文件路径
	 * @param sheetName		sheet名称
	 * @return
	 */
	public static <T> ArrayList<T> read(String sheetName ,Class<T> clazz) {
		ArrayList<T> list = new ArrayList<T>();
		FileInputStream fis = null;
		try {
			//创建流对象，找到excel文件
			fis = new FileInputStream(filePath);
			//双击打开
			Workbook workbook = WorkbookFactory.create(fis);
			//读取指定的sheet
			Sheet sheet = workbook.getSheet(sheetName);
			//先获取表头，就是获取sheet的第一行
			Row titleRow = sheet.getRow(0);
			short titleCellNum = titleRow.getLastCellNum();
			Object[] titleArray = new Object[titleCellNum];
			for (int i = 0; i < titleCellNum; i++) {
				Cell cell = titleRow.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				String value = cell.getStringCellValue();
				titleArray[i] = value.substring(0, value.indexOf("("));
				//titleArray[i] = value;
			}
			System.out.println(Arrays.toString(titleArray));
			System.out.println("===========================");
			
			//再读取内容
			int lastRowNum = sheet.getLastRowNum();
			Object[][] dataArray = new Object[lastRowNum][titleCellNum];
			for (int i = 1; i <= lastRowNum; i++) {			//读行
				Row dataRow = sheet.getRow(i);
				//Class<T> clazz = T.class;
				//反射创建对象
				T obj = clazz.newInstance();
				if (dataRow == null || isEmptyRow(dataRow)) {
					continue;
				}
				short dataCellNum = dataRow.getLastCellNum();
				for (int j = 0; j < dataCellNum; j++) {
					Cell cell = dataRow.getCell(j,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);
					String value = cell.getStringCellValue();
					//dataArray[i-1][j] = value;
					//通过反射获取setXXX方法,并且调用。
				String methodName = "set" + titleArray[j];
				//System.out.print(methodName + ",");
				Method method = clazz.getMethod(methodName, String.class);
				method.invoke(obj, value);
				}
				list.add(obj);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
	
	

	/**
	 * 判断excel   某一行是否为空
	 * @param row	
	 * @return
	 */
	private static boolean isEmptyRow(Row row) {
		short dataCellNum = row.getLastCellNum();
		for (int i = 0; i < dataCellNum; i++) {
			Cell cell = row.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cell.setCellType(CellType.STRING);
			String value = cell.getStringCellValue();
			//一次循环不能判断一行是否全部为空   但是可以判断一列是否有值
			if (StringUtils.isNotBlank(value)) {
				return false;
			}
		}
		return true;
}
	
	
	
	/**
	 * 					回写excel数据
	 * @param caseId	caseid
	 * @param cellName	列名
	 * @param result	回写的值
	 */
	/*public static void write(String caseId, String cellname, String result){
		//caseId   ->  rownum 
		Integer rownum = caseIdRownumMapping.get(wdb.getCaseId());
		//cellName -> cellnum
		Integer cellnum= cellnameCellnumMapping.get(wdb.getCellName());
		
		String result = wdb.getResult();
		
		write("用例", rownum,cellnum, result);
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 	根据指定的行号、列号。回写数据
	 * @param sheetName	表名
	 * @param rownum	行号
	 * @param cellnum	列号
	 * @param result	回写的值
	 */
/*	private static void write(String sheetName,int rownum,int cellnum,String result) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			//创建流对象，找到excel文件
			fis = new FileInputStream(filePath);
			//双击打开
			Workbook workbook = WorkbookFactory.create(fis);
			//读取指定的sheet
			Sheet sheet = workbook.getSheet(sheetName);
			//获取指定行
			Row row = sheet.getRow(rownum);
			//获取指定的列
			Cell cell = row.getCell(cellnum,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cell.setCellType(CellType.STRING);
			//回写数据
			cell.setCellValue(result);
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			//finally  不管try是否异常   都会执行finally
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}	
	}*/



	
	/**
	 * 批量回写 excel
	 */
	public static void batchWriteBack() {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			//创建流对象，找到excel文件
			fis = new FileInputStream(filePath);
			//双击打开
			Workbook workbook = WorkbookFactory.create(fis);
			//读取指定的sheet
			Sheet sheet = workbook.getSheet("用例");
			for (WriteBackData wbd : wbdList) {
				String caseId = wbd.getCaseId();
				String cellName = wbd.getCellName();
				String result = wbd.getResult();
				Integer rownum = caseIdRownumMapping.get(caseId);
				Integer cellnum= cellnameCellnumMapping.get(cellName);
				//获取指定行
				Row row = sheet.getRow(rownum);
				//获取指定的列
				Cell cell = row.getCell(cellnum,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				//回写数据
				cell.setCellValue(result);
			}
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			//finally  不管try是否异常   都会执行finally
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}	
		
	}

	

}
