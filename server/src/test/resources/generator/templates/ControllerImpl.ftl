package ${basePackage};

import com.github.pagehelper.PageHelper;
import ${apiPackage}.${modelNameUpperCamel}Controller;
import ${dtoPackage}.${modelNameUpperCamel}Dto;
import ${modelPackage}.${modelNameUpperCamel};
import ${responsePackage}.PageableResData;
import ${responsePackage}.PageableResDataBuilder;
import ${responsePackage}.ResData;
import ${responsePackage}.ResDataBuilder;
import ${servicePackage}.${modelNameUpperCamel}Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ${author} ${date}
 **/
@RequestMapping("/${modelNameLowerCamel}")
@RestController
public class ${modelNameUpperCamel}ControllerImpl implements ${modelNameUpperCamel}Controller {
    private final ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @Autowired
    public ${modelNameUpperCamel}ControllerImpl(${modelNameUpperCamel}Service ${modelNameLowerCamel}Service) {
        this.${modelNameLowerCamel}Service = ${modelNameLowerCamel}Service;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<Boolean> save(${modelNameUpperCamel}Dto ${modelNameLowerCamel}Dto) {
        return ResDataBuilder.ok(${modelNameLowerCamel}Service.save(${modelNameLowerCamel}Dto));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<Boolean> delete(String id) {
        return ResDataBuilder.ok(${modelNameLowerCamel}Service.deleteById(id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<Boolean> update(${modelNameUpperCamel}Dto ${modelNameLowerCamel}Dto) {
        return ResDataBuilder.ok(${modelNameLowerCamel}Service.update(${modelNameLowerCamel}Dto));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResData<${modelNameUpperCamel}Dto> find(String id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = (${modelNameUpperCamel}) ${modelNameLowerCamel}Service.findById(id);
        return ResDataBuilder.ok(convert2${modelNameUpperCamel}(${modelNameLowerCamel}));
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageableResData<List<${modelNameUpperCamel}Dto>> findAllPageable(int currPage, int size) {
        PageHelper.startPage(currPage, size);
        List<${modelNameUpperCamel}> all = ${modelNameLowerCamel}Service.findAll();
        if (CollectionUtils.isEmpty(all)) {
            return PageableResDataBuilder.ok(null, currPage, size, 0);
        } else {
            int totalCount = ${modelNameLowerCamel}Service.count(new ${modelNameUpperCamel}());
            List<${modelNameUpperCamel}Dto> ${modelNameLowerCamel}Dtos = all.stream().map(this::convert2${modelNameUpperCamel}).collect(Collectors.toList());
            return PageableResDataBuilder.ok(${modelNameLowerCamel}Dtos, currPage, size, totalCount);
        }
    }

    private ${modelNameUpperCamel}Dto convert2${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameUpperCamel}Dto ${modelNameLowerCamel}Dto = new ${modelNameUpperCamel}Dto();
        BeanUtils.copyProperties(${modelNameLowerCamel}, ${modelNameLowerCamel}Dto);
        return ${modelNameLowerCamel}Dto;
    }
}
