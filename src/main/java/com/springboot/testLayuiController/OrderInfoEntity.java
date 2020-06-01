package com.springboot.testLayuiController;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述: 
 * author: Larry
 * date: 2020-05-27
 */
@TableName("order_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("订单实体类")
public class OrderInfoEntity {

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "out_trade_no")
    @ApiModelProperty(value="商户订单号")
    private String outTradeNo;

    @TableField(value = "pay_status")
    @ApiModelProperty(value="支付状态:0(未付款)1（已付款）2已接单")
    private String payStatus;

    @TableField(value = "all_element_price")
    @ApiModelProperty(value="总金额(元)")
    private BigDecimal allElementPrice;

    @TableField(value = "address_id")
    @ApiModelProperty(value="用户地址id")
    private Long addressId;

    @TableField(value = "user_address")
    @ApiModelProperty(value="用户收货地址")
    private String userAddress;

    @TableField(value = "user_phone")
    @ApiModelProperty(value="用户手机号")
    private String userPhone;

    @TableField(value = "user_name")
    @ApiModelProperty(value="收货人")
    private String userName;

    @TableField(value = "self_store_address")
    @ApiModelProperty(value="商家地址")
    private String selfStoreAddress;

    @TableField(value = "remarks")
    @ApiModelProperty(value="备注(限制80字）")
    private String remarks;


    @TableField(value = "real_pay_element")
    @ApiModelProperty(value="实际支付费用(元)  优惠后")
    private BigDecimal realPayElement;

    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id")
    private Long userId;


    @TableField(value = "expiration_time")
    @ApiModelProperty(value="支付过期时间")
    private Date expirationTime;

    @TableField(value = "expiration_minutes")
    @ApiModelProperty(value="过期时间,分钟")
    private Integer expirationMinutes;

    @TableField(value = "user_del_flag")
    @ApiModelProperty(value="用户删除标记")
    private Integer userDelFlag;

    @TableField(value = "pay_way")
    @ApiModelProperty(value="支付方式 0微信 1余额")
    private Integer payWay;

    @TableField(value = "before_money_element")
    @ApiModelProperty(value="支付前余额元")
    private BigDecimal beforeMoneyElement;

    @TableField(value = "money_element")
    @ApiModelProperty(value="支付后余额元")
    private BigDecimal moneyElement;

    @TableField(value = "refund_application_time")
    @ApiModelProperty(value="发起退款时间")
    private BigDecimal refundApplicationTime;

    @TableField(value = "refund_reason")
    @ApiModelProperty(value="退款原因")
    private BigDecimal refundReason;

    @TableField(value = "money_element")
    @ApiModelProperty(value="实际退款时间")
    private BigDecimal actualRefundTime;

}