package com.buss.sms.service.impl;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.MD5Gen;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.redis.service.RedisService;

import com.buss.sms.entity.TSmsSendInfoEntity;
import com.buss.sms.entity.TSmsSubAccountEntity;
import com.buss.sms.service.TSmsSendInfoServiceI;

@Service("tSmsSendInfoService")
@Transactional
public class TSmsSendInfoServiceImpl extends CommonServiceImpl implements TSmsSendInfoServiceI {
	@Resource  
	private RabbitTemplate rabbitTemplate; 
	@Autowired
	private RedisService redisService;
	@Value("${send.msg.mq.queue.key}")
	private String SEND_MSG_KEY;//来源于rabbitmq.properties
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSmsSendInfoEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TSmsSendInfoEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TSmsSendInfoEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSmsSendInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSmsSendInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSmsSendInfoEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSmsSendInfoEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{batch_no}",String.valueOf(t.getBatchNo()));
 		sql  = sql.replace("#{autograph_name}",String.valueOf(t.getAutographName()));
 		sql  = sql.replace("#{content}",String.valueOf(t.getContent()));
 		sql  = sql.replace("#{content_end}",String.valueOf(t.getContentEnd()));
 		sql  = sql.replace("#{content_end2}",String.valueOf(t.getContentEnd2()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{send_status}",String.valueOf(t.getSendStatus()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
	 * 调用查询短信接口（剩余条数）
	 */
 	@Override
	public String balance() {
		String isSuccess = "0";
//		List<TSmsSubAccountEntity> accountList = commonDao.findByProperty(TSmsSubAccountEntity.class, "retailerId", retailerId);
//		TSmsSubAccountEntity account =  commonDao.findUniqueByProperty(TSmsSubAccountEntity.class, "retailerId", retailerId);
//		TSmsSubAccountEntity account = null;
//		if(Utility.isNotEmpty(accountList)){
//			account = accountList.get(0);
//		}
//		if(Utility.isNotEmpty(account)){
			String userCode = ResourceUtil.getConfigByName("userCode");
        	String userPass = ResourceUtil.getConfigByName("userPass");
//        	String productId = ResourceUtil.getConfigByName("productId");
//			String userCode = account.getSmsName();
//			String userPass = account.getSmsPassword();
			String url = ResourceUtil.getConfigByName("balanceUrl");// 短信接口url
			String tkey = DateUtils.formatDate(DateUtils.getCalendar(),
					"yyyyMMddHHmmss");
			String password = MD5Gen.getMD5(MD5Gen.getMD5(userPass) + tkey);
			url += "?username=" + userCode + "&password=" + password + "&tkey="
					+ tkey;
			System.out.println(url);
			HttpGet request = new HttpGet(url);
			HttpResponse response = null;

			try {
				response = HttpClients.createDefault().execute(request);
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			HttpEntity entity = response.getEntity();
			String retSrc = null;
			try {
				retSrc = EntityUtils.toString(entity, "UTF-8");
				System.out.println("response=====================" + retSrc);
				if(Integer.valueOf(retSrc)>0){//大于0的时候返回的是短信剩余条数
					isSuccess = retSrc;
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
//		}
        return isSuccess;
    }
 	
 	@Override
 	public void doBatchDel(String ids) {
 		TSUser user = ResourceUtil.getSessionUserName();
		StringBuffer sql = new StringBuffer("update t_sms_send set status = 'I', update_by = '").append(user.getUserName()).append("',update_name = '")
		.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("' where send_info_id in (");
		StringBuffer infoSql = new StringBuffer("update t_sms_send_info set status = 'I', update_by = '").append(user.getUserName()).append("',update_name = '")
		.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("' where id in (");
		for(String id:ids.split(",")){
			sql.append("'").append(id).append("',");
			infoSql.append("'").append(id).append("',");
		}
		sql = sql.deleteCharAt(sql.length()-1).append(")");
		infoSql = infoSql.deleteCharAt(infoSql.length()-1).append(")");
		commonDao.updateBySqlString(sql.toString());
		commonDao.updateBySqlString(infoSql.toString());
 	}
 	
 	
 	@Override
 	public void doAdd(TSmsSendInfoEntity tSmsSendInfo,int smsNumber,TSmsSubAccountEntity account) {
		tSmsSendInfo.setSendStatus(TSmsSendInfoEntity.SEND_STATUS_1);
		if(TSmsSendInfoEntity.SENTTIMETYPE_0.equals(tSmsSendInfo.getSendTimeType())){
			tSmsSendInfo.setSendTime(Utility.getCurrentTimestamp());//修改发送时间
		}
		//更新发送状态及发送时间
		this.commonDao.updateEntitie(tSmsSendInfo);
		//更新锁定条数状态
		account.setLockingNumber(account.getLockingNumber()+smsNumber);
		this.commonDao.updateEntitie(account);
		//MQ调用
		this.sendMsgMQ(tSmsSendInfo);
		//更新导购端d+链接红点 
		redisService.incr(common.GlobalConstants.GUIDE_D_LINK_VERSION+"_"+tSmsSendInfo.getRetailerId());
 	}
 	
 	@Override
 	public void doUpdate(TSmsSendInfoEntity tSmsSendInfo, int smsNumber) {
 		tSmsSendInfo.setSendStatus(TSmsSendInfoEntity.SEND_STATUS_1);
		if(TSmsSendInfoEntity.SENTTIMETYPE_0.equals(tSmsSendInfo.getSendTimeType())){
			tSmsSendInfo.setSendTime(Utility.getCurrentTimestamp());//修改发送时间
		}
		//短信发送修改
		this.commonDao.updateEntitie(tSmsSendInfo);
		//更新锁定条数状态
		this.commonDao.updateBySqlString("update t_sms_sub_account set locking_number = IFNULL(locking_number,0) + "+smsNumber+" where retailer_id ='"+tSmsSendInfo.getRetailerId()+"'");
		//MQ调用
		this.sendMsgMQ(tSmsSendInfo);
 	}
 	
 	/**MQ通知发送短信
	 * @param tSmsSendInfo
	 * @param smsNumber
	 */
	private void sendMsgMQ(TSmsSendInfoEntity tSmsSendInfo) {
		Map<String,Object> mqMap = new HashMap<String, Object>();
		mqMap.put("retailerId", tSmsSendInfo.getRetailerId());//零售商ID
		mqMap.put("sendInfoId", tSmsSendInfo.getId());//主表ID
		mqMap.put("msgNum", tSmsSendInfo.getMsgNum());

		//将短信条数改变信息添加到map中
		String retailerId = tSmsSendInfo.getRetailerId();
		String sql= "select username from t_s_user where id = ?";
		Map<String, Object> countMap = commonDao.findOneForJdbc(sql.toString(), retailerId);
		String retailerName = countMap.get("username").toString();
		mqMap.put("retailerName", retailerName);
//		ResourceBundle rabbitmq = ResourceBundle.getBundle("rabbitmq");
//		String key = rabbitmq.getString("send.msg.mq.queue.key");
		rabbitTemplate.convertAndSend(SEND_MSG_KEY, mqMap);
		System.out.println("SEND_MSG_KEY------"+SEND_MSG_KEY);
		LogUtil.info("----------------发送短信---------------主表id:"+tSmsSendInfo.getId());
	}
 	
 	
	@Override
	/**服务重启后需要检查并重新启动设置定时发送短信的定时任务*/
 	public void sendMsgByTimer() {
		String hql ="from TSmsSendInfoEntity where sendStatus = ? and sendTimeType = ?  ";//定时发送成功的短信主表信息
 		List<TSmsSendInfoEntity> list = this.commonDao.findHql(hql, TSmsSendInfoEntity.SEND_STATUS_2,TSmsSendInfoEntity.SENTTIMETYPE_1);
 		if(Utility.isNotEmpty(list)){
 			System.out.println(list.size()+"条定时短信记录待发送");
 			for(TSmsSendInfoEntity tSmsSendInfo : list){
 				//TODO
// 				sendMsgMQ(tSmsSendInfo);
 			}
 		}
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
}