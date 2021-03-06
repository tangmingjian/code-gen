package com.yiyi.tang.api;

import com.yiyi.tang.dto.UserDto;
import com.yiyi.tang.response.PageableResData;
import com.yiyi.tang.response.ResData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  @author Tangmingjian 2018-12-25 15:49:32
 **/
@Api("User apis")
public interface UserController {

    @PostMapping
    @ApiOperation(value = "Add user")
    ResData<Boolean> save(@RequestBody UserDto userDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user")
    ResData<Boolean> delete(@PathVariable("id") String id);

    @PutMapping
    @ApiOperation(value = "Modify user")
    ResData<Boolean> update(@RequestBody UserDto userDto);

    @GetMapping("/{id}")
    @ApiOperation(value = "Get user")
    ResData<UserDto> find(@PathVariable("id") String id);

    @GetMapping("all")
    @ApiOperation(value = "Get all users pageable")
    PageableResData<List<UserDto>> findAllPageable(@RequestParam(value = "page", defaultValue = "1") int currPage, @RequestParam(value = "size", defaultValue = "10") int size);
}
