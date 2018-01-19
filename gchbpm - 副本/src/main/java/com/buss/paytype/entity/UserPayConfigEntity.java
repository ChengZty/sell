package com.buss.paytype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 零售商支付配置
 * @author onlineGenerator
 * @date 2016-03-16 16:23:32
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_user_pay_config", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class UserPayConfigEntity implements java.io.Serializable {
	/** 主键 */
	private java.lang.String id;
	/** 创建人名称 */
	private java.lang.String createName;
	/** 创建人登录名称 */
	private java.lang.String createBy;
	/** 创建日期 */
	private java.util.Date createDate;
	/** 更新人名称 */
	private java.lang.String updateName;
	/** 更新人登录名称 */
	private java.lang.String updateBy;
	/** 更新日期 */
	private java.util.Date updateDate;
	/** 版本 */
	private java.lang.String version;
	/** 备注 */
	private java.lang.String remark;
	/** 状态 */
	private java.lang.String status;

	/** 零售商id */
	@Excel(name = "零售商id",width=12)
	private java.lang.String sid;
	/** 零售商名 */
	@Excel(name = "零售商名",width=12)
	private java.lang.String storeName;
	/** 自动结算 0 是 1 否 */
	@Excel(name = "自动结算(0是、1否)",width=12)
	private java.lang.String autoclear;
	/** 收款方式 1 商户收款 0 平台收款 */
	@Excel(name = "收款方式(0平台收款、1商户收款)",width=12)
	private java.lang.String paymentType;

	/** 微信appid */
	@Excel(name = "微信appid",width=15)
	private java.lang.String wxAppId;
	/** 微信商户秘钥 */
	@Excel(name = "微信商户秘钥",width=20)
	private java.lang.String wxPartnerKey;
	/** 微信商户号 */
	@Excel(name = "微信商户号",width=15)
	private java.lang.String wxMerchantId;
	/** 微信公众平台账号 */
	@Excel(name = "微信公众平台账号",width=20)
	private java.lang.String wxAppSectet;

	/** 支付宝合作者身份id */
	@Excel(name = "支付宝合作者身份id",width=20)
	private java.lang.String pid;
	/** 支付宝appid */
	@Excel(name = "支付宝appid",width=20)
	private java.lang.String appid;
	/** 支付宝收款账户 */
	@Excel(name = "支付宝收款账户",width=20)
	private java.lang.String paymentNo;
	/** 支付宝MD5key */
	@Excel(name = "支付宝MD5key",width=20)
	private java.lang.String md5Key;
	/** 签名类型 0 RSA, 1 RSA2 */
	@Excel(name = "支付宝签名类型(0RSA、1RSA2)",width=15)
	private java.lang.String signType;
	/** 支付宝商户私钥 */
	@Excel(name = "支付宝商户私钥",width=20)
	private java.lang.String rsaPrivateKey;
	/** 支付宝商户公钥 */
	@Excel(name = "支付宝商户公钥",width=20)
	private java.lang.String rsaPublicKey;

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 36)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 主键
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 创建人名称
	 */
	@Column(name = "CREATE_NAME", nullable = true, length = 50)
	public java.lang.String getCreateName() {
		return this.createName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 创建人名称
	 */
	public void setCreateName(java.lang.String createName) {
		this.createName = createName;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 创建人登录名称
	 */
	@Column(name = "CREATE_BY", nullable = true, length = 50)
	public java.lang.String getCreateBy() {
		return this.createBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 创建日期
	 */
	@Column(name = "CREATE_DATE", nullable = true, length = 20)
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 创建日期
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 更新人名称
	 */
	@Column(name = "UPDATE_NAME", nullable = true, length = 50)
	public java.lang.String getUpdateName() {
		return this.updateName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 更新人名称
	 */
	public void setUpdateName(java.lang.String updateName) {
		this.updateName = updateName;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 更新人登录名称
	 */
	@Column(name = "UPDATE_BY", nullable = true, length = 50)
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 更新日期
	 */
	@Column(name = "UPDATE_DATE", nullable = true, length = 20)
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 状态
	 */
	@Column(name = "STATUS", nullable = true, length = 1)
	public java.lang.String getStatus() {
		return this.status;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 状态
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	@Column(name = "version", nullable = true, length = 20)
	public java.lang.String getVersion() {
		return version;
	}

	public void setVersion(java.lang.String version) {
		this.version = version;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	@Column(name = "sid", nullable = true, length = 50)
	public java.lang.String getSid() {
		return sid;
	}

	public void setSid(java.lang.String sid) {
		this.sid = sid;
	}

	@Column(name = "store_name", nullable = true, length = 100)
	public java.lang.String getStoreName() {
		return storeName;
	}

	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}

	@Column(name = "autoclear", nullable = true, length = 1)
	public java.lang.String getAutoclear() {
		return autoclear;
	}

	public void setAutoclear(java.lang.String autoclear) {
		this.autoclear = autoclear;
	}

	@Column(name = "payment_type", nullable = true, length = 1)
	public java.lang.String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(java.lang.String paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "wx_app_id", nullable = true, length = 50)
	public java.lang.String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(java.lang.String wxAppId) {
		this.wxAppId = wxAppId;
	}

	@Column(name = "wx_partner_key", nullable = true, length = 100)
	public java.lang.String getWxPartnerKey() {
		return wxPartnerKey;
	}

	public void setWxPartnerKey(java.lang.String wxPartnerKey) {
		this.wxPartnerKey = wxPartnerKey;
	}

	@Column(name = "wx_merchant_id", nullable = true, length = 50)
	public java.lang.String getWxMerchantId() {
		return wxMerchantId;
	}

	public void setWxMerchantId(java.lang.String wxMerchantId) {
		this.wxMerchantId = wxMerchantId;
	}

	@Column(name = "wx_app_sectet", nullable = true, length = 100)
	public java.lang.String getWxAppSectet() {
		return wxAppSectet;
	}

	public void setWxAppSectet(java.lang.String wxAppSectet) {
		this.wxAppSectet = wxAppSectet;
	}

	@Column(name = "pid", nullable = true, length = 100)
	public java.lang.String getPid() {
		return pid;
	}

	public void setPid(java.lang.String pid) {
		this.pid = pid;
	}

	@Column(name = "appid", nullable = true, length = 100)
	public java.lang.String getAppid() {
		return appid;
	}

	public void setAppid(java.lang.String appid) {
		this.appid = appid;
	}

	@Column(name = "payment_no", nullable = true, length = 50)
	public java.lang.String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(java.lang.String paymentNo) {
		this.paymentNo = paymentNo;
	}

	@Column(name = "md5_key", nullable = true, length = 100)
	public java.lang.String getMd5Key() {
		return md5Key;
	}

	public void setMd5Key(java.lang.String md5Key) {
		this.md5Key = md5Key;
	}

	@Column(name = "sign_type", nullable = true, length = 10)
	public java.lang.String getSignType() {
		return signType;
	}

	public void setSignType(java.lang.String signType) {
		this.signType = signType;
	}

	@Column(name = "rsa_private_key", nullable = true, length = 2100)
	public java.lang.String getRsaPrivateKey() {
		return rsaPrivateKey;
	}

	public void setRsaPrivateKey(java.lang.String rsaPrivateKey) {
		this.rsaPrivateKey = rsaPrivateKey;
	}

	@Column(name = "rsa_public_key", nullable = true, length = 2100)
	public java.lang.String getRsaPublicKey() {
		return rsaPublicKey;
	}

	public void setRsaPublicKey(java.lang.String rsaPublicKey) {
		this.rsaPublicKey = rsaPublicKey;
	}

}
