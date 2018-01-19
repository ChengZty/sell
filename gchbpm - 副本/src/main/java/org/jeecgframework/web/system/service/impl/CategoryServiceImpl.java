package org.jeecgframework.web.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.core.util.YouBianCodeUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.service.CategoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.redis.service.RedisService;

import com.buss.count.vo.GuideGoodsTotalCountVo;

@Service("tSCategoryService")
@Transactional
public class CategoryServiceImpl extends CommonServiceImpl implements	CategoryServiceI {
	@Autowired
	private RedisService redisService;

//	private static final String MAX_SQL = "SELECT MAX(code) FROM t_s_category WHERE parent_code";

	@Override
	public void saveCategory(TSCategoryEntity category) {
		String parentCode = null;
		if(category.getParent()!=null&&oConvertUtils.isNotEmpty(category.getParent().getCode())){
			parentCode = category.getParent().getCode();
			String localMaxCode  = getMaxLocalCode(parentCode);
			category.setCode(YouBianCodeUtil.getSubYouBianCode(parentCode, localMaxCode));
			category.setParentCode(parentCode);
		}else{
			String localMaxCode  = getMaxLocalCode(null);
			category.setParent(null);
			category.setCode(YouBianCodeUtil.getNextYouBianCode(localMaxCode));
		}
		String retailerId = ResourceUtil.getRetailerId();
		retailerId = Utility.isEmpty(retailerId)?"admin":retailerId;
		category.setRetailerId(retailerId);
		this.save(category);
		this.clearCategoryRedis();
	}
	
	private synchronized String getMaxLocalCode(String parentCode){
		if(oConvertUtils.isEmpty(parentCode)){
			parentCode = "";
		}
		int localCodeLength = parentCode.length() + YouBianCodeUtil.zhanweiLength;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT code FROM t_s_category");
		sb.append(" where LENGTH(code) = ").append(localCodeLength);
		if(oConvertUtils.isNotEmpty(parentCode)){
			sb.append(" and  code like '").append(parentCode).append("%'");
		}
		//update-begin-Alex 20160310 for:去除LIMIT,解决数据库兼容性问题
		sb.append(" ORDER BY code DESC ");
		List<Map<String, Object>> objMapList = this.findForJdbc(sb.toString(), 1, 1);
		String returnCode = null;
		if(objMapList!=null&& objMapList.size()>0){
			returnCode = (String)objMapList.get(0).get("code");
		}
		//update-end-Alex 20160310 for:去除LIMIT,解决数据库兼容性问题
		return returnCode;
	}

	

	
//	/**
//	 * 获取类型编码 加锁防止并发问题
//	 * 
//	 * @param category
//	 * @return
//	 */
//	private synchronized String getCategoryCoade(TSCategoryEntity category) {
//		Long maxCode = null;
//		//step 1 顶级code只按照序列增长
//		if (category.getParent() == null
//				|| StringUtils.isEmpty(category.getParent().getCode())) {
//			category.setParent(null);
//			maxCode = this.getCountForJdbc(MAX_SQL + " IS NULL");
//			maxCode = maxCode == 0 ? 1 : maxCode + 1;
//			return String.format(
//					"%0"
//							+ Integer.valueOf(ResourceUtil
//									.getConfigByName("categoryCodeLengthType"))
//							+ "d", maxCode);
//		}
//		//step 2按照下级序列向上排序
//		TSCategoryEntity parent = this.findUniqueByProperty(TSCategoryEntity.class,"code", category
//				.getParent().getCode());
//		//防止hibernate缓存持久化异常
//		category.setParent(parent);
//		maxCode = this.getCountForJdbc(MAX_SQL + " = '"
//				+ category.getParent().getCode()+"' and code like '"+ category.getParent().getCode()+"%'");
//		maxCode = maxCode == 0 ? 1 : Long.valueOf(maxCode.toString()
//				.substring(parent.getCode().length())) + 1;
//		return parent.getCode()
//				+ String.format(
//						"%0"
//								+ Integer.valueOf(ResourceUtil
//										.getConfigByName("categoryCodeLengthType"))
//								+ "d", maxCode);
//	}
	
	/**
	 * 清除 商品分类 getGoodsCategory* 品牌商品getGoodsByBrand* 商品列表getGoodsByType*  redis缓存
	 */
	@Override
	public void clearCategoryRedis() {
		try {
			redisService.batchDel("getGoodsCategory*"+ResourceUtil.getRetailerId()+"*");
//			redisService.batchDel("getGoodsByBrand*");
			redisService.batchDel("getGoodsByBrand*"+ResourceUtil.getRetailerId()+"*");
			redisService.batchDel("getGoodsByType*"+ResourceUtil.getRetailerId()+"*");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}
	}
	
	/**
	 * 获取商品分类map对象
	 * @param categoryIdStr
	 * @return map对象<id,name>
	 */
	@Override
	public Map<String,String> getCategoryNameMap(String categoryIdStr){
		Map<String,String> returnMap = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select id,name from t_s_category where status='A' ")
		.append(" and id in(").append(categoryIdStr).append(")");
		List<Map<String,Object>> list = this.findForJdbc(sb.toString());
		if(Utility.isNotEmpty(list)){
			for (Map<String, Object> obj : list) {
				if(Utility.isNotEmpty(obj)){
					String id = obj.get("id")+"";
					String name = obj.get("name")+"";
					if(Utility.isNotEmpty(id) && Utility.isNotEmpty(name)){
						returnMap.put(id, name);
					}
				}
			}
		}
		return returnMap;
	}
	/**
	 * 分类名称填充
	 * @param list
	 */
	@Override
	public void fillCategoryName(List<GuideGoodsTotalCountVo> list){
		if(Utility.isNotEmpty(list)){
			StringBuilder sb = new StringBuilder();
			for (GuideGoodsTotalCountVo vo : list) {
				if(Utility.isNotEmpty(vo.getTopCategoryId())){
					sb.append(",'").append(vo.getTopCategoryId()).append("'");
				}
				if(Utility.isNotEmpty(vo.getSubCategoryId())){
					sb.append(",'").append(vo.getSubCategoryId()).append("'");
				}
				if(Utility.isNotEmpty(vo.getThridCategoryId())){
					sb.append(",'").append(vo.getThridCategoryId()).append("'");
				}
			}
			if(Utility.isNotEmpty(sb.toString())){
				sb.deleteCharAt(0);
				Map<String,String> categroyMap = this.getCategoryNameMap(sb.toString());
				if(Utility.isNotEmpty(categroyMap)){
					for (GuideGoodsTotalCountVo vo : list) {
						if(Utility.isNotEmpty(vo.getTopCategoryId()) 
								&& Utility.isNotEmpty(categroyMap.get(vo.getTopCategoryId()))){
							vo.setTopCategoryId(categroyMap.get(vo.getTopCategoryId()));
						}
						if(Utility.isNotEmpty(vo.getSubCategoryId()) 
								&& Utility.isNotEmpty(categroyMap.get(vo.getSubCategoryId()))){
							vo.setSubCategoryId(categroyMap.get(vo.getSubCategoryId()));
						}
						if(Utility.isNotEmpty(vo.getThridCategoryId()) 
								&& Utility.isNotEmpty(categroyMap.get(vo.getThridCategoryId()))){
							vo.setThridCategoryId(categroyMap.get(vo.getThridCategoryId()));
						}
					}	
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}