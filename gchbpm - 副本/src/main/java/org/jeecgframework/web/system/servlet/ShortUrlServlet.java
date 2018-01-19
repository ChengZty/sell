/**
 * 
 */
package org.jeecgframework.web.system.servlet;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.redis.service.RedisService;

import com.buss.sms.entity.TSmsSendEntity;

/**
 * 端连接拦截重定向
 */
public class ShortUrlServlet extends HttpServlet {
    private static final long serialVersionUID = -1257947018545327308L;
    private RedisService redisService;
    private SystemService systemService;
	private String shortUrlDomain ;//短连接域名
	private String errorPage ;//错误页面地址
	private String site ;//重定向页面地址

	@Autowired
	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	@Autowired
	public SystemService getSystemService() {
		return systemService;
	}
	
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public String getShortUrlDomain() {
		return shortUrlDomain;
	}

	public void setShortUrlDomain(String shortUrlDomain) {
		this.shortUrlDomain = shortUrlDomain;
	}

    
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@Override
	public void doGet(final HttpServletRequest request,	final HttpServletResponse response) {
		
		// 设置响应内容类型
		response.setContentType("text/html;charset=UTF-8");
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
//		System.out.println(requestPath);
		if(Utility.isEmpty(errorPage)){
			ResourceBundle env = ResourceBundle.getBundle("env");
			errorPage = env.getObject("HT_URL")+"webpage/common/errorPage.jsp";
		}
		if(Utility.isEmpty(redisService)){
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
  		  	redisService = (RedisService) webApplicationContext.getBean("redisService");
		}
		try {
			//以  1/asdfdd的端连接形式进行拦截跳转
		      if(null!=requestPath&&!"null".equals(requestPath)&&requestPath.contains("1/")&&requestPath.indexOf("1/")==0){
		    	  String shortUrl =requestPath.substring(2,requestPath.indexOf("?"));
		    	  if(StringUtil.isNotEmpty(shortUrl)){
		    		  if(shortUrl.length()>6){
		    			  shortUrl = shortUrl.substring(0, 6);
		    		  }
//		    		  LogUtil.info("shortUrl  key----1/"+shortUrl);
		    		  String longUrl = redisService.get("1/"+shortUrl);
		    		  if(StringUtil.isNotEmpty(longUrl)){
		    			// 要重定向的新位置
		    			  site = longUrl;
		    		  }else{//redis查询不到数据，去数据库查询
		    			  if(StringUtil.isEmpty(shortUrlDomain)){
		    				  ResourceBundle env = ResourceBundle.getBundle("env");
		    				  shortUrlDomain = env.getObject("SHORT_URL")+"";
		    			  }else{
		    				  if(Utility.isEmpty(systemService)){
		    					  WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		    					  systemService = (SystemService) webApplicationContext.getBean("systemService");
		    					  
		    				  }
//		    				  LogUtil.info("TSmsSendEntity  shortUrl----"+shortUrlDomain+shortUrl);
	    					  TSmsSendEntity entity = systemService.findUniqueByProperty(TSmsSendEntity.class, "shortUrl", shortUrlDomain+shortUrl);
	    					  if(Utility.isEmpty(entity)){//未查询到对应的连接
	    						  LogUtil.info("TSmsSendEntity为空");
	    						  site = errorPage;
	    					  }else{
	    						  site = entity.getLongUrl();
	    					  }
		    			  }
//		    			  site = errorPage;
//		    			  site = "\\webpage\\common\\404.jsp";
		    		  }
		    	  }else{//网页过期 或者不存在
		    		  site = errorPage;
		    	  }
		      }else{//网页过期 或者不存在
		    	  site = errorPage;
		      }
//		      response.setStatus(response.SC_MOVED_PERMANENTLY);
		      response.setStatus(response.SC_MOVED_TEMPORARILY);
		      response.setHeader("Location", site);    
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
			//网页过期 或者不存在
			 site = errorPage;
//			response.setStatus(response.SC_MOVED_PERMANENTLY);
			response.setStatus(response.SC_MOVED_TEMPORARILY);
		    response.setHeader("Location", site); 
		}

	}

	@Override
	public void doPost(final HttpServletRequest request,final HttpServletResponse response){
		doGet(request, response);
	}

}