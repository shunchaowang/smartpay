/**
 * 
 */
package com.lambo.smartpay.manage.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * $Id$
 * 
 * Copyright (C)2002－2011 福建鑫诺.All rights reserved.
 * 
 * CommonVerify.java
 * 
 * Original Author: 方文荣,Jul 27, 2011
 * 
 * 文件功能说明: 常用的验证（后台数据验证） <文件功能说明> History 版本号 | 作者 | 修改时间 | 修改内容
 */
public class CommonVerify implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 585012051968648152L;

	/**
	 * 字符串 str在min和max之间 字符串必须存在
	 * 
	 * @author fwenron
	 * @param str
	 *            验证字符串
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return
	 */
	public static boolean isStringBetweenLen(String str, int min, int max) {
		if (str == null) {
			return false;
		}
		str = str.trim();
		if (str.length() <= max && str.length() >= min) {
			return true;
		}
		return false;
	}

	/**
	 * 验证是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		Pattern p = Pattern.compile("^[0-9]{1,30}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	/**
	 * 判断是否是带端口的IP
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isIP(String str) {
		Pattern p = Pattern
				.compile("^[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}[:][0-9]{1,5}$");
		Matcher m = p.matcher(str);
		if (m.matches() == true) {
			String[] str1 = str.split(":");
			for (int i = 0; i < str1.length; i++) {
				String[] str2 = str1[i].split("\\.");
				for (int j = 0; j < str2.length; j++) {
					if (str2[j].length() == 3) {
						if (Integer.valueOf(str2[j]) < 100) {
							return false;
						}
					} else if (str2[j].length() == 2) {
						if (Integer.valueOf(str2[j]) < 10) {
							return false;
						}
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证是否是手机号码 目前放开验证条件，只要满足：13，14，15，18开头的11位号码均能验证通过
	 * 
	 * @author fwenron
	 * @param num
	 *            手机号码
	 * @return
	 */
	public static boolean validPhoneNum(String num) {
		Pattern p1 = Pattern.compile("^(13|14|15|18)[0-9]{9}$");
		Matcher m1 = p1.matcher(num);
		if (m1.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证固定电话
	 * 
	 * @author wbl
	 * @param num
	 * @return
	 */
	public static boolean validSetPhoneNum(String num) {
		Pattern p = Pattern.compile("^([0-9]{3,4}-?)?[0-9]{7,9}$");
		Matcher m = p.matcher(num);
		return m.matches();
	}

	/**
	 * 验证编号 只能是数字和字母
	 * 
	 * @author fwenron
	 * @param num
	 *            编号
	 * @return
	 */
	public static boolean validCode(String num) {
		Pattern p1 = Pattern.compile("^[0-9a-zA-Z]{1,24}$");
		Matcher m1 = p1.matcher(num);
		if (m1.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证大于0的数字
	 * 
	 * @author fwenron
	 * @param num
	 *            数字
	 * @return
	 */
	public static boolean validNumber(String num) {
		try {
			if (Float.parseFloat(num) > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 验证卡号 首位不能为0
	 * 
	 * @author fwenrong
	 * @param number
	 * @return
	 */
	public static boolean validCardNumber(String number) {
		try {
			if (number == null || number.equals("")) {
				return false;
			}
			// 首位不为0不在此处理，程序中处理
			if (Long.parseLong(number) > 0) {
				return true;
			} else
				return false;
			/*
			 * Long num = Long.parseLong(number); if(num > 0 &&
			 * number.equals(Long.toString(num))){ return true; }else return
			 * false;
			 */
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 验证名称 只能是中文、数字和字母
	 * 
	 * @author fwenron
	 * @param name
	 *            名称
	 * @return
	 */
	public static boolean validName(String name) {
		Pattern p2 = Pattern
				.compile("^(([a-z]|[A-Z]|[0-9]|[\u4e00-\u9fa5]){1,30})$");
		Matcher m2 = p2.matcher(name);
		if (m2.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证IP，不含端口
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isIp(String name) {
		Pattern p2 = Pattern
				.compile("^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
		Matcher m2 = p2.matcher(name);
		if (m2.matches()) {
			return true;
		} else {
			return false;
		}
	}
	/**  
     * 检查整数  
     * @param num  
     * @param type "0+":非负整数 "+":正整数 "-0":非正整数 "-":负整数 "":整数  
     * @return  
     */  
    public static boolean checkNumber(String num,String type){   
        String eL = "";   
        if(type.equals("0+"))eL = "^\\d+$";//非负整数   
        else if(type.equals("+"))eL = "^\\d*[1-9]\\d*$";//正整数   
        else if(type.equals("-0"))eL = "^((-\\d+)|(0+))$";//非正整数   
        else if(type.equals("-"))eL = "^-\\d*[1-9]\\d*$";//负整数   
        else eL = "^-?\\d+$";//整数   
        Pattern p = Pattern.compile(eL);   
        Matcher m = p.matcher(num);   
        boolean b = m.matches();   
        return b;   
    }   
    /**  
     * 检查浮点数  
     * @param num  
     * @param type "0+":非负浮点数 "+":正浮点数 "-0":非正浮点数 "-":负浮点数 "":浮点数  
     * @return  
     */  
    public static boolean checkFloat(String num,String type){   
        String eL = "";   
        if(type.equals("0+"))eL = "^\\d+(\\.\\d+)?$";//非负浮点数   
        else if(type.equals("+"))eL = "^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$";//正浮点数   
        else if(type.equals("-0"))eL = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";//非正浮点数   
        else if(type.equals("-"))eL = "^(-((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*)))$";//负浮点数   
        else eL = "^(-?\\d+)(\\.\\d+)?$";//浮点数   
        Pattern p = Pattern.compile(eL);   
        Matcher m = p.matcher(num);   
        boolean b = m.matches();   
        return b;   
    }  
    public static void main(String[] args) {
		System.out.println(isNum("33.3"));
		System.out.println(checkFloat("33","+"));
	}
}
