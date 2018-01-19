package com.buss.ticket.service.impl;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.ticket.entity.TTicketGoodsEntity;
import com.buss.ticket.entity.TTicketInfoEntity;
import com.buss.ticket.entity.TTicketRetailersEntity;
import com.buss.ticket.service.TTicketInfoServiceI;

@Service("tTicketInfoService")
@Transactional
public class TTicketInfoServiceImpl extends CommonServiceImpl implements TTicketInfoServiceI {
 	@Override
 	public String doBatchDistribute(HttpServletRequest request) {
 		String ticketId = request.getParameter("ticketId");
 		String sheetNum = request.getParameter("sheetNum");
 		String retailerIds = request.getParameter("retailerIds");
 		if(Utility.isNotEmpty(retailerIds)&&Utility.isNotEmpty(sheetNum)){
 			TTicketInfoEntity tTicketInfo = commonDao.get(TTicketInfoEntity.class,ticketId);
 			if(TTicketInfoEntity.TICKET_STATUS_2.equals(tTicketInfo.getTicketStatus())){
 				String[] retailerIdArr = retailerIds.split(",");
 				int unitNum = Integer.valueOf(sheetNum);//人均数量
 				Integer totalNum = unitNum*retailerIdArr.length;
 				if(totalNum>tTicketInfo.getSheetRemain()){
 					return  "分配总张数超过剩余张数";
 				}else{
 					tTicketInfo.setSheetSent(tTicketInfo.getSheetSent()+totalNum);
 					tTicketInfo.setSheetRemain(tTicketInfo.getSheetRemain()-totalNum);
 					commonDao.updateEntitie(tTicketInfo);
 					for(String retailerId : retailerIdArr){
 						String hql = "from TTicketRetailersEntity where ticketId = ? and userId = ?";
 						TTicketRetailersEntity ticketRetailer = null;
 						List<TTicketRetailersEntity> list= this.commonDao.findHql(hql, ticketId,retailerId);
 						if(Utility.isNotEmpty(list)){//更新
 							ticketRetailer = list.get(0);
 							ticketRetailer.setSheet(ticketRetailer.getSheet()+unitNum);
 							ticketRetailer.setSheetRemain(ticketRetailer.getSheetRemain()+unitNum);
 							commonDao.updateEntitie(ticketRetailer);
 						}else{//新增
 							ticketRetailer = new TTicketRetailersEntity();
 							ticketRetailer.setAddTime(Utility.getCurrentTimestamp());
 							ticketRetailer.setSheet(unitNum);
 							ticketRetailer.setSheetRemain(unitNum);
 							ticketRetailer.setSheetGive(0);
 							ticketRetailer.setStatus("A");
 							ticketRetailer.setTicketId(ticketId);
 							ticketRetailer.setUserId(retailerId);
 							ticketRetailer.setSenderId(ResourceUtil.getSessionUserName().getId());
 							commonDao.save(ticketRetailer);
 						}
 					}
 				}
 			}else{
 				return "券不是审核状态";
 			}
 		}
 		return "优惠券批量分配成功";
 	}
 	
 	@Override
 	public void doAdd(TTicketInfoEntity tTicketInfo) {
 		String sotreIds = tTicketInfo.getStoreIds();
 		if(StringUtil.isNotEmpty(sotreIds)){
 			String[] arr = sotreIds.split(",");
 			int n = arr.length;
 			List<TTicketGoodsEntity> ticketGoodsList = null;
 			if(n>0&&!TTicketInfoEntity.USING_RANGE_1.equals(tTicketInfo.getUsingRange())){//多个店铺且不是全馆券
 				ticketGoodsList = this.commonDao.findByProperty(TTicketGoodsEntity.class, "ticketId", tTicketInfo.getId());
 			}
 			String batchNo = DateUtils.getDataString(new SimpleDateFormat("yyyyMMddssmmss"));
 			for(int i=0;i<n;i++){
 				if(i==0){
 					tTicketInfo.setBatchNo(batchNo);
 	 				tTicketInfo.setSheetRemain(tTicketInfo.getSheetTotal());
 	 				tTicketInfo.setSheetSent(0);
 	 				tTicketInfo.setSheetUsed(0);
 	 				tTicketInfo.setStoreId(arr[i]);
 	 				commonDao.save(tTicketInfo);
 				}else{
 					TTicketInfoEntity tTicketInfo2 = new TTicketInfoEntity();
 					BeanUtils.copyProperties(tTicketInfo, tTicketInfo2);
 					tTicketInfo2.setId(Utility.getUUID());
 					if(Utility.isNotEmpty(ticketGoodsList)){//复制已选的商品和品牌
 						for(TTicketGoodsEntity ticketGoods : ticketGoodsList){
 							TTicketGoodsEntity entity = new TTicketGoodsEntity();
 							BeanUtils.copyProperties(ticketGoods, entity);
 							entity.setId(null);
 							entity.setTicketId(tTicketInfo2.getId());
 							commonDao.save(entity);
 						}
 					}
 					tTicketInfo2.setBatchNo(batchNo);
 					tTicketInfo2.setSheetRemain(tTicketInfo.getSheetTotal());
 					tTicketInfo2.setSheetSent(0);
 					tTicketInfo2.setSheetUsed(0);
 					tTicketInfo2.setStoreId(arr[i]);
 	 				commonDao.save(tTicketInfo2);
 				}
 				
 			}
 		}
 		
 	}
}