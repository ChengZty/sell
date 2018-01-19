package org.jeecgframework.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * 该类为FTP相关的工具类
 * 
 * @author wxq
 * 
 */
public class FtpUtil {
	protected final Logger log   = Logger.getLogger(getClass());
	private static FtpUtil ftpUtils;
	private ThreadLocal<FTPClient> ftpClientThreadLocal = new ThreadLocal<FTPClient>();
    private static String serverIp = "";// ftp服务器ip地址
    private static String serverPort = "";// ftp服务器端口
    private static String usrName = "";// ftp用户名
    private static String usrPwd = "";// ftp用户密码
    String baseWorkDirectory = "";// 基本工作目录
    int fileType = FTP.BINARY_FILE_TYPE;// 上传下载文件方式，默认使用二进制流
    // 本地编码字符串编码
    String localCharset = "GBK";
    // FTP服务器端字符串编码
    String serverCharset = "ISO-8859-1";
    private boolean binaryTransfer = true;  
    private boolean passiveMode = true;  
    private String  encoding = "UTF-8";  
    private int     clientTimeout = 1000 * 30000;  
    
    private FtpUtil() {
		
		
	}

	/**
	 * 获取FTPUtils对象实例
	 */
	public synchronized static FtpUtil getInstance(){
		if (null == ftpUtils) {
			ftpUtils = new FtpUtil();
		}
		return ftpUtils;
	}
    
	/**
	 * 初始化FTP服务器连接属性
	 */
	 static{
		serverIp = ResourceUtil.getConfigByName("ftpServerIp");
		serverPort = ResourceUtil.getConfigByName("ftpServerPort");
		usrName = ResourceUtil.getConfigByName("ftpServerUserName");
		usrPwd = ResourceUtil.getConfigByName("ftpServerPwd");
	}
    /** 
     * 返回一个FTPClient实例 
     *  
     * @throws FTPClientException 
     */  
    private FTPClient getFTPClient()  {  
        if (ftpClientThreadLocal.get() != null && ftpClientThreadLocal.get().isConnected()) {  
            return ftpClientThreadLocal.get();  
        } else {  
            FTPClient ftpClient = new FTPClient(); //构造一个FtpClient实例  
            ftpClient.setControlEncoding(encoding); //设置字符集  
            connectServer(ftpClient); //连接到ftp服务器  
            //设置为passive模式  
            if (passiveMode) {  
                ftpClient.enterLocalPassiveMode();  
            }  
            setFileType(ftpClient); //设置文件传输类型  
            try {  
                ftpClient.setSoTimeout(clientTimeout);  
            } catch (SocketException e) {  
                e.printStackTrace(); 
            }  
            ftpClientThreadLocal.set(ftpClient);  
            return ftpClient;  
        }  
    }  
    
    /** 
     * 设置文件传输类型 
     *  
     * @throws FTPClientException 
     * @throws IOException 
     */  
    private void setFileType(FTPClient ftpClient) {  
        try {  
            if (binaryTransfer) {  
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            } else {  
                ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();
        }  
    }  
	

    
   

    /**
     * 连接FTP服务器，并登录，切换至基本工作目录(通常为当前用户的根目录)
     * 
     */
    public boolean connectServer(FTPClient ftpClient) {
        try
        {
            int reply;
            ftpClient.connect(serverIp, Integer.parseInt(serverPort));
            reply = ftpClient.getReplyCode();
            if(FTPReply.isPositiveCompletion(reply))
            {
            	 if(ftpClient.login(usrName, usrPwd)){
            		 setFileType(ftpClient);  
//            		 ftpClient.setControlEncoding(localCharset);
//                   ftpClient.changeWorkingDirectory(baseWorkDirectory);
//         			  设置缓冲区大小
//         			 ftpClient.setBufferSize(3072);
            		 LogUtil.info(" ftp login success");
                     return true;
                 }
                
            } else 
            {
            	ftpClient.disconnect();
                LogUtil.info("FTP server refused connection");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }
    
    /** 
     * 断开ftp连接 
     *  
     * @throws FTPClientException 
     */  
    public void disconnect(){  
        try {  
            FTPClient ftpClient = getFTPClient();  
            ftpClient.logout();  
            if (ftpClient.isConnected()) {  
                ftpClient.disconnect();  
                ftpClient = null;  
                LogUtil.info(" ftp is close2");
            }  
        } catch (IOException e) {  
            e.printStackTrace();
        }  
    }  
    
    

    
    /**
     * 上传文件至Ftp用户根目录下的指定目录，如果subDirectory.equals("")为true， 则上传文件存放到当前用户的根目录
     * 
     * @param subDirectory
     *            子目录
     * @param storeName
     *            上传文件在FTP服务器上的存储名字
     * @param file
     *            上传文件
     * @return
     */
    public boolean uploadFileToFtpServer(String subDirectory, String storeName,File file) {
        // 上传文件成功标记
        boolean isUploadSuccess = false;
        FileInputStream fin = null;
        try {
        	FTPClient ftpClient = this.getFTPClient();
            if (file.exists()) {
                subDirectory=new String(subDirectory.getBytes(localCharset),serverCharset);
                storeName=new String(storeName.getBytes(localCharset),serverCharset);
                storeName = new String(storeName.getBytes(localCharset),serverCharset);
                storeName = this.handleStoreName(subDirectory, storeName);
                fin = new FileInputStream(file);
                if (ftpClient.storeFile(storeName, fin))
                {
                    isUploadSuccess = true;
                    System.out.println("upload file to FTP server success");
                }
            } else {
                System.out.println("upload file does not exsit");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            disconnect();
        }
        return isUploadSuccess;
    }
    
    /**
     * 上传文件至Ftp用户根目录下的指定目录，如果subDirectory.equals("")为true， 则上传文件存放到当前用户的根目录
     * 
     * @param subDirectory
     *            子目录
     * @param storeName
     *            上传文件在FTP服务器上的存储名字
     * @param file
     *            上传文件
     * @return
     */
    public boolean uploadFileToFtpServer(String subDirectory, String storeName,InputStream is) {
        // 上传文件成功标记
        boolean isUploadSuccess = false;
        try {
        		FTPClient ftpClient = this.getFTPClient();
                subDirectory=new String(subDirectory.getBytes(localCharset),serverCharset);
                storeName=new String(storeName.getBytes(localCharset),serverCharset);
                storeName = new String(storeName.getBytes(localCharset),serverCharset);
                storeName = this.handleStoreName(subDirectory, storeName);
                if (ftpClient.storeFile(storeName, is)) {
                    isUploadSuccess = true;
                    System.out.println("upload file to FTP server success");
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            disconnect();
        }
        return isUploadSuccess;
    }

    /**
     * 从FTP服务器下载文件到本地指定路径，当subDirectory.equals("")时，则在当前用户的根目录下去找要下载的文件
     * 
     * @param subDirectory
     *            ftp服务器上存放要下载文件的子目录
     * @param fileName
     *            下载文件的名字
     * @param localPath
     *            本地存放路径
     * @return 下载成功，返回true
     */
    public boolean downFileFromFtpServer(String subDirectory, String fileName,String localPath) {
        FileOutputStream fos = null;
        boolean isDownloadSuccess = false;
        try {
        	FTPClient ftpClient = this.getFTPClient();
            subDirectory=new String(subDirectory.getBytes(localCharset),serverCharset);
            String baseWorkDir = ftpClient.printWorkingDirectory();
            if (!subDirectory.equals("")) {
                baseWorkDir = baseWorkDir + "/" + subDirectory;
            }
            ftpClient.changeWorkingDirectory(baseWorkDir);

            fos = new FileOutputStream(localPath + "/" + fileName);
            fileName = new String(fileName.getBytes(localCharset),serverCharset);
            if (ftpClient.retrieveFile(fileName, fos)) {
                isDownloadSuccess = true;
                System.out.println("download from FTP server success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return isDownloadSuccess;
    }

    /**
     * 下载Ftp服务器上指定目录下的所有文件（不包含文件夹）到本机的指定目录，子目录subDirectory.equals("")时， 则指定目录就是用户的根目录
     * 
     * @param subDirectory
     *            ftp服务器上包含文件的子目录
     * @param localPath
     * @return
     */
    public boolean downloadFilesFromFtpServer(String subDirectory,
            String localPath) {
        boolean isDownloadSuccess = false;
        FileOutputStream fos = null;
        try {
        	FTPClient ftpClient = this.getFTPClient();
            subDirectory=new String(subDirectory.getBytes(localCharset),serverCharset);
            String baseWorkDir = ftpClient.printWorkingDirectory();
            if (!subDirectory.equals("")) {
                baseWorkDir = baseWorkDir + "/" + subDirectory;
            }
            ftpClient.changeWorkingDirectory(baseWorkDir);
            FTPFile[] files = ftpClient.listFiles();
            if (files != null && files.length > 0) {
                // 下载目录下所有文件
                for (FTPFile file : files) {
                    if(file.isFile()){
                    String fileName = new String(file.getName().getBytes(
                            serverCharset), localCharset);
                    fos = new FileOutputStream(localPath + "/" + fileName);
                    if (ftpClient.retrieveFile(file.getName(), fos)) {
                        System.out.println("download file: " + fileName
                                + " success");
                    }
                    fos.flush();
                    }
                }
            }
            isDownloadSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isDownloadSuccess;
    }

    /**
     * 删除ftp服务器上的指定目录下的某个文件
     * 
     * @param subDirectory
     *            子目录
     * @param fileName
     *            文件名
     * @return 删除成功，返回true
     */
    public boolean deleteFileInFtpServer(String subDirectory, String fileName) {
        boolean isDeleteSuccess = false;
        try {
        	FTPClient ftpClient = this.getFTPClient();
            subDirectory=new String(subDirectory.getBytes(localCharset),serverCharset);
            fileName = new String(fileName.getBytes(localCharset),
                    serverCharset);
            fileName = this.handleStoreName(subDirectory, fileName);
            if (ftpClient.deleteFile(fileName)) {
                isDeleteSuccess = true;
                System.out.println("delete file on ftp server success");
            } else {
                System.out.println("delete file on ftp server fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleteSuccess;
    }

    /**
     * 删除指定目录下的所有文件，如果folderName为“”，则删除subDirectory下的所有文件（不包括文件夹）。
     * 
     * @param subDirectory
     *            子目录
     * @param folderName
     *            文件夹名称
     * @return
     */
    public boolean deleteFilesInFtpServer(String subDirectory, String folderName) {
        boolean isDeleteSuccess = false;
        try 
        {
        	FTPClient ftpClient = this.getFTPClient();
            String baseWorkDir = ftpClient.printWorkingDirectory();
            if (!subDirectory.equals("")) {
                subDirectory=new String(subDirectory.getBytes(localCharset),serverCharset);
                baseWorkDir = baseWorkDir + "/" + subDirectory;
            }
            if (!folderName.equals("")) {
                folderName=new String(folderName.getBytes(localCharset),serverCharset);
                baseWorkDir = baseWorkDir + "/" + folderName;
            }
            ftpClient.changeWorkingDirectory(baseWorkDir);
            FTPFile[] files = ftpClient.listFiles();
            if (files != null) {
                for (FTPFile file : files) {
                    ftpClient.deleteFile(file.getName());
                }
                isDeleteSuccess=true;
            }
            System.out.println("delete files in ftp server success");
        }catch(Exception e)
        {
        	System.out.println("delete files in ftp server exception: "+e.getMessage());
        }
        return isDeleteSuccess;
    }


    /**
     * 在用户的根目录下创建指定文件夹,如果subDirectory是一个目录则依次创建各级文件夹。如果文件夹存在，则返回false
     * 
     * @param subDirectory
     *            子目录
     * @return 成功返回true
     */
    public boolean createDirInBaseWorkDir(String subDirectory) {
        boolean isCreateSuccess = false;
        try {
        	FTPClient ftpClient = this.getFTPClient();
            if (!subDirectory.equals("")) {
                subDirectory=new String(subDirectory.getBytes(localCharset),serverCharset);
                if (ftpClient.makeDirectory(subDirectory)) {
                    isCreateSuccess = true;
                    System.out.println("create new directory in base work directory success");
                } else {
                    System.out.println("create new directory fail,the directory exsited");
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return isCreateSuccess;
    }

    /**
     * 删除用户根目录下的指定文件夹，如果subDirectory是一个路径，则删除最低级的那个文件夹；如果文件夹不存在则返回false。
     * 如果文件夹不为空，则返回false
     * 
     * @param subDirectory
     * @return
     */
    public boolean rmDirInBaseWorkDir(String subDirectory) {
        boolean isRmDirSuccess = false;
        try
        {
        	FTPClient ftpClient = this.getFTPClient();
            subDirectory=new String(subDirectory.getBytes(localCharset),serverCharset);
            if (ftpClient.removeDirectory(subDirectory)) {
                isRmDirSuccess = true;
                System.out
                        .println("remove directory in base work directory success");
            } else {
                System.out
                        .println("remove directory in base work directory fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRmDirSuccess;
    }
    
    
	/**
	 * 递归创建远程服务器目录
	 * @param remote 远程服务器文件绝对路径
	 * @param ftpClient FTPClient对象
	 * @return 目录创建是否成功
	 * @throws IOException
	 * @throws FTPClientException 
	 */
	public void createDirecroty(String remotePath) {
		try
		{
			FTPClient ftpClient = this.getFTPClient();
			String directory = remotePath.substring(0,remotePath.lastIndexOf("/")+1);
			if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory.getBytes(localCharset),serverCharset))){
				//如果远程目录不存在，则递归创建远程服务器目录
				int start=0;
				int end = 0;
				if(directory.startsWith("/")){
					start = 1;
				}else{
					start = 0;
				}
				end = directory.indexOf("/",start);
				while(true){
					String subDirectory = new String(remotePath.substring(start,end).getBytes(localCharset),serverCharset);
					if(!ftpClient.changeWorkingDirectory(subDirectory)){
						if(ftpClient.makeDirectory(subDirectory)){
							ftpClient.changeWorkingDirectory(subDirectory);
						}else {
							System.out.println("创建目录失败");
						}
					}
					start = end + 1;
					end = directory.indexOf("/",start);
					
					//检查所有目录是否创建完毕
					if(end <= start){
						break;
					}
				}
			} 
		}catch (Exception e) {
			LogUtil.info("创建目录失败");
			e.printStackTrace();
		}
	}
    
	
	/**
     * 该方法用于处理文件时，对子目录和文件名进行处理
     * 
     * @param subDirectory
     *            子目录
     * @param storeName
     *            文件名
     * @return 返回处理后可能带有路径的文件名
     */
    private String handleStoreName(String subDirectory, String storeName) {
        // 子目录是否存在标记
        boolean isSubDirectoryExsit = false;
        try {
        	FTPClient ftpClient = this.getFTPClient();
            // 此处判断是否要生成子目录，存在则不创建
            FTPFile[] dirs = ftpClient.listDirectories();
            if (dirs != null && dirs.length > 0) {
                for (int i = 0; i < dirs.length; i++) {
                    if (dirs[i].getName().equals(subDirectory)) {
                        isSubDirectoryExsit = true;
                        break;
                    }
                }
            }
            dirs = null;
            if (!isSubDirectoryExsit && !subDirectory.equals("")) {
                ftpClient.makeDirectory(subDirectory);
                storeName = subDirectory + "/" + storeName;
            }
            if (isSubDirectoryExsit && !subDirectory.equals("")) {
                storeName = subDirectory + "/" + storeName;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return storeName;
    }
	
	
	public Boolean uploadFileToFtpServer(String remotePath, File file){
        boolean isUploadSuccess = false;
        FileInputStream fin = null;
        try {
        	FTPClient ftpClient = this.getFTPClient();
            if (file.exists()) {
                fin = new FileInputStream(file);
                if (ftpClient.storeFile(file.getName(), fin)) {
                    isUploadSuccess = true;
                }
            } else {
                LogUtil.info("upload file does not exsit");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                    LogUtil.info(" ftp is close");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
			disconnect();//关闭链接 
        }
        return isUploadSuccess;
	} 
    
    public static void main(String[] args) throws Exception{  
    	FtpUtil t = FtpUtil.getInstance();
        File file = new File("c:\\a.png");
        t.uploadFileToFtpServer("\\kkkk","a.png", file);
//      t.upload(file);  
     }

	 
}