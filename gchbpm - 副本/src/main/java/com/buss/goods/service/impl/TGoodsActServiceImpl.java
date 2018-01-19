package com.buss.goods.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.activity.entity.TGoodsActWordsEntity;
import com.buss.goods.entity.TGoodsActDetailEntity;
import com.buss.goods.entity.TGoodsActEntity;
import com.buss.goods.entity.TGoodsActNewsEntity;
import com.buss.goods.entity.TGoodsActStoreEntity;
import com.buss.goods.service.TGoodsActDetailServiceI;
import com.buss.goods.service.TGoodsActNewsServiceI;
import com.buss.goods.service.TGoodsActServiceI;
import com.buss.goods.vo.ActPriceImportVo;

import cn.redis.service.RedisService;

@Service("tGoodsActService")
@Transactional
public class TGoodsActServiceImpl extends CommonServiceImpl implements TGoodsActServiceI {
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private TGoodsActNewsServiceI tGoodsActNewsService;
	
	@Autowired
	private SystemService systemService; 
	
	@Autowired
	private TGoodsActDetailServiceI tGoodsActDetailService ;
	
	@Override
	public List<ActPriceImportVo> doCheckAndDel(TGoodsActEntity tGoodsAct,List<TGoodsActDetailEntity> otherActDetailList) {
		// 需要导出的冲突的活动商品list
		List<ActPriceImportVo> errList = new ArrayList<ActPriceImportVo>();
		if(otherActDetailList.size()>0){
			List<TGoodsActDetailEntity> delList = new ArrayList<TGoodsActDetailEntity>();//需要删除的明细列表
			//otherActDetailList 同一个款号在不同时间段，价格可能不一样，是否需要去重
			//当前活动已存在的明细列表,校验是否相同，相同则删除当前活动中的明细
			List<TGoodsActDetailEntity> existList = this.commonDao.findByProperty(TGoodsActDetailEntity.class, "goodsActId", tGoodsAct.getId());
			for(TGoodsActDetailEntity existDetail : existList){
				for(TGoodsActDetailEntity detail:otherActDetailList){
					if(existDetail.getGoodsId().equals(detail.getGoodsId())){//明细中存在该冲突的商品，则删除
						ActPriceImportVo vo = new ActPriceImportVo();
						vo.setActPrice(detail.getActPrice());
						vo.setGoodsCode(detail.getGoodsCode());
						vo.setErrTip("活动时间冲突");
						errList.add(vo);
						delList.add(existDetail);//添加需要删除的明细
						break;
					}
				}
			}
			if(delList.size()>0){
				TSUser user = ResourceUtil.getSessionUserName();
				//删除本活动中这些冲突的商品
				StringBuffer delSql = new StringBuffer("update t_goods_act_detail set status='I',update_date = '").append(Utility.getCurrentTimestamp())
						.append("',update_by='").append(user.getUserName()).append("' where id in (");
				for(TGoodsActDetailEntity detail:delList){
					delSql.append("'").append(detail.getId()).append("',");
				}
				delSql = delSql.deleteCharAt(delSql.length()-1).append(")");
				//删除冲突的活动商品
				this.commonDao.updateBySqlString(delSql.toString());
			}
		}
		return errList;
	}
	
	@Override
	public String doAudit(TGoodsActEntity tGoodsAct) throws Exception{
		String msg = null;
		tGoodsAct = commonDao.get(TGoodsActEntity.class, tGoodsAct.getId());
		if(TGoodsActEntity.VALID_Y.equals(tGoodsAct.getValid())&&TGoodsActEntity.AUDIT_STATUS_1.equals(tGoodsAct.getAuditStatus())){
			TSUser user = ResourceUtil.getSessionUserName();
			tGoodsAct.setAuditor(user.getRealName());
			tGoodsAct.setAuditTime(Utility.getCurrentTimestamp());
			tGoodsAct.setAuditStatus(TGoodsActEntity.AUDIT_STATUS_2);
			commonDao.updateEntitie(tGoodsAct);
//			int type = 1;
			//把活动商品信息放入redis 20171225改为从数据库查询
//			this.updateRedisActGoods(tGoodsAct,type);
			
		}else{
			msg = "请先刷新活动列表后再操作";
		}
		return msg;
	}
	
	/**更新reids中的活动商品  20171225改为从数据库查询
	 * @param tGoodsAct
	 * @param type 0表示删除，1标识添加
	 */
	/*@SuppressWarnings("unchecked")
	private void updateRedisActGoods(TGoodsActEntity tGoodsAct,int type) throws Exception{
		String retailerId = ResourceUtil.getRetailerId();
		Long t1 = tGoodsAct.getEndTime().getTime();
		Long t2 = System.currentTimeMillis();
		Long t = (t1-t2)/1000;//距离活动结束时间 秒
		if(t>0){//活动还没结束并且没有活动添加该商品
			List<TGoodsActDetailEntity> detailList = this.commonDao.findByProperty(TGoodsActDetailEntity.class, "goodsActId", tGoodsAct.getId());
			if(detailList.size()>0){
				List<Map<String,Object>> list = null;
				String val = this.redisService.get(common.GlobalConstants.ACT_GOODS+retailerId);
				if(Utility.isNotEmpty(val)){
					list = (List<Map<String,Object>>)MAPPER.readValue(val,MAPPER.getTypeFactory().constructParametricType(ArrayList.class, Map.class));
				}else{
					list = new ArrayList<Map<String,Object>>();
				}
				if(type==1){//新增
					for(TGoodsActDetailEntity detail : detailList){
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("goodsId", detail.getGoodsId());
						map.put("goodsActId", tGoodsAct.getId());
						map.put("addTime", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
						map.put("beginTimeStamp", tGoodsAct.getBeginTime().getTime());
						map.put("endTimeStamp", tGoodsAct.getEndTime().getTime());
						map.put("actPrice", detail.getActPrice());
						list.add(map);
					}
					t2 = System.currentTimeMillis();
					t = (t1-t2)/1000;//获取最新距离活动结束时间 秒
					this.redisService.set(common.GlobalConstants.ACT_GOODS+retailerId, MAPPER.writeValueAsString(list),t.intValue());
				}else if(type==0){//删除
					for(TGoodsActDetailEntity delDetal : detailList){
						for(Map<String,Object> map : list){
							if((map.get("goodsId")+"").equals(delDetal.getGoodsId())){
								list.remove(map);//删除该商品
								break;
							}
						}
					}
					t2 = System.currentTimeMillis();
					t = (t1-t2)/1000;//获取最新距离活动结束时间 秒
					this.redisService.set(common.GlobalConstants.ACT_GOODS+retailerId, MAPPER.writeValueAsString(list),t.intValue());
				}
			}
			
		}
	}*/

	@Override
	public String doDown(TGoodsActEntity tGoodsAct) throws Exception{
		String msg = null;
		tGoodsAct = commonDao.get(TGoodsActEntity.class, tGoodsAct.getId());
		if(TGoodsActEntity.VALID_Y.equals(tGoodsAct.getValid())&&TGoodsActEntity.AUDIT_STATUS_2.equals(tGoodsAct.getAuditStatus())){
			tGoodsAct.setValid(TGoodsActEntity.VALID_N);
//			tGoodsAct.setEndTime(new Date());  //活动的结束时间为当前时间
			commonDao.updateEntitie(tGoodsAct);
//			int type = 0;
//			//TODO 把活动商品信息从redis删除
//			this.updateRedisActGoods(tGoodsAct,type);
			
		}else{
			msg = "请先刷新活动列表后再操作";
		}
		return msg;
	}
	
	@Override
	public void doDelAct(TGoodsActEntity tGoodsAct) {
		tGoodsAct = commonDao.get(TGoodsActEntity.class, tGoodsAct.getId());
		tGoodsAct.setStatus("I");
		commonDao.updateEntitie(tGoodsAct);
		this.commonDao.updateBySqlString("update t_goods_act_detail set status = 'I' ,update_date = '"
				+Utility.getCurrentTimestamp()+"' where status = 'A' and goods_act_id= '"+tGoodsAct.getId()+"'");
	}
	
	
	/**
	 * 活动话题列表
	 */
	public void newsDatagrid(String actId, String title, String sortName, DataGrid dataGrid) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		int total = 0;
		try {
			String sql = "SELECT  an.id, n.cover_pic AS coverPic,n.title,an.create_date as createDate FROM t_news n, t_goods_act_news an WHERE n.id = an.news_id AND an.goods_act_id ='"
					+ actId + "' AND an.STATUS='A' AND n.STATUS='A' ";

			String countSql = "SELECT  count(1) FROM t_news n, t_goods_act_news an WHERE n.id = an.news_id AND an.goods_act_id ='"
					+ actId + "' AND an.STATUS='A' AND n.STATUS='A' ";

			if (Utility.isNotEmpty(title)) {
				sql += "AND n.title LIKE '%" + title + "%'";
				countSql += "AND n.title LIKE '%" + title + "%'";
			}
			if (Utility.isEmpty(sortName)) {
				sql += " ORDER BY an.update_date desc";
			} else {
				sql += " ORDER BY " + sortName + " " + dataGrid.getOrder();
			}

			total = this.systemService.getCountForJdbc(countSql).intValue();
			if (total > 0) {
				resultList = systemService.findForJdbc(sql, dataGrid.getPage(),
						dataGrid.getRows());
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		dataGrid.setResults(resultList);
		dataGrid.setTotal(total);
		
	}
	
	
	/**
	 * 添加活动话题列表
	 */
	public void actNewsDatagrid(String actId, String title,
			String sortName, DataGrid dataGrid) {
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		int total = 0;
	
		try {
			String sql = "SELECT id,n.cover_pic AS coverPic, n.title,n.update_date AS updateDate FROM t_news n where status='A' AND news_type not in ('6001','6002') and upLoaded = 'Y' ";
			sql +="AND n.id NOT IN (SELECT news_id  FROM t_goods_act_news WHERE STATUS='A' AND  goods_act_id ='" +actId+"')";
			String countSql = "SELECT count(1) FROM t_news n where status = 'A' and news_type not in ('6001','6002') and upLoaded = 'Y' ";
			countSql +="AND n.id NOT IN (SELECT news_id  FROM t_goods_act_news WHERE STATUS='A' AND goods_act_id ='" +actId+"') ";
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retailerId)){
				sql += "AND n.shopkeeper = '"+retailerId+"' ";
				countSql += "AND n.shopkeeper = '"+retailerId+"' ";
			}
			if(Utility.isNotEmpty(title)){
				sql += "AND n.title LIKE '%"+title+"%' ";
				countSql += "AND n.title LIKE '%"+title+"%' ";
			}
			if(Utility.isEmpty(sortName)){
				sql += " order by updateDate desc";
			}
			else{
				sql+= " ORDER BY "+sortName+" "+dataGrid.getOrder();
			}
			 total = this.systemService.getCountForJdbc(countSql).intValue();
			if(total > 0){
				resultList = this.systemService.findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		dataGrid.setResults(resultList);
		dataGrid.setTotal(total);
	
	}
	
	/**
	 * 删除活动话题
	 */
	public String deleteNews(String Id, String actId) {
		
		String message = "话题删除成功";
		try {
			String sql = "UPDATE t_goods_act_news SET STATUS = 'I', update_date=NOW() WHERE id = '" +Id +"'" ;
			this.systemService.updateBySqlString(sql);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//更新主表newsId（排除当前删除的记录）
			String updateSql = "UPDATE  t_goods_act SET news_id = (SELECT news_id FROM t_goods_act_news n where n.status = 'A' and n.goods_act_id = '" +actId 
					+"' and n.id <>'"+Id+"'  ORDER BY n.create_date desc LIMIT 1) where id = '"+actId+"'";
			systemService.updateBySqlString(updateSql);
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除话题失败";
			throw new BusinessException(e.getMessage());
		}
		return message;
	}
	
	
	/**
	 * 添加活动话题
	 * 单选，newsIds只会有一个
	 */
	public String doBatchAdd(String actId, String newsIds) {
		
		String message = "活动话题添加成功";
		try {
			for (String newsId : newsIds.split(",")) {
				TGoodsActNewsEntity tGoodsActNewsEntity = new TGoodsActNewsEntity();
				tGoodsActNewsEntity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
				tGoodsActNewsEntity.setGoodsActId(actId);
				tGoodsActNewsEntity.setNewsId(newsId);
				tGoodsActNewsService.save(tGoodsActNewsEntity);
				//更新主表newsId
				this.updateBySqlString("update t_goods_act set news_id ='"+newsId+"' where id ='"+actId+"'");
//				this.updateFirstAct(actId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "活动话题添加失败";
			throw new BusinessException(e.getMessage());
		}
		return message;
	}

	@Override
	public void doDelActWords(TGoodsActWordsEntity tActWords) {
		tActWords = commonDao.get(TGoodsActWordsEntity.class, tActWords.getId());
		tActWords.setStatus("I");
		commonDao.updateEntitie(tActWords);
	}

	@Override
	public void doInvalid(String actId) {
		TSUser user = ResourceUtil.getSessionUserName();
		StringBuilder sql = new StringBuilder() ;
		sql = sql.append("UPDATE t_goods_act SET audit_status='3'")
					.append(",update_by='").append(user.getUserName())
					.append("',update_date=NOW() WHERE id='").append(actId).append("'")  ;
		this.commonDao.executeSql(sql.toString()) ;
	}

	@Override
	public void doUpdate(TGoodsActEntity tGoodsAct, HttpServletRequest request) throws Exception {
		TGoodsActEntity t = this.get(TGoodsActEntity.class, tGoodsAct.getId());
		if(Utility.isNotEmpty(tGoodsAct.getNewsId())&&!tGoodsAct.getNewsId().equals(t.getNewsId())){
			TGoodsActNewsEntity newsEntity = new TGoodsActNewsEntity();
			newsEntity.setNewsId(tGoodsAct.getNewsId());
			newsEntity.setStatus("A");
			newsEntity.setGoodsActId(tGoodsAct.getId());
			this.systemService.save(newsEntity);
		}
		MyBeanUtils.copyBeanNotNull2Bean(tGoodsAct, t);
		this.saveOrUpdate(t);
		
		//更新活动与店铺的中间表 t_goods_act_store ：清空该商品关联店铺的记录 重新添加
		String storeIds = request.getParameter("storeIds");
		List<TGoodsActStoreEntity> GoodsActStoreEntities  = systemService.findByProperty(TGoodsActStoreEntity.class, "goodsActId", tGoodsAct.getId()) ;
		this.systemService.deleteAllEntitie(GoodsActStoreEntities);
		if(Utility.isNotEmpty(storeIds)){
			String  ids[]  = storeIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				String storeId = ids[i];
				if(Utility.isNotEmpty(storeId)){
					TGoodsActStoreEntity   storeEntity = new TGoodsActStoreEntity();
					storeEntity.setStoreId(ids[i]);
					storeEntity.setStatus("A");
					storeEntity.setGoodsActId(tGoodsAct.getId());
					this.systemService.save(storeEntity);
				}
			}
		}
	}

	@Override
	public List<ActPriceImportVo> doAdd(TGoodsActEntity tGoodsAct, HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		tGoodsAct.setRetailerId(retailerId);
		this.save(tGoodsAct);
		if(Utility.isNotEmpty(tGoodsAct.getNewsId())){
			TGoodsActNewsEntity newsEntity = new TGoodsActNewsEntity();
			newsEntity.setNewsId(tGoodsAct.getNewsId());
			newsEntity.setStatus("A");
			newsEntity.setGoodsActId(tGoodsAct.getId());
			this.systemService.save(newsEntity);
		}
		String storeIds = request.getParameter("storeIds");
		if(Utility.isNotEmpty(storeIds)){
			String  ids[]  = storeIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				String storeId = ids[i];
				if(Utility.isNotEmpty(storeId)){
					TGoodsActStoreEntity   storeEntity = new TGoodsActStoreEntity();
					storeEntity.setStoreId(ids[i]);
					storeEntity.setStatus("A");
					storeEntity.setGoodsActId(tGoodsAct.getId());
					this.systemService.save(storeEntity);
				}
			}
		}
		List<TGoodsActDetailEntity> otherActDetailList = this.tGoodsActDetailService.getOtherActDetailList(tGoodsAct, retailerId);
		List<ActPriceImportVo> failList = this.doCheckAndDel(tGoodsAct, otherActDetailList);
		return failList ;
	}
}