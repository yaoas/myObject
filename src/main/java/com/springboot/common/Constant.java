package com.springboot.common;

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
    public final static String FTP_PATH = "/webapps/tengli/upload";
    // Redis缓存名
    public final static String USER = "USER";

    // 是否
    public static final String SYS_TRUE = "1";
    public static final String SYS_FALSE = "0";

    /* 订单状态 */
    // 是否存在子节点
    public static final String ORDER_CLD_TOW = "2";
    public static final String ORDER_CLD_ONE = "1";
    public static final String ORDER_CLD_EMPTY = "0";
    /* 提现类型 */
    public static final String CASH_TYPE_CARD = "1";
    public static final String CASH_TYPE_REMAIN = "0";
}
