package org.jeecgframework.web.system.controller.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SendSMS;
import org.jeecgframework.core.util.SysThemesUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.FunctionVo;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserLogin;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONArray;
import com.buss.store.entity.TStoreAccountInfoEntity;
import com.buss.user.entity.TSUserMessage;
import com.buss.user.service.TSUserMessageServiceI;

import cn.redis.service.RedisService;
import common.GlobalConstants;

/**
 * 登陆初始化控制器
 * @author 
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController{
	private Logger log = Logger.getLogger(LoginController.class);
	
	private long efficientTime = DateUtils.getTimestamp().getTime()
	- GlobalConstants.VALIDATEION_EFFICIENT_TIME * 60 * 1000;

	private int LIMIT_FAIL_COUNT = 10;
	private SystemService systemService;
	private UserService userService;
	private String message = null;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private TSUserMessageServiceI tSUserMessageService;
	
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {

		this.userService = userService;
	}

	@RequestMapping(params = "goPwdInit")
	public String goPwdInit() {
		return "login/pwd_init";
	}

	
	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(TSUser user, HttpServletRequest req) {
		HttpSession session = ContextHolderUtils.getSession();
	   AjaxJson j = new AjaxJson();
	   String KEYDATA[] = (req.getParameter("KEYDATA")+"").replaceAll("qq987654321gch", "").replaceAll("QQ123456789gch", "").split(",gch,");
	   if(null != KEYDATA && KEYDATA.length == 3)
	   {
		    user.setUserName(KEYDATA[0]);//用户名
		    user.setPassword(KEYDATA[1]);//密码
		    String randCode = KEYDATA[2];//验证码
	        if (StringUtils.isEmpty(randCode)) {
	            j.setMsg("验证码为空");
	        	j.setObj("codeerror");
	            j.setSuccess(false);
	        } else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
	            // todo "randCode"和验证码servlet中该变量一样，通过统一的系统常量配置比较好，暂时不知道系统常量放在什么地方合适
	            j.setMsg("验证码错误");
	            j.setObj("codeerror");
	            j.setSuccess(false);
	        } else {
	        	String errorMsg = null ;
	        	Date dt = new Date();
	        	Date dt2 = new Date(dt.getTime()-60*60*1000);
	        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	        	String startTime = format.format(dt)+" 00:00:00";
//	        	String endTime = format.format(dt)+" 23:59:59";
	        	String startTime = format.format(dt2);
	        	String endTime = format.format(dt);
	        	//登录错误次数(超过10次就冻结当天登录权限)
//	        	DateUtils.getDate();
	        	String sql = "select count(1) from t_s_user_login where user_Name = '"+ user.getUserName()
	        	+ "' and login_Date between '"+startTime+"' and '"+endTime+"'" ;
	        	Long count = this.systemService.getCountForJdbc(sql);
	        	//记录登录错误信息
	        	if(count<LIMIT_FAIL_COUNT){
	                TSUser u = userService.checkUserExits(user);
	                if(u == null) {
	                		TSUserLogin lg = new TSUserLogin();
	                		lg.setUserName(user.getUserName());
	                		lg.setImei("web-login");
	                		lg.setIpAddress(IpUtil.getIpAddr(req));
	                		lg.setLoginDate(Utility.getCurrentTimestamp());
	                		this.systemService.save(lg);
	                		j.setObj("usererror");
	                		errorMsg = "用户名或密码错误";
	                		if(count>=LIMIT_FAIL_COUNT-1){
	                			errorMsg = "请过一个小时再登录或者联系管理员激活帐号";
	                			//更新该用户为锁定状态
	        	        		String updateSql = "update t_s_user set user_Status ='"+TSUser.USER_STATUS_FIXED+"' where username = '"+user.getUserName()+"'";
	        	        		this.systemService.updateBySqlString(updateSql);
	                		}
	                		j.setMsg(errorMsg);
	                        j.setSuccess(false);
	                        systemService.addLog("用户名："+user.getUserName()+"密码："+user.getPassword()+"登录错误", Globals.Log_Type_LOGIN, Globals.Log_Leavel_WARRING);
	                        return j;
	                }else{
	                	//存在帐号且帐号密码正确
	                	if(common.GlobalConstants.USER_TYPE_01.equals(u.getUserType())
	                			||common.GlobalConstants.USER_TYPE_02.equals(u.getUserType())
	                			||common.GlobalConstants.USER_TYPE_05.equals(u.getUserType())){
	                		//正常状态登录
	                		if(TSUser.USER_STATUS_ACTIVE.equals(u.getUserStatus())||TSUser.USER_STATUS_ALL.equals(u.getUserStatus())){
	                			saveLoginSuccessInfo(req, u, null);
	                			return j;
	                		}else if(TSUser.USER_STATUS_INACTIVE.equals(u.getUserStatus())){
	                			errorMsg = "该帐号已被停用，请联系管理员";
	                			j.setObj("usererror");
	                			j.setMsg(errorMsg);
	                			j.setSuccess(false);
	                			return j;
	                		}else if(TSUser.USER_STATUS_FIXED.equals(u.getUserStatus())){
	                			//把锁定状态改为激活状态
	                			String updateSql = "update t_s_user set user_Status ='"+TSUser.USER_STATUS_ACTIVE+"' where id = '"+u.getId()+"'";
	                			String delSql = "DELETE from t_s_user_login where user_name = '"+user.getUserName()+"' and login_Date between '"+startTime+"' and '"+endTime+"'" ;
	                			this.systemService.updateBySqlString(updateSql);
	                			this.systemService.updateBySqlString(delSql);
	                			saveLoginSuccessInfo(req, u, null);
	                			return j;
	                		}else if(TSUser.USER_STATUS_WAIT_ACTIVE.equals(u.getUserStatus())){
	                			//待激活状态
	                			errorMsg = "该帐号还未激活，请联系管理员";
	                			j.setObj("usererror");
	                			j.setMsg(errorMsg);
	                			j.setSuccess(false);
	                			return j;
	                		}else if(TSUser.USER_STATUS_DESTROY.equals(u.getUserStatus())){
	                			//已注销状态
	                			errorMsg = "该帐号已注销，请联系管理员";
	                			j.setObj("usererror");
	                			j.setMsg(errorMsg);
	                			j.setSuccess(false);
	                			return j;
	                		}
	                	}else{
	                		errorMsg = "您无权登录";
	                		j.setObj("usererror");
	            			j.setMsg(errorMsg);
	            			j.setSuccess(false);
	            			return j;
	                	}
	//                        Map<String, Object> attrMap = new HashMap<String, Object>();
	//                        j.setAttributes(attrMap);
	//                        String orgId = req.getParameter("orgId");
	                }
	        	}else{
	        		errorMsg = "请过一个小时再登录或者联系管理员激活帐号";
	        		j.setObj("usererror");
	        		j.setMsg(errorMsg);
	        		j.setSuccess(false);
	        		return j;
	        	}
	        }
	   }else
	   {
		  j.setSuccess(false);
		  j.setObj("error");//缺少参数
	   }
	   
	   
	   return j;
	}
    /**
     * 保存用户登录的信息，并将当前登录用户的组织机构赋值到用户实体中；
     * @param req request
     * @param user 当前登录用户
     * @param orgId 组织主键
     */
    private void saveLoginSuccessInfo(HttpServletRequest req, TSUser user, String orgId) {
        HttpSession session = ContextHolderUtils.getSession();
        //当前session为空 或者 当前session的用户信息与刚输入的用户信息一致时，则更新Client信息
        Client clientOld = ClientManager.getInstance().getClient(session.getId());
		if(clientOld == null || clientOld.getUser() ==null ||user.getUserName().equals(clientOld.getUser().getUserName())){
			message = "用户名: " + user.getUserName() +"登录成功";
			Client client = new Client();
	        client.setIp(IpUtil.getIpAddr(req));
	        client.setLogindatetime(new Date());
	        client.setUser(user);
	        ClientManager.getInstance().addClinet(session.getId(), client);
			// 添加登陆日志
	        systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
		} else {//如果不一致，则注销session并通过session=req.getSession(true)初始化session
			String randCode = session.getAttribute("randCode")+"";
			ClientManager.getInstance().removeClinet(session.getId());
			session.invalidate();
			session=req.getSession(true);//session初始化
			session.setAttribute(ResourceUtil.LOCAL_CLINET_USER, user);
			session.setAttribute("randCode",randCode);//保存验证码
			TSUser u = new TSUser();
			checkuser(u,req);
		}
		//update-end-Author:jg_renjie  Date:20151220 for：TASK #804 【基础权限】切换用户，用户分拥有不同的权限，切换用户权限错误问题
    }

    /**
	 * 用户登录
	 * 统一改为用login1登录
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "login")
	public String login(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		return login1(modelMap, request, response);
		/*TSUser user = ResourceUtil.getSessionUserName();
		String roles = "";
		if (user != null) {
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleName() + ",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
            modelMap.put("roleName", roles);
            modelMap.put("userType", user.getUserType());
            modelMap.put("userName", user.getUserName());
//            modelMap.put("currentOrgName", ClientManager.getInstance().getClient().getUser().getCurrentDepart().getDepartname());
            request.getSession().setAttribute("CKFinder_UserRole", "admin");
			
			// 默认风格
			String indexStyle = "shortcut";
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
					continue;
				}
				if (cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")) {
					indexStyle = cookie.getValue();
				}
			}
			// 要添加自己的风格，复制下面三行即可
//			if (StringUtils.isNotEmpty(indexStyle)
//					&& indexStyle.equalsIgnoreCase("bootstrap")) {
//				return "main/bootstrap_main";
//			}
//			if (StringUtils.isNotEmpty(indexStyle)
//					&& indexStyle.equalsIgnoreCase("shortcut")) {
//				return "main/shortcut_main";
//			}
//
//			if (StringUtils.isNotEmpty(indexStyle)
//					&& indexStyle.equalsIgnoreCase("sliding")) {
//				return "main/sliding_main";
//			}
			if (StringUtils.isNotEmpty(indexStyle)&&
					!"default".equalsIgnoreCase(indexStyle)&&
					!"undefined".equalsIgnoreCase(indexStyle)) {
				if("ace".equals(indexStyle)){
					request.setAttribute("menuMap", getFunctionMap(user));
				}
				log.info("main/"+indexStyle.toLowerCase()+"_main");
				return "main/"+indexStyle.toLowerCase()+"_main";
			}
			
			SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
			if("ace".equals(sysTheme.getStyle())||"diy".equals(sysTheme.getStyle())||"acele".equals(sysTheme.getStyle())){
//				request.setAttribute("menuMap", getFunctionMap(user));
				request.setAttribute("menuMap2", JSONArray.toJSONString(getUserFunctionList(user)));
			}
			//update-start--Author:zhoujf Date:20150610 for:ace addOneTab无效问题
			Cookie cookie = new Cookie("JEECGINDEXSTYLE", sysTheme.getStyle());
			//设置cookie有效期为一个月
			cookie.setMaxAge(3600*24*30);
			response.addCookie(cookie);
			//update-end--Author:zhoujf Date:20150610 for:ace addOneTab无效问题
			
			//update-start--Author: jg_huangxg Date:20160330 for: zIndex索引问题
			Cookie zIndexCookie = new Cookie("ZINDEXNUMBER", "1990");
			zIndexCookie.setMaxAge(3600*24);//一天
			response.addCookie(zIndexCookie);
			//update-end--Author: jg_huangxg Date:20160330 for: zIndex索引问题
			return sysTheme.getIndexPath();
		} else {
			return "login/login";
		}*/

	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		TSUser user = ResourceUtil.getSessionUserName();
		systemService.addLog("用户" + user.getUserName() + "已退出",
				Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
		ClientManager.getInstance().removeClinet(session.getId());
		session.invalidate();
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login"));
		return modelAndView;
	}

	/**
	 * 菜单跳转
	 * 
	 * @return
	 */
	/*@RequestMapping(params = "left")
	public ModelAndView left(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
        ModelAndView modelAndView = new ModelAndView();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
            modelAndView.setView(new RedirectView("loginController.do?login"));
		}else{
            List<TSConfig> configs = userService.loadAll(TSConfig.class);
            for (TSConfig tsConfig : configs) {
                request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
            }
            modelAndView.setViewName("main/left");
            request.setAttribute("menuMap", getFunctionMap(user));
        }
		return modelAndView;
	}*/

	/**
	 * 菜单逻辑要调整,改为支付返回 List<TSFunction>
	 * 获取权限的map
	 * @param user
	 * @return
	 */
	/*private Map<Integer, List<TSFunction>> getFunctionMap(TSUser user) {
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if (client.getFunctionMap() == null || client.getFunctionMap().size() == 0) {
			Map<Integer, List<TSFunction>> functionMap = new HashMap<Integer, List<TSFunction>>();
			Map<String, TSFunction> loginActionlist = getUserFunction(user);
			if (loginActionlist.size() > 0) {
				Collection<TSFunction> allFunctions = loginActionlist.values();
				for (TSFunction function : allFunctions) {
		            if(function.getFunctionType().intValue()==Globals.Function_TYPE_FROM.intValue()){
						//如果为表单或者弹出 不显示在系统菜单里面
						continue;
					}
					if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
						functionMap.put(function.getFunctionLevel() + 0,new ArrayList<TSFunction>());
					}
					functionMap.get(function.getFunctionLevel() + 0).add(function);
				}
				// 菜单栏排序
				Collection<List<TSFunction>> c = functionMap.values();
				for (List<TSFunction> list : c) {
					Collections.sort(list, new NumberComparator());
				}
			}
			client.setFunctionMap(functionMap);
			return functionMap;
		}else{
			return client.getFunctionMap();
		}
	}*/
	
	/**
	 * 获取用户全部菜单权限的List（查询一级菜单，子菜单也会都查出来）
	 * @param user
	 * @return
	 */
	private List<FunctionVo> getUserFunctionList(TSUser user) {
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
//		List<TSFunction> funList = new ArrayList<TSFunction>();
		if (client.getFunctionList() == null || client.getFunctionList().size() == 0) {
			StringBuilder sql = new StringBuilder("SELECT DISTINCT(f.id) id,f.functionname functionName,f.functionurl functionUrl,f.functionorder functionOrder,")
					.append("f.functionlevel functionLevel ,f.parentfunctionid parentId,i.path iconPath ")
					.append("from t_s_role_user ru join t_s_role_function rf on ru.roleid = rf.roleid ")
					.append("JOIN t_s_function f on rf.functionid = f.id ")
					.append("LEFT JOIN t_s_icon i on f.iconid = i.ID ")
					.append("where ru.userid = '").append(user.getId()).append("' and f.`status` = 'A'");
			
//			Map<String, Object> m = systemService.findForJdbc(sql.toString(), null).get(0);
//			Object o = m.get("functionLevel");
//			System.out.println(o.getClass());
			
			List<FunctionVo> myFunctionList= this.systemService.findObjForJdbc(sql.toString(), FunctionVo.class);
			//迭代组装树结构菜单列表
			List<FunctionVo> FunctionListTree = new ArrayList<FunctionVo>();
			this.fillFunctionVoList(FunctionListTree,myFunctionList,0);
			
//				StringBuilder hqlsb1=new StringBuilder("select distinct f from TSFunction f,TSRoleFunction rf,TSRoleUser ru  ")
//		           .append("where ru.TSRole.id=rf.TSRole.id and rf.TSFunction.id=f.id and f.status='A' and f.functionLevel = 0 and ru.TSUser.id=? order by f.functionLevel,f.functionOrder asc");
//		           List<TSFunction> list1 = systemService.findHql(hqlsb1.toString(),user.getId());
//				for (TSFunction function : list1) {
//		            if(function.getFunctionType().intValue()==Globals.Function_TYPE_FROM.intValue()){
//						//如果为表单或者弹出 不显示在系统菜单里面
//						continue;
//					}
//		            if(0==function.getFunctionLevel()){//默认显示一级菜单
//		            	funList.add(function);
//		            }
//				}
				//菜单排序
				this.sortFunctionList(FunctionListTree);
			client.setFunctionList(FunctionListTree);
			return FunctionListTree;
		}else{
			return client.getFunctionList();
		}
	}

	/**迭代组装树结构菜单列表
	 * FunctionListTree 树结构菜单列表
	 * functionList 普通结构菜单列表
	 * level 菜单层级
	 */
	private void fillFunctionVoList(List<FunctionVo> FunctionListTree,List<FunctionVo> functionList,int level) {
		if(functionList.size()>0){
			boolean hasNextFunctions = false;
			for(FunctionVo vo : functionList){
				if(level==vo.getFunctionLevel()){
					if(vo.getParentId()!=null){//有上级菜单
						//获取父菜单
						FunctionVo parentFuntionVo = this.getParentFunctionVo(FunctionListTree,vo.getParentId(),level-1);
						if(parentFuntionVo!=null){
							//添加当前菜单到对应的子菜单中
							parentFuntionVo.getSubFunctionList().add(vo);
						}
					}else{//一级菜单
						FunctionListTree.add(vo);
					}
				}else if(level<vo.getFunctionLevel()){//有下一级菜单
					hasNextFunctions = true;
				}
			}
			if(hasNextFunctions){
				//迭代子菜单
				fillFunctionVoList(FunctionListTree, functionList, level+1);
			}else{//迭代循环完成，清空原菜单列表数据
				functionList.clear();
			}
		}
	}

	/**获取父菜单
	 * @param functionList 父菜单同级的菜单列表
	 * @param parentId	父菜单id
	 * @param parentLevel 父菜单层级
	 * @return
	 */
	private FunctionVo getParentFunctionVo(List<FunctionVo> functionList, String parentId, int parentLevel) {
		boolean isParentLevel = false;
		for(FunctionVo vo : functionList){
			if(vo.getFunctionLevel()==parentLevel){
				isParentLevel = true;
				if(vo.getId().equals(parentId)){
					return vo;
				}
			}
		}
		if(!isParentLevel){//循环下一级菜单
			for(FunctionVo vo : functionList){
				FunctionVo parentFuntionVo = getParentFunctionVo(vo.getSubFunctionList(), parentId, parentLevel);
				if(parentFuntionVo!=null){
					return parentFuntionVo;
				}
			}
		}
		return null;
	}

	/**菜单递归排序*/
	private void sortFunctionList(List<FunctionVo> funList) {
		Collections.sort(funList, new FunctionVo());
		for(FunctionVo f : funList){
			List<FunctionVo> subFunctions = f.getSubFunctionList();
			if(subFunctions.size()>0){
				sortFunctionList(subFunctions);
			}
		}
	}
//	/**菜单递归排序*/
//	private void sortFunctionList(List<TSFunction> funList) {
//		Collections.sort(funList, new NumberComparator());
//		for(TSFunction f : funList){
//			List<TSFunction> subFunctions = f.getTSFunctions();
//			if(subFunctions.size()>0){
//				sortFunctionList(subFunctions);
//			}
//		}
//	}

	/**
	 * 获取用户菜单列表
	 * 
	 * @param user
	 * @return
	 */
	/*private Map<String, TSFunction> getUserFunction(TSUser user) {
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if (client.getFunctions() == null || client.getFunctions().size() == 0) {
			Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();
	           StringBuilder hqlsb1=new StringBuilder("select distinct f from TSFunction f,TSRoleFunction rf,TSRoleUser ru  ")
	           .append("where ru.TSRole.id=rf.TSRole.id and rf.TSFunction.id=f.id and f.status='A' and ru.TSUser.id=? order by f.functionLevel,f.functionOrder asc");
	           List<TSFunction> list1 = systemService.findHql(hqlsb1.toString(),user.getId());
	           for(TSFunction function:list1){
		              loginActionlist.put(function.getId(),function);
		           }
            client.setFunctions(loginActionlist);
		}
		return client.getFunctions();
	}*/

    /**
	 * 首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
		
	 //update-start--Author:jg_renjie  Date:20160315 for：配合首页改造，控制不同风格时是否引入js/css文件
		SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
		//ACE ACE2 DIY时需要在home.jsp头部引入依赖的js及css文件
		if("ace".equals(sysTheme.getStyle())||"diy".equals(sysTheme.getStyle())||"acele".equals(sysTheme.getStyle())){
			request.setAttribute("show", "1");
		} else {//default及shortcut不需要引入依赖文件，所有需要屏蔽
			request.setAttribute("show", "0");
		}
	 //update-end--Author:jg_renjie  Date:20160315 for：配合首页改造，控制不同风格时是否引入js/css文件
		return new ModelAndView("main/home");
	}
	
	  /**
	 * ACE首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "acehome")
	public ModelAndView acehome(HttpServletRequest request) {
		
	 //update-start--Author:jg_renjie  Date:20160315 for：配合首页改造，控制不同风格时是否引入js/css文件
		SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
		//ACE ACE2 DIY时需要在home.jsp头部引入依赖的js及css文件
		if("ace".equals(sysTheme.getStyle())||"diy".equals(sysTheme.getStyle())||"acele".equals(sysTheme.getStyle())){
			request.setAttribute("show", "1");
		} else {//default及shortcut不需要引入依赖文件，所有需要屏蔽
			request.setAttribute("show", "0");
		}
	 //update-end--Author:jg_renjie  Date:20160315 for：配合首页改造，控制不同风格时是否引入js/css文件
		return new ModelAndView("main/acehome");
	}
	
	  /**
	 * ACE首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "acehomeNew")
	public ModelAndView acehomeNew(HttpServletRequest request) {
		
	 //update-start--Author:jg_renjie  Date:20160315 for：配合首页改造，控制不同风格时是否引入js/css文件
		SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
		//ACE ACE2 DIY时需要在home.jsp头部引入依赖的js及css文件
		if("ace".equals(sysTheme.getStyle())||"diy".equals(sysTheme.getStyle())||"acele".equals(sysTheme.getStyle())){
			request.setAttribute("show", "1");
		} else {//default及shortcut不需要引入依赖文件，所有需要屏蔽
			request.setAttribute("show", "0");
		}
	 //update-end--Author:jg_renjie  Date:20160315 for：配合首页改造，控制不同风格时是否引入js/css文件
		return new ModelAndView("main/acehomeNew");
	}
	
	/**
	 * ACE首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "acehomeStatistics")
	public ModelAndView acehomeStatistics(HttpServletRequest request) {
		
		return new ModelAndView("main/acehomeStatistics");
	}
	
	/**
	 * 无权限页面提示跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "noAuth")
	public ModelAndView noAuth(HttpServletRequest request) {
		return new ModelAndView("common/noAuth");
	}
	/**
	 * @Title: top
	 * @Description: bootstrap头部菜单请求
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	/*@RequestMapping(params = "top")
	public ModelAndView top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/bootstrap_top");
	}*/
	/**
	 * @Title: top
	 * @author gaofeng
	 * @Description: shortcut头部菜单请求
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	/*@RequestMapping(params = "shortcut_top")
	public ModelAndView shortcut_top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/shortcut_top");
	}
	*/
	/**
	 * @Title: top
	 * @author:gaofeng
	 * @Description: shortcut头部菜单一级菜单列表，并将其用ajax传到页面，实现动态控制一级菜单列表
	 * @return AjaxJson
	 * @throws
	 */
   /* @RequestMapping(params = "primaryMenu")
    @ResponseBody
	public String getPrimaryMenu() {
		List<TSFunction> primaryMenu = getFunctionMap(ResourceUtil.getSessionUserName()).get(0);
        String floor = "";
//        update-start--Author:zhangguoming  Date:20140923 for：用户没有任何权限，首页没有退出按钮的bug
        if (primaryMenu == null) {
            return floor;
        }
//        update-end--Author:zhangguoming  Date:20140923 for：用户没有任何权限，首页没有退出按钮的bug
        for (TSFunction function : primaryMenu) {
            if(function.getFunctionLevel() == 0) {
            	String lang_key = function.getFunctionName();
            	String lang_context = mutiLangService.getLang(lang_key);
            	lang_context=lang_context.trim();
//              update-start--Author:huangzq  Date:20160113 for：:TASK#858::【系统功能】logo替换
            	if("业务申请".equals(lang_context)){

                	String ss = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context +"</span></div>";
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/ywsq.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/ywsq-up.png' style='display: none;' />" +ss+ " </li> ";
                }else if("个人办公".equals(lang_context)){

                	String ss = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context +"</span></div>";
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/grbg.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/grbg-up.png' style='display: none;' />" +ss+ " </li> ";
                }else if("流程管理".equals(lang_context)){

                	String ss = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context +"</span></div>";
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/lcsj.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/lcsj-up.png' style='display: none;' />" +ss+ " </li> ";
                }else if("Online 开发".equals(lang_context)){

                    floor += " <li><img class='imag1' src='plug-in/login/images/online.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/online_up.png' style='display: none;' />" + " </li> ";
                }else if("自定义表单".equals(lang_context)){

                	String ss = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context +"</span></div>";
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/zdybd.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/zdybd-up.png' style='display: none;' />" +ss+ " </li> ";
                }else if("系统监控".equals(lang_context)){

                    floor += " <li><img class='imag1' src='plug-in/login/images/xtjk.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/xtjk_up.png' style='display: none;' />" + " </li> ";
                }else if("统计报表".equals(lang_context)){

                    floor += " <li><img class='imag1' src='plug-in/login/images/tjbb.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/tjbb_up.png' style='display: none;' />" + " </li> ";
                }else if("消息中间件".equals(lang_context)){
                	String ss = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context +"</span></div>";
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/msg.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/msg_up.png' style='display: none;' />" +ss+ " </li> ";
                }else if("系统管理".equals(lang_context)){

                    floor += " <li><img class='imag1' src='plug-in/login/images/xtgl.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/xtgl_up.png' style='display: none;' />" + " </li> ";
                }else if("常用示例".equals(lang_context)){

                    floor += " <li><img class='imag1' src='plug-in/login/images/cysl.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/cysl_up.png' style='display: none;' />" + " </li> ";
                }else if(lang_context.contains("消息推送")){
                	
                	String s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>消息推送</div>";
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/msg.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/msg_up.png' style='display: none;' />"
                            + s +"</li> ";
                }else{
                    //其他的为默认通用的图片模式
                	String s="";
                    if(lang_context.length()>=5 && lang_context.length()<7){
                        s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context +"</span></div>";
                    }else if(lang_context.length()<5){
                        s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>"+ lang_context +"</div>";
                    }else if(lang_context.length()>=7){
                        s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context.substring(0, 6) +"</span></div>";
                    }
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/default.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/default_up.png' style='display: none;' />"
                            + s +"</li> ";
                }
            }
        }
//      update-end--Author:huangzq  Date:20160114 for：:TASK#858::【系统功能】logo替换
		return floor;
	}*/

	/**
	 * @Title: top
	 * @author:wangkun
	 * @Description: shortcut头部菜单二级菜单列表，并将其用ajax传到页面，实现动态控制二级菜单列表
	 * @return AjaxJson
	 * @throws
	 */
	/*@RequestMapping(params = "primaryMenuDiy")
	@ResponseBody
	public String getPrimaryMenuDiy() {
		//取二级菜单
		List<TSFunction> primaryMenu = getFunctionMap(ResourceUtil.getSessionUserName()).get(1);
		String floor = "";
		if (primaryMenu == null) {
			return floor;
		}
		String menuString = "user.manage role.manage department.manage menu.manage";
		for (TSFunction function : primaryMenu) {
			if(menuString.contains(function.getFunctionName())){
				if(function.getFunctionLevel() == 1) {

					String lang_key = function.getFunctionName();
					String lang_context = mutiLangService.getLang(lang_key);
					if("申请".equals(lang_key)){
						lang_context = "申请";
						String s = "";
						s = "<div style='width:67px;position: absolute;top:47px;text-align:center;color:#000000;font-size:12px;'>"+ lang_context +"</div>";
						floor += " <li><img class='imag1' src='plug-in/login/images/head_icon1.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/head_icon1.png' style='display: none;' />" + s + " </li> ";
					} else if("Online 开发".equals(lang_context)){

						floor += " <li><img class='imag1' src='plug-in/login/images/online.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/online_up.png' style='display: none;' />" + " </li> ";
					}else if("统计查询".equals(lang_context)){

						floor += " <li><img class='imag1' src='plug-in/login/images/guanli.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/guanli_up.png' style='display: none;' />" + " </li> ";
					}else if("系统管理".equals(lang_context)){

						floor += " <li><img class='imag1' src='plug-in/login/images/xtgl.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/xtgl_up.png' style='display: none;' />" + " </li> ";
					}else if("常用示例".equals(lang_context)){

						floor += " <li><img class='imag1' src='plug-in/login/images/cysl.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/cysl_up.png' style='display: none;' />" + " </li> ";
					}else if("系统监控".equals(lang_context)){

						floor += " <li><img class='imag1' src='plug-in/login/images/xtjk.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/xtjk_up.png' style='display: none;' />" + " </li> ";
					}else if(lang_context.contains("消息推送")){
						String s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>消息推送</div>";
						floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/msg.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/msg_up.png' style='display: none;' />"
								+ s +"</li> ";
					}else{
						//其他的为默认通用的图片模式
						String s = "";
						if(lang_context.length()>=5 && lang_context.length()<7){
							s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#000000;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context +"</span></div>";
						}else if(lang_context.length()<5){
							s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#000000;font-size:12px;'>"+ lang_context +"</div>";
						}else if(lang_context.length()>=7){
							s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#000000;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context.substring(0, 6) +"</span></div>";
						}
						floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/head_icon2.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/default_up.png' style='display: none;' />"
								+ s +"</li> ";
					}
				}
			}
		}

		return floor;
	}*/
	/**
	 * 云桌面返回：用户权限菜单
	 */
	/*@RequestMapping(params = "getPrimaryMenuForWebos")
	@ResponseBody
	public AjaxJson getPrimaryMenuForWebos() {
		AjaxJson j = new AjaxJson();
		//将菜单加载到Session，用户只在登录的时候加载一次
		Object getPrimaryMenuForWebos =  ContextHolderUtils.getSession().getAttribute("getPrimaryMenuForWebos");
		if(oConvertUtils.isNotEmpty(getPrimaryMenuForWebos)){
			j.setMsg(getPrimaryMenuForWebos.toString());
		}else{
			String PMenu = ListtoMenu.getWebosMenu(getFunctionMap(ResourceUtil.getSessionUserName()));
			ContextHolderUtils.getSession().setAttribute("getPrimaryMenuForWebos", PMenu);
			j.setMsg(PMenu);
		}
		return j;
	}*/

    /**
     * 另一套登录界面
     * @return
     */
//    @RequestMapping(params = "login2")
//    public String login2(){
//        return "login/login2";
//    }
	/**
	 * ACE登录界面
	 * @return
	 */
//	@RequestMapping(params = "login3")
//	public String login3(){
//		return "login/login3";
//	}
	
	
	/*************************************尖刀产品start*******************************************/
	
	/**
	 * 注册时验证手机号码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "validatePhone")
	public AjaxJson validatePhone(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		String phone = request.getParameter("mobilePhone");// 手机号码
		String userType = request.getParameter("userType");// 用户类型  02
		
		AjaxJson j = new AjaxJson();
		boolean isReg = userService.isRegister(phone,userType);
		if(isReg){//已注册过
			j.setMsg("该手机号码已注册，请重新录入");
	       	j.setObj("error");
	        j.setSuccess(false);
	        systemService.addLog("手机号码："+phone + "已注册", Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
		}else{
			
		}
        return j;
	}
	
	/**
	 * 发送短信
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "sendMsg")
	public AjaxJson sendMsg(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		String phone = request.getParameter("mobilePhone");// 手机号码
		String userType = request.getParameter("userType");// 用户类型   02
		AjaxJson j = new AjaxJson();
		//查找一分钟内redis有没有短信记录
		String value = "";
		String key = GlobalConstants.SEND_MSG_ + phone; 
		try {
			value = redisService.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(Utility.isEmpty(value)){//没有记录重新发送短信
			SendSMS sendSMS = new SendSMS();
			Map<String, String> valMap = sendSMS.getValidationCode();// 获得验证码的格式内存
			String validationCode = valMap.get("validationCode");
			String msg = valMap.get("Msg");
			boolean isSend = sendSMS.send(phone, msg);
			if (isSend) {// 短信发送成功
				TSUserMessage message = new TSUserMessage();
				message.setUserName(phone);
				message.setUserType(userType);
				message.setImei(null);
				message.setIpAddress(IpUtil.getIpAddr(request));
				message.setSendDate(DateUtils.gettimestamp());
				message.setContent(validationCode);//验证码
				systemService.save(message);
				
				j.setObj(message);
				//写入redis缓存，过期时间为一分钟
				try {
					redisService.set(key, message.getContent(),GlobalConstants.SEND_MSG_TIME);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {// 短信发送失败
				j.setMsg("验证码发送失败，请稍后重试");
		       	j.setObj("error");
		        j.setSuccess(false);
			}
		}else{
			Long time = 0l;
			try {
				time = redisService.getEffectiveTime(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
			j.setMsg("你获取验证码太频繁，请稍后"+time+"秒重试");
	       	j.setObj("error");
	        j.setSuccess(false);
		}
        return j;
	}
	
	/**
	 * 注册时验证用户名
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "validateUserName")
	public AjaxJson validateUserName(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		String username = request.getParameter("username");// 用户名
		String userType = request.getParameter("userType");// 用户类型
		//零售商和个人怎样区分?  userType为02零售商，retailerType 零售商人，货
		//验证手机号码是否使用
		//验证通过，验证用户名和密码
		AjaxJson j = new AjaxJson();
		boolean isExists = userService.validateUserName(username, userType);
		if(!isExists){
			//
		} else {
			j.setMsg("该用户名已存在");
	       	j.setObj("error");
	        j.setSuccess(false);
		}
		return j;

	}
	/**
	 * 用户注册(包括验证码验证)  
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "register")
	public AjaxJson register(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		String phone = request.getParameter("mobilePhone");// 手机号码
		String validate = request.getParameter("validate");// 验证码
		String username = request.getParameter("username");// 用户名
		String password = request.getParameter("password");// 密码
		String userType = request.getParameter("userType");// 用户类型
		AjaxJson j = new AjaxJson();
		TSUserMessage message = tSUserMessageService.getLastObject(
				username, userType);
		if (Utility.isNotEmpty(message)
				&& message.getSendDate().getTime() >= efficientTime // 在规定的时间内
				&& validate.equals(message.getContent())) { // 短信验证码一致
			//用户注册
			String encryptPassword = PasswordUtil.encrypt(username,
					password, PasswordUtil.getStaticSalt());
			TSUser user = new TSUser(phone, userType, username, encryptPassword);
			userService.saveUserInfo(user);
		} else {
			j.setMsg("验证码验证失败，请重试");
	       	j.setObj("error");
	        j.setSuccess(false);
		}
		return j;
	}
	
	 /**
	 * 用户登录(登录之前先要调用validateUserName验证用户名和密码)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "login1")
	public String login1(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		TSUser user = ResourceUtil.getSessionUserName();
		String roles = "";
		if (user != null) {
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleName() + ",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
            modelMap.put("roleName", roles);
            modelMap.put("userType", user.getUserType());
            modelMap.put("userName", user.getUserName());
            request.getSession().setAttribute("CKFinder_UserRole", "admin");
			
          //设置代表是尖刀产品登录
			SysThemesEnum sysTheme = SysThemesUtil.getSysTheme2(request);
			if("ace".equals(sysTheme.getStyle())
					||"diy".equals(sysTheme.getStyle())
					||"acele".equals(sysTheme.getStyle())
					||"acele1".equals(sysTheme.getStyle())
					||"aceCustom".equals(sysTheme.getStyle())
					){
				request.setAttribute("funTp", "1");
//				request.setAttribute("menuMap", getFunctionMap(user));//通过java标签渲染菜单
				request.setAttribute("menuMap2", JSONArray.toJSONString(getUserFunctionList(user)));//通过js渲染菜单
			}

            String userType = user.getUserType();//判断账户类型，只有零售商或其员工登录才进行账户判断
            if(TSUser.USER_TYPE_02.equals(userType) || TSUser.USER_TYPE_05.equals(userType)){
    			//用户登录后验证账户余额是否正常
    			TStoreAccountInfoEntity tStoreAccountInfo = systemService.findUniqueByProperty(TStoreAccountInfoEntity.class,"retailerId", user.getId());
    			if(tStoreAccountInfo == null){  
    				request.setAttribute("accountStatus", "2");//返回状态2  客户账户不存在
    			}else{
    				String accountStatus = tStoreAccountInfo.getAccountStatus();
    				if(TStoreAccountInfoEntity.ACCOUNT_STATUS_BAD.equals(accountStatus)){  
    					request.setAttribute("accountStatus", TStoreAccountInfoEntity.ACCOUNT_STATUS_BAD);//客户账户余额不足
    				}
    			}
            }
			
			//update-start--Author:zhoujf Date:20150610 for:ace addOneTab无效问题
			Cookie cookie = new Cookie("SHARPINDEXSTYLE", sysTheme.getStyle());//使用尖刀产品菜单样式
			//设置cookie有效期为一个月
			cookie.setMaxAge(3600*24*30);
			response.addCookie(cookie);
			//update-end--Author:zhoujf Date:20150610 for:ace addOneTab无效问题
			
			//update-start--Author: jg_huangxg Date:20160330 for: zIndex索引问题
			Cookie zIndexCookie = new Cookie("ZINDEXNUMBER", "1990");
			zIndexCookie.setMaxAge(3600*24);//一天
			response.addCookie(zIndexCookie);
			//update-end--Author: jg_huangxg Date:20160330 for: zIndex索引问题
			return sysTheme.getIndexPath();//跳到菜单页面    ace_main.jsp
		} else {
			return "login/login1";
//			return "com/dagger/mobileFirstPage";
		}

	}
	
	 /**
	 * 用户登录(登录之前先要调用validateUserName验证用户名和密码)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "mobileFirstPage")
	public String mobileFirstPage(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		return "com/dagger/mobileFirstPage";
	}
	/**
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "modifyPassword", produces = "application/json;charset=UTF-8")
	public AjaxJson modifyPassword(HttpServletRequest request,
			HttpServletResponse response) {
		TSUser user = ResourceUtil.getSessionUserName();
		String username = request.getParameter("username");// 手机号码(非用户名)
		String validation = request.getParameter("validation");// 验证码
		String password = request.getParameter("password");// 密码
		String userType = request.getParameter("userType");// 用户类型

		AjaxJson j = new AjaxJson();
		if (user != null && TSUser.USER_STATUS_ACTIVE == user.getUserStatus()) {// 正常激活状态
			TSUserMessage message = tSUserMessageService.getLastObject(
					username, userType);

			if (Utility.isNotEmpty(message)
					&& message.getSendDate().getTime() >= efficientTime // 在规定的时间内
					&& validation.equals(message.getContent())) { // 短信验证码一致
				
				// 修改密码
				String encryptPassword = PasswordUtil.encrypt(username,
						password, PasswordUtil.getStaticSalt());
				user.setPassword(encryptPassword);// 将加密后的密码保存到数据库
				userService.updateEntitie(user);
			} else {
				j.setMsg("验证码验证失败");
		       	j.setObj("error");
		        j.setSuccess(false);
			}
		} else {
			j.setMsg("该手机号的对应的帐号不存在");
	       	j.setObj("error");
	        j.setSuccess(false);
		}
		return j;
	}
	
	
	
	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout1")
	public ModelAndView logout1(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		TSUser user = ResourceUtil.getSessionUserName();
		systemService.addLog("用户" + user.getUserName() + "已退出",
				Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
		ClientManager.getInstance().removeClinet(session.getId());
		session.invalidate();
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login1"));
		return modelAndView;
	}
	
	
	
	/*************************************尖刀产品end*******************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}