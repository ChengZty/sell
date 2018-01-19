package org.jeecgframework.web.system.service.impl;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.pojo.base.TStoreSNoticeAuthorityUser;
import org.jeecgframework.web.system.service.NoticeStoreAuthorityUserServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("noticeStoreAuthorityUserService")
@Transactional
public class NoticeStoreAuthorityUserServiceImpl extends CommonServiceImpl implements NoticeStoreAuthorityUserServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TStoreSNoticeAuthorityUser)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TStoreSNoticeAuthorityUser)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TStoreSNoticeAuthorityUser)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TStoreSNoticeAuthorityUser t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TStoreSNoticeAuthorityUser t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TStoreSNoticeAuthorityUser t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TStoreSNoticeAuthorityUser t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{notice_id}",String.valueOf(t.getNoticeId()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUser().getId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
 	 * 检查通知公告授权用户是否存在
 	 */
 	public boolean checkAuthorityUser(String noticeId, String userid) {
		CriteriaQuery cq = new CriteriaQuery(TStoreSNoticeAuthorityUser.class);
		cq.eq("user.id", userid);
		cq.eq("noticeId", noticeId);
		cq.add();
		List<TStoreSNoticeAuthorityUser> rlist =   this.getListByCriteriaQuery(cq, false);
		if(rlist.size()==0){
			return false;
		}else{
			return true;
		}
	}
 	
 	/**
 	 * 根据消息ID 查询用户ID列表
 	 * @param id
 	 * @return
 	 */
	@Override
	public List<String> findListByNoticeId(String id) {
		String sql = "select user_id  from t_s_notice_store_authority_user where notice_id='"+id+"'";
		return this.commonDao.findListbySql(sql);
	}
}