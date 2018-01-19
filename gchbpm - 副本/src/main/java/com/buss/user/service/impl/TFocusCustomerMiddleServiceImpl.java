package com.buss.user.service.impl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.user.entity.TFocusCustomerEntity;
import com.buss.user.service.TFocusCustomerMiddleServiceI;
import com.buss.user.service.TFocusCustomerServiceI;
import common.DBUtil;

@Service("tFocusCustomerMiddleService")
@Transactional
public class TFocusCustomerMiddleServiceImpl extends CommonServiceImpl implements TFocusCustomerMiddleServiceI {
	
	@Autowired
	private TFocusCustomerServiceI tFocusCustomerService;
	/**
	 * 更新临时表顾客记录
	 * @param request
	 * @param rId 零售商id
	 * @return 新增的数量
	 */
	@Override
	public int updateAndAddTFocusCustomerMiddle(HttpServletRequest request,String rId){
		int size = 0;//添加的记录数量
		String batchNo = request.getParameter("batchNo");//批次号
		//通过条件获取潜在顾客
		long start = System.currentTimeMillis();
		List<TFocusCustomerEntity> detailList = tFocusCustomerService.getListByConditions(request,rId);
		long end = System.currentTimeMillis();
		System.out.println("查询共耗时" + (end - start)+"ms");
		
		//通过批次号查询顾客临时表
		Map<String,String> map = getPhoneMapByBatchNo(batchNo);
		if(Utility.isNotEmpty(detailList)){
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			start = System.currentTimeMillis();
			for (TFocusCustomerEntity tFocusCustomerEntity : detailList) {
				//顾客临时表中不存在潜在顾客表记录
				if(Utility.isEmpty(map.get(tFocusCustomerEntity.getPhoneNo()))){
					Map<String, Object> dataMap = getDateMap(tFocusCustomerEntity,rId,batchNo);
					datas.add(dataMap);
				}
			}
			end = System.currentTimeMillis();
			System.out.println("循环共耗时" + (end - start)+"ms");
			//批量插入
			try {
				if(datas.size()>0){
					size = datas.size();
					start = System.currentTimeMillis();
					Map<String,Object> resultMap = DBUtil.insertAll("t_focus_customer_middle", datas);
					if(!(Boolean) resultMap.get("success")){
						
					}
					end = System.currentTimeMillis();
					System.out.println("批量插入共耗时" + (end - start)+"ms");
					datas.clear();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	private Map<String, Object> getDateMap(TFocusCustomerEntity cust, String retailerId,String batchNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",Utility.getUUID());
		map.put("create_date",Utility.getCurrentTimestamp());
//		map.put("create_by",user.getUserName());
		map.put("status",common.GlobalConstants.STATUS_ACTIVE);
		map.put("name",cust.getName());
		map.put("sex",cust.getSex());
		map.put("phone_no",cust.getPhoneNo());
		map.put("register_area",cust.getRegisterArea());
		map.put("phone_reg_shop",cust.getPhoneRegShop());
		map.put("birthday_rank",cust.getBirthdayRank());
		map.put("to_retailer_id",retailerId);
		map.put("phone_reg_shop",cust.getPhoneRegShop());
		map.put("vip_level",cust.getVipLevel());
		map.put("constellation",cust.getConstellation());
		map.put("zodiac",cust.getZodiac());
		map.put("push_count",cust.getPushCount());
		map.put("click_number",cust.getClickNumber());
		map.put("buy_count",cust.getBuyCount());
		map.put("remark",cust.getRemark());
		map.put("batch_no",batchNo);
		map.put("type", cust.getType());
		map.put("cust_id", cust.getId());//顾客id
		return map;
	}
	
		/**
		 * 删除临时表顾客记录
		 * @param request
		 * @return 新增的数量
		 */
		@Override
		public int updateAndDelTFocusCustomerMiddle(HttpServletRequest request,String rId){
			int size = 0;
			String batchNo = request.getParameter("batchNo");//批次号
			//通过条件获取潜在顾客
			List<TFocusCustomerEntity> detailList = tFocusCustomerService.getListByConditions(request,rId);
			Map<String,TFocusCustomerEntity> map = new HashMap<String, TFocusCustomerEntity>();
			if(Utility.isNotEmpty(detailList)){
				for (TFocusCustomerEntity tFocusCustomerEntity : detailList) {
					map.put(tFocusCustomerEntity.getPhoneNo(), tFocusCustomerEntity);
				}
			}
			
			//顾客临时表
			List<String> phoneNoList = getPhoneListByBatchNo(batchNo);
			if(Utility.isNotEmpty(phoneNoList)){
				StringBuilder phoneNoSb = new StringBuilder();
				for (String phoneNo: phoneNoList) {
					//顾客临时表中存在潜在顾客表筛选的记录
					if(Utility.isNotEmpty(map.get(phoneNo))){
						phoneNoSb.append(",'").append(phoneNo).append("'");
						size ++;
					}
				}
				if(Utility.isNotEmpty(phoneNoSb.toString())){
					phoneNoSb.deleteCharAt(0);
					StringBuilder sb = new StringBuilder();
					sb.append(" update t_focus_customer_middle set status='I' where status='A' and batch_no = '").append(batchNo).append("'")
					.append(" and phone_no in(").append(phoneNoSb.toString()).append(")");
					commonDao.updateBySqlString(sb.toString());
				}
			}
		return size;
	}
	
	
		/**
		 * 删除临时顾客
		 * @param ids  逗号拼接
		 */
		@Override
		public int updateAndDelCst(String ids){
			int size = 0;
			if(Utility.isNotEmpty(ids)){
				List<String> idList = StringUtil.stringToStringListBySlipStr(",", ids);
				size = idList.size();
				String idStr = StringUtil.listNewToStringSlipStr(idList, ",");
				StringBuilder sb = new StringBuilder();
				sb.append(" update t_focus_customer_middle set status='I' where id in(").append(idStr).append(")");
				commonDao.updateBySqlString(sb.toString());
			}
			return size;
		}
	
	
		
		
		
		/**
		 * 根据条件查询潜在顾客
		 * @param batchNo 批次号
		 * @return   <phoneNo,phoneNo>
		 */
		@Override
		public Map<String,String> getPhoneMapByBatchNo(String batchNo){
			Map<String,String> returnMap = new HashMap<String, String>();
			StringBuilder sb = new StringBuilder(1024);
			sb.append(" SELECT")
			.append(" phone_no ")
			.append(" FROM")
			.append(" t_focus_customer_middle ")
			.append(" WHERE status='A'")
			.append(" and batch_no='").append(batchNo).append("'");//批次号
			List<Map<String,Object>> list = commonDao.findForJdbc(sb.toString());
			if(Utility.isNotEmpty(list)){
				String phoneNo = null;
				for (Map<String, Object> obj : list) {
					if(Utility.isNotEmpty(obj)){
						phoneNo = obj.get("phone_no").toString();
						if(Utility.isNotEmpty(phoneNo)){
							returnMap.put(phoneNo, phoneNo);
						}
					}
				}
			}
			return returnMap;
		}
		
		
		/**
		 * 根据条件查询潜在顾客
		 * @param batchNo 批次号
		 * @return   List<phoneNo>
		 */
		@Override
		public List<String> getPhoneListByBatchNo(String batchNo){
			List<String> returnList = new ArrayList<String>();
			StringBuilder sb = new StringBuilder(1024);
			sb.append(" SELECT")
			.append(" phone_no ")
			.append(" FROM")
			.append(" t_focus_customer_middle ")
			.append(" WHERE status='A'")
			.append(" and batch_no='").append(batchNo).append("'");//批次号
			List<Map<String,Object>> list = commonDao.findForJdbc(sb.toString());
			if(Utility.isNotEmpty(list)){
				String phoneNo = null;
				for (Map<String, Object> obj : list) {
					if(Utility.isNotEmpty(obj)){
						phoneNo = obj.get("phone_no").toString();
						if(Utility.isNotEmpty(phoneNo)){
							returnList.add(phoneNo);
						}
					}
				}
			}
			return returnList;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}