package com.kngines.hbase.po;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemPo {

	private String gooId;

	private Integer count;

	private String gooName;

	private BigDecimal gooPrice;

	public String getGooId() {
		return gooId;
	}

	public void setGooId(String gooId) {
		this.gooId = gooId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getGooName() {
		return gooName;
	}

	public void setGooName(String gooName) {
		this.gooName = gooName;
	}

	public BigDecimal getGooPrice() {
		return gooPrice;
	}

	public void setGooPrice(BigDecimal gooPrice) {
		this.gooPrice = gooPrice;
	}

}
