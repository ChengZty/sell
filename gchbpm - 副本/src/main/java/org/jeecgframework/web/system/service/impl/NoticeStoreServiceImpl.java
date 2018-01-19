package org.jeecgframework.web.system.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TStoreSNotice;
import org.jeecgframework.web.system.pojo.base.TStoreSNoticeAuthorityUser;
import org.jeecgframework.web.system.pojo.base.TStoreSNoticeReadUser;
import org.jeecgframework.web.system.service.NoticeStoreService;
import org.springframework.stereotype.Service;

/**
 * 通知公告Service接口实现类
 * @author  alax
 *
 */
@Service("noticeStoreService")
public class NoticeStoreServiceImpl extends CommonServiceImpl implements NoticeStoreService{
	
	/**
	 * 
	 * @param noticeTilte 标题
	 * @param noticeContent 内容
	 * @param noticeType 类型
	 * @param noticeLevel 级别
	 * @param noticeTerm 期限
	 * @param createUser 创建者
	 * @return
	 */
	public String addNotice(String noticeTitle, String noticeContent,
			String noticeType, String noticeLevel, Date noticeTerm,
			String createUser) {
		String noticeId=null;
		TStoreSNotice notice = new TStoreSNotice();
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeContent(noticeContent);
		notice.setNoticeLevel(noticeLevel);
		notice.setCreateTime(new Date());
		this.save(notice);
		noticeId = notice.getId();
		return noticeId;
	}

    /**
     * 追加通告授权用户
     */
	public void addNoticeAuthorityUser(String noticeId, String userid) {
		if(noticeId != null && userid!=null){
			TStoreSNoticeAuthorityUser entity = new  TStoreSNoticeAuthorityUser();
			entity.setNoticeId(noticeId);
			TSUser tsuser = new TSUser();
			tsuser.setId(userid);
			entity.setUser(tsuser);
			this.saveOrUpdate(entity);
		}
	}
	
	public <T> void delete(T entity) {
		TStoreSNotice notice = (TStoreSNotice)entity;
		super.deleteAllEntitie(super.findByProperty(TStoreSNoticeReadUser.class, "noticeId", notice.getId()));
		super.deleteAllEntitie(super.findByProperty(TStoreSNoticeAuthorityUser.class, "noticeId", notice.getId()));
		super.delete(notice);
 		//执行删除操作配置的sql增强
		this.doDelSql(notice);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TStoreSNotice)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TStoreSNotice)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TStoreSNotice t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TStoreSNotice t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TStoreSNotice t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TStoreSNotice t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{notice_title}",String.valueOf(t.getNoticeTitle()));
 		sql  = sql.replace("#{notice_content}",String.valueOf(t.getNoticeContent()));
 		sql  = sql.replace("#{notice_level}",String.valueOf(t.getNoticeLevel()));
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}
