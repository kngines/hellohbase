package com.kngines.hase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.kngines.hbase.HbaseUtils;

public class HbaseUtilsTest {

	/**
	 * 
	 * @Title: createTableTest @Description: TODO 建表测试 @param: @throws
	 *         IOException @return: void @throws
	 */
	public static void createTableTest(String family1, String family2, TableName tableName) throws IOException {

		HbaseUtils.createTableIfNotExits(Arrays.asList(family1, family2), tableName);
	}

	/**
	 * 
	 * @Title: addStringColTest @Description: TODO 新增表数据测试 @param: @param
	 *         family1 @param: @param family2 @param: @param
	 *         tableName @param: @throws IOException @return: void @throws
	 */
	public static void addStringColTest(String family1, String family2, TableName tableName) throws IOException {
		// 2.添加数据到表
		Table table = HbaseUtils.getTable(tableName);
		// String randomNumeric = RandomStringUtils.randomNumeric(2);
		Put colOne = new Put(Bytes.toBytes(RandomStringUtils.randomAlphabetic(8)));//
		HbaseUtils.addStringCol(colOne, family1, "name", "kngines");
		HbaseUtils.addStringCol(colOne, family1, "age", "28");
		HbaseUtils.addStringCol(colOne, family2, "address", "杭州市浙江警察学院");
		table.put(Arrays.asList(colOne));
	}

	/**
	 * 
	 * @Title: printAllTest @Description: TODO 打印目标表中所有数据 @param: @param
	 *         family1 @param: @param family2 @param: @param
	 *         tableName @param: @throws IOException @return: void @throws
	 */
	public static void printAllTest(String family1, String family2, String tableName) throws IOException {
		HbaseUtils.printAll(TableName.valueOf(tableName));
	}

	public static void main(String[] args) throws Exception {

		String family1 = "info";
		String family2 = "detail";
		TableName tableName = TableName.valueOf("sys_user");

		// 1 建表测试
		createTableTest(family1,family2,tableName);

		// 2 插入测试
		// addStringColTest(family1, family2, tableName);

		// 3 打印所有行测试
		// printAllTest(family1, family2, tableName);

		// 4 根据rowkey 查询某一行; 方法1
		// Result rs = HbaseUtils.getOne(tableName, "lduCGGgM");
		// byte[] baddress = rs.getValue(Bytes.toBytes("detail"),
		// Bytes.toBytes("address"));
		// byte[] bname = rs.getValue(Bytes.toBytes("info"),
		// Bytes.toBytes("name"));
		// byte[] bage = rs.getValue(Bytes.toBytes("info"),
		// Bytes.toBytes("age"));
		// System.out.println("address: " + Bytes.toString(baddress) + " name: "
		// + Bytes.toString(bname) + " age: " + Bytes.toString(bage));

		// 4 根据rowkey 查询某一行; 方法1=2
		// HbaseUtils.printNavigableMap(rs);

		// 5 数据过滤
		// 筛选列族 info 下的列 age 的列值和字符串 "25" 相等
		List<String> conditions = new ArrayList<String>();
		conditions.add("info,age,25");
		HbaseUtils.selectByFilter(tableName, conditions);

		// 6 删除数据表
		// HbaseUtils.deleteTable(tableName);
	}

}
