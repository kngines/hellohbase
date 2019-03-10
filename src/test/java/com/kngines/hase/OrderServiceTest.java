package com.kngines.hase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.hbase.TableName;
import org.junit.Test;

import com.kngines.hbase.HbaseUtils;
import com.kngines.hbase.entity.PageResult;
import com.kngines.hbase.order.OrderService;
import com.kngines.hbase.order.OrderServiceHbaseImpl;
import com.kngines.hbase.po.OrderHTable;
import com.kngines.hbase.po.OrderItemHTable;
import com.kngines.hbase.po.OrderItemPo;
import com.kngines.hbase.po.OrderPo;

public class OrderServiceTest {

	private OrderService orderService = new OrderServiceHbaseImpl();

	@Test
	public void testPrintAll() {
		HbaseUtils.printAll(TableName.valueOf(OrderHTable.T_NAME));
		System.out.println();
		 HbaseUtils.printAll(TableName.valueOf(OrderItemHTable.T_NAME));

	}

	@Test
	public void testDeleteAll() throws Exception {
		HbaseUtils.deleteTable(TableName.valueOf(OrderHTable.T_NAME));
		HbaseUtils.deleteTable(TableName.valueOf(OrderItemHTable.T_NAME));
	}

	@Test
	public void testCreateOrder() throws Exception {
		for (int i = 0; i < 5; i++) {
			createMockOrder();
		}
	}

	private void createMockOrder() {
		OrderPo orderPo = new OrderPo();
		orderPo.setFromUser(RandomStringUtils.randomAlphabetic(3));
		orderPo.setToUser(RandomStringUtils.randomAlphabetic(3));
		
		List<OrderItemPo> items = new ArrayList<>();
		for (int j = 0; j < 2; j++) {
			OrderItemPo item = new OrderItemPo();
			item.setCount(j);
			item.setGooPrice(new BigDecimal(100 + j));
			item.setGooName("good-" + j);
			items.add(item);
		}
		orderPo.setItems(items);
		orderService.createOrder(orderPo);
	}

	@Test
	public void testQueryPage() throws Exception {
		PageResult<OrderPo> queryPage = orderService.queryPage(0, 10);
		System.out.printf("分页查询page=[%d],size=[%s]的总数为=[%d]结果为:%n", queryPage.getPage(), queryPage.getSize(),
				queryPage.getTotal());
		queryPage.getDatas().stream().forEach(System.out::println);
	}

//	@Test
//	public void testName() throws Exception {
//		orderService.deleteOrder("XcL1527064088676lS1o");
//	}
}
