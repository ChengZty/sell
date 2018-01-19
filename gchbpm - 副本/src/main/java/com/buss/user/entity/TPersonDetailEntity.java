package com.buss.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 完善的资料
 * @author zhangdaihao
 * @date 2016-05-04 11:49:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_person_Detail", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TPersonDetailEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**年龄段位*/
	private java.lang.String birthdayRank;
	/**家庭/婚姻状况*/
	private java.lang.String marriageStatus;
	/**住房类型*/
	private java.lang.String housingType;
	/**爱好*/
	private java.lang.String hobby;
	/**喜欢的影视明星*/
	private java.lang.String favoriteMovieStar;
	/**喜爱的歌手*/
	private String favoriteSinger;
	/**喜爱的体育明星*/
	private String favoriteSportsStars;
	/**喜爱的运动*/
	private java.lang.String favoriteSports;
	/**常用的app*/
	private java.lang.String constantApp;
	/**嗜好*/
	private java.lang.String habit;
	/**娱乐休闲方式*/
	private java.lang.String entertainmentMode;
	/**医疗保险*/
	private java.lang.String medicalInsurance;
	/**是否定期体检*/
	private java.lang.String isRegularCheck;
	/**有损部位*/
	private java.lang.String harmPart;
	/**推拿按摩（是否）*/
	private java.lang.String isMassage;
	/**整形（是否）*/
	private java.lang.String isPlastic;
	/**SPA（是否）*/
	private java.lang.String isSpa;
	/**是否养宠物*/
	private java.lang.String isKeepPets;
	/**恋爱史（是否）*/
	private java.lang.String isLoveHistory;
	/**婚姻史（是否）*/
	private java.lang.String isMarritalHistory;
	/**热爱的城市*/
	private java.lang.String loveCity;
	/**讨厌的城市*/
	private java.lang.String hateCity;
	/**热爱的国家*/
	private java.lang.String loveCountry;
	/**讨厌的国家*/
	private java.lang.String hateCountry;
	/**成长的遗憾*/
	private java.lang.String growPitty;
	/**最近的成就*/
	private java.lang.String recentAchievements;
	/**最近的挫折*/
	private java.lang.String recentFrustration;
	/**关注的问题*/
	private java.lang.String concernProblems;
	/**常用的服装轮廓*/
	private java.lang.String clothingProfile;
	/**基本的着装风格*/
	private java.lang.String basicDressStyle;
	/**喜欢的设计风格*/
	private java.lang.String favoriteDesignStyle;
	/**上衣尺码*/
	private java.lang.String coatSize;
	/**下装尺码*/
	private java.lang.String downSize;
	/**内衣/罩杯尺码*/
	private java.lang.String cupSize;
	/**鞋尺码*/
	private java.lang.String shoeSize;
	/**胸围*/
	private java.lang.String bust;
	/**腰围*/
	private java.lang.String waist;
	/**臂围*/
	private java.lang.String arm;
	/**肩宽*/
	private java.lang.String breadth;
	/**臀围*/
	private java.lang.String hipline;
	/**特殊部位*/
	private java.lang.String specialPart;
	/**初级体型特征*/
	private java.lang.String primaryBodyFeature;
	/**目前发型*/
	private java.lang.String currentHair;
	/**皮肤状况*/
	private java.lang.String skinCondition;
	/**牙齿状况*/
	private java.lang.String dentalCondition;
	/**是否愿意接受新品信息*/
	private java.lang.String isAcceptNew;
	/**是否愿意接受促销信息*/
	private java.lang.String isAcceptSale;
	/**帐号id*/
	private java.lang.String userId;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  年龄段位
	 */
	@Column(name ="BIRTHDAY_RANK",nullable=true,length=50)
	public java.lang.String getBirthdayRank(){
		return this.birthdayRank;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  年龄段位
	 */
	public void setBirthdayRank(java.lang.String birthdayRank){
		this.birthdayRank = birthdayRank;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  家庭/婚姻状况
	 */
	@Column(name ="MARRIAGE_STATUS",nullable=true,length=50)
	public java.lang.String getMarriageStatus(){
		return this.marriageStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  家庭/婚姻状况
	 */
	public void setMarriageStatus(java.lang.String marriageStatus){
		this.marriageStatus = marriageStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  住房类型
	 */
	@Column(name ="HOUSING_TYPE",nullable=true,length=50)
	public java.lang.String getHousingType(){
		return this.housingType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  住房类型
	 */
	public void setHousingType(java.lang.String housingType){
		this.housingType = housingType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  爱好
	 */
	@Column(name ="HOBBY",nullable=true,length=50)
	public java.lang.String getHobby(){
		return this.hobby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  爱好
	 */
	public void setHobby(java.lang.String hobby){
		this.hobby = hobby;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  喜欢的影视明星
	 */
	@Column(name ="FAVORITE_MOVIE_STAR",nullable=true,length=50)
	public java.lang.String getFavoriteMovieStar(){
		return this.favoriteMovieStar;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  喜欢的影视明星
	 */
	public void setFavoriteMovieStar(java.lang.String favoriteMovieStar){
		this.favoriteMovieStar = favoriteMovieStar;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  喜爱的歌手
	 */
	@Column(name ="FAVORITE_SINGER",nullable=true,precision=50)
	public String getFavoriteSinger(){
		return this.favoriteSinger;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  喜爱的歌手
	 */
	public void setFavoriteSinger(String favoriteSinger){
		this.favoriteSinger = favoriteSinger;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  喜爱的体育明星
	 */
	@Column(name ="FAVORITE_SPORTS_STARS",nullable=true,precision=50)
	public String getFavoriteSportsStars(){
		return this.favoriteSportsStars;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  喜爱的体育明星
	 */
	public void setFavoriteSportsStars(String favoriteSportsStars){
		this.favoriteSportsStars = favoriteSportsStars;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  喜爱的运动
	 */
	@Column(name ="FAVORITE_SPORTS",nullable=true,length=50)
	public java.lang.String getFavoriteSports(){
		return this.favoriteSports;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  喜爱的运动
	 */
	public void setFavoriteSports(java.lang.String favoriteSports){
		this.favoriteSports = favoriteSports;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  常用的app
	 */
	@Column(name ="CONSTANT_APP",nullable=true,length=50)
	public java.lang.String getConstantApp(){
		return this.constantApp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  常用的app
	 */
	public void setConstantApp(java.lang.String constantApp){
		this.constantApp = constantApp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  嗜好
	 */
	@Column(name ="HABIT",nullable=true,length=50)
	public java.lang.String getHabit(){
		return this.habit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  嗜好
	 */
	public void setHabit(java.lang.String habit){
		this.habit = habit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  娱乐休闲方式
	 */
	@Column(name ="ENTERTAINMENT_MODE",nullable=true,length=50)
	public java.lang.String getEntertainmentMode(){
		return this.entertainmentMode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  娱乐休闲方式
	 */
	public void setEntertainmentMode(java.lang.String entertainmentMode){
		this.entertainmentMode = entertainmentMode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  医疗保险
	 */
	@Column(name ="MEDICAL_INSURANCE",nullable=true,length=50)
	public java.lang.String getMedicalInsurance(){
		return this.medicalInsurance;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  医疗保险
	 */
	public void setMedicalInsurance(java.lang.String medicalInsurance){
		this.medicalInsurance = medicalInsurance;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否定期体检
	 */
	@Column(name ="IS_REGULAR_CHECK",nullable=true,length=1)
	public java.lang.String getIsRegularCheck(){
		return this.isRegularCheck;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否定期体检
	 */
	public void setIsRegularCheck(java.lang.String isRegularCheck){
		this.isRegularCheck = isRegularCheck;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  有损部位
	 */
	@Column(name ="HARM_PART",nullable=true,length=50)
	public java.lang.String getHarmPart(){
		return this.harmPart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  有损部位
	 */
	public void setHarmPart(java.lang.String harmPart){
		this.harmPart = harmPart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  推拿按摩（是否）
	 */
	@Column(name ="IS_MASSAGE",nullable=true,length=1)
	public java.lang.String getIsMassage(){
		return this.isMassage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  推拿按摩（是否）
	 */
	public void setIsMassage(java.lang.String isMassage){
		this.isMassage = isMassage;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整形（是否）
	 */
	@Column(name ="IS_PLASTIC",nullable=true,length=1)
	public java.lang.String getIsPlastic(){
		return this.isPlastic;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整形（是否）
	 */
	public void setIsPlastic(java.lang.String isPlastic){
		this.isPlastic = isPlastic;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  SPA（是否）
	 */
	@Column(name ="IS_SPA",nullable=true,length=1)
	public java.lang.String getIsSpa(){
		return this.isSpa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  SPA（是否）
	 */
	public void setIsSpa(java.lang.String isSpa){
		this.isSpa = isSpa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否养宠物
	 */
	@Column(name ="IS_KEEP_PETS",nullable=true,length=1)
	public java.lang.String getIsKeepPets(){
		return this.isKeepPets;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否养宠物
	 */
	public void setIsKeepPets(java.lang.String isKeepPets){
		this.isKeepPets = isKeepPets;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  恋爱史（是否）
	 */
	@Column(name ="IS_LOVE_HISTORY",nullable=true,length=1)
	public java.lang.String getIsLoveHistory(){
		return this.isLoveHistory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  恋爱史（是否）
	 */
	public void setIsLoveHistory(java.lang.String isLoveHistory){
		this.isLoveHistory = isLoveHistory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  婚姻史（是否）
	 */
	@Column(name ="IS_MARRITAL_HISTORY",nullable=true,length=1)
	public java.lang.String getIsMarritalHistory(){
		return this.isMarritalHistory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  婚姻史（是否）
	 */
	public void setIsMarritalHistory(java.lang.String isMarritalHistory){
		this.isMarritalHistory = isMarritalHistory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  热爱的城市
	 */
	@Column(name ="LOVE_CITY",nullable=true,length=50)
	public java.lang.String getLoveCity(){
		return this.loveCity;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  热爱的城市
	 */
	public void setLoveCity(java.lang.String loveCity){
		this.loveCity = loveCity;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  讨厌的城市
	 */
	@Column(name ="HATE_CITY",nullable=true,length=50)
	public java.lang.String getHateCity(){
		return this.hateCity;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  讨厌的城市
	 */
	public void setHateCity(java.lang.String hateCity){
		this.hateCity = hateCity;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  热爱的国家
	 */
	@Column(name ="LOVE_COUNTRY",nullable=true,length=50)
	public java.lang.String getLoveCountry(){
		return this.loveCountry;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  热爱的国家
	 */
	public void setLoveCountry(java.lang.String loveCountry){
		this.loveCountry = loveCountry;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  讨厌的国家
	 */
	@Column(name ="HATE_COUNTRY",nullable=true,length=50)
	public java.lang.String getHateCountry(){
		return this.hateCountry;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  讨厌的国家
	 */
	public void setHateCountry(java.lang.String hateCountry){
		this.hateCountry = hateCountry;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  成长的遗憾
	 */
	@Column(name ="GROW_PITTY",nullable=true,length=50)
	public java.lang.String getGrowPitty(){
		return this.growPitty;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  成长的遗憾
	 */
	public void setGrowPitty(java.lang.String growPitty){
		this.growPitty = growPitty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最近的成就
	 */
	@Column(name ="RECENT_ACHIEVEMENTS",nullable=true,length=50)
	public java.lang.String getRecentAchievements(){
		return this.recentAchievements;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  最近的成就
	 */
	public void setRecentAchievements(java.lang.String recentAchievements){
		this.recentAchievements = recentAchievements;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最近的挫折
	 */
	@Column(name ="RECENT_FRUSTRATION",nullable=true,length=50)
	public java.lang.String getRecentFrustration(){
		return this.recentFrustration;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  最近的挫折
	 */
	public void setRecentFrustration(java.lang.String recentFrustration){
		this.recentFrustration = recentFrustration;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关注的问题
	 */
	@Column(name ="CONCERN_PROBLEMS",nullable=true,length=50)
	public java.lang.String getConcernProblems(){
		return this.concernProblems;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关注的问题
	 */
	public void setConcernProblems(java.lang.String concernProblems){
		this.concernProblems = concernProblems;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  常用的服装轮廓
	 */
	@Column(name ="CLOTHING_PROFILE",nullable=true,length=50)
	public java.lang.String getClothingProfile(){
		return this.clothingProfile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  常用的服装轮廓
	 */
	public void setClothingProfile(java.lang.String clothingProfile){
		this.clothingProfile = clothingProfile;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本的着装风格
	 */
	@Column(name ="BASIC_DRESS_STYLE",nullable=true,length=50)
	public java.lang.String getBasicDressStyle(){
		return this.basicDressStyle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本的着装风格
	 */
	public void setBasicDressStyle(java.lang.String basicDressStyle){
		this.basicDressStyle = basicDressStyle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  喜欢的设计风格
	 */
	@Column(name ="FAVORITE_DESIGN_STYLE",nullable=true,length=50)
	public java.lang.String getFavoriteDesignStyle(){
		return this.favoriteDesignStyle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  喜欢的设计风格
	 */
	public void setFavoriteDesignStyle(java.lang.String favoriteDesignStyle){
		this.favoriteDesignStyle = favoriteDesignStyle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上衣尺码
	 */
	@Column(name ="COAT_SIZE",nullable=true,length=5)
	public java.lang.String getCoatSize(){
		return this.coatSize;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上衣尺码
	 */
	public void setCoatSize(java.lang.String coatSize){
		this.coatSize = coatSize;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  下装尺码
	 */
	@Column(name ="DOWN_SIZE",nullable=true,length=5)
	public java.lang.String getDownSize(){
		return this.downSize;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下装尺码
	 */
	public void setDownSize(java.lang.String downSize){
		this.downSize = downSize;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内衣/罩杯尺码
	 */
	@Column(name ="CUP_SIZE",nullable=true,length=5)
	public java.lang.String getCupSize(){
		return this.cupSize;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内衣/罩杯尺码
	 */
	public void setCupSize(java.lang.String cupSize){
		this.cupSize = cupSize;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  鞋尺码
	 */
	@Column(name ="SHOE_SIZE",nullable=true,length=5)
	public java.lang.String getShoeSize(){
		return this.shoeSize;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  鞋尺码
	 */
	public void setShoeSize(java.lang.String shoeSize){
		this.shoeSize = shoeSize;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  胸围
	 */
	@Column(name ="BUST",nullable=true,length=5)
	public java.lang.String getBust(){
		return this.bust;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  胸围
	 */
	public void setBust(java.lang.String bust){
		this.bust = bust;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  腰围
	 */
	@Column(name ="WAIST",nullable=true,length=5)
	public java.lang.String getWaist(){
		return this.waist;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  腰围
	 */
	public void setWaist(java.lang.String waist){
		this.waist = waist;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  臂围
	 */
	@Column(name ="ARM",nullable=true,length=5)
	public java.lang.String getArm(){
		return this.arm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  臂围
	 */
	public void setArm(java.lang.String arm){
		this.arm = arm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  肩宽
	 */
	@Column(name ="BREADTH",nullable=true,length=5)
	public java.lang.String getBreadth(){
		return this.breadth;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  肩宽
	 */
	public void setBreadth(java.lang.String breadth){
		this.breadth = breadth;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  臀围
	 */
	@Column(name ="HIPLINE",nullable=true,length=5)
	public java.lang.String getHipline(){
		return this.hipline;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  臀围
	 */
	public void setHipline(java.lang.String hipline){
		this.hipline = hipline;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  特殊部位
	 */
	@Column(name ="SPECIAL_PART",nullable=true,length=5)
	public java.lang.String getSpecialPart(){
		return this.specialPart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  特殊部位
	 */
	public void setSpecialPart(java.lang.String specialPart){
		this.specialPart = specialPart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  初级体型特征
	 */
	@Column(name ="PRIMARY_BODY_FEATURE",nullable=true,length=50)
	public java.lang.String getPrimaryBodyFeature(){
		return this.primaryBodyFeature;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  初级体型特征
	 */
	public void setPrimaryBodyFeature(java.lang.String primaryBodyFeature){
		this.primaryBodyFeature = primaryBodyFeature;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  目前发型
	 */
	@Column(name ="CURRENT_HAIR",nullable=true,length=50)
	public java.lang.String getCurrentHair(){
		return this.currentHair;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  目前发型
	 */
	public void setCurrentHair(java.lang.String currentHair){
		this.currentHair = currentHair;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  皮肤状况
	 */
	@Column(name ="SKIN_CONDITION",nullable=true,length=50)
	public java.lang.String getSkinCondition(){
		return this.skinCondition;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  皮肤状况
	 */
	public void setSkinCondition(java.lang.String skinCondition){
		this.skinCondition = skinCondition;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  牙齿状况
	 */
	@Column(name ="DENTAL_CONDITION",nullable=true,length=50)
	public java.lang.String getDentalCondition(){
		return this.dentalCondition;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  牙齿状况
	 */
	public void setDentalCondition(java.lang.String dentalCondition){
		this.dentalCondition = dentalCondition;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否愿意接受新品信息
	 */
	@Column(name ="IS_ACCEPT_NEW",nullable=true,length=1)
	public java.lang.String getIsAcceptNew(){
		return this.isAcceptNew;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否愿意接受新品信息
	 */
	public void setIsAcceptNew(java.lang.String isAcceptNew){
		this.isAcceptNew = isAcceptNew;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否愿意接受促销信息
	 */
	@Column(name ="IS_ACCEPT_SALE",nullable=true,length=1)
	public java.lang.String getIsAcceptSale(){
		return this.isAcceptSale;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否愿意接受促销信息
	 */
	public void setIsAcceptSale(java.lang.String isAcceptSale){
		this.isAcceptSale = isAcceptSale;
	}
	@Column(name ="user_Id",nullable=true,length=36)
	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
}
