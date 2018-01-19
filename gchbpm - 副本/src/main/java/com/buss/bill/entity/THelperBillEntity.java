package com.buss.bill.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**   
 * @Title: Entity
 * @Description: 帮手实收明细
 * @author onlineGenerator
 * @date 2016-05-18 14:19:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_helper_bill", schema = "")
@SuppressWarnings("serial")
public class THelperBillEntity implements java.io.Serializable {
	public static final String BUSINESS_TYPE_1 = "1";//确认收货（后改为付款）
	public static final String BUSINESS_TYPE_2 = "2";//同意退货(退款)
	public static final String HELPER_TYPE_1 = "1";//专家
	public static final String HELPER_TYPE_2 = "2";//达人
	public static final String HELPER_TYPE_3 = "3";//导购
	/**主键*/
	private java.lang.String id;
	/**财务结算表ID*/
	private java.lang.String finBillId;
	/**流水时间*/
	private java.util.Date addTime;
	/**订单编号*/
	private java.lang.String orderNo;
	/**子订单编号*/
	private java.lang.String subOrderNo;
	/**业务日期*/
	private java.util.Date businessDate;
	/**业务类型 确认收货_1","同意退款_2*/
	private java.lang.String businessType;
	/**帮手实收*/
	private java.math.BigDecimal helperMoney;
	/**帮手ID*/
	private java.lang.String helperId;
	/**帮手名字*/
	private java.lang.String helperName;
	/**帮手类型*/
	private java.lang.String helperType;
	/**二级分类*/
	private java.lang.String subCategoryId;
	/**导购ID*/
	private java.lang.String guideId;
	/**导购所属零售商ID*/
	private java.lang.String storeId;
	/**货品所属零售商云商ID*/
	private java.lang.String toStoreGoodsId;
	/**货品所属零售商云商类型*/
	private java.lang.String storeType;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	@Column(name ="fin_Bill_Id",nullable=true,length=36)
	public java.lang.String getFinBillId() {
		return finBillId;
	}
	public void setFinBillId(java.lang.String finBillId) {
		this.finBillId = finBillId;
	}
	@Column(name ="add_Time",nullable=true,length=20)
	public java.util.Date getAddTime() {
		return addTime;
	}
	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	@Column(name ="order_No",nullable=true)
	public java.lang.String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name ="sub_Order_No",nullable=true)
	public java.lang.String getSubOrderNo() {
		return subOrderNo;
	}
	public void setSubOrderNo(java.lang.String subOrderNo) {
		this.subOrderNo = subOrderNo;
	}
	@Column(name ="business_Date",nullable=true)
	public java.util.Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(java.util.Date businessDate) {
		this.businessDate = businessDate;
	}
	@Column(name ="BUSINESS_TYPE",nullable=true)
	public java.lang.String getBusinessType(){
		return this.businessType;
	}
	public void setBusinessType(java.lang.String businessType){
		this.businessType = businessType;
	}
	@Column(name ="helper_Money",nullable=true)
	public java.math.BigDecimal getHelperMoney() {
		return helperMoney;
	}
	public void setHelperMoney(java.math.BigDecimal helperMoney) {
		this.helperMoney = helperMoney;
	}
	@Column(name ="helper_Id",nullable=true)
	public java.lang.String getHelperId() {
		return helperId;
	}
	public void setHelperId(java.lang.String helperId) {
		this.helperId = helperId;
	}
	@Column(name ="helper_Name",nullable=true)
	public java.lang.String getHelperName() {
		return helperName;
	}
	public void setHelperName(java.lang.String helperName) {
		this.helperName = helperName;
	}
	@Column(name ="helper_type",nullable=true)
	public java.lang.String getHelperType() {
		return helperType;
	}
	public void setHelperType(java.lang.String helperType) {
		this.helperType = helperType;
	}
	@Column(name ="sub_Category_Id",nullable=true)
	public java.lang.String getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(java.lang.String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	@Column(name ="guide_Id",nullable=true)
	public java.lang.String getGuideId() {
		return guideId;
	}
	public void setGuideId(java.lang.String guideId) {
		this.guideId = guideId;
	}
	@Column(name ="store_Id",nullable=true)
	public java.lang.String getStoreId() {
		return storeId;
	}
	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
	@Column(name ="to_Store_Goods_Id",nullable=true)
	public java.lang.String getToStoreGoodsId() {
		return toStoreGoodsId;
	}
	public void setToStoreGoodsId(java.lang.String toStoreGoodsId) {
		this.toStoreGoodsId = toStoreGoodsId;
	}
	@Column(name ="store_Type",nullable=true)
	public java.lang.String getStoreType() {
		return storeType;
	}
	public void setStoreType(java.lang.String storeType) {
		this.storeType = storeType;
	}
	
}
