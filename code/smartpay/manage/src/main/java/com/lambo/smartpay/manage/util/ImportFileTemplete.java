/**
 * 
 */
package com.lambo.smartpay.manage.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * $Id$
 * 
 * Copyright (C)2015 福州蓝宝.All rights reserved.
 * 
 * ImportFileTemplete.java
 * 
 * Original Author: 廖流利
 * 
 * 文件功能说明: 导入文件模板 (特别是导入Excel)
 * 
 * <文件功能说明> History 版本号 | 作者 | 修改时间 | 修改内容
 */
public class ImportFileTemplete implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3984539645814072632L;

	/**
	 * 存放各种导入文件模板的文件夹名
	 */
	public static final String TEMPLATE_FOLD_NAME = "xls";

	/**
	 * 订单与发货单模板
	 */
	public static final String ORDER_SENDGOODS_TEMPLATE = "xml/ORDER_SENDGOODS_TEMPLATE.xls";
	public static final Map<String, Boolean> ORDER_SENDGOODS_FORMAT = new HashMap<String, Boolean>();
	static {
        ORDER_SENDGOODS_FORMAT.put("订单编号", true);
        ORDER_SENDGOODS_FORMAT.put("货运单号", true);
        ORDER_SENDGOODS_FORMAT.put("客户名称", true);
        ORDER_SENDGOODS_FORMAT.put("客户联系电话", true);
        ORDER_SENDGOODS_FORMAT.put("客户地址", true);
	}
	/**
	 * 商城批量加载模板
	 */
	public static final String ORDER_SENDGOODS_TEMPLATE1 = "xml/Mall_bulk_loading.xls";
	public static final Map<String, Boolean> ORDER_SENDGOODS_FORMAT1 = new HashMap<String, Boolean>();
	static {
		ORDER_SENDGOODS_FORMAT1.put("商户编号", true);
		ORDER_SENDGOODS_FORMAT1.put("商城名称", true);
		ORDER_SENDGOODS_FORMAT1.put("商城地址", true);

	}

}