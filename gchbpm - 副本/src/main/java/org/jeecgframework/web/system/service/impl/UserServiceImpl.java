package org.jeecgframework.web.system.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.store.entity.TStoreAccountInfoEntity;
import com.buss.user.entity.TPersonEntity;
import common.GlobalConstants;

/**
 * 
 * @author  
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	public TSUser checkUserExits(TSUser user){
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}
	public String getUserRole(TSUser user){
		return this.commonDao.getUserRole(user);
	}
	
	public void pwdInit(TSUser user,String newPwd) {
			this.commonDao.pwdInit(user,newPwd);
	}
	
	public int getUsersOfThisRole(String id) {
		Criteria criteria = getSession().createCriteria(TSRoleUser.class);
		criteria.add(Restrictions.eq("TSRole.id", id));
		int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		return allCounts;
	}
	
	@Override
	/**
	 * 获取自动完成列表
	 * 
	 * @param <T>
	 * @return
	 */
	public  List getAutoList(Autocomplete autocomplete,String hqlCondition) {
		StringBuffer sb = new StringBuffer("");
		for (String searchField : autocomplete.getSearchField().split(",")) {
			sb.append("  or " + searchField + " like '%"
					+ autocomplete.getTrem() + "%' ");
		}
		String hql = "from " + autocomplete.getEntityName() + " where (1!=1 "
				+ sb.toString()+")";
		if(!StringUtil.isEmpty(hqlCondition)){
			hql +=hqlCondition;
		}
		return commonDao.getSession().createQuery(hql)
				.setFirstResult(autocomplete.getCurPage() - 1)
				.setMaxResults(autocomplete.getMaxRows()).list();
	}
	
	/**
	 * 判断该手机号码是否已注册
	 * @param phone  手机号码
	 * @return
	 */
	@Override
	public boolean isRegister(String phone,String userType){
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(1) from t_s_user where mobilePhone = '").append(phone).append("'")
		.append(" and user_type='").append(userType).append("'")
		.append(" and status='A'");
		long count = this.getCountForJdbc(sb.toString());
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断用户名是否已注册
	 * @param username  用户名
	 * @param userType  用户类型
	 * @return
	 */
	@Override
	public boolean validateUserName(String username,String userType){
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(1) from t_s_user where username = '").append(username).append("'")
		.append(" and user_type='").append(userType).append("'")
		.append(" and status='A'");
		long count = this.getCountForJdbc(sb.toString());
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@Override
	public void saveUserInfo(TSUser user){
		//保存账号信息
		user.setCreateDate(Utility.getCurrentTimestamp());
		user.setStatus(GlobalConstants.STATUS_ACTIVE);
		commonDao.save(user);
		//保存个人信息
		TPersonEntity person = new TPersonEntity();
		person.setUserId(user.getId());
		person.setUserType(user.getUserType());
		person.setStatus(GlobalConstants.STATUS_ACTIVE);
		person.setCreateDate(Utility.getCurrentTimestamp());
		commonDao.save(person);
	}
	
	
	@Override
	public void batchSaveActiveTime(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		String ids = request.getParameter("ids");
		String activeTime = request.getParameter("activeTime");
		if(Utility.isNotEmpty(ids)&&Utility.isNotEmpty(activeTime)){
			StringBuffer sql = new StringBuffer("update t_s_user set active_time = '").append(activeTime).append(" 00:00:00',")
				.append("update_date = '").append(Utility.getCurrentTimestamp()).append("',update_by = '").append(user.getUserName())
				.append("' where status = 'A' and user_status in ('").append(TSUser.USER_STATUS_ACTIVE).append("','").append(TSUser.USER_STATUS_FIXED)
				.append("') and id in (")
			;
			for(String id : ids.split(",")){
				sql.append("'").append(id).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1).append(") or ( retailer_id in (");
			for(String id : ids.split(",")){
				sql.append("'").append(id).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1).append(") and user_type <>'04' )");
			this.commonDao.updateBySqlString(sql.toString());
		}
	}
	
	@Override
	public void batchSaveEffectiveTime(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		String ids = request.getParameter("ids");
		String effectiveTime = request.getParameter("effectiveTime");
		if(Utility.isNotEmpty(ids)&&Utility.isNotEmpty(effectiveTime)){
			StringBuffer sql = new StringBuffer("update t_s_user set effective_time = '").append(effectiveTime).append("',")
				.append("update_date = '").append(Utility.getCurrentTimestamp()).append("',update_by = '").append(user.getUserName())
				.append("' where status = 'A' and id in (")
			;
			for(String id : ids.split(",")){
				sql.append("'").append(id).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1).append(") ");
			this.commonDao.updateBySqlString(sql.toString());
		}
		
	}
	
	
	
	
	
	
	
}
