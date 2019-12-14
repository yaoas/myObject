package com.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.common.base.entity.DataEntity;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("sys_log")
public class SysLogEntity extends DataEntity<Long> {
    /**
     * 主键id
     */
    @TableId
    private  Long id;
    /**
     * 请求地址
     */
    @TableField(value = "requestUrl")
    private  String requestUrl;
    /**
     * 请求方式
     */
    @TableField(value = "requestMode")
    private  String requestMode;
    /**
     * 请求ip
     */
    @TableField(value = "requestIp")
    private  String requestIp;
    /**
     * 请求方法
     */
    @TableField(value = "requestClassMethod")
    private  String requestClassMethod;
    /**
     * 参数
     */
    @TableField(value = "requestParams")
    private  String requestParams;
    /**
     * 结果
     */
    @TableField(value = "requestResponse")
    private  String requestResponse;
    /**
     * 请求时间
     */
    @TableField(value = "requestTime")
    private  String requestTime;
    /**
     * 0正常1异常
     */
    @TableField(value = "ifError")
    private  String ifError;
    /**
     * 异常信息
     */
    @TableField(value = "errorMessage")
    private  String errorMessage;



    @Override
    protected Serializable pkVal() {
        return null;
    }
}
