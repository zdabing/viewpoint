package com.viewpoint.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FtpUtilsTest {

    private static String pictureUrl = "192.168.1.145:7777";
    private static String host = "192.168.1.145";
    private static int port = 21;
    private static String username = "ftp_test";
    private static String password = "123456";
    private static String path = "/home/ftp_test";

    /**
     * @description 上传文件到服务器，并且返回路径
     * @param filename 文件名称
     * @param input 输入的文件流
     * @return
     */
    public static String uploadFile( String mkdir,String filename, InputStream input) {

        // 创建FTP客户端
        FTPClient ftpClient = new FTPClient();

        try {
            //链接
            ftpClient.connect(host, port);
            //登录
            ftpClient.login(username,password);
            //检测连接是否成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }

            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            path = path +mkdir;
            ftpClient.makeDirectory(new String(mkdir.getBytes("GBK"),"iso-8859-1"));
            ftpClient.changeWorkingDirectory(new String(mkdir.getBytes("GBK"),"iso-8859-1"));
            ftpClient.storeFile(new String(filename.getBytes("GBK"),"iso-8859-1"), input);

            input.close();
            ftpClient.logout();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }

        String picUrl = pictureUrl + filename;

        return pictureUrl;
    }

    /**
     *
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param fileName
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void delFile(String fileName){
        // 创建FTP客户端
        FTPClient ftpClient = new FTPClient();

        try {
            //链接
            ftpClient.connect(host, port);
            //登录
            ftpClient.login(username,password);
            //检测连接是否成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.makeDirectory(path);
            ftpClient.changeWorkingDirectory(path);

            ftpClient.deleteFile(fileName);
            System.out.println("删除文件成功："+fileName);
            ftpClient.logout();
        } catch (SocketException e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        }finally{
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }

    //生成上传文件的名称
    public static String getFileName(String primitiveFileName){
        //使用uuid生成文件名
        String fileName=UUID.randomUUID().toString();
        //获取文件后缀
        String suffix = primitiveFileName.substring(primitiveFileName.lastIndexOf("."));
        return fileName + suffix;
    }

        /*//设置上传路径
        String basePath="/home/ftpuser/image/";
        String dateStr = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());
        for(String pathStr:dateStr.split("/")){
            basePath+=pathStr+"/";
            boolean flag = ftp.changeWorkingDirectory(basePath);
            if(!flag){
                //创建上传的路径  该方法只能创建一级目录，在这里如果/home/ftpuser存在则可创建image
                ftp.makeDirectory(basePath);
            }
        }
        //指定上传路径
        ftp.changeWorkingDirectory(basePath);*/
}
