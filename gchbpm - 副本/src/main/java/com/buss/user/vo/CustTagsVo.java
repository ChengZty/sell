package com.buss.user.vo;


import com.buss.user.entity.TUserTagStoreEntity;

/**   
 * @Title: Entity
 * @Description: 用户标签vo
 * @author onlineGenerator
 * @date 2016-03-10 14:29:30
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class CustTagsVo implements java.io.Serializable {
	/**标题*/
	private java.lang.String tagTitle;
	/**编码*/
	private java.lang.String tagCode;
	/**类型*/
	private java.lang.String tagType;
	/**标签值*/
	private java.lang.String tagValues;
	/**用户标签的答案*/
	private TUserTagStoreEntity tagStore ;
	public java.lang.String getTagTitle() {
		return tagTitle;
	}
	public void setTagTitle(java.lang.String tagTitle) {
		this.tagTitle = tagTitle;
	}
	public java.lang.String getTagCode() {
		return tagCode;
	}
	public void setTagCode(java.lang.String tagCode) {
		this.tagCode = tagCode;
	}
	public java.lang.String getTagType() {
		return tagType;
	}
	public void setTagType(java.lang.String tagType) {
		this.tagType = tagType;
	}
	public java.lang.String getTagValues() {
		return tagValues;
	}
	public void setTagValues(java.lang.String tagValues) {
		this.tagValues = tagValues;
	}
	public TUserTagStoreEntity getTagStore() {
		return tagStore;
	}
	public void setTagStore(TUserTagStoreEntity tagStore) {
		this.tagStore = tagStore;
	}
	
}
