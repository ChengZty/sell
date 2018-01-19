package com.buss.user.service.impl;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.user.entity.TShopEntity;
import com.buss.user.service.TShopServiceI;

@Service("tShopService")
@Transactional
public class TShopServiceImpl extends CommonServiceImpl implements TShopServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TShopEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TShopEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TShopEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TShopEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TShopEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TShopEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TShopEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{code}",String.valueOf(t.getCode()));
 		sql  = sql.replace("#{shop_name}",String.valueOf(t.getShopName()));
 		sql  = sql.replace("#{province_id}",String.valueOf(t.getProvinceId()));
 		sql  = sql.replace("#{city_id}",String.valueOf(t.getCityId()));
 		sql  = sql.replace("#{area}",String.valueOf(t.getArea()));
 		sql  = sql.replace("#{detail_address}",String.valueOf(t.getDetailAddress()));
 		sql  = sql.replace("#{phone_no}",String.valueOf(t.getPhoneNo()));
 		sql  = sql.replace("#{shop_level}",String.valueOf(t.getShopLevel()));
 		sql  = sql.replace("#{valid_period}",String.valueOf(t.getValidPeriod()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	@Override
 	public void saveShopAndCreateUser(TShopEntity tShop, HttpServletRequest request) {
 		List<TSUser> list = commonDao.findHql("from TSUser where userName = ? and userType = ? ", tShop.getCode(),TSUser.USER_TYPE_02);
 		if(list.size()>0){//已经有零售商帐号，则不需要再生成，更新零售商ID到店铺
 			TSUser user= list.get(0);
 			LogUtil.info("零售商帐号："+tShop.getCode()+"已存在");
 			tShop.setRetailerId(user.getId());
 		}else{//生成零售商帐号
 			LogUtil.info("零售商帐号："+tShop.getCode()+"不存在，新增管理员帐号");
 			String userCode = request.getParameter("userCode");
 			TSUser user = new TSUser();
 			BeanUtils.copyProperties(tShop, user);
 			user.setId(null);
 			user.setUserName(tShop.getCode());
 			user.setUserCode(userCode);
 			user.setPassword(PasswordUtil.encrypt(user.getUserName(), "123456", PasswordUtil.getStaticSalt()));
 			user.setRealName(tShop.getShopName());
 			user.setMobilePhone(tShop.getPhoneNo());
 			String roleid = "2c92808654af399f0154b3772667015a";//角色为零售商roleId
 			user.setUserType(TSUser.USER_TYPE_02);//零售商
 			user.setRetailerType(TSUser.RETAILER_TYPE_REAL);//人货
 			user.setUserStatus(Globals.User_Normal);
 			user.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 			commonDao.save(user);
 			tShop.setRetailerId(user.getId());
 			if (StringUtil.isNotEmpty(roleid)) {
 				//角色
 				TSRoleUser rUser = new TSRoleUser();
 				TSRole role = commonDao.get(TSRole.class, roleid);
 				rUser.setTSRole(role);
 				rUser.setTSUser(user);
 				commonDao.save(rUser);
 			}
 		}
 		//保存店铺
 		commonDao.save(tShop);
 		
 	}
}