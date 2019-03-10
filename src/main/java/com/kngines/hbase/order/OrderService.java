package com.kngines.hbase.order;

import com.kngines.hbase.entity.PageResult;
import com.kngines.hbase.po.OrderPo;

public interface OrderService {

	String createOrder(OrderPo orderPo);

	void updateStatus(String orderCode, int status);

	void deleteOrder(String orderCode);
	
	OrderPo getOrder(String orderCode);

	PageResult<OrderPo> queryPage(int page, int size);

}
