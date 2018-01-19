package com.buss.user.service;
import com.buss.user.entity.TVipLevelEntity;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.vo.CommonVo;

import java.io.Serializable;
import java.util.List;

public interface TVipLevelServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TVipLevelEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TVipLevelEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TVipLevelEntity t);

	/**获取零售商顾客VIP等级列表（id，vipName）
	 * @return
	 */
	List<CommonVo> getLvlList();
}
