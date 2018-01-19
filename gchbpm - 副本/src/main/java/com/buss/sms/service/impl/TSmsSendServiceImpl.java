package com.buss.sms.service.impl;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.ShortUrl;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.buss.sms.entity.TSmsSendEntity;
import com.buss.sms.entity.TSmsSendInfoEntity;
import com.buss.sms.entity.TSmsSendTemplateEntity;
import com.buss.sms.service.TSmsSendServiceI;
import com.buss.user.entity.TFocusCustomerEntity;
import com.buss.user.entity.TFocusCustomerMiddleEntity;
import com.buss.user.entity.vo.TFocusCustomerVo;
import common.DBUtil;
import common.GlobalConstants;

@Service("tSmsSendService")
@Transactional
public class TSmsSendServiceImpl extends CommonServiceImpl implements TSmsSendServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSmsSendEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TSmsSendEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TSmsSendEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSmsSendEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSmsSendEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSmsSendEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSmsSendEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{send_content}",String.valueOf(t.getSendContent()));
 		sql  = sql.replace("#{send_time}",String.valueOf(t.getSendTime()));
 		sql  = sql.replace("#{send_time_type}",String.valueOf(t.getSendTimeType()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public List<TSmsSendEntity> importExcel(String retailerId,String[] arr,MultipartFile file,ImportParams params) throws IOException, Exception{
 		List<TSmsSendEntity> tSmsSends = new ArrayList<TSmsSendEntity>();
 		TSmsSendTemplateEntity tsst = commonDao.get(TSmsSendTemplateEntity.class, arr[2]);
 		String rId = ResourceUtil.getRetailerId();//零售商ID
		TSmsSendInfoEntity smsSend = new TSmsSendInfoEntity();
		smsSend.setId(arr[0]);
		smsSend.setBatchNo(arr[1]);
		smsSend.setAutographName(tsst.getAutographName());
		smsSend.setContent(tsst.getContent());
		smsSend.setStatus(GlobalConstants.STATUS_ACTIVE);
		smsSend.setContentEnd(tsst.getContentEnd());
		smsSend.setContentEnd2(tsst.getContentEnd2());
		smsSend.setRetailerId(retailerId);
		smsSend.setSendTimeType(arr[3]);
		smsSend.setSendStatus("0");//未发送
		if(arr[3].toString().equals("1")){//有时间
			smsSend.setSendTime(DateUtils.parseDate(arr[4],"yyyy-dd-mm HH:mm:ss"));
		}
		/*-------------初始默认值 begin---------------------*/
		smsSend.setClickNumber(0);
		smsSend.setClickRate(BigDecimal.ZERO);
		smsSend.setBuyRate(BigDecimal.ZERO);
		smsSend.setBuySingle(0);
		smsSend.setClickRate(BigDecimal.ZERO);
		smsSend.setPushCount(0);
		smsSend.setReach(0);
		smsSend.setReachRate(BigDecimal.ZERO);
		/*------------------end----------------------------*/
		commonDao.save(smsSend);
		List<TSmsSendEntity> listTSmsSendEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TSmsSendEntity.class,params);
		Set<String> fPhone = new HashSet<String>();
		for (TSmsSendEntity tSmsSendEntity : listTSmsSendEntitys) {
			fPhone.add(tSmsSendEntity.getPhone());
		}
		//长链接集合
		List<String> longUrls = new ArrayList<String>();
		TSmsSendEntity tSmsSend = null;
		for (String str : fPhone) {
			tSmsSend = new TSmsSendEntity();
			tSmsSend.setId(Utility.getUUID());
			tSmsSend.setSendInfoId(arr[0]);
			tSmsSend.setBatchNo(arr[1]);
			tSmsSend.setPhone(str);
			tSmsSend.setLongUrl(tsst.getUrl()+"&sid="+tSmsSend.getId()+"&rId="+rId);
			tSmsSend.setStatus(GlobalConstants.STATUS_ACTIVE);
			tSmsSend.setIsClick("0");
//			tSmsSend.setShortUrl("");
//			tSmsSend.setSendTimeType(arr[3]);
//			if(arr[3].toString().equals("1")){
//				if(Utility.isNotEmpty(arr[4])){
//					tSmsSend.setSendTime(DateUtils.parseDate(arr[4],"yyyy-MM-dd HH:mm:ss"));
//				}
//			}
			tSmsSends.add(tSmsSend);
			
			longUrls.add(tSmsSend.getLongUrl());//长链接
		}
		//生成短链接<短链接参数，长链接>和<长链接，短链接参数>
		Map<String,String> shortUrls = ShortUrl.DoubleShortText(longUrls);
		ResourceBundle env = ResourceBundle.getBundle("env");
		for (TSmsSendEntity tSmsSendEntity : tSmsSends) {
			tSmsSendEntity.setShortUrl(env.getObject("SHORT_URL")+shortUrls.get(tSmsSendEntity.getLongUrl()));
//			tSmsSendEntity.setSendContent("【"+tsst.getAutographName()+"】"+tsst.getContent()+tsst.getContentEnd()+"快戳"+tSmsSendEntity.getShortUrl()+"，"+tsst.getContentEnd2());
		}
		//批量插入
		long start = System.currentTimeMillis();
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		for (TSmsSendEntity tSmsSendEntity : tSmsSends) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id",tSmsSendEntity.getId());
			map.put("send_info_id",tSmsSendEntity.getSendInfoId());
			map.put("batch_no",tSmsSendEntity.getBatchNo());
			map.put("phone",tSmsSendEntity.getPhone());
//			map.put("send_content",tSmsSendEntity.getSendContent());
//			if(TSmsSendInfoEntity.SENTTIMETYPE_1.equals(smsSend.getSendTimeType())){
//				map.put("send_time",tSmsSendEntity.getSendTime());
//			}
//			map.put("send_time_type",tSmsSendEntity.getSendTimeType());
			map.put("long_url",tSmsSendEntity.getLongUrl());
			map.put("short_url",tSmsSendEntity.getShortUrl());
			map.put("is_click",tSmsSendEntity.getIsClick());
			map.put("status",tSmsSendEntity.getStatus());
			datas.add(map);
		}
		Map<String,Object> resultMap = DBUtil.insertAll("t_sms_send", datas);
		int affectRowCount = (Integer) resultMap.get("affectRowCount");
		if(!(Boolean) resultMap.get("success")){
			throw new SQLException();
		}
		/*smsSend.setPushCount(affectRowCount);
		commonDao.updateEntitie(smsSend);*/
		commonDao.updateBySqlString("update t_sms_send_info set push_count = "+affectRowCount+" where id = '"+smsSend.getId()+"'") ;
		System.out.println("共耗时" + (System.currentTimeMillis() - start));		
 		return tSmsSends;
 	}
 	@Override
 	public List<TSmsSendEntity> importSendCustomer(TFocusCustomerVo tfcv,TSmsSendInfoEntity smsSend,Map<String, String> paramsMap) throws Exception{
 		String batchNo = paramsMap.get("batchNo");
 		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT PHONE_NO as phoneNo,cust_id custId,name FROM t_focus_customer_middle WHERE STATUS = '").append(GlobalConstants.STATUS_ACTIVE).append("'")
		.append(" AND batch_no ='").append(batchNo).append("'");
		
		List<TFocusCustomerMiddleEntity> list = commonDao.findObjForJdbc(sql.toString(), TFocusCustomerMiddleEntity.class);
 		sql = null;
		
 		List<TSmsSendEntity> tSmsSends = new ArrayList<TSmsSendEntity>();
 		
		commonDao.saveOrUpdate(smsSend);
		Map<String, String> phoneMap = new HashMap<String, String>();
		Map<String, String> nameMap = new HashMap<String, String>();
		for (TFocusCustomerMiddleEntity t : list) {
			phoneMap.put(t.getPhoneNo(), t.getCustId());
			nameMap.put(t.getCustId(), t.getName());
		}
		//长链接集合
		List<String> longUrls = new ArrayList<String>();
		TSmsSendEntity tSmsSend = null;
		for (Map.Entry<String, String> entry : phoneMap.entrySet()) {
			tSmsSend = new TSmsSendEntity();
			tSmsSend.setId(Utility.getUUID());
			tSmsSend.setSendInfoId(smsSend.getId());
			tSmsSend.setBatchNo(smsSend.getBatchNo());
			tSmsSend.setPhone(entry.getKey());
			tSmsSend.setName(nameMap.get(entry.getValue()));
			tSmsSend.setLongUrl(smsSend.getPushUrl()+"&sid="+tSmsSend.getId()+"&rId="+smsSend.getRetailerId());
			tSmsSend.setStatus(GlobalConstants.STATUS_ACTIVE);
			tSmsSend.setSendStatus("");
			tSmsSend.setReceiveStatus("");
			tSmsSend.setIsClick("0");
			tSmsSend.setRetailerId(smsSend.getRetailerId());
			tSmsSend.setCustId(entry.getValue());
			tSmsSends.add(tSmsSend);
			longUrls.add(tSmsSend.getLongUrl());//长链接
		}
		//生成短链接<短链接参数，长链接>和<长链接，短链接参数>
		Map<String,String> shortUrls = ShortUrl.DoubleShortText(longUrls);
		ResourceBundle env = ResourceBundle.getBundle("env");
		for (TSmsSendEntity tSmsSendEntity : tSmsSends) {
			tSmsSendEntity.setShortUrl(env.getObject("SHORT_URL")+shortUrls.get(tSmsSendEntity.getLongUrl()));
		}
		//批量插入
		long start = System.currentTimeMillis();
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (TSmsSendEntity tSmsSendEntity : tSmsSends) {
			map = new HashMap<String, Object>();
			map.put("id",tSmsSendEntity.getId());
			map.put("send_info_id",tSmsSendEntity.getSendInfoId());
			map.put("batch_no",tSmsSendEntity.getBatchNo());
			map.put("phone",tSmsSendEntity.getPhone());
			map.put("name",tSmsSendEntity.getName());
			map.put("long_url",tSmsSendEntity.getLongUrl());
			map.put("short_url",tSmsSendEntity.getShortUrl());
			map.put("send_status",tSmsSendEntity.getSendStatus());
			map.put("receive_status",tSmsSendEntity.getReceiveStatus());
			map.put("is_click",tSmsSendEntity.getIsClick());
			map.put("retailer_id", tSmsSendEntity.getRetailerId());
			map.put("cust_id", tSmsSendEntity.getCustId());
			map.put("status",tSmsSendEntity.getStatus());
			datas.add(map);
		}
		int affectRowCount = 0;
		if(datas.size()>0){
			Map<String,Object> resultMap = DBUtil.insertAll("t_sms_send", datas);
			affectRowCount = (Integer) resultMap.get("affectRowCount");
			if(!(Boolean) resultMap.get("success")){
				throw new SQLException();
			}
			commonDao.updateBySqlString("update t_sms_send_info set push_count = "+affectRowCount+" where id = '"+smsSend.getId()+"'") ;
		}
		System.out.println("共耗时" + (System.currentTimeMillis() - start));		
 		return tSmsSends;
 	}
 	
 	/**
 	 * @param autographName 签名名称 
 	 * @param content	正文
 	 * @param shortUrl 短链接
 	 * * @param contentEnd 结尾
 	 * @return
 	 */
 	private String setSendContent(String autographName,String content,String shortUrl,String contentEnd){
 		return "【"+autographName+"】"+content+GlobalConstants.SEND_URL_LEFT+shortUrl+GlobalConstants.SEND_URL_RIGHT+contentEnd;
 	}
 	
 	@Override
 	public void doDel(TSmsSendEntity tSmsSend) {
 		tSmsSend.setStatus(GlobalConstants.STATUS_INACTIVE);
 		this.commonDao.updateEntitie(tSmsSend);
 		this.commonDao.updateBySqlString("update t_sms_send_info set push_count = push_count - 1 where id ='"+tSmsSend.getSendInfoId()+"'");
 	}
}