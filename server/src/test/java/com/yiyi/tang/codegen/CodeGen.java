package com.yiyi.tang.codegen;

import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tangmingjian 2018-12-22 下午1:09
 **/
public class CodeGen {

    private static final String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private static final String AUTHOR = "Tangmingjian";
    /********************数据库信息********************/
    private static final String JDBC_URL = "jdbc:oracle:thin:@10.103.21.17:1521:zggpayptest";
    private static final String JDBC_USERNAME = "gpay";
    private static final String JDBC_PASSWORD = "gpay_1234";
    private static final String JDBC_DIVER_CLASS_NAME = "oracle.jdbc.OracleDriver";
    /********************数据库信息********************/

    /********************项目路径********************/
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    /********************项目路径********************/

    /********************Java文件路径********************/
    private static final String JAVA_PATH = "/src/main/java";
    /********************Java文件路径********************/

    /********************Resources文件路径********************/
    private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径
    /********************Resources文件路径********************/


    /********************Dao模块路径********************/
    private static final String DAO_MODULE_PATH = PROJECT_PATH + "/dao";
    /********************Dao模块路径********************/


    /********************Service模块路径********************/
    private static final String SERVICE_MODULE_PATH = PROJECT_PATH + "/service";
    /********************Service模块路径********************/


    /********************Server模块路径********************/
    private static final String SERVER_MODULE_PATH = PROJECT_PATH + "/server";
    /********************Server模块路径********************/


    /********************Api模块路径********************/
    private static final String API_MODULE_PATH = PROJECT_PATH + "/api";
    /********************Api模块路径********************/


    /********************项目模版路径********************/
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/server/src/test/resources/generator/templates";
    /********************项目模版路径********************/

    private static final String BASE_PACKAGE = "com.yiyi.tang";
    private static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";
    private static final String MAPPER_PACKAGE = BASE_PACKAGE + ".mappers";
    private static final String SERVICE_PACKAGE = BASE_PACKAGE + ".services";
    private static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
    private static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".api";
    private static final String CONTROLLER_IMPL_PACKAGE = CONTROLLER_PACKAGE + ".impl";
    private static final String DTO_PACKAGE = BASE_PACKAGE + ".dto";
    private static final String RESPONSE_PACKAGE = BASE_PACKAGE + ".response";
    private static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".base.Mapper";//Mapper插件基础接口的完全限定名


    public static void main(String[] args) {
        new ArrayList<TableToModel>() {{
            add(TableToModel.builder().tableName("td_auth_order").modelName("AuthOrder").idType("Integer").build());
            //add other tables
        }}
                .stream()
                .map(CodeGen::setDefaultModelName)
                .forEach(CodeGen::genCode);
    }

    private static TableToModel setDefaultModelName(TableToModel o) {
        if (StringUtils.isEmpty(o.getModelName())) {
            o.setModelName(tableName2ModelNameUpperCamel(o.getTableName()));
        }
        return o;
    }

    private static void genCode(TableToModel tableToModel) {
        genDaoCode(tableToModel);
        genServiceCode(tableToModel);
        genServerCode(tableToModel);
        genApiCode(tableToModel);
    }


    private static void genDaoCode(TableToModel tableToModel) {
        System.out.println(String.format("gen table %S dao code begin", tableToModel.getTableName()));
        Context context = new Context(ModelType.FLAT);
        context.setId("Code-gen");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);


        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
        pluginConfiguration.addProperty("lombok", "Getter,Setter,ToString");
        pluginConfiguration.addProperty("useMapperCommentGenerator", "false");
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(DAO_MODULE_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(DAO_MODULE_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mappers");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(DAO_MODULE_PATH + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableToModel.getTableName());
        tableConfiguration.setDomainObjectName(tableToModel.getModelName());
//        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "select SEQ_{1}.nextval from dual", false, "pre"));
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", false, "pre"));
        context.addTableConfiguration(tableConfiguration);

//        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
//        commentGeneratorConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperCommentGenerator");
//        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            DefaultShellCallback callback = new DefaultShellCallback(true);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
            System.out.println(warnings);
        } catch (Exception e) {
            throw new RuntimeException("gen Model and Mapper failed", e);
        }
        System.out.println(tableToModel.getModelName() + ".java gen successful");
        System.out.println(tableToModel.getModelName() + "Mapper.java gen successful");
        System.out.println(tableToModel.getModelName() + "Mapper.xml gen successful");
        System.out.println(String.format("gen table %S dao code end", tableToModel.getTableName()));
    }

    private static void genServiceCode(TableToModel tableToModel) {
        System.out.println(String.format("gen table %S service code begin", tableToModel.getTableName()));
        try {
            freemarker.template.Configuration configuration = getConfiguration();
            Map<String, String> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("modelNameUpperCamel", tableToModel.getModelName());
            data.put("modelNameLowerCamel", tableName2ModelNameLowerCamel(tableToModel.getModelName()));
            data.put("basePackage", SERVICE_PACKAGE);
            data.put("mapperPackage", MAPPER_PACKAGE);
            data.put("modelPackage", MODEL_PACKAGE);
            data.put("idType", tableToModel.getIdType());

            File service = new File(SERVICE_MODULE_PATH + JAVA_PATH + package2Path(SERVICE_PACKAGE) + tableToModel.getModelName() + "Service.java");
            if (!service.getParentFile().exists()) {
                service.getParentFile().mkdirs();
            }
            configuration.getTemplate("Service.ftl").process(data, new FileWriter(service));
            System.out.println(tableToModel.getModelName() + "Service.java gen successful");


            File serviceImpl = new File(SERVICE_MODULE_PATH + JAVA_PATH + package2Path(SERVICE_IMPL_PACKAGE) + tableToModel.getModelName() + "ServiceImpl.java");
            if (!serviceImpl.getParentFile().exists()) {
                serviceImpl.getParentFile().mkdirs();
            }
            configuration.getTemplate("ServiceImpl.ftl").process(data, new FileWriter(serviceImpl));
            System.out.println(tableToModel.getModelName() + "ServiceImpl.java gen successful");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(String.format("gen table %S service code end", tableToModel.getTableName()));
    }

    private static freemarker.template.Configuration getConfiguration() throws Exception {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }


    private static void genServerCode(TableToModel tableToModel) {
        System.out.println(String.format("gen table %S server code begin", tableToModel.getTableName()));
        try {
            freemarker.template.Configuration configuration = getConfiguration();
            Map<String, String> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("modelNameUpperCamel", tableToModel.getModelName());
            data.put("modelNameLowerCamel", tableName2ModelNameLowerCamel(tableToModel.getModelName()));
            data.put("basePackage", CONTROLLER_IMPL_PACKAGE);
            data.put("servicePackage", SERVICE_PACKAGE);
            data.put("modelPackage", MODEL_PACKAGE);
            data.put("apiPackage", CONTROLLER_PACKAGE);
            data.put("dtoPackage", DTO_PACKAGE);
            data.put("responsePackage", RESPONSE_PACKAGE);
            data.put("idType",tableToModel.getIdType());

            File server = new File(SERVER_MODULE_PATH + JAVA_PATH + package2Path(CONTROLLER_IMPL_PACKAGE) + tableToModel.getModelName() + "ControllerImpl.java");
            if (!server.getParentFile().exists()) {
                server.getParentFile().mkdirs();
            }
            configuration.getTemplate("ControllerImpl.ftl").process(data, new FileWriter(server));
            System.out.println(tableToModel.getModelName() + "ControllerImpl.java gen successful");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(String.format("gen table %S server code end", tableToModel.getTableName()));
    }

    private static void genApiCode(TableToModel tableToModel) {
        System.out.println(String.format("gen table %S api code begin", tableToModel.getTableName()));
        try {
            freemarker.template.Configuration configuration = getConfiguration();
            Map<String, String> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("modelNameUpperCamel", tableToModel.getModelName());
            data.put("modelNameLowerCamel", tableName2ModelNameLowerCamel(tableToModel.getModelName()));
            data.put("basePackage", CONTROLLER_PACKAGE);
            data.put("dtoPackage", DTO_PACKAGE);
            data.put("responsePackage", RESPONSE_PACKAGE);
            data.put("idType",tableToModel.getIdType());

            File api = new File(API_MODULE_PATH + JAVA_PATH + package2Path(CONTROLLER_PACKAGE) + tableToModel.getModelName() + "Controller.java");
            if (!api.getParentFile().exists()) {
                api.getParentFile().mkdirs();
            }
            configuration.getTemplate("Controller.ftl").process(data, new FileWriter(api));
            System.out.println(tableToModel.getModelName() + "Controller.java gen successful");


            File dto = new File(API_MODULE_PATH + JAVA_PATH + package2Path(DTO_PACKAGE) + tableToModel.getModelName() + "Dto.java");
            if (!dto.getParentFile().exists()) {
                dto.getParentFile().mkdirs();
            }
            configuration.getTemplate("Dto.ftl").process(data, new FileWriter(dto));
            System.out.println(tableToModel.getModelName() + "Dto.java gen successful");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(String.format("gen table %S api code end", tableToModel.getTableName()));
    }

    private static String tableName2ModelNameLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    private static String tableName2ModelNameUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }

    private static String package2Path(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}
