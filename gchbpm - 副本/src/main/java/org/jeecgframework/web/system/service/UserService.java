package org.jeecgframework.web.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.web.system.pojo.base.TSUser;
/**
 * 
 * @author  
 *
 */
public interface UserService extends CommonService{

	public TSUser checkUserExits(TSUser user);
	public String getUserRole(TSUser user);
	public void pwdInit(TSUser user, String newPwd);
	/**
	 * 判断这个角色是不是还有用户使用
	 *@Author JueYue
	 *@date   2013-11-12
	 *@param id
	 *@return
	 */
	public int getUsersOfThisRole(String id);
	public List getAutoList(Autocomplete autocomplete, String hqlCondition);
	
	/**
	 * 判断该手机号码是否已注册
	 * @param phone  手机号码
	 * @return
	 */
	boolean isRegister(String phone,String userType);
	
	/**
	 * 判断用户名是否已注册
	 * @param username  用户名
	 * @param userType  用户类型
	 * @return
	 */
	boolean validateUserName(String username, String userType);
	/**
	 * 用户注册
	 * @return
	 */
	void saveUserInfo(TSUser user);
	
	
	/**批量修改合约时间
	 * @param request
	 */
	public void batchSaveActiveTime(HttpServletRequest request);
	
	/**批量修改有效时间
	 * @param request
	 */
	public void batchSaveEffectiveTime(HttpServletRequest request);
}
