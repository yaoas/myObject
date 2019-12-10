package com.springboot.common;

import com.jfinal.kit.PropKit;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class FtpUtils {
    public  static FTPClient ftp;
    /**
     *
     * @param path 上传到ftp服务器哪个路径下
     * @param addr 地址
     * @param port 端口号
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws Exception
     */
    public  static boolean connect(String path,String addr,int port,String username,String password) throws Exception {
        boolean result = false;
        ftp = new FTPClient();
        int reply;
        ftp.connect(addr,port);
        ftp.login(username,password);
        ftp.enterLocalPassiveMode();
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            return result;
        }
        ftp.changeWorkingDirectory(path);
        result = true;
        return result;
    }
    /**
     *
     * @param file 上传的文件或文件夹
     * @throws Exception
     */
    public static String upload(File file, String fileName) throws Exception{
        File file2 = new File(file.getPath());
        System.out.println(file2.getName());
        FileInputStream input = new FileInputStream(file2);
        boolean b = ftp.storeFile(fileName, input);
        input.close();
        ftp.disconnect();
        if(b){
            return  Constant.FTP_PATH + "/" + fileName;
        }
        else {
            return  "上传失败";
        }

    }
    /**
     * 下载文件
     * <BR/>
     * @param outputStream outputStream
     * @param url url
     * @return InputStream
     * @throws IOException
     * @since 1.0.0
     *
     */
    /**
     * 下载文件
     * <BR/>
     * @param OutputStream outputStream
     * @param String url
     * @return InputStream
     * @throws IOException
     * @since 1.0.0
     *
     */
    public static void downFileByFtp( String url,
                                             OutputStream outputStream) {
        InputStream inputStream = null;
        try {
            inputStream = ftp.retrieveFileStream(new String(url.getBytes("UTF-8"), "ISO-8859-1"));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);}

        } catch (IOException e) {

            e.printStackTrace();
        }
        finally {

            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}