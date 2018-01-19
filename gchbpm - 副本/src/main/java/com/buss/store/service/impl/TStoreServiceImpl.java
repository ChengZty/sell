package com.buss.store.service.impl;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.goods.service.TGoodsServiceI;
import com.buss.goods.vo.GoodsStockImportVo;
import com.buss.store.entity.TStoreEntity;
import com.buss.store.entity.TStorePicsEntity;
import com.buss.store.service.TStoreServiceI;
import com.buss.store.vo.StoreImportVo;

import common.DBUtil;

@Service("tStoreService")
@Transactional
public class TStoreServiceImpl extends CommonServiceImpl implements TStoreServiceI {
	@Autowired
	private TGoodsServiceI tGoodsService;
 	
 	@Override
 	public void saveStore(TStoreEntity tStore) {
 		TSUser user = ResourceUtil.getSessionUserName();
		String retailerId = null;
		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商
			retailerId = user.getId();
		}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){//零售商员工
			retailerId = user.getRetailerId();
		}
		tStore.setRetailerId(retailerId);
 		this.commonDao.save(tStore);
 		this.updateStorePics(tStore, "A");
 	}
 	
 	@Override
 	public void updateStore(TStoreEntity tStore) {
 		this.commonDao.updateEntitie(tStore);
		//修改顾客保存的店铺名
		String sql = "update t_focus_customer set phone_reg_shop_name = '"+tStore.getName()+"' where phone_reg_shop='"+
				tStore.getId()+"' and to_retailer_id='"+tStore.getRetailerId()+"'";
		this.commonDao.updateBySqlString(sql);
 		this.updateStorePics(tStore, "U");
 	}
 	
 	private void updateStorePics(TStoreEntity tStore, String optType) {
 		List<TStorePicsEntity> oldStorePics = null;
 		if("U".equals(optType)){
 			oldStorePics = this.commonDao.findHql("from TStorePicsEntity where storeId =? ", tStore.getId());
 		}
 		List<TStorePicsEntity> newStorePics = tStore.getStorePics();
 		if(Utility.isEmpty(oldStorePics)){//以前没有图片
 			if(!Utility.isEmpty(newStorePics)){//新增
 	 			int n=newStorePics.size();
 	 			for(int i=0;i<n;i++){
 	 				TStorePicsEntity entity = newStorePics.get(i);
 	 				entity.setStoreId(tStore.getId());
 	 				entity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 	 				this.commonDao.save(entity);
 	 			}
 	 		}
 		}else{//以前有图片
 			if(Utility.isEmpty(newStorePics)){
 				this.commonDao.updateBySqlString("update t_store_pics set status = 'I' where store_id ='"+tStore.getId()+"' ");
 			}else{
 				int n= newStorePics.size();
 				for(int i=0;i<n;i++){
 					TStorePicsEntity entity = newStorePics.get(i);
 					if(Utility.isEmpty(entity.getId())){//新增
 						entity.setStoreId(tStore.getId());
 						entity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 						this.commonDao.save(entity);
 					}else{//修改
 						this.commonDao.updateBySqlString("update t_store_pics set pic_url = '"+entity.getPicUrl()
 		 	 					+"' where id ='"+entity.getId()+"'");
 						for(TStorePicsEntity old : oldStorePics){
 							if(entity.getId().equals(old.getId())){//把有的都移除，剩余的都要是删除的
 								oldStorePics.remove(old);
 								break;
 							}
 						}
 					}
 				}
 				if(!Utility.isEmpty(oldStorePics)){//删除
 					for(TStorePicsEntity entity : oldStorePics){
 						this.commonDao.updateBySqlString("update t_store_pics set status = 'I' where id ='"+entity.getId()+"'");
					}
 				}
 			}
 		}
	}

	@Override
 	public String deleteStore(String storeId) {
 		String retailerId = ResourceUtil.getRetailerId();
 		TStoreEntity tStore = this.flushEntity(TStoreEntity.class, storeId);
 		StringBuffer Sql = new StringBuffer("select count(1) count from t_s_user tsu left join t_person tp on tsu.id=tp.user_id where ");
 		Sql.append("tsu.status='A' and tp.status='A' and tsu.user_type='03' and tsu.user_status='1' ");
 		if(Utility.isNotEmpty(retailerId)){
 			Sql.append("and tsu.retailer_id='"+retailerId+"' ");
 		}
 		if(Utility.isNotEmpty(tStore.getId())){
 			Sql.append("and tp.store_id='"+tStore.getId()+"' ");
 		}
 		int guideNum = this.getCountForJdbc(Sql.toString()).intValue();
 		if(guideNum < 1){
 			tStore.setStatus("I");
 	 		this.commonDao.updateEntitie(tStore);
 	 		String updatePicsSql = "update t_store_pics set status = 'I' where store_id = '"+tStore.getId()+"'";
 	 		this.commonDao.updateBySqlString(updatePicsSql);

 			return "实体店删除成功";
 		}else{
 			return "实体店存在导购，不能删除！";
 		}
 	}
	
	@Override
 	public List<CommonVo> getStoreList(){
 		List<CommonVo> lis = new ArrayList<CommonVo>();
 		String retailerId = ResourceUtil.getRetailerId();
 		if(Utility.isNotEmpty(retailerId)){
 			List<TStoreEntity> lvlList = this.commonDao.findByProperty(TStoreEntity.class, "retailerId", retailerId);
 			if(!Utility.isEmpty(lvlList))
 			{
 				for(TStoreEntity e:lvlList){
 					CommonVo vo = new CommonVo();
 					vo.setId(e.getId());
 					vo.setName(e.getName());
 					lis.add(vo);
 				}
 			}
 		}else{
 			List<TStoreEntity> lvlList = this.commonDao.findByProperty(TStoreEntity.class, "status", "A");
 			if(!Utility.isEmpty(lvlList))
 			{
 				for(TStoreEntity e:lvlList){
 					CommonVo vo = new CommonVo();
 					vo.setId(e.getId());
 					vo.setName(e.getName());
 					lis.add(vo);
 				}
 			}
 		}
		return lis;
 	}
	
	@Override
	public Map<String, String> batchSaveStores(List<StoreImportVo> importList) throws Exception {
		Map<String,String> resultMap = new HashMap<String,String>();
		TSUser retailer = null;
		TSUser nowUser = ResourceUtil.getSessionUserName();
		if(common.GlobalConstants.USER_TYPE_02.equals(nowUser.getUserType())){
			retailer = nowUser;
		}else if(common.GlobalConstants.USER_TYPE_05.equals(nowUser.getUserType())){
			retailer = this.commonDao.get(TSUser.class, nowUser.getRetailerId());
		}
		resultMap.put("result", "BAD");//默认保存失败
		boolean hasError = false;//校验过程中有错误
		//校验店铺编号
		hasError = this.checkStoreCodes(importList,retailer.getId());
		if(!hasError){
			this.batchSaveStores(importList,retailer);
			resultMap.put("successCount", importList.size()+"");//成功数量按条码数算
			resultMap.put("result", "OK");
		}else{
			for(int i=0;i<importList.size();i++){
				StoreImportVo vo = importList.get(i);
				if(Utility.isEmpty(vo.getRemark())){
					importList.remove(vo);//去掉没有错误的记录
					i--;
				}else{
					vo.setRemark(vo.getRemark().substring(1));
				}
			}
			resultMap.put("errCount", importList.size()+"");
			String title = "实体店导入错误提示（请在原导入表中修改好后再次导入）";
			String key = this.tGoodsService.uploadExcelFileToQN(StoreImportVo.class, importList,retailer,"storeUpload",title);
			System.out.println(key);
			resultMap.put("errKey", key);
		}
		return resultMap;
	}

	private void batchSaveStores(List<StoreImportVo> importList, TSUser retailer) throws Exception {
		List<Map<String, Object>> storeDatas = new ArrayList<Map<String, Object>>();//实体店数据
		Date createDate = Utility.getCurrentTimestamp();
		TSUser user = ResourceUtil.getSessionUserName();
		for(StoreImportVo vo : importList){
			Map<String, Object> storeMap = new HashMap<String, Object>();
			storeMap.put("id",Utility.getUUID());
			storeMap.put("create_name",user.getRealName());
			storeMap.put("create_by",user.getUserName());
			storeMap.put("create_date",createDate);
			storeMap.put("update_date",createDate);
			storeMap.put("status",common.GlobalConstants.STATUS_ACTIVE);
			storeMap.put("name", vo.getName());
			storeMap.put("store_code", vo.getStoreCode());
			storeMap.put("address", vo.getAddress());
			storeMap.put("phone_no", vo.getPhoneNo());
			storeMap.put("sort_num", vo.getSortNum());
			storeMap.put("retailer_id", retailer.getId());
			storeDatas.add(storeMap);
		}
		if(Utility.isNotEmpty(storeDatas)){
			Long tt1 = System.currentTimeMillis();
			 Map<String, Object> resMap= DBUtil.insertAll("t_store", storeDatas);
			 Long tt2 = System.currentTimeMillis();
			 System.out.println("批量导入实体店====="+(tt2-tt1)+"ms");
			 if((Boolean) resMap.get("success")){
				 int affectRowCount = (Integer) resMap.get("affectRowCount");
				 System.out.println("成功插入"+affectRowCount);
			 }
		}
	}

	private boolean checkStoreCodes(List<StoreImportVo> importList,String retailerId) {
		boolean hasError = false;
		Set<String> barCodes = new HashSet<String>();
		for(StoreImportVo vo : importList){
			if(barCodes.contains(vo.getStoreCode())){
				hasError = true;
				vo.setRemark(vo.getRemark()+"，该店铺编号重复");
			}else{
				barCodes.add(vo.getStoreCode());
			}
		}
		//已存在的店铺
		List<TStoreEntity> list = this.commonDao.findByProperty(TStoreEntity.class, "retailerId", retailerId);
		if(list.size()>0){
			for(StoreImportVo vo : importList){
				for(TStoreEntity entity : list){
					if(vo.getStoreCode().equals(entity.getStoreCode())){
						vo.setRemark(vo.getRemark()+"，该店铺编号系统中已存在");
						hasError = true;
						break;
					}
				}
			}
		}
		return hasError;
	}
}