package com.snaker.system.mapper;

import com.snaker.system.domain.SysUserFavor;

import java.util.List;

/**
 * 用户喜好Mapper接口
 * 
 * @author snaker
 * @date 2020-12-02
 */
public interface SysUserFavorMapper 
{
    /**
     * 查询用户喜好
     * 
     * @param id 用户喜好ID
     * @return 用户喜好
     */
    public SysUserFavor selectSysUserFavorById(String id);

    /**
     * 查询用户喜好列表
     * 
     * @param sysUserFavor 用户喜好
     * @return 用户喜好集合
     */
    public List<SysUserFavor> selectSysUserFavorList(SysUserFavor sysUserFavor);

    /**
     * 新增用户喜好
     * 
     * @param sysUserFavor 用户喜好
     * @return 结果
     */
    public int insertSysUserFavor(SysUserFavor sysUserFavor);

    /**
     * 修改用户喜好
     * 
     * @param sysUserFavor 用户喜好
     * @return 结果
     */
    public int updateSysUserFavor(SysUserFavor sysUserFavor);

    /**
     * 删除用户喜好
     * 
     * @param id 用户喜好ID
     * @return 结果
     */
    public int deleteSysUserFavorById(String id);

    /**
     * 批量删除用户喜好
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserFavorByIds(String[] ids);
}
