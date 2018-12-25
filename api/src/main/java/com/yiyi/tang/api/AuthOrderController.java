package com.yiyi.tang.api;

import com.yiyi.tang.dto.AuthOrderDto;
import com.yiyi.tang.response.PageableResData;
import com.yiyi.tang.response.ResData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  @author Tangmingjian 2018-12-25 16:23:32
 **/
@Api("AuthOrder apis")
public interface AuthOrderController {

    @PostMapping
    @ApiOperation(value = "Add authorder")
    ResData<Boolean> save(@RequestBody AuthOrderDto authorderDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete authorder")
    ResData<Boolean> delete(@PathVariable("id") Integer id);

    @PutMapping
    @ApiOperation(value = "Modify authorder")
    ResData<Boolean> update(@RequestBody AuthOrderDto authorderDto);

    @GetMapping("/{id}")
    @ApiOperation(value = "Get authorder")
    ResData<AuthOrderDto> find(@PathVariable("id") Integer id);

    @GetMapping("all")
    @ApiOperation(value = "Get all authorders pageable")
    PageableResData<List<AuthOrderDto>> findAllPageable(@RequestParam(value = "page", defaultValue = "1") int currPage, @RequestParam(value = "size", defaultValue = "10") int size);
}
