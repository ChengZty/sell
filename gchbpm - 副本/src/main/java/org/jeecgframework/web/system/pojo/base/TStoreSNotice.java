package org.jeecgframework.web.system.pojo.base;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 零售商通知公告表
 *  @author  alax
 */
@Entity
@Table(name = "t_s_notice_store")
@SuppressWarnings("serial")
@Where(clause="status='A'")
public class TStoreSNotice implements java.io.Serializable {
	public static final String NOTICE_LEVEL_ALL = "1";//全体导购
	public static final String NOTICE_LEVEL_SPEC = "2";//指定导购
	private String id;
	private String noticeTitle;// 通告标题
	private String noticeContent;// 通告内容
	private String noticeLevel;// 通告授权级别（1:全公司导购，2：选择性导购）
	private String storeId; //零售商ID
	private Date createTime;  //创建时间
	private String isSend; //是否已推送
	/**状态*/
	private java.lang.String status;
	private String isRead; //是否已读(0:未读，1：已读)
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
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
	 *@param: java.lang.String  ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	@Column(name ="notice_title",nullable=true)
	public String getNoticeTitle() {
		return noticeTitle;
	}
	
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	
	@Column(name ="notice_content",nullable=true)
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	
	@Column(name ="notice_level",nullable=true)
	public String getNoticeLevel() {
		return noticeLevel;
	}
	public void setNoticeLevel(String noticeLevel) {
		this.noticeLevel = noticeLevel;
	}
	
	
	@Column(name ="create_time",nullable=true)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name ="store_id",nullable=true)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	@Transient
	public String getIsRead() {
		return isRead;
	}
	@Column(name ="is_send",nullable=true)
	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}
	@Column(name ="status",nullable=true)
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	
}