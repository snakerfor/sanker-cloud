/*
 * @(#)ActReProcdefMapper.java 2020年1月6日 上午10:20:27
 * Copyright 2020 snaker, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snaker.activiti.mapper;

import com.snaker.activiti.domain.ActReProcdef;
import com.snaker.common.core.dao.BaseMapper;

import java.util.List;

/**
 * <p>File：ActReProcdefMapper.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月6日 上午10:20:27</p>
 * <p>Company: snakerit.com </p>
 * @author snaker
 * @version 1.0
 */
public interface ActReProcdefMapper
{
    public List<ActReProcdef> selectActReProcdefList(ActReProcdef actReProcdef);
}
