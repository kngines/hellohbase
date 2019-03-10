package com.kngines.hbase.po;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class OrderPo {

	private String orderCode; // 订单号

	private String fromUser;

	private String toUser;

	private List<OrderItemPo> items;

	private Set<String> itemIds;

	private String status;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public List<OrderItemPo> getItems() {
		return items;
	}

	public void setItems(List<OrderItemPo> items) {
		this.items = items;
	}

	public Set<String> getItemIds() {
		return itemIds;
	}

	public void setItemIds(Set<String> itemIds) {
		this.itemIds = itemIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String remark;

}
