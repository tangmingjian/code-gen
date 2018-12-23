package com.yiyi.tang.response;

/**
 * @author tangmingjian 2018-12-22 下午1:45
 **/
public class ResDataBuilder {

    private ResDataBuilder() {
    }

    public static <T> ResData<T> ok(T data) {
        return new ResData(ResCode.SUCCESS, data);
    }

    public static <T> ResData<T> error() {
        return new ResData(ResCode.ERROR, "");
    }

    public static <T> ResData<T> illegalParams(String tips) {
        return new ResData(ResCode.PARAMS_ERROR, tips);
    }

    public static <T> ResData<T> ok(Integer code, String msg, T data) {
        return new ResData(code, msg, data);
    }
}
