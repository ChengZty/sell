package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.State;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.util.LogUtil;

public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;

	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
		State state = null;

		if ("true".equals(this.conf.get("isBase64"))) {
			LogUtil.info("-----------Base64 start-----------");
			state = Base64Uploader.save(this.request.getParameter(filedName),this.conf);
			LogUtil.info("-----------Base64 end-----------");
		} else {
			 if("true".equals(this.conf.get("useFtpUpload")))
			 {	 
				 LogUtil.info("-----------useFtpUpload start-----------");
	              state = FtpUploader.save(request, conf);
	             LogUtil.info("-----------useFtpUpload end-----------");
			 }else
			 {
				 LogUtil.info("-----------BinaryUploader start-----------");
				 state = BinaryUploader.save(this.request, this.conf);
				 LogUtil.info("-----------BinaryUploader start-----------");
			 }	 
		}

		return state;
	}
}
