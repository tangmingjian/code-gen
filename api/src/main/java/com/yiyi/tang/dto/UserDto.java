package com.yiyi.tang.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Tangmingjian 2018-12-25 15:07:20
 **/
@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String address;

    private String phone;

    private String slat;

    private Boolean enable;

    private Date createDate;

    private Date updateDate;
}

