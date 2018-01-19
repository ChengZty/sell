package com.buss.user.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.job.entity.TDayAwardDetailEntity;
import com.buss.job.service.TDayAwardDetailServiceI;
import com.buss.user.entity.TPersonAnswersEntity;
import com.buss.user.entity.TPersonDreamEntity;
import com.buss.user.entity.TPersonEntity;
import com.buss.user.entity.TPersonHobbyEntity;
import com.buss.user.service.TPersonServiceI;

@Service("tPersonService")
@Transactional
public class TPersonServiceImpl extends CommonServiceImpl implements TPersonServiceI {
	
	@Autowired
	private TDayAwardDetailServiceI tDayAwardDetailService;
	@Override
	public String saveTPersonAndUser(TPersonEntity tPerson,TSUser users) {
		if(users==null){
			super.save(tPerson);
			return null;
		}
		return "帐号已存在";
	}
	
	/**
	 * tPerson
	 * initPwd 初始密码
	 */
	@Override
	public void saveGuide(TPersonEntity tPerson,String initPwd) {
		TSUser user = new TSUser();
		TSUser nowUser = null;
		String retailerId = null;
		if(Utility.isEmpty(initPwd)){//后台零售商录入导购帐号
			nowUser = ResourceUtil.getSessionUserName();
			retailerId = ResourceUtil.getRetailerId();
			user.setRetailerEdition(ResourceUtil.getRetailerEdition());
		}else{//生成观潮汇测试帐号(2c92808655c8cf010155e2fabca90090) 20171013 改为 查询营运部(2c9280865e14e9ad015e3d16a1261c3c)
//			nowUser = this.commonDao.get(TSUser.class,"2c92808655c8cf010155e2fabca90090");
			nowUser = this.commonDao.get(TSUser.class,"2c9280865e14e9ad015e3d16a1261c3c");
			//测试帐号默认馆(艺术生活馆)
//			tPerson.setStoreId("297e9b815d9d4a06015d9d5df3ab0001");
			tPerson.setStoreId("2c9280865f01d137015f14ffcdb8661b");
			retailerId = nowUser.getId();
			user.setEffectiveTime(DateUtil.addDay(new Date(), 3));//测试帐号有效时间3天
			user.setRetailerEdition(nowUser.getRetailerEdition());
		}
		BeanUtils.copyProperties(tPerson, user);
		user.setUserName(tPerson.getPhoneNo());
		user.setMobilePhone(tPerson.getPhoneNo());
		user.setRealName(tPerson.getRealName());
		user.setProvinceId(nowUser.getProvinceId());
		user.setCityId(nowUser.getCityId());
		user.setArea(nowUser.getArea());
		user.setUserType(common.GlobalConstants.USER_TYPE_03);
		user.setStatus(common.GlobalConstants.STATUS_ACTIVE);
		user.setPassword(PasswordUtil.encrypt(user.getUserName(), Utility.isEmpty(initPwd)?"123456":initPwd, PasswordUtil.getStaticSalt()));
		user.setUserStatus(TSUser.USER_STATUS_WAIT_ACTIVE);//导购激活后才能正常使用
		user.setRetailerId(retailerId);
		
		this.commonDao.save(user);
		tPerson.setUserId(user.getId());
		tPerson.setToRetailerId(retailerId);
		tPerson.setProvinceId(nowUser.getProvinceId());
		tPerson.setCityId(nowUser.getCityId());
		tPerson.setArea(nowUser.getArea());
		tPerson.setHasTags("0");
		tPerson.setCanBind("1");
		tPerson.setUserType(common.GlobalConstants.USER_TYPE_03);
		tPerson.setStatus(common.GlobalConstants.STATUS_ACTIVE);
		this.save(tPerson);
	}
	
	@Override
	public String batchSaveGuides(List<TPersonEntity> listTPersonEntitys) {
		for(TPersonEntity tPerson : listTPersonEntitys){
			saveGuide(tPerson,null);
		}
		return "数据导入成功";
	}
	
	@Override
	public void doChangeArea(HttpServletRequest request) {
		String personId = request.getParameter("personId");
		String userId = request.getParameter("userId");
		String storeId = request.getParameter("storeId");
		//导购帐号信息
		TSUser guide = this.commonDao.get(TSUser.class, userId);
		//导购基础信息
		TPersonEntity person = this.commonDao.get(TPersonEntity.class, personId);
		//要转的馆的零售商帐号信息
		TSUser retailer = this.commonDao.get(TSUser.class, storeId);
		TSUser newGuide = new TSUser();
		TPersonEntity newPerson = new TPersonEntity();
		//导购问题资料
		String hql = "from TPersonAnswersEntity where answer_value is not null and user_id=?";
		List<TPersonAnswersEntity> personAnsList = this.commonDao.findHql(hql, userId);
		//导购梦想
		TPersonDreamEntity personDream = this.commonDao.findUniqueByProperty(TPersonDreamEntity.class, "userId", userId);
		//导购爱好
		TPersonHobbyEntity personHobby = this.commonDao.findUniqueByProperty(TPersonHobbyEntity.class, "userId", userId);
		try {
			MyBeanUtils.copyBeanNotNull2Bean(guide, newGuide);
			MyBeanUtils.copyBeanNotNull2Bean(person, newPerson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//修改之前的帐号信息(用户名和状态和第三方帐号)
		guide.setUserName(this.getNewUserName(guide.getUserName()));
		guide.setUserStatus(TSUser.USER_STATUS_INACTIVE);
		guide.setThirdPartyId(null);
		guide.setThirdPartyQq(null);
		guide.setThirdPartySource(null);
		guide.setThirdPartyWx(null);
		this.commonDao.updateEntitie(guide);
		//修改之前的导购信息（手机号）
		person.setPhoneNo(guide.getUserName());
		this.commonDao.updateEntitie(person);
		//解绑顾客
		String updateSql = "update t_person set to_guide_id = null,to_guide_name=null,is_bind=0 where to_guide_id = '"+userId+"' and user_type = '04' and `status` = 'A'";
		this.commonDao.updateBySqlString(updateSql);
		//保存新的帐号信息
		this.setNewGuideInfo(newGuide,retailer);
		this.commonDao.save(newGuide);
		//保存新的导购基础信息
		this.setNewPersonInfo(newPerson,newGuide);
		this.commonDao.save(newPerson);
		//保存导购问题资料
		for(TPersonAnswersEntity answer :personAnsList){
			TPersonAnswersEntity entity = new TPersonAnswersEntity();
			entity.setAnswerValue(answer.getAnswerValue());
			entity.setQuestionValue(answer.getQuestionValue());
			entity.setInfoType(answer.getInfoType());
			entity.setUserId(newGuide.getId());
			this.commonDao.save(entity);
		}
		if(Utility.isNotEmpty(personDream)){
			//保存导购梦想资料
			TPersonDreamEntity dream = new TPersonDreamEntity();
			dream.setStatus("A");
			dream.setDream(personDream.getDream());
			dream.setUserId(newGuide.getId());
			this.commonDao.save(dream);
		}
		if(Utility.isNotEmpty(personHobby)){
			//保存导购爱好资料
			TPersonHobbyEntity hobby = new TPersonHobbyEntity();
			hobby.setStatus("A");
			hobby.setHobby(personHobby.getHobby());
			hobby.setUserId(newGuide.getId());
			this.commonDao.save(hobby);
		}
	}

	private void setNewPersonInfo(TPersonEntity newPerson, TSUser newGuide) {
		newPerson.setId(null);
		newPerson.setCreateBy(null);
		newPerson.setCreateName(null);
		newPerson.setCreateDate(null);
		newPerson.setArea(newGuide.getArea());
		newPerson.setCityId(newGuide.getCityId());
		newPerson.setProvinceId(newGuide.getProvinceId());
		newPerson.setHasTags("0");
		newPerson.setFans(0);
		newPerson.setMoney(BigDecimal.ZERO);
		newPerson.setQuantity(BigDecimal.ZERO);
		newPerson.setToRetailerId(newGuide.getRetailerId());
		newPerson.setUserId(newGuide.getId());
	}

	private void setNewGuideInfo(TSUser newGuide, TSUser retailer) {
		newGuide.setId(null);
		newGuide.setCreateBy(null);
		newGuide.setCreateName(null);
		newGuide.setCreateDate(null);
		newGuide.setArea(retailer.getArea());
		newGuide.setCityId(retailer.getCityId());
		newGuide.setProvinceId(retailer.getProvinceId());
		newGuide.setImei(null);
		newGuide.setImUserName(null);
		newGuide.setImUserPwd(null);
		newGuide.setRetailerId(retailer.getId());
	}

	private String getNewUserName(String userName) {
		String newUserName = null;
		String sql = "SELECT DISTINCT(username) as username from t_s_user where user_type = '03' and username like '%"+userName+"%' ORDER BY username desc ";
		 List<Map<String, Object>> list = this.commonDao.findForJdbc(sql, null);
		if(Utility.isNotEmpty(list)){
			newUserName = list.get(0).get("username")+"";
			if(newUserName.indexOf("_")!=-1){
				int n = newUserName.lastIndexOf("_");
				String last = newUserName.substring(n+1);
				 if (Character.isDigit(last.charAt(0))){//是数字
					 int index = Integer.valueOf(last)+1;
					 newUserName = newUserName.substring(0,n+1)+index;
				 }else{
					 newUserName = newUserName+"_1";
				 }
			}else{
				newUserName = newUserName+"_1";
			}
		}else{
			newUserName = userName+"_1";
		}
		return newUserName;
	}
	
	/**
	 * 获取零售商下用户person id和真实名称
	 * @param userType 03导购
	 * @return
	 */
	@Override
 	public List<CommonVo> getPersonList(String userType){
		String retailerId = ResourceUtil.getRetailerId();
 		StringBuilder sb = new StringBuilder();
 		sb.append(" select id,real_name name from t_person where status = 'A' ")
 		.append(" and user_type='").append(userType).append("'")
 		.append(" and to_retailer_id='").append(retailerId).append("'");
 		List<CommonVo> list = this.findObjForJdbc(sb.toString(), CommonVo.class);
		return list;
 	}
	
	
	/**
	 * 获取某个店铺下用户person id和真实名称
	 * @param userType 03导购
	 * @param storeId 店铺id
	 * @return
	 */
	@Override
 	public List<CommonVo> getPersonListByStoreId(String userType,String storeId){
 		StringBuilder sb = new StringBuilder();
 		sb.append(" select id,real_name name from t_person where status = 'A' ")
 		.append(" and user_type='").append(userType).append("'")
 		.append(" and store_id='").append(storeId).append("'");
 		List<CommonVo> list = this.findObjForJdbc(sb.toString(), CommonVo.class);
		return list;
 	}
	
	@Override
	public TPersonEntity getPersonByUserId(String userId) {
		List<TPersonEntity> list  = this.commonDao.findHql("from TPersonEntity where userId = ?", userId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public void doUpdateGuide(TPersonEntity tPerson) throws Exception {
		TPersonEntity t = commonDao.get(TPersonEntity.class, tPerson.getId());
		//更新店铺ID
		if(Utility.isNotEmpty(tPerson.getStoreId())&&(!tPerson.getStoreId().equals(t.getStoreId()))){
			commonDao.updateBySqlString("update t_day_sales_record set store_id = '"+tPerson.getStoreId()+"' where guide_id='"+
					tPerson.getUserId()+"' and store_id is null");
		}
		MyBeanUtils.copyBeanNotNull2Bean(tPerson, t);
		this.commonDao.updateEntitie(t);
	}
	
	@Override
	public void doInitGuideAccount(Map<String, Object> map) {
		// 生成随机六位数密码
//		int initPwd = new Random().nextInt(999999);
//		initPwd = initPwd>100000?initPwd:initPwd+100000;
		int initPwd = 123456;
		TPersonEntity tPerson = new TPersonEntity();
		tPerson.setPhoneNo(map.get("phoneNo")+"");
		tPerson.setRealName("测试"+tPerson.getPhoneNo().substring(5));
		saveGuide(tPerson, initPwd+"");
		map.put("pwd", initPwd);
	}

	/*
	  增加or 扣减金币
	 */
	@Override
	public String doGold(String guideId, String retailerId, Integer doType,
			String description, Integer goldCount) {

		String message = doType == 0 ? "扣除金币成功" : "增加金币成功";
		Integer totalGold = 0;
		try {
			String sql = "SELECT gold_num count FROM t_person WHERE STATUS = 'A' AND user_id = '"
					+ guideId + "'";
			if (Utility.isNotEmpty(retailerId)) {
				sql += " AND to_retailer_id='" + retailerId + "' ";
			}
			totalGold = this.getCountForJdbc(sql).intValue();
			TDayAwardDetailEntity awardDetailEntity = new TDayAwardDetailEntity();

			if (doType == 0) {
				// 扣除金币
				if (totalGold - goldCount < 0) {
					message = "金币余额不足，扣除失败";
				} else {
					totalGold -= goldCount;
					String updateGold = "UPDATE t_person SET gold_num = '"
							+ totalGold
							+ "', update_date=NOW()  WHERE STATUS = 'A' AND user_Id = '"
							+ guideId + "'";
					if (Utility.isNotEmpty(retailerId)) {
						updateGold += " AND to_retailer_id='" + retailerId
								+ "' ";
						awardDetailEntity.setRetailerId(retailerId);
					}
					this.executeSql(updateGold);

					// 添加消费明细记录
					awardDetailEntity.setGoldNum(-goldCount);
					awardDetailEntity.setUserId(guideId);
					awardDetailEntity.setStatus("A");
					awardDetailEntity.setTitle(description);
					tDayAwardDetailService.save(awardDetailEntity);
				}
			}
			// 增加金币
			else {
				totalGold += goldCount;
				String updateGold = "UPDATE t_person SET gold_num = '"
						+ totalGold
						+ "', update_date=NOW()  WHERE STATUS = 'A' AND user_Id = '"
						+ guideId + "'";
				if (Utility.isNotEmpty(retailerId)) {
					updateGold += " AND to_retailer_id='" + retailerId + "' ";
					awardDetailEntity.setRetailerId(retailerId);
				}
				this.executeSql(updateGold);

				// 添加消费明细记录
				awardDetailEntity.setGoldNum(goldCount);
				awardDetailEntity.setUserId(guideId);
				awardDetailEntity.setStatus("A");
				awardDetailEntity.setTitle(description);
				tDayAwardDetailService.save(awardDetailEntity);
			}
		} catch (Exception e) {
			message = "操作失败";
			throw new BusinessException(e.getMessage());
		}
		return message;
	}

	/**
	 * 查询导购金币流水记录 
	 */
	@Override
	public void datagridOfGuideGoldDetail(String guideId, String retailerId,
			DataGrid dataGrid ,String queryCreateDateBegin,String queryCreateDateEnd) {
		
		Integer total = 0;
		String sortName = dataGrid.getSort();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			String sql = "SELECT id, title, gold_num as goldNum, create_date as createDate FROM t_day_award_detail WHERE STATUS = 'A' AND user_Id = '"
					+ guideId + "'";
			String totalSql = "SELECT count(1) FROM t_day_award_detail WHERE STATUS = 'A' AND user_Id = '"
					+ guideId + "'";
			if (Utility.isNotEmpty(retailerId)) {
				sql += " AND retailer_id='" + retailerId + "'";
				totalSql += " AND retailer_id='" + retailerId + "'";
			}
			if(StringUtil.isNotEmpty(queryCreateDateBegin)){
				sql +=" and create_date >='" +queryCreateDateBegin +"'";
				totalSql +=" and create_date >='" +queryCreateDateBegin +"'";
			}
			if(StringUtil.isNotEmpty(queryCreateDateEnd)){
				sql +=" and create_date <='" +queryCreateDateEnd +"'";
				totalSql +=" and create_date <='" +queryCreateDateEnd+"'" ;
			}
			if (Utility.isEmpty(sortName)) {
				sql += " ORDER BY update_date desc";
			} else {
				sql += " ORDER BY " + sortName + " " + dataGrid.getOrder();
			}
			total = this.getCountForJdbc(totalSql).intValue();
			if (total > 0) {
				resultList = this.findForJdbc(sql, dataGrid.getPage(),
						dataGrid.getRows());
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		dataGrid.setResults(resultList);
		dataGrid.setTotal(total);
	}
}