package com.yiyi.tang.api.impl;

import com.github.pagehelper.PageHelper;
import com.yiyi.tang.api.UserController;
import com.yiyi.tang.dto.UserDto;
import com.yiyi.tang.model.User;
import com.yiyi.tang.response.PageableResData;
import com.yiyi.tang.response.PageableResDataBuilder;
import com.yiyi.tang.response.ResData;
import com.yiyi.tang.response.ResDataBuilder;
import com.yiyi.tang.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tangmingjian 2018-12-23 16:07:13
 **/
@RequestMapping("/user")
@RestController
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<Boolean> save(UserDto userDto) {
        return ResDataBuilder.ok(userService.save(userDto));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<Boolean> delete(String id) {
        return ResDataBuilder.ok(userService.deleteById(id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<Boolean> update(UserDto userDto) {
        return ResDataBuilder.ok(userService.update(userDto));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<UserDto> find(String id) {
        User user = (User) userService.findById(id);
        return ResDataBuilder.ok(convert2User(user));
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageableResData<List<UserDto>> findAllPageable(int currPage, int size) {
        PageHelper.startPage(currPage, size);
        List<User> all = userService.findAll();
        if (CollectionUtils.isEmpty(all)) {
            return PageableResDataBuilder.ok(null, currPage, size, 0);
        } else {
            int totalCount = userService.count(new User());
            List<UserDto> userDtos = all.stream().map(this::convert2User).collect(Collectors.toList());
            return PageableResDataBuilder.ok(userDtos, currPage, size, totalCount);
        }
    }

    private UserDto convert2User(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
