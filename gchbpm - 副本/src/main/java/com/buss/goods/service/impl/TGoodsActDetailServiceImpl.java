package com.buss.goods.service.impl;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.goods.entity.TGoodsActDetailEntity;
import com.buss.goods.entity.TGoodsActEntity;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.goods.service.TGoodsActDetailServiceI;
import com.buss.goods.vo.ActPriceImportVo;

import common.DBUtil;
import common.GlobalConstants;

@Service("tGoodsActDetailService")
@Transactional
public class TGoodsActDetailServiceImpl extends CommonServiceImpl implements TGoodsActDetailServiceI {
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> batchSaveActPrice(String goodsActId, List<ActPriceImportVo> actPriceImportVoList,
			String retailerId) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		//校验并返回错误信息或者商品对应的list
		Map<String, Object> map2 = this.checkData(actPriceImportVoList, retailerId,goodsActId);
		List<ActPriceImportVo> failList = (List<ActPriceImportVo>) map2.get("failList");
		List<TGoodsActDetailEntity> successList = (List<TGoodsActDetailEntity>) map2.get("successList");
		int n=0;//成功插入的记录数
		if(successList!=null&&successList.size()>0){
			n = this.batchSaveDetail(successList,goodsActId);
		}
		map.put("successCount", n);//成功记录数
		map.put("failList", failList);
		successList = null;
		return map;
	}
	
	/**jdbc批量保存明细,返回成功插入的记录条数*/
	private int batchSaveDetail(List<TGoodsActDetailEntity> successList,String goodsActId) throws SQLException {
		int n = 0;
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();//数据list
		TSUser user = ResourceUtil.getSessionUserName();
		Date createDate = Utility.getCurrentTimestamp();
		for(TGoodsActDetailEntity detail : successList){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", Utility.getUUID());
			map.put("create_name",user.getRealName());
			map.put("create_by",user.getUserName());
			map.put("create_date",createDate);
			map.put("update_date",createDate);
			map.put("status",GlobalConstants.STATUS_ACTIVE);
			map.put("goods_act_id", goodsActId);
			map.put("goods_code", detail.getGoodsCode());
			map.put("goods_id", detail.getGoodsId());
			map.put("goods_name", detail.getGoodsName());
			map.put("small_pic", detail.getSmallPic());
			map.put("original_price", detail.getOriginalPrice());
			map.put("lowest_price_discount", detail.getLowestPriceDiscount());
			map.put("lowest_price", detail.getLowestPrice());
			map.put("act_price", detail.getActPrice());
			map.put("discount", detail.getDiscount());
			datas.add(map);
		}
		if(Utility.isNotEmpty(datas)){
			Map<String,Object> resultMap= DBUtil.insertAll("t_goods_act_detail", datas);
			n = (Integer) resultMap.get("affectRowCount");
		}
		return n;
	}

	/**校验并返回错误list和校验通过的的list
	 * @param actPriceImportVoList
	 * @param retailerId
	 * @return successList，failList
	 */
	private Map<String,Object> checkData(List<ActPriceImportVo> actPriceImportVoList,String retailerId,String goodsActId) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<ActPriceImportVo> tempList = new ArrayList<ActPriceImportVo>();//导入列表无问题临时数据，还未和数据库数据对比校验
		List<ActPriceImportVo> failList = new ArrayList<ActPriceImportVo>();//导入列表有问题的数据
		List<TGoodsActDetailEntity> successList =  new ArrayList<TGoodsActDetailEntity>();//和数据库款号匹配上的数据
		Set<String> goodsCodeSet  = new HashSet<String>();
		for(ActPriceImportVo vo : actPriceImportVoList){
			if(goodsCodeSet.contains(vo.getGoodsCode())){
				vo.setErrTip("款号 "+vo.getGoodsCode()+" 重复");
				failList.add(vo);
				break;
			}else{
				goodsCodeSet.add(vo.getGoodsCode());
				tempList.add(vo);
			}
		}
		if(Utility.isEmpty(failList)){
			//当前活动已存在的明细列表,校验是否重复
			List<TGoodsActDetailEntity> existList = this.commonDao.findByProperty(TGoodsActDetailEntity.class, "goodsActId", goodsActId);
			if(existList.size()>0){
				for(int i=0;i<tempList.size();i++){
					ActPriceImportVo vo = tempList.get(i);
//				boolean exist = false;
					for(TGoodsActDetailEntity detail : existList){
						//明细中已经导入过该款号，临时列表中移除该vo，并加入到错误列表中
						if(vo.getGoodsCode().equals(detail.getGoodsCode())){
							tempList.remove(vo);
							i--;
							vo.setErrTip("列表中已存在款号 "+vo.getGoodsCode());
							failList.add(vo);
//						exist = true;
							break;
						}
					}
				}
			}
			//获取活动时间冲突的商品列表
			TGoodsActEntity tGoodsAct = this.commonDao.get(TGoodsActEntity.class, goodsActId);
			if(Utility.isEmpty(failList)&&tGoodsAct!=null){//修改，此时需要校验是导入的商品是否和别的活动商品有冲突
				//获取其他活动和当前活动冲突的商品列表
				List<TGoodsActDetailEntity> list = this.getOtherActDetailList(tGoodsAct, retailerId);
				if(list.size()>0){
					for(int i=0;i<tempList.size();i++){
						ActPriceImportVo vo = tempList.get(i);
						boolean exist = false;
						for(TGoodsActDetailEntity detail : list){
							if(vo.getGoodsCode().equals(detail.getGoodsCode())){
								tempList.remove(vo);
								i--;
								exist = true;
								break;
							}
						}
						if(exist){//存在冲突的商品
							vo.setErrTip("商品活动时间冲突 ");
							failList.add(vo);
						}
					}
				}
			}
			
			//还有待校验的临时数据，校验系统中是否有该款号
			if(Utility.isEmpty(failList)&&tempList.size()>0){
				StringBuffer sql = new StringBuffer();
				sql.append("select id goodsId,goods_code goodsCode,goods_name goodsName,small_pic smallPic,original_price originalPrice,lowest_price_discount lowestPriceDiscount,lowest_price lowestPrice")
				.append(" from t_goods where status ='A' and retailer_id = '").append(retailerId).append("' and goods_status = '").append(TGoodsEntity.GOODS_STATUS_4)
				.append("' and id not in (").append("select goods_id from t_goods_act_detail where status = 'A' and goods_act_id ='").append(goodsActId).append("')");
				
				List<TGoodsActDetailEntity> allList = this.commonDao.findObjForJdbc(sql.toString(), TGoodsActDetailEntity.class);
				if(allList.size()>0){
					for(ActPriceImportVo vo : tempList){
						boolean exist = false;
						for(TGoodsActDetailEntity detail : allList){
							if(vo.getGoodsCode().equals(detail.getGoodsCode())){
								if(Utility.isNotEmpty(vo.getActPrice())){//优先去价格
									detail.setActPrice(vo.getActPrice());
									detail.setDiscount(vo.getActPrice().divide(detail.getOriginalPrice(), 2, BigDecimal.ROUND_HALF_UP));
								}else{
									detail.setDiscount(vo.getDiscount());
									detail.setActPrice(vo.getDiscount().multiply(detail.getOriginalPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
								}
								successList.add(detail);
								exist = true;
								break;
							}
						}
						if(!exist){//系统不存在
							vo.setErrTip("系统不存在款号 "+vo.getGoodsCode());
							failList.add(vo);
						}
					}
				}else{
					failList.addAll(tempList);
				}
			}
			map.put("successList", successList);
		}
		map.put("failList", failList);
		actPriceImportVoList = null;
		tempList = null;
		return map;
	}
	
	/**获取其他活动和当前活动冲突的商品列表*/
	@Override
	public List<TGoodsActDetailEntity> getOtherActDetailList(TGoodsActEntity tGoodsAct,String retailerId){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.goods_id goodsId,t.goods_code goodsCode,t.act_price actPrice from t_goods_act_detail t ")
		.append(" where t.goods_id  in (")
		.append("SELECT DISTINCT(d.goods_id) from t_goods_act a LEFT JOIN t_goods_act_detail d on a.id = d.goods_act_id")
		.append(" where a.status='A'  and d.status='A' and a.valid ='").append(TGoodsActEntity.VALID_Y)
		.append("' AND a.id <> '").append(tGoodsAct.getId()).append("' and a.retailer_id='")
		.append(retailerId).append("' and ((a.begin_time BETWEEN '").append(DateUtil.dateToStringWithTime(tGoodsAct.getBeginTime()))
		.append("' AND '").append(DateUtil.dateToStringWithTime(tGoodsAct.getEndTime())).append("') OR ")
		.append("(a.end_time BETWEEN '").append(DateUtil.dateToStringWithTime(tGoodsAct.getBeginTime()))
		.append("' AND '").append(DateUtil.dateToStringWithTime(tGoodsAct.getEndTime())).append("') OR")
		.append("(a.begin_time < '").append(DateUtil.dateToStringWithTime(tGoodsAct.getBeginTime()))
		.append("' AND a.end_time > '").append(DateUtil.dateToStringWithTime(tGoodsAct.getEndTime())).append("')")
		.append(")) and t.goods_act_id <> '").append(tGoodsAct.getId()).append("' and t.`status` = 'A'");
		//获取活动时间冲突的商品列表
		List<TGoodsActDetailEntity> list = this.commonDao.findObjForJdbc(sql.toString(), TGoodsActDetailEntity.class);
		return list;
	}
}