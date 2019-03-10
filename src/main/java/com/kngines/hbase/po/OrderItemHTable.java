package com.kngines.hbase.po;

public class OrderItemHTable {

	public static String T_NAME = "order_item";

	public static String F_INFO = "info";

	public static String INFO_C_GOO_ID = "gooId";
	public static String INFO_C_COUNT = "count";
	public static String INFO_C_GOO_NAME = "gooName";
	public static String INFO_C_GOO_PRICE = "gooPrice";

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

	public static String getINFO_C_GOO_ID() {
		return INFO_C_GOO_ID;
	}

	public static void setINFO_C_GOO_ID(String iNFO_C_GOO_ID) {
		INFO_C_GOO_ID = iNFO_C_GOO_ID;
	}

	public static String getINFO_C_COUNT() {
		return INFO_C_COUNT;
	}

	public static void setINFO_C_COUNT(String iNFO_C_COUNT) {
		INFO_C_COUNT = iNFO_C_COUNT;
	}

	public static String getINFO_C_GOO_NAME() {
		return INFO_C_GOO_NAME;
	}

	public static void setINFO_C_GOO_NAME(String iNFO_C_GOO_NAME) {
		INFO_C_GOO_NAME = iNFO_C_GOO_NAME;
	}

	public static String getINFO_C_GOO_PRICE() {
		return INFO_C_GOO_PRICE;
	}

	public static void setINFO_C_GOO_PRICE(String iNFO_C_GOO_PRICE) {
		INFO_C_GOO_PRICE = iNFO_C_GOO_PRICE;
	}
}
