package com.edu.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 16:33
 */
@Data
public class DownloadBean {
    private Integer download_id;
    private Integer user_id;
    private String user_name;
    private Integer song_id;
    private String song_name;
    private Date download_date;
}
