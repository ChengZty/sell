package com.baidu.ueditor.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.jeecgframework.core.util.FtpUtil;
import org.jeecgframework.core.util.LogUtil;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;

public class StorageManager {
	public static final int BUFFER_SIZE = 8192;

	public StorageManager() {
	}

	public static State saveBinaryFile(byte[] data, String path) {
		File file = new File(path);

		State state = valid(file);

		if (!state.isSuccess()) {
			return state;
		}

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
		} catch (IOException ioe) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true, file.getAbsolutePath());
		state.putInfo( "size", data.length );
		state.putInfo( "title", file.getName() );
		return state;
	}

	public static State saveFileByInputStream(InputStream is, String path,long maxSize) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();

			if (tmpFile.length() > maxSize) {
				tmpFile.delete();
				return new BaseState(false, AppInfo.MAX_SIZE);
			}

			state = saveTmpFile(tmpFile, path);

			if (!state.isSuccess()) {
				tmpFile.delete();
			}

			return state;
			
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	public static State saveFileByInputStream(InputStream is, String path) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();

			state = saveTmpFile(tmpFile, path);

			if (!state.isSuccess()) {
				tmpFile.delete();
			}

			return state;
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File targetFile = new File(path);

		if (targetFile.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}
		try {
			FileUtils.moveFile(tmpFile, targetFile);
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true);
		state.putInfo( "size", targetFile.length() );
		state.putInfo( "title", targetFile.getName() );
		
		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}
	/**
	   * 上传FTP文件
	   * @param is
	   * @param path
	   * @param maxSize
	   * @return
	   */
	  public static State saveFtpFileByInputStream(InputStream is, String remoteDir, String path, long maxSize,boolean keepLocalFile,String savePath)
	  {
	    State state = null;

	    File tmpFile = getTmpFile();

	    byte[] dataBuf = new byte[2048];
	    BufferedInputStream bis = new BufferedInputStream(is, 8192);
	    try
	    {
	      BufferedOutputStream bos = new BufferedOutputStream(
	        new FileOutputStream(tmpFile), 8192);

	      int count = 0;
	      while ((count = bis.read(dataBuf)) != -1) {
	        bos.write(dataBuf, 0, count);
	      }
	      bos.flush();
	      bos.close();

	      if (tmpFile.length() > maxSize) {
	        tmpFile.delete();
	        return new BaseState(false, 1);
	      }
	      LogUtil.info("-----------------.StorageManager.saveFtpFileByInputStream.--saveFtpTmpFile--start-----------------------"+path);
	      state = saveFtpTmpFile(tmpFile, remoteDir, path, keepLocalFile,savePath);
	      LogUtil.info("-----------------.StorageManager.saveFtpFileByInputStream.--remoteDir--end-----------------------"+path);
	      if (!state.isSuccess()){
	        tmpFile.delete();
	      }
	      
	      return state;
	    }
	    catch (IOException localIOException) {
	    }
	    return new BaseState(false, 4);
	  }
	  
	  
	  private static State saveFtpTmpFile(File tmpFile, String remoteDir, String path,boolean keepLocalFile,String savePath) {
	        State state = null;//用来拼装 上传成功后返回的json
	        File targetFile = new File(path);
	        if (targetFile.canWrite())
	          return new BaseState(false, 2);
	        try
	        {
	          FileUtils.moveFile(tmpFile, targetFile);
	        } catch (IOException e) {
	          return new BaseState(false, 4);
	        }
	        
	        try
	        {
	        	FtpUtil t = FtpUtil.getInstance();
	        	LogUtil.info("-----------------.StorageManager.saveFtpTmpFile--创建目录--start-----------------------"+path);
	        	t.createDirecroty(remoteDir);
	        	LogUtil.info("-----------------.StorageManager.saveFtpTmpFile--创建目录--end-----------------------"+path);
	        	LogUtil.info("-----------------.StorageManager.saveFtpTmpFile--上传开始--start-----------------------"+path);
	        	Boolean isUploadSuccess = t.uploadFileToFtpServer(remoteDir, targetFile);
	        	LogUtil.info("-----------------.StorageManager.saveFtpTmpFile--上传文件"+isUploadSuccess+"--end-----------------------"+path);
	            if(!isUploadSuccess){
	            	t = null;
	                return new BaseState(false, 4);
	            }
	        }catch (Exception e) {
	        	LogUtil.info(e.getMessage());
	            return new BaseState(false, 4);
	        }
	        state = new BaseState(true);
	        state.putInfo("url", savePath);
	        state.putInfo("size", targetFile.length());
	        state.putInfo("title", targetFile.getName());
	        try
	        {
	            if(!keepLocalFile)
	            {	
	                targetFile.delete();
	            }   
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return state;
	  }
}
