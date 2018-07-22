package com.viewpoint.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.util.UUID;

@Slf4j
public class FtpFileTransfer {

    private FTPClient ftpClient = null;//ftp客户端对象
    private static String hostname = null;//FTP主机名称
    private static Integer port = 0;	//FTP服务端口
    private static String userName = null;//FTP服务器登录用户名
    private static String passwd=null;//FTP服务器登录密码
    private final String SPAPRATE_TOEKN = "/";

    /**
     * 从配置文件中获取配置值
     */
    public FtpFileTransfer(String hostname,int port,String userName,String passwd){
        this.hostname = hostname;
        this.port = port;
        this.userName = userName;
        this.passwd = passwd;
    }
    /**
     * 方法描述：上传文件
     * @param srcPath 源文件路径
     * @param ftpPath FTP端存放路径
     * @param targetName FTP端存放名称
     * @return 操作是否成功
     */
    public boolean uploadFile(String srcPath,String ftpPath, String targetName){
        boolean ret = false;
        File file = new File(srcPath);
        ret = uploadFile(file, ftpPath, targetName);
        return ret;
    }

    /**
     * 方法描述：上传文件
     * @param file 待上传的文件
     * @param ftpPath 目标文件路径
     * @param targetName 目标名称
     * @return
     */
    public boolean uploadFile(File file,String ftpPath,String targetName){
        if(file == null){
            log.info("File is null");
            return false;
        }
        try {
            InputStream is = new FileInputStream(file);
            return uploadFileStream(is, ftpPath, targetName);
        } catch (FileNotFoundException e) {
            return false;
        }
    }
    /**
     * 方法描述：上传文件
     * @param is 源文件流
     * @param ftpPath 放置在服务器上的位置
     * @param targetName 放置在服务器上的名称
     * @return 操作是否成功
     */
    public boolean uploadFileStream(InputStream is,String ftpPath, String targetName){
        boolean ret = false;
        if(is == null){
            log.info("File is null");
            return ret;
        }
        ret = this.connect2Ftp();
        if(!ret){
            log.info("connect to ftp server failure");
            this.disconnect2Ftp();
            return ret;
        }
        try {
            //boolean mkdir = this.makeDir(ftpPath);
            /*if(ftpPath.startsWith(SPAPRATE_TOEKN)){
                ftpPath = ftpPath.substring(ftpPath.indexOf(SPAPRATE_TOEKN)+SPAPRATE_TOEKN.length());
            }*/
            ret = ftpClient.changeWorkingDirectory(ftpPath);
            if(ret){
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("utf-8");
                ftpClient.enterLocalPassiveMode();
                ret = ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                if(ret){
                    ret = ftpClient.storeFile(targetName, is);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(is);
            disconnect2Ftp();
        }
        return ret;
    }


    /**
     * 方法描述：下载文件
     * @param remoteFileName FTP上的文件名称，含路径
     * @param localFilePath 取到文件到本地后文件的存放位置
     * @return 操作是否成功
     */
    public boolean downloadFile(String remoteFileName, String localFilePath){
        boolean ret = false;
        remoteFileName = remoteFileName.trim();
        if(remoteFileName.startsWith(SPAPRATE_TOEKN)){
            remoteFileName = remoteFileName.substring(remoteFileName.indexOf(SPAPRATE_TOEKN)
                    +SPAPRATE_TOEKN.length());
        }else if(remoteFileName.startsWith("\\")){
            remoteFileName = remoteFileName.substring(remoteFileName.indexOf("\\")
                    +"\\".length());
        }

        String path = null;
        if(!remoteFileName.contains("//")){
            if(remoteFileName.contains("/")){
                remoteFileName=remoteFileName.replace("/", "\\");
            }
        }
        path = remoteFileName.substring(0,remoteFileName.lastIndexOf("\\"));
        String fileName = remoteFileName.substring(path.length()+2);
        FileOutputStream fos = null;
        try {
            ret = connect2Ftp();
            if(!ret){
                disconnect2Ftp();
                return ret;
            }
            ret = ftpClient.changeWorkingDirectory(path);
            fos = new FileOutputStream(localFilePath);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("utf-8");
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ret = ftpClient.retrieveFile(fileName, fos);
        } catch (IOException e) {
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            disconnect2Ftp();
        }
        return ret;
    }

    /**
     * 方法描述：删除FTP服务器上的文件
     * @param filePath 文件名称
     * @return 删除成功/失败
     */
    public boolean deleteFile(String filePath){
        boolean ret = false;
        if(filePath == null || filePath.isEmpty() || filePath.equals("")){
            return false;
        }
        try {
            connect2Ftp();
            ftpClient.changeWorkingDirectory("\\");
            ret = ftpClient.deleteFile(filePath);
        } catch (IOException e) {
        }finally{
            disconnect2Ftp();
        }
        return ret;
    }

    /**
     * 方法描述：删除文件夹
     * @param dirPath 文件夹路径
     * @param force 是否强制删除
     * @return 是否删除成功
     */
    public boolean deleteDirs(String dirPath, boolean force){
        boolean ret = false;
        if(dirPath.startsWith(SPAPRATE_TOEKN)){
            dirPath = dirPath.substring(dirPath.indexOf(SPAPRATE_TOEKN)+SPAPRATE_TOEKN.length());
        }
        String path = dirPath;
        try {
            ret = ftpClient.changeWorkingDirectory(dirPath);
            String[] names = ftpClient.listNames();
            if(force){
                for(String name:names){
                    if(dirPath.endsWith(SPAPRATE_TOEKN)){
                        ret = deleteFile(dirPath+name);
                    }else{
                        ret = deleteFile(dirPath+SPAPRATE_TOEKN+name);
                    }
                }
            }
            ret = ftpClient.changeWorkingDirectory("//");
            while(true){
                ret = ftpClient.removeDirectory(path);
                if(path.contains(SPAPRATE_TOEKN)){
                    path = path.substring(0, path.lastIndexOf(SPAPRATE_TOEKN));
                }else{
                    break;
                }
            }
        } catch (Exception e) {
        }
        return ret;
    }
    /**
     * 方法描述：连接到远端的FTP服务器
     * @return 是否连接成功
     */
    private boolean connect2Ftp(){
        boolean ret = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(hostname, port);
            ret = ftpClient.login(userName, passwd);
            log.info("Finished login the ftp server, result="+ret);
        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * 方法描述：断开ftp链接
     */
    private void disconnect2Ftp(){
        if(ftpClient != null){
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 方法描述：根据目录要求创建ftp端的文件夹路径
     * @param dir 文件夹路径，如：绝对路径，/ab/cde/ef/hg，相对路径, ab/cd
     * @return 是否创建成功
     * @throws IOException
     */
    private boolean makeDir(String dir) throws IOException{
        boolean ret = false;
        int i = 0;
        if(dir != null && !dir.isEmpty()){
            String[] dirs = null;
            int len = 0;
            if(dir.contains("//")){
                dir = dir.replace("//", SPAPRATE_TOEKN);
                i = 1;
            }
            if(dir.contains(SPAPRATE_TOEKN)){
                dirs = dir.split(SPAPRATE_TOEKN);
                len = dirs.length;
                StringBuffer sb = new StringBuffer();
                for(;i<len;i++){
                    sb.append(dirs[i]);
                    sb.append(SPAPRATE_TOEKN);
                    ret = ftpClient.makeDirectory(sb.toString());
                }
            }else{
                ret = ftpClient.makeDirectory(dir);
            }
        }
        return ret;
    }

    public boolean isFileExist(String filePath) {
        connect2Ftp();
        boolean ret = false;
        try {
            String rt = ftpClient.getModificationTime(filePath);
            if (rt != null && !rt.isEmpty()) {
                ret = true;
            }
        } catch (IOException e) {
        } finally {
            disconnect2Ftp();
        }
        return ret;
    }

    //生成上传文件的名称
    public static String getFileName(String primitiveFileName){
        //使用uuid生成文件名
        String fileName=UUID.randomUUID().toString();
        //获取文件后缀
        String suffix = primitiveFileName.substring(primitiveFileName.lastIndexOf("."));
        return fileName + suffix;
    }

    public static void main(String[] args) {
        FtpFileTransfer ftpFileTransfer = new FtpFileTransfer("192.168.1.145",21,"ftp_test","123456");
        /*if (ftpFileTransfer.uploadFile("E://建邺区科技创新大赛.pptx","/home/ftp_test",getFileName("123.pptx"))){
            System.out.println("成功");
        }*/
        if (ftpFileTransfer.deleteFile("37db22f1-ff4b-4207-b514-175f33a55d51.pptx")){
            System.out.println("成功");
        }
    }

}
