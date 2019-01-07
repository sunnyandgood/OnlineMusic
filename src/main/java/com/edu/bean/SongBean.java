package com.edu.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 16:36
 */
@Data
public class SongBean {
    private Integer song_id;
    private String song_name;
    private String song_singer;
    private Integer type_id;
    private Double song_size;
    private String song_url;
    private String song_format;
    private Integer song_clicks;
    private Integer song_download;
    private Date song_uptime;
    private Integer vip_id;
}
