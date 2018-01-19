package com.buss.order.vo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class FahuoListVo implements java.io.Serializable{
	private List<TOrderDeliveryVo> deliveryList = new ArrayList<TOrderDeliveryVo>();

	public List<TOrderDeliveryVo> getDeliveryList() {
		return deliveryList;
	}

	public void setDeliveryList(List<TOrderDeliveryVo> deliveryList) {
		this.deliveryList = deliveryList;
	}

}
