package com.lambo.smartpay.manage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * $Id$
 * 
 * Copyright (C)2002－2008 福建鑫诺.All rights reserved.
 * 
 * ExcelFileUtil.java
 * 
 * Original Author: 何则锐,2009-2-5
 * 
 * 文件功能说明: excel <文件功能说明> History 版本号 | 作者 | 修改时间 | 修改内容
 */

public class ExcelFileUtil {

	private static final Log log = LogFactory.getLog(ExcelFileUtil.class);

	@SuppressWarnings("unused")
	private static String FILE_DIRECTORY;

	private static String fileName = "";


	public static String getFile(String filePath) {
		String fileDir = filePath;
		new File(fileDir).mkdir();
		return fileDir;
	}

	/**
	 * 创建临时的文件
	 * 
	 * @param filePath
	 *            临时的文件
	 * @return File
	 */
	public static File createTempFile(String filePath, String name) {
		// String fileName = getFile(filePath) + File.separator
		// + Calendar.getInstance().getTimeInMillis();
		String fileName = getFile(filePath) + File.separator + name + "_"
				+ TimeUtil.getNowTimeFormate();
		File file = new File(fileName);
		return file;
	}
	
	public static File createTempFile1(String filePath, String name) {
		// String fileName = getFile(filePath) + File.separator
		// + Calendar.getInstance().getTimeInMillis();
		String fileName = getFile(filePath) + File.separator + name;
		File file = new File(fileName);
		return file;
	}

	/**
	 * 将List生成Excel文件,并返回Excel文件的输出流
	 * 
	 * @param title
	 *            标题
	 * @param list
	 *            文档内容
	 * @param excludeColumnIndex
	 *            是否包含代理商列
	 * @return 生成Excel文件的输出流
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static final File write(String title, String path, List list,
			int excludeColumnIndex, String name) {

		if (list == null || list.size() == 0) {
			return null;
		}

		File file = ExcelFileUtil.createTempFile1(path, name);
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		// 格式

		WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLUE);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		int sheetNum = 0;

		WritableSheet sheet = workbook.createSheet("Sheet" + sheetNum, 0);
		Label titleLabel = new Label(0, 0, title, cellFormat);
		try {
			if (titleLabel != null)
				sheet.addCell(titleLabel);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (WriteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		// 写文件
		int row = 1;
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Object[] objects = (Object[]) iterator.next();
			for (int column = 0; column < objects.length; column++) {
				Label label = null;
				if (row > 60000) {
					sheetNum++;
					sheet = workbook.createSheet("Sheet" + sheetNum, sheetNum);
					row = 1;
					WritableSheet newSheet = workbook.getSheet(0);
					Cell[] cells = newSheet.getRow(1);
					for (int j = 0; j < cells.length; j++) {
						Label newLabel = new Label(j, 0,
								cells[j].getContents(), cellFormat);
						try {
							sheet.addCell(newLabel);
						} catch (RowsExceededException e) {
							e.printStackTrace();
							log.error(e.getMessage());
						} catch (WriteException e) {
							e.printStackTrace();
							log.error(e.getMessage());
						}
					}
				}
				if (row == 1) {

					label = new Label(column, row,
							(objects[column] == null) ? "" : objects[column]
									+ "", cellFormat);
				} else {
					if (excludeColumnIndex == -1) {
						label = new Label(column, row,
								(objects[column] == null) ? ""
										: objects[column] + "");
					} else {
						if (column > excludeColumnIndex) {
							label = new Label(column - 1, row,
									(objects[column] == null) ? ""
											: objects[column] + "");
						} else if (column != excludeColumnIndex) {
							label = new Label(column, row,
									(objects[column] == null) ? ""
											: objects[column] + "");
						}
					}

				}
				try {
					if (label != null)
						sheet.addCell(label);
				} catch (RowsExceededException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				} catch (WriteException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
			row++;
		}

		// 保存
		try {
			workbook.write();
			workbook.close();
		} catch (WriteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return file;
	}
	
	/**
	 * 将List生成Excel文件,并返回Excel文件的输出流
	 * 
	 * @param title
	 *            标题
	 * @param list
	 *            文档内容
	 * @param excludeColumnIndex
	 *            是否包含代理商列
	 * @return 生成Excel文件的输出流
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static final File writeToOrg_xls(String title, String path, List list,
			int excludeColumnIndex, String name) {

		if (list == null || list.size() == 0) {
			return null;
		}

		File file = ExcelFileUtil.createTempFile1(path, name);
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		// 格式

		WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLUE);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		NumberFormat nf = new NumberFormat("0.00"); 
		NumberFormat ni = new NumberFormat("0");
		WritableCellFormat floatFormat = new WritableCellFormat(nf); 
		
		WritableCellFormat integerFormat = new WritableCellFormat(ni);
		int sheetNum = 0;

		WritableSheet sheet = workbook.createSheet("Sheet" + sheetNum, 0);
		String [] str = title.split(",");
		Label titleLabel = new Label(0, 0, str[0], cellFormat);
		Label titleLabe2 = new Label(1,0,str[1],cellFormat);
		try {
			floatFormat.setAlignment(Alignment.LEFT);
			integerFormat.setAlignment(Alignment.LEFT);
			cellFormat.setAlignment(Alignment.LEFT);
			if (titleLabel != null)
				sheet.addCell(titleLabel);
			if(titleLabe2 != null)
				sheet.addCell(titleLabe2);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (WriteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		// 写文件
		int row = 1;
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Object[] objects = (Object[]) iterator.next();
			for (int column = 0; column < objects.length; column++) {
				Label label = null;
				Number labelN = null;
				if (row > 60000) {
					sheetNum++;
					sheet = workbook.createSheet("Sheet" + sheetNum, sheetNum);
					row = 1;
					WritableSheet newSheet = workbook.getSheet(0);
					Cell[] cells = newSheet.getRow(1);
					for (int j = 0; j < cells.length; j++) {
						Label newLabel = new Label(j, 0,
								cells[j].getContents(), cellFormat);
						try {
							sheet.addCell(newLabel);
						} catch (RowsExceededException e) {
							e.printStackTrace();
							log.error(e.getMessage());
						} catch (WriteException e) {
							e.printStackTrace();
							log.error(e.getMessage());
						}
					}
				}
				if (row == 1) {

					label = new Label(column, row,
							(objects[column] == null) ? "" : objects[column]
									+ "", cellFormat);
				} else {
					if (excludeColumnIndex == -1) {
						label = new Label(column, row,
								(objects[column] == null) ? ""
										: objects[column] + "");
					} else {
						if (column > excludeColumnIndex) {
							label = new Label(column - 1, row,
									(objects[column] == null) ? ""
											: objects[column] + "");
						} else if (column != excludeColumnIndex) {
							label = new Label(column, row,
									(objects[column] == null) ? ""
											: objects[column] + "");
						}
					}

				}
				//数字 123451234512345 商户号
				//金融：20551174.35
				//电话059188882222
				if (row == 1) {
					label = new Label(column, row,(objects[column] == null) ? "" : objects[column] + "", cellFormat);
				} else {
					if (excludeColumnIndex == -1) {
						if(CommonVerify.checkNumber(objects[column].toString(),"0+")){
							labelN = new Number(column, row,(objects[column] == null) ? 0 : Double.parseDouble(objects[column].toString()),integerFormat);
						}
						else if(CommonVerify.checkFloat(objects[column].toString(),"0+")){
							labelN = new Number(column, row,(objects[column] == null) ? 0 : Double.parseDouble(objects[column].toString()),floatFormat);
						}else{
							label = new Label(column, row,(objects[column] == null) ? "" : objects[column] + "",cellFormat);
						}
					} else {
						if (column > excludeColumnIndex) {
							if(CommonVerify.checkNumber(objects[column].toString(),"0+")){
								labelN = new Number(column, row,(objects[column] == null) ? 0 : Double.parseDouble(objects[column].toString()),integerFormat);
							}
							else if(CommonVerify.checkFloat(objects[column].toString(),"0+")){
								labelN = new Number(column, row,(objects[column] == null) ? 0 : Double.parseDouble(objects[column].toString()),floatFormat);
							}else{
								label = new Label(column, row,(objects[column] == null) ? "" : objects[column] + "",cellFormat);
							}
						} else if (column != excludeColumnIndex) {
							if(CommonVerify.checkNumber(objects[column].toString(),"0+")){
								labelN = new Number(column, row,(objects[column] == null) ? 0 : Double.parseDouble(objects[column].toString()),integerFormat);
							}
							else if(CommonVerify.checkFloat(objects[column].toString(),"0+")){
								labelN = new Number(column, row,(objects[column] == null) ? 0 : Double.parseDouble(objects[column].toString()),floatFormat);
							}else{
								label = new Label(column, row,(objects[column] == null) ? "" : objects[column] + "",cellFormat);
							}
						}
					}

				}
				try {
					if(CommonVerify.checkNumber(objects[column].toString(),"0+")){
						sheet.addCell(labelN);
						if(objects[column].toString().length()>7){
							sheet.setColumnView(column, objects[column].toString().length()+5);
						}
					}
					else if(CommonVerify.checkFloat(objects[column].toString(),"0+")){
						sheet.addCell(labelN);
						if(objects[column].toString().length()>7){
							sheet.setColumnView(column, objects[column].toString().length()+5);
						}
					}else{
						if (label != null)
							sheet.addCell(label);
					}
					
				} catch (RowsExceededException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				} catch (WriteException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
			row++;
		}

		// 保存
		try {
			workbook.write();
			workbook.close();
		} catch (WriteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return file;
	}
	
	/**
	 * 将List生成Excel文件,并返回Excel文件的输出流
	 * 
	 * @param title
	 *            标题
	 * @param list
	 *            文档内容
	 * @param excludeColumnIndex
	 *            是否包含代理商列
	 * @param path 生成文件存放路径
	 * @param rmFilePath 汇总模板文件路径
	 * @return 生成Excel文件的输出流
	 * 
	 */
	public static final File writeToExcel(String title, String path, List<Map<String,String>> list,
			int excludeColumnIndex, String name,String rmFilePath) {

		if (list == null || list.size() == 0) {
			log.error("导出数据为空！");
			return null;
		}
		
		log.debug("要转excel文件的内容是："+list);
		
		jxl.Workbook rw = null;
		try {
			rw = jxl.Workbook.getWorkbook(new File(rmFilePath));
		} catch (BiffException e1) {
			log.error("取汇总表格模板出错！");
			e1.printStackTrace();
			log.error(e1.getMessage());
			return null;
		} catch (IOException e1) {
			log.error("取汇总表格模板出错！");
			e1.printStackTrace();
			log.error(e1.getMessage());
			return null;
		}
		
		File file = ExcelFileUtil.createTempFile(path, name);
		
		//创建可写入的Excel工作薄对象
		jxl.write.WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(file, rw);
		} catch (IOException e) {
			log.debug("创建工作薄时出现错误！");
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
		            
		//读取下载的一张工作表
		jxl.write.WritableSheet ws = wwb.getSheet(0);

		//获得第一个单元格对象
		@SuppressWarnings("unused")
		jxl.write.WritableCell wc = ws.getWritableCell(0, 0);
		            
		//System.out.println(wc.getContents());
		//System.out.println(wc.getType());
		//System.out.println(CellType.LABEL);
		Sheet rs = wwb.getSheet(0);
		//取第一行第一列的值
		Cell c00 = rs.getCell(0, 0);
		//判断单元格的类型, 做出相应的转化，如果第一行第一列不是Number则是不合格的电子模板
		if(c00.getType() != CellType.NUMBER){
			log.debug("汇总展现模板不标准，请在第一行第一列输入增加数据开始的行号！");
			return null;
		}
		//取已经被汇总表头占用的行数
		int num = Integer.parseInt(c00.getContents());
		//一个一个往后面插入行   
		//样式
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLUE);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		//实际表格从0开始算起
		num = num - 1;
		for(Map<String,String> map: list){
			Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
			int i = 0;
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				String key = entry.getKey();
		        String value = entry.getValue();
		        if(key.equals("ORGANNAME")){
		        	continue;
		        }
		        Label label = new Label(i, num,value,cellFormat);  
		        try {
					ws.addCell(label);
				} catch (RowsExceededException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				} catch (WriteException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
		        i++;
			}
			num++;
		}

		//更改标题
		Label label1 = new Label(0, 0,title,cellFormat);    
        try {
			ws.addCell(label1);
		} catch (RowsExceededException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
		} catch (WriteException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
		}

		// 保存
		try {
			//写入Excel对象
			wwb.write();
			//关闭可写入的Excel对象
			wwb.close();
			//关闭只读的Excel对象
			rw.close();
		} catch (WriteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return file;
	}

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		ExcelFileUtil.fileName = fileName;
	}

	/**
	 * 将Excel文件导入,返回list
	 * 
	 * @param excelFile
	 *            导入的Excel文件
	 * @param format
	 *            导入文件的格式列表,按一定顺序（即要求excel也是按这个格式 ）
	 * @param list
	 *            excel文件导入到list
	 * @param row
	 *           模板在第row行
	 * @return String 返回提示信息
	 */
	@SuppressWarnings("unchecked")
	public static final String readExcel(File excelFile, Map<String,Boolean> format,
			List<List<Object>> list,int row) {
		jxl.Workbook rwb;
		ClassPathResource resource = new ClassPathResource("");
		String tmpFilePath="";
            try {
                tmpFilePath = resource.getFile().getAbsolutePath()+ File.separator + "tmp.xls";
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            jxl.write.WritableWorkbook wbe;
            try {
                InputStream is = new FileInputStream(excelFile);
                rwb = Workbook.getWorkbook(is);
			 wbe= Workbook.createWorkbook(new File(tmpFilePath), rwb);//创建workbook的副本 -----新增
		} catch (Exception e) {
			e.printStackTrace();
            log.debug(e);
			return "请导入97-03版本的Excel文件！";
		}
		//Sheet rs = rwb.getSheet(0);
		Sheet rs = wbe.getSheet(0);
		int rsColumns = rs.getColumns();
		int rsRows = rs.getRows();
		Cell[] cell = rs.getRow(row);
		System.out.println("======"+cell.length);
		System.out.println("------"+format.size()); 
		if(cell==null || cell.length!=format.size()){
            return "导入的文件模板格式不对,请下载模板！";
        }
		if(rsRows<=row+1){
			return "没有数据可导入！";
		}
		for (int i = 0; i < cell.length; i++) {
			if (cell[i] == null) {
				log.debug("有字段为空");
				return "导入的文件格式不对,请下载模板！";
			}
//			if (null==format.get(cell[i].getContents().trim())) {
//				log.debug("导入文件第" + (i + 1) + "字段：" + cell[i].getContents());
//				return "导入的文件模板格式不对,请下载模板！";
//			}
		}
		Cell cij;
		for (int i = row+1; i < rsRows; i++) {
			List<Object> temp=new ArrayList();
			for (int j = 0; j < rsColumns; j++) {
				cij = rs.getCell(j, i);
//				if(format.get(rs.getCell(j, row).getContents().trim()) && cij.getContents().trim().equals("")){
//					return "第"+(i+1)+"行的‘"+rs.getCell(j, row).getContents().trim()+"'项不能为空，请输入！";
//				}
//				if(j == 0){
//					if(format.get(rs.getCell(0, row).getContents().trim()) && cij.getContents().trim().equals("")){
//						return "第"+(i+1)+"行的‘"+rs.getCell(j, row).getContents().trim()+"'项不能为空，请输入！";
//					}	
//				}
				if (cij.getType() == CellType.DATE) {
					DateCell datecij = (DateCell)cij;
					temp.add(datecij.getDate());
				}else{
					temp.add(cij.getContents().trim());
				}
			}
			list.add(temp);
			temp=null;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static final String readNumberExcel(File excelFile,List<List<HashMap<String,Object>>> list){
		jxl.Workbook rwb = null; 
		try{
			InputStream fis = new FileInputStream(excelFile);
			rwb =  Workbook.getWorkbook(fis);
		}catch(Exception e){
			log.debug(e);
			//return "";
		}
		List<HashMap<String,Object>> tempList = new ArrayList<HashMap<String,Object>>();
		Sheet sheet = rwb.getSheet(0);
		int shRows = sheet.getRows();//行值
		//int shColumns = sheet.getColumns();//列值
		if(shRows > 0){
			for(int i = 1;i<shRows;i++){
				Cell[] cell = sheet.getRow(i);//获取第 i 行的元素
				if(cell == null || cell.length == 0){
					continue;
				}
				if(("".equals(getCellString(cell,0)) || getCellString(cell,0)== null) && 
						("".equals(getCellString(cell,1)) || getCellString(cell,1)== null)){
					continue;
				}
				//判断号段
				if ( getCellString(cell, 1) != null && !"".equals(getCellString(cell, 1)))// 号段非空,检查该号是否已存在
				{
					HashMap map = new HashMap();
					map.put("index",getCellString(cell,0));//序号
					map.put("number",getCellString(cell,1));//号码段
					tempList.add(map);
				}
			}
			list.add(tempList);
		}
		return null;
		
	}
	/**获取行元素
	 * @param cell
	 * @param index
	 * @return
	 */
	private static String getCellString(jxl.Cell[] cell, int index) {

		try {
			String str = cell[index].getContents().toString();
			if ("null".equalsIgnoreCase(str.trim())||"".equalsIgnoreCase(str.trim())||str.trim()== null) {
				return null;
			}
			return str;
		} catch (Exception e) {
			return "";
		}

	}
	
	/**
	 * 将List<List<Object>>的对象转换为List<String>
	 *@author fwenron
	 * @param list 二维数组对象
	 * @param column 列
	 * @return
	 */
	public static List<String> changeStringList(List<List<Object>> list,int column){
		List<String> resultList = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			resultList.add(list.get(i).get(column).toString());
		}
		return resultList;
		
	}
	/**
	 * 导出详情的报表
	 * @param title 一级标题
	 * @param path
	 * @param master 主表
	 * @param colslist 列标题集合
	 * @param contentlist 普通列表
	 * @param filename 文件名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final File writeToDetail_xls(String[] title, String path,String master[],
			List colslist,List contentlist, String filename) {
		if((master ==null || master.length==0) 
			&& (contentlist ==null || contentlist.size()==0)){//无值返回
			return null;
		}
		File file = ExcelFileUtil.createTempFile1(path, filename);
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(file);
			int sheetNum = 0;
			WritableSheet ws = wwb.createSheet("Sheet" + sheetNum, 0);
			// 格式
			//以下定义格式
		    WritableFont font1=new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD); 
		    WritableCellFormat titleFormat=new WritableCellFormat(font1);
			titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
			titleFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);
			WritableCellFormat contentFormat=new WritableCellFormat();
			contentFormat.setAlignment(jxl.format.Alignment.CENTRE);
			contentFormat.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);    
			contentFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			int rows = 0;
			int cols = 0;
			if(master!=null && master.length>0){//主表有数据
				ws.mergeCells(0,0,7,0);//合并单元格
				ws.addCell(new jxl.write.Label(0, 0, title[0],titleFormat));//标题
				String[] cols0 = (String[])colslist.get(0);
				rows ++;
				for (int i=0;i<cols0.length;i++){//主表
			    	ws.mergeCells(cols,rows,cols+1,rows);
			    	ws.mergeCells(cols+2,rows,cols+3,rows);
			    	ws.addCell(new jxl.write.Label(cols, rows,cols0[i],titleFormat));//标题	
			    	ws.addCell(new jxl.write.Label(cols+2, rows,master[i],contentFormat));//数据
			    	if(i%2==1){
			    		rows ++;
			    		cols = 0;
			    	}	
			    	else if(i==cols0.length-1 && i%2==0){//用于奇数的主表信息
			    		rows ++;
			    	}
			    	else{
			    		cols = 4;
			    	}
				}
				rows ++;
				if(contentlist!=null && contentlist.size()>0){//普通列表必须有值
					for(int i=1;i<colslist.size();i++){
						cols0 = (String[])colslist.get(i);
						List tempList = (ArrayList)contentlist.get(i-1);//取一个列表、标题、列标题、列数据
						ws.mergeCells(0,rows,cols0.length*2-1,rows);
						ws.addCell(new jxl.write.Label(0,rows, title[i],titleFormat));
						rows ++;
						for (int j=0;j<cols0.length;j++){//标题
							ws.mergeCells(j*2,rows,j*2+1,rows);
					    	ws.addCell(new jxl.write.Label(j*2, rows,cols0[j],titleFormat));
						}
						rows ++;
						for(int j=0;j<tempList.size();j++){//数据
							HashMap map = (HashMap)tempList.get(j);
							for(int k=0;k<cols0.length;k++){
								ws.mergeCells(k*2,rows,k*2+1,rows);
								if(k==0){
									ws.addCell(new jxl.write.Label(0, rows,""+(j+1),contentFormat));
								}
								else{
									ws.addCell(new jxl.write.Label(k*2, rows,String.valueOf(map.get("obj"+(k-1))),contentFormat));
								}
							}
							rows ++;//行加1
						}
						rows += 2;
					}
				}
			}
			else{
				if(contentlist!=null && contentlist.size()>0){//普通列表必须有值
					for(int i=0;i<colslist.size();i++){
						String[] cols0 = (String[])colslist.get(i);
						List tempList = (ArrayList)contentlist.get(i);//取一个列表、标题、列标题、列数据
						ws.mergeCells(0,rows,cols0.length*2-1,rows);
						ws.addCell(new jxl.write.Label(0,rows, title[i],titleFormat));
						rows ++;
						for (int j=0;j<cols0.length;j++){//标题
							ws.mergeCells(j*2,rows,j*2+1,rows);
					    	ws.addCell(new jxl.write.Label(j*2, rows,cols0[j],titleFormat));
						}
						rows ++;
						for(int j=0;j<tempList.size();j++){//数据
							HashMap map = (HashMap)tempList.get(j);
							for(int k=0;k<cols0.length;k++){
								ws.mergeCells(k*2,rows,k*2+1,rows);
								if(k==0){
									ws.addCell(new jxl.write.Label(0, rows,""+(j+1),contentFormat));
								}
								else{
									ws.addCell(new jxl.write.Label(k*2, rows,String.valueOf(map.get("obj"+(k-1))),contentFormat));
								}
							}
							rows ++;//行加1
						}
						rows += 2;
					}
				}
			}
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return file;
	}
	/**
	 * 导出带有合并行头的表格
	 * @param title 定义好的行头列表
	 * @param path
	 * @param list
	 * @param xlsHeadList
	 * @param name
	 * @param writerow 开始写数据的行数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final File writeToMerge_xls(String title, String path, List list,
			 String name,int writerow,List<XlsHeadBean> xlsHeadList) {
		if (list == null || list.size() == 0) {
			return null;
		}
		if (xlsHeadList == null || xlsHeadList.size() == 0) {
			return null;
		}
		File file = ExcelFileUtil.createTempFile1(path, name);
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		// 格式
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLUE);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		int sheetNum = 0;
		WritableSheet sheet = workbook.createSheet("Sheet" + sheetNum, 0);
		String [] str = title.split(",");
		try {
			sheet.mergeCells(0, 0, 1, 0);
			Label titleLabel = new Label(0, 0, str[0], cellFormat);
			Label titleLabe2 = new Label(2,0,str[1],cellFormat);
			//标题
			if (titleLabel != null)
				sheet.addCell(titleLabel);
			if(titleLabe2 != null)
				sheet.addCell(titleLabe2);
			for (XlsHeadBean xh : xlsHeadList) {
				if(xh.isMergeFlag()){
					sheet.mergeCells(xh.getMeStartCols(),xh.getMeStartRows(),xh.getMeEndCols(),xh.getMeEndRows());
				}
				if(xh.getColumnView()>0){
					sheet.setColumnView(xh.getCols(), xh.getColumnView());
				}
				Label  tempLabel = new Label(xh.getCols(), xh.getRows(), xh.getCellName(), cellFormat);
				if (tempLabel != null){
					sheet.addCell(tempLabel);
				}
			}
			// 写文件
			int row = writerow;
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				for (int column = 0; column < objects.length; column++) {
					Label label = null;
					if (row > 60000) {
						sheetNum++;
						sheet = workbook.createSheet("Sheet" + sheetNum, sheetNum);
						row = 1;
						WritableSheet newSheet = workbook.getSheet(0);
						Cell[] cells = newSheet.getRow(1);
						for (int j = 0; j < cells.length; j++) {
							Label newLabel = new Label(j, 0,
									cells[j].getContents(), cellFormat);
							try {
								sheet.addCell(newLabel);
							} catch (RowsExceededException e) {
								e.printStackTrace();
								log.error(e.getMessage());
							} catch (WriteException e) {
								e.printStackTrace();
								log.error(e.getMessage());
							}
						}
					}else {
							label = new Label(column, row,
									(objects[column] == null) ? ""
											: objects[column] + "");
					}
					if (label != null){
						sheet.addCell(label);
					}
				}
				row++;
			}

			// 保存
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	/**
	 * 导出详情的报表
	 * @param title 一级标题
	 * @param path
	 * @param master 主表
	 * @param resultMap 列标题集合
	 * @param filename 文件名
	 * @return
	 */
	public static final File writeToSimpleDetail_xls(String title, String path,String master[],
			String[] resultMap,String filename) {
		if((master ==null || master.length==0) 
			&& (resultMap ==null || resultMap.length==0)){//无值返回
			return null;
		}
		File file = ExcelFileUtil.createTempFile1(path, filename);
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(file);
			int sheetNum = 0;
			WritableSheet ws = wwb.createSheet("Sheet" + sheetNum, 0);
			// 格式
			//以下定义格式
		    WritableFont font1=new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD); 
		    WritableCellFormat titleFormat=new WritableCellFormat(font1);
			titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
			titleFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);
			WritableCellFormat contentFormat=new WritableCellFormat();
			contentFormat.setAlignment(jxl.format.Alignment.CENTRE);
			contentFormat.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);    
			contentFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			int rows = 0;
			int cols = 0;
			if(master!=null && master.length>0){//主表有数据
				ws.mergeCells(0,0,7,0);//合并单元格
				ws.addCell(new jxl.write.Label(0, 0, title,titleFormat));//标题
				rows ++;
				for (int i=0;i<master.length;i++){//主表
			    	ws.mergeCells(cols,rows,cols+1,rows);
			    	ws.addCell(new jxl.write.Label(cols, rows,master[i],titleFormat));//标题
			    	ws.mergeCells(cols+2,rows,cols+3,rows);
			    	if(CommonVerify.checkFloat(resultMap[i].toString(),"0+") && resultMap[i].toString().length()<12){
			    		ws.addCell(new Number(cols+2, rows,(resultMap[i] == null) ? 0 : Double.parseDouble(resultMap[i].toString())));
			    	}
			    	else{
			    		ws.addCell(new jxl.write.Label(cols+2, rows,""+resultMap[i],contentFormat));//值
			    	}
			    	rows ++;
		    		cols = 0;
				}
			}
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return file;
	}
}
