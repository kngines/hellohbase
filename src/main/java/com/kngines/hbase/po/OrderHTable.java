package com.kngines.hbase.po;

public class OrderHTable {

	public static String T_NAME = "order";

	public static String F_INFO = "info";

	public static String INFO_C_ORDER_CODE = "orderCode";
	public static String INFO_C_FROM_USER = "fromUser";
	public static String INFO_C_TO_USER = "toUser";

	public static String getT_NAME() {
		return T_NAME;
	}

	public static void setT_NAME(String t_NAME) {
		T_NAME = t_NAME;
	}

	public static String getF_INFO() {
		return F_INFO;
	}

	public static void setF_INFO(String f_INFO) {
		F_INFO = f_INFO;
	}

	public static String getINFO_C_ORDER_CODE() {
		return INFO_C_ORDER_CODE;
	}

	public static void setINFO_C_ORDER_CODE(String iNFO_C_ORDER_CODE) {
		INFO_C_ORDER_CODE = iNFO_C_ORDER_CODE;
	}

	public static String getINFO_C_FROM_USER() {
		return INFO_C_FROM_USER;
	}

	public static void setINFO_C_FROM_USER(String iNFO_C_FROM_USER) {
		INFO_C_FROM_USER = iNFO_C_FROM_USER;
	}

	public static String getINFO_C_TO_USER() {
		return INFO_C_TO_USER;
	}

	public static void setINFO_C_TO_USER(String iNFO_C_TO_USER) {
		INFO_C_TO_USER = iNFO_C_TO_USER;
	}

	public static String getINFO_C_STATUS() {
		return INFO_C_STATUS;
	}

	public static void setINFO_C_STATUS(String iNFO_C_STATUS) {
		INFO_C_STATUS = iNFO_C_STATUS;
	}

	public static String getF_DETAIL() {
		return F_DETAIL;
	}

	public static void setF_DETAIL(String f_DETAIL) {
		F_DETAIL = f_DETAIL;
	}

	public static String getDETAIL_C_ITEM_IDS() {
		return DETAIL_C_ITEM_IDS;
	}

	public static void setDETAIL_C_ITEM_IDS(String dETAIL_C_ITEM_IDS) {
		DETAIL_C_ITEM_IDS = dETAIL_C_ITEM_IDS;
	}

	public static String getDETAIL_C_REMARK() {
		return DETAIL_C_REMARK;
	}

	public static void setDETAIL_C_REMARK(String dETAIL_C_REMARK) {
		DETAIL_C_REMARK = dETAIL_C_REMARK;
	}

	public static String INFO_C_STATUS = "status";

	public static String F_DETAIL = "details";

	public static String DETAIL_C_ITEM_IDS = "itemIds";
	public static String DETAIL_C_REMARK = "remark";

}
