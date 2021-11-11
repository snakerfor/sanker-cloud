package com.snaker.system.service.impl;

import com.snaker.common.core.text.Convert;
import com.snaker.system.domain.SysDictData;
import com.snaker.system.mapper.SysDictDataMapper;
import com.snaker.system.service.ISysDictDataService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典 业务层处理
 *
 * @author sfd
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {
    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        return dictDataMapper.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 通过字典ID删除字典数据信息
     *
     * @param dictCode 字典数据ID
     * @return 结果
     */
    @Override
    public int deleteDictDataById(Long dictCode) {
        return dictDataMapper.deleteDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    public int deleteDictDataByIds(String ids) {
        return dictDataMapper.deleteDictDataByIds(Convert.toStrArray(ids));
    }

    /**
     * 新增保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData dictData) {
        return dictDataMapper.insertDictData(dictData);
    }

    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData dictData) {
        return dictDataMapper.updateDictData(dictData);
    }

    /**
     * @return java.util.Map<java.lang.String, java.util.Map < java.lang.String, java.lang.String>>
     * @Description 字典表数据整合（用于导出数据）
     * @Param [types]
     * @Author snaker
     * @Date 2020/11/10 8:32
     **/
    @Override
    public Map<String, Map<String, String>> getDictDatasMap(String[] types) {
        Map<String, Map<String, String>> rs = new HashMap<String, Map<String, String>>();
        for (String type : types) {
            List<SysDictData> dictDataList = dictDataMapper.selectDictDataByType(type);
            Map<String, String> map = new HashMap<String, String>();
            for (SysDictData sysDictData : dictDataList) {
                map.put(sysDictData.getDictValue(), sysDictData.getDictLabel());
            }
            rs.put(type, map);
        }
        return rs;
    }

    @Override
    public Map<String, String> getLabelToValue(String type) {
        List<SysDictData> dictDataList = dictDataMapper.selectDictDataByType(type);
        Map<String, String> map = new HashMap<String, String>();
        for (SysDictData sysDictData : dictDataList) {
            map.put(sysDictData.getDictLabel(), sysDictData.getDictValue());
        }
        return map;
    }

    @Override
    public String getDicLabel(String dicType, String dicValue) {
        if (StringUtils.isAnyBlank(dicType, dicValue)) {
            return null;
        }
        List<SysDictData> dictDataListDB = dictDataMapper.selectDictDataByType(dicType);
        for (SysDictData sysDictDataDB : dictDataListDB) {
            String dictValueDB = sysDictDataDB.getDictValue();
            if (dicValue.equals(dictValueDB)) {
                return sysDictDataDB.getDictLabel();
            }
        }
        return null;
    }

    @Override
    public Map<String, List<SysDictData>> getSysDictDatasByDictTypes(String[] dictTypes) {
        Map<String, List<SysDictData>> result = new HashMap<String, List<SysDictData>>();
        if (dictTypes == null || dictTypes.length == 0) {
            return result;
        }

        SysDictData querySysDictData = new SysDictData();
        querySysDictData.setQueryDictTypes(new HashSet<>(Arrays.asList(dictTypes)));
        querySysDictData.setStatus("0");
        return dictDataMapper.selectDictDataList(querySysDictData).stream().collect(Collectors.groupingBy(item -> item.getDictType()));
    }

    @Override
    public Map<String, Map<String, String>> getDictTypeAndValueAndLabel(String[] dictTypes) {
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        if (dictTypes == null || dictTypes.length == 0) {
            return result;
        }

        Map<String, List<SysDictData>> sysDictDatasDBDictTypeAsKeyMap = getSysDictDatasByDictTypes(dictTypes);

        for (String dictType : dictTypes) {
            Map<String, String> map = new HashMap<String, String>();
            List<SysDictData> sysDictDatasDBByDictType = sysDictDatasDBDictTypeAsKeyMap.get(dictType);
            if (CollectionUtils.isNotEmpty(sysDictDatasDBByDictType)) {
                for (SysDictData sysDictData : sysDictDatasDBByDictType) {
                    map.put(sysDictData.getDictValue(), sysDictData.getDictLabel());
                }
            }
            result.put(dictType, map);
        }
        return result;
    }

    @Override
    public String getDictLabelByDictDataAndTypeAndValue(Map<String, Map<String, String>> dictDataMap, String dictType, String dictValue) {
        if (StringUtils.isAnyBlank(dictType, dictValue)) {
            throw new RuntimeException("异常:dictType或dictValue为空");
        }

        Map<String, String> dictDataOfMap = dictDataMap.get(dictType);
        if (dictDataOfMap != null) {
            return dictDataOfMap.get(dictValue);
        }
        return null;
    }

    @Override
    public String getDictValueByDictDataAndTypeAndLabel(Map<String, Map<String, String>> dictDataMap, String dictType, String dictLabel) {
        if (StringUtils.isAnyBlank(dictType, dictLabel)) {
            throw new RuntimeException("异常:dictType或dictLabel为空");
        }

        Map<String, String> dictDataOfMap = dictDataMap.get(dictType);
        if (dictDataOfMap != null) {
            for (String value : dictDataOfMap.keySet()) {
                if (dictLabel.equalsIgnoreCase(dictDataOfMap.get(value))) return value;
            }
            return null;
        }
        return null;
    }

}
