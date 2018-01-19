package com.buss.shop.vo;

import java.util.List;

import com.buss.shop.entity.TSmsPicClassEntity;
import com.buss.shop.entity.TSmsPicInfoEntity;


/**   
 * @Title: Entity
 * @Description: t_sms_send
 * @author onlineGenerator
 * @date 2017-02-25 12:31:17
 * @version V1.0   
 *
 */
public class TSmsPicPageVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	List<TSmsPicClassEntity> picClass;
	List<TSmsPicInfoEntity> picInfos;
	private int totalPage;
	private int totalcount;
	
	public List<TSmsPicClassEntity> getPicClass() {
		return picClass;
	}
	public void setPicClass(List<TSmsPicClassEntity> picClass) {
		this.picClass = picClass;
	}
	public List<TSmsPicInfoEntity> getPicInfos() {
		return picInfos;
	}
	public void setPicInfos(List<TSmsPicInfoEntity> picInfos) {
		this.picInfos = picInfos;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	
}
