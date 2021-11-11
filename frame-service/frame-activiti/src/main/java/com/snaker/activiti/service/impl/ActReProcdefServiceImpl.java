/*
 * @(#)ActReProcdefServiceImpl.java 2020年1月6日 上午10:22:35
 * Copyright 2020 snaker, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snaker.activiti.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snaker.activiti.domain.ActReProcdef;
import com.snaker.activiti.mapper.ActReProcdefMapper;
import com.snaker.activiti.service.IActReProcdefService;

/**
 * <p>File：ActReProcdefServiceImpl.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月6日 上午10:22:35</p>
 * <p>Company: snakerit.com </p>
 * @author snaker
 * @version 1.0
 */
@Service
public class ActReProcdefServiceImpl implements IActReProcdefService
{
    @Autowired
    private ActReProcdefMapper procdefMapper;

    /* (non-Javadoc)
     * @see com.snaker.activiti.service.IActReProcdefService#selectList(com.snaker.activiti.domain.ActReProcdef)
     */
    @Override
    public List<ActReProcdef> selectList(ActReProcdef actReProcdef)
    {
        return procdefMapper.selectActReProcdefList(actReProcdef);
    }
}
