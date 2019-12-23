package com.springboot.controller;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.common.poiSecond.ExcelObject;
import com.springboot.common.utils.R;
import com.springboot.entity.SysLogEntity;
import com.springboot.service.SysLogService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("hello")
public class ExcelImportOrExportController {
    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";
    @Autowired
    private SysLogService sysLogService;
    /**
     * @description:导出数据
     * @param:[schoolId, response]
     * @return:void
     * @date:2019/5/11
     * @author:tangyj
     * @remark:
     */
    @RequestMapping("/exportExcel")
    public void download(
            HttpServletResponse response) throws Exception {
        //临时生成测试数据
        String fileName = "系统日志列表.xls";
        String headTitle = "系统日志列表";
        QueryWrapper qw = new QueryWrapper<SysLogEntity>();
        qw.orderByDesc("create_time");
        List<SysLogEntity> list = sysLogService.list(qw);
        //标题列
        String titleArray[] ={"请求地址","请求方式","请求ip","请求方法","请求参数"};
        int colunmNum = titleArray.length;
        List<String> headTitleList = new ArrayList<String>();
        for (int i = 0; i < colunmNum; i++) {
            headTitleList.add(titleArray[i]);
        }
        List<List<String>> dataList = new ArrayList<List<String>>();
        //拼接参数
        for (int i = 0; i < list.size(); i++) {
                List<String> datas = new ArrayList<>();
                SysLogEntity sysLogEntity = list.get(i);
                datas.add(sysLogEntity.getRequestUrl());
                datas.add(sysLogEntity.getRequestMode());
                datas.add(sysLogEntity.getRequestIp());
                datas.add(sysLogEntity.getRequestClassMethod());
                datas.add(sysLogEntity.getRequestParams());
                dataList.add(datas);
        }
        //1-创建一个HSSFWorkbook
        ExcelObject excel = new ExcelObject("系统日志");
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
    /**
     * 导入excel
     */
    @RequestMapping("/importExcel")
    public  Object importExcel(MultipartFile myFile)throws Exception{
        //        1、用HSSFWorkbook打开或者创建“Excel文件对象”
        //
        //        2、用HSSFWorkbook对象返回或者创建Sheet对象
        //
        //        3、用Sheet对象返回行对象，用行对象得到Cell对象
        //
        //        4、对Cell对象读写。
        //获得文件名
        Workbook workbook = null ;
        String fileName = myFile.getOriginalFilename();
        if(fileName.endsWith(XLS)){
            //2003
            workbook = new HSSFWorkbook(myFile.getInputStream());
        }else if(fileName.endsWith(XLSX)){
            //2007
            workbook = new XSSFWorkbook(myFile.getInputStream());
        }else{
            throw new Exception("文件不是Excel文件");
        }

        Sheet sheet = workbook.getSheet("Sheet1");
        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        if(rows==0){
            try {
                throw new Exception("请填写数据");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 1; i <= rows+1; i++) {
            // 读取左上端单元格
            Row row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                // **读取cell**
                SysLogEntity sceneSpotEntity = new SysLogEntity();
                //姓名
                String name = getCellValue(row.getCell(0));
                sceneSpotEntity.setErrorMessage(name);
                //密码
                String password = getCellValue(row.getCell(1));
                sceneSpotEntity.setRequestParams(password);
                sysLogService.save(sceneSpotEntity);
            }
        }

        return R.ok(rows-1);

    }
    /**
     * 获得Cell内容
     *
     * @param cell
     * @return
     */
    public  String getCellValue(Cell cell) {
        String value = "";
        if (cell != null) {
            // 以下是判断数据的类型
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    value = cell.getNumericCellValue() + "";
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if (date != null) {
                            value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        } else {
                            value = "";
                        }
                    } else {
                        value = new DecimalFormat("0").format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    value = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    value = cell.getBooleanCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                    value = cell.getCellFormula() + "";
                    break;
                case HSSFCell.CELL_TYPE_BLANK: // 空值
                    value = "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR: // 故障
                    value = "非法字符";
                    break;
                default:
                    value = "未知类型";
                    break;
            }
        }
        return value.trim();
    }
}