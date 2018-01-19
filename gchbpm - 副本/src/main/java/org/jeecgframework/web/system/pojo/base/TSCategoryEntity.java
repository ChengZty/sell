package org.jeecgframework.web.system.pojo.base;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 * @Title: Entity
 * @Description: 分类管理
 * @author JueYue
 * @date 2014-09-16 21:50:55
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_s_category", schema = "")
@Where(clause = " status = 'A' ")
@DynamicUpdate(true)
@DynamicInsert(true)
public class TSCategoryEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/** id */
	private java.lang.String id;
	/** 类型名称 */
	private java.lang.String name;
	/** 类型编码 */
	private java.lang.String code;
	/** 图片地址 */
	private java.lang.String imgUrl;
	/** 层级 */
	private Integer level;
	/** sort */
	private Integer sort;
	/** 分类图标 */
	private TSIcon icon;
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
	/** 上级 */
	private TSCategoryEntity parent;
	/** 上级code */
	private java.lang.String parentCode;
	/** 记录状态 */
	private java.lang.String status;
	/** 品牌上面的分类图片 20170112新增*/  
	private java.lang.String imgBrandUrl;
	/** 零售商id 平台的id为admin 20171107新增*/
	private java.lang.String retailerId;

	private List<TSCategoryEntity> list;

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String id
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
	 * @param: java.lang.String id
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
	@Column(name = "CREATE_DATE", nullable = true)
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
	@Column(name = "UPDATE_DATE", nullable = true)
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
	 * @return: java.lang.String 类型名称
	 */
	@Column(name = "NAME", nullable = true, length = 32)
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 类型名称
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 类型编码
	 */
	@Column(name = "CODE", nullable = true, length = 32)
	public java.lang.String getCode() {
		return this.code;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 类型编码
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
	}

	/**
	 * 方法: 取得TSCategoryEntity
	 * 
	 * @return: TSCategoryEntity 上级code
	 */
//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne()
	@JoinColumn(name = "PARENT_ID",referencedColumnName = "ID")
	public TSCategoryEntity getParent() {
		return this.parent;
	}

	/**
	 * 方法: 设置TSCategoryEntity
	 * 
	 * @param: TSCategoryEntity 上级
	 */
	public void setParent(TSCategoryEntity parent) {
		this.parent = parent;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parent")
	@Where(clause="status = 'A'")
	public List<TSCategoryEntity> getList() {
		return list;
	}

	public void setList(List<TSCategoryEntity> list) {
		this.list = list;
	}

	@ManyToOne()
	@JoinColumn(name = "ICON_ID")
	public TSIcon getIcon() {
		return icon;
	}

	public void setIcon(TSIcon icon) {
		this.icon = icon;
	}
	@Column(name = "IMG_URL", nullable = true, length = 255)
	public java.lang.String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(java.lang.String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Column(name = "LEVEL", nullable = true)
	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}
	@Column(name = "SORT", nullable = true)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	@Column(name ="parent_code")
	public java.lang.String getParentCode() {
		return parentCode;
	}

	public void setParentCode(java.lang.String parentCode) {
		this.parentCode = parentCode;
	}
	@Column(name ="img_Brand_Url")
	public java.lang.String getImgBrandUrl() {
		return imgBrandUrl;
	}

	public void setImgBrandUrl(java.lang.String imgBrandUrl) {
		this.imgBrandUrl = imgBrandUrl;
	}
	@Column(name ="retailer_Id")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	
}
