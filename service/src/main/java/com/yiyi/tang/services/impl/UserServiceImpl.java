package com.yiyi.tang.services.impl;

import com.yiyi.tang.mappers.UserMapper;
import com.yiyi.tang.model.User;
import com.yiyi.tang.services.base.AbstractService;
import com.yiyi.tang.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@author Tangmingjian 2018-12-25 15:07:20
 **/
@Service
@Slf4j
@Transactional
public class UserServiceImpl extends AbstractService<User, String> implements UserService<User, String> {

    @Autowired
    private UserMapper userMapper;
}
