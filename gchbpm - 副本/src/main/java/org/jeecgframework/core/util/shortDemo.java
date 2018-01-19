package org.jeecgframework.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("deprecation")
public class shortDemo {  
    public static HttpClient httpclient;  
    static {  
        // 构造 HttpClient  
        httpclient = new DefaultHttpClient();  
    }  
  
    /**
     * 单个短链接生成
     * @param url
     * @return
     */
    public static String generateShortUrl(String url) {  
        try {  
            // 构造发送post请求  
            HttpPost httpost = new HttpPost();  
            List<NameValuePair> params = new ArrayList<NameValuePair>();  
            // 传递请求参数  
            params.add(new BasicNameValuePair("longUrl", url)); // 
            httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));  
            // 发送请求并且获取执行结果  
            HttpResponse response = httpclient.execute(httpost);  
            // 获取结果内容  
            String jsonStr = EntityUtils  
                    .toString(response.getEntity(), "utf-8");  
            // 将结果转成json对象  
            JSONObject object = JSON.parseObject(jsonStr);  
            // 获取短链接地址url  
            return object.getString("shortUrl");  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "Error";  
        }  
    }  
  
    /**
     * 批量短链接生成
     * @param urls
     * @return
     */
    public static String generateDoubleShortUrl(List<String> urls) {  
        try {  
            // 构造发送post请求  
            HttpPost httpost = new HttpPost("http://localhost:8080/guideApp/tBaseController.do?doubleShorten");  
            List<NameValuePair> params = new ArrayList<NameValuePair>();  
            // 传递请求参数  
            for (String url : urls) {
            	params.add(new BasicNameValuePair("longUrl", url)); // 
			}
            httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));  
            // 发送请求并且获取执行结果  
            HttpResponse response = httpclient.execute(httpost);  
            // 获取结果内容  
//            String jsonStr = EntityUtils  
//                    .toString(response.getEntity(), "utf-8");  
//            // 将结果转成json对象  
//            JSONObject object = JSON.parseObject(jsonStr);  
            // 获取短链接地址url  
//            return object.getString("shortUrl"); 
            return null;
        } catch (Exception e) {  
            e.printStackTrace();  
            return "Error";  
        }  
    }  
    
    public static void main(String[] args) {  
        List<String> list = new ArrayList<String>();  
        list.add("http://www.szwebi.com/18year/?utm_source=Baidu&utm_medium=search_cpc&utm_account=baidu-c2930187&utm_campaign=%20-sz&utm_adgroup=[%20]B%205-%20&utm_term=%80%20%20&utm_channel=baidu-c2930187");
        list.add("http://www.baidu.com");
        System.out.println(generateDoubleShortUrl(list));  
        System.out.println(generateShortUrl(list.get(0)));
    }  
}  