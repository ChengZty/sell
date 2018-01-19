package com.buss.news.service;
import com.buss.count.vo.GuideNewsTotalCountVo;
import com.buss.news.entity.TNewsTypeEntity;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.vo.CommonVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TNewsTypeServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TNewsTypeEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TNewsTypeEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TNewsTypeEntity t);

	/**更新资讯分类
	 * @param t
	 * @throws Exception 
	 */
	public void doCopy(TNewsTypeEntity t) throws Exception;

	/**批量保存资讯分类（复制资讯的时候保存）
	 * @param newsTypeSet
	 */
	public void batchSaveByCopyNews(Set<String> newsTypeSet);

	/**
	 * 清除品牌缓存 key:getAllNewType* 资讯分类的新增，修改，删除
	 */
	public void clearNewsTypeReids();

	/**获取最大的资讯分类code后+1
	 * @return
	 */
	public String getUniqueMaxCode();

	/**获取所有资讯分类code_retailerId和对应name的vo，零售商id是为了使vo中的id是唯一的，
	 * 因为不容零售商code可以相同（来源于资讯复制，或者分类复制）
	 * @return CommonVo
	 * vo的id格式:7_admin,9_admin
	 * vo的name格式:俪人佳选,母婴亲选
	 */
	public CommonVo getAllNewsTypeCodeAndNameVo();
	/**
	 * 获取资讯分类的名称
	 * @param newsTypeStr  资讯分类的code
	 * @param retailerId
	 * @return
	 */
	Map<String, String> getNewsTypeNames(String newsTypeStr, String retailerId);
	/**
	 * 获取资讯分类名称
	 * @param resultList
	 * @param retailerId
	 */
	void fillNewsTypeNames(List<GuideNewsTotalCountVo> resultList,
			String retailerId);
}
