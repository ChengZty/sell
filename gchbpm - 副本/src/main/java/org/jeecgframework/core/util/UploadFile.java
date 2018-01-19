package org.jeecgframework.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.storage.Configuration;
import com.qiniu.common.QiniuException;
import com.qiniu.processing.OperationManager;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
/**
 * 7牛
 * @author lenovo
 *
 */
public class UploadFile {
	private static final String ACCESS_KEY = ResourceUtil.getConfigByName("QN_ACCESS_KEY");;
	private static final String SECRET_KEY = ResourceUtil.getConfigByName("QN_SECRET_KEY");
	//要上传的空间
	private static final String bucket = ResourceUtil.getConfigByName("QN_bucketname");
	public static String uptoken = null;
	private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	//自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
	private static Zone z = Zone.autoZone();
	private static Configuration c = new Configuration(z);
	 //创建上传对象
	private static UploadManager uploadManager = new UploadManager(c);
	private static BucketManager bucketManager = new BucketManager(auth,c);
	  
	 /* public static String getUptoken()
	  {
	    return Auth.create(ACCESS_KEY, SECRET_KEY).uploadToken(bucket);
	  }
//弃用，改为放入redis共用
	  public static void genUptoken()
	  {
		    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		    executor.scheduleAtFixedRate(new Runnable() {
		      public void run() {
		    	  UploadFile.uptoken = UploadFile.getUptoken();
		      }
		    }
		    , 0L, 3500L, TimeUnit.SECONDS);
	  }
	  */
	  
	  /**上传
	 * @param is
	 * @param newFileName
	 * @return
	 */
	public static boolean upload(InputStream is,String newFileName){
		try {
		      //调用put方法上传
			  byte[] b = new byte[is.available()];
		      is.read(b);
		      Response res = uploadManager.put(b, newFileName, uptoken);
		      //打印返回的信息
		      System.out.println(res.bodyString()); 
		      return true;
	      } catch (Exception e) {
	          // 请求失败时打印的异常的信息
	          e.printStackTrace();
	          return false;
	      }  
	 }
	
	 /**上传
	 * @param is
	 * @param newFileName
	 * @return
	 */
	public static boolean upload(InputStream is,String newFileName,String uptoken){
		try {
		      //调用put方法上传
			  byte[] b = new byte[is.available()];
		      is.read(b);
		      Response res = uploadManager.put(b, newFileName, uptoken);
		      //打印返回的信息
		      System.out.println(res.bodyString()); 
		      return true;
	      } catch (Exception e) {
	          // 请求失败时打印的异常的信息
	          e.printStackTrace();
	          return false;
	      }  
	 }
	
	 /**
     * 上传数据
     * @param data  上传的数据
     * @param key   上传数据保存的文件名
     */
	public static boolean upload(final byte[] data, final String key){
		try {
		      //调用put方法上传
		      Response res = uploadManager.put(data, key, uptoken);
		      //打印返回的信息
		      System.out.println(res.bodyString()); 
		      return true;
	      } catch (Exception e) {
	          // 请求失败时打印的异常的信息
	          e.printStackTrace();
	          return false;
	      }  
	 }
	  
     /**上传
     * @param filePath 上传的文件路径
     * @param key 上传文件保存的文件名
     * @return
     */
    public static boolean upload(String filePath,String key){
		  try 
		  {
		      //调用put方法上传
		      Response res = uploadManager.put(filePath, key, uptoken);
		      //打印返回的信息
		      System.out.println(res.bodyString()); 
		      return true;
	      } catch (Exception e) {
	          // 请求失败时打印的异常的信息
	          e.printStackTrace();
	          return false;
	      }  
	 }
     
     /**删除
     * @param key
     */
    public static void delete(String key){
		  try 
		  {
		      //调用delete方法删除
		      bucketManager.delete(bucket, key);
	      } catch (Exception e) {
	          // 请求失败时打印的异常的信息
	          e.printStackTrace();
	      }  
	 }
    
    /**服务器的视频进行转码（转码是异步进行）
     * @param key 七牛服务器文件的key
     * @param fops 转码操作参数  如：avthumb/mp4/s/640x360/vb/64k
     * @param pipeline 转码的队列
     * @param newKey 保存的文件key
     */
    public void convertVideo(String key,String fops,String pipeline,String notifyUrl,String newKey) {
    	OperationManager operater = new OperationManager(auth,c);
//    	String key = "aaa/小幸运.mp4";
//    	  String newKey = "aaa/小幸运222.mp4";
    	  //设置转码操作参数
    	  //设置转码的队列
    	//可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
    	//  String urlbase64 = UrlSafeBase64.encodeToString("目标Bucket_Name:自定义文件key");
    	  String urlbase64 = UrlSafeBase64.encodeToString(bucket+":"+newKey);
    	  String pfops = fops + "|saveas/"+urlbase64;
    	    //设置pipeline参数（notifyURL转码成功后回调地址）
    	    StringMap params = new StringMap().putWhen("force", 1, true).putNotEmpty("pipeline", pipeline).putNotEmpty("notifyURL", notifyUrl);
    	    try {
    	      String persistid = operater.pfop(bucket, key, pfops, params);
    	      //打印返回的persistid（转码是异步进行）
    	      System.out.println(persistid);
    	    } catch (QiniuException e) {
    	      //捕获异常信息
    	        Response r = e.response;
    	        // 请求失败时简单状态信息
    	        System.out.println(r.toString());
    	        try {
    	           // 响应的文本信息
    	          System.out.println(r.bodyString());
    	        } catch (QiniuException e1) {
    	            //ignore
    	        }
    	    }
   	}
    
    /**获取视频信息
     * @param videoUrl 视频外链+?avinfo
     * @return
     */
    public static String getVideoInfo(String videoUrl) {
    	HttpGet request = new HttpGet(videoUrl);
        HttpResponse response = null;
	 	try {
	 		response = HttpClients.createDefault().execute(request);
	 	} catch (ClientProtocolException e1) {
	 		e1.printStackTrace();
	 	} catch (IOException e1) {
	 		e1.printStackTrace();
	 	} 
	        HttpEntity entity = response.getEntity();
			try {
				String retSrc = EntityUtils.toString(entity, "UTF-8");
				return retSrc;
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	          return null;
	 }
    
     
     /**上传转码视频
     * @param FilePath 文件路径
     * @param fops 转码操作参数  如：avthumb/mp4/s/640x360/vb/64k
     * @param pipeline 转码的队列
     * @param fileName 保存的文件名
     * @return bodyString
     */
    public static String uploadVideo(File file,String fops,String pipeline,String newKey) {
    	  //可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
    	String urlbase64 = UrlSafeBase64.encodeToString(bucket+":"+newKey);
    	    String pfops = fops + "|saveas/" + urlbase64;
    	    try {
                //调用put方法上传
                Response res = uploadManager.put(file, null, getUpToken(pfops, pipeline,null));
                //打印返回的信息
                System.out.println(res.bodyString());
                return res.bodyString();
            } catch (QiniuException e) {
                Response r = e.response;
                // 请求失败时打印的异常的信息
                System.out.println(r.toString());
                try {
                    //响应的文本信息
                    System.out.println(r.bodyString());
                } catch (QiniuException e1) {
                    //ignore
                }
            }
            return null;
   	}
     
     //上传策略中设置persistentOps字段和persistentPipeline字段，persistentNotifyUrl字段
     public static String getUpToken(String pfops,String pipeline,String notifyUrl) {
         return auth.uploadToken(bucket, null, 3600, new StringMap()
                 .putNotEmpty("persistentOps", pfops)
                 .putNotEmpty("persistentNotifyUrl", notifyUrl)
                 .putNotEmpty("persistentPipeline", pipeline), true);
     }
   
}
