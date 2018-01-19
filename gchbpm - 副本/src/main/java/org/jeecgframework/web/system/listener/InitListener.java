package org.jeecgframework.web.system.listener;

import javax.servlet.ServletContextEvent;

import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.DynamicDataSourceServiceI;
import org.jeecgframework.web.system.service.MenuInitService;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

//import com.buss.sms.service.TSmsSendInfoServiceI;

import cn.redis.service.RedisService;


/**
 * 系统初始化监听器,在系统启动时运行,进行一些初始化工作
 * @author laien
 *
 */
public class InitListener  implements javax.servlet.ServletContextListener {

	
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		SystemService systemService = (SystemService) webApplicationContext.getBean("systemService");
		RedisService redisService = (RedisService) webApplicationContext.getBean("redisService");
		MenuInitService menuInitService = (MenuInitService) webApplicationContext.getBean("menuInitService");
		MutiLangServiceI mutiLangService = (MutiLangServiceI) webApplicationContext.getBean("mutiLangService");
		DynamicDataSourceServiceI dynamicDataSourceService = (DynamicDataSourceServiceI) webApplicationContext.getBean("dynamicDataSourceService");
//		TSmsSendInfoServiceI tSmsSendInfoService = (TSmsSendInfoServiceI) webApplicationContext.getBean("tSmsSendInfoService");
//		设置SSL
		System.setProperty("jsse.enableSNIExtension", "false");
		/**
		 * 第一部分：对数据字典进行缓存
		 */
//		systemService.initAllTypeGroups();
		redisService.initAllTypeGroups();//初始化字典
		systemService.initAllTSIcons();
		redisService.initAllSysParamters();//初始化所有系统参数
		
		/**
		 * 第二部分：自动加载新增菜单和菜单操作权限
		 * 说明：只会添加，不会删除（添加在代码层配置，但是在数据库层未配置的）
		 */
		if("true".equals(ResourceUtil.getConfigByName("auto.scan.menu.flag").toLowerCase())){
			menuInitService.initMenu();
		}
		
		/**
		 * 第三部分：加载多语言内容
		 */
		mutiLangService.initAllMutiLang();
		
		/**
		 * 第四部分：加载配置的数据源信息
		 */
		dynamicDataSourceService.initDynamicDataSource();
		
		/**
		 * 第五部分：服务重启后需要检查并重新启动设置定时发送短信的定时任务
		 * (如果只有一台服务器则需要启动此程序，最后把定时任务独立出来，并把该逻辑放入定时任务)
		 */
//		tSmsSendInfoService.sendMsgByTimer();
	}

}
