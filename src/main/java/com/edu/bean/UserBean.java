package com.edu.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 16:42
 */
@Data
public class UserBean {
    private Integer user_id;
    private String user_name;
    private String user_password;
    private Integer vip_id;
    private String vip;
    private Date user_birthday;
    private String user_gender;
    private Integer type_id;
    private String type_name;
}
