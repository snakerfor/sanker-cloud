package com.snaker.system.controller;

import com.snaker.system.domain.SysUserFavor;
import com.snaker.system.service.ISysUserFavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snaker.common.core.domain.R;
import com.snaker.common.core.controller.BaseController;

import java.util.Date;
import java.util.List;

/**
 * 用户喜好 提供者
 *
 * @author snaker
 * @date 2020-12-02
 */
@RestController
@RequestMapping("sysUserFavor")
public class SysUserFavorController extends BaseController {

    @Autowired
    private ISysUserFavorService sysUserFavorService;

    /**
     * 查询用户喜好
     */
    @GetMapping("get/{id}")
    public SysUserFavor get(@PathVariable("id") String id) {
        return sysUserFavorService.selectSysUserFavorById(id);

    }

    /**
     * 查询用户喜好列表
     */
    @GetMapping("list")
    public R list(SysUserFavor sysUserFavor) {
        startPage();
        return result(sysUserFavorService.selectSysUserFavorList(sysUserFavor));
    }

    /**
     * 查询用户喜好列表
     */
    @GetMapping("getUserFavor")
    public R getUserFavor(SysUserFavor sysUserFavor) {
        startPage();
        sysUserFavor.setUserId(getCurrentUserId());
        List<SysUserFavor> sysUserFavors = sysUserFavorService.selectSysUserFavorList(sysUserFavor);
        if (sysUserFavors.size() > 0) {
            return R.data(sysUserFavors.get(0).getRecord());
        }
        return R.ok();
    }


    /**
     * 新增保存用户喜好
     */
    @PostMapping("save")
    public R addSave(@RequestBody SysUserFavor sysUserFavor) {
        return toAjax(sysUserFavorService.insertSysUserFavor(sysUserFavor));
    }

    /**
     * 提交用户喜好
     */
    @PostMapping("submit")
    public R submit(@RequestBody SysUserFavor sysUserFavor) {
        sysUserFavor.setUserId(getCurrentUserId());
        List<SysUserFavor> sysUserFavors = sysUserFavorService.selectSysUserFavorList(sysUserFavor);
        if (sysUserFavors.size() > 0) {
            sysUserFavors.get(0).setRecord(sysUserFavor.getRecord());
            return toAjax(sysUserFavorService.updateSysUserFavor(sysUserFavors.get(0)));
        } else {
            sysUserFavor.setUpdateTime(new Date());
            return toAjax(sysUserFavorService.insertSysUserFavor(sysUserFavor));
        }
    }

    /**
     * 修改保存用户喜好
     */
    @PostMapping("update")
    public R editSave(@RequestBody SysUserFavor sysUserFavor) {
        return toAjax(sysUserFavorService.updateSysUserFavor(sysUserFavor));
    }

    /**
     * 删除用户喜好
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(sysUserFavorService.deleteSysUserFavorByIds(ids));
    }

}
