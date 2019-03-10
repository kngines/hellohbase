package com.kngines.hbase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Properties;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

public final class HbaseUtils {
	private static Logger logger = Logger.getLogger(HbaseUtils.class);

	private static Connection conn = null;
	public static final String ZOOKEEPER_CONF_FILE_PATH = "/hbase/hbase.properties";
	public static final String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	public static final String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = "hbase.zookeeper.property.clientPort";
	public static final String HBASE_ZOOKEEPER_PROPERTY_MASTER = "hbase.zookeeper.property.master";

	/**
	 * zookeeper是hbase集群的"协调器"。
	 * 由于zookeeper的轻量级特性，因此我们可以将多个hbase集群共用一个zookeeper集群，以节约大量的服务器。
	 * 多个hbase集群共用zookeeper集群的方法是使用同一组ip，修改不同hbase集群的"zookeeper.znode.parent"属性，让它们使用不同的根目录。
	 */
	// public static final String ZOOKEEPER_ZNODE_PARENT =
	// "zookeeper.znode.parent";

	static {
		Configuration conf = new Configuration();

		// PropertyResourceBundle是ResourceBundle的具体子类，是通过对属性文件的静态字符串管理来语言环境资源。
		InputStream ins = HbaseUtils.class.getResourceAsStream(ZOOKEEPER_CONF_FILE_PATH);
		Properties prop = new Properties();
		try {
			prop.load(ins);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		conf.set(HBASE_ZOOKEEPER_QUORUM, prop.getProperty("quorum"));
		conf.set(HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT, prop.getProperty("clientPort"));
		conf.set(HBASE_ZOOKEEPER_PROPERTY_MASTER, prop.getProperty("master"));
		try {
			conn = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			logger.error("连接获取失败: " + e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: createTableIfNotExits @Description: TODO 创建数据表 @param: @param
	 * familys @param: @param tableName @param: @throws IOException @return:
	 * void @throws
	 */
	public static void createTableIfNotExits(List<String> familys, TableName tableName) throws IOException {
		if (familys == null || familys.size() <= 0) {
			return;
		}
		Set<String> familysSet = new HashSet<>(familys);
		Admin admin = conn.getAdmin();

		if (admin.tableExists(tableName)) {
			logger.info(tableName + " 表已经存在!");
		} else {
			HTableDescriptor desc = new HTableDescriptor(tableName);

			// HTableDescriptor 列族(Column Family)
			// HTableDescriptor 类包含表的名字以及表的列族信息
			familysSet.stream().filter(a -> a != null).forEach(a -> desc.addFamily(new HColumnDescriptor(a)));
			
			admin.createTable(desc);
			logger.info("创建表 \'" + tableName + "\' 成功!");
		}
	}

	/**
	 * 
	 * @Title: deleteTable   
	 * @Description: TODO 删除数据表 
	 * @param: @param tableName      
	 * @return: void      
	 * @throws
	 */
	public static void deleteTable(TableName tableName) {
		try {
			
			// org.apache.hadoop.hbase.client.HBaseAdmin: 提供一个接口来管理HBase数据库的表信息
			Admin admin = conn.getAdmin();
			if (!admin.tableExists(tableName)) {// 表不存在
				logger.info(tableName + " is not exists!");
			} else {
				admin.disableTable(tableName);// 废弃表
				admin.deleteTable(tableName);// 删除表
				
				logger.info(tableName + " is delete!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: getTable   
	 * @Description: TODO 返回表对象 
	 * @param: @param tableName
	 * @param: @return      
	 * @return: Table      
	 * @throws
	 */
	public static Table getTable(TableName tableName) {
		try {
			return conn.getTable(tableName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: deleteOne   
	 * @Description: TODO 根据rowkey 删除行数据
	 * @param: @param tableName
	 * @param: @param rowkey      
	 * @return: void      
	 * @throws
	 */
	public static void deleteOne(TableName tableName, String rowkey) {
		try {
			Table table = getTable(tableName);
			/**
			 * toBytes()方法是将参数使用UTF-8的编码格式转换成byte[],
			 * getBytes()是用读取file.encoding的编码格式,然后用读取的格式进行转换,
			 * 即,getBytes转换的byte[]的格式取决于操作系统和用户设置,最好统一只用toBytes()方法。
			 */
			table.delete(new Delete(Bytes.toBytes(rowkey)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: getOne   
	 * @Description: TODO 获取行数据  
	 * @param: @param tableName
	 * @param: @param rowkey
	 * @param: @return      
	 * @return: Result      
	 * @throws
	 */
	public static Result getOne(TableName tableName, String rowkey) {
		try {
			Table table = getTable(tableName);
			return table.get(new Get(Bytes.toBytes(rowkey)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @Title: selectByFilter   
	 * @Description: TODO 条件筛选数据
	 * @param: @param tableName
	 * @param: @param conditions      
	 * @return: void      
	 * @throws
	 */
	public static void selectByFilter(TableName tableName, List<String> conditions) {
		Table table = null;
		try {
			table = getTable(tableName);

			// 各个条件之间是“或”的关系，默认是“与”
			FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
			Scan scan = new Scan();
			
			for (String condition : conditions) {
				String[] s = condition.split(",");
				filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(s[0]), Bytes.toBytes(s[1]),
						CompareOp.EQUAL, Bytes.toBytes(s[2])));
				// 添加下面这一行后，则只返回指定的cell，同一行中的其他cell不返回
				// s1.addColumn(Bytes.toBytes(s[0]), Bytes.toBytes(s[1]));
			}
			scan.setFilter(filterList);
			// ColumnPrefixFilter 用于指定列名前缀值相等
			// new ColumnPrefixFilter(Bytes.toBytes("values"));
			// s1.setFilter(f);

			// MultipleColumnPrefixFilter 和 ColumnPrefixFilter 行为差不多，但可以指定多个前缀
			// byte[][] prefixes = new byte[][] {Bytes.toBytes("value1"),
			// Bytes.toBytes("value2")};
			// Filter f = new MultipleColumnPrefixFilter(prefixes);
			// s1.setFilter(f);

			// QualifierFilter 是基于列名的过滤器。
			// Filter f = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new
			// BinaryComparator(Bytes.toBytes("col5")));
			// s1.setFilter(f);

			// RowFilter 是rowkey过滤器,通常根据rowkey来指定范围时，使用scan扫描器的StartRow和StopRow
			// 方法比较好。Rowkey也可以使用。
			// Filter f = new
			// RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL, new
			// RegexStringComparator(".*5$"));//正则获取结尾为5的行
			// s1.setFilter(f);
			ResultScanner resScan = table.getScanner(scan); // 存储Get或者Scan操作后获取表的单行值
			resScan.forEach(result -> printNavigableMap(result));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (table != null) {
					table.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Title: printAll   
	 * @Description: TODO 打印表数据
	 * @param: @param tableName      
	 * @return: void      
	 * @throws
	 */
	public static void printAll(TableName tableName) {
		try {
			Table table = getTable(tableName);
			Scan scan = new Scan();
			ResultScanner scanner = table.getScanner(scan);
			logger.error("查询表:[%s]的内容->" + tableName);
			scanner.forEach(row -> {
				logger.info("\nRowkey: " + new String(row.getRow()));
				printNavigableMap(row);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: printOne   
	 * @Description: TODO 打印行数据
	 * @param: @param tableName
	 * @param: @param rowkey      
	 * @return: void      
	 * @throws
	 */
	public static void printOne(TableName tableName, String rowkey) {
		try {
			Table table = getTable(tableName);
			logger.error("查询表:[" + tableName + "],Rowkey的内容为-> " + rowkey);
			Result result = table.get(new Get(Bytes.toBytes(rowkey)));
			printNavigableMap(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: printNavigableMap   
	 * @Description: TODO NavigableMap是SortedMap的扩展，提供方便的导航方法，如lowerKey，floorKey，ceilingKey和higherKey。
	 * @param: @param result      
	 * @return: void      
	 * @throws
	 */
	public static void printNavigableMap(Result result) {
		NavigableMap<byte[], NavigableMap<byte[], byte[]>> noVersionMap = result.getNoVersionMap();
		
		noVersionMap.forEach((fl, da) -> {
			String family = new String(fl);
			Iterator<Entry<byte[], byte[]>> iterator = da.entrySet().iterator();
			
			while (iterator.hasNext()) {
				Entry<byte[], byte[]> entry = iterator.next();
				logger.info("\t" + family + "." + new String(entry.getKey()));
				byte[] value = entry.getValue();
				logger.info(":" + (value == null ? "" : new String(value)));
			}
		});
	}

	/**
	 * 
	 * @Title: addStringCol   
	 * @Description: TODO   增加数据列 
	 * @param: @param row
	 * @param: @param colFamily
	 * @param: @param colName
	 * @param: @param colValue      
	 * @return: void      
	 * @throws
	 */
	public static void addStringCol(Put row, String colFamily, String colName, String colValue) {
		row.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(colName), Bytes.toBytes(colValue));
	}
	
	/**
	 * 
	 * @Title: listAllTables   
	 * @Description: TODO    列出所有数据表名
	 * @param: @return      
	 * @return: List<String>      
	 * @throws
	 */
	public static List<String> listAllTables() {
		logger.info("list all tables.");
	    try {
	    	Admin admin = conn.getAdmin();
	        TableName[] tableNames = admin.listTableNames();
	        List<String> tabs = new ArrayList<String>();
	        for (int i = 0; i < tableNames.length; i++) {
	        	tabs.add(tableNames[i].getNameAsString());
	        }
	        return tabs;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
