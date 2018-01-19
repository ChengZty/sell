package org.jeecgframework.web.system.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;

import com.buss.count.vo.GuideGoodsTotalCountVo;

public interface CategoryServiceI extends CommonService{
	/**
	 * 保存分类管理
	 * @param category
	 */
	void saveCategory(TSCategoryEntity category);

	/**
	 * 清除 商品分类 getGoodsCategory* 品牌商品getGoodsByBrand* 商品列表getGoodsByType*  redis缓存
	 */
	void clearCategoryRedis();
	/**
	 * 获取商品分类map对象
	 * @param categoryIdStr
	 * @return map对象<id,name>
	 */
	Map<String, String> getCategoryNameMap(String categoryIdStr);
	/**
	 * 分类名称填充
	 * @param list
	 */
	void fillCategoryName(List<GuideGoodsTotalCountVo> list);

}
