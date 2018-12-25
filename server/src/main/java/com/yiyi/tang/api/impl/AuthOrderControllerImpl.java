package com.yiyi.tang.api.impl;

import com.github.pagehelper.PageHelper;
import com.yiyi.tang.api.AuthOrderController;
import com.yiyi.tang.dto.AuthOrderDto;
import com.yiyi.tang.model.AuthOrder;
import com.yiyi.tang.response.PageableResData;
import com.yiyi.tang.response.PageableResDataBuilder;
import com.yiyi.tang.response.ResData;
import com.yiyi.tang.response.ResDataBuilder;
import com.yiyi.tang.services.AuthOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tangmingjian 2018-12-25 16:23:32
 **/
@RequestMapping("/authorder")
@RestController
public class AuthOrderControllerImpl implements AuthOrderController {
    private final AuthOrderService authorderService;

    @Autowired
    public AuthOrderControllerImpl(AuthOrderService authorderService) {
        this.authorderService = authorderService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<Boolean> save(AuthOrderDto authorderDto) {
        return ResDataBuilder.ok(authorderService.save(convert2AuthOrder(authorderDto)));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<Boolean> delete(Integer id) {
        return ResDataBuilder.ok(authorderService.deleteById(id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<Boolean> update(AuthOrderDto authorderDto) {
        return ResDataBuilder.ok(authorderService.update(convert2AuthOrder(authorderDto)));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<AuthOrderDto> find(Integer id) {
        AuthOrder authorder = (AuthOrder) authorderService.findById(id);
        return ResDataBuilder.ok(convert2AuthOrderDto(authorder));
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageableResData<List<AuthOrderDto>> findAllPageable(int currPage, int size) {
        PageHelper.startPage(currPage, size);
        List<AuthOrder> all = authorderService.findAll();
        if (CollectionUtils.isEmpty(all)) {
            return PageableResDataBuilder.ok(null, currPage, size, 0);
        } else {
            int totalCount = authorderService.count(new AuthOrder());
            List<AuthOrderDto> authorderDtos = all.stream().map(this::convert2AuthOrderDto).collect(Collectors.toList());
            return PageableResDataBuilder.ok(authorderDtos, currPage, size, totalCount);
        }
    }

    private AuthOrderDto convert2AuthOrderDto(AuthOrder authorder) {
        if (authorder == null) {
            return null;
        }
        AuthOrderDto authorderDto = new AuthOrderDto();
        BeanUtils.copyProperties(authorder, authorderDto);
        return authorderDto;
    }

    private AuthOrder convert2AuthOrder(AuthOrderDto authorderDto) {
        if (authorderDto == null) {
            return null;
        }
        AuthOrder authorder = new AuthOrder();
        BeanUtils.copyProperties(authorderDto, authorder);
        return authorder;
    }

}
