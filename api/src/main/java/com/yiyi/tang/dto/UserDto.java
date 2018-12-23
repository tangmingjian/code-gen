package com.yiyi.tang.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tangmingjian 2018-12-22 下午2:59
 **/
@Data
public class UserDto implements Serializable {

    private String id;

    private String username;

    private String address;

    private String phone;

    private Boolean enable;

    private Date createDate;

    private Date updateDate;
}

