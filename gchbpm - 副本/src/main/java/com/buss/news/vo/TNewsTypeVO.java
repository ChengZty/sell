package com.buss.news.vo;

public class TNewsTypeVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**编码（资讯类型）*/
	private java.lang.String code="";
	/**名字*/
	private java.lang.String name="";
	/**封面图片*/
	private java.lang.String coverPic="";
	public java.lang.String getCode() {
		return code;
	}
	public void setCode(java.lang.String code) {
		this.code = code;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getCoverPic() {
		return coverPic;
	}
	public void setCoverPic(java.lang.String coverPic) {
		this.coverPic = coverPic;
	}
	
}
