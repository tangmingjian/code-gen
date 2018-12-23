package com.yiyi.tang.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tangmingjian 2018-12-22 下午1:20
 **/
@Data
public class ResData<T> implements Serializable {
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    ResData(ResCode resCode, T data) {
        this.code = resCode.getCode();
        this.msg = resCode.getDesc();
        this.data = data;
    }


    ResData(ResCode resCode, String tips) {
        this.code = resCode.getCode();
        this.msg = resCode.getDesc() + "(" + tips + ")";
    }


    ResData(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
