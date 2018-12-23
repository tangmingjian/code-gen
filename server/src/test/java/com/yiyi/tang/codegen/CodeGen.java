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

import static com.yiyi.tang.codegen.CodeGenConstants.*;

/**
 * @author tangmingjian 2018-12-22 下午1:09
 **/
public class CodeGen {

    public static final String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    public static final String AUTHOR = "Tangmingjian";
    /********************数据库信息********************/
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
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


    public static void main(String[] args) {
        List<TableToModel> list = new ArrayList() {{
            add(TableToModel.builder().tableName("t_user").modelName("User").idType("String").build());
        }};

        list.stream()
                .map(CodeGen::setDefaultModelName)
                .forEach(CodeGen::genCode);
    }

    private static TableToModel setDefaultModelName(TableToModel o){
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
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(DAO_MODULE_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(DAO_MODULE_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(DAO_MODULE_PATH + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableToModel.getTableName());
        tableConfiguration.setDomainObjectName(tableToModel.getModelName());
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

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

        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }
        System.out.println(tableToModel.getModelName() + ".java 生成成功");
        System.out.println(tableToModel.getModelName() + "Mapper.java 生成成功");
        System.out.println(tableToModel.getModelName() + "Mapper.xml 生成成功");
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
            data.put("mapperPackage",MAPPER_PACKAGE);
            data.put("modelPackage",MODEL_PACKAGE);
            data.put("idType",tableToModel.getIdType());

            File service = new File(SERVICE_MODULE_PATH + JAVA_PATH + package2Path(SERVICE_PACKAGE) + tableToModel.getModelName() + "Service.java");
            if (!service.getParentFile().exists()) {
                service.getParentFile().mkdirs();
            }
            configuration.getTemplate("Service.ftl").process(data, new FileWriter(service));
            System.out.println(tableToModel.getModelName() + "Service.java 生成成功");



            File serviceImpl = new File(SERVICE_MODULE_PATH + JAVA_PATH + package2Path(SERVICE_IMPL_PACKAGE) + tableToModel.getModelName() + "ServiceImpl.java");
            if (!serviceImpl.getParentFile().exists()) {
                serviceImpl.getParentFile().mkdirs();
            }
            configuration.getTemplate("ServiceImpl.ftl").process(data, new FileWriter(serviceImpl));
            System.out.println(tableToModel.getModelName() + "ServiceImpl.java 生成成功");
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
            data.put("servicePackage",SERVICE_PACKAGE);
            data.put("modelPackage",MODEL_PACKAGE);
            data.put("apiPackage",CONTROLLER_PACKAGE);
            data.put("dtoPackage",DTO_PACKAGE);
            data.put("responsePackage",RESPONSE_PACKAGE);

            File server = new File(SERVER_MODULE_PATH + JAVA_PATH + package2Path(CONTROLLER_IMPL_PACKAGE) + tableToModel.getModelName() + "ControllerImpl.java");
            if (!server.getParentFile().exists()) {
                server.getParentFile().mkdirs();
            }
            configuration.getTemplate("ControllerImpl.ftl").process(data, new FileWriter(server));
            System.out.println(tableToModel.getModelName() + "ControllerImpl.java 生成成功");
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
            data.put("dtoPackage",DTO_PACKAGE);
            data.put("responsePackage",RESPONSE_PACKAGE);

            File api = new File(API_MODULE_PATH + JAVA_PATH + package2Path(CONTROLLER_PACKAGE) + tableToModel.getModelName() + "Controller.java");
            if (!api.getParentFile().exists()) {
                api.getParentFile().mkdirs();
            }
            configuration.getTemplate("Controller.ftl").process(data, new FileWriter(api));
            System.out.println(tableToModel.getModelName() + "Controller.java 生成成功");
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