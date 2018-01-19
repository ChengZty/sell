package org.jeecgframework.web.system.pojo.base;
import java.util.ArrayList;
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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 *地域管理表
 * @author  
 */
@Entity
@Table(name = "t_s_territory")
@Where(clause="status='A'")
public class TSTerritory  implements java.io.Serializable {
	/** id */
	private java.lang.String id;
	private static final long serialVersionUID = 1L;
	private TSTerritory TSTerritory;//父地域
	private String territoryName;//地域名称
	private Short territoryLevel;//等级
	private String territorySort;//同区域中的显示顺序
	private String territoryCode;//区域码
	private String territoryPinyin;//区域名称拼音
	private double xwgs84;//wgs84格式经度(mapabc 的坐标系)
	private double ywgs84;//wgs84格式纬度(mapabc 的坐标系)
	private String isShow;//是否显示
	private String remark;//备注
	/**修改人*/
	@Excel(name="修改人")
	private java.lang.String updateName;
	/**修改日期*/
	@Excel(name="修改日期",format = "yyyy-MM-dd")
	private java.util.Date updateDate;
	/**修改人ID*/
	@Excel(name="修改人ID")
	private java.lang.String updateBy;
	/**创建人*/
	@Excel(name="创建人")
	private java.lang.String createName;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private java.util.Date createDate;
	/**创建ID*/
	@Excel(name="创建ID")
	private java.lang.String createBy;
	/**状态*/
	@Excel(name="状态")
	private java.lang.String status;
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
	private List<TSTerritory> TSTerritorys = new ArrayList<TSTerritory>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "territoryparentid")
	@ForeignKey(name="null")//取消hibernate的外键生成
	public TSTerritory getTSTerritory() {
		return this.TSTerritory;
	}
	public void setTSTerritory(TSTerritory TSTerritory) {
		this.TSTerritory = TSTerritory;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSTerritory")
	@Where(clause="status='A'")
	public List<TSTerritory> getTSTerritorys() {
		return TSTerritorys;
	}
	public void setTSTerritorys(List<TSTerritory> TSTerritorys) {
		this.TSTerritorys = TSTerritorys;
	}
	@Column(name = "territoryname", nullable = false, length = 50)
	public String getTerritoryName() {
		return territoryName;
	}

	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}

	@Column(name = "territorysort",nullable = false,length = 3)
	public String getTerritorySort() {
		return territorySort;
	}

	public void setTerritorySort(String territorySort) {
		this.territorySort = territorySort;
	}
	@Column(name = "territorylevel",nullable = false,length = 1)
	public Short getTerritoryLevel() {
		return territoryLevel;
	}
	public void setTerritoryLevel(Short territoryLevel) {
		this.territoryLevel = territoryLevel;
	}
	@Column(name = "territorycode",nullable = false,length = 10)
	public String getTerritoryCode() {
		return territoryCode;
	}
	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}
	@Column(name = "territory_pinyin",nullable = true,length = 40)
	public String getTerritoryPinyin() {
		return territoryPinyin;
	}
	public void setTerritoryPinyin(String territoryPinyin) {
		this.territoryPinyin = territoryPinyin;
	}
	@Column(name = "x_wgs84",nullable = false,length = 40)
	public double getXwgs84() {
		return xwgs84;
	}
	public void setXwgs84(double xwgs84) {
		this.xwgs84 = xwgs84;
	}
	@Column(name = "y_wgs84",nullable = false,length = 40)
	public double getYwgs84() {
		return ywgs84;
	}
	public void setYwgs84(double ywgs84) {
		this.ywgs84 = ywgs84;
	}
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=32)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=32)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人ID
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=36)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人ID
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=32)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=32)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建ID
	 */
	@Column(name ="CREATE_BY",nullable=true,length=36)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建ID
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	@Column(name ="is_show")
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getRemark() {
		return remark;
	}
	@Column(name ="remark")
	public void setRemark(String remark) {
		this.remark = remark;
	}
}