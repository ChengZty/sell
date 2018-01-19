package org.jeecgframework.core.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.FunctionVo;
import org.jeecgframework.web.system.pojo.base.TSFunctionVo;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cn.redis.service.RedisService;


/**
 * 权限拦截器
 * 
 * @author  
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {
	 
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);
	@Autowired
	private SystemService systemService;
	@Autowired
	private RedisService redisService;
	private List<String> excludeUrls;
//	private static List<TSFunction> functionList;
	private static List<TSFunctionVo> allFunctionVoList;//全部菜单voList


	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if(client == null){ 
			client = ClientManager.getInstance().getClient(request.getParameter("sessionId"));
		}
		if (excludeUrls.contains(requestPath)) {
			//如果该请求不在拦截范围内，直接返回true
			return true;
		} else {
			if (client != null && client.getUser()!=null ) {
				String clickFunctionId = request.getParameter("clickFunctionId");
				if((!hasMenuAuth(requestPath,clickFunctionId)) && !client.getUser().getUserName().equals("admin")){
					 response.sendRedirect("loginController.do?noAuth");
					return false;
				} 
				
				//Step.1 第一部分处理页面表单和列表的页面控件权限（页面表单字段+页面按钮等控件）
				//TODO 菜单页面权限控制相关代码暂时未用到
				/*
				String functionId = "";
				List<TSFunction> myFunctionList = ClientManager.getInstance().getClient(ContextHolderUtils.getSession().getId()).getFunctionList();
				for (TSFunction function : myFunctionList) {
					if (function.getFunctionUrl() != null && function.getFunctionUrl().startsWith(requestPath)) {
						functionId = function.getId();
						break;
					}
				}
				if(!oConvertUtils.isEmpty(functionId)){
					//获取菜单对应的页面控制权限（包括表单字段和操作按钮）
					Set<String> operationCodes = systemService.getOperationCodesByUserIdAndFunctionId(client.getUser().getId(), functionId);
					request.setAttribute(Globals.OPERATIONCODES, operationCodes);
				}
				if(!oConvertUtils.isEmpty(functionId)){
					List<TSOperation> allOperation=this.systemService.findByProperty(TSOperation.class, "TSFunction.id", functionId);
					
					List<TSOperation> newall = new ArrayList<TSOperation>();
					if(allOperation.size()>0){
						for(TSOperation s:allOperation){ 
						    //s=s.replaceAll(" ", "");
							newall.add(s); 
						}
//---author:jg_xugj----start-----date:20151210--------for：#781  【oracle兼容】兼容问题fun.operation!='' 在oracle 数据下不正确
						String hasOperSql="SELECT operation FROM t_s_role_function fun, t_s_role_user role WHERE  " +
							"fun.functionid='"+functionId+"' AND fun.operation is not null  AND fun.roleid=role.roleid AND role.userid='"+client.getUser().getId()+"' ";
//---author:jg_xugj----end-----date:20151210--------for：#781  【oracle兼容】兼容问题fun.operation!='' 在oracle 数据下不正确
						List<String> hasOperList = this.systemService.findListbySql(hasOperSql); 
					    for(String operationIds:hasOperList){
							    for(String operationId:operationIds.split(",")){
							    	operationId=operationId.replaceAll(" ", "");
							        TSOperation operation =  new TSOperation();
							        operation.setId(operationId);
							    	newall.remove(operation);
							    } 
						} 
					}
					request.setAttribute(Globals.NOAUTO_OPERATIONCODES, newall);
					
					 //Step.2  第二部分处理列表数据级权限
					 //小川 -- 菜单数据规则集合(数据权限)
					 List<TSDataRule> MENU_DATA_AUTHOR_RULES = new ArrayList<TSDataRule>(); 
					 //小川 -- 菜单数据规则sql(数据权限)
					 String MENU_DATA_AUTHOR_RULE_SQL="";

					
				 	//数据权限规则的查询
				 	//查询所有的当前这个用户所对应的角色和菜单的datarule的数据规则id
					Set<String> dataruleCodes = systemService.getOperationCodesByUserIdAndDataId(client.getUser().getId(), functionId);
					request.setAttribute("dataRulecodes", dataruleCodes);
					for (String dataRuleId : dataruleCodes) {
						TSDataRule dataRule = systemService.flushEntity(TSDataRule.class, dataRuleId);
						    MENU_DATA_AUTHOR_RULES.add(dataRule);
							MENU_DATA_AUTHOR_RULE_SQL += SysContextSqlConvert.setSqlModel(dataRule);
					
					}
					 JeecgDataAutorUtils.installDataSearchConditon(request, MENU_DATA_AUTHOR_RULES);//菜单数据规则集合
					 JeecgDataAutorUtils.installDataSearchConditon(request, MENU_DATA_AUTHOR_RULE_SQL);//菜单数据规则sql

				}*/
				//刷新redis中session存活时间
				redisService.expire(session.getId(), 3600);//刷新1小时生存时间
				return true;
			} else {
//				ClientManager.getInstance().removeClinet(sessionId);//3600s后自动删除
				forward(request, response);
				return false;
			}

		}
	}
	
	/**
	 * 判断用户是否有菜单访问权限
	 * @param request
	 * @return
	 */
	private boolean hasMenuAuth(String requestPath,String functionId){
		// 是否是功能表中管理的url
		boolean bMgrUrl = false;
		if (allFunctionVoList == null) {
			String sql = "select id functionId,functionname functionName,functionurl functionUrl from t_s_function where status = 'A'";
			allFunctionVoList = systemService.findObjForJdbc(sql, TSFunctionVo.class);
//			functionList = systemService.findHql("from TSFunction where functionType = ? ", (short)0);
		}
		for (TSFunctionVo function : allFunctionVoList) {
			if (function.getFunctionId().equals(functionId)) {
				bMgrUrl = true;//全部功能菜单中有此菜单
				break;
			}
//			if (function.getFunctionUrl() != null && function.getFunctionUrl().startsWith(requestPath)&&function.getFunctionId().equals(functionId)) {
//				bMgrUrl = true;//全部功能菜单中有此菜单
//				break;
//			}
		}
		if (!bMgrUrl) {
			return true;
		}else{//全部功能菜单中有此菜单，校验是否是当前登录者的角色所拥有的菜单
			List<FunctionVo> myFunctionTree = ClientManager.getInstance().getClient(ContextHolderUtils.getSession().getId()).getFunctionList();
			List<TSFunctionVo> myFunctionVoList = new ArrayList<TSFunctionVo>();//非树结构菜单列表
			this.getFunctionVoListFromTree(myFunctionVoList,myFunctionTree);
			//判断当前访问菜单是否是在本人的权限菜单中
			for(TSFunctionVo function : myFunctionVoList){
				if (function.getFunctionId().equals(functionId)) {
					return true;
				}
			}
		}
		 
//		String funcid=oConvertUtils.getString(request.getParameter("clickFunctionId"));
//		if(!bMgrUrl && (requestPath.indexOf("loginController.do")!=-1||funcid.length()==0)){
//			return true;
//		}
		
//		TSUser currLoginUser = ClientManager.getInstance().getClient(ContextHolderUtils.getSession().getId()).getUser();
//        String userid = currLoginUser.getId();
		//requestPath=requestPath.substring(0, requestPath.indexOf("?")+1);
//		String sql = "SELECT DISTINCT f.id FROM t_s_function f,t_s_role_function  rf,t_s_role_user ru " +
//					" WHERE f.id=rf.functionid AND rf.roleid=ru.roleid AND " +
//					"ru.userid='"+userid+"' AND f.functionurl like '"+requestPath+"%'";
//		List list = this.systemService.findListbySql(sql);
//		if(list.size()==0){
//            String functionOfOrgSql = "SELECT DISTINCT f.id from t_s_function f, t_s_role_function rf  " +
//                    "WHERE f.ID=rf.functionid  AND f.functionurl like '"+requestPath+"%'";
//            List functionOfOrgList = this.systemService.findListbySql(functionOfOrgSql);
//			return functionOfOrgList.size() > 0;
//        }else{
//			return true;
//		}
		return false;
	}
	
	/**迭代菜单数结构遍历所有子菜单并放入functionVoList中*/
	private void getFunctionVoListFromTree(List<TSFunctionVo> functionVoList,List<FunctionVo> myFunctionTree) {
		for(FunctionVo f : myFunctionTree){
//			List<TSFunction> subFunctions = f.getTSFunctions();
			List<FunctionVo> subFunctions = f.getSubFunctionList();
			if(Utility.isNotEmpty(f.getFunctionUrl())){
//				System.out.println(f.getFunctionName()+"===="+f.getFunctionLevel()+"============"+f.getFunctionUrl());
				TSFunctionVo vo = new TSFunctionVo();
				vo.setFunctionId(f.getId());
				vo.setFunctionUrl(f.getFunctionUrl());
				vo.setFunctionName(f.getFunctionName());
				functionVoList.add(vo);
			}
			if(subFunctions.size()>0){
				getFunctionVoListFromTree(functionVoList,subFunctions);
			}
		}
	}
	
	/**
	 * 转发
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "forword")
	public ModelAndView forword(HttpServletRequest request) {
		return new ModelAndView(new RedirectView("loginController.do?login"));
	}

	private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map params = request.getParameterMap();// 请求参数
		String[] funTp =  (String[]) params.get("funTp");
		if(Utility.isNotEmpty(funTp)&&"1".equals(funTp[0])){//超时后点击login1里面的菜单
			request.getRequestDispatcher("webpage/login/timeout.jsp?funTp=1").forward(request, response);
		}else{
			request.getRequestDispatcher("webpage/login/timeout.jsp").forward(request, response);
		}
	}

}
