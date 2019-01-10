package com.edu.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 10:04
 */
@Data
public class UserDisplayBean {
    private Integer user_id;
    private String user_name;
    private String user_password;
    private String vip;
    private Date user_birthday;
    private String user_gender;
    private String type_name;
}
