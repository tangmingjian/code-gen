package ${basePackage}.impl;

import ${mapperPackage}.${modelNameUpperCamel}Mapper;
import ${modelPackage}.${modelNameUpperCamel};
import ${basePackage}.base.AbstractService;
import ${basePackage}.${modelNameUpperCamel}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@author ${author} ${date}
 **/
@Service
@Slf4j
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}, ${idType}> implements ${modelNameUpperCamel}Service<${modelNameUpperCamel}, ${idType}> {

    @Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;
}
