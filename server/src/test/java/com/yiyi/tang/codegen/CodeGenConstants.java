package com.yiyi.tang.codegen;

/**
 * @author tangmingjian 2018-12-23 下午1:57
 **/
public class CodeGenConstants {
    public static final String BASE_PACKAGE = "com.yiyi.tang";
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".services";
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".api";
    public static final String CONTROLLER_IMPL_PACKAGE = CONTROLLER_PACKAGE + ".impl";
    public static final String DTO_PACKAGE = BASE_PACKAGE+".dto";
    public static final String RESPONSE_PACKAGE = BASE_PACKAGE+".response";

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".base.Mapper";//Mapper插件基础接口的完全限定名

}
