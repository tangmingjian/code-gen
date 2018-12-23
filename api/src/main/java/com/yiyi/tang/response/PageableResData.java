package com.yiyi.tang.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author tangmingjian 2018-12-22 下午1:24
 **/
@Data
@ToString(callSuper = true)
public class PageableResData<T> extends ResData<T> {
    /**
     * 当前页
     */
    private int currPage;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 是否有前一页
     */
    private boolean hasBefore;

    /**
     * 是否有后一页
     */
    private boolean hasNext;

    /**
     * 页大小
     */
    private long size;

    /**
     * 总数
     */
    private long totalSize;


    PageableResData(int code, String msg, T data, int currPage, int size, int totalSize) {
        super(code, msg, data);
        setPageInfo(currPage, size, totalSize);
    }

    PageableResData(ResCode code, T data, int currPage, int size, int totalSize) {
        super(code, data);
        setPageInfo(currPage, size, totalSize);
    }


    PageableResData(ResCode code, String tips) {
        super(code.getCode(), code.getDesc() + "(" + tips + ")", null);
    }

    PageableResData(ResCode code) {
        super(code.getCode(), code.getDesc(), null);
    }

    private void setPageInfo(int currPage, int size, int totalSize) {
        this.currPage = currPage;
        this.size = size;
        this.totalSize = totalSize;
        this.totalPages = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
        this.hasBefore = currPage > 0 && totalPages > 1;
        this.hasNext = currPage>0 && currPage < totalPages;
    }
}
