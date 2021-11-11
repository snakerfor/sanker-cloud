package com.snaker.system.service.impl;

import com.snaker.system.mapper.SysPostMapper;
import com.snaker.system.mapper.SysUserPostMapper;
import com.snaker.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 岗位信息 服务层处理
 *
 * @author sfd
 */
@Service
public class SysPostServiceImpl implements ISysPostService {
    
    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

}
