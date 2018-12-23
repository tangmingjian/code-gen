package com.yiyi.tang.response;

/**
 * @author tangmingjian 2018-12-22 下午1:45
 **/
public class PageableResDataBuilder {

    private PageableResDataBuilder() {
    }

    public static <T> PageableResData<T> ok(T data, int currPage, int size, int totalSize) {
        return new PageableResData(ResCode.SUCCESS, data, currPage, size, totalSize);
    }

    public static <T> PageableResData<T> error() {
        return new PageableResData(ResCode.ERROR);
    }

    public static <T> PageableResData<T> illegalParams(String tips) {
        return new PageableResData(ResCode.ERROR, tips);
    }
}
