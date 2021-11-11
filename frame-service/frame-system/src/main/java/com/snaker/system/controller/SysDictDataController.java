package com.snaker.system.controller;

import com.snaker.common.auth.annotation.HasPermissions;
import com.snaker.common.core.controller.BaseController;
import com.snaker.common.core.domain.R;
import com.snaker.common.log.annotation.OperLog;
import com.snaker.common.log.enums.BusinessType;
import com.snaker.system.domain.SysDictData;
import com.snaker.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 字典数据 提供者
 *
 * @author snaker
 * @date 2019-05-20
 */
@RestController
@RequestMapping("dict/data")
public class SysDictDataController extends BaseController {

    @Autowired
    private ISysDictDataService sysDictDataService;

    /**
     * 查询字典数据
     */
    @GetMapping("get/{dictCode}")
    public SysDictData get(@PathVariable("dictCode") Long dictCode) {
        return sysDictDataService.selectDictDataById(dictCode);

    }

    /**
     * 查询字典数据列表
     */
    @GetMapping("list")
    @HasPermissions("system:dict:list")
    public R list(SysDictData sysDictData) {
        startPage();
        return result(sysDictDataService.selectDictDataList(sysDictData));
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    @GetMapping("type/{dictType}")
    public List<SysDictData> getType(@PathVariable String dictType) {
        return sysDictDataService.selectDictDataByType(dictType);
    }

    @PostMapping("/types")
    public Map<String, List<SysDictData>> getTypes(@RequestBody String[] dictTypes) {
        return sysDictDataService.getSysDictDatasByDictTypes(dictTypes);
    }

    /**
     * @return java.util.List<com.snaker.system.domain.SysDictData>
     * @Description 获取字典数据by字典类型
     * @Param [dictType]
     * @Author snaker
     * @Date 2020/11/11 13:32
     **/
    @GetMapping("type")
    public List<SysDictData> getDataByType(@RequestParam("dictType") String dictType) {
        return sysDictDataService.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @GetMapping("label")
    public String getLabel(String dictType, String dictValue) {
        return sysDictDataService.selectDictLabel(dictType, dictValue);
    }


    /**
     * 新增保存字典数据
     */
    @OperLog(title = "字典数据", businessType = BusinessType.INSERT)
    @HasPermissions("system:dict:add")
    @PostMapping("save")
    public R addSave(@RequestBody SysDictData sysDictData) {
        return toAjax(sysDictDataService.insertDictData(sysDictData));
    }

    /**
     * 修改保存字典数据
     */
    @OperLog(title = "字典数据", businessType = BusinessType.UPDATE)
    @HasPermissions("system:dict:edit")
    @PostMapping("update")
    public R editSave(@RequestBody SysDictData sysDictData) {
        return toAjax(sysDictDataService.updateDictData(sysDictData));
    }

    /**
     * 删除字典数据
     */
    @OperLog(title = "字典数据", businessType = BusinessType.DELETE)
    @HasPermissions("system:dict:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(sysDictDataService.deleteDictDataByIds(ids));
    }

}
