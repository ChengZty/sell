package org.jeecgframework.web.system.controller.core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ListtoMenu;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.RoletoJson;
import org.jeecgframework.core.util.SetListSort;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.SysThemesUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.datatable.DataTables;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleFunction;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSTerritory;
import org.jeecgframework.web.system.pojo.base.TSTradeUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.jeecgframework.web.system.vo.AreaVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.store.service.TStoreAccountServiceI;
import com.buss.visibleGoods.entity.TRetailerRelationEntity;

import cn.redis.service.RedisService;


/**
 * @ClassName: UserController
 * @Description: (用户管理处理类)
 * @author 
 */
@Scope("prototype")
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RedisService redisService;
	@Resource  
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private TStoreAccountServiceI tStoreAccountService;
	
	private String message = null;

	private static Properties properties = new Properties();
	static{
		try {
			// 从类路径下读取属性文件
			properties.load(UserController.class.getClassLoader().getResourceAsStream("rabbitmq.properties"));
		} catch (IOException e) {
			logger.error(e);
		}
	}
	//导购端激活/停用key
	public static final String appCostKey = (String) properties.get("app.month.cost.mq.key");

	/**
	 * 菜单列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "menu")
	public void menu(HttpServletRequest request, HttpServletResponse response) {
		SetListSort sort = new SetListSort();
		TSUser u = ResourceUtil.getSessionUserName();
		// 登陆者的权限
		Set<TSFunction> loginActionlist = new HashSet<TSFunction>();// 已有权限菜单
		List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", u.getId());
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
			if (roleFunctionList.size() > 0) {
				for (TSRoleFunction roleFunction : roleFunctionList) {
					TSFunction function = (TSFunction) roleFunction.getTSFunction();
					loginActionlist.add(function);
				}
			}
		}
		List<TSFunction> bigActionlist = new ArrayList<TSFunction>();// 一级权限菜单
		List<TSFunction> smailActionlist = new ArrayList<TSFunction>();// 二级权限菜单
		if (loginActionlist.size() > 0) {
			for (TSFunction function : loginActionlist) {
				if (function.getFunctionLevel() == 0) {
					bigActionlist.add(function);
				} else if (function.getFunctionLevel() == 1) {
					smailActionlist.add(function);
				}
			}
		}
		// 菜单栏排序
		Collections.sort(bigActionlist, sort);
		Collections.sort(smailActionlist, sort);
		String logString = ListtoMenu.getMenu(bigActionlist, smailActionlist);
		// request.setAttribute("loginMenu",logString);
		try {
			response.getWriter().write(logString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户列表页面跳转[跳转到标签和手工结合的html页面]
	 * 
	 * @return
	 */
	@RequestMapping(params = "userDemo")
	public String userDemo(HttpServletRequest request) {
		// 给部门查询条件中的下拉框准备数据
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
		return "system/user/userList2";
	}
	
	
	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "user")
	public String user(HttpServletRequest request) {
		// 给部门查询条件中的下拉框准备数据
//		List<TSDepart> departList = systemService.getList(TSDepart.class);
		TSUser user = ResourceUtil.getSessionUserName();
//		request.setAttribute("userType", user.getUserType());
//		request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
		if(common.GlobalConstants.USER_TYPE_01.equals(user.getUserType())){
			return "system/user/userList";
		}else{
			return "system/user/userListOfRetailer";
		}
	}
	/**
	 * 零售商列表 设置品牌显示  页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerList")
	public String retailerList(HttpServletRequest request) {
		return "system/user/retailerList";
	}
	
	/**
	 * 零售商列表 设置品牌显示  页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerList2")
	public String retailerList2(HttpServletRequest request) {
		request.setAttribute("userId", request.getParameter("userId"));
		return "system/user/retailerList2";
	}
	
	/**
	 * 零售商列表 设置可见商品  页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerListForGoods")
	public String retailerListForGoods(HttpServletRequest request) {
		return "system/user/retailerListForGoods";
	}
	
	/**
	 * 零售商列表 设置可见商品选择某个零售商的零售商列表  页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerListForRetailer")
	public String retailerListForRetailer(HttpServletRequest request) {
		request.setAttribute("rId", request.getParameter("rId"));//零售商ID
		request.setAttribute("isOther", request.getParameter("isOther"));//零售商ID
		return "system/user/retailerListForRetailer";
	}
	
	/**
	 * 零售商列表 设置可见商品选择某个零售商的云商列表  页面跳转
	 * @return
	 */
	@RequestMapping(params = "cloudListForRetailer")
	public String cloudListForRetailer(HttpServletRequest request) {
		request.setAttribute("rId", request.getParameter("rId"));//零售商ID
		return "system/user/cloudListForRetailer";
	}
	
	/**
	 * 零售商签约管理列表  页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailersSignedManagement")
	public String retailersSignedManagementList(HttpServletRequest request) {
		return "system/user/retailersSignedManagementList";
	}
	
	/**
	 * 观潮汇测试导购帐号管理列表  页面跳转
	 * @return
	 */
	@RequestMapping(params = "guideTestList")
	public String guideTestList(HttpServletRequest request) {
		request.setAttribute("rId", "2c92808655c8cf010155e2fabca90090");//观潮汇
		return "system/user/guideTestList";
	}
	
	/**
	 * 零售商（有导购的）列表 导购排名话术  页面跳转
	 * @return
	 */
	@RequestMapping(params = "hasGuideRetailerList")
	public String hasGuideRetailerList(HttpServletRequest request) {
		return "system/user/hasGuideRetailerList";
	}

	/**
	 * 用户信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "userinfo")
	public String userinfo(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("user", user);
		return "system/user/userinfo";
	}
	
	@RequestMapping(params = "findGuideList")
	public ModelAndView findGuideList(HttpServletRequest request) {
		return new ModelAndView("com/buss/user/guideList");
	}
	
	/**
	 * 零售商列表
	 * @return
	 */
	@RequestMapping(params = "storeUserList")
	public ModelAndView storeUserList(HttpServletRequest request) {
		return new ModelAndView("com/buss/goods/retailers");
	}
	
	
	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(params = "changepassword")
	public String changepassword(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("user", user);
		return "system/user/changepassword";
	}
	
	

	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping(params = "savenewpwd")
	@ResponseBody
	public AjaxJson savenewpwd(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "修改密码成功";
		TSUser user = ResourceUtil.getSessionUserName();
		String password = oConvertUtils.getString(request.getParameter("password"));
		String newpassword = oConvertUtils.getString(request.getParameter("newpassword"));
		String pString = PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt());
		if (!pString.equals(user.getPassword())) {
			j.setMsg("原密码不正确");
			j.setSuccess(false);
			return j;
		} else {
			try {
				user.setPassword(PasswordUtil.encrypt(user.getUserName(), newpassword, PasswordUtil.getStaticSalt()));
				systemService.updateEntitie(user);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 
	 * 修改用户密码
	 * @author Chj
	 */
	
	@RequestMapping(params = "changepasswordforuser")
	public ModelAndView changepasswordforuser(TSUser user, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(user.getId())) {
			user = systemService.flushEntity(TSUser.class, user.getId());
			req.setAttribute("user", user);
			idandname(req, user);
		}
		return new ModelAndView("system/user/adminchangepwd");
	}
	
	
	
	@RequestMapping(params = "savenewpwdforuser")
	@ResponseBody
	public AjaxJson savenewpwdforuser(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String id = oConvertUtils.getString(req.getParameter("id"));
		String password = oConvertUtils.getString(req.getParameter("password"));
		if (StringUtil.isNotEmpty(id)) {
			TSUser users = systemService.flushEntity(TSUser.class,id);
//			System.out.println(users.getUserName());
			users.setPassword(PasswordUtil.encrypt(users.getUserName(), password, PasswordUtil.getStaticSalt()));
			users.setUserStatus(Globals.User_Normal);
			systemService.updateEntitie(users);	
			message = "用户: " + users.getUserName() + "密码重置成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} 
		
		j.setMsg(message);

		return j;
	}
	/**
	 * 锁定，停用，零售商激活账户
	 * @author pu.chen
	 */
	@RequestMapping(params = "lock")
	@ResponseBody
	public AjaxJson lock(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		TSUser user = systemService.flushEntity(TSUser.class, id);
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可操作";
			j.setMsg(message);
			return j;
		}
		if(TSUser.USER_STATUS_DESTROY==user.getUserStatus()){
			message = "已注销用户不可操作";
			j.setMsg(message);
			return j;
		}
		String lockValue=req.getParameter("lockvalue");
		try{
			String type = null;//端口操作类型
			if("0".equals(lockValue)){
				user.setInactiveTime(Utility.getCurrentTimestamp());//停用时间
				message = "用户：" + user.getUserName() + "停用成功!";
				if(TSUser.USER_TYPE_03.equals(user.getUserType())){//导购，清除redis导购数据
					redisService.del("GUIDE_ID_" + user.getId());
				}
				//之前是激活状态
				if(TSUser.USER_STATUS_ACTIVE==user.getUserStatus()&&TSUser.USER_TYPE_03.equals(user.getUserType())){
					type = "0";//停用
					Map<String,String> appCostMap = new HashMap<String, String>();
					appCostMap.put("userId", user.getId());
					appCostMap.put("type", type);
					rabbitTemplate.convertAndSend(appCostKey,appCostMap);
				}
			}else if("1".equals(lockValue)){
				//删除错误的登录信息
				this.userService.updateBySqlString("delete from t_s_user_login where user_name='"+user.getUserName()
						+"' and login_date >'"+DateUtils.date2Str(DateUtils.date_sdf)+" 00:00:00'");
				message = "用户：" + user.getUserName() + "激活成功!";
				//导购端
				if(TSUser.USER_TYPE_03.equals(user.getUserType())&&(TSUser.USER_STATUS_INACTIVE==user.getUserStatus()
						||TSUser.USER_STATUS_WAIT_ACTIVE==user.getUserStatus())){
					type = "1";//激活
					Map<String,String> appCostMap = new HashMap<String, String>();
					appCostMap.put("userId", user.getId());
					appCostMap.put("type", type);
					//之前是待激活状态（首次激活有7天免费使用，即激活日期推迟七天）
					if(TSUser.USER_STATUS_WAIT_ACTIVE==user.getUserStatus()){
						appCostMap.put("freeTry", "1");
					}
					rabbitTemplate.convertAndSend(appCostKey,appCostMap);
				}
			}else if("2".equals(lockValue)){
				message = "用户：" + user.getUserName() + "锁定成功!";
			}
			user.setUserStatus(new Short(lockValue));
			userService.updateEntitie(user);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			message = "操作失败!";
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 后台激活账户
	 * @author pu.chen
	 */
	@RequestMapping(params = "unlock")
	@ResponseBody
	public AjaxJson unlock(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		TSUser user = systemService.flushEntity(TSUser.class, id);
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可操作";
			j.setMsg(message);
			return j;
		}
		try{
			message = "用户：" + user.getUserName() + "激活成功!";
			if(TSUser.USER_STATUS_DESTROY==user.getUserStatus()){//已注销的用户
				//查询同类型的用户名非注销状态的个数
				String sql = "select count(1) from t_s_user where status = 'A' and username = '"+user.getUserName()
						+"' and user_type = '"+user.getUserType()+"' and user_status <> '"+TSUser.USER_STATUS_DESTROY+"'";
				Long ct = this.systemService.getCountForJdbc(sql);
				if(ct>0){//注销后注册了新的同名用户且用户类型相同
					message = "已存在同名用户：" + user.getUserName() + "，请联系平台管理员确认!";
				}else{
					user.setUserStatus(TSUser.USER_STATUS_ACTIVE);
					userService.updateEntitie(user);
				}
			}else{
				user.setUserStatus(TSUser.USER_STATUS_ACTIVE);
				userService.updateEntitie(user);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "操作失败!";
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 注销账户
	 * @author pu.chen
	 */
	@RequestMapping(params = "destroy")
	@ResponseBody
	public AjaxJson destroy(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		TSUser user = systemService.flushEntity(TSUser.class, id);
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可操作";
			j.setMsg(message);
			return j;
		}
		try{
			if(TSUser.USER_STATUS_DESTROY!=user.getUserStatus()){
				message = "用户：" + user.getUserName() + "注销成功!";
				user.setUserStatus(TSUser.USER_STATUS_DESTROY);
				userService.updateEntitie(user);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}else{
				message = "用户：" + user.getUserName() + "已注销!";
			}
		}catch(Exception e){
			message = "操作失败!";
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 刷新所属区域
	 */
	@RequestMapping(params = "updateArea")
	@ResponseBody
	public AjaxJson updateArea(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try{
			message = "刷新所属区域成功";
			//零售商
			TSUser user = ResourceUtil.getSessionUserName();
			if(TSUser.USER_TYPE_05.equals(user.getUserType())){
				user = this.systemService.get(TSUser.class, user.getRetailerId());
			}
			String sql = "UPDATE t_s_user set province_id = '"+user.getProvinceId()+"',city_id = '"+user.getCityId()
					+"',area = '"+user.getArea()+"' where retailer_id = '"+user.getId()+"'";
			this.systemService.updateBySqlString(sql);
			String sql2 = "UPDATE t_person set province_id = '"+user.getProvinceId()+"',city_id = '"+user.getCityId()
			+"',area = '"+user.getArea()+"' where to_retailer_id = '"+user.getId()+"'";
			this.systemService.updateBySqlString(sql2);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			message = "操作失败!";
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 得到角色列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "role")
	@ResponseBody
	public List<ComboBox> role(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
		String id = request.getParameter("id");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		List<TSRole> roles = new ArrayList<TSRole>();
		if (StringUtil.isNotEmpty(id)) {
			List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", id);
			if (roleUser.size() > 0) {
				for (TSRoleUser ru : roleUser) {
					roles.add(ru.getTSRole());
				}
			}
		}
		List<TSRole> roleList = systemService.getList(TSRole.class);
		comboBoxs = TagUtil.getComboBox(roleList, roles, comboBox);
		return comboBoxs;
	}

	/**
	 * 得到部门列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "depart")
	@ResponseBody
	public List<ComboBox> depart(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
		String id = request.getParameter("id");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		List<TSDepart> departs = new ArrayList<TSDepart>();
		if (StringUtil.isNotEmpty(id)) {
//			TSUser user = systemService.get(TSUser.class, id);
//			if (user.getTSDepart() != null) {
//				TSDepart depart = systemService.get(TSDepart.class, user.getTSDepart().getId());
//				departs.add(depart);
//			}
            // todo zhanggm 获取指定用户的组织机构列表
            List<TSDepart[]> resultList = systemService.findHql("from TSDepart d,TSUserOrg uo where d.id=uo.orgId and uo.id=?", id);
            for (TSDepart[] departArr : resultList) {
                departs.add(departArr[0]);
            }
        }
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		comboBoxs = TagUtil.getComboBox(departList, departs, comboBox);
		return comboBoxs;
	}
	

	/**
	 * easyui AJAX请求零售商列表数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "retailerListDatagrid")
	public void retailerListDatagrid(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user, request.getParameterMap());
		try{
			//自定义追加查询条件
			cq.eq("userStatus", org.jeecgframework.web.system.pojo.base.TSUser.USER_STATUS_ACTIVE);
			cq.eq("userType", common.GlobalConstants.USER_TYPE_02);
			cq.eq("retailerType", TSUser.RETAILER_TYPE_REAL);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyuiAJAX用户列表请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
        //查询条件组装器
        TSUser nowUser = ResourceUtil.getSessionUserName();
        if(common.GlobalConstants.USER_TYPE_02.equals(nowUser.getUserType())){
        	cq.eq("retailerId", nowUser.getId());
        }else if(common.GlobalConstants.USER_TYPE_05.equals(nowUser.getUserType())){
        	cq.eq("retailerId", nowUser.getRetailerId());
        }
        cq.isNotNull("userName");
//        Short[] userstate = new Short[]{Globals.User_Normal, Globals.User_ADMIN, Globals.User_Forbidden};
//        cq.in("userStatus", userstate);
//        String orgIds = request.getParameter("orgIds");
//        List<String> orgIdList = extractIdListByComma(orgIds);
//        // 获取 当前组织机构的用户信息
//        if (!CollectionUtils.isEmpty(orgIdList)) {
//            CriteriaQuery subCq = new CriteriaQuery(TSUserOrg.class);
//            subCq.setProjection(Property.forName("tsUser.id"));
//            subCq.in("tsDepart.id", orgIdList.toArray());
//            subCq.add();
//
//            cq.add(Property.forName("id").in(subCq.getDetachedCriteria()));
//        }

        cq.add();
        this.systemService.getDataGridReturn(cq, true);
//        List<TSUser> cfeList = new ArrayList<TSUser>();
//        for (Object o : dataGrid.getResults()) {
//            if (o instanceof TSUser) {
//                TSUser cfe = (TSUser) o;
//                if (cfe.getId() != null && !"".equals(cfe.getId())) {
//                    List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", cfe.getId());
//                    if (roleUser.size() > 0) {
//                        String roleName = "";
//                        for (TSRoleUser ru : roleUser) {
//                            roleName += ru.getTSRole().getRoleName() + ",";
//                        }
//                        roleName = roleName.substring(0, roleName.length() - 1);
//                        cfe.setUserKey(roleName);
//                    }
//                }
//                cfeList.add(cfe);
//            }
//        }
        TagUtil.datagrid(response, dataGrid);
    }

	
	/**
	 * easyuiAJAX 零售商列表(有货的零售商)请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridOfRetailList")
	public void datagridOfRetailList(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
        //查询条件组装器
        	cq.eq("userType", common.GlobalConstants.USER_TYPE_02);
        	cq.notEq("retailerType", TSUser.RETAILER_TYPE_POPLE);
        	cq.eq("userStatus",TSUser.USER_STATUS_ACTIVE);
        	cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
	
	
	/**
	 * easyuiAJAX 零售商列表(有导购的零售商)请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "hasGuideRetailerDatagrid")
	public void hasGuideRetailerDatagrid(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
		//查询条件组装器
		cq.eq("userType", common.GlobalConstants.USER_TYPE_02);
		cq.notEq("retailerType", TSUser.RETAILER_TYPE_GOODS);
		cq.eq("userStatus",TSUser.USER_STATUS_ACTIVE);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	
	
	/**
	 * 
	 * 零售商帐号列表
	 * easyuiAJAX用户列表请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "storeDatagrid")
	public void storeDatagrid(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
        cq.eq("userType", common.GlobalConstants.USER_TYPE_02);
        String retailerType = request.getParameter("retailer_Type");
        if(Utility.isNotEmpty(retailerType)){
        	cq.eq("retailerType", retailerType);
        }
        cq.eq("userStatus",TSUser.USER_STATUS_ACTIVE);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
	
	
	/**
	 * 
	 * 零售商帐号列表
	 * easyuiAJAX用户列表请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "retaileRelationDatagrid")
	public void retaileRelationDatagrid(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		Map<String, String[]> map = request.getParameterMap();
		cq.eq("userType", common.GlobalConstants.USER_TYPE_02);
		String retailerType = request.getParameter("retailer_Type");
		String rId = request.getParameter("rId");//零售商ID
		String hql ="from TRetailerRelationEntity where retailerId = ? and otherRetailerType = ? ";
		List <TRetailerRelationEntity> list = this.systemService.findHql(hql, rId,retailerType);
		StringBuffer condtions = new StringBuffer(" 1=1 ");
		if(Utility.isNotEmpty(list)){
			condtions.append(" and id not in (");
			for(TRetailerRelationEntity entity : list){
				condtions.append("'").append(entity.getOtherRetailerId()).append("',");
			}
			condtions = condtions.deleteCharAt(condtions.length()-1).append(")");
		}
		if(Utility.isNotEmpty(retailerType)){
			cq.eq("retailerType", retailerType);
		}
		String isOther = request.getParameter("isOther");//其他零售商
		if("1".equals(isOther)){
			cq.notEq("id", rId);
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user, map,condtions.toString());
		cq.eq("userStatus",TSUser.USER_STATUS_ACTIVE);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyuiAJAX 零售商列表(有导购的零售商)请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "guideDatagrid")
	public void guideDatagrid(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
		//查询条件组装器
		cq.eq("userType", common.GlobalConstants.USER_TYPE_03);
		Short[] userStatusArr = {TSUser.USER_STATUS_ACTIVE,TSUser.USER_STATUS_WAIT_ACTIVE};
		cq.in("userStatus", userStatusArr);
//		cq.eq("userStatus",TSUser.USER_STATUS_ACTIVE);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 用户信息录入和更新
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSUser user, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可删除";
			j.setMsg(message);
			return j;
		}
		user = systemService.flushEntity(TSUser.class, user.getId());
		List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		if (!user.getUserStatus().equals(Globals.User_ADMIN)) {
			if (roleUser.size()>0) {
				// 删除用户时先删除用户和角色关系表
				delRoleUser(user);
//                systemService.executeSql("delete from t_s_user_org where user_id=?", user.getId()); // 删除 用户-机构 数据
				user.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				user.setUpdateDate(DateUtils.gettimestamp());
                userService.updateEntitie(user);
//                userService.delete(user);
				message = "用户：" + user.getUserName() + "删除成功";
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			} else {
				userService.delete(user);
				message = "用户：" + user.getUserName() + "删除成功";
			}
		} else {
			message = "超级管理员不可删除";
		}

		j.setMsg(message);
		return j;
	}

	public void delRoleUser(TSUser user) {
		// 同步删除用户角色关联表
		List<TSRoleUser> roleUserList = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		if (roleUserList.size() >= 1) {
			for (TSRoleUser tRoleUser : roleUserList) {
				systemService.delete(tRoleUser);
			}
		}
	}
	/**
	 * 检查用户名
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "checkUser")
	@ResponseBody
	public ValidForm checkUser(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String userName=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		List<TSUser> roles=systemService.findByProperty(TSUser.class,"userName",userName);
		if(roles.size()>0&&!code.equals(userName))
		{
			v.setInfo("用户名已存在");
			v.setStatus("n");
		}
		return v;
	}

	/**
	 * 后台用户录入
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "saveUser")
	@ResponseBody
	public AjaxJson saveUser(HttpServletRequest req, TSUser user) {
		AjaxJson j = new AjaxJson();
		// 得到用户的角色
		String roleid = oConvertUtils.getString(req.getParameter("roleid"));
		// 得到用户的行业信息
		String tradeId = oConvertUtils.getString(req.getParameter("tradeId"));
		String password = oConvertUtils.getString(req.getParameter("password"));
		if (StringUtil.isNotEmpty(user.getId())) {//更新
			TSUser users = systemService.flushEntity(TSUser.class, user.getId());
//			users.setEmail(user.getEmail());
//			users.setOfficePhone(user.getOfficePhone());
			users.setMobilePhone(user.getMobilePhone());
			if(TSUser.USER_TYPE_03.equals(users.getUserType())&&Utility.isNotEmpty(user.getRealName())&&!user.getRealName().equals(users.getRealName())){
				//更新导购真实姓名
				this.systemService.updateBySqlString("update t_person set real_name = '"+user.getRealName()+"' where user_id = '"+user.getId()+"'");
			}
			users.setRealName(user.getRealName());
//			users.setUserStatus(Globals.User_Normal);
//			users.setUserType(user.getUserType());
			users.setRetailerEdition(user.getRetailerEdition());
			users.setProvinceId(user.getProvinceId());
			users.setCityId(user.getCityId());
			users.setArea(user.getArea());
			systemService.updateEntitie(users);
			List<TSRoleUser> ru = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			systemService.deleteAllEntitie(ru);
			message = "用户: " + users.getUserName() + "更新成功";
			if (StringUtil.isNotEmpty(roleid)) {
				saveRoleUser(users, roleid);
			}
			//更新用户的行业类型
			if (StringUtil.isNotEmpty(tradeId)) {
				saveTradeUser(users, tradeId);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {//新增
			Long count = systemService.getCountForJdbc("select count(1) from t_s_user where status='A' and user_type in('01','02','05') and username ='"+user.getUserName()+"'");
//			TSUser users = systemService.findUniqueByProperty(TSUser.class, "userName",user.getUserName());
			if (count>0) {
				message = "用户: " + user.getUserName() + "已经存在";
			} else {
				user.setPassword(PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt()));
				user.setUserStatus(TSUser.USER_STATUS_ACTIVE);
//				user.setActiveTime(Utility.getCurrentTimestamp());//激活时间
				TSUser nowUser = ResourceUtil.getSessionUserName();
				if(common.GlobalConstants.USER_TYPE_02.equals(nowUser.getUserType())){
					user.setRetailerId(nowUser.getId());
					user.setRetailerEdition(nowUser.getRetailerEdition());//零售商版本
					user.setRetailerType(nowUser.getRetailerType());
					user.setMobilePhone(user.getUserName());//录入的导购用户名就是手机号
				}else if(common.GlobalConstants.USER_TYPE_05.equals(nowUser.getUserType())){
					user.setRetailerId(nowUser.getRetailerId());
					user.setRetailerEdition(nowUser.getRetailerEdition());//零售商版本
					user.setRetailerType(nowUser.getRetailerType());
					user.setMobilePhone(user.getUserName());//录入的导购用户名就是手机号
				}
				systemService.save(user);
                // todo zhanggm 保存多个组织机构
//                saveUserOrgList(req, user);
				message = "用户: " + user.getUserName() + "添加成功";
				if (StringUtil.isNotEmpty(roleid)) {
					saveRoleUser(user, roleid);
				}
				//保存用户的行业类型
				if (StringUtil.isNotEmpty(tradeId)) {
					saveTradeUser(user, tradeId);
				}
				if(TSUser.USER_TYPE_02.equals(user.getUserType())){//录入的零售商，则保存对应的账户表,短信账户表
					this.tStoreAccountService.initStoreAccountInfo(user);
				}
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}

		}
		j.setMsg(message);

		return j;
	}
	
    protected void saveRoleUser(TSUser user, String roleidstr) {
		String[] roleids = roleidstr.split(",");
		for (int i = 0; i < roleids.length; i++) {
			TSRoleUser rUser = new TSRoleUser();
			TSRole role = systemService.flushEntity(TSRole.class, roleids[i]);
			rUser.setTSRole(role);
			rUser.setTSUser(user);
			systemService.save(rUser);

		}
	}
    
    //保存用户的行业类型
    protected void saveTradeUser(TSUser user, String tradeIDs) {
		String[] tradeids = tradeIDs.split(",");
		List<TSTradeUser> tradeUsers = systemService.findByProperty(TSTradeUser.class, "userId", user.getId());
		
		//判断是否要删除
		Boolean flag = true;
		List<String> hasTrade = new ArrayList<String>();
		for (TSTradeUser tsTradeUser : tradeUsers) {
			String tradeId = tsTradeUser.getTradeId();
			for (int i = 0; i < tradeids.length; i++) {
				if(tradeId.equals(tradeids[i])){
					flag = false;
				}
			}
			if(flag){
				//将TSTradeUser 标记为删除
				tsTradeUser.setStatus("I");
				systemService.updateEntitie(tsTradeUser);
			}else{
				hasTrade.add(tradeId);
			}
			flag = true;
		}
		
		for (int i = 0; i < tradeids.length; i++) {
			for (String hasTradeId : hasTrade) {
				if(hasTradeId.equals(tradeids[i])){
					flag = false;
				}
			}
			if(flag){
				//创建新用户行业连接数据
				TSTradeUser tradeUser = new TSTradeUser();
				TSCategoryEntity tsCategory = systemService.flushEntity(TSCategoryEntity.class, tradeids[i]);
				tradeUser.setUserId(user.getId());
				tradeUser.setUserName(user.getUserName());
				tradeUser.setTradeId(tsCategory.getId());
				tradeUser.setTradeName(tsCategory.getName());
				tradeUser.setStatus("A");
				systemService.save(tradeUser);
			}
			flag = true;
		}
	}

	/**
	 * 用户选择角色跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "roles")
	public ModelAndView roles(HttpServletRequest request) {
		//--author：zhoujf-----start----date:20150531--------for: 编辑用户，选择角色,弹出的角色列表页面，默认没选中
		ModelAndView mv = new ModelAndView("system/user/users");
		String ids = oConvertUtils.getString(request.getParameter("ids"));
		mv.addObject("ids", ids);
		return mv;
	}

	/**
	 * 角色显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridRole")
	public void datagridRole(TSRole tsRole, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSRole.class, dataGrid);
		//查询条件组装器
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){//零售商
			cq.eq("retailerId", retailerId);
		}else{//后台
			cq.isNull("retailerId");//过滤零售商自己定义的角色
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsRole);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 导购列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridGuide")
	public void datagridGuide(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		//查询条件组装器
		String retailerId = ResourceUtil.getRetailerId();
		cq.eq("retailerId", retailerId);
		cq.eq("userType", common.GlobalConstants.USER_TYPE_03);
		String tStoreId = request.getParameter("t_store_id");//店铺ID
		if(Utility.isNotEmpty(tStoreId)){
			cq.eq("storeId", tStoreId);
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * web用户列表 平台发送消息页面选择用户
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridOfWeb")
	public void datagridOfWeb(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		//查询条件组装器
		String[] str = {TSUser.USER_TYPE_01,TSUser.USER_TYPE_02,TSUser.USER_TYPE_05};//web用户类型，平台，零售商，零售商员工
		cq.in("userType", str);
		cq.eq("userStatus", TSUser.USER_STATUS_ACTIVE);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	

	/**
	 * 新增
	 * @param request
	 * @param user
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSUser user, HttpServletRequest req) {
		List<AreaVo> list = getAreaList();
		req.setAttribute("areaList", list);
        TSUser userNow = ResourceUtil.getSessionUserName();
        if(common.GlobalConstants.USER_TYPE_01.equals(userNow.getUserType())){
			return new ModelAndView("system/user/user");
		}else{
			return new ModelAndView("system/user/user-add");
		}
        
	}
	
	
	/**
	 * 修改
	 * @param request
	 * @param user
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSUser user, HttpServletRequest req) {
//		List<TSDepart> departList = new ArrayList<TSDepart>();
//		String departid = oConvertUtils.getString(req.getParameter("departid"));
//		if(!StringUtil.isEmpty(departid)){
//			departList.add((TSDepart)systemService.getEntity(TSDepart.class,departid));
//		}else {
//			departList.addAll((List)systemService.getList(TSDepart.class));
//		}
//		req.setAttribute("departList", departList);
//        List<String> orgIdList = new ArrayList<String>();
		if (StringUtil.isNotEmpty(user.getId())) {
			user = systemService.flushEntity(TSUser.class, user.getId());
			req.setAttribute("user", user);
			idandname(req, user);

			List<AreaVo> list = getAreaList();
			req.setAttribute("areaList", list);
			if(StringUtil.isNotEmpty(user.getProvinceId())){
				String id = user.getProvinceId();
				List<AreaVo> cityList = new ArrayList<AreaVo>();
				TSTerritory territory = systemService.get(TSTerritory.class, id);//父地域
				List<TSTerritory> territoryList = systemService.findByProperty(TSTerritory.class, "TSTerritory", territory);
				if(territoryList!=null){
					for(TSTerritory ter:territoryList){
						AreaVo vo = new AreaVo();
						vo.setAreaId(ter.getId());
						vo.setAreaName(ter.getTerritoryName());
						cityList.add(vo);
					}
					req.setAttribute("cityList", cityList);
				}
			}
			
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){
				String sql = "select trade_id tradeId,trade_name tradeName from t_s_trade_user where status='A' and user_id='"+user.getId()+"'";
				List<Map<String, Object>> resultList =  systemService.findForJdbc(sql);
				String tradeId="";
				String tradeName="";
				if(resultList.size() > 0){
					for (Map<String, Object> map : resultList) {
						tradeId +=map.get("tradeId")+",";
						tradeName +=map.get("tradeName")+",";
					}
					tradeId = tradeId.substring(0, tradeId.length()-1);
					tradeName = tradeName.substring(0, tradeName.length()-1);
				}
				req.setAttribute("tradeId", tradeId);
				req.setAttribute("tradeName", tradeName);
				
			}
//            orgIdList = systemService.findHql("select d.id from TSDepart d,TSUserOrg uo where d.id=uo.tsDepart.id and uo.tsUser.id=?", new String[]{user.getId()});
		}
//        req.setAttribute("orgIdList", JSON.toJSON(orgIdList));
        TSUser userNow = ResourceUtil.getSessionUserName();
        if(common.GlobalConstants.USER_TYPE_01.equals(userNow.getUserType())){
        	return new ModelAndView("system/user/user-update");
        }else{
        	return new ModelAndView("system/user/user-update2");
        	
        }
	}
	
	/**获取所有省的ID和name
	 * @return
	 */
	public List<AreaVo> getAreaList(){
		List<AreaVo> list = new ArrayList<AreaVo>();
		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
		cq.eq("territoryLevel", (short)2);
		cq.add();
		List<TSTerritory> territoryList = systemService.getListByCriteriaQuery(cq, false);
		if(territoryList!=null){
			AreaVo blankVo = new AreaVo();
			blankVo.setAreaId("");
			blankVo.setAreaName("--请选择--");
			list.add(blankVo);
			for(TSTerritory ter:territoryList){
				AreaVo vo = new AreaVo();
				vo.setAreaId(ter.getId());
				vo.setAreaName(ter.getTerritoryName());
				list.add(vo);
			}
		}
		return list;
	}
	
    /**
     * 用户的登录后的组织机构选择页面
     * @param request request
     * @return 用户选择组织机构页面
     */
	@RequestMapping(params = "userOrgSelect")
	public ModelAndView userOrgSelect(HttpServletRequest request) {
		List<TSDepart> orgList = new ArrayList<TSDepart>();
		String userId = oConvertUtils.getString(request.getParameter("userId"));

        List<Object[]> orgArrList = systemService.findHql("from TSDepart d,TSUserOrg uo where d.id=uo.tsDepart.id and uo.tsUser.id=?", new String[]{userId});
        for (Object[] departs : orgArrList) {
            orgList.add((TSDepart) departs[0]);
        }
        request.setAttribute("orgList", orgList);

        TSUser user = systemService.flushEntity(TSUser.class, userId);
        request.setAttribute("user", user);

		return new ModelAndView("system/user/userOrgSelect");
    }

	/**获取角色名称
	 * @param req
	 * @param user
	 */
	public void idandname(HttpServletRequest req, TSUser user) {
		List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		String roleId = "";
		String roleName = "";
		if (roleUsers.size() > 0) {
			for (TSRoleUser tRoleUser : roleUsers) {
				roleId += tRoleUser.getTSRole().getId() + ",";
				roleName += tRoleUser.getTSRole().getRoleName() + ",";
			}
		}
		req.setAttribute("id", roleId);
		req.setAttribute("roleName", roleName);

	}

	/**
	 * 零售商选择行业类型跳转页面
	 */
	@RequestMapping(params = "goTradeList")
	public ModelAndView goTradeList(HttpServletRequest request) {
		//--author：zhoujf-----start----date:20150531--------for: 编辑用户，选择角色,弹出的角色列表页面，默认没选中
		ModelAndView mv = new ModelAndView("system/user/tradeList");
		String ids = oConvertUtils.getString(request.getParameter("ids"));
		mv.addObject("ids", ids);
		return mv;
	}
	/**
	 * 角色显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridTrade")
	public void datagridTrade(TSTradeUser tradeUser, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TSRole.class, dataGrid);
		//查询条件组装器
		/*String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){//零售商
			cq.eq("retailerId", retailerId);
		}else{//后台
			cq.isNull("retailerId");//过滤零售商自己定义的角色
			
		}*/
		String sql = "";
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){  //零售商
			sql = "select trade_id id,trade_name tradeName from t_s_trade_user where status='A' and user_id='"+retailerId+"'";
		}else{  //后台
//			sql = "select id,name tradeName from t_s_category where status='A' and level='2' and create_by='admin'";
			sql = "select id,name tradeName from t_s_category where status='A' and level='2' ";
		}
		
		
		
		List<Map<String, Object>> resultList =  systemService.findForJdbc(sql);
		dataGrid.setResults(resultList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 根据部门和角色选择用户跳转页面
	 */
	@RequestMapping(params = "choose")
	public String choose(HttpServletRequest request) {
		List<TSRole> roles = systemService.loadAll(TSRole.class);
		request.setAttribute("roleList", roles);
		return "system/membership/checkuser";
	}

	/**
	 * 部门和角色选择用户的panel跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "chooseUser")
	public String chooseUser(HttpServletRequest request) {
		String departid = request.getParameter("departid");
		String roleid = request.getParameter("roleid");
		request.setAttribute("roleid", roleid);
		request.setAttribute("departid", departid);
		return "system/membership/userlist";
	}

	/**
	 * 部门和角色选择用户的用户显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridUser")
	public void datagridUser(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String departid = request.getParameter("departid");
		String roleid = request.getParameter("roleid");
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		if (departid.length() > 0) {
			cq.eq("TDepart.departid", oConvertUtils.getInt(departid, 0));
			cq.add();
		}
		String userid = "";
		if (roleid.length() > 0) {
			List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TRole.roleid", oConvertUtils.getInt(roleid, 0));
			if (roleUsers.size() > 0) {
				for (TSRoleUser tRoleUser : roleUsers) {
					userid += tRoleUser.getTSUser().getId() + ",";
				}
			}
			cq.in("userid", oConvertUtils.getInts(userid.split(",")));
			cq.add();
		}
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 根据部门和角色选择用户跳转页面
	 */
	@RequestMapping(params = "roleDepart")
	public String roleDepart(HttpServletRequest request) {
		List<TSRole> roles = systemService.loadAll(TSRole.class);
		request.setAttribute("roleList", roles);
		return "system/membership/roledepart";
	}

	/**
	 * 部门和角色选择用户的panel跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "chooseDepart")
	public ModelAndView chooseDepart(HttpServletRequest request) {
		String nodeid = request.getParameter("nodeid");
		ModelAndView modelAndView = null;
		if (nodeid.equals("role")) {
			modelAndView = new ModelAndView("system/membership/users");
		} else {
			modelAndView = new ModelAndView("system/membership/departList");
		}
		return modelAndView;
	}

	/**
	 * 部门和角色选择用户的用户显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridDepart")
	public void datagridDepart(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 测试
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "test")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		String jString = request.getParameter("_dt_json");
		DataTables dataTables = new DataTables(request);
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataTables);
		String username = request.getParameter("userName");
		if (username != null) {
			cq.like("userName", username);
			cq.add();
		}
		DataTableReturn dataTableReturn = systemService.getDataTableReturn(cq, true);
		TagUtil.datatable(response, dataTableReturn, "id,userName,mobilePhone,TSDepart_departname");
	}

	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "index")
	public String index() {
		return "bootstrap/main";
	}

	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "main")
	public String main() {
		return "bootstrap/test";
	}

	/**
	 * 测试
	 * 
	 * @return
	 */
	@RequestMapping(params = "testpage")
	public String testpage(HttpServletRequest request) {
		return "test/test";
	}

	/**
	 * 设置签名跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addsign")
	public ModelAndView addsign(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return new ModelAndView("system/user/usersign");
	}

	/**
	 * 用户录入
	 * 
	 * @param user
	 * @param req
	 * @return
	 */

	@RequestMapping(params = "savesign", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson savesign(HttpServletRequest req) {
		UploadFile uploadFile = new UploadFile(req);
		String id = uploadFile.get("id");
		TSUser user = systemService.flushEntity(TSUser.class, id);
		uploadFile.setRealPath("signatureFile");
		uploadFile.setCusPath("signature");
		uploadFile.setByteField("signature");
		uploadFile.setBasePath("resources");
		uploadFile.setRename(false);
		uploadFile.setObject(user);
		AjaxJson j = new AjaxJson();
		message = user.getUserName() + "设置签名成功";
		systemService.uploadFile(uploadFile);
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		j.setMsg(message);

		return j;
	}
	/**
	 * 测试组合查询功能
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "testSearch")
	public void testSearch(TSUser user, HttpServletRequest request,HttpServletResponse response,DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		if(user.getUserName()!=null){
			cq.like("userName", user.getUserName());
		}
		if(user.getRealName()!=null){
			cq.like("realName", user.getRealName());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "changestyle")
	public String changeStyle(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		if(user==null){
			return "login/login";
		}
//		String indexStyle = "shortcut";
//		String cssTheme="";
//		Cookie[] cookies = request.getCookies();
//		for (Cookie cookie : cookies) {
//			if(cookie==null || StringUtils.isEmpty(cookie.getName())){
//				continue;
//			}
//			if(cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")){
//				indexStyle = cookie.getValue();
//			}
//			if(cookie.getName().equalsIgnoreCase("JEECGCSSTHEME")){
//				cssTheme = cookie.getValue();
//			}
//		}
		SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme(request);
		request.setAttribute("indexStyle", sysThemesEnum.getStyle());
//		request.setAttribute("cssTheme", cssTheme);
		return "system/user/changestyle";
	}
	/**
	* @Title: saveStyle
	* @Description: 修改首页样式
	* @param request
	* @return AjaxJson    
	* @throws
	 */
	@RequestMapping(params = "savestyle")
	@ResponseBody
	public AjaxJson saveStyle(HttpServletRequest request,HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(Boolean.FALSE);
		TSUser user = ResourceUtil.getSessionUserName();
		if(user!=null){
			String indexStyle = request.getParameter("indexStyle");
//			String cssTheme = request.getParameter("cssTheme");
//			if(StringUtils.isNotEmpty(cssTheme)){
//				Cookie cookie4css = new Cookie("JEECGCSSTHEME", cssTheme);
//				cookie4css.setMaxAge(3600*24*30);
//				response.addCookie(cookie4css);
//				logger.info("cssTheme:"+cssTheme);
//			}else if("ace".equals(indexStyle)){
//				Cookie cookie4css = new Cookie("JEECGCSSTHEME", "metro");
//				cookie4css.setMaxAge(3600*24*30);
//				response.addCookie(cookie4css);
//				logger.info("cssTheme:metro");
//			}else {
//				Cookie cookie4css = new Cookie("JEECGCSSTHEME", "");
//				cookie4css.setMaxAge(3600*24*30);
//				response.addCookie(cookie4css);
//				logger.info("cssTheme:default");
//			}
			
			if(StringUtils.isNotEmpty(indexStyle)){
				Cookie cookie = new Cookie("JEECGINDEXSTYLE", indexStyle);
				//设置cookie有效期为一个月
				cookie.setMaxAge(3600*24*30);
				response.addCookie(cookie);
				logger.info("indexStyle:"+indexStyle);
				j.setSuccess(Boolean.TRUE);
				j.setMsg("样式修改成功，请刷新页面");
			}
//            ClientManager.getInstance().getClient().getFunctions().clear();
		}else{
			j.setMsg("请登录后再操作");
		}
		return j;
	}
	
	/**
	 * 自动完成查询零售商导购请求返回数据
	 * 
	 * @param request
	 * @param responss
	 */
	@RequestMapping(params = "getGuideAutoList")
	public void getGuideAutoList(HttpServletRequest request, HttpServletResponse response, Autocomplete autocomplete) {
		String q = request.getParameter("q");
//		String trem = StringUtil.getEncodePra(request.getParameter("trem"));// 重新解析参数
		autocomplete.setTrem(q);
		String hqlCondition = null;
		TSUser user = ResourceUtil.getSessionUserName();
		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){
			hqlCondition = " and retailerId ='"+user.getId()+"' and userType = '"+common.GlobalConstants.USER_TYPE_03+"' ";
		}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
			hqlCondition = " and retailerId ='"+user.getRetailerId()+"' and userType = '"+common.GlobalConstants.USER_TYPE_03+"'";
		}
		List autoList = this.userService.getAutoList(autocomplete,hqlCondition);
		hqlCondition = null;
//		List autoList = systemService.getAutoList(autocomplete);
		String labelFields = autocomplete.getLabelField();
		String[] fieldArr = labelFields.split(",");
		String valueField = autocomplete.getValueField();
		String[] allFieldArr = null;
		if (StringUtil.isNotEmpty(valueField)) {
			allFieldArr = new String[fieldArr.length+1];
			for (int i=0; i<fieldArr.length; i++) {
				allFieldArr[i] = fieldArr[i];
			}
			allFieldArr[fieldArr.length] = valueField;
		}
		
		try {
			String str = TagUtil.getAutoList(autocomplete, autoList);
			str = "(" + str + ")";
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(JSONHelper.listtojson(allFieldArr,allFieldArr.length,autoList));
            response.getWriter().flush();
            response.getWriter().close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	
	/**
	 * 下载快捷方式
	 * 
	 * @return
	 */
	@RequestMapping(params = "addShortcut")
	@ResponseBody
	public void addShortcut(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		String url = uri+"/loginController.do?login1";
		String templateContent = "[InternetShortcut]\r\n" + "URL="+url +"\r\n"+
		"IDList=\r\n"+
		"IconFile="+uri+"/favicon.ico\r\n"+
		"IconIndex=0\r\n";     
//	    String realfilename = "g+后台管理系统.url"; 
	    try {       
	    	String realfilename = "g+"+URLEncoder.encode("后台管理系统.url","UTF-8"); 
	        byte[] buffer = templateContent.getBytes();        
	        response.reset();       
//	        response.setContentType("text/html; charset=utf-8"); 
	        response.setCharacterEncoding("UTF-8"); 
	        response.setHeader("Content-Disposition", "attachment; filename=" + realfilename);    
	        response.setHeader("Content-type", "application/octet-stream;charset=UTF-8"); 
	        OutputStream os = response.getOutputStream();       
	        os.write(buffer);  
	        os.close();             
	    } catch (Exception e) {       
	        e.printStackTrace();       
	    }     
	}
	
	
	/**
	 * 批量编辑零售商合约时间
	 * @param request
	 * @param ids
	 */
	@RequestMapping(params = "goBatchUpdateActiveTime")
	public ModelAndView goBatchUpdateActiveTime(String ids,HttpServletRequest req) {
		req.setAttribute("ids", ids);
		return new ModelAndView("system/user/retailersActiveTime-update");
	}
	
	/**批量更新零售商合约时间和对应的导购和员工帐号激活时间
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "batchSaveActiveTime")
	@ResponseBody
	public AjaxJson batchSaveActiveTime(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商合约时间修改成功";
		try {
			this.userService.batchSaveActiveTime(request);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "零售商合约时间修改失败";
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量编辑导购有效时间
	 * @param request
	 * @param ids
	 */
	@RequestMapping(params = "goBatchUpdateEffectiveTime")
	public ModelAndView goBatchUpdateEffectiveTime(String ids,HttpServletRequest req) {
		req.setAttribute("ids", ids);
		return new ModelAndView("system/user/guideEffectiveTime-update");
	}
	
	/**批量更新导购有效时间
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "batchSaveEffectiveTime")
	@ResponseBody
	public AjaxJson batchSaveEffectiveTime(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "导购有效时间修改成功";
		try {
			this.userService.batchSaveEffectiveTime(request);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "导购有效时间修改失败";
		}
		j.setMsg(message);
		return j;
	}
}