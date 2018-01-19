package org.jeecgframework.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

@SuppressWarnings("deprecation")
public class SendSMS {
	
	/**
	 * 发送短信
	 * @param mob
	 * @param msg
	 * @return
	 */
	public  boolean send(String mob, String msg) {
		boolean isSuccess = false;
		String messageType = ResourceUtil.getConfigByName("messageType");
		if("1".equals(messageType)){//第一家短信平台
			isSuccess = this.send1(mob, msg);
		} else if("2".equals(messageType)){//上海助通短信平台
			isSuccess = this.send2(mob, msg);
		}
		return isSuccess;
	}

	/**
	 *  发送短信 
	 * @param mob  手机号码
	 * @param msg  (随机六位数)
	 * @return
	 */
	public  boolean send1(String mob, String msg) {
		String str = "";
        try {
        	String url = ResourceUtil.getConfigByName("url");
        	String userCode = ResourceUtil.getConfigByName("userCode");
        	String userPass = ResourceUtil.getConfigByName("userPass");
        	// 创建HttpClient实例     
            HttpClient httpclient = new DefaultHttpClient();
             //构造一个post对象
             HttpPost httpPost = new HttpPost(url);
             //添加所需要的post内容
             List<NameValuePair> nvps = new ArrayList<NameValuePair>();
             nvps.add(new BasicNameValuePair("userCode", userCode));
             nvps.add(new BasicNameValuePair("userPass", userPass));
             nvps.add(new BasicNameValuePair("DesNo", mob));
             nvps.add(new BasicNameValuePair("Msg", msg));
             nvps.add(new BasicNameValuePair("Channel", "0"));

             httpPost.setEntity( new UrlEncodedFormEntity(nvps, "UTF-8") );
             HttpResponse response = httpclient.execute(httpPost);
             HttpEntity entity = response.getEntity();
             if (entity != null) {    
            	 InputStream instreams = entity.getContent();    
            	 str = convertStreamToString(instreams);  
                 //System.out.println(str);  
             }  
//        	HttpRequestSender httpRequest = new HttpRequestSender();
//        	//发送 POST 请求
//        	 String sr=WeixinUtil.httpRequest(
//        			 "http://h.1069106.com:1210/Services/MsgSend.asmx/SendMsg", 
//        			 "POST",
//             		 "userCode=XXXXX&userPass=XXXXX&DesNo="+mob+"&Msg="+msg+"&Channel=1");
            Document doc = null;
            doc = DocumentHelper.parseText(str); // 将字符串转为XML

            if (doc == null ) return false;
            Element rootElt = doc.getRootElement(); // 获取根节点
            if (rootElt == null ) return false;
            //System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
            //System.out.println("根节点的值：" + rootElt.getText()); // 拿到根节点的名称
            if (rootElt.getText() == null || "".equals(rootElt.getText())) return false;
            if (Long.parseLong(rootElt.getText()) > 0 ) {
            	LogUtil.info("短信发送成功");
            	return true;
            } else {
            	LogUtil.info("短信发送失败");
            	return false;
            }
        } catch (DocumentException e) {
        	LogUtil.info("短信发送失败");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
        	LogUtil.info("短信发送失败");
            e.printStackTrace();
            return false;
        }
	}
	
	/**
	 *  助通      发送短信 
	 * @param mob  手机号码
	 * @param msg  (随机六位数组成的短信内容)
	 * @return
	 */
	public  boolean send2(String mob, String msg) {
		boolean isSuccess = false;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
        	String url = ResourceUtil.getConfigByName("url");//短信接口url
        	String userCode = ResourceUtil.getConfigByName("userCode");
        	String userPass = ResourceUtil.getConfigByName("userPass");
        	String productId = ResourceUtil.getConfigByName("productId");
        	String tkey = DateUtils.formatDate(DateUtils.getCalendar(), "yyyyMMddHHmmss");
        	String password = MD5Gen.getMD5(MD5Gen.getMD5(userPass)+tkey);
        	
        	msg=URLEncoder.encode(msg,"utf-8");//传递的中文进行编码
        	StringBuilder param = new StringBuilder();
        	param.append("url=").append(url).append("&username=").append(userCode).append("&password=").append(password)
        	.append("&tkey=").append(tkey).append("&mobile=").append(mob).append("&content=").append(msg).append("&xh")
        	.append("&productid=").append(productId);
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置相应请求时间
            conn.setConnectTimeout(30000);
            //设置读取超时时间
            conn.setReadTimeout(30000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param.toString());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            String[] resultMsg = result.split(",");
            if("1".equals(resultMsg[0])){
            	isSuccess = true;
            	LogUtil.info("短信发送成功："+result);
            }else{
            	LogUtil.info("短信发送失败："+result);
            }
        } catch (Exception e) {
        	LogUtil.info("短信发送失败："+result);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }
	
	public static String convertStreamToString(InputStream is) {      
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
        StringBuilder sb = new StringBuilder();      
       
        String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {  
                sb.append(line + "\n");      
            }      
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb.toString();      
    }  
  
	/**
	 * 获得六位随机数字
	 * @return
	 */
	public static int getSixRandomNum(){
		return (int)((Math.random()*9+1)*1000);
	}
	
	/**
	 * 获得随机六位数字的验证码<msg,valideate>
	 * @return
	 */
	public Map<String, String> getValidationCode(){
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		int SixRandomNum = getSixRandomNum();
		sb.append("校验码");
		sb.append(SixRandomNum);
		sb.append("，请于2分钟内输入。如非本人操作，请注意帐号安全，不要把校验码透露给他人。");
		sb.append("【生活真选】");
		map.put("Msg", sb.toString());
		map.put("SixRandomNum", SixRandomNum + "");
		return map;
	}
}
