package com.buss.cms.service.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.redis.service.RedisService;

import com.buss.cms.entity.TContentCategoryEntity;
import com.buss.cms.entity.TContentCustomIndexEntity;
import com.buss.cms.entity.TGuideMainElementEntity;
import com.buss.cms.service.TContentCustomIndexServiceI;
import com.buss.words.entity.TCustWordsEntity;

@Service("tContentCustomIndexService")
@Transactional
public class TContentCustomIndexServiceImpl extends CommonServiceImpl implements TContentCustomIndexServiceI {
	@Autowired
	private RedisService redisService;
	@Autowired
	private SystemService systemService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TContentCustomIndexEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TContentCustomIndexEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TContentCustomIndexEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TContentCustomIndexEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TContentCustomIndexEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TContentCustomIndexEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TContentCustomIndexEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{content_id}",String.valueOf(t.getContentId()));
 		sql  = sql.replace("#{sort_order}",String.valueOf(t.getSortOrder()));
 		sql  = sql.replace("#{is_show}",String.valueOf(t.getIsShow()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
 	 * 添加零售商内容分类
 	 */
	@Override
	public void insterCmsItems(String retailerId) {
		List<TContentCategoryEntity>  cmslist   =  this.commonDao.findByQueryString(" from TContentCategoryEntity where status='A'  and is_parent=false");
		if(!Utility.isEmpty(cmslist)){
			List<TContentCustomIndexEntity> indexList = this.commonDao.findByProperty(TContentCustomIndexEntity.class,"retailerId", retailerId);
			List<TContentCustomIndexEntity>  newList = new ArrayList<TContentCustomIndexEntity>();
			if(Utility.isEmpty(indexList)){
				for (TContentCategoryEntity obj : cmslist) {
					createItem(retailerId, newList, obj);
				}
		 	}else{
		 		for (TContentCategoryEntity obj : cmslist) {
		 			boolean isexist = false;
		 			for (TContentCustomIndexEntity item : indexList) {
						if(item.getContentId() == obj.getId()){
							isexist= true;
							break;
						}
		 			}
		 			if(!isexist){
		 				createItem(retailerId, newList, obj);
		 			}
		 		}
		 	}
		 	if(!Utility.isEmpty(newList)){
		 		this.commonDao.batchSave(newList);
		 	}
		}
	}
	/**
	 * 数据转换
	 * @param retailerId
	 * @param newList
	 * @param obj
	 */
	private void createItem(String retailerId,
			List<TContentCustomIndexEntity> newList, TContentCategoryEntity obj) {
		TContentCustomIndexEntity entity = new TContentCustomIndexEntity();
		entity.setId(null);
		entity.setContentId(obj.getId());
		entity.setIsShow(true);
		entity.setRetailerId(retailerId);
		entity.setSortOrder(obj.getSortOrder());
		newList.add(entity);
	}
	
	/**
	 * 清除 APP首页缓存 getHomePage* redis缓存
	 */
	@Override
	public void clearHomePageRedis() {
		try {
			redisService.batchDel("getHomePage*"+ResourceUtil.getRetailerId()+"*");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}
	}
	
	

	//获取导购主页元素信息
	public void getGuideMainElementDatagrid(HttpServletRequest request, DataGrid dataGrid){
		//修改每页查询15条数据
		dataGrid.setRows(15);
		//零售商ID
		String retailerId = ResourceUtil.getRetailerId();
		StringBuffer sql = getBeanSql();

		sql.append("(select * from t_guide_main_element where status='A' and retailer_id = 'admin' ) at ")
		.append("left join ")
		.append("(select * from t_guide_main_element where status='A' ");
		
		if(Utility.isNotEmpty(retailerId)){//零售商查询
			sql.append("and retailer_id='"+retailerId+"'");
		}else{
			sql.append(" and retailer_id='retailer_id' ");
		}
		sql.append(") rt on at.element_code = rt.element_code order by at.group_code desc,orderNum asc");
		
		List<TGuideMainElementEntity> list = systemService.findObjForJdbc(sql.toString(), dataGrid.getPage(),dataGrid.getRows(), TGuideMainElementEntity.class);
		for (TGuideMainElementEntity tGuideMainElementEntity : list) {
			//如果存在自定义id则替换新参数id
			if(Utility.isNotEmpty(tGuideMainElementEntity.getCustomId())){
				tGuideMainElementEntity.setId(tGuideMainElementEntity.getCustomId());
			}
		}
		
		dataGrid.setResults(list);
		dataGrid.setTotal(list.size());
	}
	
	//更新导购主页元素信息
	public void guideMaimElement(HttpServletRequest request){
		TGuideMainElementEntity guideMainElement = systemService.flushEntity(TGuideMainElementEntity.class, request.getParameter("id"));
		//零售商ID
		String retailerId = ResourceUtil.getRetailerId();
		String userId = guideMainElement.getRetailerId();
		String orderNum = request.getParameter("orderNum");
		String pic = request.getParameter("pic");
		if(Utility.isNotEmpty(retailerId)){//零售商更新
			if(retailerId.equals(userId)){//自己更新
				if(Utility.isNotEmpty(orderNum)){
					guideMainElement.setOrderNum(orderNum);
				}
				if(Utility.isNotEmpty(pic)){
					guideMainElement.setPic(pic);
				}
				systemService.updateEntitie(guideMainElement);
			}else{//使用平台的创建
				TGuideMainElementEntity tGuideMainElement = new TGuideMainElementEntity();
				tGuideMainElement.setStatus("A");
				tGuideMainElement.setGroupCode(guideMainElement.getGroupCode());
				tGuideMainElement.setElementTitle(guideMainElement.getElementTitle());
				tGuideMainElement.setElementCode(guideMainElement.getElementCode());
				tGuideMainElement.setPic(request.getParameter("pic"));
				tGuideMainElement.setRetailerId(retailerId);
				tGuideMainElement.setOrderNum(guideMainElement.getOrderNum());
				systemService.save(tGuideMainElement);
			}
		}else{//g+更新
			if(Utility.isNotEmpty(orderNum)){
				guideMainElement.setOrderNum(orderNum);
			}
			if(Utility.isNotEmpty(pic)){
				guideMainElement.setPic(pic);
			}
			systemService.updateEntitie(guideMainElement);
		}
		
	}
	
	private StringBuffer getBeanSql(){
		StringBuffer sb = new StringBuffer();
		sb.append("select at.id id, ")
		.append("at.create_name createName, ")
		.append("at.create_by createBy, ")
		.append("at.create_date createDate, ")
		.append("at.update_name updateName, ")
		.append("at.update_by updateBy, ")
		.append("at.update_date updateDate, ")
		.append("at.status status, ")
		.append("at.group_code groupCode, ")
		.append("at.element_title elementTitle, ")
		.append("at.element_code elementCode, ")
		.append("at.pic pic, ")
		.append("rt.id customId, ")
		.append("rt.pic customPic, ")
		.append("ifnull(rt.order_num,at.order_num) orderNum, ")
		.append("at.retailer_id retailerId ")
		.append("from ");
		return sb;
	}
}