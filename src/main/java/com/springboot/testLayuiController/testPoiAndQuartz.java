package com.springboot.testLayuiController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.common.excelComomon.ExportExcel;
import com.springboot.common.quartzJob.QuartzJobManager;
import com.springboot.common.quartzJob.TestQuartz;
import com.springboot.common.quartzJob.TestQuartz2;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class testPoiAndQuartz {

    /**
     * @description:导出数据
     * @param:[Object, response]
     * @return:void
     * @author:yas
     * @remark:
     */
    @ApiOperation("导出")
    @RequestMapping("/exportExcel")
    public void download(
            HttpServletResponse response) throws Exception {
        String fileName = "订单列表.xls";
        String headTitle = "订单列表";
        QueryWrapper qw = new QueryWrapper<OrderInfoEntity>();
        //查询对应的条件
        List<OrderInfoEntity> list = new ArrayList<>();
        //标题列
        String titleArray[] ={"订单id","商户号","支付金额","支付状态"};
        int colunmNum = titleArray.length;
        List<String> headTitleList = new ArrayList<String>();
        for (int i = 0; i < colunmNum; i++) {
            headTitleList.add(titleArray[i]);
        }
        List<List<String>> dataList = new ArrayList<List<String>>();
        //拼接参数
        for (int i = 0; i < list.size(); i++) {
            List<String> datas = new ArrayList<>();
            OrderInfoEntity orderInfoEntity = list.get(i);
            datas.add(String.valueOf(orderInfoEntity.getId()));
            datas.add(orderInfoEntity.getOutTradeNo());
            datas.add(String.valueOf(orderInfoEntity.getAllElementPrice()));
            datas.add(orderInfoEntity.getPayStatus());
            dataList.add(datas);
        }
        ExportExcel.excelExport(colunmNum,headTitle,headTitleList,dataList,fileName,response);
    }
    @PostMapping("/task")
    public void task() throws Exception {
        String name = String.valueOf(new Date());
        QuartzJobManager.getInstance().addJob(TestQuartz.class, name,"testjob", "*/1 * * * * ?");
        QuartzJobManager.getInstance().addJob(TestQuartz2.class, name,"testjob2", "*/1 * * * * ?");
    }
}
