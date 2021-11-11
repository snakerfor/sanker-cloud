/*
 * @(#)IActReProcdefService.java 2020年1月6日 上午10:21:18
 * Copyright 2020 snaker, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snaker.activiti.service;

import java.util.List;

import com.snaker.activiti.domain.ActReProcdef;

/**
 * <p>File：IActReProcdefService.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月6日 上午10:21:18</p>
 * <p>Company: snakerit.com </p>
 * @author snaker
 * @version 1.0
 */
public interface IActReProcdefService
{
    public List<ActReProcdef> selectList(ActReProcdef actReProcdef);
}
