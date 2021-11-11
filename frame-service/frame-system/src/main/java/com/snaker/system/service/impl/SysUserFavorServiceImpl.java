package com.snaker.system.service.impl;

import java.util.List;
import com.snaker.common.utils.DateUtils;
import com.snaker.system.domain.SysUserFavor;
import com.snaker.system.mapper.SysUserFavorMapper;
import com.snaker.system.service.ISysUserFavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.snaker.common.core.text.Convert;

/**
 * 用户喜好Service业务层处理
 * 
 * @author snaker
 * @date 2020-12-02
 */
@Service
public class SysUserFavorServiceImpl implements ISysUserFavorService
{
    @Autowired
    private SysUserFavorMapper sysUserFavorMapper;

    /**
     * 查询用户喜好
     * 
     * @param id 用户喜好ID
     * @return 用户喜好
     */
    @Override
    public SysUserFavor selectSysUserFavorById(String id)
    {
        return sysUserFavorMapper.selectSysUserFavorById(id);
    }

    /**
     * 查询用户喜好列表
     * 
     * @param sysUserFavor 用户喜好
     * @return 用户喜好
     */
    @Override
    public List<SysUserFavor> selectSysUserFavorList(SysUserFavor sysUserFavor)
    {
        return sysUserFavorMapper.selectSysUserFavorList(sysUserFavor);
    }

    /**
     * 新增用户喜好
     * 
     * @param sysUserFavor 用户喜好
     * @return 结果
     */
    @Override
    public int insertSysUserFavor(SysUserFavor sysUserFavor)
    {
        sysUserFavor.setCreateTime(DateUtils.getNowDate());
        return sysUserFavorMapper.insertSysUserFavor(sysUserFavor);
    }

    /**
     * 修改用户喜好
     * 
     * @param sysUserFavor 用户喜好
     * @return 结果
     */
    @Override
    public int updateSysUserFavor(SysUserFavor sysUserFavor)
    {
        sysUserFavor.setUpdateTime(DateUtils.getNowDate());
        return sysUserFavorMapper.updateSysUserFavor(sysUserFavor);
    }

    /**
     * 删除用户喜好对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysUserFavorByIds(String ids)
    {
        return sysUserFavorMapper.deleteSysUserFavorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户喜好信息
     * 
     * @param id 用户喜好ID
     * @return 结果
     */
    public int deleteSysUserFavorById(String id)
    {
        return sysUserFavorMapper.deleteSysUserFavorById(id);
    }
}
