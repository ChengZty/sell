package com.buss.user.vo;

import java.io.Serializable;
/**
 * 导购个人资料
 * @author lenovo
 *
 */
public class PersonDetailsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 
	/**昵称*/
	private java.lang.String name="";
	/**真实姓名*/
	private java.lang.String realName="";
	/**性别*/
	private java.lang.String sex="";
	/**头像*/
	private java.lang.String photo="";
	/**手机号码*/
	private java.lang.String phoneNo="";
	/**生日*/
	private java.util.Date birthday;//跟实体birthday类型不一致（不影响数据）
	/**所在省*/
	private java.lang.String provinceId="";
	/**所在市*/
	private java.lang.String cityId="";
	/**所属地区*/
	private java.lang.String area="";
	/**身份证号*/
	private java.lang.String idCard="";
	/**星座*/
	private java.lang.String constellation="";
	/**生肖*/
	private java.lang.String zodiac="";
	/**籍贯*/
	private java.lang.String birthplace="";
	/**购物习惯*/
	private java.lang.String shoppingHabits="";
	/**支付习惯*/
	private java.lang.String payHabits="";
	/**资质证书*/
	private java.lang.String certification="";
	/**身高*/
	private java.lang.String height;
	/**体重*/
	private java.lang.String weight;
	
	/**梦想*/
	private java.lang.String dream="";//用逗号拼接
	/**专长*/
	private java.lang.String speciality="";
	/**爱好*/
	private java.lang.String hobby="";
	//新添加的个人信息
	/**学历*/
	private java.lang.String education="";
	/**曾就读学校*/
	private java.lang.String onceSchool="";
	/**在读*/
	private java.lang.String reading="";
	/**分工特长*/
	private java.lang.String laborSpecially="";
	
	/**个人性格描述*/
	private java.lang.String personCharacterDesc="";
	/**从业开始时间*/
	private java.lang.String jobStartTime="";
	/**常住地*/
	private java.lang.String permanentPlace="";
	/**曾经居住地*/
	private java.lang.String onceLivePlace="";
	/**居住房状况*/
	private java.lang.String housingCondition="";
	/**出行工具状况*/
	private java.lang.String travelTools="";
	/**使用通讯工具状况*/
	private java.lang.String communicationTools="";
	
	
	// 婚姻状况 marriageStatus
	/**生育状况*/
	private java.lang.String bearStauts="";
	
	/**另一半基础状况*/
	private java.lang.String halfBaseInfo="";
	/**另一半感性描述*/
	private java.lang.String halfEmotionalDesc="";
	
	/**孩子基础状况*/
	private java.lang.String childrenBaseInfo="";
	/**孩子感性描述*/
	private java.lang.String childrenEmotionalDesc="";
	
	/**父亲 年龄*/
	private java.lang.String fatherAge="";
	/**父亲行业*/
	private java.lang.String fatherIndustry="";
	/**父亲在职状态*/
	private java.lang.String fatherWorkStatus="";
	
	/**母亲 年龄*/
	private java.lang.String motherAge="";
	/**母亲行业*/
	private java.lang.String motherIndustry="";
	/**母亲在职状态*/
	private java.lang.String motherWorkStatus="";
	
	/**兄弟姐妹 */
	private java.lang.String xdjm;
	/**兄弟姐妹 年龄*/
	private java.lang.String xdjmAge="";
	/**兄弟姐妹行业*/
	private java.lang.String xdjmIndustry="";
	/**兄弟姐妹在职状态*/
	private java.lang.String xdjmWorkStatus="";
	
	/**爱好的宠物*/
	private java.lang.String favoritePets="";
	/**养育的宠物*/
	private java.lang.String keepPets="";
	/**个人爱好  hobbyStr  （逗号拼接）*/
	private java.lang.String hobbyStr="";
	/**个人的着装风格*/
	private java.lang.String personalDressStyle="";  //（逗号拼接）
	/**喜欢的品牌*/
	private java.lang.String favoriteBrands="";
	//喜欢的影视明星  favoriteMovieStar
	//喜爱的歌手  favoriteSinger
	//喜爱的体育明星  favoriteSportsStars
	//喜爱的运动  favoriteSports
	//最向往的国家
	private java.lang.String wantGoCountry="";
	//最喜欢的城市：loveCity
	/**去过的国家*/
	private java.lang.String goneCountry="";
	/**去过的城市*/
	private java.lang.String goneCity="";
	//常用app：constantApp(公用)
	//嗜好：habit（公用）
	/**常购品类*/
	private java.lang.String oftenBuy="";
	/**常用品牌*/
	private java.lang.String brand="";
	/**个人SLOGAN*/
	private java.lang.String slogan="";
	/**送给顾客的一段话*/
	private java.lang.String words="";
	
	/**家庭/婚姻状况*/
	private java.lang.String marriageStatus;
	/**喜欢的影视明星*/
	private java.lang.String favoriteMovieStar;
	/**喜爱的歌手*/
	private String favoriteSinger;
	/**喜爱的体育明星*/
	private String favoriteSportsStars;
	/**喜爱的运动*/
	private java.lang.String favoriteSports;
	/**热爱的城市*/
	private java.lang.String loveCity;
	/**常用的app*/
	private java.lang.String constantApp;
	/**嗜好*/
	private java.lang.String habit;
	
	public java.lang.String getMarriageStatus() {
		return marriageStatus;
	}
	public void setMarriageStatus(java.lang.String marriageStatus) {
		this.marriageStatus = marriageStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public java.lang.String getRealName() {
		return realName;
	}
	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}
	public java.lang.String getSex() {
		return sex;
	}
	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}
	public java.lang.String getPhoto() {
		return photo;
	}
	public void setPhoto(java.lang.String photo) {
		this.photo = photo;
	}
	public java.lang.String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(java.lang.String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public java.lang.String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(java.lang.String provinceId) {
		this.provinceId = provinceId;
	}
	public java.lang.String getCityId() {
		return cityId;
	}
	public void setCityId(java.lang.String cityId) {
		this.cityId = cityId;
	}
	public java.lang.String getArea() {
		return area;
	}
	public void setArea(java.lang.String area) {
		this.area = area;
	}
	public java.lang.String getIdCard() {
		return idCard;
	}
	public void setIdCard(java.lang.String idCard) {
		this.idCard = idCard;
	}
	public java.lang.String getConstellation() {
		return constellation;
	}
	public void setConstellation(java.lang.String constellation) {
		this.constellation = constellation;
	}
	public java.lang.String getZodiac() {
		return zodiac;
	}
	public void setZodiac(java.lang.String zodiac) {
		this.zodiac = zodiac;
	}
	public java.lang.String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(java.lang.String birthplace) {
		this.birthplace = birthplace;
	}
	public java.lang.String getShoppingHabits() {
		return shoppingHabits;
	}
	public void setShoppingHabits(java.lang.String shoppingHabits) {
		this.shoppingHabits = shoppingHabits;
	}
	public java.lang.String getPayHabits() {
		return payHabits;
	}
	public void setPayHabits(java.lang.String payHabits) {
		this.payHabits = payHabits;
	}
	public java.lang.String getCertification() {
		return certification;
	}
	public void setCertification(java.lang.String certification) {
		this.certification = certification;
	}
	public java.lang.String getDream() {
		return dream;
	}
	public void setDream(java.lang.String dream) {
		this.dream = dream;
	}
	public java.lang.String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(java.lang.String speciality) {
		this.speciality = speciality;
	}
	public java.lang.String getHobby() {
		return hobby;
	}
	public void setHobby(java.lang.String hobby) {
		this.hobby = hobby;
	}
	public java.lang.String getEducation() {
		return education;
	}
	public void setEducation(java.lang.String education) {
		this.education = education;
	}
	public java.lang.String getOnceSchool() {
		return onceSchool;
	}
	public void setOnceSchool(java.lang.String onceSchool) {
		this.onceSchool = onceSchool;
	}
	public java.lang.String getReading() {
		return reading;
	}
	public void setReading(java.lang.String reading) {
		this.reading = reading;
	}
	public java.lang.String getLaborSpecially() {
		return laborSpecially;
	}
	public void setLaborSpecially(java.lang.String laborSpecially) {
		this.laborSpecially = laborSpecially;
	}
	public java.lang.String getPersonCharacterDesc() {
		return personCharacterDesc;
	}
	public void setPersonCharacterDesc(java.lang.String personCharacterDesc) {
		this.personCharacterDesc = personCharacterDesc;
	}
	public java.lang.String getJobStartTime() {
		return jobStartTime;
	}
	public void setJobStartTime(java.lang.String jobStartTime) {
		this.jobStartTime = jobStartTime;
	}
	public java.lang.String getPermanentPlace() {
		return permanentPlace;
	}
	public void setPermanentPlace(java.lang.String permanentPlace) {
		this.permanentPlace = permanentPlace;
	}
	public java.lang.String getOnceLivePlace() {
		return onceLivePlace;
	}
	public void setOnceLivePlace(java.lang.String onceLivePlace) {
		this.onceLivePlace = onceLivePlace;
	}
	public java.lang.String getHousingCondition() {
		return housingCondition;
	}
	public void setHousingCondition(java.lang.String housingCondition) {
		this.housingCondition = housingCondition;
	}
	public java.lang.String getTravelTools() {
		return travelTools;
	}
	public void setTravelTools(java.lang.String travelTools) {
		this.travelTools = travelTools;
	}
	public java.lang.String getCommunicationTools() {
		return communicationTools;
	}
	public void setCommunicationTools(java.lang.String communicationTools) {
		this.communicationTools = communicationTools;
	}
	public java.lang.String getBearStauts() {
		return bearStauts;
	}
	public void setBearStauts(java.lang.String bearStauts) {
		this.bearStauts = bearStauts;
	}
	public java.lang.String getHalfBaseInfo() {
		return halfBaseInfo;
	}
	public void setHalfBaseInfo(java.lang.String halfBaseInfo) {
		this.halfBaseInfo = halfBaseInfo;
	}
	public java.lang.String getHalfEmotionalDesc() {
		return halfEmotionalDesc;
	}
	public void setHalfEmotionalDesc(java.lang.String halfEmotionalDesc) {
		this.halfEmotionalDesc = halfEmotionalDesc;
	}
	public java.lang.String getChildrenBaseInfo() {
		return childrenBaseInfo;
	}
	public void setChildrenBaseInfo(java.lang.String childrenBaseInfo) {
		this.childrenBaseInfo = childrenBaseInfo;
	}
	public java.lang.String getChildrenEmotionalDesc() {
		return childrenEmotionalDesc;
	}
	public void setChildrenEmotionalDesc(java.lang.String childrenEmotionalDesc) {
		this.childrenEmotionalDesc = childrenEmotionalDesc;
	}
	public java.lang.String getFatherAge() {
		return fatherAge;
	}
	public void setFatherAge(java.lang.String fatherAge) {
		this.fatherAge = fatherAge;
	}
	public java.lang.String getFatherIndustry() {
		return fatherIndustry;
	}
	public void setFatherIndustry(java.lang.String fatherIndustry) {
		this.fatherIndustry = fatherIndustry;
	}
	public java.lang.String getFatherWorkStatus() {
		return fatherWorkStatus;
	}
	public void setFatherWorkStatus(java.lang.String fatherWorkStatus) {
		this.fatherWorkStatus = fatherWorkStatus;
	}
	public java.lang.String getMotherAge() {
		return motherAge;
	}
	public void setMotherAge(java.lang.String motherAge) {
		this.motherAge = motherAge;
	}
	public java.lang.String getMotherIndustry() {
		return motherIndustry;
	}
	public void setMotherIndustry(java.lang.String motherIndustry) {
		this.motherIndustry = motherIndustry;
	}
	public java.lang.String getMotherWorkStatus() {
		return motherWorkStatus;
	}
	public void setMotherWorkStatus(java.lang.String motherWorkStatus) {
		this.motherWorkStatus = motherWorkStatus;
	}
	public java.lang.String getXdjmAge() {
		return xdjmAge;
	}
	public void setXdjmAge(java.lang.String xdjmAge) {
		this.xdjmAge = xdjmAge;
	}
	public java.lang.String getXdjmIndustry() {
		return xdjmIndustry;
	}
	public void setXdjmIndustry(java.lang.String xdjmIndustry) {
		this.xdjmIndustry = xdjmIndustry;
	}
	public java.lang.String getXdjmWorkStatus() {
		return xdjmWorkStatus;
	}
	public void setXdjmWorkStatus(java.lang.String xdjmWorkStatus) {
		this.xdjmWorkStatus = xdjmWorkStatus;
	}
	public java.lang.String getFavoritePets() {
		return favoritePets;
	}
	public void setFavoritePets(java.lang.String favoritePets) {
		this.favoritePets = favoritePets;
	}
	public java.lang.String getKeepPets() {
		return keepPets;
	}
	public void setKeepPets(java.lang.String keepPets) {
		this.keepPets = keepPets;
	}
	public java.lang.String getPersonalDressStyle() {
		return personalDressStyle;
	}
	public void setPersonalDressStyle(java.lang.String personalDressStyle) {
		this.personalDressStyle = personalDressStyle;
	}
	public java.lang.String getFavoriteBrands() {
		return favoriteBrands;
	}
	public void setFavoriteBrands(java.lang.String favoriteBrands) {
		this.favoriteBrands = favoriteBrands;
	}
	public java.lang.String getWantGoCountry() {
		return wantGoCountry;
	}
	public void setWantGoCountry(java.lang.String wantGoCountry) {
		this.wantGoCountry = wantGoCountry;
	}
	public java.lang.String getGoneCountry() {
		return goneCountry;
	}
	public void setGoneCountry(java.lang.String goneCountry) {
		this.goneCountry = goneCountry;
	}
	public java.lang.String getGoneCity() {
		return goneCity;
	}
	public void setGoneCity(java.lang.String goneCity) {
		this.goneCity = goneCity;
	}
	public java.lang.String getOftenBuy() {
		return oftenBuy;
	}
	public void setOftenBuy(java.lang.String oftenBuy) {
		this.oftenBuy = oftenBuy;
	}
	public java.lang.String getBrand() {
		return brand;
	}
	public void setBrand(java.lang.String brand) {
		this.brand = brand;
	}
	public java.lang.String getSlogan() {
		return slogan;
	}
	public void setSlogan(java.lang.String slogan) {
		this.slogan = slogan;
	}
	public java.lang.String getWords() {
		return words;
	}
	public void setWords(java.lang.String words) {
		this.words = words;
	}
	public java.lang.String getFavoriteMovieStar() {
		return favoriteMovieStar;
	}
	public void setFavoriteMovieStar(java.lang.String favoriteMovieStar) {
		this.favoriteMovieStar = favoriteMovieStar;
	}
	public String getFavoriteSinger() {
		return favoriteSinger;
	}
	public void setFavoriteSinger(String favoriteSinger) {
		this.favoriteSinger = favoriteSinger;
	}
	public String getFavoriteSportsStars() {
		return favoriteSportsStars;
	}
	public void setFavoriteSportsStars(String favoriteSportsStars) {
		this.favoriteSportsStars = favoriteSportsStars;
	}
	public java.lang.String getFavoriteSports() {
		return favoriteSports;
	}
	public void setFavoriteSports(java.lang.String favoriteSports) {
		this.favoriteSports = favoriteSports;
	}
	public java.lang.String getLoveCity() {
		return loveCity;
	}
	public void setLoveCity(java.lang.String loveCity) {
		this.loveCity = loveCity;
	}
	public java.lang.String getConstantApp() {
		return constantApp;
	}
	public void setConstantApp(java.lang.String constantApp) {
		this.constantApp = constantApp;
	}
	public java.lang.String getHabit() {
		return habit;
	}
	public void setHabit(java.lang.String habit) {
		this.habit = habit;
	}
	public java.lang.String getXdjm() {
		return xdjm;
	}
	public void setXdjm(java.lang.String xdjm) {
		this.xdjm = xdjm;
	}
	public java.lang.String getHeight() {
		return height;
	}
	public void setHeight(java.lang.String height) {
		this.height = height;
	}
	public java.lang.String getWeight() {
		return weight;
	}
	public void setWeight(java.lang.String weight) {
		this.weight = weight;
	}
	public java.lang.String getHobbyStr() {
		return hobbyStr;
	}
	public void setHobbyStr(java.lang.String hobbyStr) {
		this.hobbyStr = hobbyStr;
	}
	
}
