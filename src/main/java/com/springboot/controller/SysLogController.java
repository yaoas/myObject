package com.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.base.controller.BaseController;
import com.springboot.common.utils.R;
import com.springboot.entity.SysLogEntity;
import com.springboot.service.SysLogService;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 操作日志记录controller
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(SysLogController.class);
    @Autowired
    private SysLogService sysLogService;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    /**
     * 分页查询列表
     */
    @GetMapping(value = "/page/list")
    public Object pageList(SysLogEntity sysLogEntity) {
        QueryWrapper qw = new QueryWrapper<SysLogEntity>();
        qw.orderByDesc("create_time");
        IPage page = sysLogService.page(new Page(sysLogEntity.getCurrentPage(), sysLogEntity.getPageSize()), qw);
        return R.ok(page);
    }


    /**
     * 新增/修改
     */
    @PostMapping(value = "/save")
    public R save(@RequestBody SysLogEntity sceneSpotEntity){
        boolean saveFlag = sysLogService.saveOrUpdate(sceneSpotEntity);

        //log.info("");
        if (saveFlag) {
            return R.ok("保存成功！");
        } else {
            return R.fail("保存失败！");
        }
    }

}
