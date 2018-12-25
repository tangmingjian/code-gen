package com.yiyi.tang.services.impl;

import com.yiyi.tang.mappers.AuthOrderMapper;
import com.yiyi.tang.model.AuthOrder;
import com.yiyi.tang.services.base.AbstractService;
import com.yiyi.tang.services.AuthOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@author Tangmingjian 2018-12-25 16:23:32
 **/
@Service
@Slf4j
@Transactional
public class AuthOrderServiceImpl extends AbstractService<AuthOrder, Integer> implements AuthOrderService<AuthOrder, Integer> {

    @Autowired
    private AuthOrderMapper authorderMapper;
}
