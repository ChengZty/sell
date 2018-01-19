package com.buss.user.service.impl;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.base.entity.TBaseTagsEntity;
import com.buss.store.entity.TStoreEntity;
import com.buss.user.entity.TCustInfoCompleteEntity;
import com.buss.user.entity.TCustInfoDetailEntity;
import com.buss.user.entity.TFocusCustomerEntity;
import com.buss.user.entity.TPersonEntity;
import com.buss.user.entity.TVipLevelEntity;
import com.buss.user.service.TFocusCustomerServiceI;

import cn.redis.service.RedisService;
import common.DBUtil;
import common.GlobalConstants;
import common.YearUtil;

@Service("tFocusCustomerService")
@Transactional
public class TFocusCustomerServiceImpl extends CommonServiceImpl implements TFocusCustomerServiceI {
	@Autowired
	private RedisService redisService;
	@Resource  
	private RabbitTemplate rabbitTemplate; 
	
	@Override
	public String doBatchSave(List<TFocusCustomerEntity> listTFocusCustomerEntitys)throws Exception {
		String msg = "文件导入成功";
		String retailerId = ResourceUtil.getRetailerId();
		TSUser user =ResourceUtil.getSessionUserName();
		String hql = "from TVipLevelEntity where retailerId = '"+retailerId+"' and status = 'A'";
		List<TVipLevelEntity> vipLvlList = this.commonDao.findByQueryString(hql);
		if(!Utility.isEmpty(listTFocusCustomerEntitys)){
			int n =3;
			HashSet<String> phoneNoSet = new HashSet<String>();
			StringBuffer phoneSB = new StringBuffer("select DISTINCT(phone_no) from t_focus_customer where  phone_no in(");
			//验证手机号是否重复
			for(TFocusCustomerEntity entity : listTFocusCustomerEntitys){
				n++;
				String phoneNo = entity.getPhoneNo();
				if(!phoneNoSet.contains(phoneNo)){
					phoneNoSet.add(phoneNo);
					phoneSB.append("'").append(phoneNo).append("',");
				}else{
					msg = "第"+n+"行手机号："+entity.getPhoneNo()+"重复";
					return msg;
				}
			}
			phoneNoSet = null;
			phoneSB.deleteCharAt(phoneSB.length()-1);
			phoneSB.append(")  and status ='A' ");
			List<String> existNo = this.commonDao.findListbySql(phoneSB.toString());
			//查出零售商已经录过的号码和导购录的号码，重复的则不导入
			HashSet<String> guideAddPhoneNoSet = new HashSet<String>();
			
			if(!Utility.isEmpty(existNo)){
				guideAddPhoneNoSet.addAll(existNo);
				int existSize = existNo.size();
				int inSize = listTFocusCustomerEntitys.size() -  existSize;
				msg = "成功插入"+inSize+"条数据,"+existSize+"条数据和g+重复";
			}
//			List<TFocusCustomerEntity> entitys = new ArrayList<TFocusCustomerEntity>();
			//批量插入的数据map
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			for (TFocusCustomerEntity tFocusCustomer : listTFocusCustomerEntitys) {
				if(guideAddPhoneNoSet.contains(tFocusCustomer.getPhoneNo())){
					continue;
				}else{
					if(!Utility.isEmpty(tFocusCustomer.getPhoneNo())){
						tFocusCustomer.setAddRetailerId(retailerId);
						tFocusCustomer.setToRetailerId(retailerId);
						tFocusCustomer.setVipLevelId(this.getVipLvlId(vipLvlList,tFocusCustomer.getVipLevel()));
//						tFocusCustomer.setStatus(common.GlobalConstants.STATUS_ACTIVE);
//						tFocusCustomer.setIsUseApp("0");//未使用app
						formatBaseData(tFocusCustomer);
						//批量插入数据的map
						Map<String, Object> map = getDateMap(user,tFocusCustomer,retailerId,Utility.getCurrentTimestamp());
						datas.add(map);
//						entitys.add(tFocusCustomer);
					}
				}
			}
//			if(!Utility.isEmpty(entitys)){
//				this.commonDao.batchSave(entitys);
//			}
			if(datas.size()>0){
				long start = System.currentTimeMillis();
				DBUtil.insertAll("t_focus_customer", datas);
				long end = System.currentTimeMillis();
				System.out.println("批量插入共耗时" + (end - start)+"ms");
				listTFocusCustomerEntitys.clear();
				datas.clear();
			}
		}
		return msg;
		
	}
	
	/**获取顾客vip等级的vip ID
	 * @param vipLvlList
	 * @param vipName
	 * @return
	 */
	public String getVipLvlId(List<TVipLevelEntity> vipLvlList,String vipName) {
		String val = "";
		if(!StringUtil.isEmpty(vipName)){
			for(TVipLevelEntity entity : vipLvlList){
				if(vipName.equalsIgnoreCase(entity.getVipName())){
					val = entity.getId();
					break;
				}
			}
		}
		return val;
	}
	
	
 	/**
 	 * 验证数据在零售商平台是否存在
 	 * @param phoneNo 电话
 	 * @param retailerId 零售商ID
 	 */
	@Override
	public int checkPhoneAndRetailerExist(String phoneNo,String retailerId) {
		int flag = 0;//是否已注册 0为没注册，1为已注册
		String checkCustmSql = "select count(1) from t_focus_customer where phone_no='"+phoneNo+"' and status ='A' and to_retailer_id='"+retailerId+"'";
		
		Long num = this.commonDao.getCountForJdbc(checkCustmSql);
		if(num > 0){//已经注册了顾客
			flag = 1;
		}
		return flag;
	}
	
	/**
 	 * 验证数据在平台是否存在
 	 */
	@Override
	public int checkExist(String phoneNo) {
		int flag = 0;//是否已注册 0为没注册，1为已注册
		String checkCustmSql = "select count(1) from t_person where phone_no='"+phoneNo+"' and status ='A' and user_type ='04'";
		
		Long num = this.commonDao.getCountForJdbc(checkCustmSql);
		if(num > 0){//已经注册了顾客
			flag = 1;
		}else{
			StringBuffer hql = new StringBuffer("from TFocusCustomerEntity where phoneNo = ?  order by createDate desc");
			List<TFocusCustomerEntity> custList = this.commonDao.findHql(hql.toString(), phoneNo);
			if(Utility.isNotEmpty(custList)){//已经录入潜在顾客（导购已经录入，后台还是可以录）
				String retailerId = ResourceUtil.getRetailerId();
				if(flag==0){
					for(TFocusCustomerEntity cust : custList){
						if(retailerId.equals(cust.getToRetailerId())){//后台已经录过
							flag = 1;
							break;
						}
					}
				}
				if(flag==0){//当前零售商没有导购和后台录入记录
					TFocusCustomerEntity focusCustm = custList.get(0);//其他零售商最近录入的记录
					Long n = new Date().getTime()-180L*24L*3600L*1000L-focusCustm.getCreateDate().getTime();
					if(n>0){//已经超过180天(可以重新录入)
						flag = 2;
					}else{
						flag = 1;
					}
				}
			}
		}
		return flag;
	}
	
	@Override
	public void saveFocusCustm(int flag, TFocusCustomerEntity tFocusCustomer) {
		String retailerId = ResourceUtil.getRetailerId();
		tFocusCustomer.setToRetailerId(retailerId);
		tFocusCustomer.setAddRetailerId(retailerId);
		tFocusCustomer.setType("");
		this.formatBaseData(tFocusCustomer);
		if(0==flag){// 0为没注册
			this.commonDao.save(tFocusCustomer);
		}else if(2==flag){//已经超过180天(之前其他零售商不能录入，后改为可以录入)
			this.commonDao.save(tFocusCustomer);
		}
		//更新顾客资料完整度
		this.updateCustomerInfoComplete(tFocusCustomer);
	}
	
	@Override
	public void updateCustomer(TFocusCustomerEntity tFocusCustomer) {
		this.formatBaseData(tFocusCustomer);
		this.commonDao.updateEntitie(tFocusCustomer);
		//更新顾客资料完整度
		this.updateCustomerInfoComplete(tFocusCustomer);
	}
	
	/**更新顾客资料完整度
	 * name,vipLevelId,sex,commonContact,phoneNo,customerSource,residentPlace,appearance,birthday,birthdayRank,customerSize
	 * @param tFocusCustomer
	 */
	private void updateCustomerInfoComplete(TFocusCustomerEntity tFocusCustomer) {
		//资料数记入完整度的字段
		String toBeCountProperties = "name,vipLevelId,sex,commonContact,phoneNo,customerSource,residentPlace,appearance,birthday,birthdayRank,customerSize";
		//本次编辑后的基础资料完整个数
		Integer finishedNum = getCustomerDateNum(tFocusCustomer,toBeCountProperties);
		int questionsCount = TFocusCustomerEntity.QUESTIONS_NUM;//该阶段标签或者基础问题总数
		//基础资料
		String tagStage = TBaseTagsEntity.TAG_STAGE_J;
		String guideId = Utility.isNotEmpty(tFocusCustomer.getAddGuideId())?tFocusCustomer.getAddGuideId():tFocusCustomer.getToGuideId();
		String phoneNo = tFocusCustomer.getPhoneNo();
		String retailerId = tFocusCustomer.getToRetailerId();
		saveOrUpdateTCustInfoComplete(guideId, phoneNo, retailerId, finishedNum, questionsCount, tagStage, tFocusCustomer.getId());
	}
	
	/**
	 * 获取顾客完整问题个数
	 * @param tFocusCustomerEntity
	 * @param toBeCountProperties 格式： name,sex,phoneNo 20171117新增
	 * @return
	 */
	public Integer getCustomerDateNum(TFocusCustomerEntity tFocusCustomerEntity,String toBeCountProperties){
		int num = 0;
		if(Utility.isNotEmpty(toBeCountProperties)){//
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(tFocusCustomerEntity);
			for (int i =0;i<pds.length;i++)
			{
				PropertyDescriptor pd = pds[i];
				String propname = pd.getName();
				//获取对象属性方法
		        Method method = pd.getReadMethod();
		        Object value = null;
		        try {
		        	//获取属性对应的值
		        	value = method.invoke(tFocusCustomerEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(Utility.isNotEmpty(value)&&toBeCountProperties.contains(propname)){
					for(String propertyName : toBeCountProperties.split(",")){
						if(propname.equals(propertyName)){
							num++;
							break;
						}
					}
				}
			}
		}
		return num;
	}
	
	/**
	 * 记录顾客资料完整度主表
	 * @param custId 表主键ID
	 * @param guideId 导购ID
	 * @param phoneNo 手机号
	 * @param retailerId 零售ID
	 * @param finishedNum 完成个数
	 * @param questionsCount 当前阶段标签或者问题总个数
	 * @param tagStage 阶段类型
	 * @param customerId 潜在顾客ID
	 */
	public void saveOrUpdateTCustInfoComplete(String guideId,String phoneNo,String retailerId,int finishedNum,int questionsCount, String tagStage,String customerId){
		int totalNum =0;//问题（基础资料）和标签（生活属性，消费属性，公司自定义标签）的总个数
		Set<String> set = redisService.hkeys(common.GlobalConstants.TAG_COUNT+"_*");//基础资料（J），生活属性(2)，消费属性(3)
		for (String string : set) {
			String tagNum = redisService.get(string);
			if(Utility.isNotEmpty(tagNum)){
				totalNum = totalNum + Integer.parseInt(tagNum);
			}
		}
		//公司自定义标签个数
		String totalCurrentTagStageSql = "select count(1) from t_base_tags where status = 'A' and to_User_Type='"+TBaseTagsEntity.TO_USER_TYPE_CUST
				+"' and tag_stage = '"+TBaseTagsEntity.TAG_STAGE_4+"' and valid = '"+TBaseTagsEntity.VALID_Y+"' and retailer_id='"+retailerId+"'";
		int companyTagStageCount = this.commonDao.getCountForJdbc(totalCurrentTagStageSql).intValue();
		totalNum+=companyTagStageCount;
		int historyDetailFinishedNum = 0;//明细历史完成数量
		TCustInfoDetailEntity custInfoDetailEntity = null;//查询顾客资料完整度表中是否已存在记录
		int historyFinishedNum = 0;//主表历史完成数量
		//根据导购+顾客custId查询资料完整度主表数据
		TCustInfoCompleteEntity completeEntity =this.checkTCustInfoCompleteIsExist(retailerId,guideId, customerId);
		if(Utility.isNotEmpty(completeEntity)){
			historyFinishedNum = completeEntity.getFinishedNum();
			custInfoDetailEntity = this.checkTCustInfoDetailIsExist(completeEntity.getId(), tagStage);
		}
		if(Utility.isNotEmpty(custInfoDetailEntity)){//如果明细不为空则修改
			historyDetailFinishedNum = custInfoDetailEntity.getFinishedNum();
			if(finishedNum>0){//数据有更改
				int percent = finishedNum*100/questionsCount;
				custInfoDetailEntity.setFinishedNum(finishedNum);
				custInfoDetailEntity.setPercent(percent);
			}
		}
		TSUser user = ResourceUtil.getSessionUserName();
		if(Utility.isNotEmpty(completeEntity)){//存在主表记录修改
			completeEntity.setPhoneNo(phoneNo);//更新顾客手机号（开始的时候没有录入顾客手机号）
			completeEntity.setFinishedNum(finishedNum - historyDetailFinishedNum + historyFinishedNum);
			completeEntity.setTotalNum(totalNum);
			completeEntity.setUpdateBy(user.getUserName());
			completeEntity.setUpdateDate(new Date());
		}else{
			completeEntity = new TCustInfoCompleteEntity();
			completeEntity.setGuideId(guideId);
			completeEntity.setRetailerId(retailerId);
			completeEntity.setPhoneNo(phoneNo);
			completeEntity.setFinishedNum(finishedNum);
			completeEntity.setCustomerId(customerId);
			completeEntity.setTotalNum(totalNum);	
			completeEntity.setCreateBy(user.getUserName());
			completeEntity.setCreateDate(new Date());
			completeEntity.setStatus(GlobalConstants.STATUS_ACTIVE);
		}
		if(Utility.isNotEmpty(completeEntity.getTotalNum())&&Utility.isNotEmpty(completeEntity.getFinishedNum())){
			completeEntity.setPercent((int)Math.rint(completeEntity.getFinishedNum()*10.0/completeEntity.getTotalNum()) * 10);//保留整数10的倍数
		}
		this.commonDao.saveOrUpdate(completeEntity);
		
		if(Utility.isEmpty(custInfoDetailEntity)){//添加顾客资料明细
			custInfoDetailEntity = new TCustInfoDetailEntity();
			custInfoDetailEntity.setCustInfoId(completeEntity.getId());
			custInfoDetailEntity.setTotalNum(questionsCount);
			custInfoDetailEntity.setType(tagStage);
			custInfoDetailEntity.setFinishedNum(finishedNum);
			custInfoDetailEntity.setPercent(finishedNum*100/questionsCount);
			
		}
		custInfoDetailEntity.setCustInfoId(completeEntity.getId());
		if(Utility.isEmpty(custInfoDetailEntity.getId())){
			custInfoDetailEntity.setCreateDate(new Date());
			custInfoDetailEntity.setUpdateDate(new Date());
			custInfoDetailEntity.setStatus(GlobalConstants.STATUS_ACTIVE);
		}
		
		this.commonDao.saveOrUpdate(custInfoDetailEntity);
		
	}
	
	/**
	 * 检查顾客资料完成表中资料类型是否存在资料完整明细表中
	 * @param custInfoId 主表ID
	 * @param type 阶段类型
	 * @return
	 */
	public TCustInfoDetailEntity checkTCustInfoDetailIsExist(String custInfoId,String type){
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ")
		.append(" id, ")
		.append(" create_name createName,")
		.append(" create_by createBy, ")
		.append(" create_date createDate,")
		.append(" update_name updateName,")
		.append(" update_by updateBy,")
		.append(" update_date updateDate,")
		.append(" status ,")
		.append(" t_cust_info_id tCustInfoId, ")
		.append(" finished_num finishedNum, ")
		.append(" type, ")
		.append(" total_num totalNum, ")
		.append(" percent ")
		.append(" FROM t_cust_info_detail")
		.append(" where status = '").append(GlobalConstants.STATUS_ACTIVE).append("'")
		.append(" and t_cust_info_id = '").append(custInfoId).append("'")
		.append(" and type = '").append(type).append("'");
		List<TCustInfoDetailEntity> list = this.commonDao.findObjForJdbc(sb.toString(), TCustInfoDetailEntity.class);
		TCustInfoDetailEntity custInfoDetailEntity = null;
		if(Utility.isNotEmpty(list)){
			custInfoDetailEntity = list.get(0);
		}
		return custInfoDetailEntity;
	}
	
	/**
	 * 检查顾客是否存在资料完整表中
	 * @param guideId 导购ID
	 * @param customerId 待发展顾客id
	 * @return
	 */
	public TCustInfoCompleteEntity checkTCustInfoCompleteIsExist(String retailerId,String guideId,String customerId){
		List<TCustInfoCompleteEntity> list = null;
		if(Utility.isEmpty(guideId)){
			list = this.commonDao.findHql("from TCustInfoCompleteEntity where retailerId=? and customerId=?", retailerId,customerId);
		}else{
			list = this.commonDao.findHql("from TCustInfoCompleteEntity where retailerId=? and guideId=? and customerId=?", retailerId,guideId,customerId);
		}
		TCustInfoCompleteEntity completeEntity = null;
		if(Utility.isNotEmpty(list)){
			completeEntity = list.get(0);
		}
		return completeEntity;
	}

	/**
	 * 修改验证电话号码 在该零售商下面是否已经存在了
	 */
	@Override
	public boolean checkPhoneExist(String phoneNo, TFocusCustomerEntity t) {
		String sql = null;
		if(Utility.isNotEmpty(t.getAddRetailerId())&&Utility.isEmpty(t.getToGuideId())){//零售商添加的且没有分配导购
			sql = "select count(1) from t_focus_customer where phone_no='"+phoneNo+"' and id <>'"+t.getId()+"' and to_retailer_id ='"
			+t.getToRetailerId()+"' and status ='A'";
		}else{
			String guideId = t.getAddGuideId();
			if(Utility.isEmpty(guideId)){
				guideId = t.getToGuideId();
			}
			sql = "select count(1) from t_focus_customer where phone_no='"+phoneNo+"' and id <>'"+t.getId()+"' and (add_guide_id ='"
			+guideId+"' or to_guide_id = '"+guideId+"') and status ='A'";
		}
		Long num = this.commonDao.getCountForJdbc(sql);
		if(num > 0){
			return true;
		}
		return false;
	}
	
	//零售商 标准版  导入顾客信息
	@Override
	public String doBatchSaveInfo(List<TFocusCustomerEntity> listTFocusCustomerEntitys) throws Exception {
		String msg = null;
		TSUser user =ResourceUtil.getSessionUserName();
		String retailerId = ResourceUtil.getRetailerId();
//		String vipHql = "from TVipLevelEntity where retailerId = '"+retailerId+"' and status = 'A'";
		String storeHql = "from TStoreEntity where retailerId = '"+retailerId+"' ";
		//VIP等级列表
//		List<TVipLevelEntity> vipLvlList = this.commonDao.findByQueryString(vipHql);
		//店铺列表
		List<TStoreEntity> storeList = this.commonDao.findByQueryString(storeHql);
//		String finAbHql = "from TFinAbilityEntity where retailerId = '"+retailerId+"' and status = 'A'";
//		//经济实力列表
//		List<TFinAbilityEntity> finAbList = this.commonDao.findByQueryString(finAbHql);
//		int n =2;
		//手机号set集合
//		HashSet<String> phoneNoSet = new HashSet<String>();
		//批量插入的数据map
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Timestamp createDate = Utility.getCurrentTimestamp();
		for(TFocusCustomerEntity cust : listTFocusCustomerEntitys){
//			n++;
			Boolean exist = false;//存在VIP等级或者经济实力
			//性别
			if(StringUtil.isNotEmpty(cust.getSex())){
				cust.setSex(cust.getSex().replace("null", ""));
				if(cust.getSex().contains("男")){
					cust.setSex("0");
				}else if(cust.getSex().contains("女")){
					cust.setSex("1");
				}
			}
			//获取登记店铺
			if(Utility.isNotEmpty(cust.getPhoneRegShopName())){
				for(TStoreEntity store : storeList){
					if(store.getName().equalsIgnoreCase(cust.getPhoneRegShopName())){
						exist = true;
						cust.setPhoneRegShop(store.getId());
						cust.setPhoneRegShopName(store.getName());
						break;
					}
				}
				if(!exist){
//					msg = "第"+n+"行登记店铺："+cust.getPhoneRegShop()+" 系统中不存在";
					msg = "登记店铺："+cust.getPhoneRegShopName()+" 系统中不存在";
					return msg;
				}
				exist = false;
			}
			//格式化星座和生肖
			this.formatBaseData(cust);
			//批量插入数据的map
			Map<String, Object> map = getDateMap(user,cust,retailerId,createDate);
			datas.add(map);
		}
//		phoneNoSet = null;
		if(datas.size()>0){
			long start = System.currentTimeMillis();
			Map<String,Object> resultMap = DBUtil.insertAll("t_focus_customer", datas);
			if(!(Boolean) resultMap.get("success")){
				throw new SQLException();
			}
			long end = System.currentTimeMillis();
			System.out.println("批量插入共耗时" + (end - start)+"ms");
			try
			{
				//TODO MQ调用插入顾客资料完整度主表和明细
				Map<String,Object> mqMap = new HashMap<String, Object>();
				mqMap.put("retailerId", retailerId);
				mqMap.put("createDate", createDate);
				ResourceBundle rabbitmq = ResourceBundle.getBundle("rabbitmq");
				String key = rabbitmq.getString("insert.custinfo.complete.mq.queue.key");
				rabbitTemplate.convertAndSend(key, mqMap);
			}catch(Exception e){
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			}
//			listTFocusCustomerEntitys.clear();
			datas.clear();
		}
		return msg;
	}
	
	
	
	private Map<String, Object> getDateMap(TSUser user,	TFocusCustomerEntity cust, String retailerId, Timestamp createDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",Utility.getUUID());
		map.put("create_date",createDate);
		map.put("create_by",user.getUserName());
		map.put("status",common.GlobalConstants.STATUS_ACTIVE);
		map.put("name",Utility.isEmpty(cust.getName())?"":cust.getName());
		map.put("sex",cust.getSex());
		map.put("phone_no",cust.getPhoneNo());
		map.put("register_area",cust.getRegisterArea());
//		map.put("birthday",Utility.isEmpty(cust.getBirthday())?null:DateUtils.formatDate(cust.getBirthday(), "yyyy-MM-dd"));
		map.put("birthday",cust.getBirthday());
		map.put("birthday_rank",cust.getBirthdayRank());
		map.put("to_retailer_id",retailerId);
		map.put("add_retailer_id",retailerId);
//		map.put("vip_level_id",cust.getVipLevelId());
		map.put("phone_reg_shop",cust.getPhoneRegShop());
		map.put("phone_reg_shop_name",cust.getPhoneRegShopName());
		map.put("vip_level",cust.getVipLevel());
		map.put("is_use_app","0");
		map.put("source",TFocusCustomerEntity.SOURCE_4);
		map.put("constellation",cust.getConstellation());
		map.put("zodiac",cust.getZodiac());
		map.put("fin_ability_id",cust.getFinAbilityId());
		map.put("fin_ability_name",cust.getFinAbilityName());
		map.put("push_count","0");
		map.put("click_number","0");
		map.put("buy_count","0");
		map.put("un_order","0");
		map.put("remark",cust.getRemark());
		map.put("type","");
		return map;
	}

	/**格式化星座和生肖和年龄段
	 * @param cust
	 */
	@SuppressWarnings("deprecation")
	private void formatBaseData(TFocusCustomerEntity cust) {
		if(Utility.isNotEmpty(cust.getBirthday())){
			int year = cust.getBirthday().getYear()+1900;
			if(year<3000){
				int month = cust.getBirthday().getMonth()+1;
				int day = cust.getBirthday().getDate();
				cust.setConstellation(YearUtil.getConstellation(month, day));//星座
				cust.setZodiac(YearUtil.getZodiac(year));//生肖
				cust.setBirthdayRank(YearUtil.getYearRank(year));//年龄段
			}
		}
	}

	//校验导入的号码是否和系统中的有重复，有则返回重复的号码
	@SuppressWarnings("rawtypes")
	@Override
	public String getExistPhoneNo(List<TFocusCustomerEntity> listTFocusCustomerEntitys) {
		long t = System.currentTimeMillis();
		String rId = ResourceUtil.getRetailerId();
		List existCusts = this.commonDao.findByQueryString("select c.phoneNo from TFocusCustomerEntity c where c.toRetailerId = '"+rId+"' and c.phoneNo is not null");
		if(existCusts.size()>0){
			Set<String> phoneNoSet = new HashSet<String>();
			for(Object cust : existCusts){
				phoneNoSet.add(cust+"");
			}
			for(TFocusCustomerEntity impotCust : listTFocusCustomerEntitys){
				if(phoneNoSet.contains(impotCust.getPhoneNo())){
					return impotCust.getPhoneNo();
				}
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("检查系统是否存在导入的相同号码共耗时" + (t2 - t)+"ms");
		return null;
	}

	/*@Override
	public void updateEmptyPhoneArea() {
		long t = System.currentTimeMillis();
		String rId = ResourceUtil.getRetailerId();
//		rId = "2c92808654c942ba0154cbfbec11003b";//武汉21382提记录更新 共耗时1954ms(1000)  共耗时2176ms(500)
		String sql = "SELECT CONCAT(b.mobile_area1,b.mobile_area2,'(',b.mobile_type,')') area,t.phone_no phone from base_mobile_area b ,"
					+" (SELECT DISTINCT(left(c.phone_no,7)) phone_no FROM  t_focus_customer c where c.phone_no >0 and c.phone_no not REGEXP '^0' "
					+" and  LENGTH(c.phone_no) =11 and c.status = 'A' and c.to_retailer_id = ? and c.phone_area is null) t where b.mobile_number = t.phone_no";
		//获取没有归属地的手机号码对应的归属地列表
		long t1 = System.currentTimeMillis();
		List<Map<String, Object>> list = this.commonDao.findForJdbc(sql, rId);
		long t2 = System.currentTimeMillis();
		int n = list.size();
		System.out.println("查询出号段"+n+"条记录共耗时" + (t2 - t1)+"ms");	
		if(n>0){
			String updateSql = "UPDATE t_focus_customer SET phone_area = case left(phone_no,7)";
			StringBuffer condition = new StringBuffer();
			StringBuffer condition2 = new StringBuffer();
			for (int i = 0; i < n; i++) {
				Map<String, Object> map = list.get(i);
				condition.append(" WHEN '").append(map.get("phone")).append("' THEN '").append(map.get("area")).append("'");
				condition2.append(map.get("phone")).append(",");
				if((i+1)%1000==0){//1000个手机归属地号段更新一次
					condition.append(" END");
					condition2.insert(0, "(").deleteCharAt(condition2.length()-1).append(")");
					long start = System.currentTimeMillis();
					int num = this.updatePhoneAreaByConditions(updateSql,condition,condition2,rId);
					long end = System.currentTimeMillis();
					System.out.println("批量更新1000个号段"+num+"条记录共耗时" + (end - start)+"ms");	
					condition.delete(0, condition.length());
					condition2.delete(0, condition2.length());
				}
			}
			list.clear();
			if(condition.length()>1){//没到1000或者最后一次余数不到1000的号段
				condition.append(" END");
				condition2.insert(0, "(").deleteCharAt(condition2.length()-1).append(")");
				long start = System.currentTimeMillis();
				int num = this.updatePhoneAreaByConditions(updateSql,condition,condition2,rId);
				long end = System.currentTimeMillis();
				System.out.println("批量更新"+num+"条记录共耗时" + (end - start)+"ms");
				condition=null;
				condition2=null;
			}
		}
		long tt = System.currentTimeMillis();
		System.out.println("批量更新共耗时" + (tt - t)+"ms");
	}*/

	/**更新手机归属地
	 * @param updateSql
	 * @param condition
	 * @param condition2
	 * @param rId
	 */
	/*private int updatePhoneAreaByConditions(String updateSql,StringBuffer condition, StringBuffer condition2,String rId) {
		String sql = updateSql+condition.toString()+" where left(phone_no,7) in "+condition2.toString()+" and to_retailer_id = '"+rId+"'";
		return this.commonDao.updateBySqlString(sql);
	}*/
	
	/**
	 * 根据条件查询潜在顾客
	 * @param request
	 * @param rId 零售商id
	 * @return
	 */
	@Override
	public List<TFocusCustomerEntity> getListByConditions(
			HttpServletRequest request, String rId ){
		String createDate_begin = request.getParameter("createDate_begin");
		String createDate_end = request.getParameter("createDate_end");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String phoneNo = request.getParameter("phoneNo");
		String birthdayRank = request.getParameter("birthdayRank");//年龄段
		String constellation = request.getParameter("constellation");//星座
		String zodiac = request.getParameter("zodiac");//生肖
		String registerArea = request.getParameter("registerArea");//登记地区
		String phoneRegShop = request.getParameter("phoneRegShop");//登记店铺
		String phoneArea = request.getParameter("phoneArea");//识别地区
		String vipLevel = request.getParameter("vipLevel");//VIP等级
		String pushCount_begin = request.getParameter("pushCount_begin");//推送次数
		String pushCount_end = request.getParameter("pushCount_end");
		String clickNumber_begin = request.getParameter("clickNumber_begin");//点击次数
		String clickNumber_end = request.getParameter("clickNumber_end");
		String buyCount_begin = request.getParameter("buyCount_begin");//购买次数
		String buyCount_end = request.getParameter("buyCount_end");
		String types = request.getParameter("types");//类型(1无反应顾客,2点击顾客,3交易顾客)  1,2,3,
		String remark = request.getParameter("remark");//备注
		
		StringBuilder sb = new StringBuilder(1024);
		request.getParameter("selectType");
		sb.append(" SELECT")
		.append(" m.id,")
		.append(" m.create_date createDate,")
		.append(" m.name,")
		.append(" m.sex,")
		.append(" m.phone_no phoneNo,")
		.append(" m.birthday_rank birthdayRank,")
		.append(" m.constellation,")
		.append(" m.zodiac,")
		.append(" m.register_area registerArea,")
		.append(" m.phone_reg_shop phoneRegShop,")
		.append(" m.phone_area phoneArea,")
		.append(" m.vip_level vipLevel,")
		.append(" m.push_count pushCount,")
		.append(" m.click_number clickNumber,")
		.append(" m.buy_count buyCount,")
		.append(" m.type,")//顾客类型
		.append(" m.remark")
		.append(" FROM")
		.append(" t_focus_customer m")
		.append(" WHERE m.`status`='A'")
		.append(" and un_order='0'")//未退订
		.append(" and to_retailer_id='").append(rId).append("' and m.phone_no is not null")//所属零售商id
		;
		if(Utility.isNotEmpty(createDate_begin)){
			sb.append(" AND m.create_date >='").append(createDate_begin).append("'");
		}
		if(Utility.isNotEmpty(createDate_end)){
			sb.append(" AND m.create_date <='").append(createDate_end).append("'");
		}
		if(Utility.isNotEmpty(name)){
			sb.append(" AND m.`name` LIKE '%").append(name).append("%'");
		}
		if(Utility.isNotEmpty(sex)){
			sb.append(" AND m.sex = '").append(sex).append("'");
		}
		if(Utility.isNotEmpty(phoneNo)){//手机号码按前几位进行查询
			sb.append(" AND m.phone_no LIKE '").append(phoneNo).append("%'");
		}
		if(Utility.isNotEmpty(birthdayRank)){
			sb.append(" AND m.birthday_rank LIKE '%").append(birthdayRank).append("%'");
		}
		if(Utility.isNotEmpty(constellation)){
			sb.append(" AND m.constellation LIKE '%").append(constellation).append("%'");
		}
		if(Utility.isNotEmpty(zodiac)){
			sb.append(" AND m.zodiac LIKE '%").append(zodiac).append("%'");
		}
		if(Utility.isNotEmpty(registerArea)){
			sb.append(" AND m.register_area LIKE '%").append(registerArea).append("%'");
		}
		if(Utility.isNotEmpty(phoneRegShop)){
			sb.append(" AND m.phone_reg_shop = '").append(phoneRegShop).append("'");
		}
		if(Utility.isNotEmpty(phoneArea)){
			sb.append(" AND m.phone_area LIKE '%").append(phoneArea).append("%'");
		}
		if(Utility.isNotEmpty(vipLevel)){
			sb.append(" AND m.vip_level LIKE '%").append(vipLevel).append("%'");
		}
		if(Utility.isNotEmpty(pushCount_begin)){
			sb.append(" AND m.push_count >=").append(pushCount_begin);
		}
		if(Utility.isNotEmpty(pushCount_end)){
			sb.append(" AND m.push_count <=").append(pushCount_end);
		}
		if(Utility.isNotEmpty(clickNumber_begin)){
			sb.append(" AND m.click_number >=").append(clickNumber_begin);
		}
		if(Utility.isNotEmpty(clickNumber_end)){
			sb.append(" AND m.click_number <=").append(clickNumber_end);
		}
		if(Utility.isNotEmpty(buyCount_begin)){
			sb.append(" AND m.buy_count >=").append(buyCount_begin);
		}
		if(Utility.isNotEmpty(buyCount_end)){
			sb.append(" AND m.buy_count <=").append(buyCount_end);
		}
		if(Utility.isNotEmpty(remark)){
			sb.append(" AND m.remark LIKE '%").append(remark).append("%'");
		}
		if(Utility.isNotEmpty(types)){//顾客类型
			String[] typeArr = types.split(",");
			String typeStr = StringUtil.listNewToStringSlipStr(Arrays.asList(typeArr), ",");
			sb.append(" AND m.type in(").append(typeStr).append(")");
		}
		sb.append(" GROUP BY m.phone_no");//手机号去重
		List<TFocusCustomerEntity> list = commonDao.findObjForJdbc(sb.toString(), TFocusCustomerEntity.class);
		return list;
	}
	
	
	@Override
	public void doCancelGive(String id) {
		 TFocusCustomerEntity tFocusCustomer = commonDao.get(TFocusCustomerEntity.class, id);
		 String retailerId =  tFocusCustomer.getToRetailerId();
		 Long c = this.commonDao.getCountForJdbc("select count(1) from t_focus_customer where status = 'A' and to_retailer_id = '"+
				 retailerId+"' and phone_no = '"+tFocusCustomer.getPhoneNo()+"'");
		 if(c>1){//说明有其他导购录入过，则把该号码变成导购的私有号码，不在有公司这个记录
			 tFocusCustomer.setStatus(common.GlobalConstants.STATUS_INACTIVE);
			 commonDao.updateEntitie(tFocusCustomer);
		 }else{//只有公司录入的这一天，可以重新分配给其他导购
			 tFocusCustomer.setToGuideId(null);
			 tFocusCustomer.setToGuideName(null);
			 tFocusCustomer.setPhoneRegShop(null);
			 tFocusCustomer.setPhoneRegShopName(null);
			 tFocusCustomer.setAddRetailerId(retailerId);
			 tFocusCustomer.setUpdateDate(DateUtils.gettimestamp());
			 commonDao.updateEntitie(tFocusCustomer);
			 //清空导购录入的顾客资料完整度解绑
			 this.commonDao.executeSql("update t_cust_info_complete  set guide_id=null where retailer_id='"+retailerId+"' and customer_id='"+tFocusCustomer.getId()+"'");
		 }
		
	}
	

	
	/**
	 * 导购停用、回收导购录入的顾客资料（包括待发展和交易顾客）
	 * @param guideId
	 */	
	@Override
	public void revokeCustInfo(String retailerId,String guideId) {
		//第一步回收导购自己录入和公司分配的交易顾客和待发展顾客
	   this.commonDao.executeSql("update t_focus_customer set add_guide_id = null,add_guide_name=null,to_guide_id=null,to_guide_name=null,phone_reg_shop=null,phone_reg_shop_name=null,add_retailer_id='"+retailerId
				+"', update_date='"+DateUtils.gettimestamp()+"'  where `status` = 'A' and (add_guide_id='"+ guideId+"' or to_Guide_Id='"+ guideId+"') ");
		//清空导购录入的顾客资料完整度解绑
	   this.commonDao.executeSql("update t_cust_info_complete  set guide_id=null where retailer_id='"+retailerId+"' and guide_id='"+guideId+"'");
	}
	
	/**
	 * 
	 * 批量分配待发展顾客
	 * @param ids
	 * @param guideId
	 */
	@Override
	public void doBatchGive(String ids, String guideId) {
		TPersonEntity guide =  this.findUniqueByProperty(TPersonEntity.class, "userId", guideId);
		String   storeId = guide.getStoreId();
		String   storeName = null;
		if(!Utility.isEmpty(storeId)){
			 List<Map<String, Object>>  storelist  =  commonDao.findForJdbc("select name from t_store where id = ?", storeId);
			 if(!Utility.isEmpty(storelist)){
				 Map<String, Object>  storeMap = storelist.get(0);
				  if(!Utility.isEmpty(storeMap)){
					  storeName = storeMap.get("name")+"";
				  }
			 }
		}
		 for(String id:ids.split(",")){
				 TFocusCustomerEntity tFocusCustomer = commonDao.get(TFocusCustomerEntity.class, id);
				 String retailerId =  tFocusCustomer.getToRetailerId();
				 if(StringUtil.isEmpty(tFocusCustomer.getAddGuideId())){
					 tFocusCustomer.setToGuideId(guideId);
					 tFocusCustomer.setToGuideName(guide.getRealName());
					 tFocusCustomer.setUpdateDate(DateUtils.gettimestamp());
					 tFocusCustomer.setPhoneRegShop(storeId);
					 tFocusCustomer.setPhoneRegShopName(storeName);
					 tFocusCustomer.setAddRetailerId(retailerId);
					 commonDao.updateEntitie(tFocusCustomer);
					 this.commonDao.executeSql("update t_cust_info_complete  set guide_id='"+guideId+"' where retailer_id='" +retailerId+"' and customer_id='"+tFocusCustomer.getId()+"'");
				 }
		 }
	}
	
	
}