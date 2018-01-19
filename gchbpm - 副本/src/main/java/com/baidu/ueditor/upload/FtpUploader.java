package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jeecgframework.core.util.LogUtil;

public class FtpUploader
{
    
  public static final State save(HttpServletRequest request, Map<String, Object> conf)
  {
    FileItemStream fileStream = null;
    boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

    if (!ServletFileUpload.isMultipartContent(request)) {
      return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
    }

    ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

    if (isAjaxUpload) {
      upload.setHeaderEncoding("UTF-8");
    }
    try
    {
      FileItemIterator iterator = upload.getItemIterator(request);

      while (iterator.hasNext()) {
        fileStream = iterator.next();

        if (!fileStream.isFormField())
          break;
        fileStream = null;
      }

      if (fileStream == null) {
        return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
      }

      String savePath = (String)conf.get("savePath");
      LogUtil.info("-----------------.FtpUploader.save.--savePath"+savePath+"-----------------------");
      String originFileName = fileStream.getName();
      LogUtil.info("-----------------.FtpUploader.save.--originFileName"+originFileName+"-----------------------");
      String suffix = FileType.getSuffixByFilename(originFileName);

      originFileName = originFileName.substring(0, 
        originFileName.length() - suffix.length());
      savePath = savePath + suffix;
      LogUtil.info("-----------------.FtpUploader.save.--savePath"+savePath+"-----------------------");
      long maxSize = ((Long)conf.get("maxSize")).longValue();

      if (!validType(suffix, (String[])conf.get("allowFiles"))) {
        return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
      }

      savePath = PathFormat.parse(savePath, originFileName);
      LogUtil.info("-----------------.FtpUploader.save.--savePath"+savePath+"-----------------------");
      String remoteDir = "";
      
      int pos = savePath.lastIndexOf("/");
      if(pos > -1){
          remoteDir = savePath.substring(0,pos + 1);
      }
      LogUtil.info("-----------------.FtpUploader.save.--remoteDir"+remoteDir+"-----------------------");
      String physicalPath = (String)conf.get("rootPath") + savePath;
      LogUtil.info("-----------------.FtpUploader.save.--physicalPath"+physicalPath+"-----------------------");
      boolean keepLocalFile = "false".equals(conf.get("keepLocalFile")) ? false : true;
      InputStream is = fileStream.openStream();
      State storageState = StorageManager.saveFtpFileByInputStream(is, remoteDir,physicalPath, maxSize, keepLocalFile,savePath);
      is.close();

      if (storageState.isSuccess()) {
//        storageState.putInfo("url", savePath);
        storageState.putInfo("type", suffix);
        storageState.putInfo("original", originFileName + suffix);
      }

      return storageState;
    } catch (FileUploadException e) {
      return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
    } catch (IOException localIOException) {
    }
    return new BaseState(false, AppInfo.IO_ERROR);
  }

  @SuppressWarnings("rawtypes")
private static boolean validType(String type, String[] allowTypes) {
    List list = Arrays.asList(allowTypes);

    return list.contains(type);
  }
}