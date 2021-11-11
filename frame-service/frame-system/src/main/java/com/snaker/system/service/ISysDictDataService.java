package com.snaker.system.service;

import java.util.List;
import java.util.Map;

import com.snaker.system.domain.SysDictData;

/**
 * 字典 业务层
 *
 * @author sfd
 */
public interface ISysDictDataService
{
    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String selectDictLabel(String dictType, String dictValue);

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * 通过字典ID删除字典数据信息
     *
     * @param dictCode 字典数据ID
     * @return 结果
     */
    public int deleteDictDataById(Long dictCode);

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteDictDataByIds(String ids);

    /**
     * 新增保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int insertDictData(SysDictData dictData);

    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int updateDictData(SysDictData dictData);

    Map<String, Map<String,String>> getDictDatasMap(String[] types);

    Map<String, String> getLabelToValue(String type);

    String getDicLabel(String dicType, String dicValue);

    Map<String, List<SysDictData>> getSysDictDatasByDictTypes(String[] dictTypes);

    Map<String, Map<String, String>> getDictTypeAndValueAndLabel(String[] dictTypes);

    String getDictLabelByDictDataAndTypeAndValue(Map<String, Map<String, String>> dictDataMap, String dictType, String dictValue);

    String getDictValueByDictDataAndTypeAndLabel(Map<String, Map<String, String>> dictDataMap, String dictType, String dictLabel);
}
