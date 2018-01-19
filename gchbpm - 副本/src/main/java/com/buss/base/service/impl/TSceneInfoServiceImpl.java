package com.buss.base.service.impl;
import com.buss.base.service.TSceneInfoServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;

import com.buss.base.entity.TScenePicsEntity;
import com.buss.base.entity.TBrandShowEntity;
import com.buss.base.entity.TSceneInfoEntity;
import com.buss.base.entity.TScenePicsEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("tSceneInfoService")
@Transactional
public class TSceneInfoServiceImpl extends CommonServiceImpl implements TSceneInfoServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSceneInfoEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TSceneInfoEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TSceneInfoEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSceneInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSceneInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSceneInfoEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSceneInfoEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{title}",String.valueOf(t.getTitle()));
 		sql  = sql.replace("#{cover_pic}",String.valueOf(t.getCoverPic()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	@Override
 	public void saveScene(TSceneInfoEntity tSceneInfo) {
 		this.commonDao.save(tSceneInfo);
 		this.updateScenePics(tSceneInfo, "A");
 	}
 	
 	@Override
 	public void updateScene(TSceneInfoEntity tSceneInfo) {
 		this.commonDao.updateEntitie(tSceneInfo);
 		this.updateScenePics(tSceneInfo, "U");
 	}
 	
 	/**更新场景图片明细
 	 * @param tSceneInfo
 	 * @param optType U更新，A新增
 	 */
 	public void updateScenePics(TSceneInfoEntity tSceneInfo,String optType) {
 		List<TScenePicsEntity> oldDetailPics = null;
 		if("U".equals(optType)){
 			oldDetailPics = this.commonDao.findHql("from TScenePicsEntity where sceneId =? and status = 'A' order by orderNum asc", tSceneInfo.getId());
 		}
 		List<TScenePicsEntity> newDetailPics = tSceneInfo.getDetailPics();
 		if(Utility.isEmpty(oldDetailPics)){//以前没有黄点
 			if(!Utility.isEmpty(newDetailPics)){//新增
 	 			int n=newDetailPics.size();
 	 			for(int i=0;i<n;i++){
 	 				TScenePicsEntity entity = newDetailPics.get(i);
 	 				entity.setSceneId(tSceneInfo.getId());
 	 				entity.setOrderNum(i);
 	 				entity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 	 				this.commonDao.save(entity);
 	 			}
 	 		}
 		}else{//以前有黄点
 			if(Utility.isEmpty(newDetailPics)){
 				this.commonDao.updateBySqlString("update t_scene_pics set status = 'I',update_date = '"+Utility.getCurrentTimestamp()
 	 					+"' where scene_id ='"+tSceneInfo.getId()+"' ");
 			}else{
 				int n=newDetailPics.size();
 				for(int i=0;i<n;i++){
 					TScenePicsEntity entity = newDetailPics.get(i);
 					if(Utility.isEmpty(entity.getId())){//新增
 						entity.setSceneId(tSceneInfo.getId());
 						entity.setOrderNum(i);
 						entity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 						this.commonDao.save(entity);
 					}else{//修改
 						this.commonDao.updateBySqlString("update t_scene_pics set update_date = '"+Utility.getCurrentTimestamp()
 		 	 					+"',order_num ="+i+",pic_map_content = '"+entity.getPicMapContent()
 		 	 					+"' where id ='"+entity.getId()+"'");
 						for(TScenePicsEntity old : oldDetailPics){
 							if(entity.getId().equals(old.getId())){//把有的都移除，剩余的都要是删除的
 								oldDetailPics.remove(old);
 								break;
 							}
 						}
 					}
 				}
 				if(!Utility.isEmpty(oldDetailPics)){//删除
 					for(TScenePicsEntity entity : oldDetailPics){
 						this.commonDao.updateBySqlString("update t_scene_pics set status = 'I',update_date = '"+Utility.getCurrentTimestamp()
 	 					+"' where id ='"+entity.getId()+"'");
					}
 				}
 			}
 		}
 	}
 	
 	@Override
 	public void deleteScene(String id) {
 		this.commonDao.updateBySqlString("update t_scene_info set status = 'I',update_date = '"+Utility.getCurrentTimestamp()
					+"' where id ='"+id+"'");
 		this.commonDao.updateBySqlString("update t_scene_pics set status = 'I',update_date = '"+Utility.getCurrentTimestamp()
 				+"' where scene_id ='"+id+"'");
 	}
}