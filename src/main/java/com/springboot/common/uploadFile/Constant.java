package com.springboot.common.uploadFile;

public class Constant {
    // 默认日期格式
    public static final String SYS_DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    //ftp地址
    public static final String FTP_ADDRESS = "192.168.0.188";
    //ftp端口
    public static final int FTP_PORT = 21;
    //ftp用户名
    public static final String FTP_USERNAME = "123";
    //ftp密码
    public static final String FTP_PASSWORD = "123";
    // FTP状态码
    public final static int FTP_REPLY_SUCCESS = 230;
    // FTP存放根路径
    public final static String FTP_PATH = "/webapps/longman/upload";
    // 自定义文件上传到服务器路径
    public final static String CUST_PATH = "/opt/test/image/";
    // 自定义文件服务器ip
    public final static String CUST_IP_PATH = "http://39.97.237.246/image/";
    // 自定义文件上传到项目某路径
    public final static String CUST_LOCALHOST_PATH = "\\src\\main\\resources\\images\\";
    //fastdfs的https地址
    public final static String FASTDFS_PATH = "https://fdfs.baodingjl.com/";




}
