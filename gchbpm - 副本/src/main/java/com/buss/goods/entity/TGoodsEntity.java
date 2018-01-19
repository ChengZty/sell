package com.buss.goods.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.buss.activity.entity.TFinActivityWordsEntity;
import com.buss.newGoods.entity.TGuideRecommendDetailEntity;

/**   
 * @Title: Entity
 * @Description: 商品表
 * @author onlineGenerator
 * @date 2016-03-17 20:05:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_goods", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TGoodsEntity implements java.io.Serializable {
	public static String GOODS_TYPE_1 = "1";//单品
	public static String GOODS_TYPE_2 = "2";//组合
	public static String GOODS_TYPE_3 = "3";//混搭
	public static String NEW_GOODS_TYPE_1 = "1";//旧模块商品
	public static String NEW_GOODS_TYPE_2 = "2";//新模块商品
	public static String GOODS_STATUS_0 = "0";//草稿箱中
//	public static String GOODS_STATUS_1 = "1";//待审核
//	public static String GOODS_STATUS_2 = "2";//审核不通过
	public static String GOODS_STATUS_3 = "3";//待上架（上新商品新增状态）
	public static String GOODS_STATUS_4 = "4";//已上架（审核通过,销售中）
	public static String GOODS_STATUS_5 = "5";//已下架
//	public static String GOODS_STATUS_6 = "6";//违规下架
	public static String PUBLISH_STATUS_1 = "1";//立即发布（进入 上架 状态）
	public static String PUBLISH_STATUS_2 = "2";//放入草稿箱（进入 草稿箱中 状态）
	public static String PUBLISH_STATUS_3 = "3";//待发布（进入 待上架 状态）
	/*字典subgdstype */
	public static String SUB_GOODS_TYPE_1 = "1";//新品快报
	public static String SUB_GOODS_TYPE_2 = "2";//当红
	public static String SUB_GOODS_TYPE_3 = "3";//活动推广
	/*所属零售商类型*/
	public static String RETAILER_TYPE_1 = "1";//零售商 人货
	public static String RETAILER_TYPE_2 = "2";//零售商 货
	public static String RETAILER_TYPE_3 = "3";//零售商 人
	/*组合来源 */
	public static String GROUP_SOURCE_1 = "1";//零售商录入
	public static String GROUP_SOURCE_2 = "2";//后台系统录入
	/*运费优惠类型 */
	public static String FARE_PREFERENTIAL_TYPE_0 = "0";//无
	public static String FARE_PREFERENTIAL_TYPE_1 = "1";//满免
	public static String FARE_PREFERENTIAL_TYPE_2 = "2";//递减
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**状态*/
	private java.lang.String status;
	/**一级分类*/
	@Excel(name="一级分类")
	private java.lang.String topCategoryId;
	/**二级分类*/
	@Excel(name="二级分类")
	private java.lang.String subCategoryId;
	/**三级分类*/
	@Excel(name="三级分类")
	private java.lang.String thridCategoryId;
	/**场景*/
	@Excel(name="场景")
	private java.lang.String sceneType;
	/**短标题*/
	private java.lang.String title;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String goodsName;
	/**品牌*/
	@Excel(name="品牌ID")
	private java.lang.String brandId;
	/**品牌*/
	@Excel(name="品牌编码")
	private java.lang.String brandCode;
	@Excel(name="品牌名称")
	private java.lang.String brandName;
	/**商品编码/款号*/
	@Excel(name="商品编码")
	private java.lang.String goodsCode;
	//商品编号(零售商编码+品牌编码+款号)
	private java.lang.String code;
	/**原价*/
	@Excel(name="原价")
	private java.math.BigDecimal originalPrice;
	/**现价*/
	@Excel(name="现价")
	private java.math.BigDecimal currentPrice;
	/**活动价*/
	@Excel(name="活动价")
	private java.math.BigDecimal activityPrice;
	/**最低折扣*/
	@Excel(name="最低折扣")
	private java.math.BigDecimal lowestPriceDiscount;
	/**最低价*/
	@Excel(name="最低价")
	private java.math.BigDecimal lowestPrice;
	/**组合价*/
	@Excel(name="组合价")
	private java.math.BigDecimal groupPrice;
	/**销量*/
	@Excel(name="销量")
	private java.math.BigDecimal salesVolume;

	/**库存*/
	@Excel(name="库存")
	private java.math.BigDecimal goodsStock;
	/**更新时间 改为上架时间*/
	@Excel(name="上架时间",format = "yyyy-MM-dd")
	private java.util.Date goodsUpdateTime;
	/**搜索关键字*/
	@Excel(name="搜索关键字")
	private java.lang.String seoCare;
	/**描述*/
	@Excel(name="描述")
	private java.lang.String seoDesc;
	/**小图*/
	private java.lang.String smallPic;
	/**主图一*/
	@Excel(name="主图一")
	private java.lang.String picOne;
	/**主图二*/
	@Excel(name="主图二")
	private java.lang.String picTwo;
	/**主图三*/
	@Excel(name="主图三")
	private java.lang.String picThree;
	/**主图四*/
	@Excel(name="主图四")
	private java.lang.String picFour;
	/**主图五*/
	@Excel(name="主图五")
	private java.lang.String picFive;
	/**零售商编码*/
	private java.lang.String retailerCode;
	/**所属零售商ID*/
	private java.lang.String retailerId;
	/**所属零售商*/
	private java.lang.String retailerName;
	/**所属零售商类型*/
	private java.lang.String retailerType;
	/**商品类型*/
	private java.lang.String goodsType;//单品：1， 2016.10.24以后G+上新类别的商品（组合，混搭）都是2
	/**商品另一种类型*/
	private java.lang.String subGoodsType;//1:新品快报,2:当红,3:活动推广
	/**商品状态*/
	@Excel(name="商品状态")
	private java.lang.String goodsStatus;
	@Excel(name="发布状态")
	private java.lang.String publishStatus;
	/**省*/
	private java.lang.String provinceId;
	/**市*/
	private java.lang.String cityId;
	/**描述（以前的商品）*/
	private TGoodsDescEntity desc ;
	/**组合来源 1：零售商录入，2：后台系统录入*/
	private String groupSource ;
	/**之前的现价*/
	private java.math.BigDecimal prePrice;
	/**之前的最低价*/
	private java.math.BigDecimal preLowestPrice;
	/**之前的活动价*/
	private java.math.BigDecimal preActivityPrice;
	/**组合单品明细列表*/
	private List<TGoodsPicEntity> tGoodsPicDetails = new ArrayList<TGoodsPicEntity>();
	/**关键词列表*/
//	private List<TGoodsAttrEntity> tGoodsAttrDetails = new ArrayList<TGoodsAttrEntity>();
	/**明细库存列表*/
	private List<TGoodsStoreEntity> tGoodsStoreDetails  = new ArrayList<TGoodsStoreEntity>();
	/**可见类目*/
	private List<TVisibleCategriesEntity> tVisibleCatgys  = new ArrayList<TVisibleCategriesEntity>();
	/**系统商品列表*/
//	private List<TSysRecmdPicsEntity> tSysRecmdPics  = new ArrayList<TSysRecmdPicsEntity>();
	/**管家点评商品列表*/
//	private List<TGuideRecmdPicsEntity> tGuideRecmdPics  = new ArrayList<TGuideRecmdPicsEntity>();
	//可见类目（逗号隔开）
	private String categries ;
	/**点击次数*/
	private java.lang.Integer goodsCollect;
	/**无感次数*/
	private java.lang.Integer noSenseNum;
	/**点赞次数*/
	private java.lang.Integer goodNum;
	/**评价星级*/
	private java.math.BigDecimal goodsStar;//星级（一级到五级）
	/**评星次数*/
	private java.math.BigDecimal scoresNum;
	/**邮费类型  0：免邮，1：定额*/
	private String fareType ;
	/**运费（定额或者递减的第一件的邮费），共用字段*/
	private java.math.BigDecimal fare;
	/**运费优惠类型  0：无，1：满免，2：递减*/
	private String farePreferentialType;
	/**运费优惠（满免金额，或者第二件开始每件的邮费），共用字段*/
	private java.math.BigDecimal goodsFarePreferential;
	/**封面图片*/
	private String coverPic ;
	/**是否进行广告设置（Y/N，默认为N）*/
	private String isShow ;
	/**是否是特殊商品（Y/N，默认为N）*/
	private String isSpecial ;
	/**新商品类别（1：以前的商品，2：新版商品，   默认为1）*/
	private String newGoodsType ;
	/**产品信息列表*/
	private List<TProductInfoEntity> productInfoList  = new ArrayList<TProductInfoEntity>();
	/**管家点评明细列表*/
	private List<TGuideRecommendDetailEntity> recommendDetailsList  = new ArrayList<TGuideRecommendDetailEntity>();
	/**上新云仓商品描述 真特色、真用途、真权威、真服务*/
//	private List<TGoodsDescEntity> descList  = new ArrayList<TGoodsDescEntity>();
	/**排序  20161128增加*/
	private Long sortNum;
	/**是否有4真 1:有，0：没有    20170102新增*/
	private String hasZhen;
	/**话术列表*/
	private List<TFinActivityWordsEntity> tGoodsWordsDetails  = new ArrayList<TFinActivityWordsEntity>();
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
//	@GeneratedValue(generator = "paymentableGenerator")
//	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
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
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  一级分类
	 */
	@Column(name ="TOP_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getTopCategoryId(){
		return this.topCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一级分类
	 */
	public void setTopCategoryId(java.lang.String topCategoryId){
		this.topCategoryId = topCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  二级分类
	 */
	@Column(name ="SUB_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getSubCategoryId(){
		return this.subCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  二级分类
	 */
	public void setSubCategoryId(java.lang.String subCategoryId){
		this.subCategoryId = subCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三级分类
	 */
	@Column(name ="THRID_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getThridCategoryId(){
		return this.thridCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三级分类
	 */
	public void setThridCategoryId(java.lang.String thridCategoryId){
		this.thridCategoryId = thridCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  场景
	 */
	@Column(name ="SCENE_TYPE",nullable=true,length=2)
	public java.lang.String getSceneType(){
		return this.sceneType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  场景
	 */
	public void setSceneType(java.lang.String sceneType){
		this.sceneType = sceneType;
	}
	@Column(name ="TITLE")
	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=false,length=150)
	public java.lang.String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsName(java.lang.String goodsName){
		this.goodsName = goodsName;
	}
	@Column(name ="BRAND_ID",nullable=true,length=36)
	public java.lang.String getBrandId() {
		return brandId;
	}

	public void setBrandId(java.lang.String brandId) {
		this.brandId = brandId;
	}
	@Column(name ="BRAND_CODE",nullable=true,length=10)
	public java.lang.String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(java.lang.String brandCode) {
		this.brandCode = brandCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品牌
	 */
	@Column(name ="BRAND_NAME",nullable=true,length=36)
	public java.lang.String getBrandName(){
		return this.brandName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  品牌
	 */
	public void setBrandName(java.lang.String brandName){
		this.brandName = brandName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品编码
	 */
	@Column(name ="GOODS_CODE",nullable=true,length=50)
	public java.lang.String getGoodsCode(){
		return this.goodsCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编码
	 */
	public void setGoodsCode(java.lang.String goodsCode){
		this.goodsCode = goodsCode;
	}
	@Column(name ="CODE",nullable=true,length=50)
	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  原价
	 */
	@Column(name ="ORIGINAL_PRICE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getOriginalPrice(){
		return this.originalPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  原价
	 */
	public void setOriginalPrice(java.math.BigDecimal originalPrice){
		this.originalPrice = originalPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  现价
	 */
	@Column(name ="CURRENT_PRICE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getCurrentPrice(){
		return this.currentPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  现价
	 */
	public void setCurrentPrice(java.math.BigDecimal currentPrice){
		this.currentPrice = currentPrice;
	}
	@Column(name ="ACTIVITY_PRICE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(java.math.BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}
	@Column(name ="LOWEST_PRICE_DISCOUNT",nullable=true,scale=2,length=5)
	public java.math.BigDecimal getLowestPriceDiscount() {
		return lowestPriceDiscount;
	}

	public void setLowestPriceDiscount(java.math.BigDecimal lowestPriceDiscount) {
		this.lowestPriceDiscount = lowestPriceDiscount;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  最低价
	 */
	@Column(name ="LOWEST_PRICE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getLowestPrice(){
		return this.lowestPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  最低价
	 */
	public void setLowestPrice(java.math.BigDecimal lowestPrice){
		this.lowestPrice = lowestPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  组合价
	 */
	@Column(name ="GROUP_PRICE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getGroupPrice(){
		return this.groupPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  组合价
	 */
	public void setGroupPrice(java.math.BigDecimal groupPrice){
		this.groupPrice = groupPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  销量
	 */
	@Column(name ="SALES_VOLUME",nullable=true,length=12)
	public java.math.BigDecimal getSalesVolume(){
		return this.salesVolume;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  销量
	 */
	public void setSalesVolume(java.math.BigDecimal salesVolume){
		this.salesVolume = salesVolume;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  库存
	 */
	@Column(name ="GOODS_STOCK",nullable=true,length=12)
	public java.math.BigDecimal getGoodsStock(){
		return this.goodsStock;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  库存
	 */
	public void setGoodsStock(java.math.BigDecimal goodsStock){
		this.goodsStock = goodsStock;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新时间
	 */
	@Column(name ="GOODS_UPDATE_TIME",nullable=true,length=32)
	public java.util.Date getGoodsUpdateTime(){
		return this.goodsUpdateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setGoodsUpdateTime(java.util.Date goodsUpdateTime){
		this.goodsUpdateTime = goodsUpdateTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  搜索关键字
	 */
	@Column(name ="SEO_CARE",nullable=true,length=32)
	public java.lang.String getSeoCare(){
		return this.seoCare;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  搜索关键字
	 */
	public void setSeoCare(java.lang.String seoCare){
		this.seoCare = seoCare;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="SEO_DESC",nullable=true,length=200)
	public java.lang.String getSeoDesc(){
		return this.seoDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setSeoDesc(java.lang.String seoDesc){
		this.seoDesc = seoDesc;
	}
	@Column(name ="small_Pic",nullable=true,length=150)
	public java.lang.String getSmallPic() {
		return smallPic;
	}

	public void setSmallPic(java.lang.String smallPic) {
		this.smallPic = smallPic;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主图一
	 */
	@Column(name ="PIC_ONE",nullable=true,length=150)
	public java.lang.String getPicOne(){
		return this.picOne;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主图一
	 */
	public void setPicOne(java.lang.String picOne){
		this.picOne = picOne;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主图二
	 */
	@Column(name ="PIC_TWO",nullable=true,length=150)
	public java.lang.String getPicTwo(){
		return this.picTwo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主图二
	 */
	public void setPicTwo(java.lang.String picTwo){
		this.picTwo = picTwo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主图三
	 */
	@Column(name ="PIC_THREE",nullable=true,length=150)
	public java.lang.String getPicThree(){
		return this.picThree;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主图三
	 */
	public void setPicThree(java.lang.String picThree){
		this.picThree = picThree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主图四
	 */
	@Column(name ="PIC_FOUR",nullable=true,length=150)
	public java.lang.String getPicFour(){
		return this.picFour;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主图四
	 */
	public void setPicFour(java.lang.String picFour){
		this.picFour = picFour;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主图五
	 */
	@Column(name ="PIC_FIVE",nullable=true,length=150)
	public java.lang.String getPicFive(){
		return this.picFive;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主图五
	 */
	public void setPicFive(java.lang.String picFive){
		this.picFive = picFive;
	}
	@Column(name ="RETAILER_CODE",nullable=false,length=10)
	public java.lang.String getRetailerCode() {
		return retailerCode;
	}

	public void setRetailerCode(java.lang.String retailerCode) {
		this.retailerCode = retailerCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属零售商
	 */
	@Column(name ="RETAILER_ID",nullable=false,length=36)
	public java.lang.String getRetailerId(){
		return this.retailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属零售商
	 */
	public void setRetailerId(java.lang.String retailerId){
		this.retailerId = retailerId;
	}
	@Column(name ="RETAILER_NAME",nullable=false,length=36)
	public java.lang.String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(java.lang.String retailerName) {
		this.retailerName = retailerName;
	}
	@Column(name ="retailer_Type",length=1)
	public java.lang.String getRetailerType() {
		return retailerType;
	}

	public void setRetailerType(java.lang.String retailerType) {
		this.retailerType = retailerType;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品类型
	 */
	@Column(name ="GOODS_TYPE",nullable=true,length=2)
	public java.lang.String getGoodsType(){
		return this.goodsType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品类型
	 */
	public void setGoodsType(java.lang.String goodsType){
		this.goodsType = goodsType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品状态
	 */
	@Column(name ="GOODS_STATUS",nullable=true,length=2)
	public java.lang.String getGoodsStatus(){
		return this.goodsStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品状态
	 */
	public void setGoodsStatus(java.lang.String goodsStatus){
		this.goodsStatus = goodsStatus;
	}
	@Column(name ="PUBLISH_STATUS",nullable=true,length=1)
	public java.lang.String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(java.lang.String publishStatus) {
		this.publishStatus = publishStatus;
	}

	@Transient
	public TGoodsDescEntity getDesc() {
		return desc;
	}

	public void setDesc(TGoodsDescEntity desc) {
		this.desc = desc;
	}

	@Transient
	public List<TGoodsPicEntity> gettGoodsPicDetails() {
		return tGoodsPicDetails;
	}

	public void settGoodsPicDetails(List<TGoodsPicEntity> tGoodsPicDetails) {
		this.tGoodsPicDetails = tGoodsPicDetails;
	}

//	@Transient
//	public List<TGoodsAttrEntity> gettGoodsAttrDetails() {
//		return tGoodsAttrDetails;
//	}
//
//	public void settGoodsAttrDetails(List<TGoodsAttrEntity> tGoodsAttrDetails) {
//		this.tGoodsAttrDetails = tGoodsAttrDetails;
//	}
	@Transient
	public List<TGoodsStoreEntity> gettGoodsStoreDetails() {
		return tGoodsStoreDetails;
	}

	public void settGoodsStoreDetails(List<TGoodsStoreEntity> tGoodsStoreDetails) {
		this.tGoodsStoreDetails = tGoodsStoreDetails;
	}
	@Transient
	public List<TVisibleCategriesEntity> gettVisibleCatgys() {
		return tVisibleCatgys;
	}
	public void settVisibleCatgys(List<TVisibleCategriesEntity> tVisibleCatgys) {
		this.tVisibleCatgys = tVisibleCatgys;
	}
//	@Transient
//	public List<TSysRecmdPicsEntity> gettSysRecmdPics() {
//		return tSysRecmdPics;
//	}
//
//	public void settSysRecmdPics(List<TSysRecmdPicsEntity> tSysRecmdPics) {
//		this.tSysRecmdPics = tSysRecmdPics;
//	}
//
//	@Transient
//	public List<TGuideRecmdPicsEntity> gettGuideRecmdPics() {
//		return tGuideRecmdPics;
//	}
//
//	public void settGuideRecmdPics(List<TGuideRecmdPicsEntity> tGuideRecmdPics) {
//		this.tGuideRecmdPics = tGuideRecmdPics;
//	}

	@Column(name ="SUB_GOODS_TYPE",nullable=true,length=2)
	public java.lang.String getSubGoodsType() {
		return subGoodsType;
	}

	public void setSubGoodsType(java.lang.String subGoodsType) {
		this.subGoodsType = subGoodsType;
	}

	@Column(name ="province_id",nullable=true,length=36)
	public java.lang.String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(java.lang.String provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name ="city_id",nullable=true,length=36)
	public java.lang.String getCityId() {
		return cityId;
	}

	public void setCityId(java.lang.String cityId) {
		this.cityId = cityId;
	}
	@Transient
	public java.math.BigDecimal getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(java.math.BigDecimal prePrice) {
		this.prePrice = prePrice;
	}
	@Transient
	public java.math.BigDecimal getPreLowestPrice() {
		return preLowestPrice;
	}

	public void setPreLowestPrice(java.math.BigDecimal preLowestPrice) {
		this.preLowestPrice = preLowestPrice;
	}

	@Transient
	public java.math.BigDecimal getPreActivityPrice() {
		return preActivityPrice;
	}

	public void setPreActivityPrice(java.math.BigDecimal preActivityPrice) {
		this.preActivityPrice = preActivityPrice;
	}

	@Transient
	public String getCategries() {
		return categries;
	}

	public void setCategries(String categries) {
		this.categries = categries;
	}
	@Column(name ="group_Source",nullable=true,length=1)
	public String getGroupSource() {
		return groupSource;
	}

	public void setGroupSource(String groupSource) {
		this.groupSource = groupSource;
	}
	@Column(name ="GOODS_COLLECT")
	public java.lang.Integer getGoodsCollect() {
		return goodsCollect;
	}

	public void setGoodsCollect(java.lang.Integer goodsCollect) {
		this.goodsCollect = goodsCollect;
	}
	@Column(name ="NO_SENSE_NUM")
	public java.lang.Integer getNoSenseNum() {
		return noSenseNum;
	}

	public void setNoSenseNum(java.lang.Integer noSenseNum) {
		this.noSenseNum = noSenseNum;
	}
	@Column(name ="GOOD_NUM")
	public java.lang.Integer getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(java.lang.Integer goodNum) {
		this.goodNum = goodNum;
	}
	@Column(name ="GOODS_STAR")
	public java.math.BigDecimal getGoodsStar() {
		return goodsStar;
	}

	public void setGoodsStar(java.math.BigDecimal goodsStar) {
		this.goodsStar = goodsStar;
	}
	@Column(name ="SCORES_NUM")
	public java.math.BigDecimal getScoresNum() {
		return scoresNum;
	}

	public void setScoresNum(java.math.BigDecimal scoresNum) {
		this.scoresNum = scoresNum;
	}
	@Column(name ="fare_Type")
	public String getFareType() {
		return fareType;
	}

	public void setFareType(String fareType) {
		this.fareType = fareType;
	}
	@Column(name ="fare")
	public java.math.BigDecimal getFare() {
		return fare;
	}

	public void setFare(java.math.BigDecimal fare) {
		this.fare = fare;
	}
	@Column(name ="fare_Preferential_Type")
	public String getFarePreferentialType() {
		return farePreferentialType;
	}

	public void setFarePreferentialType(String farePreferentialType) {
		this.farePreferentialType = farePreferentialType;
	}
	@Column(name ="goods_Fare_Preferential")
	public java.math.BigDecimal getGoodsFarePreferential() {
		return goodsFarePreferential;
	}

	public void setGoodsFarePreferential(java.math.BigDecimal goodsFarePreferential) {
		this.goodsFarePreferential = goodsFarePreferential;
	}
	@Column(name ="cover_Pic")
	public String getCoverPic() {
		return coverPic;
	}
	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}
	@Column(name ="is_Show")
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	@Column(name ="is_Special")
	public String getIsSpecial() {
		return isSpecial;
	}
	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}
	@Column(name ="new_Goods_Type")
	public String getNewGoodsType() {
		return newGoodsType;
	}

	public void setNewGoodsType(String newGoodsType) {
		this.newGoodsType = newGoodsType;
	}
	@Transient
	public List<TProductInfoEntity> getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfoList(List<TProductInfoEntity> productInfoList) {
		this.productInfoList = productInfoList;
	}
	@Transient
	public List<TGuideRecommendDetailEntity> getRecommendDetailsList() {
		return recommendDetailsList;
	}

	public void setRecommendDetailsList(List<TGuideRecommendDetailEntity> recommendDetailsList) {
		this.recommendDetailsList = recommendDetailsList;
	}
//	@Transient
//	public List<TGoodsDescEntity> getDescList() {
//		return descList;
//	}
//
//	public void setDescList(List<TGoodsDescEntity> descList) {
//		this.descList = descList;
//	}
	@Column(name ="sort_Num")
	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}
	@Column(name ="has_Zhen")
	public String getHasZhen() {
		return hasZhen;
	}

	public void setHasZhen(String hasZhen) {
		this.hasZhen = hasZhen;
	}
	@Transient
	public List<TFinActivityWordsEntity> gettGoodsWordsDetails() {
		return tGoodsWordsDetails;
	}

	public void settGoodsWordsDetails(List<TFinActivityWordsEntity> tGoodsWordsDetails) {
		this.tGoodsWordsDetails = tGoodsWordsDetails;
	}
	
}
