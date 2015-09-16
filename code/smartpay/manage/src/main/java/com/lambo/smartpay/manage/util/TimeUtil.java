package com.lambo.smartpay.manage.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * 功能说明: 时间工具类
 * 
 * TimeUtil.java
 * 
 * Original Author: 郑庆铸,Mar 5, 2012
 * 
 * Copyright (C)2002－2010 福建鑫诺.All rights reserved.
 * 
 */
public class TimeUtil {

	/**
	 * 系统的日期时间格式 格式 yyyy-MM-dd HH:mm:ss
	 */
	public final static SimpleDateFormat SYS_DATE_TIME_CT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 系统的日期格式 格式 yyyy-MM-dd
	 */
	public final static SimpleDateFormat SYS_DATE_CT = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 系统的日期格式 格式 yyyyMMddHHmmss
	 */
	public final static SimpleDateFormat SYS_DATE_YEAR_MONTH_HOUR_MIN_CT = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	/**
	 * 系统的日期格式 格式 yyMMddHHmmss
	 */
	public final static SimpleDateFormat SYS_DATE_SHORT_YEAR_MONTH_HOUR_MIN_CT = new SimpleDateFormat(
			"yyMMddHHmmss");

	/**
	 * 系统的日期格式 格式 yyyyMMdd
	 */
	public final static SimpleDateFormat SYS_DATE_YEAR_MONTH_CT = new SimpleDateFormat(
			"yyyyMMdd");

	/**
	 * 系统的日期格式 格式 yyMMdd
	 */
	public final static SimpleDateFormat SYS_DATE_CT_SHORT_YEAR = new SimpleDateFormat(
			"yyMMdd");

	/**
	 * 系统的日期格式 格式 yyyy-MM
	 */
	public final static SimpleDateFormat SYS_DATE_MONTH_CT = new SimpleDateFormat(
			"yyyy-MM");
	/**
	 * 系统的日期格式 格式 yyyyMM
	 */
	public final static SimpleDateFormat SYS_DATE_SHORT_MONTH_CT = new SimpleDateFormat(
			"yyyyMM");

	/**
	 * 系统的日期格式 格式 yyMM
	 */
	public final static SimpleDateFormat SYS_DATE_YEARMONTH = new SimpleDateFormat(
			"yyMM");

	/**
	 * 系统的日期 年份 格式 yy
	 */
	public final static SimpleDateFormat SYS_DATE_YEAR = new SimpleDateFormat(
			"yy");
	/**
	 * 系统的日期 月份 格式 MM
	 */
	public final static SimpleDateFormat SYS_DATE_MONTH = new SimpleDateFormat(
			"MM");

	/**
	 * 系统日期时间格式 xx月xx日 hh:mm:ss
	 */
	private final static SimpleDateFormat SYS_MON_DATE_TIME = new SimpleDateFormat(
			"MM月dd日 HH:mm:ss");

	/**
	 * 
	 * Description: 获取当前时间
	 * 
	 * @return Timestamp对象 当前时间
	 * @exception
	 */
	public static Timestamp getNowTime() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public static String getNow() {
		return SYS_DATE_YEAR_MONTH_HOUR_MIN_CT.format(new Date());
	}

	/**
	 * 将时间转换成(yyyy-MM-dd HH:mm:ss)的时间字串符格式。
	 * 
	 * @param date
	 *            日期对象
	 * 
	 * @return 返回日期/时间字符串
	 */
	public static String DateToStrFormat(Date date) {
		return SYS_DATE_TIME_CT.format(date);
	}

	/**
	 * 将时间转换成(MM月dd日 HH:mm:ss)格式
	 * 
	 * @param date
	 * @return
	 */
	public static String DateToShortDateStrFormat(Date date) {
		return SYS_MON_DATE_TIME.format(date);
	}

	/**
	 * 日期格式输出 yyyy-MM-dd
	 * 
	 * @param date
	 *            日期对象
	 * 
	 * @return
	 */
	public static String DateToShortStrFormat(Date date) {
		return SYS_DATE_CT.format(date);
	}

	/**
	 * 
	 * Description: 根据Timestamp对象获取年月日。以数组形式返回
	 * 
	 * @param timestamp
	 * @return Object数组 obj[0]：年份 obj[1]：月份 obj[2]：天
	 * @exception
	 */
	public static String[] getYMDFromTimestamp(Timestamp timestamp) {
		String[] str = new String[3];
		str[0] = new SimpleDateFormat("yyyy").format(timestamp);
		str[1] = new SimpleDateFormat("MM").format(timestamp);
		str[2] = new SimpleDateFormat("dd").format(timestamp);
		return str;
	}

	/**
	 * 
	 * Description: 获取年份
	 * 
	 * @param timestamp
	 * @return String对象,如2004
	 * @exception
	 */
	public static String getYear(Timestamp timestamp) {
		return TimeUtil.getYMDFromTimestamp(timestamp)[0];
	}

	/**
	 * 
	 * Description: 获取月份
	 * 
	 * @param timestamp
	 * @return String对象,如12
	 * @exception
	 */
	public static String getMonth(Timestamp timestamp) {
		return TimeUtil.getYMDFromTimestamp(timestamp)[1];
	}

	/**
	 * 
	 * Description: 获取天
	 * 
	 * @param timestamp
	 * @return String对象,如31
	 * @exception
	 */
	public static String getDay(Timestamp timestamp) {
		return TimeUtil.getYMDFromTimestamp(timestamp)[2];
	}

	/**
	 * 
	 * Description: 判断是否是同一天
	 * 
	 * @param time1
	 * @param time2
	 * @return true:是同一天 false:非同一天
	 * @exception
	 */
	public static boolean isTheSameDate(Timestamp time1, Timestamp time2) {
		if (time1 != null && time2 != null) {
			String date1 = new SimpleDateFormat("yyyy-MM-dd").format(time1);
			String date2 = new SimpleDateFormat("yyyy-MM-dd").format(time2);
			return date1.equals(date2);
		}
		return false;
	}

	/**
	 * 
	 * Description: 格式化时间输出yy-MM-dd HH点mm分ss秒
	 * 
	 * @return
	 * @exception
	 */
	public static String getNowTimeFormate() {
		Timestamp timestamp = new Timestamp(Calendar.getInstance()
				.getTimeInMillis());
		return new SimpleDateFormat("yy-MM-dd HH点mm分ss秒").format(timestamp);
	}

	/**
	 * 
	 * Description: 获得date前num天的时间
	 * 
	 * @param date
	 *            时间
	 * @param num
	 *            天数
	 * @return
	 * @exception
	 */
	public static Date getPreviousDay(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -num);
		return cal.getTime();
	}

	/**
	 * 
	 * Description: 获得date后num天的时间
	 * 
	 * @param date
	 *            时间
	 * @param num
	 *            天数
	 * @return
	 * @exception
	 */
	public static Date getLaterDay(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, +num);
		return cal.getTime();
	}

	/**
	 * 获取 date 往前 或 往后 num 小时
	 * 
	 * @param date
	 * @param num
	 *            类型为int ，小时数字 可正可负
	 * @return Date
	 */
	public static Date getVaryHour(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, num);
		return cal.getTime();
	}

	/**
	 * 
	 * Description: 查询时用的开始时间
	 * 
	 * @param date
	 * @return
	 * @exception
	 */
	public static Timestamp getStartTime(Date date) {
		if (date != null) {
			Calendar dateCalendar = Calendar.getInstance();
			dateCalendar.setTime(date);
			Calendar calendar = new GregorianCalendar(dateCalendar
					.get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH),
					dateCalendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			return new Timestamp(calendar.getTimeInMillis());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * Description: 查询时用的结束时间
	 * 
	 * @param date
	 * @return
	 * @exception
	 */
	public static Timestamp getEndTime(Date date) {
		if (date != null) {
			Calendar dateCalendar = Calendar.getInstance();
			dateCalendar.setTime(date);
			Calendar calendar = new GregorianCalendar(dateCalendar
					.get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH),
					dateCalendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			return new Timestamp(calendar.getTimeInMillis());
		} else {
			return null;
		}
	}

	/**
	 * 获取当前日期是星期几
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 把yyyyMMddHHmmss格式转化成yyyy-mm-dd hh:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(date.substring(0, 4)); // 年
		sb.append("-");
		sb.append(date.substring(4, 6)); // 月
		sb.append("-");
		sb.append(date.substring(6, 8)); // 日
		sb.append(" ");
		sb.append(date.substring(8, 10)); // 时
		sb.append(":");
		sb.append(date.substring(10, 12)); // 分
		sb.append(":");
		sb.append(date.substring(12, 14)); // 秒
		return sb.toString();
	}
	/**
	 * 把yyyyMMddHHmmss格式转化成yyyy-mm-dd hh:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatStrDate(String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(date.substring(0, 4)); // 年
		sb.append("-");
		sb.append(date.substring(4, 6)); // 月
		sb.append("-");
		sb.append(date.substring(6, 8)); // 日
		return sb.toString();
	}
	/* wangbl add start */
	/**
	 * 将日期字符串格式化成(yyyy-MM-dd)字符串.
	 * 
	 * @param str
	 *            日期格式的字符串。
	 * @return 返回日期字符串。格式:2010-08-08
	 */
	public static String dateStrFormatString(String str) {
		SimpleDateFormat formattxt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = TimeUtil.dateStrFormatDate(str);
		return formattxt.format(date);
	}

	/**
	 * 将日期字符串格式化成(yyyy-MM-dd HH:mm:ss)字符串.
	 * 
	 * @param str
	 *            日期格式的字符串。
	 * @return 返回日期字符串。格式:2010-08-08 12:12:12
	 */
	public static String dataStrFullFormatString(String str) {
		SimpleDateFormat formattxt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = TimeUtil.dateStrFormatDate(str);
		return formattxt.format(date);
	}

	/**
	 * 将时间转换成(yyyy-MM-dd HH:mm:ss)的时间字串符格式。
	 * 
	 * @param date
	 *            日期对象
	 * @return 返回日期/时间字符串
	 */
	public static String dateFormatDateStr(Date date) {
		SimpleDateFormat formattxt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formattxt.format(date);
	}

	/**
	 * 将日期字符串转换成日期
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 返回日期.如:2010-08-08 12:12:12
	 */
	public static Date dateStrFormatDate(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 将日期字符串转换成日期
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 返回日期.如:2010-08-08
	 */
	public static Date StrFormatDate(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 
	 * Description: 将字符串转成日期2
	 * 
	 * @param str
	 *            yyyyMMddHHmmss
	 * @return
	 * @exception
	 */
	public static Date conStr3Date(String str) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Date parse = sf.parse(str);
			return parse;
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}

	}
	public static Date conStrDate(String str) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		try {
			Date parse = sf.parse(str);
			return parse;
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}

	}
	/* wangbl add end */

	/**
	 * 获取当前时间(14位字符串)
	 */
	public static String getNowTime14() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * 获取 年-月(yyyy-MM)格式的日期
	 * 
	 * @param date
	 * @return 格式 yyyy-MM
	 */
	public static Date getMonth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date rest = null;
		try {
			rest = sdf.parse(TimeUtil.SYS_DATE_MONTH_CT.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rest;
	}

	/**
	 * 获取每个月的 第一刻时间 如 2012-01-01 00:00:00
	 * 
	 *
	 *
	 */
	public static Date getFirstTimeOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		/*
		 * cal.set(Calendar.YEAR, year); cal.set(Calendar.MONTH, month);
		 * cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DATE));
		 * cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0);
		 * cal.set(Calendar.SECOND, 0);
		 */
		return cal.getTime();
	}

	/**
	 * 获取每个月的最后一刻时间 如2012-01-31 23:59:59
	 * 
	 *
	 * @return String
	 */
	public static Date getLastTimeOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		// cal.set(year,month,cal.getActualMaximum(Calendar.DATE),23,59,59);
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	// 获取当天日期yyyyMMdd
	public static String getNowDay() {
		return SYS_DATE_YEAR_MONTH_CT.format(new Date());
	}

	public static String getVaryHour(String time, int hourNum) {
		String rest = "";
		if (time != null && !"".equals(time)) {
			String[] times = time.split(":", -1);
			if (times.length == 3) {
				int hour = (Integer.valueOf(times[0]) + hourNum);
				while (hour > 23 || hour < 0) {
					if (hour > 23) {
						hour -= 24;
					} else if (hour < 0) {
						hour += 24;
					}
				}
				if (hour < 10) {
					times[0] = "0" + String.valueOf(hour);
				} else {
					times[0] = String.valueOf(hour);
				}
				rest = times[0] + ":" + times[1] + ":" + times[2];
			}
		}
		return rest;
	}

	/**
	 * 判断是否当前日期 是返回true 否返回false
	 * 
	 * @param time
	 *            比较日期时间date
	 * @param nowTime
	 *            当前日期时间date
	 * @return
	 */
	public static boolean isTheSameDate2(Date time, Date nowTime) {
		if (time != null && nowTime != null) {
			String date1 = new SimpleDateFormat("yyyy-MM-dd").format(time);
			String date2 = new SimpleDateFormat("yyyy-MM-dd").format(nowTime);
			return date1.equals(date2);
		}
		return false;
	}

	/**
	 * 将日期字符串转换成日期时间
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 返回日期.如:2010-08-08 12:12:12
	 */
	public static Date strToDateFormat(String dateStr) {

		try {
			Date date = SYS_DATE_TIME_CT.parse(dateStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * 
	 * Description: 给定时间转为Timestamp
	 * 
	 * @param date
	 * @return
	 * @exception
	 */
	public static Timestamp getTimestamp(Date date) {
		if (date != null) {
			Calendar dateCalendar = Calendar.getInstance();
			dateCalendar.setTime(date);
			Calendar calendar = new GregorianCalendar(dateCalendar
					.get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH),
					dateCalendar.get(Calendar.DAY_OF_MONTH), dateCalendar
							.get(Calendar.HOUR_OF_DAY), dateCalendar
							.get(Calendar.MINUTE), dateCalendar
							.get(Calendar.SECOND));
			return new Timestamp(calendar.getTimeInMillis());
		} else {
			return null;
		}
	}
	// 获取当天日期yyyyMMdd
	/**
	 * 获取当月
	 */
	public static String getNowMonth() {
		return SYS_DATE_SHORT_MONTH_CT.format(new Date());
	}
	// //请在此以上 添加 各功能函数////
	public static void main(String args[]) {
		// Timestamp now = TimeUtil.getNowTime();
		/*
		 * System.out.println(getYear(now)); System.out.println(getMonth(now));
		 * System.out.println(getDay(now));
		 * 
		 * System.out.println(getNowTime());
		 */
		/*
		 * Date dt = getVaryHour(now, -1); System.out.println(dt);
		 
		// System.out.println("==="+ TimeUtil);
		// System.out.println("===="+TimeUtil.getFirstTimeOfMonth(TimeUtil.getMonth(new
		// Date())));
		// System.out.println("===="+TimeUtil.getLastTimeOfMonth(TimeUtil.getMonth(new
		// Date())));
		System.out.println(getNowMonth());
		System.out.println("====" + getVaryHour("18:15:20", 50));
		boolean x = isTheSameDate2(TimeUtil.conStr3Date("20120416121212"),
				TimeUtil.getNowTime());
		System.out.println(x);
		*/
		String devId="1,2,3,4,5,";
		String[] tempGoodsObj = devId.split("\\,");
		for (int j = 0; j < tempGoodsObj.length; j++) {
			String tempDevId = tempGoodsObj[j];
			System.out.println(tempDevId);
		}
		
	}
}
