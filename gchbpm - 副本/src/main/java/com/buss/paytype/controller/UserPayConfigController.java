package com.buss.paytype.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.buss.paytype.entity.UserPayConfigEntity;

import common.GlobalConstants;

/**
 * @Title: Controller
 * @Description: 支付配置
 * @author onlineGenerator
 * @date 2016-03-16 16:23:32
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/userPayConfigController")
public class UserPayConfigController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(UserPayConfigController.class);

	@Autowired
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 支付方式列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/paytype/userPayConfigList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(UserPayConfigEntity userPayConfig,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(UserPayConfigEntity.class,
				dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				userPayConfig, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除支付配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(UserPayConfigEntity userPayConfig,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		userPayConfig = systemService.flushEntity(UserPayConfigEntity.class,
				userPayConfig.getId());
		message = "支付配置删除成功";
		try {
			userPayConfig.setStatus(common.GlobalConstants.STATUS_INACTIVE);
			userPayConfig.setUpdateDate(Utility.getCurrentTimestamp());
			systemService.updateEntitie(userPayConfig);
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "支付配置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除支付配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "支付配置删除成功";
		try {
			for (String id : ids.split(",")) {
				UserPayConfigEntity userPayConfig = systemService.flushEntity(
						UserPayConfigEntity.class, id);
				userPayConfig.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				userPayConfig.setUpdateDate(Utility.getCurrentTimestamp());
				systemService.updateEntitie(userPayConfig);
				systemService.addLog(message, Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "支付配置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 支付配置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(UserPayConfigEntity userPayConfig,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(userPayConfig.getId())) {
			userPayConfig = systemService.flushEntity(
					UserPayConfigEntity.class, userPayConfig.getId());
			req.setAttribute("userPayConfigPage", userPayConfig);
		}
		return new ModelAndView("com/buss/paytype/userPayConfig-add");
	}

	/**
	 * 支付配置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(UserPayConfigEntity userPayConfig,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(req.getParameter("id"))) {
			userPayConfig = systemService.flushEntity(
					UserPayConfigEntity.class, req.getParameter("id"));
			req.setAttribute("userPayConfigPage", userPayConfig);
		}
		return new ModelAndView("com/buss/paytype/userPayConfig-update");
	}

	/**
	 * 添加支付配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(UserPayConfigEntity userPayConfig,
			HttpServletRequest request) {
		userPayConfigParamsTrim(userPayConfig);
		
		AjaxJson j = new AjaxJson();
		message = "支付配置添加成功";
		try {
			List<UserPayConfigEntity> t = systemService.findByProperty(
					UserPayConfigEntity.class, "sid", userPayConfig.getSid());
			// UserPayConfigEntity t =
			// systemService.get(UserPayConfigEntity.class,
			// userPayConfig.getSid());
			if (t == null || t.size() < 1) {
				systemService.save(userPayConfig);
				systemService.addLog(message, Globals.Log_Type_INSERT,
						Globals.Log_Leavel_INFO);
			} else {
				message = "支付配置添加失败，零售商配置已存在！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "支付配置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	//零售商支付参数去掉空格
	private void userPayConfigParamsTrim(UserPayConfigEntity userPayConfig) {
		userPayConfig.setAppid(userPayConfig.getAppid().trim());
		userPayConfig.setPid(userPayConfig.getPid().trim());
		userPayConfig.setRsaPrivateKey(userPayConfig.getRsaPrivateKey().trim());
		userPayConfig.setRsaPublicKey(userPayConfig.getRsaPublicKey().trim());
		userPayConfig.setSid(userPayConfig.getSid().trim());
		userPayConfig.setStoreName(userPayConfig.getStoreName().trim());
		userPayConfig.setWxAppId(userPayConfig.getWxAppId().trim());
		userPayConfig.setWxAppSectet(userPayConfig.getWxAppSectet().trim());
		userPayConfig.setWxMerchantId(userPayConfig.getWxMerchantId().trim());
		userPayConfig.setWxPartnerKey(userPayConfig.getWxPartnerKey().trim());
	}

	/**
	 * 更新支付配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(UserPayConfigEntity userPayConfig,
			HttpServletRequest request) {
		userPayConfigParamsTrim(userPayConfig);
		AjaxJson j = new AjaxJson();
		message = "支付配置更新成功";
		UserPayConfigEntity t = systemService.get(UserPayConfigEntity.class,
				userPayConfig.getId());
		try {
			if (Utility.isEmpty(t)) {
				systemService.save(userPayConfig);
				systemService.addLog(message, Globals.Log_Type_INSERT,
						Globals.Log_Leavel_INFO);
				message = "零售商配置不存在，已创建！";
			} else {
				MyBeanUtils.copyBeanNotNull2Bean(userPayConfig, t);
				systemService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "支付配置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 添加支付配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "checkStore")
	@ResponseBody
	public AjaxJson checkStore(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String sid = request.getParameter("sid");
		message = "零售商配置不存在，验证成功";
		try {
			List<UserPayConfigEntity> t = systemService.findByProperty( UserPayConfigEntity.class, "sid", sid);
			if (t == null || t.size() < 1) {
				j.setObj("200");
			} else {
				message = "零售商配置已存在，请重新选择！";
				j.setObj("500");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "验证失败，请重新选择";
			j.setObj("500");
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "userPayConfigController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(UserPayConfigEntity userPayConfig, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(UserPayConfigEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, userPayConfig, request.getParameterMap());
		List<UserPayConfigEntity> userPayConfigs = this.systemService.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "零售商支付配置");
		modelMap.put(NormalExcelConstants.CLASS, UserPayConfigEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("零售商支付配置列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, userPayConfigs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(UserPayConfigEntity userPayConfig, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "零售商支付配置");
		modelMap.put(NormalExcelConstants.CLASS, UserPayConfigEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("零售商支付配置列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<UserPayConfigEntity> listBasePayTypeEntitys = ExcelImportUtil.importExcel(file.getInputStream(), UserPayConfigEntity.class, params);
				for (UserPayConfigEntity basePayType : listBasePayTypeEntitys) {
					systemService.save(basePayType);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
