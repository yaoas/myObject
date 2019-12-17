package com.springboot.controller;

import javax.servlet.http.HttpServletResponse;

import com.springboot.common.poiSecond.ExcelObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class MemberSchoolController {


    /**
     * @description:导出数据
     * @param:[schoolId, response]
     * @return:void
     * @date:2019/5/11
     * @author:tangyj
     * @remark:
     */
    @RequestMapping("/hello")
    public void download(
            HttpServletResponse response) throws Exception {
        //临时生成测试数据
        String fileName = "导出excel例子2.xls";
        String headTitle = "这是头标题";
        int colunmNum = 10;

        List<String> headTitleList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            headTitleList.add("第" + (i + 1) + "列标题");
        }
        List<List<String>> dataList = new ArrayList<List<String>>();
        for (int i = 0; i < 5; i++) {
            List<String> datas = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                datas.add("第" + (i + 1) + "行第" + (j + 1) + "列");
            }
            dataList.add(datas);
        }
        //1-创建一个HSSFWorkbook
        ExcelObject excel = new ExcelObject("实验数据");
        //2-写入头标题
        excel.createHeadTile(colunmNum, headTitle);//头标默认写在第一行
        //3-写入行标题
        excel.createRowTitle(headTitleList, 1);
        //4-写入具体数据
        excel.createDataByRow(2, dataList);//因为没有行标题，所以从第二行开始
        //5-生成excel文件
        //根据情况决定生成不生成文件
       // excel.buildExcelFile(fileName);
        //6-浏览器下载excel
        excel.buildExcelDocument(fileName, response);
    }
}