package com.springboot.controller;

import com.springboot.common.CreatrQrCode.QRCodeUtil;

public class QRTest {

    public static void main(String[] args) {
        //生成二维码
        String content= "测试二维码生成内容";
        String imgPath="123.jpg";//二维码logo
        String destPath ="log";//生成二维码的地方
        System.out.println("logo图片地址imgPath=="+imgPath);
        System.out.println("存放二维码图片的地址destPath=="+destPath);
        try {
            QRCodeUtil.encode(content,imgPath,destPath, true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //解析二维码
        String destPath1 = "log/47571.jpg"; //解析图片的路径
        try {
            String result = QRCodeUtil.decode(destPath1);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(destPath1+"不是二维码");
        }
    }
}
