package com.snaker.system.controller;

import com.snaker.common.auth.annotation.HasPermissions;
import com.snaker.common.core.controller.BaseController;
import com.snaker.common.core.domain.R;
import com.snaker.common.pojo.entity.SysProcess;
import com.snaker.common.pojo.entity.TwoTuple;
import com.snaker.system.domain.SysDept;
import com.snaker.system.service.IOAWebService;
import com.snaker.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 部门 提供者
 *
 * @author snaker
 * @date 2019-05-20
 */
@RestController
@RequestMapping("dept")
public class SysDeptController extends BaseController {

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private IOAWebService ioaWebService;

    /**
     * 查询部门
     */
    @GetMapping("get/{deptId}")
    public SysDept get(@PathVariable("deptId") Long deptId) {
        return sysDeptService.selectDeptById(deptId);
    }

    /**
     * 查询部门列表
     */
    @GetMapping("list")
    public R list(SysDept sysDept) {
        startPage();
        return result(sysDeptService.selectDeptList(sysDept));
    }

    /**
     * 查询所有可用部门
     */
    @GetMapping("list/enable")
    public R listEnable(SysDept sysDept) {
        sysDept.setStatus("0");
        return result(sysDeptService.selectDeptList(sysDept));
    }

    /**
     * 新增保存部门
     */
    @PostMapping("save")
    public R addSave(@RequestBody SysDept sysDept) {
        return toAjax(sysDeptService.insertDept(sysDept));
    }

    /**
     * 修改保存部门
     */
    @PostMapping("update")
    public R editSave(@RequestBody SysDept sysDept) {
        return toAjax(sysDeptService.updateDept(sysDept));
    }

    /**
     * 删除部门
     */
    @PostMapping("remove/{deptId}")
    public R remove(@PathVariable("deptId") Long deptId) {
        return toAjax(sysDeptService.deleteDeptById(deptId));
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/role/{roleId}")
    public Set<String> deptTreeData(@PathVariable("roleId") Long roleId) {
        if (null == roleId || roleId <= 0) return null;
        return sysDeptService.roleDeptIds(roleId);
    }

    @HasPermissions("system:deptuser:refresh")
    @GetMapping("/syncDeptUser")
    public R syncDeptUser() throws Exception {
        TwoTuple<SysProcess, Boolean> twoTuple = ioaWebService.initSyncDeptAndUserProcess(false);
        SysProcess sysProcess = twoTuple.getT1();
        //说明是新建的进程，需要出发执行
        if (twoTuple.getT2()) {
            ioaWebService.asyncSyncDeptAndUser(sysProcess);
        }
        return R.data(sysProcess);
    }
}
