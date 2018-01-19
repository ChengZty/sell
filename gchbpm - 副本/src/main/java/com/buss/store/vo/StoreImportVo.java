package com.buss.store.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class StoreImportVo implements java.io.Serializable{
	/**店铺名称*/
	@Excel(name="店铺名称",width=20)
	private java.lang.String name;
	/**名称*/
	@Excel(name="店铺编号",width=20)
	private java.lang.String storeCode;
	/**地址*/
	@Excel(name="地址",width=50)
	private java.lang.String address;
	/**电话*/
	@Excel(name="电话",width=15)
	private java.lang.String phoneNo;
	/**排序*/
	@Excel(name="排序",width=10)
	private java.lang.Integer sortNum=1;
	/**备注*/
	@Excel(name="备注",width=50)
	private java.lang.String remark="";
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(java.lang.String storeCode) {
		this.storeCode = storeCode;
	}
	public java.lang.String getAddress() {
		return address;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(java.lang.String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public java.lang.Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(java.lang.Integer sortNum) {
		this.sortNum = sortNum;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
}
