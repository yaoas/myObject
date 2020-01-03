package com.springboot.controller;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FtpJSchTestLinux {

    private static ChannelSftp sftp = null;

    //账号
    private static String user = "admin";
    //主机ip
    private static String host =  "192.168.0.167";
    //密码
    private static String password = "yaoas123";
    //端口
    private static int port = 22;
    //上传地址
    private static String directory = "/home/didetongcheng/";
    //下载目录
    private static String saveFile = "D:\\";

    public static FtpJSchTestLinux ftp;

    public FtpJSchTestLinux getConnect(){
        ftp = new FtpJSchTestLinux();
        try {
            JSch jsch = new JSch();

            //获取sshSession  账号-ip-端口
            Session sshSession =jsch.getSession(user, host,port);
            //添加密码
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            //严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");

            sshSession.setConfig(sshConfig);
            //开启sshSession链接
            sshSession.connect();
            //获取sftp通道
            Channel channel = sshSession.openChannel("sftp");
            //开启
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftp;
    }

    /**
     *
     * @param uploadFile 上传文件的路径
     * @return 服务器上文件名
     * @throws JSchException
     */
    public String upload(String uploadFile) throws JSchException {
        File file = null;
        String fileName = null;
        try {
            sftp.cd(directory);
            file = new File(uploadFile);
            //获取随机文件名
            //fileName  = UUID.randomUUID().toString() + file.getName().substring(file.getName().length()-5);
            fileName = file.getName();
            //文件名是 随机数加文件名的后5位
            sftp.put(new FileInputStream(file), fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file == null ? null : fileName;
    }

    /**
     * 下载文件
     *
     * @param directory
     *            下载目录
     * @param downloadFile
     *            下载的文件名
     * @param saveFile
     *            存在本地的路径
     * @param sftp
     */
    public InputStream download(String downloadFileName, OutputStream out) {
        InputStream inputStream = null;
        FtpJSchTestLinux ftp = new FtpJSchTestLinux();
        ftp = ftp.getConnect();
        Vector listFiles;
        try {
            listFiles = ftp.listFiles("/home/admin/");
            System.out.println(listFiles);
            try {
                sftp.cd(directory);

                //File file = new File(saveFile + downloadFileName);
                //sftp.get(downloadFileName, new FileOutputStream(file));
                 inputStream = sftp.get("/home/admin/qrcode.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, len);}
                System.out.println("success");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //ftp.download("qrcode.jpg");
            //String upload = ftp.upload(saveFile + "qrcode.jpg");
            //System.out.println(upload);
            //6c19bac6-b863-4f0f-91e8-38d1a8e03164.xlsx
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            //关闭session和连接
            try {
                sftp.getSession().disconnect();
            } catch (JSchException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            sftp.quit();
            sftp.disconnect();
        }

        return inputStream;
    }

    /**
     * 下载文件流
     *
     * @param directory
     *            下载目录
     * @param downloadFile
     *            下载的文件名
     * @param saveFile
     *            存在本地的路径
     * @param sftp
     */
    public static void downloadOut(String downloadFileName, OutputStream out) {
        FtpJSchTestLinux ftp = new FtpJSchTestLinux();
        ftp = ftp.getConnect();
        InputStream inputStream = null;
        try {
            inputStream = sftp.get(downloadFileName);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);}
            inputStream.close();
            out.close();
            sftp.getSession().disconnect();
            sftp.quit();
            sftp.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭session和连接
            try {
                sftp.getSession().disconnect();
            } catch (JSchException e) {
                e.printStackTrace();
            }
            sftp.quit();
            sftp.disconnect();
        }

    }

    /**
     * 删除文件
     *
     * @param deleteFile
     *            要删除的文件名字
     * @param sftp
     */
    public void delete(String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory
     *            要列出的目录
     * @param sftp
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory)
            throws SftpException {
        return sftp.ls(directory);
    }

    public static void main(String[] args) {
        FtpJSchTestLinux ftp = new FtpJSchTestLinux();
        ftp = ftp.getConnect();
        Vector listFiles;
        try {
            listFiles = ftp.listFiles(directory);
            System.out.println(listFiles);
          //  ftp.download("qrcode.jpg");
            String upload = ftp.upload(saveFile + "qrcode22.jpg");
            //System.out.println(upload);
            //6c19bac6-b863-4f0f-91e8-38d1a8e03164.xlsx
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            //关闭session和连接
            try {
                sftp.getSession().disconnect();
            } catch (JSchException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            sftp.quit();
            sftp.disconnect();
        }

    }


}
