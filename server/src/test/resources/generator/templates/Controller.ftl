package ${basePackage};

import ${dtoPackage}.${modelNameUpperCamel}Dto;
import ${responsePackage}.PageableResData;
import ${responsePackage}.ResData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  @author ${author} ${date}
 **/
@Api("${modelNameUpperCamel} apis")
public interface ${modelNameUpperCamel}Controller {

    @PostMapping
    @ApiOperation(value = "Add ${modelNameLowerCamel}Dto")
    ResData<Boolean> save(${modelNameUpperCamel}Dto ${modelNameLowerCamel}Dto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete ${modelNameLowerCamel}")
    ResData<Boolean> delete(@PathVariable("id") String id);

    @PutMapping
    @ApiOperation(value = "Modify ${modelNameLowerCamel}Dto")
    ResData<Boolean> update(${modelNameUpperCamel}Dto ${modelNameLowerCamel}Dto);

    @GetMapping("/{id}")
    @ApiOperation(value = "Get ${modelNameLowerCamel}")
    ResData<${modelNameUpperCamel}Dto> find(@PathVariable("id") String id);

    @GetMapping("all")
    @ApiOperation(value = "Get all ${modelNameLowerCamel}s pageable")
    PageableResData<List<${modelNameUpperCamel}Dto>> findAllPageable(@RequestParam(value = "page", defaultValue = "1") int currPage, @RequestParam(value = "size", defaultValue = "10") int size);
}
