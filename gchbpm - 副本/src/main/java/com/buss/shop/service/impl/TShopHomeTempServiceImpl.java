package com.buss.shop.service.impl;
import com.buss.shop.service.TShopHomeTempServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;

import com.buss.shop.entity.TShopHomeTempEntity;
import com.buss.shop.entity.TShopHomeTempHisEntity;
import common.GlobalConstants;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ResourceBundle;
import java.util.UUID;
import java.io.Serializable;

@Service("tShopHomeTempService")
@Transactional
public class TShopHomeTempServiceImpl extends CommonServiceImpl implements TShopHomeTempServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TShopHomeTempEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TShopHomeTempEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TShopHomeTempEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TShopHomeTempEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TShopHomeTempEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TShopHomeTempEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TShopHomeTempEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{pub_status}",String.valueOf(t.getPubStatus()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	/**
 	 * 更新已完成的首页模板
 	 * @param tShopHomeTemp
 	 * @param retailerId
 	 * @throws Exception
 	 */
 	@Override
 	public void updateFinish(TShopHomeTempEntity tShopHomeTemp,
			String retailerId) throws Exception {
		if(Utility.isNotEmpty(tShopHomeTemp.getId())){
			TShopHomeTempEntity t = commonDao.get(TShopHomeTempEntity.class, tShopHomeTemp.getId());
			MyBeanUtils.copyBeanNotNull2Bean(tShopHomeTemp, t);
			t.setPubStatus(TShopHomeTempEntity.PUB_STATUS_2);//已完成
			commonDao.updateEntitie(t);
			
//			先删除之前的,再保存历史
			updateShopHomeTemp(tShopHomeTemp, retailerId);
		}else{
			tShopHomeTemp.setRetailerId(retailerId);
			tShopHomeTemp.setStatus(GlobalConstants.STATUS_ACTIVE);
			tShopHomeTemp.setPubStatus(TShopHomeTempEntity.PUB_STATUS_2);//已完成
			if(Utility.isEmpty(tShopHomeTemp.getId())){
				tShopHomeTemp.setCreateDate(Utility.getCurrentTimestamp());
			}
			commonDao.save(tShopHomeTemp);
//			先删除之前的,再保存历史
			updateShopHomeTemp(tShopHomeTemp, retailerId);
		}
	}

	private void updateShopHomeTemp(TShopHomeTempEntity tShopHomeTemp,
			String retailerId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" update t_shop_home_temp_his set status='I' where retailer_id='").append(retailerId).append("'");
		commonDao.updateBySqlString(sb.toString());
		
		ResourceBundle env = ResourceBundle.getBundle("env");
		String url = env.getObject("CST_REQUEST_PRE_URL")+"tShopHomeTempController.do?viewShopHomePage&rId="+ResourceUtil.getRetailerId();
		TShopHomeTempHisEntity his = new TShopHomeTempHisEntity();
		his.setContent(tShopHomeTemp.getContent());
		his.setRetailerId(retailerId);
		his.setUrl(url);//手机端访问首页地址
		his.setCreateDate(Utility.getCurrentTimestamp());
		his.setStatus(GlobalConstants.STATUS_ACTIVE);
		commonDao.save(his);
	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
}