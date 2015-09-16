package com.lambo.smartpay.manage.util;
/**
 * 
 * $Id$ 
 * 
 * Copyright (C)2002－2006 福建鑫诺.All rights reserved. 
 *
 * XlsBean.java
 * 
 * Original Author:廖流利,Nov 27, 2012
 *
 * 文件功能说明  
 * 		xls的属性
 * History
 * 版本号  |   作者   |  修改时间   |   修改内容
 */
public class XlsHeadBean {
	//值
	private String cellName = null;
	//所在行
	private int rows = 0;
	//所在列
	private int cols = 0;
	//是否做合并
	private boolean mergeFlag = false;
	//合并开始行
	private int meStartRows = 0;
	//合并结束行
	private int meEndRows = 0;
	//合并开始列
	private int meStartCols = 0;
	//合并结束列
	private int meEndCols = 0;
	//列表宽度
	private int columnView = 0;
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public boolean isMergeFlag() {
		return mergeFlag;
	}
	public void setMergeFlag(boolean mergeFlag) {
		this.mergeFlag = mergeFlag;
	}
	public int getMeStartRows() {
		return meStartRows;
	}
	public void setMeStartRows(int meStartRows) {
		this.meStartRows = meStartRows;
	}
	public int getMeEndRows() {
		return meEndRows;
	}
	public void setMeEndRows(int meEndRows) {
		this.meEndRows = meEndRows;
	}
	public int getMeStartCols() {
		return meStartCols;
	}
	public void setMeStartCols(int meStartCols) {
		this.meStartCols = meStartCols;
	}
	public int getMeEndCols() {
		return meEndCols;
	}
	public void setMeEndCols(int meEndCols) {
		this.meEndCols = meEndCols;
	}
	public int getColumnView() {
		return columnView;
	}
	public void setColumnView(int columnView) {
		this.columnView = columnView;
	}
}
