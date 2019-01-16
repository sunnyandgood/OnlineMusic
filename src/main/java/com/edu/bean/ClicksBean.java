package com.edu.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 16:21
 */
@Data
public class ClicksBean {
    private Integer click_id;
    private Integer user_id;
    private String user_name;
    private Integer song_id;
    private String song_name;
    private Date click_date;
}
