package com.yiyi.tang.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tangmingjian 2018-12-22 下午1:18
 **/
@AllArgsConstructor
@Getter
public enum ResCode {

    SUCCESS(200, "成功"),

    PARAMS_ERROR(400, "参数错误"),

    ERROR(500, "系统异常");


    private Integer code;
    private String desc;
}
