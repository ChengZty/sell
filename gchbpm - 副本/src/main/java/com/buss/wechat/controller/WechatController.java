package com.buss.wechat.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.PagerUtil;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.FtpUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import blade.kit.DateKit;
import blade.kit.StringKit;
import blade.kit.http.HttpRequest;
import blade.kit.http.HttpRequestException;
import blade.kit.json.JSON;
import blade.kit.json.JSONArray;
import blade.kit.json.JSONObject;
import cn.redis.service.RedisService;

import com.buss.wechat.utill.CookieUtil;
import com.buss.wechat.utill.JSUtil;
import com.buss.wechat.utill.Matchers;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import common.GlobalConstants;

@Scope("prototype")
@Controller
@RequestMapping("/wx")
public class WechatController extends BaseController {
	@Autowired
	private SystemService systemService;
	private static final String WEI_XIN_INFO = "WEI_XIN_INFO";
	private static final String WEI_XIN_FRIENDS = "WEI_XIN_FRIENDS";
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WechatController.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	// 微信特殊账号
	private List<String> SpecialUsers = Arrays.asList("newsapp", "fmessage", "filehelper", "weibo", "qqmail", "fmessage", "tmessage", "qmessage", "qqsync", "floatbottle", "lbsapp", "shakeapp", "medianote", "qqfriend", "readerapp", "blogapp", "facebookapp", "masssendapp", "meishiapp", "feedsapp", "voip", "blogappweixin", "weixin", "brandsessionholder", "weixinreminder", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "officialaccounts", "notification_messages", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "wxitil", "userexperience_alarm", "notification_messages");
	
	@Autowired
	private RedisService redisService;
	/**
	 * 跳转微信登录页面
	 * 扫码后记录USERID和二维码UUID绑定存到REDIS
	 */
	@RequestMapping(params = "index")
	public String index(HttpServletRequest request) {
		try
		{
			String   uuidkey = getUuidKey();
			Map<String, String> map =getInfo(uuidkey);
			if(Utility.isEmpty(map) || map.size() ==0){
				return "wechat/index";
			}else{//redis如果能查到信息则表明已经登录过
//				return "wechat/wechat";
				//验证登录状态
				String webpush_url = map.get("webpush_url");
				JSONObject BaseRequest =  JSON.parse( map.get("BaseRequest")+"").asObject();
				String cookie = map.get("cookie");
				String synckey = map.get("synckey");
				
				String url = webpush_url + "/synccheck";
				JSONObject body = new JSONObject();
				body.put("BaseRequest", BaseRequest);
				Long t1 = System.currentTimeMillis();
				HttpRequest req = HttpRequest.get(url, true,
						"r", DateKit.getCurrentUnixTime() + StringKit.getRandomNumber(5),
						"skey", BaseRequest.getString("Skey"),
						"uin", BaseRequest.getString("Uin"),
						"sid", BaseRequest.getString("Sid"),
						"deviceid","e" + DateKit.getCurrentUnixTime(),
						"synckey", synckey,
						"_", System.currentTimeMillis())
						.header("Cookie", cookie);
				
				String res = req.body();
				Long t2 = System.currentTimeMillis();
				System.out.println("验证登录状态耗时-----------------------------------"+(t2-t1)+"ms");
				req.disconnect();
				if(StringKit.isBlank(res)){
					redisService.del(uuidkey);
					return "wechat/index";
				}else{
					String retcode = Matchers.match("retcode:\"(\\d+)\",", res);
					//0: '发送成功',其他状态异常
					if("0".equals(retcode)){
						return "wechat/wechat";
					}else{
						redisService.del(uuidkey);
						return "wechat/index";
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "wechat/index";
	}
	
	/**
	 * 获取UUID
	 * @return
	 */
	@RequestMapping(params = "getQRCode")
	@ResponseBody
	public  Map<String,String> getQRCode() {
		Map<String,String> map = new HashMap<String, String>();
		try
		{
			String imgPath="";
			String uuid = "";
			String url = "https://login.weixin.qq.com/jslogin";
			HttpRequest request = HttpRequest.get(url, true, "appid", "wx782c26e4c19acffb","redirect_uri","https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage", "fun", "new","lang", "zh_CN","_" , DateKit.getCurrentUnixTime());
			logger.info("[*] " + request);
			String res = request.body();
			request.disconnect();
			if(StringKit.isNotBlank(res)){
				String code = Matchers.match("window.QRLogin.code = (\\d+);", res);
				if(null != code){
					if(code.equals("200")){
						uuid = Matchers.match("window.QRLogin.uuid = \"(.*)\";", res);
						String qrcode_url = "https://login.weixin.qq.com/qrcode/" + uuid;
						FtpUtil ftpUtil = FtpUtil.getInstance();
						String fileName = uuid.concat(".jpg");
						final File outputFile = new File(fileName);
						byte[] btImg = getImageFromNetByUrl(qrcode_url); 
						FileOutputStream fops = new FileOutputStream(outputFile);    
			            fops.write(btImg);    
			            fops.flush();    
			            fops.close(); 
						ftpUtil.uploadFileToFtpServer(GlobalConstants.WX_UUID_PIC,fileName, outputFile);
						imgPath = ResourceUtil.getConfigByName("imgWWW") + "/"+ ResourceUtil.getConfigByName("imgRootPath") + "/"+ GlobalConstants.WX_UUID_PIC + "/" + fileName;
						//删除文件
						if(outputFile.exists()){
							outputFile.delete();
						}
						
					} else {
						logger.info("[*] 错误的状态码:"+code);
					}
				}
			}
			map.put("uuid", uuid);
			map.put("imgPath", imgPath);
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return map;
	}
	
	
	  /**  
     * 根据地址获得数据的字节流  
     * @param strUrl 网络连接地址  
     * @return  
     */    
    public  byte[] getImageFromNetByUrl(String strUrl){    
        try {    
            URL url = new URL(strUrl);    
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
            conn.setRequestMethod("GET");    
            conn.setConnectTimeout(5 * 1000);    
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据    
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据    
            return btImg;    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return null;    
    }    
	
    /**  
     * 从输入流中获取数据  
     * @param inStream 输入流  
     * @return  
     * @throws Exception  
     */    
    public static byte[] readInputStream(InputStream inStream) throws Exception{    
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        while( (len=inStream.read(buffer)) != -1 ){    
            outStream.write(buffer, 0, len);    
        }    
        inStream.close();    
        return outStream.toByteArray();    
    }    
    
	/**
	 * 
	 * 等待扫码登录
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "waitForLogin")
	@ResponseBody
	public Map<String,String> waitForLogin(HttpServletRequest req) {
		Map<String,String> map = new HashMap<String, String>();
		try{
				String uuid = req.getParameter("uuid");
				String tip    = req.getParameter("tip");
				//默认为1
				if(Utility.isEmpty(tip)){
					tip = "1";
				}
				String url = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login";
		
				String time = DateKit.getCurrentUnixTime()+ StringKit.getRandomNumber(3);
				Long rnum = Long.valueOf(time)/1962;
				HttpRequest request = HttpRequest.get(url,true,"loginicon",true, "tip", Integer.valueOf(tip).intValue(), "uuid",uuid, "r",rnum,"_" , time);
				String res = request.body();
				request.disconnect();

				if(null == res){
					logger.info("[*] 扫描二维码验证失败");
					fillLoginMap(map,"None","1","扫描二维码验证失败");
					return map;
				}
				String code = Matchers.match("window.code=(\\d+);", res);
				if(null == code){
					logger.info("[*] 扫描二维码验证失败");
					fillLoginMap(map,"None","1","扫描二维码验证失败");
					return map;
				} else {
					if(code.equals("201")){
						logger.info("[*] 成功扫描,请在手机上点击确认以登录");
						fillLoginMap(map,code,"0","成功扫描,请在手机上点击确认以登录");
						String userAvatar = Matchers.match("window.userAvatar = '(\\S+)';", res);
						map.put("userAvatar", userAvatar);
						return map;
					} else if(code.equals("200")){
						logger.info("[*] 正在登录...");
						fillLoginMap(map,code,"0","正在登录...");
						Map<String,String> parsMap = new HashMap<String, String>();
						//重订向
						String pm = Matchers.match("window.redirect_uri=\"(\\S+?)\";", res);
						String redirectHost = "wx.qq.com";
						try 
						{
							URL pmURL = new URL(pm);
							redirectHost = pmURL.getHost();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						String pushServer = JSUtil.getPushServer(redirectHost);
						String webpush_url = "https://" + pushServer + "/cgi-bin/mmwebwx-bin";
						String redirect_uri = pm + "&fun=new";
						String base_uri =  redirect_uri.substring(0, redirect_uri.lastIndexOf("/"));
						parsMap.put("pushServer",pushServer);
						parsMap.put("webpush_url",webpush_url);
						parsMap.put("redirect_uri",redirect_uri);
						parsMap.put("base_uri",base_uri);
						parsMap.put("code",code);
						String uuidkey =getUuidKey();
						redisService.set(uuidkey, MAPPER.writeValueAsString(parsMap));
						
						return map;
					} else if(code.equals("400")){
						fillLoginMap(map,code,"0","登录超时");
						logger.info("[*] 登录超时");
						return map;
					} else {
						logger.info("[*] 扫描code]".concat(code) );
						fillLoginMap(map,code,"0","扫描code");
						return map;
					}
				}
		}catch (Exception e) {
			logger.info(e);
		}
		return map;
	}
	
	/**
	 * 登录参数
	 * @param map
	 * @param tip
	 * @param code
	 * @param msg
	 */
	private void fillLoginMap(Map<String, String> map,String code,String tip,String msg) {
		map.put("tip", tip);
		map.put("code", code);
		map.put("msg",msg);
	}
	
	/**
	 * 
	 * 获取用户的微信信息
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> getInfo(String uuidkey) {
		Map<String,String> map = null;
		String strMap = redisService.get(uuidkey);
		if(!StringKit.isBlank(strMap)){
			 try {
				map = MAPPER	.readValue(strMap, HashMap.class);
			}catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}else{
			 map = new HashMap<String, String>();
		}
		return map;
	}
	/**
	 * 获取UUIDKEY
	 * @return
	 */
	private String getUuidKey() {
		String userid = ResourceUtil.getSessionUserName().getId();
		String uuidkey = WEI_XIN_INFO+userid;
		return uuidkey;
	}

	/**
	 * 
	 * 重新获取二位码
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "reflush")
	public String reflush(HttpServletRequest request) {
		try 
		{
			String uuidkey =getUuidKey();
			redisService.del(uuidkey);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return index(request);
	}
	
	/**
	 * 
	 * 登录初始化
	 * @return
	 */
	@RequestMapping(params = "login")
	public String login() {
		try
		{
			String   uuidkey = getUuidKey();
			Map<String, String> map =getInfo(uuidkey);
//			String pushServer = map.get("pushServer");
//			String webpush_url = map.get("webpush_url");
			String redirect_uri = map.get("redirect_uri")+"";
			String base_uri =  map.get("base_uri")+"";
			String skey = null;
			String wxsid =null;
			String wxuin =null;
			String pass_ticket =null;
			JSONObject User =null;
			String cookie = map.get("cookie");
			//如果cookie为空，则获取cookie并初始化
			if(StringKit.isBlank(cookie))
			{
				//获取ticket
				HttpRequest rec_request = HttpRequest.get(redirect_uri);
				logger.info("[*] " + rec_request);
				String rec_res = rec_request.body();
				cookie = CookieUtil.getCookie(rec_request);
				map.put("cookie", cookie);
				rec_request.disconnect();
				JSONObject BaseRequest = new JSONObject();
				if(!StringKit.isBlank(rec_res)){
							skey = Matchers.match("<skey>(\\S+)</skey>", rec_res);
							wxsid = Matchers.match("<wxsid>(\\S+)</wxsid>", rec_res);
							wxuin = Matchers.match("<wxuin>(\\S+)</wxuin>", rec_res);
							pass_ticket = Matchers.match("<pass_ticket>(\\S+)</pass_ticket>", rec_res);
							BaseRequest.put("Uin",wxuin);
							BaseRequest.put("Sid",wxsid);
							BaseRequest.put("Skey",skey);
							BaseRequest.put("DeviceID","e" + DateKit.getCurrentUnixTime());
							map.put("BaseRequest",BaseRequest.toString());
							map.put("pass_ticket",pass_ticket);
				}
				//微信初始化
				String init_url =base_uri + "/webwxinit?r=" + DateKit.getCurrentUnixTime() + "&pass_ticket=" +pass_ticket +"&skey=" +skey;
					
				JSONObject body = new JSONObject();
				body.put("BaseRequest",BaseRequest);
					
				HttpRequest init_request = HttpRequest.post(init_url)
							.header("Content-Type", "application/json;charset=utf-8")
							.header("Cookie",cookie)
							.send(body.toString());
				String init_res = init_request.body();
				init_request.disconnect();
					
				if(!StringKit.isBlank(init_res)){
						JSONObject jsonObject = JSON.parse(init_res).asObject();
						if(null != jsonObject){
							JSONObject BaseResponse = jsonObject.getJSONObject("BaseResponse");
							if(null != BaseResponse){
								int ret = BaseResponse.getInt("Ret", -1);
								if(ret == 0){
									JSONObject SyncKey = jsonObject.getJSONObject("SyncKey");
									 User = jsonObject.getJSONObject("User");
									StringBuffer synckeyBuff = new StringBuffer();
									JSONArray list = SyncKey.getJSONArray("List");
									for(int i=0, len=list.size(); i<len; i++){
										JSONObject item = list.getJSONObject(i);
										synckeyBuff.append("|" + item.getInt("Key", 0) + "_" + item.getInt("Val", 0));
									}
									String synckey = synckeyBuff.substring(1);
									map.put("UserName", User.getString("UserName"));
									map.put("User", User.toString());
									map.put("synckey",synckey);
								}
							}
						}
				}
				redisService.set(uuidkey, MAPPER.writeValueAsString(map));
				
				//初始化消息通知
				String url = base_uri + "/webwxstatusnotify?lang=zh_CN&pass_ticket=" + pass_ticket;
				JSONObject stbody = new JSONObject();
				body.put("BaseRequest", BaseRequest);
				body.put("Code", 3);
				body.put("FromUserName",User.getString("UserName"));
				body.put("ToUserName", User.getString("UserName"));
				body.put("ClientMsgId", DateKit.getCurrentUnixTime());
				HttpRequest req4 = HttpRequest.post(url)
						.header("Content-Type", "application/json;charset=utf-8")
						.header("Cookie", cookie)
						.send(stbody.toString());
				req4.disconnect();
			}	
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}	
		return "wechat/wechat";
	}

	/**
	 * 获取联系人
	 */
	@RequestMapping(params = "getContact")
	public void getContact(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		
		JSONArray memberList = new JSONArray();
		ArrayList contactList = null;
		 
		 String userid = ResourceUtil.getSessionUserName().getId();
		String uuidkey = WEI_XIN_INFO+userid;
		String weiXinFriendKey =  WEI_XIN_FRIENDS+userid;
		
		try {
//			redisService.del(weiXinFriendKey);
			String value = redisService.get(weiXinFriendKey);
			  if(!Utility.isEmpty(value))
			  {
				  contactList = MAPPER.readValue(value, ArrayList.class);  
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(contactList == null || contactList.isEmpty()){
				contactList = new ArrayList();
				Map<String, String> map =getInfo(uuidkey);
				String base_uri =  map.get("base_uri")+"";
				String pass_ticket = map.get("pass_ticket")+"";
				String cookie =  map.get("cookie")+"";
				String userName = map.get("UserName");
				JSONObject BaseRequest =  JSON.parse( map.get("BaseRequest")+"").asObject();
				String skey =  BaseRequest.getString("Skey");
				
				String url =base_uri + "/webwxgetcontact?pass_ticket=" +pass_ticket + "&skey=" +skey + "&r=" + DateKit.getCurrentUnixTime();
				JSONObject cbody = new JSONObject();
				cbody.put("BaseRequest", BaseRequest);
				
				HttpRequest crequest = HttpRequest.post(url)
						.header("Content-Type", "application/json;charset=utf-8")
						.header("Cookie",cookie)
						.send(cbody.toString());
			
				String cres = crequest.body();
				crequest.disconnect();
			
				JSONObject jsonObject = JSON.parse(cres).asObject();
				JSONObject BaseResponse = jsonObject.getJSONObject("BaseResponse");
				if(null != BaseResponse){
					int cret = BaseResponse.getInt("Ret", -1);
					if(cret == 0){
						memberList = jsonObject.getJSONArray("MemberList");
						if(null != memberList){
							for(int i=0, len=memberList.size(); i<len; i++){
								JSONObject contact =memberList.getJSONObject(i);
								contact.set("id", Utility.getUUID());
								//公众号/服务号
								if(contact.getInt("VerifyFlag", 0) == 24){
									continue;
								}
								//公众号/服务号
								if(contact.getInt("VerifyFlag", 0) == 8){
									continue;
								}
								//群聊
								if(contact.getString("UserName").indexOf("@@") != -1){
									continue;
								}
								//特殊联系人
								if(SpecialUsers.contains(contact.getString("UserName"))){
									continue;
								}
								//自己
								if(contact.getString("UserName").equals(userName)){
									continue;
								}
								contactList.add(JSONHelper.json2Map(contact.toString()));
							}
						}
					}
				}
				try {
					redisService.set(weiXinFriendKey, MAPPER.writeValueAsString(contactList), GlobalConstants.WECHAT_REFRESH_TIME);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//查询条件组装器
		try{
			
			int totalSize = contactList.size();
			int pageSize = dataGrid.getRows();
			int curPageNo = dataGrid.getPage();
			int offset = PagerUtil.getOffset(totalSize, curPageNo,pageSize);//开始位置
			int endSet = (offset+pageSize > totalSize) ?totalSize:offset+pageSize;
			
			dataGrid.setResults(contactList.subList(offset, endSet));
			dataGrid.setTotal(contactList.size());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	/**
	 * 
	 * 猜测微信应该在某个时刻只能发送一条消息
	 * 建议生成MQ延时发送消息
	 * 发送消息
	 */
	@RequestMapping(params = "sendMsg")
	@ResponseBody
	public String sendMsg(HttpServletRequest req) {
		String msg = "success";
		try
		{
			String content = req.getParameter("content");
			String[] users = (req.getParameter("users")+"").split(",");
			String   uuidkey = getUuidKey();
			Map<String, String> map =getInfo(uuidkey);
			String base_uri =  map.get("base_uri")+"";
			JSONObject BaseRequest =  JSON.parse( map.get("BaseRequest")+"").asObject();
			final String cookie = map.get("cookie");
			String pass_ticket = map.get("pass_ticket")+"";
			String userName =  map.get("UserName")+"";
			final String url =base_uri + "/webwxsendmsg?lang=zh_CN&pass_ticket=" + pass_ticket;
			
			for(int i =0;i<users.length;i++){
				final  JSONObject body = new JSONObject();
				String clientMsgId = DateKit.getCurrentUnixTime() + StringKit.getRandomNumber(5);
				JSONObject Msg = new JSONObject();
				//1 文字   3图片  6文件 43 视频
				Msg.put("Type", 1);
				Msg.put("Content", content);
				Msg.put("FromUserName", userName);
				Msg.put("LocalID", clientMsgId);
				Msg.put("ClientMsgId", clientMsgId);
				Msg.put("ToUserName", users[i]);
				body.put("BaseRequest", BaseRequest);
				body.put("Msg", Msg);
				
        		Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						try
						{
				            HttpRequest request = HttpRequest.post(url)
									            .header("Content-Type", "application/json;charset=utf-8")
									            .header("Cookie", cookie)
									            .send(body.toString());
				            String res = request.body();
				            /*	返回格式如下Ret为0表示成功
				            "BaseResponse": {
				            	"Ret": 0,
				            	"ErrMsg": ""
				            	}
				            	,
				            	"MsgID": "5931475013271402636",
				            	"LocalID": "149438621363546"
				            	}*/
//				            System.out.println("-------------------发送微信消息的返回结果："+res);
							request.disconnect();
						}catch (Exception e) {
							e.printStackTrace();
							logger.info(e.getMessage());
						}
					}
				});
        		t.start();
        		t.sleep(200);
			}
			systemService.addLog("群发微信消息", Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
			return msg;
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			systemService.addLog("群发微信消息异常："+e.getMessage(), Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
			msg = "failure";
			return msg;
		}
	}
	
	/**
	 * 
	 * 退出
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "wxLogout")
	@ResponseBody
	public String wxLogout(HttpServletRequest request) {
		String msg = "success";
		try 
		{
			String uuidkey =getUuidKey();//用户的key
			String weiXinFriendKey =  WEI_XIN_FRIENDS+ResourceUtil.getSessionUserName().getId();//好友的key
			Map<String, String> map =getInfo(uuidkey);
			if(!Utility.isEmpty(map) && map.size() > 0)
			{
				JSONObject BaseRequest =  JSON.parse( map.get("BaseRequest")+"").asObject();
				String cookie = map.get("cookie");	
				String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxlogout?redirect=1&type=1&skey="+BaseRequest.getString("Skey");
				JSONObject body = new JSONObject();
				body.put("sid", BaseRequest.getString("Sid"));
				body.put("uin", BaseRequest.getString("Uin"));
				HttpRequest req = HttpRequest.post(url)
				.header("Content-Type", "application/json;charset=utf-8")
				.header("Cookie", cookie)
				.send(body.toString());
				 req.body();
				 req.disconnect();
				redisService.del(uuidkey);
				redisService.del(weiXinFriendKey);
			}
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			msg = "failure";
			return msg;
		}
	}
}