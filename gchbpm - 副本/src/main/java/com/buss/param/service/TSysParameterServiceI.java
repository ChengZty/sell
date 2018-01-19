package com.buss.param.service;
import com.buss.param.entity.TSysParameterEntity;
import com.buss.param.entity.vo.TSysParameterVO;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TSysParameterServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSysParameterEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSysParameterEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSysParameterEntity t);
 	
 	/**
 	 * 获取零售商下某个系统参数
 	 * @param paraCode
 	 * @param retailerId
 	 * @return
 	 */
	TSysParameterVO getParameterByCode(String paraCode, String retailerId);
 	/**
 	 * 保存或更新参数值
 	 * @param tSysParameter
 	 */
	void updateSysParameter(TSysParameterEntity tSysParameter);

	/**批量更新或者保存系统参数
	 * @param tSysParameter
	 */
	public void doBatchSaveOrUpdate(TSysParameterEntity tSysParameter) throws Exception;
}
